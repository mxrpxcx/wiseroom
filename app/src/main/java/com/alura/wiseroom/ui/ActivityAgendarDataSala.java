

package com.alura.wiseroom.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.ReservaAdapter;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.ReservaModel;
import com.alura.wiseroom.model.SalaModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAgendarDataSala extends AppCompatActivity {

    Button btData, btHoraInicio, btHoraFim;
    EditText etSobre;
    ImageButton btAdiciona;
    ListView listView;
    String etHoraInicio, etHoraFim, etData, etNome;
    ReservaAdapter reservaAdapter;
    ArrayList<ReservaModel> listaReservas = new ArrayList<>();
    AlarmManager alarmManager;
    boolean flagDeleteAlarm = false;
    boolean flagEditAlarm = false;
    SalaModel salaSelecioanda;
    ColaboradorModel colaboradorLogado;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_data_sala);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mQueue = Volley.newRequestQueue(this);
        init();
        setListeners();
        fetchDatabaseToArrayList();
        Log.i("Reservas ID COL", colaboradorLogado.toString());
        Log.i("Reservas ID SAL", salaSelecioanda.toString());

    }

    private void init() {
        listView = (ListView) findViewById(R.id.listViewX);
        btAdiciona = (ImageButton) findViewById(R.id.btAdiciona);
        etSobre = (EditText) findViewById(R.id.etSobre);
        btData = (Button) findViewById(R.id.btData);
        btHoraInicio = (Button) findViewById(R.id.btHoraInicio);
        btHoraFim = (Button) findViewById(R.id.btHoraFim);
        recebeDados();
    }

    private void setListeners() {
        btData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraCalendario();
            }
        });
        btHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraRelogioInicio();
            }
        });
        btHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraRelogioFim();
            }
        });

        btAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNome = etSobre.getText().toString();
                String txtHoraInicio = btHoraInicio.getText().toString();
                String txtHoraFim = btHoraFim.getText().toString();
                String txtData = btData.getText().toString();

                if (etNome.equals("")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione uma descricao", Toast.LENGTH_SHORT).show();
                } else if (txtData.equals("Selecione a data")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione uma data", Toast.LENGTH_SHORT).show();
                } else if (txtHoraInicio.equals("Selecione a hora de inicio")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione um horário de inicio", Toast.LENGTH_SHORT).show();
                } else if (txtHoraFim.equals("Selecione a hora de fim")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione um horário de término", Toast.LENGTH_SHORT).show();
                }else {
                    if(1==2){// se já existir uma reserva, mostrar erro
                         }
                    else {
                        inserirBanco();
                        fetchDatabaseToArrayList();
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showListDialog(String.valueOf(position));
            }
        });
    }

    private void inserirBanco() {
        ReservaModel reservaModel = new ReservaModel();
        reservaModel.setDescricaoReserva(etNome);
        reservaModel.setDataReserva(etData);
        reservaModel.setHoraInicioReserva(etHoraInicio);
        reservaModel.setHoraFimReserva(etHoraFim);
        reservaModel.setIdSalaReserva(salaSelecioanda.getIdSala());
        reservaModel.setIdColaboradorReserva(colaboradorLogado.getIdColaborador());
        reservaModel.setNomeColaborador(colaboradorLogado.getNomeColaborador());

      //  reservaModel.setColaboradorReserva(colaboradorLogado);
      //  reservaModel.setSalaReserva(salaSelecioanda);


        Gson gson  = new Gson();

        try {
            String reservaCoded = new String(Base64.encodeToString(gson.toJson(reservaModel).getBytes("UTF-8"), Base64.NO_WRAP));
            enviarReservasServer(reservaCoded);
            Log.i("teste coded", reservaCoded);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }




    private void fetchDatabaseToArrayList() {
        listaReservas.clear();
        verificaReserva(salaSelecioanda.getIdSala(), colaboradorLogado.getIdColaborador());
        reservaAdapter = new ReservaAdapter(ActivityAgendarDataSala.this, R.layout.item_lista_reserva, listaReservas);
        listView.setAdapter(reservaAdapter);
    }


    private void mostraCalendario() {
        Calendar calendar = Calendar.getInstance();
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAgendarDataSala.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                mes=mes+1;
                btData.setText(""+dia+"-"+mes+"-"+ano);
                etData=""+dia+"-"+mes+"-"+ano;
            }
        },ano,mes,dia);
        datePickerDialog.show();
    }

    private void mostraRelogioInicio() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAgendarDataSala.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                btHoraInicio.setText(""+hora+":"+minuto);
                etHoraInicio =""+hora+":"+minuto;
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }

    private void mostraRelogioFim() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAgendarDataSala.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                btHoraFim.setText(""+hora+":"+minuto);
                etHoraFim = ""+hora+":"+minuto;
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }

    private void showListDialog(final String pos) {

        String[] arr = {"Deletar","Editar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAgendarDataSala.this);
        builder.setTitle("Opções");
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                if(position==0) {
                    confirmaApagar(pos);
                }
                else {
                    ReservaModel reservaModel = listaReservas.get(Integer.parseInt(pos));
                    Intent i = new Intent(ActivityAgendarDataSala.this, ActivityEditarDataAgendada.class);
                    i.putExtra("idReserva", reservaModel.getIdReserva());
                    flagEditAlarm=true;
                    startActivity(i);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void confirmaApagar(final String position) {
        AlertDialog.Builder builder= new AlertDialog.Builder(ActivityAgendarDataSala.this);
        builder.setTitle("Confirmação");
        builder.setMessage("Deseja cancelar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReservaModel reservaModel = listaReservas.get(Integer.parseInt(position));
                deletarReserva(reservaModel.getIdReserva());

            }
        });
        builder.setNeutralButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(flagEditAlarm==true) {
            fetchDatabaseToArrayList();
            flagEditAlarm=false;
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

    public void verificaReserva(final String idSala, final String idColaborador){
        String url = "http://172.30.248.130:8080/ReservaDeSala/rest/reserva/byIdColaboradorSala";
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

                            }
                        } catch (Exception e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("authorization", "secret");
                params.put("idSala", idSala);
                params.put("idColaborador", idColaborador);
                return params;
            }
        };
        mQueue.add(request);
    }

    public void deletarReserva(final String idReserva){

        StringRequest request = new StringRequest(Request.Method.POST, "http://172.30.248.130:8080/ReservaDeSala/rest/reserva/deleteById",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authorization", "secret");
                params.put("idReserva", idReserva);
                return params;
            }
        };
        mQueue.add(request);
    }

    public void enviarReservasServer(final String reservaCoded){
        String url = "http://172.30.248.130/rest/reservasala/reserva/cadastrar";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error.Response", error.toString());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("authorization","secret");
                params.put("novaReserva", reservaCoded);
                Log.i("teste coded", reservaCoded);

                return params;
            }
        };
        mQueue.add(postRequest);
    }
}