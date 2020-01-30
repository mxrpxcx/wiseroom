package com.alura.wiseroom.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mostraDescricaoReserva(position);
            }
        });

    }

    private void mostraDescricaoReserva(int posicao){

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
                reservaModel.setSalaReserva(salaSelecioanda);

               Cursor cursor2 = db.rawQuery("SELECT * FROM " + WiseRoomDB.TABELA_NOME_DATA + " WHERE " +
                        WiseRoomDB.COLUNA_ID_DATA + "=?", new String[]{reservaModel.getId()});
                if (cursor2 != null) {
                    while (cursor2.moveToNext()) {



                        String dataSelecionadaData = cursor2.getString(cursor2.getColumnIndex(WiseRoomDB.COLUNA_DATA_MARCADA));
                        String horaSelecionadaComeco = cursor2.getString(cursor2.getColumnIndex(WiseRoomDB.COLUNA_HORARIO_MARCADO));
                       //String horaSelecionadaFim = cursor2.getString(cursor2.getColumnIndex(WiseRoomDB.COLUNA_HORARIO_MARCADO));
                        String descricaoReuniao = cursor2.getString(cursor2.getColumnIndex(WiseRoomDB.COLUNA_NOME_DATA));
                        String idColaboradorQueMarcou = cursor2.getString(cursor2.getColumnIndex(WiseRoomDB.COLUNA_ID_COLABORADOR_QUE_MARCOU));


                        dataSelecionada = new DataModel();
                        dataSelecionada.setNomeData(descricaoReuniao);
                        dataSelecionada.setDataData(dataSelecionadaData);
                        dataSelecionada.setHoraData(horaSelecionadaComeco);

                        reservaModel.setDataReservada(dataSelecionada);


                        Cursor cursor3 = db.rawQuery("SELECT * FROM " + WiseRoomDB.TABELA_NOME_COLABORADOR + " WHERE " +
                                WiseRoomDB.COLUNA_ID_COLABORADOR + "=?", new String[]{idColaboradorQueMarcou});
                        if (cursor3 != null) {
                            while (cursor3.moveToNext()) {
                                String colaboradorNome = cursor3.getString(cursor3.getColumnIndex(WiseRoomDB.COLUNA_NOME_COLABORADOR));
                                ColaboradorModel colaboradorModel = new ColaboradorModel();
                                colaboradorModel.setNome(colaboradorNome);
                                reservaModel.setColaboradorReserva(colaboradorModel);
                            }
                        }

                    }

                }


                Log.i("TESTE RESERVA LIST ", reservaModel.toString());
                listaReservas.add(reservaModel);
                listIds.add(id);
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
