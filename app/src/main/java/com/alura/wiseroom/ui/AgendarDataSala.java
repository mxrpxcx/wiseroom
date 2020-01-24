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
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.adapter.DataAdapter;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.DataModel;

import java.util.ArrayList;
import java.util.Calendar;

public class AgendarDataSala extends AppCompatActivity {


    Button btParar;
    TextView tvVoltar;
    Button btData, btHora;
    EditText etSobre;
    ImageButton btAdiciona;
    ListView listView;
    String etHora,etData,etNome;
    DataAdapter dataAdapter;
    ArrayList<DataModel> listaDatas = new ArrayList<>();
    ArrayList<Integer> listaIds = new ArrayList<>();
    AlarmManager alarmManager;
    boolean flagDeleteAlarm = false;
    boolean flagEditAlarm = false;
    String idSala;
    String idColaborador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_data_sala);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        setListeners();
        fetchDatabaseToArrayList();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.listView);
        btAdiciona = (ImageButton) findViewById(R.id.btAdiciona);
        etSobre = (EditText) findViewById(R.id.etSobre);
        btData = (Button) findViewById(R.id.btData);
        btHora = (Button) findViewById(R.id.btHora);
        tvVoltar = (TextView) findViewById(R.id.tvVoltar);
        btParar.setVisibility(View.INVISIBLE);
        tvVoltar.setVisibility(View.INVISIBLE);
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
                if(etNome.equals("")) {
                    Toast.makeText(AgendarDataSala.this, "Adicionar nome", Toast.LENGTH_SHORT).show();
                }
                else if(txtData.equals("Selecione a data")) {
                    Toast.makeText(AgendarDataSala.this, "Adicionar data", Toast.LENGTH_SHORT).show();
                }
                else if(txtHora.equals("Selecione a hora")) {
                    Toast.makeText(AgendarDataSala.this, "Adicionar hora", Toast.LENGTH_SHORT).show();
                }
                else {
                    long id = inserirBanco();
                    definirReserva(id);
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

    private long inserirBanco() {

        WiseRoomDB wise = new WiseRoomDB(AgendarDataSala.this);
        SQLiteDatabase db = wise.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(wise.COLUNA_NOME_DATA, etNome);
        cv.put(wise.COLUNA_DATA_MARCADA, etData);
        cv.put(wise.COLUNA_HORARIO_MARCADO, etHora);
        cv.put(wise.COLUNA_ID_SALA_MARCADA, idSala);

        long id = wise.inserirData(db, cv);
        if (id>0) {
            Toast.makeText(AgendarDataSala.this, "Horario definido", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(AgendarDataSala.this, "Tente novamente", Toast.LENGTH_SHORT).show();
        }
        db.close();

        etSobre.setText("");
        btHora.setText("Selecione a hora");
        btData.setText("Selecione a data");
        return id;
    }

    private void definirReserva(long id) {
        WiseRoomDB wise = new WiseRoomDB(this);
        SQLiteDatabase db = wise.getReadableDatabase();
        String selection = WiseRoomDB.COLUNA_ID_DATA +" = '"+id+"'";
        Cursor cursor = wise.selecionarData(db,selection);
        String etData[] = new String[3];
        String etHora[] = new String[2];
        String data = null
                ,hora = null
                ,nome = null;

        if(cursor!=null) {
            while(cursor.moveToNext()) {
                data = cursor.getString(2);
                hora = cursor.getString(3);
                nome = cursor.getString(1);
            }
        }

        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i = new Intent();
        i.putExtra(wise.COLUNA_ID_DATA,id);
        i.putExtra(wise.COLUNA_NOME_DATA,nome);
        i.putExtra(wise.COLUNA_DATA_MARCADA,data);
        i.putExtra(wise.COLUNA_HORARIO_MARCADO,hora);
        i.putExtra(wise.COLUNA_ID_SALA_MARCADA, idSala);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,(int)id,i,0);

        if(flagDeleteAlarm) {
            alarmManager.cancel(pendingIntent);
            flagDeleteAlarm=false;
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
            calendario.set(Integer.parseInt(etData[2]),Integer.parseInt(etData[1])-1,
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

            Toast.makeText(this, "Reservado" , Toast.LENGTH_SHORT).show();
        }
    }


    private void fetchDatabaseToArrayList() {
        listaDatas.clear();
        listaIds.clear();
        WiseRoomDB wise = new WiseRoomDB(this);
        SQLiteDatabase db = wise.getReadableDatabase();
        int k=0;

        String selecao = WiseRoomDB.COLUNA_ID_SALA_MARCADA +" = ''";
        Cursor cursor = wise.selecionarData(db, selecao );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String data = cursor.getString(2);
                String hora = cursor.getString(3);
                DataModel dataModel = new DataModel();
                dataModel.setNomeData(nome);
                dataModel.setDataData(data);
                dataModel.setHoraData(hora);
                dataModel.setIdSalaxData(null);
                listaDatas.add(dataModel);
                listaIds.add(id);
            }
            cursor.close();
            db.close();

            dataAdapter = new DataAdapter(AgendarDataSala.this, R.layout.item_lista_data, listaDatas);
            listView.setAdapter(dataAdapter);
        }

    }


    private void mostraCalendario() {
        Calendar calendar = Calendar.getInstance();
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AgendarDataSala.this, new DatePickerDialog.OnDateSetListener() {
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(AgendarDataSala.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                btHora.setText(""+hora+":"+minuto);
                etHora=""+hora+":"+minuto;
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }

    private void showListDialog(final int pos) {

        String[] arr = {"Deletar","Editar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AgendarDataSala.this);
        builder.setTitle("Opções");
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                if(position==0) {
                    confirmaApagar(pos);
                }
                else {
                    int id = listaIds.get(pos);
                    Intent i = new Intent(AgendarDataSala.this, ActivityEditarDataAgendada.class);
                    i.putExtra("ID", id);
                    flagEditAlarm=true;
                    startActivity(i);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void confirmaApagar(final int position) {
        AlertDialog.Builder builder= new AlertDialog.Builder(AgendarDataSala.this);
        builder.setTitle("Confirmação");
        builder.setMessage("Deseja deletar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WiseRoomDB wise = new WiseRoomDB(AgendarDataSala.this);
                SQLiteDatabase db = wise.getWritableDatabase();
                int id = listaIds.get(position);
                String whereCluase = WiseRoomDB.COLUNA_ID_DATA +" = '"+id+"'";

                int flag = wise.deletarData(db, whereCluase);
                if (flag > 0) {
                    Toast.makeText(AgendarDataSala.this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                    listaDatas.clear();
                    listaIds.clear();
                    fetchDatabaseToArrayList();
                    dataAdapter.notifyDataSetChanged();
                    flagDeleteAlarm = true;
                    definirReserva(id);
                }
                else {
                    Toast.makeText(AgendarDataSala.this, "Erro ao cancelar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNeutralButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog deleteDialog=builder.create();
        deleteDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(flagEditAlarm==true) {
            fetchDatabaseToArrayList();
            long id = ActivityEditarDataAgendada.idUpdate;
            definirReserva(id);
            flagEditAlarm=false;
        }
    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if(intentMain.hasExtra("idColaborador")){

            String idRecebidoColaborador = intentMain.getStringExtra("idColaborador");
            idColaborador = idRecebidoColaborador;

        }

        if(intentMain.hasExtra("idSala")){

            String idRecebidoSala = intentMain.getStringExtra("idSala");
            idSala = idRecebidoSala;

        }
    }
}
