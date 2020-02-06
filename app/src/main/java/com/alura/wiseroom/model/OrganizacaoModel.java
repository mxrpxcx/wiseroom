package com.alura.wiseroom.model;

import java.io.Serializable;

public class OrganizacaoModel implements Serializable {
    protected String idEmpresa;
    protected String nome;
    protected String dominioOrganizacao;


    public OrganizacaoModel() {
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
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
        return "OrganizacaoModel{" +
                ", nome='" + nome + '\'' +
                ", dominioOrganizacao='" + dominioOrganizacao + '\'' +
                '}';
    }
}
