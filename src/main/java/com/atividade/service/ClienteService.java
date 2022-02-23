package com.atividade.service;

import com.atividade.dtos.ClienteDto;
import com.atividade.exception.ResourceNotFoundException;
import com.atividade.model.Cliente;
import com.atividade.repository.ClienteCustomRepository;
import com.atividade.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {

    final ClienteCustomRepository clienteCustomRepository;
    final ClienteRepository clienteRepository;


    public ClienteService(ClienteCustomRepository clienteCustomRepository, ClienteRepository clienteRepository) {
        this.clienteCustomRepository = clienteCustomRepository;
        this.clienteRepository = clienteRepository;
    }

    public ResponseEntity<Object> salvar(ClienteDto clientedto){
        var cliente = new Cliente();
        BeanUtils.copyProperties(clientedto, cliente);
        clienteRepository.save(cliente);
        return ResponseEntity.ok(true);
    }

    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fildName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fildName, errorMessage);
                }
        );

        return errors;
    }

    public Cliente atualizar(Long id, Cliente clNovo){
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

    public void deleteById(Long id) {
        var teste =clienteRepository.findById(id);
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> findById(Long id){
        return clienteRepository.findById(id);
    }

    public Page find1(Long id, String nome, LocalDate dataDeNasciemnto, String rg, String cpf, String telefone, Pageable pageable) {
        return clienteCustomRepository.find1(id, nome, dataDeNasciemnto, rg, cpf, telefone, pageable);
    }
}
