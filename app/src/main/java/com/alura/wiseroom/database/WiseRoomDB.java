package com.alura.wiseroom.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alura.wiseroom.model.SalaModel;

import java.util.ArrayList;
import java.util.List;

public class WiseRoomDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbWiseroom";
    private static final int DATABASE_VERSION = 3;

    // Colaborador
    public static final String TABELA_NOME_COLABORADOR =  "tbColaborador";
    public static final String COLUNA_ID_COLABORADOR = "idColaborador";
    public static final String COLUNA_NOME_COLABORADOR = "nomeColaborador";
    public static final String COLUNA_EMAIL_COLABORADOR = "emailColaborador";
    public static final String COLUNA_SENHA = "senhaColaborador";
    // Sala
    public static final String TABELA_NOME_SALA =  "tbColaborador";
    public static final String COLUNA_ID_SALA = "idSala";
    public static final String COLUNA_NOME_SALA = "nomeSala";
    public static final String COLUNA_CAPACIDADE_SALA  = "capacidadeSala";
    public static final String COLUNA_DESCRICAO_SALA = "descricaoSala";
    public static final String COLUNA_DATA = "dataReserva";



    private static final String CREATE_TABLE_QUERY_COLABORADOR =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME_COLABORADOR + " (" + COLUNA_ID_COLABORADOR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME_COLABORADOR + " TEXT, " +
                    COLUNA_EMAIL_COLABORADOR + " TEXT, " +
                    COLUNA_SENHA + " TEXT " + ")";

    private static final String CREATE_TABLE_QUERY_SALA =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME_SALA + " (" + COLUNA_ID_SALA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME_SALA + " TEXT, " +
                    COLUNA_DATA + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    COLUNA_CAPACIDADE_SALA + " TEXT, " +
                    COLUNA_DESCRICAO_SALA + " TEXT " + ")";

    public WiseRoomDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY_COLABORADOR);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY_SALA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME_COLABORADOR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME_SALA);
        onCreate(sqLiteDatabase);
    }


    public SalaModel getSala(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(COLUNA_NOME_SALA,
                new String[]{COLUNA_NOME_SALA, COLUNA_CAPACIDADE_SALA, COLUNA_DATA, COLUNA_DESCRICAO_SALA},
                COLUNA_ID_SALA + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        SalaModel SalaModel = new SalaModel(
            cursor.getInt(cursor.getColumnIndex(COLUNA_ID_SALA)),
            cursor.getString(cursor.getColumnIndex(COLUNA_NOME_SALA)),
            cursor.getInt(cursor.getColumnIndex(COLUNA_CAPACIDADE_SALA)),
            cursor.getString(cursor.getColumnIndex(COLUNA_DESCRICAO_SALA)),
            cursor.getString(cursor.getColumnIndex(COLUNA_DATA))
            );


        cursor.close();

        return SalaModel;
    }

    public List<SalaModel> getTodasSalas(int codigoSala) {
        List<SalaModel> listaSalas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABELA_NOME_SALA + " WHERE "+ COLUNA_ID_SALA +" = "+ codigoSala + " ORDER BY " +
                COLUNA_DATA + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SalaModel sala = new SalaModel();
                sala.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID_SALA)));
                sala.setDescricaoSala(cursor.getString(cursor.getColumnIndex(COLUNA_DESCRICAO_SALA)));
                sala.setDataSala(cursor.getString(cursor.getColumnIndex(COLUNA_DATA)));
                listaSalas.add(sala);
            } while (cursor.moveToNext());
        }

        db.close();
        return listaSalas;
    }
}
