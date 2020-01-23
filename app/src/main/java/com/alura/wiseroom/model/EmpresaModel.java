package com.alura.wiseroom.model;

public class EmpresaModel {
    private int id;
    private String nome;
    private int idOrganizacao;
    private String dominioOrganizacao;


    public EmpresaModel(int id, String nome, int idOrganizacao, String dominioOrganizacao) {
        this.id = id;
        this.nome = nome;
        this.idOrganizacao = idOrganizacao;
        this.dominioOrganizacao = dominioOrganizacao;
    }

    public EmpresaModel() {
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

    public int getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(int idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public String getDominioOrganizacao() {
        return dominioOrganizacao;
    }

    public void setDominioOrganizacao(String dominioOrganizacao) {
        this.dominioOrganizacao = dominioOrganizacao;
    }
}
