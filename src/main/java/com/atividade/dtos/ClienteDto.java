package com.atividade.dtos;

import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ClienteDto {

    @NotBlank(message = "Campo nome está vazio ")
    private String nome;

    @NotNull(message = "Campo data está vazio")
    private LocalDate dataDeNascimento;
    @NotBlank(message = "Campo rg está vazio")
    private String rg;
    @CPF(message = "CPF invalido")
    @NotBlank(message = "Campo CPF está vazio")
    private String cpf;
    @NotBlank(message = "Campo telefone está vazio")
    private String telefone;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
