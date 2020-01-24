package com.alura.wiseroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;

public class ActivityEscolha extends AppCompatActivity {
    private Button btAdicionarNovaSala;
    private Button btDisponibilidadeSala;
    private Button btReservarSala;
    private String dominioAtual;
    private String idColaborador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        criarBotaoPlusAcoes();
        recebeDados();


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
                startActivity(new Intent(ActivityEscolha.this, ActivityVerificarSala.class));
            }
        });

        btAdicionarNovaSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEscolha.this, ActivityListarSalaReservada.class);
                intent.putExtra("idColaborador", idColaborador);
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
        if(intentMain.hasExtra("idColaborador")){

            String idRecebido = intentMain.getStringExtra("idColaborador");
            idColaborador = idRecebido;

        }
    }
}
