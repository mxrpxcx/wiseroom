package com.alura.wiseroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alura.wiseroom.R;
import com.alura.wiseroom.model.ReservaModel;

import java.util.ArrayList;

public class ReservaAdapter extends ArrayAdapter {

    private Context context;
    private int layoutRes;
    private ArrayList<ReservaModel> listaReservas;

    private LayoutInflater inflater;

    public ReservaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ReservaModel> listaReservas) {
        super(context, resource,listaReservas);
        this.context = context;
        layoutRes = resource;
        this.listaReservas = listaReservas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(layoutRes,null);

        TextView dataReservada = (TextView) view.findViewById(R.id.tvDataReservada);
        TextView horaReservada = (TextView) view.findViewById(R.id.tvHoraReservada);
        TextView nomeColaborador = (TextView) view.findViewById(R.id.tvNomeColaborador);

        ReservaModel reservaModel = listaReservas.get(position);
        nomeColaborador.setText(reservaModel.getColaboradorQueReservou().getNome());
        horaReservada.setText(reservaModel.getDataReservada().getDataData());
        dataReservada.setText(reservaModel.getDataReservada().getHoraData());
        return view;
    }
}
