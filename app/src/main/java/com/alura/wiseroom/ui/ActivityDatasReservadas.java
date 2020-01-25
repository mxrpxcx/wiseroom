package com.alura.wiseroom.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.DataAdapter;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.DataModel;

import java.util.ArrayList;

public class ActivityDatasReservadas extends AppCompatActivity {

    ListView listView;
    DataAdapter dataAdapter;
    ArrayList<DataModel> listaDatas = new ArrayList<>();
    ArrayList<Integer> listIds = new ArrayList<>();
    boolean flagEditAlarm = false;
    String idRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas_reservadas);
        idRecebido = getIntent().getStringExtra("idSala");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        fetchDatabaseToArrayList();
    }


    private void init () {
        listView = (ListView) findViewById(R.id.listViewX);
    }

    private void fetchDatabaseToArrayList() {
        listaDatas.clear();
        listIds.clear();
        WiseRoomDB wise = new WiseRoomDB(this);
        SQLiteDatabase db = wise.getReadableDatabase();

        String selecao = WiseRoomDB.COLUNA_ID_SALA_MARCADA +" = '"+idRecebido+"'";
        Cursor cursor = wise.selecionarData(db, selecao);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String data = cursor.getString(2);
                String hora = cursor.getString(3);

                DataModel dataModel = new DataModel();
                dataModel.setNomeData(nome);
                dataModel.setDataData(data);
                dataModel.setHoraData(hora);
                dataModel.setIdSalaxData(idRecebido);


                listaDatas.add(dataModel);
                listIds.add(id);
            }
            cursor.close();
            db.close();

            dataAdapter = new DataAdapter(ActivityDatasReservadas.this, R.layout.item_lista_data, listaDatas);
            listView.setAdapter(dataAdapter);
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
