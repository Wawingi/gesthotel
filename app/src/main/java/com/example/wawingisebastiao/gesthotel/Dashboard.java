package com.example.wawingisebastiao.gesthotel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Wawingi Sebastiao on 20/04/2019.
 */

public class Dashboard extends AppCompatActivity implements Runnable {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    public void run(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
