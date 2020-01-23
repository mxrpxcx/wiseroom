package com.alura.wiseroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;

public class ActivityEscolha extends AppCompatActivity {
    private Button btAdicionarNovaSala;
    private Button btDisponibilidadeSala;
    private Button btReservarSala;
    private String dominioAtual;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);
        criarBotaoPlusAcoes();
        recebeDominio();

    }

    private void criarBotaoPlusAcoes() {
        btAdicionarNovaSala = findViewById(R.id.btAdiciona);
        btDisponibilidadeSala = findViewById(R.id.btVerifica);
        btReservarSala = findViewById(R.id.btReserva);

        btReservarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEscolha.this, ActivityReservarSala.class));
            }
        });

        btDisponibilidadeSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEscolha.this, ActivityReservarSala.class));
            }
        });

        btAdicionarNovaSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEscolha.this, ActivityAdicionarSala.class));
            }
        });
    }

    private void recebeDominio() {
        Intent intentMain = getIntent();
        String emailColaborador = intentMain.getStringExtra("emailColaborador");
        int indexArroba = emailColaborador.indexOf("@");
        int indexPonto = emailColaborador.indexOf(".");
        dominioAtual = emailColaborador.substring(indexArroba + 1, indexPonto);
    }
}
