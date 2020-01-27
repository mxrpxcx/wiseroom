package com.alura.wiseroom.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.SalaModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityReservarSala extends AppCompatActivity {
    final Activity activity = this;
    ColaboradorModel colaboradorLogado;
    SQLiteDatabase db;
    Cursor cursor;
    SQLiteOpenHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_sala);
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Escolha a sala");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
        dbHelper = new WiseRoomDB(this);
        db = dbHelper.getWritableDatabase();

        recebeDados();

        Log.i("Teste id col", colaboradorLogado.toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null){
            if (intentResult.getContents() !=  null){


                Log.i("teste qr ",  intentResult.getContents());

                cursor = db.rawQuery("SELECT * FROM " + WiseRoomDB.TABELA_NOME_SALA + " WHERE " +
                        WiseRoomDB.COLUNA_ID_SALA + "=?", new String[]{intentResult.getContents()});
                if (cursor != null) {
                    if (cursor.getCount() > 0) {

                        cursor.moveToFirst();

                        String intentId = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_ID_SALA));
                        String intentNome = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_NOME_SALA));
                        int intentCapacidade = cursor.getInt(cursor.getColumnIndex(WiseRoomDB.COLUNA_CAPACIDADE_SALA));
                        String intentDescricao = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_DESCRICAO_SALA));

                        SalaModel salaSelecionada = new SalaModel();
                        salaSelecionada.setId(intentId);
                        salaSelecionada.setNome(intentNome);
                        salaSelecionada.setCapacidade(intentCapacidade);
                        salaSelecionada.setDescricaoSala(intentDescricao);


                        Intent intent = new Intent(ActivityReservarSala.this, ActivityAgendarDataSala.class);
                        intent.putExtra("salaSelecionada", salaSelecionada);
                        intent.putExtra("colaboradorLogado", colaboradorLogado);
                        startActivity(intent);
                    }
                }

            }

            else{

                Intent intent = new Intent(ActivityReservarSala.this, ActivityEscolha.class);
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if(intentMain.hasExtra("colaboradorLogado")){

            ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
            colaboradorLogado = col;

        }
    }

}
