package com.alura.wiseroom.ui.colaborador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.ui.qrcode.ActivityReservarSala;
import com.alura.wiseroom.ui.qrcode.ActivityVerificarSala;
import com.alura.wiseroom.ui.sala.ActivityListarSalas;

public class ActivityPerfil extends AppCompatActivity {
    long millis = 0L;
    private Button btDisponibilidadeSala;
    private Button btReservarSala;
    private Button btListarSalas;
    private ColaboradorModel colaboradorLogado;
    private TextView textLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        recebeDados();
        criarBotaoPlusAcoes();
        Log.i("TESTE ESCOLHA COL ", colaboradorLogado.getNomeColaborador());
    }

    private void criarBotaoPlusAcoes() {
        btDisponibilidadeSala = findViewById(R.id.btVerificaQR);
        btReservarSala = findViewById(R.id.btReservaQR);
        btListarSalas = findViewById(R.id.btAcessaSala);


        btReservarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPerfil.this, ActivityReservarSala.class);
                intent.putExtra("colaboradorLogado", colaboradorLogado);
                startActivity(intent);
                finish();

            }
        });

        btListarSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPerfil.this, ActivityListarSalas.class);
                intent.putExtra("colaboradorLogado", colaboradorLogado);
                startActivity(intent);
                finish();
            }
        });

        btDisponibilidadeSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPerfil.this, ActivityVerificarSala.class);
                intent.putExtra("colaboradorLogado", colaboradorLogado);
                startActivity(intent);
                finish();

            }
        });

        textLogout = findViewById(R.id.textViewLogOut);

        textLogout.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intentLogOut = new Intent(ActivityPerfil.this, ActivityLogin.class);
                                              startActivity(intentLogOut);
                                              finish();
                                          }
                                      }
        );
    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if (intentMain.hasExtra("colaboradorLogado")) {

            ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
            colaboradorLogado = col;

        }
    }

    @Override
    public void onBackPressed() {
        if (millis == 0) {
            millis = System.currentTimeMillis();
        } else {
            long end = System.currentTimeMillis() - millis;
            if (end < 2000) {
                moveTaskToBack(true);
            } else {
                millis = System.currentTimeMillis();
            }
        }
    }

}
