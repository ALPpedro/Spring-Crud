package com.atividade.repository;

import com.atividade.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    @Query(value = "select u from Cliente u where trim(u.nome) like %?1%")
    List<Cliente> buscarPorNome(String nome, Pageable pageable);

    @Query(value = "select u from Cliente u where trim(u.telefone) like %?1%")
    List<Cliente> buscarPorTelefone(int telefone, Pageable pageable);

    @Query(value = " select u from Cliente u where u.dataDeNascimento like ?1")
    List<Cliente> buscarPorDataDeNascimento(LocalDate dataDeNascimento, Pageable pageable);

    Page<Cliente> findAll(Pageable sort);


}
