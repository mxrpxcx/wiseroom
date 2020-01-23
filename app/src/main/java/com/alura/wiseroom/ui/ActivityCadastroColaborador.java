package com.alura.wiseroom.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.database.WiseRoomDB;

public class ActivityCadastroColaborador extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_colaborador);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        openHelper = new WiseRoomDB(this);

        final EditText txtNome = (EditText) findViewById(R.id.editNomeCadastro);
        final EditText txtEmail = (EditText) findViewById(R.id.editEmailCadastro);
        final EditText txtSenha = (EditText) findViewById(R.id.editSenhaCadastro);
        Button btnCadastrar = (Button) findViewById(R.id.btCadastro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = openHelper.getWritableDatabase();

                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                inserirCliente(nome, email, senha);

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

    public void inserirCliente(String nome, String email, String senha) {
        ContentValues values = new ContentValues();
        values.put(WiseRoomDB.COLUNA_NOME, nome);
        values.put(WiseRoomDB.COLUNA_EMAIL, email);
        values.put(WiseRoomDB.COLUNA_SENHA, senha);
        long id = db.insert(WiseRoomDB.TABELA_NOME, null, values);
    }
}
