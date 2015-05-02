package br.com.buritech.agendaescolar.model.bean;

import java.io.Serializable;

/**
 * Created by marciopalheta on 01/05/15.
 */
public class Professor implements Serializable{
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private String email;
    private String foto;

    @Override
    public String toString() {
        return nome;
    }

    //Métodos de get e set omitidos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
