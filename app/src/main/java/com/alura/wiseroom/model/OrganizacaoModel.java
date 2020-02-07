package com.alura.wiseroom.model;

import java.io.Serializable;

public class OrganizacaoModel implements Serializable {
    protected String idOrganizacao;
    protected String nomeOrganizacao;
    protected String dominioOrganizacao;


    public OrganizacaoModel() {
    }

    public String getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(String idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public String getNomeOrganizacao() {
        return nomeOrganizacao;
    }

    public void setNomeOrganizacao(String nomeOrganizacao) {
        this.nomeOrganizacao = nomeOrganizacao;
    }

    public String getDominioOrganizacao() {
        return dominioOrganizacao;
    }

    public void setDominioOrganizacao(String dominioOrganizacao) {
        this.dominioOrganizacao = dominioOrganizacao;
    }
}
