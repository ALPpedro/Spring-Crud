package com.atividade.controller;

import com.atividade.dtos.ClienteDto;
import com.atividade.exception.ResourceNotFoundException;
import com.atividade.model.Cliente;
import com.atividade.repository.ClienteCustomRepository;
import com.atividade.repository.ClienteRepository;
import com.atividade.service.ClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/clientes")
public class ClienteController{

    @Autowired
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping
    public ResponseEntity<Object> incluir(@RequestBody @Valid ClienteDto clientedto){
        return clienteService.salvar(clientedto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex){
        return clienteService.handleValidationException(ex);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente clNovo){
        return clienteService.atualizar(id, clNovo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        clienteService.deleteById(id);
    }

    @GetMapping("/id/{id}")
    public Optional<Cliente> buscarid(@PathVariable Long id){
       return clienteService.findById(id);
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

        return clienteService.find1(id, nome, dataDeNasciemnto, rg, cpf, telefone, pageable);
    }



}
