package com.alura.wiseroom.model;

public class FilialModel extends OrganizacaoModel {
   private String idMatriz;
   private String nomeFilial;

    public String getNomeFilial() {
        return nomeFilial;
    }

    public void setNomeFilial(String nomeFilial) {
        this.nomeFilial = nomeFilial;
    }

    public String getIdMatriz() {
        return idMatriz;
    }

    public void setIdMatriz(String idMatriz) {
        this.idMatriz = idMatriz;
    }
}
