package com.example.wawingisebastiao.gesthotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

/**
 * Created by Wawingi Sebastiao on 22/04/2019.
 */

public class Pesquisa extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchView searchView;
    Button btnpesquisar;
    String d;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa);
        searchView = (SearchView)findViewById(R.id.serchview);
        btnpesquisar = (Button)findViewById(R.id.btnpesquisar);
        searchView.setOnQueryTextListener(this);


        d = String.valueOf(searchView.getQuery());

        btnpesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("PESQUISA: "+d);
            }
        });


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
