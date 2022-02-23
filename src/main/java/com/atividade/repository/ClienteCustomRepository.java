package com.atividade.repository;

import com.atividade.model.Cliente;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteCustomRepository{

    private final EntityManager em;

    public ClienteCustomRepository(EntityManager em) {
        super();
        this.em = em;
    }

    public List<Cliente> find(Pageable pageable, Long id, String nome, LocalDate dataDeNascimento, String rg, String cpf, String telefone){
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

        return q.getResultList();

    }


    public List teste(Long page, Long size){
        if (size == null ){
            size = 5L;
        }
        if (page == null){
            page = 1L;
        }
        String countQ = "Select count (c.id) from Cliente c";
        var countQuery = em.createQuery(countQ);
        Long countResults = (Long) countQuery.getSingleResult();
        int lastPageNumber;
        if (countResults%size==0 )
        {
            lastPageNumber = (int) (countResults/size);
        }
        else {
            lastPageNumber = (int) ((countResults/size)+1);
        }

        var query1 =  em.createQuery("From Cliente");
        query1.setFirstResult((int) ((page -1) * size));
        query1.setMaxResults(Math.toIntExact((size)));
        List teste = new ArrayList<>();
        teste.add("Total de Elementos: "+countResults);
        teste.add("Total de Páginas: "+lastPageNumber);
        teste.add("Página Atual: "+ page);
        teste.add("Total de elementos nessa página: "+ size);

        return teste;
    }

    public Page find1(Long id, String nome, LocalDate dataDeNascimento, String rg, String cpf, String telefone, Pageable pageable){

        String query = "select C from Cliente as C ";
        String countQ = "Select Count(id) from Cliente as c ";
        String condicao = "where";

        if (id != null){
            query += condicao+" C.id = :id";
            countQ += condicao+" c.id = :id";
            condicao =" and ";
        }
        if (nome != null){
            query += condicao+" C.nome LIKE :nome";
            countQ += condicao+" c.nome LIKE :nome";
            condicao = " and ";
        }

        if (dataDeNascimento != null){
            query += condicao+" C.dataDeNascimento LIKE :dataDeNascimento";
            countQ += condicao+" c.dataDeNascimento LIKE :dataDeNascimento";
            condicao = " and ";
        }

        if (rg !=null){
            query += condicao+" C.rg LIKE :rg";
            countQ += condicao+" c.rg LIKE :rg";
            condicao =" and ";
        }

        if (cpf != null){
            query += condicao+" C.cpf LIKE :cpf";
            countQ += condicao+" c.cpf LIKE :cpf";
            condicao = " and ";
        }

        if (telefone != null){
            query += condicao+" C.telefone LIKE :telefone";
            countQ += condicao+" c.telefone LIKE :telefone";
            condicao = " and ";
        }

        var q = em.createQuery(query, Cliente.class);
        var countQuery = em.createQuery(countQ);


        if (id != null){
            q.setParameter("id", id);
            countQuery.setParameter("id", id);
        }
        if (nome != null){
            q.setParameter("nome", "%"+nome+"%");
            countQuery.setParameter("nome", "%"+nome+"%");
        }

        if (dataDeNascimento != null){
            q.setParameter("dataDeNascimento", dataDeNascimento);
            countQuery.setParameter("dataDeNascimento", dataDeNascimento);
        }

        if (rg !=null){
            q.setParameter("rg", "%"+rg+"%");
            countQuery.setParameter("rg", "%"+rg+"%");
        }

        if (cpf != null){
            q.setParameter("cpf", "%"+cpf+"%");
            countQuery.setParameter("cpf", "%"+cpf+"%");
        }

        if (telefone != null){
            q.setParameter("telefone", "%"+telefone+"%");
            countQuery.setParameter("telefone", "%"+telefone+"%");
        }

        q.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List users = q.getResultList();




        Long countResults = (Long) countQuery.getSingleResult();


            Page teste = new Paginacao(users, pageable, countResults);
        return teste;

    }
}
