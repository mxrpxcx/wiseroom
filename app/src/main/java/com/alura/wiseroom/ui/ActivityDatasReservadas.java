package com.alura.wiseroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.ReservaAdapter;
import com.alura.wiseroom.constants.Constants;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.Event;
import com.alura.wiseroom.model.OrganizacaoModel;
import com.alura.wiseroom.model.ReservaModel;
import com.alura.wiseroom.model.SalaModel;
import com.alura.wiseroom.network.HttpRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDatasReservadas extends AppCompatActivity {

    ListView listView;
    ReservaAdapter reservaAdapter;
    ArrayList<ReservaModel> listaReservas = new ArrayList<>();
    ArrayList<String> listIds = new ArrayList<>();
    // ArrayList<ColaboradorModel> listaColaboradores = new ArrayList<>();
    boolean flagEditAlarm = false;
    SalaModel salaSelecioanda;
    ColaboradorModel colaboradorLogado;
    RequestQueue mQueue;
    ColaboradorModel colaboradorBodega;

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


    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if (flagEditAlarm == true) {
            fetchDatabaseToArrayList();
            flagEditAlarm = false;
        }
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void mostraDescricaoReserva(int posicao) {

    }

    private void init() {
        listView = (ListView) findViewById(R.id.listViewY);
    }

    private void fetchDatabaseToArrayList() {

        listaReservas.clear();
        listIds.clear();
        verificaReserva(salaSelecioanda.getIdSala());
        reservaAdapter = new ReservaAdapter(ActivityDatasReservadas.this, R.layout.item_lista_reserva, listaReservas);
        listView.setAdapter(reservaAdapter);

    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if (intentMain.hasExtra("colaboradorLogado")) {

            ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
            colaboradorLogado = col;

        }

        if (intentMain.hasExtra("salaSelecionada")) {

            SalaModel sal = (SalaModel) intentMain.getSerializableExtra("salaSelecionada");
            salaSelecioanda = sal;

        }
    }


    public void verificaReserva(final String idSala) {

        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("idSala", idSala);
            String url = "http://172.30.248.130:8080/ReservaDeSala/rest/reserva/byIdSala";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "GET", "DataReservada").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void customEventReceived(Event event) {
        if (event.getEventName().equals("Login" + Constants.eventSuccessLabel)) {
            Log.i("teste responsee", event.getEventMsg());

            Gson gson = new Gson();

            Type listType = new TypeToken<List<ReservaModel>>() {
            }.getType();
            List<ReservaModel> reservas = gson.fromJson(event.getEventMsg(), listType);

            for (int i = 0; i < reservas.size(); i++) {

                ReservaModel reservaRecebidaJson = new ReservaModel();

                reservaRecebidaJson = reservas.get(i);
                Log.i("teste reserva ", reservas.get(i).toString());

                reservaRecebidaJson.setIdReserva(reservas.get(i).getIdReserva());
                reservaRecebidaJson.setDescricaoReserva(reservas.get(i).getDescricaoReserva());
                reservaRecebidaJson.setDataReserva(reservas.get(i).getDataReserva());
                reservaRecebidaJson.setHoraInicioReserva(reservas.get(i).getHoraInicioReserva());
                reservaRecebidaJson.setHoraFimReserva(reservas.get(i).getHoraFimReserva());
                reservaRecebidaJson.setIdColaborador(reservas.get(i).getIdColaboradorReserva());

                //     recebeColaborador(reservaRecebidaJson.getIdColaboradorReserva());


                //   reservaRecebidaJson.setColaboradorReserva(colaboradorBodega);
                reservaRecebidaJson.setSalaReserva(salaSelecioanda);

                Log.i("teste bodega", reservaRecebidaJson.getSalaReserva().toString());
                listaReservas.add(reservaRecebidaJson);
                listIds.add(reservaRecebidaJson.getIdReserva());

            }
        } else if (event.getEventName().equals("Login" + Constants.eventErrorLabel)) {
                Snackbar snackbar = Snackbar.make(null, "Erro ao realizar login", Snackbar.LENGTH_LONG);
                snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                snackbar.show();
            }
        }
    }








