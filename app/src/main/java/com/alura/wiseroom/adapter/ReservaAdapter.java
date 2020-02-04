package com.alura.wiseroom.adapter;

import android.content.Context;
import android.util.Log;
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

import org.w3c.dom.Text;

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

        ReservaModel reservaModel = listaReservas.get(position);

      //  ((TextView) view.findViewById(R.id.tvNomeColaborador)).setText(reservaModel.getColaboradorReserva().getNome());
        ((TextView) view.findViewById(R.id.tvHoraReservadaInicio)).setText(reservaModel.getHoraInicio());
        ((TextView) view.findViewById(R.id.tvHoraReservadaFim)).setText(reservaModel.getHoraFim());
        ((TextView) view.findViewById(R.id.tvDataReservada)).setText(reservaModel.getDataMarcada());

        return view;
    }

}
