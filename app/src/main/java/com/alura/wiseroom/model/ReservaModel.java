package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private String idReserva;
    private SalaModel salaReserva;
    private ColaboradorModel colaboradorReserva;

    private String
    descricaoData,
    dataMarcada,
    horaInicio,
    horaFim;


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

    public String getDataMarcada() {
        return dataMarcada;
    }

    public void setDataMarcada(String dataMarcada) {
        this.dataMarcada = dataMarcada;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getDescricaoData() {
        return descricaoData;
    }

    public void setDescricaoData(String descricaoData) {
        this.descricaoData = descricaoData;
    }

    @Override
    public String toString() {
        return "ReservaModel{" +
                ", salaReserva=" + salaReserva +
                ", colaboradorReserva=" + colaboradorReserva +
                '}';
    }
}
