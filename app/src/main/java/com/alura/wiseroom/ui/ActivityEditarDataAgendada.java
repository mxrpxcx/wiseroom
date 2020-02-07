package com.alura.wiseroom.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActivityEditarDataAgendada extends AppCompatActivity {
    Button btData, btHoraInicio, btHoraFim;
    EditText etSobre;
    ImageButton btAdiciona;
    String etHoraInicio,
            etHoraFim,
            etData,
            etNome;

    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_data_agendada);
        mQueue = Volley.newRequestQueue(this);
        init();
        Intent i = getIntent();
        String id = i.getStringExtra("idReserva");


        recebeReserva(id);

        etSobre.setText(etNome);
        btData.setText(etData);
        btHoraInicio.setText(etHoraInicio);
        btHoraFim.setText(etHoraFim);
        setListeners(id);
    }




    private void init() {
        btAdiciona = (ImageButton) findViewById(R.id.btAdicionaU);
        etSobre = (EditText) findViewById(R.id.etNomeU);
        btData = (Button) findViewById(R.id.btDataU);
        btHoraInicio = (Button) findViewById(R.id.btHoraInicioU);
        btHoraFim = (Button) findViewById(R.id.btHoraFimU);
    }


    private void setListeners(final String id) {
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
                String txtInicio = btHoraInicio.getText().toString();
                String txtFim = btHoraFim.getText().toString();
                String textDate = btData.getText().toString();
                if(etNome.equals("")) {
                    Toast.makeText(ActivityEditarDataAgendada.this, "Adicionar nome", Toast.LENGTH_SHORT).show();
                }
                else if(textDate.equals("Selecione a data") || textDate.equals("")) {
                    Toast.makeText(ActivityEditarDataAgendada.this, "Adicionar data", Toast.LENGTH_SHORT).show();
                }
                else if(txtInicio.equals("Selecione a hora de inicio") || txtInicio.equals("")) {
                    Toast.makeText(ActivityEditarDataAgendada.this, "Adicionar hora inicio", Toast.LENGTH_SHORT).show();
                }
                else if(txtFim.equals("Selecione a hora de fim") || txtFim.equals("")) {
                    Toast.makeText(ActivityEditarDataAgendada.this, "Adicionar hora fim", Toast.LENGTH_SHORT).show();
                }
                else {
                 atualizaReserva(id);
                }
            }
        });
    }


    private void mostraCalendario() {
        Calendar calendar = Calendar.getInstance();
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityEditarDataAgendada.this, new DatePickerDialog.OnDateSetListener() {
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityEditarDataAgendada.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                btHoraInicio.setText(""+hora+":"+minuto);
                etHoraInicio=""+hora+":"+minuto;
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }

    private void mostraRelogioFim() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityEditarDataAgendada.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                btHoraFim.setText(""+hora+":"+minuto);
                etHoraFim=""+hora+":"+minuto;
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }

    private void recebeReserva(final String idReserva) {
        String url = "http://172.30.248.130:3000/listaReserva.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resposta) {
                        if (resposta.length() > 0) {
                            try {
                                for (int i = 0; i < resposta.length(); i++) {

                                    JSONObject reservaJson = resposta.getJSONObject(i);

                                    etSobre.setText(reservaJson.getString("descricao"));
                                    btData.setText(reservaJson.getString("dataReservada"));
                                    btHoraInicio.setText(reservaJson.getString("horaInicio"));
                                    btHoraFim.setText(reservaJson.getString("horaFim"));


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
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idReserva", idReserva);
                return params;
            }
        };
        mQueue.add(request);
    }

    private void atualizaReserva(final String idReserva) {

        StringRequest request = new StringRequest(Request.Method.POST, "172.30.248.130/atualizaReserva.php",
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
                p.put("idReserva", idReserva);
                p.put("descricaoReserva", etNome);
                p.put("dataReserva", btData.toString());
                p.put("horaInicioReserva", btHoraInicio.toString());
                p.put("horaFimReserva", btHoraFim.toString());

                return p;
            }
        };
        mQueue.add(request);
    }

}
