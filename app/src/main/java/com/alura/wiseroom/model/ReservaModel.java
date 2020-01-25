package com.alura.wiseroom.model;

public class ReservaModel {
    private int id;
    private int idReservaxSala;
    private int idReservaxColaborador;
    private int idReservaxData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReservaxSala() {
        return idReservaxSala;
    }

    public void setIdReservaxSala(int idReservaxSala) {
        this.idReservaxSala = idReservaxSala;
    }

    public int getIdReservaxColaborador() {
        return idReservaxColaborador;
    }

    public void setIdReservaxColaborador(int idReservaxColaborador) {
        this.idReservaxColaborador = idReservaxColaborador;
    }

    public int getIdReservaxData() {
        return idReservaxData;
    }

    public void setIdReservaxData(int idReservaxData) {
        this.idReservaxData = idReservaxData;
    }
}
