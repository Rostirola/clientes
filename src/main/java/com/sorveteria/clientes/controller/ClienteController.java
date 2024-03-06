package com.sorveteria.clientes.controller;

import com.sorveteria.clientes.dto.ClienteDto;
import com.sorveteria.clientes.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/Clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<ClienteDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return clienteService.obterTodos(paginacao);
    };

    @GetMapping
    public ResponseEntity<ClienteDto> detalhes(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(clienteService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteDto dto, UriComponentsBuilder uriBuilder) {
        ClienteDto cliente = clienteService.criaCliente(dto);
        URI endereco = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(endereco).body(cliente);
    }

    @PutMapping
    public ResponseEntity<ClienteDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid ClienteDto dto) {
        return ResponseEntity.ok(clienteService.atualizaCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteDto> remover(@PathVariable @NotNull Long id) {
        clienteService.excluirCliente(id);;
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/porta")
    public String retornaPorta(@Value("${local.server.port}") String porta) {
        return String.format("Requisição respondida pela instância executando na porta %s", porta);
    }
}
