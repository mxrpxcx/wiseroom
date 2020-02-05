package com.alura.wiseroom.model;

import java.io.Serializable;

public class SalaModel implements Serializable {
    private String idSala;
    private String nome;
    private int capacidade;
    private double areaDaSala;
    private String descricaoSala;

    public SalaModel() {
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public double getAreaDaSala() {
        return areaDaSala;
    }

    public void setAreaDaSala(double areaDaSala) {
        this.areaDaSala = areaDaSala;
    }

    public String getDescricaoSala() {
        return descricaoSala;
    }

    public void setDescricaoSala(String descricaoSala) {
        this.descricaoSala = descricaoSala;
    }

    @Override
    public String toString() {
        return "SalaModel{" +
                ", nome='" + nome + '\'' +
                ", capacidade=" + capacidade +
                ", areaDaSala=" + areaDaSala +
                ", descricaoSala='" + descricaoSala + '\'' +
                '}';
    }
}
