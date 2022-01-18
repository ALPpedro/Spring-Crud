package com.atividade.controller;

import com.atividade.exception.ResourceNotFoundException;
import com.atividade.model.Cliente;
import com.atividade.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public Page<Cliente> listar(Pageable pageable){
        return clienteRepository.findAll(pageable);
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
        if (clNovo.getTelefone() != 0) {
            cliente.setTelefone(clNovo.getTelefone());
        }
        return clienteRepository.save(cliente);
    }

    @GetMapping("/clientenome/{nome}")
    public List<Cliente>listaNome(@PathVariable String nome, Pageable pageable){
        return clienteRepository.buscarPorNome(nome.trim(), pageable);
    }

    @GetMapping("/clientetelefone/{telefone}")
    public  List<Cliente>listaTelefone(@PathVariable int telefone, Pageable pageable){
        return clienteRepository.buscarPorTelefone(telefone, pageable);
    }

    @GetMapping("/clientedatanascimento/{dataDeNascimento}")
    public List<Cliente> listarPorData(@PathVariable String dataDeNascimento, Pageable pageable){
        LocalDate data = LocalDate.parse(dataDeNascimento);
        return  clienteRepository.buscarPorDataDeNascimento(data, pageable);
    }

}
