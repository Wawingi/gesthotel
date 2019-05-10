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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wawingi Sebastiao on 06/05/2019.
 */
public class Estatistica extends AppCompatActivity {
    static int est1,est2,est3;
    String descricao[] = {"Sujo","Manutenção","Livre"};
    PieChart grafico;

    private static final String URL = "http://192.168.1.102/sigeia/Controller/Estatistica.php";
    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estatica);

        grafico = (PieChart)findViewById(R.id.graficoId);
        List<PieEntry> entradaGrafico = new ArrayList<>();

        int item[] = {est1,est2,est3};

        //Webservice begin
        requestQueue = Volley.newRequestQueue(this);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            try{

                estatisticaQuarto();

                //item = new int[3];
                //item[0] = est1;
                //item[1] = est2;
                //item[2] = est3;

                System.out.println("ESTADO C: "+est1);
                System.out.println("ESTADO C: "+est2);
                System.out.println("ESTADO C: "+est3);

                System.out.println("ARRAY: "+item[0]);

                for(int i=0;i<item.length;i++){
                    entradaGrafico.add(new PieEntry(item[i],descricao[i]));
                }

                PieDataSet dataSet = new PieDataSet(entradaGrafico,"");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                PieData pieData = new PieData(dataSet);

                grafico.setData(pieData);
                grafico.invalidate();


            } catch (Exception e){

            }
        }else{
            Toast.makeText(Estatistica.this,"Nenhum dado encontrado, Verifique a sua conexão com a Internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void estatisticaQuarto(){
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

                            //boolean isErro = jsonObject.getBoolean("erro");

                            //if(isErro){
                                est1 = jsonObject.getInt("sujo");
                                est2 = jsonObject.getInt("manutencao");
                                est3 = jsonObject.getInt("livre");

                            System.out.println("ESTADO: "+est1);
                            System.out.println("ESTADO: "+est2);
                            System.out.println("ESTADO: "+est3);

                            //}
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
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
