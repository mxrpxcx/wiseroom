package com.alura.wiseroom.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.ColaboradorModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityCadastroColaborador extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_colaborador);
        mQueue = Volley.newRequestQueue(this);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        openHelper = new WiseRoomDB(this);
        db = openHelper.getWritableDatabase();

        db.delete(WiseRoomDB.TABELA_NOME_COLABORADOR,null,null);
        receberColaboradoresServer();

        final EditText txtNome = (EditText) findViewById(R.id.editNomeCadastro);
        final EditText txtEmail = (EditText) findViewById(R.id.editEmailCadastro);
        final EditText txtSenha = (EditText) findViewById(R.id.editSenhaCadastro);
        Button btnCadastrar = (Button) findViewById(R.id.btCadastro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                ColaboradorModel colaboradorEnviar = new ColaboradorModel();

                colaboradorEnviar.setNome(nome);
                colaboradorEnviar.setEmail(email);
                colaboradorEnviar.setSenha(senha);


                enviarColaboradoresServer(colaboradorEnviar);
                inserirColaborador(nome, email, senha);

                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCadastroColaborador.this);
                builder.setTitle("Sucesso");
                builder.setMessage("Sua conta foi criada com sucesso");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    public void inserirColaborador(String nome, String email, String senha) {
        ContentValues values = new ContentValues();
        values.put(WiseRoomDB.COLUNA_NOME_COLABORADOR, nome);
        values.put(WiseRoomDB.COLUNA_EMAIL_COLABORADOR, email);
        values.put(WiseRoomDB.COLUNA_SENHA, senha);
        db.insert(WiseRoomDB.TABELA_NOME_COLABORADOR, null, values);
    }

    public void receberColaboradoresServer(){
        String url = "http://172.30.248.130:3000/colaborador";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resposta) {
                        try {

                            for (int i = 0; i < resposta.length(); i++) {

                                JSONObject colaboradorJson = resposta.getJSONObject(i);
                                ColaboradorModel colaboradorRecebidoJson = new ColaboradorModel();

                                colaboradorRecebidoJson.setId(colaboradorJson.getString("id"));
                                colaboradorRecebidoJson.setNome(colaboradorJson.getString("nome"));
                                colaboradorRecebidoJson.setIdOrganizacao(colaboradorJson.getString("idOrganizacao"));
                                colaboradorRecebidoJson.setEmail(colaboradorJson.getString("email"));
                                colaboradorRecebidoJson.setAdministrador(colaboradorJson.getBoolean("administrador"));
                                colaboradorRecebidoJson.setSenha(colaboradorJson.getString("senha"));

                                Log.i("TESTE RODANDO ?? ", "model objeto "+colaboradorRecebidoJson.toString());

                                ContentValues cv = new ContentValues();
                                cv.put(WiseRoomDB.COLUNA_ID_COLABORADOR, colaboradorRecebidoJson.getId());
                                cv.put(WiseRoomDB.COLUNA_NOME_COLABORADOR, colaboradorRecebidoJson.getNome());
                                cv.put(WiseRoomDB.COLUNA_EMAIL_COLABORADOR, colaboradorRecebidoJson.getEmail());
                                cv.put(WiseRoomDB.COLUNA_SENHA, colaboradorRecebidoJson.getSenha());

                                Log.i("TESTE RODANDO ?? ", "cv values "+cv.toString());


                                db.insert(WiseRoomDB.TABELA_NOME_COLABORADOR, null, cv);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void enviarColaboradoresServer(final ColaboradorModel colaboradorModel){
        String url = "http://172.30.248.130:3000/colaborador";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "bugou");
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nome", colaboradorModel.getNome());
                params.put("email", colaboradorModel.getEmail());
                params.put("idOrganizacao", colaboradorModel.getIdOrganizacao());
                params.put("administrador", String.valueOf(colaboradorModel.isAdministrador()));
                params.put("senha", colaboradorModel.getSenha());
                return params;
            }
        };
        mQueue.add(postRequest);
    }
}
