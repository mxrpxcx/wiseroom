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
import com.alura.wiseroom.model.DataModel;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter {

    private Context context;
    private int layoutRes;
    private ArrayList<DataModel> listaDatas;

    private LayoutInflater inflater;

    public DataAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<DataModel> listaDatas) {
        super(context, resource,listaDatas);
        this.context = context;
        layoutRes = resource;
        this.listaDatas = listaDatas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(layoutRes,null);

            TextView data = (TextView) view.findViewById(R.id.tvData);
            TextView hora = (TextView) view.findViewById(R.id.tvHora);
            TextView nome = (TextView) view.findViewById(R.id.tvNome);

        DataModel dataModel = listaDatas.get(position);
        nome.setText(dataModel.getNomeData());
        hora.setText(dataModel.getHoraData());
        data.setText(dataModel.getDataData());
        return view;
    }
}
