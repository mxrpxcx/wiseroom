package com.alura.wiseroom.ui;

import android.content.Intent;
import android.os.Bundle;
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
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;
    private TextView salasVaziasView;
    private WiseRoomDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_salas_para_reserva);
        constraintLayout = findViewById(R.id.constraint_layout);
        recyclerView = findViewById(R.id.recycler_view);
        salasVaziasView = findViewById(R.id.salas_vazias);

        db = new WiseRoomDB(this);
        Intent intentReserva = getIntent();
        String codigo = intentReserva.getStringExtra("codigoSala");
        int codigoDaSala = Integer.parseInt(codigo);
        listaSalas.addAll(db.getTodasSalas(codigoDaSala));

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
