package com.alura.wiseroom;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.alura.wiseroom.database.WiseRoomDB;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.SalaModel;

public class Aplicacao extends Application {
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        ColaboradorModel colaborador = new ColaboradorModel("a","a@a.com","a");
        SalaModel salaModel = new SalaModel(1, "sala 1", 50, "sala", "18/02/2020");

        ContentValues cv1 = new ContentValues();
        cv1.put(WiseRoomDB.COLUNA_NOME_COLABORADOR, "a");
        cv1.put(WiseRoomDB.COLUNA_EMAIL_COLABORADOR, "a@a.com");
        cv1.put(WiseRoomDB.COLUNA_SENHA, "a");
        db.insert(WiseRoomDB.TABELA_NOME_COLABORADOR, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(WiseRoomDB.COLUNA_NOME_SALA, "sala 1");
        cv2.put(WiseRoomDB.COLUNA_CAPACIDADE_SALA, 50);
        cv2.put(WiseRoomDB.COLUNA_DESCRICAO_SALA, "sala");
        cv2.put(WiseRoomDB.COLUNA_DATA, "18/02/2002");
        db.insert(WiseRoomDB.TABELA_NOME_SALA, null, cv2);

    }

}
