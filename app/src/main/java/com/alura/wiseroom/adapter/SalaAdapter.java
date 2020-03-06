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
import com.alura.wiseroom.model.SalaModel;

import java.util.ArrayList;


public class SalaAdapter extends ArrayAdapter {

    private int layoutRes;
    private ArrayList<SalaModel> listaSalas;

    private LayoutInflater inflater;

    public SalaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<SalaModel> listaSalas) {
        super(context, resource, listaSalas);
        layoutRes = resource;
        this.listaSalas = listaSalas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(layoutRes, null);

        SalaModel salaModel = listaSalas.get(position);

        ((TextView) view.findViewById(R.id.tvNomeSala)).setText(salaModel.getNomeSala());
        ((TextView) view.findViewById(R.id.tvCapacidadeSala)).setText(salaModel.getCapacidadeSala());
        ((TextView) view.findViewById(R.id.tvAreaSala)).setText(String.valueOf(salaModel.getAreaSala()));

        return view;
    }
}