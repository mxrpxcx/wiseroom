package com.alura.wiseroom.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityVerificarSala extends AppCompatActivity {
    final Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_sala);
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Escolha a sala");
        intentIntegrator.setCameraId(0);
        intentIntegrator.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null){
            if (intentResult.getContents() !=  null){
                // Selecionado
                Intent intent = new Intent(ActivityVerificarSala.this, ActivityDatasReservadas.class);
                intent.putExtra("idSala", intentResult.getContents().toString());
                startActivity(intent);

            }else{
                Intent intent = new Intent(ActivityVerificarSala.this, ActivityEscolha.class);
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

}
