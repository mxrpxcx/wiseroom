package com.alura.wiseroom.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.ReservaAdapter;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.DataModel;
import com.alura.wiseroom.model.ReservaModel;
import com.alura.wiseroom.model.SalaModel;

import java.util.ArrayList;

public class ActivityDatasReservadas extends AppCompatActivity {

    ListView listView;
    ReservaAdapter reservaAdapter;
    ArrayList<ReservaModel> listaReservas = new ArrayList<>();
    ArrayList<Integer> listIds = new ArrayList<>();
    boolean flagEditAlarm = false;
    SalaModel salaSelecioanda;
    ColaboradorModel colaboradorLogado;
    DataModel dataSelecionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas_reservadas);
        recebeDados();
        Log.i("Reservas ID COL", colaboradorLogado.toString());
        Log.i("Reservas ID SAL", salaSelecioanda.toString());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        fetchDatabaseToArrayList();
    }


    private void init () {
        listView = (ListView) findViewById(R.id.listViewY);
    }

    private void fetchDatabaseToArrayList() {
        listaReservas.clear();
        listIds.clear();
        WiseRoomDB wise = new WiseRoomDB(this);
        SQLiteDatabase db = wise.getReadableDatabase();


        String selecao = WiseRoomDB.COLUNA_ID_SALA_RESERVADA +" = '"+salaSelecioanda.getId()+"'";
        Cursor cursor = wise.selecionarReserva(db, selecao);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);


                ReservaModel reservaModel = new ReservaModel();
                reservaModel.setId(String.valueOf(id));
                reservaModel.setColaboradorReserva(colaboradorLogado);
                reservaModel.setSalaReserva(salaSelecioanda);



                cursor = db.rawQuery("SELECT * FROM " + WiseRoomDB.TABELA_NOME_DATA + " WHERE " +
                        WiseRoomDB.COLUNA_ID_DATA + "=?", new String[]{reservaModel.getId()});
                if (cursor != null) {
                    while (cursor.moveToNext()) {

                        String dataSelecionadaData = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_DATA_MARCADA));
                        String horaSelecionadaComeco = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_HORARIO_MARCADO));
                       //String horaSelecionadaFim = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_HORARIO_MARCADO));
                        String descricaoReuniao = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_NOME_DATA));

                        DataModel dataSelecionada = new DataModel();
                        dataSelecionada.setNomeData(descricaoReuniao);
                        dataSelecionada.setDataData(dataSelecionadaData);
                        dataSelecionada.setHoraData(horaSelecionadaComeco);


                        reservaModel.setDataReservada(dataSelecionada);

                        Log.i("TESTE RESERVA LIST ", reservaModel.toString());
                        listaReservas.add(reservaModel);
                        listIds.add(id);

                    }


                }



            }


            cursor.close();
            db.close();

            reservaAdapter = new ReservaAdapter(ActivityDatasReservadas.this, R.layout.item_lista_reserva, listaReservas);
            listView.setAdapter(reservaAdapter);
        }

    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if(intentMain.hasExtra("colaboradorLogado")){

            ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
            colaboradorLogado = col;

        }

        if(intentMain.hasExtra("salaSelecionada")){

            SalaModel sal = (SalaModel) intentMain.getSerializableExtra("salaSelecionada");
            salaSelecioanda = sal;

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
