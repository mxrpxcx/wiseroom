package com.alura.wiseroom.model;

import java.io.Serializable;

public class EmpresaModel implements Serializable {
    protected String id;
    protected String nome;
    protected String dominioOrganizacao;


    public EmpresaModel(String id, String nome, String idOrganizacao, String dominioOrganizacao) {
        this.id = id;
        this.nome = nome;
        this.dominioOrganizacao = dominioOrganizacao;
    }

    public EmpresaModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDominioOrganizacao() {
        return dominioOrganizacao;
    }

    public void setDominioOrganizacao(String dominioOrganizacao) {
        this.dominioOrganizacao = dominioOrganizacao;
    }

    @Override
    public String toString() {
        return "EmpresaModel{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", dominioOrganizacao='" + dominioOrganizacao + '\'' +
                '}';
    }
}
