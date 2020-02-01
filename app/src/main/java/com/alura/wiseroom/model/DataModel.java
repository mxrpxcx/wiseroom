package com.alura.wiseroom.model;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String
            id,
            nomeData,
            dataMarcada,
            horaInicio,
            horaFim;

    ColaboradorModel colaboradorModel;

    private String idSalaxData;

    public ColaboradorModel getColaboradorModel() {
        return colaboradorModel;
    }

    public void setColaboradorModel(ColaboradorModel colaboradorModel) {
        this.colaboradorModel = colaboradorModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeData() {
        return nomeData;
    }

    public void setNomeData(String nomeData) {
        this.nomeData = nomeData;
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

    public String getIdSalaxData() {
        return idSalaxData;
    }

    public void setIdSalaxData(String idSalaxData) {
        this.idSalaxData = idSalaxData;
    }


}
