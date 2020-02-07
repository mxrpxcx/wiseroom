package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private String idReserva;
    private SalaModel salaReserva;
    private ColaboradorModel colaboradorReserva;

    private String
    descricaoReserva,
    dataReserva,
    horaInicioReserva,
    horaFimReserva;


    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
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

    public String getDescricaoReserva() {
        return descricaoReserva;
    }

    public void setDescricaoReserva(String descricaoReserva) {
        this.descricaoReserva = descricaoReserva;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getHoraInicioReserva() {
        return horaInicioReserva;
    }

    public void setHoraInicioReserva(String horaInicioReserva) {
        this.horaInicioReserva = horaInicioReserva;
    }

    public String getHoraFimReserva() {
        return horaFimReserva;
    }

    public void setHoraFimReserva(String horaFimReserva) {
        this.horaFimReserva = horaFimReserva;
    }

    @Override
    public String toString() {
        return "ReservaModel{" +
                ", salaReserva=" + salaReserva +
                ", colaboradorReserva=" + colaboradorReserva +
                '}';
    }
}
