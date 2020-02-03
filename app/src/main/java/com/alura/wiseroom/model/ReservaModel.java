package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private String id;
    private SalaModel salaReserva;
    private ColaboradorModel colaboradorReserva;
    private DataModel dataReservada;

    private String
    descricaoData,
    dataMarcada,
    horaInicio,
    horaFim;


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
                "id='" + id + '\'' +
                ", salaReserva=" + salaReserva +
                ", colaboradorReserva=" + colaboradorReserva +
                ", dataReservada=" + dataReservada +
                '}';
    }
}
