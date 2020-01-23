package com.alura.wiseroom.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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




    private static final String CREATE_TABLE_QUERY_COLABORADOR =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME_COLABORADOR + " (" + COLUNA_ID_COLABORADOR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME_COLABORADOR + " TEXT, " +
                    COLUNA_EMAIL_COLABORADOR + " TEXT, " +
                    COLUNA_SENHA + " TEXT " + ")";

    private static final String CREATE_TABLE_QUERY_SALA =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME_SALA + " (" + COLUNA_ID_SALA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME_SALA + " TEXT, " +
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


}
