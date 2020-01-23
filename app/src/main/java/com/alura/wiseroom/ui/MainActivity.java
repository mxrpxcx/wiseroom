package com.alura.wiseroom.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.ColaboradorModel;

import gr.net.maroulis.library.EasySplashScreen;


public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    private Button btCadastro;

    private EditText tvEmail;
    private EditText tvSenha;
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLogin = findViewById(R.id.btLogin);
        btCadastro = findViewById(R.id.btCadastro);




        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityCadastroColaborador.class));
            }

        });

        ActionBar ab = getSupportActionBar();
        ab.hide();

        tvEmail = (EditText) findViewById(R.id.editEmail);
        tvSenha = (EditText) findViewById(R.id.editSenha);

        dbHelper = new WiseRoomDB(this);
        db = dbHelper.getWritableDatabase();
        final ColaboradorModel colaborador = new ColaboradorModel();


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logaColaborador(colaborador);

            }
        });

    }




    private void logaColaborador(ColaboradorModel colaborador) {
        colaborador.setEmail(tvEmail.getText().toString());
        colaborador.setSenha(tvSenha.getText().toString());

        cursor = db.rawQuery("SELECT * FROM " + WiseRoomDB.TABELA_NOME_COLABORADOR + " WHERE " +
                        WiseRoomDB.COLUNA_EMAIL_COLABORADOR + "=? AND " +
                        WiseRoomDB.COLUNA_SENHA + "=?",
                new String[]{colaborador.getEmail(), colaborador.getSenha()});
        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

                String intentNome = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_NOME_COLABORADOR));
                String intentEmail = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_EMAIL_COLABORADOR));
                Toast.makeText(MainActivity.this, "Login Realizado com Sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ActivityEscolha.class);
                intent.putExtra("nomeColaborador", intentNome);
                intent.putExtra("emailColaborador", intentEmail);
                startActivity(intent);
                db.close();
                finish();
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Erro de autenticacao");
                builder.setMessage("O nome de usuário e/ou senha estão incorretos.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }
    }
}
