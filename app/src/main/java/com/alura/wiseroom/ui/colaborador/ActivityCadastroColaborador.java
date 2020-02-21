package com.alura.wiseroom.ui.colaborador;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.constants.Constants;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.Event;
import com.alura.wiseroom.model.OrganizacaoModel;
import com.alura.wiseroom.network.HttpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ActivityCadastroColaborador extends AppCompatActivity {

    RequestQueue mQueue;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_colaborador);
        mQueue = Volley.newRequestQueue(this);
        ActionBar ab = getSupportActionBar();
        ab.hide();


        final EditText txtNome = findViewById(R.id.editNomeCadastro);
        final EditText txtEmail = findViewById(R.id.editEmailCadastro);
        final EditText txtSenha = findViewById(R.id.editSenhaCadastro);
        btnCadastrar = findViewById(R.id.btCadastro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                ColaboradorModel colaboradorEnviar = new ColaboradorModel();

                colaboradorEnviar.setIdColaborador("230");
                colaboradorEnviar.setNomeColaborador(nome);
                colaboradorEnviar.setEmailColaborador(email);
                colaboradorEnviar.setSenhaColaborador(senha);

                int indexArroba = colaboradorEnviar.getEmailColaborador().indexOf("@");
                int indexPonto = colaboradorEnviar.getEmailColaborador().indexOf(".");
                String dominioAtual = colaboradorEnviar.getEmailColaborador().substring(indexArroba + 1, indexPonto);

                OrganizacaoModel organizacaoModel = new OrganizacaoModel();
                organizacaoModel.setDominioOrganizacao(dominioAtual);

                colaboradorEnviar.setOrganizacaoColaborador(organizacaoModel);

                Gson gson = new Gson();

                String userCoded = new String(Base64.encodeToString(gson.toJson(colaboradorEnviar).getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP));
                enviarColaboradoresServer(userCoded);
                Log.i("teste coded", userCoded);

            }
        });

    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void enviarColaboradoresServer(final String code) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("novoColaborador", code);
            String url = Constants.url + "/colaborador/cadastro";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "POST", "Cadastro").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void customEventReceived(Event event) {
        if (event.getEventName().equals("Cadastro" + Constants.eventSuccessLabel)) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCadastroColaborador.this);
            builder.setTitle("Sucesso");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (event.getEventName().equals("Cadastro" + Constants.eventErrorLabel)) {
            Snackbar snackbar = Snackbar.make(btnCadastrar, "Erro ao realizar cadastro", Snackbar.LENGTH_LONG);
            snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            snackbar.show();
        }
    }

}
