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
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.ReservaModel;
import com.alura.wiseroom.model.SalaModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDatasReservadas extends AppCompatActivity {

    ListView listView;
    ReservaAdapter reservaAdapter;
    ArrayList<ReservaModel> listaReservas = new ArrayList<>();
    ArrayList<String> listIds = new ArrayList<>();
    boolean flagEditAlarm = false;
    SalaModel salaSelecioanda;
    ColaboradorModel colaboradorLogado;
    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas_reservadas);
        mQueue = Volley.newRequestQueue(this);
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
        verificaReserva(salaSelecioanda.getId());
        reservaAdapter = new ReservaAdapter(ActivityDatasReservadas.this, R.layout.item_lista_reserva, listaReservas);
        listView.setAdapter(reservaAdapter);

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
            flagEditAlarm=false;
        }
    }

    public void verificaReserva(String idSala){
        String url = "http://172.30.248.130:3000/reserva?idSala="+idSala;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resposta) {
                        if (resposta.length() > 0) {
                            try {
                                for (int i = 0; i < resposta.length(); i++) {

                                    JSONObject reservaJson = resposta.getJSONObject(i);
                                    ReservaModel reservaRecebidaJson = new ReservaModel();

                                    reservaRecebidaJson.setId(reservaJson.getString("id"));
                                    reservaRecebidaJson.setDescricaoData(reservaJson.getString("descricao"));
                                    reservaRecebidaJson.setDataMarcada(reservaJson.getString("dataReservada"));
                                    reservaRecebidaJson.setHoraInicio(reservaJson.getString("horaInicio"));
                                    reservaRecebidaJson.setHoraFim(reservaJson.getString("horaFim"));
                                    reservaRecebidaJson.setColaboradorReserva(colaboradorLogado);
                                    reservaRecebidaJson.setSalaReserva(salaSelecioanda);



                                    listaReservas.add(reservaRecebidaJson);
                                    listIds.add(reservaRecebidaJson.getId());

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
    
}

