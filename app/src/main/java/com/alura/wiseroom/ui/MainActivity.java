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


public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    private Button btCadastro;
    private EditText tvEmail;
    private EditText tvSenha;
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    private Button btTutorial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        btLogin = findViewById(R.id.btLogin);
        btCadastro = findViewById(R.id.btCadastro);
        btTutorial = findViewById(R.id.btAjuda);

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



        tvEmail = (EditText) findViewById(R.id.editEmail);
        tvSenha = (EditText) findViewById(R.id.editSenha);

        tvEmail.setText("b@b.com");
        tvSenha.setText("b");

        dbHelper = new WiseRoomDB(this);
        db = dbHelper.getWritableDatabase();
        final ColaboradorModel colaborador = new ColaboradorModel();


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logaColaborador(colaborador);

            }
        });


        ContentValues cv1 = new ContentValues();
        cv1.put(WiseRoomDB.COLUNA_NOME_COLABORADOR, "b");
        cv1.put(WiseRoomDB.COLUNA_EMAIL_COLABORADOR, "b@b.com");
        cv1.put(WiseRoomDB.COLUNA_SENHA, "b");
        db.insert(WiseRoomDB.TABELA_NOME_COLABORADOR, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(WiseRoomDB.COLUNA_ID_SALA, 0);
        cv2.put(WiseRoomDB.COLUNA_NOME_SALA, "sala 0");
        cv2.put(WiseRoomDB.COLUNA_CAPACIDADE_SALA, 50);
        cv2.put(WiseRoomDB.COLUNA_DESCRICAO_SALA, "sala");
        db.insert(WiseRoomDB.TABELA_NOME_SALA, null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(WiseRoomDB.COLUNA_ID_SALA_MARCADA, "0");
        cv3.put(WiseRoomDB.COLUNA_DATA_MARCADA, "0");
        cv3.put(WiseRoomDB.COLUNA_ID_COLABORADOR_RESERVA, 1);
        db.insert(WiseRoomDB.TABELA_NOME_RESERVA, null, cv3);



        Log.i("TESTE RESERVA  ", cv3.toString());
        Log.i("Teste Sala  ", "sala adicionada? "+cv2.toString());
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

                String intentId = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_ID_COLABORADOR));
                String intentEmail = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_EMAIL_COLABORADOR));
                String intentSenha = cursor.getString(cursor.getColumnIndex(WiseRoomDB.COLUNA_SENHA));

                ColaboradorModel colaboradorModel = new ColaboradorModel();
                colaboradorModel.setId(intentId);
                colaboradorModel.setEmail(intentEmail);
                colaboradorModel.setSenha(intentSenha);

                Intent intent = new Intent(MainActivity.this, ActivityEscolha.class);

                intent.putExtra("colaboradorLogado", colaboradorModel);

                startActivity(intent);
                db.close();

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
