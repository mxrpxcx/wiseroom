package com.alura.wiseroom.model;

public class FilialModel extends EmpresaModel {
   private String idMatriz;
   private String nomeMatriz;

    public String getNomeMatriz() {
        return nomeMatriz;
    }

    public void setNomeMatriz(String nomeMatriz) {
        this.nomeMatriz = nomeMatriz;
    }

    public String getIdMatriz() {
        return idMatriz;
    }

    public void setIdMatriz(String idMatriz) {
        this.idMatriz = idMatriz;
    }
}
