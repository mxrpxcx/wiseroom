package com.alura.wiseroom.model;

public class DataModel {
    private String nomeData,
            dataData,
            horaData;

    private String idSalaxData;

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