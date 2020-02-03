

package com.alura.wiseroom.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Calendar;

public class ActivityAgendarDataSala extends AppCompatActivity {

    Button btData, btHora;
    EditText etSobre;
    ImageButton btAdiciona;
    ListView listView;
    String etHora, etData, etNome;
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
        btHora = (Button) findViewById(R.id.btHoraInicio);
        recebeDados();
    }

    private void setListeners() {
        btData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraCalendario();
            }
        });
        btHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraRelogio();
            }
        });

        btAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNome = etSobre.getText().toString();
                String txtHora = btHora.getText().toString();
                String txtData = btData.getText().toString();
                if (etNome.equals("")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione um nome", Toast.LENGTH_SHORT).show();
                } else if (txtData.equals("Selecione a data")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione uma data", Toast.LENGTH_SHORT).show();
                } else if (txtHora.equals("Selecione a hora")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione um horário", Toast.LENGTH_SHORT).show();
                } else {
                    String id = inserirBanco();
                    definirData(id);
                    fetchDatabaseToArrayList();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showListDialog(position);
            }
        });
    }

    private void inserirBanco() {



        ContentValues cv = new ContentValues();
        cv.put(wise.COLUNA_NOME_DATA, etNome);
        cv.put(wise.COLUNA_DATA_MARCADA, etData);
        cv.put(wise.COLUNA_HORARIO_MARCADO, etHora);
        cv.put(wise.COLUNA_ID_SALA_MARCADA, salaSelecioanda.getId());
        cv.put(wise.COLUNA_ID_COLABORADOR_QUE_MARCOU, colaboradorLogado.getId());


        long id = wise.inserirData(db, cv);


        ContentValues cvReserva = new ContentValues();
        cvReserva.put(wise.COLUNA_ID_COLABORADOR_RESERVA, colaboradorLogado.getId());
        cvReserva.put(wise.COLUNA_ID_SALA_RESERVADA, salaSelecioanda.getId());
        cvReserva.put(wise.COLUNA_ID_DATA_RESERVADA, id);
        wise.inserirReserva(db, cvReserva);


        db.close();
    }

    private void definirData(String id) {
        WiseRoomDB wise = new WiseRoomDB(this);
        SQLiteDatabase db = wise.getReadableDatabase();
        String selection = WiseRoomDB.COLUNA_ID_DATA + " = '" + id + "'";
        Cursor cursor = wise.selecionarData(db, selection);
        String etData[] = new String[3];
        String etHora[] = new String[2];
        String data = null, hora = null, nome = null;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                data = cursor.getString(2);
                hora = cursor.getString(3);
                nome = cursor.getString(1);
            }
        }

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i = new Intent();
        i.putExtra(wise.COLUNA_ID_DATA, id);
        i.putExtra(wise.COLUNA_NOME_DATA, nome);
        i.putExtra(wise.COLUNA_DATA_MARCADA, data);
        i.putExtra(wise.COLUNA_HORARIO_MARCADO, hora);
        i.putExtra(wise.COLUNA_ID_SALA_MARCADA, salaSelecioanda.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Integer.parseInt(id), i, 0);

        if (flagDeleteAlarm) {
            alarmManager.cancel(pendingIntent);
            flagDeleteAlarm = false;
        }

       else {
            int k=0;
            for(String s: data.split("-")) {
                etData[k++]=s;
            }
            k=0;

            for(String s: hora.split(":")) {
                etHora[k++]=s;
            }

        Calendar calendario = Calendar.getInstance();
        calendario.set(Integer.parseInt(etData[2]), Integer.parseInt(etData[1]) - 1,
                Integer.parseInt(etData[0]),
                Integer.parseInt(etHora[0]),
                Integer.parseInt(etHora[1]));
        long mili = calendario.getTimeInMillis();
        calendario.setTimeInMillis(mili);
        Calendar calendarCurrent = Calendar.getInstance();
        long miliCurrent = calendarCurrent.getTimeInMillis();
        calendarCurrent.setTimeInMillis(miliCurrent);
        long diff = mili - miliCurrent;
        long currentTime = System.currentTimeMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime + diff, pendingIntent);

        Toast.makeText(this, "Reservado", Toast.LENGTH_SHORT).show();
    }

}


    private void fetchDatabaseToArrayList() {
        listaReservas.clear();
        listaIds.clear();
        verificaReserva(salaSelecioanda.getId(), colaboradorLogado.getId());
        reservaAdapter = new ReservaAdapter(ActivityAgendarDataSala.this, R.layout.item_lista_data, listaReservas);
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

    private void mostraRelogio() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAgendarDataSala.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                btHora.setText(""+hora+":"+minuto);
                etHora=""+hora+":"+minuto;
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
                    Intent i = new Intent(ActivityAgendarDataSala.this, ActivityEditarDataAgendada.class);
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
            String id = ActivityEditarDataAgendada.idUpdate;
            definirData(id);
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
                                    reservaRecebidaJson.getColaboradorReserva().setId((reservaJson.getString("idColaborador")));
                                    reservaRecebidaJson.getSalaReserva().setId((reservaJson.getString("idSala")));


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
        String url = "http://172.30.248.130:3000/reserva?id="+idReserva;
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resposta) {
                        listaReservas.clear();
                        listaIds.clear();
                        fetchDatabaseToArrayList();
                        reservaAdapter.notifyDataSetChanged();
                        flagDeleteAlarm = true;
                        definirData(idReserva);
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


