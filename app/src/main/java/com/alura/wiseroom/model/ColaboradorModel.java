package com.alura.wiseroom.model;

import java.io.Serializable;

public class ColaboradorModel implements Serializable {

   private String idColaborador;
   private String nomeColaborador;
   private String organizacaoColaborador="null";
   private String emailColaborador;
   private boolean administrador=false;
   private String senhaColaborador;



   public ColaboradorModel() {
   }

   public String getIdColaborador() {
      return idColaborador;
   }

   public void setIdColaborador(String idColaborador) {
      this.idColaborador = idColaborador;
   }

   public String getNomeColaborador() {
      return nomeColaborador;
   }

   public void setNomeColaborador(String nomeColaborador) {
      this.nomeColaborador = nomeColaborador;
   }

   public String getOrganizacaoColaborador() {
      return organizacaoColaborador;
   }

   public void setOrganizacaoColaborador(String organizacaoColaborador) {
      this.organizacaoColaborador = organizacaoColaborador;
   }

   public String getEmailColaborador() {
      return emailColaborador;
   }

   public void setEmailColaborador(String emailColaborador) {
      this.emailColaborador = emailColaborador;
   }

   public boolean isAdministrador() {
      return administrador;
   }

   public void setAdministrador(boolean administrador) {
      this.administrador = administrador;
   }

   public String getSenhaColaborador() {
      return senhaColaborador;
   }

   public void setSenhaColaborador(String senhaColaborador) {
      this.senhaColaborador = senhaColaborador;
   }
}
