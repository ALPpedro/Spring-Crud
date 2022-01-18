package com.atividade.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Cliente extends RepresentationModel<Cliente> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDeNascimento;

    private String rg;
    private String cpf;
    private int telefone;

    public  Cliente(){

    }
    public Cliente(String nome, LocalDate dataDeNascimento, String rg, String cpf, int telefone) {
        super();
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.rg = rg;
        this.cpf = cpf;
        this.telefone = telefone;
    }

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

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public int getTelefone() {
        return telefone;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
