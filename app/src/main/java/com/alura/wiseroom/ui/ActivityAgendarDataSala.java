

package com.alura.wiseroom.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActivityAgendarDataSala extends AppCompatActivity {

    Button btData, btHoraInicio, btHoraFim;
    EditText etSobre;
    ImageButton btAdiciona;
    ListView listView;
    String etHoraInicio, etHoraFim, etData, etNome;
    ReservaAdapter reservaAdapter;
    ArrayList<ReservaModel> listaReservas = new ArrayList<>();
    ArrayList<String> listaIds = new ArrayList<>();
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
                    inserirBanco();
                    fetchDatabaseToArrayList();
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
        reservaModel.setDescricaoData(etNome);
        reservaModel.setDataMarcada(etData);
        reservaModel.setHoraInicio(etHoraInicio);
        reservaModel.setHoraFim(etHoraFim);
        reservaModel.setSalaReserva(salaSelecioanda);
        reservaModel.setColaboradorReserva(colaboradorLogado);

        enviarReservasServer(reservaModel);
    }




    private void fetchDatabaseToArrayList() {
        listaReservas.clear();
        listaIds.clear();
        verificaReserva(salaSelecioanda.getId(), colaboradorLogado.getId());
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
                btData.setText(""+dia+"/"+mes+"/"+ano);
                etData=""+dia+"/"+mes+"/"+ano;
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
                    String id = listaIds.get(Integer.parseInt(pos));
                    Intent i = new Intent(ActivityAgendarDataSala.this, null);
                    i.putExtra("ID", id);
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
                String id = listaIds.get(Integer.parseInt(position));
                deletarReserva(id);

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

    public void verificaReserva(String idSala, String idColaborador){
        String url = "http://172.30.248.130:3000/reserva?idSala="+idSala+"&idColaborador="+idColaborador;
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
                                    listaIds.add(reservaRecebidaJson.getId());

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

    public void deletarReserva(final String idReserva){
        final String _id = idReserva;
        StringRequest request = new StringRequest(Request.Method.POST, ".../delete.php",
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
            protected Map<String, String> getParams() {
                Map<String, String> p = new HashMap<>();
                p.put("id", String.valueOf(_id));
                return p;
            }
        };
        mQueue.add(request);
    }

    public void enviarReservasServer(final ReservaModel reservaModel){
        String url = "http://172.30.248.130:3000/reserva";

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

                        Log.d("Error.Response", "bugou");
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("descricao",reservaModel.getDescricaoData());
                params.put("dataReservada", reservaModel.getDataMarcada());
                params.put("horaInicio",reservaModel.getHoraInicio());
                params.put("horaFim", reservaModel.getHoraFim());
                params.put("idSala", reservaModel.getSalaReserva().getId());
                params.put("idColaborador", reservaModel.getColaboradorReserva().getId());
                return params;
            }
        };
        mQueue.add(postRequest);
    }
}