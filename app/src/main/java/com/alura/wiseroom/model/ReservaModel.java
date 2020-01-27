package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private String id;
    private SalaModel salaReserva;
    private ColaboradorModel colaboradorReserva;
    private DataModel dataReservada;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SalaModel getSalaReserva() {
        return salaReserva;
    }

    public void setSalaReserva(SalaModel salaReserva) {
        this.salaReserva = salaReserva;
    }

    public ColaboradorModel getColaboradorReserva() {
        return colaboradorReserva;
    }

    public void setColaboradorReserva(ColaboradorModel colaboradorReserva) {
        this.colaboradorReserva = colaboradorReserva;
    }

    public DataModel getDataReservada() {
        return dataReservada;
    }

    public void setDataReservada(DataModel dataReservada) {
        this.dataReservada = dataReservada;
    }

    @Override
    public String toString() {
        return "ReservaModel{" +
                "id='" + id + '\'' +
                ", salaReserva=" + salaReserva +
                ", colaboradorReserva=" + colaboradorReserva +
                ", dataReservada=" + dataReservada +
                '}';
    }
}
