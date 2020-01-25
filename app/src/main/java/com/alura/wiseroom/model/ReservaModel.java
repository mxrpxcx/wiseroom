package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private String id;
    private String idReservaxSala;
    private String idReservaxColaborador;
    private String idReservaxData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdReservaxSala() {
        return idReservaxSala;
    }

    public void setIdReservaxSala(String idReservaxSala) {
        this.idReservaxSala = idReservaxSala;
    }

    public String getIdReservaxColaborador() {
        return idReservaxColaborador;
    }

    public void setIdReservaxColaborador(String idReservaxColaborador) {
        this.idReservaxColaborador = idReservaxColaborador;
    }

    public String getIdReservaxData() {
        return idReservaxData;
    }

    public void setIdReservaxData(String idReservaxData) {
        this.idReservaxData = idReservaxData;
    }
}
