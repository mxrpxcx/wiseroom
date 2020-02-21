package com.alura.wiseroom.ui.colaborador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.constants.Constants;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.Event;
import com.alura.wiseroom.network.HttpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;


public class ActivityLogin extends AppCompatActivity {
    RequestQueue mQueue;
    private Button btLogin;
    private TextView etCadastro;
    private EditText tvEmail;
    private EditText tvSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar ab = getSupportActionBar();
        mQueue = Volley.newRequestQueue(this);

        ab.hide();
        setarValores();

        final ColaboradorModel colaborador = new ColaboradorModel();
        setListenersDoBotao();
        setListenerColaborador(colaborador);
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void logaColaborador(ColaboradorModel colaborador) {
        colaborador.setEmailColaborador(tvEmail.getText().toString());
        colaborador.setSenhaColaborador(tvSenha.getText().toString());
        verificaRegistro(colaborador.getEmailColaborador(), colaborador.getSenhaColaborador());

    }

    private void setListenerColaborador(final ColaboradorModel colaborador) {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logaColaborador(colaborador);
            }
        });
    }

    private void setListenersDoBotao() {
        etCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, ActivityCadastroColaborador.class));
            }
        });
    }

    private void setarValores() {
        btLogin = findViewById(R.id.btLogin);
        etCadastro = findViewById(R.id.textViewCadastroVddeiro);
        tvEmail = findViewById(R.id.editEmail);
        tvSenha = findViewById(R.id.editSenha);
        tvEmail.setText("b@b.b");
        tvSenha.setText("b");
    }


    public void verificaRegistro(final String email, final String senha) {

        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("emailColaborador", email);
            params.put("senhaColaborador", senha);
            String url = Constants.url + "/colaborador/login";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "GET", "Login").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void customEventReceived(Event event) {
        if (event.getEventName().equals("Login" + Constants.eventSuccessLabel)) {

            Gson gson = new Gson();

            ColaboradorModel colaboradorJson = gson.fromJson(event.getEventMsg(), ColaboradorModel.class);
            ColaboradorModel colaboradorRecebidoJson = new ColaboradorModel();

            colaboradorRecebidoJson.setIdColaborador(colaboradorJson.getIdColaborador());
            colaboradorRecebidoJson.setNomeColaborador(colaboradorJson.getNomeColaborador());
            colaboradorRecebidoJson.setEmailColaborador(colaboradorJson.getEmailColaborador());
            colaboradorRecebidoJson.setAdministrador(colaboradorJson.isAdministrador());
            colaboradorRecebidoJson.setSenhaColaborador(colaboradorJson.getSenhaColaborador());

            Log.i("TESTE RODANDO ?? ", "model objeto " + colaboradorRecebidoJson.toString());

            Intent intent = new Intent(ActivityLogin.this, ActivityPerfil.class);
            intent.putExtra("colaboradorLogado", colaboradorRecebidoJson);
            startActivity(intent);

        } else if (event.getEventName().equals("Login" + Constants.eventErrorLabel)) {
            Snackbar snackbar = Snackbar.make(btLogin, "Erro ao realizar login", Snackbar.LENGTH_LONG);
            snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            snackbar.show();
        }
    }


}
