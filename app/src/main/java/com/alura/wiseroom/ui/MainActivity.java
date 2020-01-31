package com.alura.wiseroom.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.help;
import com.alura.wiseroom.model.ColaboradorModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    private Button btCadastro;
    private EditText tvEmail;
    private EditText tvSenha;
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    private Button btTutorial;
    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        mQueue = Volley.newRequestQueue(this);

        ab.hide();
        setarValores();

        dbHelper = new WiseRoomDB(this);
        db = dbHelper.getWritableDatabase();
        final ColaboradorModel colaborador = new ColaboradorModel();
        setListenersDoBotao();
        setListenerColaborador(colaborador);
        criaValores();
    }

    private void logaColaborador(ColaboradorModel colaborador) {
        colaborador.setEmail(tvEmail.getText().toString());
        colaborador.setSenha(tvSenha.getText().toString());
       verificaRegistro(colaborador.getEmail(), colaborador.getSenha());


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
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityCadastroColaborador.class));
            }
        });

        btTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, help.class));
            }
        });
    }

    private void setarValores() {
        btLogin = findViewById(R.id.btLogin);
        btCadastro = findViewById(R.id.btCadastro);
        btTutorial = findViewById(R.id.btAjuda);
        tvEmail = (EditText) findViewById(R.id.editEmail);
        tvSenha = (EditText) findViewById(R.id.editSenha);
        tvEmail.setText("b@b.com");
        tvSenha.setText("b");
    }

    private void criaValores(){

        ContentValues cv2 = new ContentValues();
        cv2.put(WiseRoomDB.COLUNA_ID_SALA, "15");
        cv2.put(WiseRoomDB.COLUNA_NOME_SALA, "Sala conselho jedi");
        cv2.put(WiseRoomDB.COLUNA_CAPACIDADE_SALA, 50);
        cv2.put(WiseRoomDB.COLUNA_DESCRICAO_SALA, "sala bonita");
        db.insert(WiseRoomDB.TABELA_NOME_SALA, null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(WiseRoomDB.COLUNA_ID_SALA, "14");
        cv3.put(WiseRoomDB.COLUNA_NOME_SALA, "Sala grande");
        cv3.put(WiseRoomDB.COLUNA_CAPACIDADE_SALA, 500);
        cv3.put(WiseRoomDB.COLUNA_DESCRICAO_SALA, "sala grande");
        db.insert(WiseRoomDB.TABELA_NOME_SALA, null, cv3);

        Log.i("Teste Sala  ", "sala adicionada? "+cv2.toString());
    }

    public void verificaRegistro(String email, String senha){
        String url = "http://172.30.248.130:3000/colaborador?email="+email+"&senha="+senha;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resposta) {
                        if (resposta.length() > 0) {
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

                                    Log.i("TESTE RODANDO ?? ", "model objeto " + colaboradorRecebidoJson.toString());

                                    Intent intent = new Intent(MainActivity.this, ActivityEscolha.class);
                                    intent.putExtra("colaboradorLogado", colaboradorRecebidoJson);
                                    startActivity(intent);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

}
