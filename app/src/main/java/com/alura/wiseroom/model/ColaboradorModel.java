package com.alura.wiseroom.model;

public class ColaboradorModel {
   private int id;
   private String nome;
   private int idOrganizacao;
   private String email;
   private boolean administrador;


   public ColaboradorModel(int id, String nome, int idOrganizacao, String email, boolean administrador) {
      this.id = id;
      this.nome = nome;
      this.idOrganizacao = idOrganizacao;
      this.email = email;
      this.administrador = administrador;
   }

   public ColaboradorModel() {
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
}
