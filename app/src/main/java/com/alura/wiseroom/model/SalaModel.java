package com.alura.wiseroom.model;

import java.io.Serializable;

public class SalaModel implements Serializable {
    private String idSala;
    private String nomeSala;
    private int capacidadeSala;
    private double areaSala;
    private String descricaoSala;

    public SalaModel() {
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public int getCapacidadeSala() {
        return capacidadeSala;
    }

    public void setCapacidadeSala(int capacidadeSala) {
        this.capacidadeSala = capacidadeSala;
    }

    public double getAreaSala() {
        return areaSala;
    }

    public void setAreaSala(double areaSala) {
        this.areaSala = areaSala;
    }

    public String getDescricaoSala() {
        return descricaoSala;
    }

    public void setDescricaoSala(String descricaoSala) {
        this.descricaoSala = descricaoSala;
    }
}
