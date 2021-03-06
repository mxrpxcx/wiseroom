package com.alura.wiseroom.ui.sala;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.SalaAdapter;
import com.alura.wiseroom.constants.Constants;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.Event;
import com.alura.wiseroom.model.SalaModel;
import com.alura.wiseroom.network.HttpRequest;
import com.alura.wiseroom.ui.colaborador.ActivityPerfil;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityListarSalas extends AppCompatActivity {

    ListView listView;
    SalaAdapter salaAdapter;
    ArrayList<SalaModel> listaSalas = new ArrayList<>();
    TextView tvNomeSala;
    ColaboradorModel colaboradorLogado;
    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_salas);
        mQueue = Volley.newRequestQueue(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        recebeDados();
        init();

        tvNomeSala.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                fetchDatabaseToArrayList();
                return true;
            }
        });

    }


    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if (intentMain.hasExtra("colaboradorLogado")) {

            ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
            colaboradorLogado = col;

        }
    }

    private void init() {
        listView = findViewById(R.id.listViewSala);
        tvNomeSala = findViewById(R.id.editBuscaSala);
    }


    private void fetchDatabaseToArrayList() {

        listaSalas.clear();
        verificaSala(String.valueOf(tvNomeSala));
        salaAdapter = new SalaAdapter(ActivityListarSalas.this, R.layout.item_lista_sala, listaSalas);
        listView.setAdapter(salaAdapter);

    }

    public void verificaSala(final String nomeSala) {

        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("nomeSala", nomeSala);
            String url = Constants.url + "/sala/getSalaByNome";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "GET", "ListaSala").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void customEventReceived(Event event) {
        if (event.getEventName().equals("ListaSala" + Constants.eventSuccessLabel)) {
            Log.i("teste responsee", event.getEventMsg());

            Gson gson = new Gson();

            Type listType = new TypeToken<List<SalaModel>>() {
            }.getType();
            List<SalaModel> salas = gson.fromJson(event.getEventMsg(), listType);

            for (int i = 0; i < salas.size(); i++) {

                SalaModel salaRecebidaJson = new SalaModel();

                salaRecebidaJson = salas.get(i);
                Log.i("teste reserva ", salas.get(i).toString());

                salaRecebidaJson.setIdSala(salas.get(i).getIdSala());
                salaRecebidaJson.setDescricaoSala(salas.get(i).getDescricaoSala());
                salaRecebidaJson.setAreaSala(salas.get(i).getAreaSala());
                salaRecebidaJson.setCapacidadeSala(salas.get(i).getCapacidadeSala());
                salaRecebidaJson.setNomeSala(salas.get(i).getNomeSala());

                listaSalas.add(salaRecebidaJson);

            }

            salaAdapter = new SalaAdapter(ActivityListarSalas.this, R.layout.item_lista_sala, listaSalas);
            listView.setAdapter(salaAdapter);
        } else if (event.getEventName().equals("ListaSala" + Constants.eventErrorLabel)) {
            Snackbar snackbar = Snackbar.make(null, "Erro ao receber dados", Snackbar.LENGTH_LONG);
            snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            snackbar.show();
        }
    }


    @Override
    public void onBackPressed() {
        sair();
    }

    public void sair() {
        Intent intent = new Intent(ActivityListarSalas.this, ActivityPerfil.class);
        intent.putExtra("colaboradorLogado", colaboradorLogado);
        startActivity(intent);
        finish();
    }

}
