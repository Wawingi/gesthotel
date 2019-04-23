package com.example.wawingisebastiao.gesthotel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends AppCompatActivity {
    EditText txtuser,txtsenha;
    Button btnlogar;
    private static final String URL = "http://192.168.1.102/sigeia/Controller/login.php";

    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtuser = (EditText)findViewById(R.id.edituser);
        txtsenha = (EditText)findViewById(R.id.editpassword);
        btnlogar = (Button)findViewById(R.id.btnlogar);

        requestQueue = Volley.newRequestQueue(this);

        //Verificação da conexão e passagem das permissões com internet
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();


            btnlogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(netInfo != null && netInfo.isConnectedOrConnecting()) {
                        boolean validar = true;
                        if (txtuser.getText().length() == 0) {
                            txtuser.setError("Nome de usuário requerido!");
                            txtuser.requestFocus();
                            validar = false;
                        }
                        if (txtsenha.getText().length() == 0) {
                            txtsenha.setError("Senha requerida");
                            txtsenha.requestFocus();
                            validar = false;
                        }

                        if (validar) {
                           validarlogin();
                        }
                    } else {
                        Toast.makeText(MainActivity.this,"Verifique a sua conexão com a Rede", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void validarlogin(){
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
                                Toast.makeText(getApplicationContext(),"Desculpa! as suas credenciais estão incorrectas, digite novamente.",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(MainActivity.this,ListarQuarto.class);
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
                params.put("utilizador",txtuser.getText().toString());
                params.put("senha",txtsenha.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
