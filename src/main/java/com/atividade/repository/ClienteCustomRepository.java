package com.atividade.repository;

import com.atividade.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ClienteCustomRepository {

    private final EntityManager em;

    public ClienteCustomRepository(EntityManager em) {
        this.em = em;
    }

    public Page<Cliente> find(Pageable pageable, Long id, String nome, LocalDate dataDeNascimento, String rg, String cpf, String telefone){
        String query = "select C from Cliente as C ";
        String condicao = "where";

        if (id != null){
            query += condicao+" C.id = :id";
            condicao =" and ";
        }
        if (nome != null){
            query += condicao+" C.nome = :nome";
            condicao = " and ";
        }

        if (dataDeNascimento != null){
            query += condicao+" C.dataDeNascimento = :dataDeNascimento";
            condicao = " and ";
        }

        if (rg !=null){
            query += condicao+" C.rg = :rg";
            condicao =" and ";
        }

        if (cpf != null){
            query += condicao+" C.cpf = :cpf";
            condicao = " and ";
        }

        if (telefone != null){
            query += condicao+" C.telefone = :telefone";
            condicao = " and ";
        }

        var q = em.createQuery(query, Cliente.class);


        if (id != null){
           q.setParameter("id", id);
        }
        if (nome != null){
            q.setParameter("nome", nome);
        }

        if (dataDeNascimento != null){
            q.setParameter("dataDeNascimento", dataDeNascimento);
        }

        if (rg !=null){
            q.setParameter("rg", rg);
        }

        if (cpf != null){
            q.setParameter("cpf", cpf);
        }

        if (telefone != null){
            q.setParameter("telefone", telefone);
        }

        Page<Cliente> resultList = new PageImpl<Cliente>(q.getResultList());
        return resultList;

    }
}
