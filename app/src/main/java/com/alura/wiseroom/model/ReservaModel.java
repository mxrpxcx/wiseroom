package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private String idReserva;
    private SalaModel salaReserva;
    private ColaboradorModel colaboradorReserva;
    private String ativo;

    private String
            descricaoReserva,
            idColaboradorReserva,
            nomeColaborador,
            idSalaReserva,
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

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getIdColaboradorReserva() {
        return idColaboradorReserva;
    }

    public void setIdColaboradorReserva(String idColaboradorReserva) {
        this.idColaboradorReserva = idColaboradorReserva;
    }

    public void setIdColaborador(String idColaboradorReserva) {
        this.idColaboradorReserva = idColaboradorReserva;
    }

    public String getIdSalaReserva() {
        return idSalaReserva;
    }

    public void setIdSalaReserva(String idSalaReserva) {
        this.idSalaReserva = idSalaReserva;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    @Override
    public String toString() {
        return "ReservaModel{" +
                "idReserva='" + idReserva + '\'' +
                ", salaReserva=" + salaReserva +
                ", colaboradorReserva=" + colaboradorReserva +
                ", ativo='" + ativo + '\'' +
                ", descricaoReserva='" + descricaoReserva + '\'' +
                ", idColaboradorReserva='" + idColaboradorReserva + '\'' +
                ", idSalaReserva='" + idSalaReserva + '\'' +
                ", dataReserva='" + dataReserva + '\'' +
                ", horaInicioReserva='" + horaInicioReserva + '\'' +
                ", horaFimReserva='" + horaFimReserva + '\'' +
                '}';
    }
}
