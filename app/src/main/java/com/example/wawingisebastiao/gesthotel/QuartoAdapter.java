package com.example.wawingisebastiao.gesthotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Wawingi Sebastiao on 17/04/2019.
 */
public class QuartoAdapter extends RecyclerView.Adapter<QuartoAdapter.ViewHolder> {

    private List<QuartosClass> listItems;
    private Context context;

    public QuartoAdapter(List<QuartosClass> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterquarto,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QuartosClass listItem = listItems.get(position);
        holder.txtquarto.setText(String.valueOf(listItem.getQuarto()));
        holder.txttipologia.setText(listItem.getTipologia());
        holder.txtandar.setText(listItem.getAndar());
        holder.txtestado.setText(listItem.getEstado());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtquarto;
        public TextView txttipologia;
        public TextView txtandar;
        public TextView txtestado;

        public ViewHolder(View itemView) {
            super(itemView);

            txtquarto = (TextView)itemView.findViewById(R.id.txtquarto);
            txttipologia = (TextView)itemView.findViewById(R.id.txttipologia);
            txtandar = (TextView)itemView.findViewById(R.id.txtandar);
            txtestado = (TextView)itemView.findViewById(R.id.txtestado);
        }
    }
}
