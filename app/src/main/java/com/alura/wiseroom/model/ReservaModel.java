package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private String id;
    private SalaModel salaReservada;
    private ColaboradorModel colaboradorQueReservou;
    private DataModel dataReservada;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SalaModel getSalaReservada() {
        return salaReservada;
    }

    public void setSalaReservada(SalaModel salaReservada) {
        this.salaReservada = salaReservada;
    }

    public ColaboradorModel getColaboradorQueReservou() {
        return colaboradorQueReservou;
    }

    public void setColaboradorQueReservou(ColaboradorModel colaboradorQueReservou) {
        this.colaboradorQueReservou = colaboradorQueReservou;
    }

    public DataModel getDataReservada() {
        return dataReservada;
    }

    public void setDataReservada(DataModel dataReservada) {
        this.dataReservada = dataReservada;
    }
}
