package com.alura.wiseroom.ui;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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


        final EditText txtNome = (EditText) findViewById(R.id.editNomeCadastro);
        final EditText txtEmail = (EditText) findViewById(R.id.editEmailCadastro);
        final EditText txtSenha = (EditText) findViewById(R.id.editSenhaCadastro);
        btnCadastrar = (Button) findViewById(R.id.btCadastro);

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

                Gson gson  = new Gson();

                try {
                    String userCoded = new String(Base64.encodeToString(gson.toJson(colaboradorEnviar).getBytes("UTF-8"), Base64.NO_WRAP));
                    enviarColaboradoresServer(userCoded);
                    Log.i("teste coded", userCoded);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

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

    public void enviarColaboradoresServer(final String code){
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("novoColaborador", code);
            String url = "http://172.30.248.130:8080/ReservaDeSala/rest/colaborador/cadastro";

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
