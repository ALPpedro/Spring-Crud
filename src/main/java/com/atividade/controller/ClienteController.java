package com.atividade.controller;

import com.atividade.exception.ResourceNotFoundException;
import com.atividade.model.Cliente;
import com.atividade.repository.ClienteCustomRepository;
import com.atividade.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/clientes")
public class ClienteController{

    @Autowired
    private ClienteRepository clienteRepository;
    private final ClienteCustomRepository clienteCustomRepository;

    public ClienteController(ClienteRepository clienteRepository, ClienteCustomRepository clienteCustomRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteCustomRepository = clienteCustomRepository;
    }



    @PostMapping
    public void incluir(@RequestBody Cliente cliente){
        clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente clNovo){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
        if (clNovo.getNome()!=null){
            cliente.setNome(clNovo.getNome());
        }
        if (clNovo.getDataDeNascimento()!= null) {
            cliente.setDataDeNascimento(clNovo.getDataDeNascimento());
        }
        if (clNovo.getRg() != null) {
            cliente.setRg(clNovo.getRg());
        }
        if (clNovo.getCpf() != null) {
            cliente.setCpf(clNovo.getCpf());
        }
        if (clNovo.getTelefone() != null) {
            cliente.setTelefone(clNovo.getTelefone());
        }
        return clienteRepository.save(cliente);
    }
    @GetMapping("/id/{id}")
    public Optional<Cliente> buscarid(@PathVariable Long id){
                  return clienteRepository.findById(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        clienteRepository.deleteById(id);
    }

    @GetMapping()
    public  Page buscarTudao(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "dataDeNascimento", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDeNasciemnto,
            @RequestParam(value = "rg", required = false) String rg,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "telefone", required = false) String telefone,
            Pageable pageable
            ){

        return clienteCustomRepository.find1(id, nome, dataDeNasciemnto, rg, cpf, telefone, pageable);
    }



}
