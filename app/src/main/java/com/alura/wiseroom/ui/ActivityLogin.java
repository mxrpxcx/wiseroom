package com.alura.wiseroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.OrganizacaoModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ActivityLogin extends AppCompatActivity {
    private Button btLogin;
    private TextView etCadastro;
    private EditText tvEmail;
    private EditText tvSenha;
    RequestQueue mQueue;


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

    private void logaColaborador(ColaboradorModel colaborador) {
        colaborador.setEmailColaborador(tvEmail.getText().toString());
        colaborador.setSenhaColaborador(tvSenha.getText().toString());
        verificaRegistro(colaborador.getEmailColaborador(), colaborador.getSenhaColaborador());
        finish();

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
        tvEmail = (EditText) findViewById(R.id.editEmail);
        tvSenha = (EditText) findViewById(R.id.editSenha);
        tvEmail.setText("b@b.b");
        tvSenha.setText("b");
    }


    public void verificaRegistro(final String email, final String senha){
        String url = "http://172.30.248.130:8080/ReservaDeSala/rest/colaborador/login";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resposta){
                                    Log.i("teste response", resposta);
                                    Gson gson = new Gson();


                                    ColaboradorModel colaboradorJson = gson.fromJson(resposta, ColaboradorModel.class);
                                    ColaboradorModel colaboradorRecebidoJson = new ColaboradorModel();


                                        colaboradorRecebidoJson.setIdColaborador(colaboradorJson.getIdColaborador());
                                        colaboradorRecebidoJson.setNomeColaborador(colaboradorJson.getNomeColaborador());
                                        colaboradorRecebidoJson.setEmailColaborador(colaboradorJson.getEmailColaborador());
                                        colaboradorRecebidoJson.setAdministrador(colaboradorJson.isAdministrador());
                                        colaboradorRecebidoJson.setSenhaColaborador(colaboradorJson.getSenhaColaborador());
                                 //       OrganizacaoModel organizacaoJson = colaboradorJson.getOrganizacaoColaborador();
                                   //     OrganizacaoModel organizacaoModel = new OrganizacaoModel();
                                   //     organizacaoModel.setIdOrganizacao(organizacaoJson.getIdOrganizacao());
                                  //      organizacaoModel.setNomeOrganizacao(organizacaoJson.getNomeOrganizacao());
                                    //    organizacaoModel.setDominioOrganizacao(organizacaoJson.getDominioOrganizacao());
                                  //      colaboradorRecebidoJson.setOrganizacaoColaborador(organizacaoModel);


                                    Log.i("TESTE RODANDO ?? ", "model objeto " + colaboradorRecebidoJson.toString());

                                    Intent intent = new Intent(ActivityLogin.this, ActivityPerfil.class);
                                    intent.putExtra("colaboradorLogado", colaboradorRecebidoJson);
                                    startActivity(intent);


                            }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("authorization", "secret");
                params.put("emailColaborador", email);
                params.put("senhaColaborador", senha);
                return params;
            }
        };

        mQueue.add(request);
    }

}
