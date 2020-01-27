package com.alura.wiseroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.model.ColaboradorModel;

public class ActivityEscolha extends AppCompatActivity {
    private Button btDisponibilidadeSala;
    private Button btReservarSala;
    private String dominioAtual;
    private ColaboradorModel colaboradorLogado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        criarBotaoPlusAcoes();
        recebeDados();
        Log.i("TESTE ESCOLHA COL ", colaboradorLogado.toString());


    }

    private void criarBotaoPlusAcoes() {
        btDisponibilidadeSala = findViewById(R.id.btVerifica);
        btReservarSala = findViewById(R.id.btReserva);



        btReservarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEscolha.this, ActivityReservarSala.class);
                intent.putExtra("colaboradorLogado", colaboradorLogado);
                startActivity(intent);
                finish();
            }
        });

        btDisponibilidadeSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEscolha.this, ActivityVerificarSala.class);
                intent.putExtra("colaboradorLogado", colaboradorLogado);
                startActivity(intent);
                finish();
            }
        });


    }

    private void recebeDados() {
            Intent intentMain = getIntent();
            if(intentMain.hasExtra("emailColaborador")) {
                String emailColaborador = intentMain.getStringExtra("emailColaborador");
                int indexArroba = emailColaborador.indexOf("@");
                int indexPonto = emailColaborador.indexOf(".");
                dominioAtual = emailColaborador.substring(indexArroba + 1, indexPonto);
            }
            if(intentMain.hasExtra("colaboradorLogado")){

                ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
                colaboradorLogado = col;

            }
    }
}
