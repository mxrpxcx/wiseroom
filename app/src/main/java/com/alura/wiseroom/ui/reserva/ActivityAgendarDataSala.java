

package com.alura.wiseroom.ui.reserva;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.ReservaAdapter;
import com.alura.wiseroom.constants.Constants;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.Event;
import com.alura.wiseroom.model.ReservaModel;
import com.alura.wiseroom.model.SalaModel;
import com.alura.wiseroom.network.HttpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    SalaModel salaSelecioanda;
    ColaboradorModel colaboradorLogado;
    RequestQueue mQueue;
    View v;

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

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }


    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
                } else if (txtData.equalsIgnoreCase("Inserir data")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione uma data", Toast.LENGTH_SHORT).show();
                } else if (txtHoraInicio.equalsIgnoreCase("hora inicio")) {
                    Toast.makeText(ActivityAgendarDataSala.this, "Adicione um horário de inicio", Toast.LENGTH_SHORT).show();
                } else if (txtHoraFim.equalsIgnoreCase("hora fim")) {
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
                showListDialog(position);
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
                if(hora<10 || minuto < 10){

                    if(hora<10) {
                        btHoraInicio.setText(""+"0"+hora+":"+minuto);
                        etHoraInicio = ""+"0"+hora+":"+minuto;
                    }

                    if ( hora <10 && minuto < 10) {
                        btHoraInicio.setText(""+"0"+hora+":"+"0"+minuto);
                        etHoraInicio =""+"0"+hora+":"+"0"+minuto;
                    }

                    if ( minuto < 10 && hora >= 10) {
                        btHoraInicio.setText(""+""+hora+":"+"0"+minuto);
                        etHoraInicio =""+""+hora+":"+"0"+minuto;
                    }

                }

                else {
                btHoraInicio.setText(""+hora+":"+minuto);
                etHoraInicio =""+hora+":"+minuto;
                }

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
                if(hora<10 || minuto < 10){

                    if(hora<10) {
                        btHoraFim.setText(""+"0"+hora+":"+minuto);
                        etHoraFim = ""+"0"+hora+":"+minuto;
                    }

                    if ( hora <10 && minuto < 10) {
                        btHoraFim.setText(""+"0"+hora+":"+"0"+minuto);
                        etHoraFim =""+"0"+hora+":"+"0"+minuto;
                    }

                    if ( minuto < 10 && hora >= 10) {
                        btHoraFim.setText(""+""+hora+":"+"0"+minuto);
                        etHoraFim =""+""+hora+":"+"0"+minuto;
                    }

                }

                else {
                    btHoraFim.setText(""+hora+":"+minuto);
                    etHoraFim =""+hora+":"+minuto;
                }
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }

    private void showListDialog(final int pos) {

        String[] arr = {"Deletar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAgendarDataSala.this);
        builder.setTitle("Opções");
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                if(position==0) {
                    confirmaApagar(pos);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void confirmaApagar(final int position) {
        AlertDialog.Builder builder= new AlertDialog.Builder(ActivityAgendarDataSala.this);
        builder.setTitle("Confirmação");
        builder.setMessage("Deseja cancelar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReservaModel reservaModel = listaReservas.get(position);
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

        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("idSala", idSala);
            params.put("idColaborador", idColaborador);
            String url = Constants.url+"/reserva/byIdColaboradorSala";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "GET", "ListaReserva").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletarReserva(final String idReserva){
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("idReserva", idReserva);
            String url = Constants.url+"/reserva/deleteById";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "POST", "DeletaReserva").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarReservasServer(final String reservaCoded){
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("novaReserva", reservaCoded);
            String url = Constants.url+"/reserva/cadastrar";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "POST", "EnviaReserva").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void customEventReceived(Event event) {
        if (event.getEventName().equals("EnviaReserva" + Constants.eventSuccessLabel)) {
            Log.i("teste", event.getEventMsg().toString());
            fetchDatabaseToArrayList();

        } else if (event.getEventName().equals("EnviaReserva" + Constants.eventErrorLabel)) {
            Log.i("teste erro cadastro", event.getEventMsg());
            Snackbar snackbar = Snackbar.make(listView, "Erro ao cadastrar reserva", Snackbar.LENGTH_LONG);
            snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            snackbar.show();
            fetchDatabaseToArrayList();
        }

        if (event.getEventName().equals("DeletaReserva" + Constants.eventSuccessLabel)) {
            Log.i("teste", event.getEventMsg().toString());
            fetchDatabaseToArrayList();

        } else if (event.getEventName().equals("DeletaReserva" + Constants.eventErrorLabel)) {
            Snackbar snackbar = Snackbar.make(listView, "Erro ao deletar reserva", Snackbar.LENGTH_LONG);
            snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            snackbar.show();
            fetchDatabaseToArrayList();

        }

        if (event.getEventName().equals("ListaReserva" + Constants.eventSuccessLabel)) {

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


            }

                } else if (event.getEventName().equals("ListaReserva" + Constants.eventErrorLabel)) {
                Log.i("teste erro cadastro", event.getEventMsg());
                Snackbar snackbar = Snackbar.make(listView, "Erro ao listar reservas ", Snackbar.LENGTH_LONG);
                    snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    snackbar.show();

        }

        }
    }