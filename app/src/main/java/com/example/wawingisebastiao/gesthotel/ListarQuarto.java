package com.example.wawingisebastiao.gesthotel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wawingi Sebastiao on 17/04/2019.
 */

public class ListarQuarto extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public List<QuartosClass> listItems;
    public ListView lv;

    private static final String URL = "http://192.168.1.102/sigeia/Controller/ListarQuarto.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listarquarto);

        lv = (ListView)findViewById(R.id.recyclerview);

        listItems = new ArrayList<>();

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            try{
                obterDados();

                Adaptador adaptador = new Adaptador(getApplicationContext(),listItems);
                lv.setAdapter(adaptador);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        QuartosClass quarto = (QuartosClass) lv.getItemAtPosition(i);

                        int quarto_numero = quarto.getQuarto();
                        String tipologia = quarto.getTipologia();
                        String andar = quarto.getAndar();
                        String estado = quarto.getEstado();

                        Intent intent = new Intent(ListarQuarto.this,ItemQuarto.class);
                        intent.putExtra("quarto",quarto_numero);
                        intent.putExtra("tipologia",tipologia);
                        intent.putExtra("andar",andar);
                        intent.putExtra("estado",estado);
                        startActivity(intent);
                    }
                });


            } catch (Exception e){

            }
        }else{
            Toast.makeText(ListarQuarto.this,"Nenhum dado encontrado, Verifique a sua conexão com a Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void obterDados() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("quartos");

                            for(int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                QuartosClass item = new QuartosClass();
                                item.setQuarto(o.getInt("quarto"));
                                item.setTipologia(o.getString("tipologia"));
                                item.setAndar(o.getString("andar"));
                                item.setEstado(o.getString("estado"));

                                listItems.add(item);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                Intent intent = new Intent(ListarQuarto.this,Pesquisa.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                Intent intent2 = new Intent(ListarQuarto.this,Estatistica.class);
                startActivity(intent2);
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
                Intent intent = new Intent(ListarQuarto.this,MainActivity.class);
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
