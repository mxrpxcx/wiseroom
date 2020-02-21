package com.alura.wiseroom.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alura.wiseroom.R;
import com.alura.wiseroom.model.SalaModel;

import java.util.List;

public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.MyViewHolder> {

    private final List<SalaModel> listaSalas;
    private Context context;


    public SalaAdapter(Context context, List<SalaModel> listaSalas) {
        this.context = context;
        this.listaSalas = listaSalas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_salas, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SalaModel sala = listaSalas.get(position);

        Log.i("TesteSala", "Sala " + sala.getNomeSala());

        holder.sala.setText(sala.getNomeSala());
        holder.capacidade.setText(sala.getCapacidadeSala());
    }

    @Override
    public int getItemCount() {
        return listaSalas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sala;
        public TextView capacidade;

        public MyViewHolder(View view) {
            super(view);
            sala = view.findViewById(R.id.sala);
            capacidade = view.findViewById(R.id.capacidade);
        }
    }

}