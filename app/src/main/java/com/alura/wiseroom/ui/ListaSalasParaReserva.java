package com.alura.wiseroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.DivisorDeSalas;
import com.alura.wiseroom.adapter.RecyclerTouchListener;
import com.alura.wiseroom.adapter.SalaAdapter;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.SalaModel;

import java.util.ArrayList;
import java.util.List;

public class ListaSalasParaReserva extends AppCompatActivity {
    private SalaAdapter salasAdapter;
    private List<SalaModel> listaSalas = new ArrayList<>();
    private RecyclerView recyclerView;
    private WiseRoomDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_salas_para_reserva);
        recyclerView = findViewById(R.id.recycler_view);

        db = new WiseRoomDB(this);
        Intent intentReserva = getIntent();
        String codigo = intentReserva.getStringExtra("codigoSala");
        int codigoDaSala = Integer.parseInt(codigo);
        listaSalas.add(db.getSala(codigoDaSala));

        Log.i("TesteSala", "lista Salas? "+listaSalas.get(codigoDaSala).getNome());

        salasAdapter = new SalaAdapter(this, listaSalas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DivisorDeSalas(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(salasAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
