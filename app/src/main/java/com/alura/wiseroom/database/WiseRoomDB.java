 package com.alura.wiseroom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alura.wiseroom.model.SalaModel;

public class WiseRoomDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbWiseroom";
    private static final int DATABASE_VERSION = 23;

    // Colaborador
    public static final String TABELA_NOME_COLABORADOR =  "tbColaborador";
    public static final String COLUNA_ID_COLABORADOR = "idColaborador";
    public static final String COLUNA_NOME_COLABORADOR = "nomeColaborador";
    public static final String COLUNA_EMAIL_COLABORADOR = "emailColaborador";
    public static final String COLUNA_SENHA = "senhaColaborador";

    // Sala
    public static final String TABELA_NOME_SALA =  "tbSala";
    public static final String COLUNA_ID_SALA = "idSala";
    public static final String COLUNA_NOME_SALA = "nomeSala";
    public static final String COLUNA_CAPACIDADE_SALA  = "capacidadeSala";
    public static final String COLUNA_DESCRICAO_SALA = "descricaoSala";

    // Data marcada
    public static final String TABELA_NOME_DATA = "tbData";
    public static final String COLUNA_ID_DATA = "idData";
    public static final String COLUNA_NOME_DATA = "nomeData";
    public static final String COLUNA_DATA_MARCADA = "dataMarcada";
    public static final String COLUNA_HORARIO_MARCADO = "horarioMarcado";
    public static final String COLUNA_ID_SALA_MARCADA = "idSalaxData";
    public static final String COLUNA_ID_COLABORADOR_QUE_MARCOU = "idColaboradorxData";

    // Tabela reserva
    public static final String TABELA_NOME_RESERVA = "tbReserva";
    public static final String COLUNA_ID_RESERVA = "idReserva";
    public static final String COLUNA_ID_DATA_RESERVADA = "idDataxReserva";
    public static final String COLUNA_ID_SALA_RESERVADA = "idSalaxReserva";
    public static final String COLUNA_ID_COLABORADOR_RESERVA = "idColaboradorxReserva";


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


    private static final String CREATE_TABLE_QUERY_DATA =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME_DATA + " (" + COLUNA_ID_DATA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME_DATA + " TEXT, " +
                    COLUNA_DATA_MARCADA + " TEXT, " +
                    COLUNA_HORARIO_MARCADO + " TEXT, " +
                    COLUNA_ID_SALA_MARCADA + " INTEGER," +
                    COLUNA_ID_COLABORADOR_QUE_MARCOU + " INTEGER, " +

                    "FOREIGN KEY "+ "("+COLUNA_ID_COLABORADOR_QUE_MARCOU+")"+ "REFERENCES "+ TABELA_NOME_COLABORADOR+"("+
                    COLUNA_ID_COLABORADOR+"), " +

                    "FOREIGN KEY "+ "("+COLUNA_ID_SALA_MARCADA+")"+ "REFERENCES "+ TABELA_NOME_SALA+"("+
                    COLUNA_ID_SALA+")"+")";

    private static final String CREATE_TABLE_QUERY_RESERVA =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME_RESERVA + " (" + COLUNA_ID_RESERVA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_ID_DATA_RESERVADA + " INTEGER, " +
                    COLUNA_ID_COLABORADOR_RESERVA + " INTEGER, " +
                    COLUNA_ID_SALA_RESERVADA + " INTEGER, " +

                    "FOREIGN KEY "+ "("+COLUNA_ID_DATA_RESERVADA+")"+ "REFERENCES "+ TABELA_NOME_DATA+"("+
                    COLUNA_ID_DATA+"), " +

                    "FOREIGN KEY "+ "("+COLUNA_ID_COLABORADOR_RESERVA+")"+ "REFERENCES "+ TABELA_NOME_COLABORADOR+"("+
                    COLUNA_ID_COLABORADOR+"), " +

                    "FOREIGN KEY "+ "("+COLUNA_ID_SALA_RESERVADA+")"+ "REFERENCES "+ TABELA_NOME_SALA+"("+
                    COLUNA_ID_SALA+")"
                    +")";



    public WiseRoomDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY_COLABORADOR);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY_SALA);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY_DATA);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY_RESERVA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME_COLABORADOR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME_SALA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME_DATA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME_RESERVA);
        onCreate(sqLiteDatabase);
    }




    public static long inserirData (SQLiteDatabase db, ContentValues cv) {
        return db.insert(TABELA_NOME_DATA, null, cv);
    }



    public static long inserirReserva (SQLiteDatabase db, ContentValues cv) {
        return db.insert(TABELA_NOME_RESERVA, null, cv);
    }

    public static Cursor selecionarData (SQLiteDatabase db,String selection) {
        return db.query(TABELA_NOME_DATA, null, selection, null, null, null, null, null);
    }

    public static Cursor selecionarReserva (SQLiteDatabase db,String selection) {
        return db.query(TABELA_NOME_RESERVA, null, selection, null, null, null, null, null);
    }

    public static int deletarData (SQLiteDatabase db,String whereClause) {
        return db.delete(TABELA_NOME_DATA, whereClause, null);
    }

    public static int deletarReserva (SQLiteDatabase db,String whereClause) {
        return db.delete(TABELA_NOME_RESERVA, whereClause, null);
    }

    public static int atualizarData (SQLiteDatabase db,String whereClause,ContentValues cv) {

        return db.update(TABELA_NOME_DATA,cv,whereClause,null);
    }

}
