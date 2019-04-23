package com.example.wawingisebastiao.gesthotel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wawingi Sebastiao on 22/04/2019.
 */

public class ItemQuarto extends AppCompatActivity {
    int quarto;
    String tipologia,andar,estado,novoestado=null;
    private static final String URL = "http://192.168.1.102/sigeia/Controller/EditarQuarto.php";

    StringRequest stringRequest;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemquarto);

        TextView txtquarto = (TextView)findViewById(R.id.txtquarto);
        TextView txttipologia = (TextView)findViewById(R.id.txttipologia);
        TextView txtandar = (TextView)findViewById(R.id.txtandar);
        TextView txtestado = (TextView)findViewById(R.id.txtestado);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogrupo);
        Button btnestado = (Button)findViewById(R.id.btnestado);

        Intent recebeDados = getIntent();

        quarto = recebeDados.getIntExtra("quarto",-1);
        tipologia = recebeDados.getStringExtra("tipologia");
        andar = recebeDados.getStringExtra("andar");
        estado = recebeDados.getStringExtra("estado");

        txtquarto.setText(String.valueOf(quarto));
        txttipologia.setText(tipologia);
        txtandar.setText(andar);
        txtestado.setText(estado);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rdlivre:
                        novoestado = "LIVRE";
                        break;
                    case R.id.rdsujo:
                        novoestado = "SUJO";
                        break;
                    case R.id.rdocupado:
                        novoestado = "OCUPADO";
                        break;
                    case R.id.rdmanutec:
                        novoestado = "MANUTENÇÃO";
                        break;
                }
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        btnestado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarEstado();
            }
        });
    }

    public void editarEstado(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Log",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            if(isErro){
                                Toast.makeText(getApplicationContext(),"Desculpa! ocorreu algum erro...",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Estado do quarto alterado com sucesso.",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ItemQuarto.this,ListarQuarto.class);
                                startActivity(intent);
                                finish();
                            }
                        }catch (Exception e){
                            Log.v("Log",e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e("Log",error.getMessage());
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("novoestado",novoestado);
                params.put("quarto", String.valueOf(quarto));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Menu opções de três pontos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:

                return true;

            case R.id.item2:

                return true;

            case R.id.item3:
                caixaDialogo();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void caixaDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("ALERTA");
        builder.setMessage("Tens a certeza que pretendes sair?");
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent(ItemQuarto.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"Operação abortada",Toast.LENGTH_LONG).show();
                    }
                });
        builder.create().show();
    }

}
