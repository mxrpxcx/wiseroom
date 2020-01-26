package com.alura.wiseroom.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityReservarSala extends AppCompatActivity {
    final Activity activity= this;
    String idColaborador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_sala);
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Escolha a sala");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
                recebeDados();

        Log.i("Teste id col", idColaborador);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null){
            if (intentResult.getContents() !=  null){

                Intent intent = new Intent(ActivityReservarSala.this, ActivityAgendarDataSala.class);
                intent.putExtra("idSala", intentResult.getContents());
                intent.putExtra("idColaborador", idColaborador);
                startActivity(intent);
                finish();
                Log.i("Teste id sala", intentResult.getContents());
            }else{

                Intent intent = new Intent(ActivityReservarSala.this, ActivityEscolha.class);
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if(intentMain.hasExtra("idColaborador")){

            String idRecebido = intentMain.getStringExtra("idColaborador");
            idColaborador = idRecebido;

        }
    }

}
