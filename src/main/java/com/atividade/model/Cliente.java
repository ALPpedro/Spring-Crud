package com.atividade.model;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;

    @Column(name = "dataDeNascimento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDeNascimento;

    @Column(name = "rg")
    private String rg;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "telefone")
    private String telefone;

    public  Cliente(){

    }
    public Cliente(String nome, LocalDate dataDeNascimento, String rg, String cpf, String telefone) {
        super();
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.rg = rg;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Cliente(Cliente cliente) {
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

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
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
