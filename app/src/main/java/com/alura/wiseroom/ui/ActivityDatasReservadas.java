package com.alura.wiseroom.ui;

import android.content.Intent;
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
import com.alura.wiseroom.model.OrganizacaoModel;
import com.alura.wiseroom.model.ReservaModel;
import com.alura.wiseroom.model.SalaModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    private void mostraDescricaoReserva(int posicao){

    }

    private void init () {
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

    public void verificaReserva(final String idSala){
        String url = "http://172.30.248.130:8080/ReservaDeSala/rest/reserva/byIdSala";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resposta) {
                        try {
                            Log.i("teste responsee", resposta);

                            Gson gson = new Gson();


                            Type listType = new TypeToken<List<ReservaModel>>() {
                            }.getType();
                            List<ReservaModel> reservas = gson.fromJson(resposta, listType);


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
                        } catch (Exception e){e.printStackTrace();}
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("authorization", "secret");
                params.put("idSala", idSala);

                return params;
            }
        };


        mQueue.add(request);
    }

    /* public void recebeColaborador(final String idColaborador){
        String url = "http://172.30.248.130:8080/ReservaDeSala/rest/colaborador/byIdColaborador";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resposta) {
                        try {
                            Log.i("response col ", resposta);
                            Gson gson = new Gson();

                            ColaboradorModel colaboradorJson = gson.fromJson(resposta, ColaboradorModel.class);
                            ColaboradorModel colaboradorRecebidoJson = new ColaboradorModel();

                            colaboradorRecebidoJson.setIdColaborador(colaboradorJson.getIdColaborador());
                            colaboradorRecebidoJson.setNomeColaborador(colaboradorJson.getNomeColaborador());
                            colaboradorRecebidoJson.setEmailColaborador(colaboradorJson.getEmailColaborador());
                            colaboradorRecebidoJson.setAdministrador(colaboradorJson.isAdministrador());

                            //  OrganizacaoModel organizacaoJson = colaboradorJson.getOrganizacaoColaborador();
                            //      OrganizacaoModel organizacaoModel = new OrganizacaoModel();

                            //      organizacaoModel.setIdOrganizacao(organizacaoJson.getIdOrganizacao());
                            //      organizacaoModel.setNomeOrganizacao(organizacaoJson.getNomeOrganizacao());
                            //      organizacaoModel.setDominioOrganizacao(organizacaoJson.getDominioOrganizacao());
                            //      colaboradorRecebidoJson.setOrganizacaoColaborador(organizacaoModel);

                            colaboradorBodega = new ColaboradorModel();
                            colaboradorBodega = colaboradorRecebidoJson;
                        } catch(Exception e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("authorization", "secret");
                params.put("idColaborador", idColaborador);

                return params;
            }
        };
        mQueue.add(request);

    } */

}







