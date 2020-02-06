package com.alura.wiseroom.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.model.ColaboradorModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ActivityCadastroColaborador extends AppCompatActivity {

    RequestQueue mQueue;

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

                int indexArroba = colaboradorEnviar.getEmail().indexOf("@");
                int indexPonto = colaboradorEnviar.getEmail().indexOf(".");
                String dominioAtual = colaboradorEnviar.getEmail().substring(indexArroba + 1, indexPonto);

                colaboradorEnviar.setIdOrganizacao(dominioAtual);
                enviarColaboradoresServer(colaboradorEnviar);

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




    public void enviarColaboradoresServer(final ColaboradorModel colaboradorModel){
        String url = "http://172.30.248.130/insereColaborador.php";

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
