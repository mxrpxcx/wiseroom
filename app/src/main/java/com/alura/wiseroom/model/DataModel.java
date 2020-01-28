package com.alura.wiseroom.model;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String nomeData,
            dataData,
            horaData;

    ColaboradorModel colaboradorModel;

    private String idSalaxData;

    public ColaboradorModel getColaboradorModel() {
        return colaboradorModel;
    }

    public void setColaboradorModel(ColaboradorModel colaboradorModel) {
        this.colaboradorModel = colaboradorModel;
    }

    public String getNomeData() {
        return nomeData;
    }

    public void setNomeData(String nomeData) {
        this.nomeData = nomeData;
    }

    public String getDataData() {
        return dataData;
    }

    public void setDataData(String dataData) {
        this.dataData = dataData;
    }

    public String getHoraData() {
        return horaData;
    }

    public void setHoraData(String horaData) {
        this.horaData = horaData;
    }

    public String getIdSalaxData() {
        return idSalaxData;
    }

    public void setIdSalaxData(String idSalaxData) {
        this.idSalaxData = idSalaxData;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "nomeData='" + nomeData + '\'' +
                ", dataData='" + dataData + '\'' +
                ", horaData='" + horaData + '\'' +
                ", idSalaxData=" + idSalaxData +
                '}';
    }
}
