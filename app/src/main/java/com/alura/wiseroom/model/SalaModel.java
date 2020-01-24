package com.alura.wiseroom.model;

public class SalaModel {
    private int id;
    private String nome;
    private int capacidade;
    private double areaDaSala;
    private String descricaoSala;
    private String dataSala;

    public SalaModel(String nome, int capacidade, String descricaoSala) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.descricaoSala = descricaoSala;
        this.dataSala = dataSala;
    }

    public SalaModel() {
    }

    public String getDataSala() {
        return dataSala;
    }

    public void setDataSala(String dataSala) {
        this.dataSala = dataSala;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
