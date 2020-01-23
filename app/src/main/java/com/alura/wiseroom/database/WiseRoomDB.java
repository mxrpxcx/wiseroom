package com.alura.wiseroom.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WiseRoomDB extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "dbWiseroom";
    private static final int DATABASE_VERSION = 1;

    public static final String[] TABELA_NOME =  new String[] {"tbColaborador","tbSala"};
    public static final String[] COLUNA_ID = new String[] {"idColaborador","idSala"};
    public static final String[] COLUNA_NOME = new String[] {"nomeColaborador", "nomeSala"};
    public static final String COLUNA_EMAIL = "emailColaborador";
    public static final String COLUNA_SENHA = "senhaColaborador";
    public static final String COLUNA_CAPACIDADE_SALA  = "capacidadeSala";
    public static final String COLUNA_DESCRICAO_SALA = "descricaoSala";




    private static final String CREATE_TABLE_QUERY_COLABORADOR =
            "CREATE TABLE " + TABELA_NOME[0] + " (" + COLUNA_ID[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME[0] + " TEXT, " +
                    COLUNA_EMAIL + " TEXT, " +
                    COLUNA_SENHA + " TEXT " + ")";

    private static final String CREATE_TABLE_QUERY_SALA =
            "CREATE TABLE " + TABELA_NOME[1] + " (" + COLUNA_ID[1] + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME[1] + " TEXT, " +
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME);
        onCreate(sqLiteDatabase);
    }


}
