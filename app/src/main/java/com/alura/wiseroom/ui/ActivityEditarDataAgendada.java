package com.alura.wiseroom.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Calendar;

public class ActivityEditarDataAgendada extends AppCompatActivity {
    Button btData, btHoraInicio, btHoraFim;
    EditText etSobre;
    ImageButton btAdiciona;
    String etHoraInicio,
            etHoraFim,
            etData,
            etNome;

    static String idUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_data_agendada);
        init();
        Intent i = getIntent();
        int id = i.getIntExtra("idReserva",0);


    //    Cursor cursor = wise.selecionarData(db, null);
    //    if (cursor != null) {
     //       while (cursor.moveToNext()) {
      //          int idData = cursor.getInt(0);
        //        if (idData == id) {
       //             etNome = cursor.getString(1);
        //            etData = cursor.getString(2);
        //            etHoraInicio = cursor.getString(3);
          //          break;
        //        }
       //     }
        //    cursor.close();
         //   db.close();
        //   }

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


    private void setListeners(final int id) {
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
                //    att id
                }
            }
        });
    }

  /*  private void atualizaBanco(int id) {

        WiseRoomDB wise = new WiseRoomDB(ActivityEditarDataAgendada.this);
        SQLiteDatabase db = wise.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(wise.COLUNA_NOME_DATA, etNome);
        cv.put(wise.COLUNA_DATA_MARCADA, etData);
        cv.put(wise.COLUNA_HORARIO_MARCADO, etHora);

        String whereClause = wise.COLUNA_ID_DATA +" = '"+id+"'";
        int l = WiseRoomDB.atualizarData(db, whereClause,cv);
        if (l>0) {
            Toast.makeText(ActivityEditarDataAgendada.this, "Remarcado", Toast.LENGTH_SHORT).show();
            idUpdate=id;
            finish();
        }
        else {
            Toast.makeText(ActivityEditarDataAgendada.this, "Tente novamente", Toast.LENGTH_SHORT).show();
        }
        db.close();
    } */

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

}
