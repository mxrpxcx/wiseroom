package com.alura.wiseroom.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alura.wiseroom.R;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.SalaModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.MyViewHolder> {

    private Context context;
    private final List<SalaModel> listaSalas;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sala;
        public TextView data;

        public MyViewHolder(View view) {
            super(view);
            sala = view.findViewById(R.id.sala);
            data = view.findViewById(R.id.data);
        }
    }


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

        Log.i("TesteSala", "Sala "+sala.getNome());

        holder.sala.setText(sala.getNome());
        holder.data.setText(formatDate(sala.getDataSala()));
    }

    @Override
    public int getItemCount() {
        return listaSalas.size();
    }


    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}