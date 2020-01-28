package com.alura.wiseroom.model;

import java.io.Serializable;

public class ColaboradorModel implements Serializable {

   private String id;
   private String nome;
   private String idOrganizacao;
   private String email;
   private boolean administrador;
   private String senha;


   public ColaboradorModel(String nome, String email, String senha) {
      this.senha = senha;
      this.nome = nome;
      this.email = email;
   }

   public ColaboradorModel() {
   }

   public String getSenha() {
      return senha;
   }

   public void setSenha(String senha) {
      this.senha = senha;
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
              "id='" + id + '\'' +
              ", nome='" + nome + '\'' +
              ", idOrganizacao=" + idOrganizacao +
              ", email='" + email + '\'' +
              ", administrador=" + administrador +
              ", senha='" + senha + '\'' +
              '}';
   }
}
