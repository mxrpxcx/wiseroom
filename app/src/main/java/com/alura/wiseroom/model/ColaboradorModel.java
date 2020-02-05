package com.alura.wiseroom.model;

import java.io.Serializable;

public class ColaboradorModel implements Serializable {

   private String idColaborador;
   private String nome;
   private String organizacao="null";
   private String email;
   private boolean administrador=false;
   private String senha;


   public ColaboradorModel(String nome, String email, String senha) {
      this.senha = senha;
      this.nome = nome;
      this.email = email;
   }

   public ColaboradorModel() {
   }

   public String getOrganizacao() {
      return organizacao;
   }

   public void setOrganizacao(String organizacao) {
      this.organizacao = organizacao;
   }

   public String getSenha() {
      return senha;
   }

   public void setSenha(String senha) {
      this.senha = senha;
   }

   public String getIdColaborador() {
      return idColaborador;
   }

   public void setIdColaborador(String idColaborador) {
      this.idColaborador = idColaborador;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getIdOrganizacao() {
      return organizacao;
   }

   public void setIdOrganizacao(String idOrganizacao) {
      this.organizacao = idOrganizacao;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public boolean isAdministrador() {
      return administrador;
   }

   public void setAdministrador(boolean administrador) {
      this.administrador = administrador;
   }

   @Override
   public String toString() {
      return "ColaboradorModel{" +
              ", nome='" + nome + '\'' +
              ", idOrganizacao=" + organizacao +
              ", email='" + email + '\'' +
              ", administrador=" + administrador +
              ", senha='" + senha + '\'' +
              '}';
   }
}
