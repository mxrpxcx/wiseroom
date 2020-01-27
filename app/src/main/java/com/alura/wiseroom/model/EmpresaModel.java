package com.alura.wiseroom.model;

import java.io.Serializable;

public class EmpresaModel implements Serializable {
    private String id;
    private String nome;
    private String idOrganizacao;
    private String dominioOrganizacao;


    public EmpresaModel(String id, String nome, String idOrganizacao, String dominioOrganizacao) {
        this.id = id;
        this.nome = nome;
        this.idOrganizacao = idOrganizacao;
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

    public String getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(String idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
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
                ", idOrganizacao='" + idOrganizacao + '\'' +
                ", dominioOrganizacao='" + dominioOrganizacao + '\'' +
                '}';
    }
}
