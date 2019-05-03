package com.example.wawingisebastiao.gesthotel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wawingi Sebastiao on 22/04/2019.
 */

public class Pesquisa extends AppCompatActivity {
    Button btnpesquisar;
    EditText txtpesquisa;
    TextView txtquarto,txttipologia,txtandar,txtestado;
    int pesquisa=0;

    private static final String URL = "http://192.168.1.102/sigeia/Controller/ProcurarQuarto.php";

    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa);

        txtpesquisa = (EditText)findViewById(R.id.editpesquisa);
        btnpesquisar = (Button)findViewById(R.id.btnpesquisar);

        txtquarto = (TextView)findViewById(R.id.txtqrto);
        txttipologia = (TextView)findViewById(R.id.txttipologia);
        txtandar = (TextView)findViewById(R.id.txtandar);
        txtestado = (TextView)findViewById(R.id.txtestado);

        requestQueue = Volley.newRequestQueue(this);

        btnpesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verificação da conexão e passagem das permissões com internet
                StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
                ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo netInfo = cm.getActiveNetworkInfo();
                pesquisa = Integer.parseInt(txtpesquisa.getText().toString());

                pesquisarQuarto();

            }
        });

        //Back Button config
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //Back button click
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void pesquisarQuarto(){
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
                                Toast.makeText(getApplicationContext(),"Nenhum Quarto encontrado, por favor verifique o número e tente novamente!",Toast.LENGTH_LONG).show();
                            }else{

                                txtquarto.setText(String.valueOf(jsonObject.getInt("quarto")));
                                txttipologia.setText(jsonObject.getString("tipologia"));
                                txtandar.setText(jsonObject.getString("andar"));
                                txtestado.setText(jsonObject.getString("estado"));
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
                params.put("quarto", String.valueOf(pesquisa));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
