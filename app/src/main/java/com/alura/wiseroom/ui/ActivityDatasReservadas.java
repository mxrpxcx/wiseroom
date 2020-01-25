package com.alura.wiseroom.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.ReservaAdapter;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.ReservaModel;

import java.util.ArrayList;

public class ActivityDatasReservadas extends AppCompatActivity {

    ListView listView;
    ReservaAdapter reservaAdapter;
    ArrayList<ReservaModel> listaReservas = new ArrayList<>();
    ArrayList<Integer> listIds = new ArrayList<>();
    boolean flagEditAlarm = false;
    String idSala;
    String idColaborador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas_reservadas);
        idColaborador = getIntent().getStringExtra("idSala");
        idSala = getIntent().getStringExtra("idColaborador");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        fetchDatabaseToArrayList();
    }


    private void init () {
        listView = (ListView) findViewById(R.id.listViewX);
    }

    private void fetchDatabaseToArrayList() {
        listaReservas.clear();
        listIds.clear();
        WiseRoomDB wise = new WiseRoomDB(this);
        SQLiteDatabase db = wise.getReadableDatabase();

        String selecao = WiseRoomDB.COLUNA_ID_SALA_RESERVADA +" = '"+idColaborador+"'";
        Cursor cursor = wise.selecionarReserva(db, selecao);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String data = cursor.getString(2);
                String hora = cursor.getString(3);

                ReservaModel reservaModel = new ReservaModel();
                reservaModel.getColaboradorQueReservou().setId(idColaborador);
                reservaModel.getSalaReservada().setId(idSala);
                reservaModel.getDataReservada();


                listaReservas.add(reservaModel);
                listIds.add(id);
            }
            cursor.close();
            db.close();

            reservaAdapter = new ReservaAdapter(ActivityDatasReservadas.this, R.layout.item_lista_reserva, listaReservas);
            listView.setAdapter(reservaAdapter);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(flagEditAlarm==true) {
            fetchDatabaseToArrayList();
            long id = ActivityEditarDataAgendada.idUpdate;

            flagEditAlarm=false;
        }
    }
}
