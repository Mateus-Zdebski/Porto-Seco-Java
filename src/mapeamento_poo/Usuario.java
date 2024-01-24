/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapeamento_poo;

/**
 *
 * @author Mateus
 */
public class Usuario {

    private int id;
    private String nome;
    private String senha;
    private String email;
    private String perfil;
    private String telefone;

    // Construtor
    public Usuario() {
    }

    // Métodos get
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getTelefone() {
        return telefone;
    }

    // Métodos set
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
