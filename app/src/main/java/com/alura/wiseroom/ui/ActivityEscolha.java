package com.alura.wiseroom.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alura.wiseroom.R;

public class ActivityEscolha extends AppCompatActivity {
    private Button btAdicionarNovaSala;
    private Button btDisponibilidadeSala;
    private Button btReservarSala;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        btAdicionarNovaSala = findViewById(R.id.btAdiciona);
        btDisponibilidadeSala = findViewById(R.id.btVerifica);
        btReservarSala = findViewById(R.id.btReserva);

        btReservarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEscolha.this, ActivityEscolherSala.class));
            }
        });

        btDisponibilidadeSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEscolha.this, ActivityEscolherSala.class));
            }
        });

        btAdicionarNovaSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEscolha.this, ActivityAdicionarSala.class));
            }
        });

    }
}
