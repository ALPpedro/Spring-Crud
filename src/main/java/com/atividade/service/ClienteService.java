package com.atividade.service;

import com.atividade.dtos.ClienteDto;
import com.atividade.exception.ResourceNotFoundException;
import com.atividade.helper.ExcelHelper;
import com.atividade.model.Cliente;
import com.atividade.repository.ClienteCustomRepository;
import com.atividade.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    @Transactional
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

    String teste;
    public ResponseEntity<?> atualizar(Long id, Cliente clNovo){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Id não encontrado"));
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
        clienteRepository.save(cliente);
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
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


    public List save(MultipartFile file) {
        try {
            List<Cliente> clientes = ExcelHelper.excelToTutorials(file.getInputStream());

            return clientes;

        } catch (IOException e) {
            throw new ResourceNotFoundException("Falha ao armazenar dados do Excel: " + e.getMessage());
        }
    }
    public List<Cliente> getAllTutorials() {
        return clienteRepository.findAll();
    }






}
