package com.example.wawingisebastiao.gesthotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Wawingi Sebastiao on 22/04/2019.
 */

public class Adaptador extends BaseAdapter{
    Context context;
    List<QuartosClass> listaquartos;


    public Adaptador(Context context, List<QuartosClass> listaquartos) {
        this.context = context;
        this.listaquartos = listaquartos;
    }

    @Override

    public int getCount() {
        return listaquartos.size();
    }

    @Override
    public Object getItem(int posicao) {
        return listaquartos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return 0;
    }

    @Override
    public View getView(int posicao, View convertview, ViewGroup parent) {


        View vista = convertview;
        LayoutInflater inflate =LayoutInflater.from(context);
        vista = inflate.inflate(R.layout.adapterquarto,null);

        TextView txtquarto = (TextView)vista.findViewById(R.id.txtquarto);
        TextView txttipologia = (TextView)vista.findViewById(R.id.txttipologia);
        TextView txtandar = (TextView)vista.findViewById(R.id.txtandar);
        TextView txtestado = (TextView)vista.findViewById(R.id.txtestado);

        txtquarto.setText(String.valueOf(listaquartos.get(posicao).getQuarto()));
        txttipologia.setText(listaquartos.get(posicao).getTipologia().toString());
        txtandar.setText(listaquartos.get(posicao).getAndar().toString());
        txtestado.setText(listaquartos.get(posicao).getEstado().toString());

        return vista;
    }
}
