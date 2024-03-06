package com.sorveteria.clientes.service;

import com.sorveteria.clientes.dto.ClienteDto;
import com.sorveteria.clientes.model.Cliente;
import com.sorveteria.clientes.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<ClienteDto> obterTodos(Pageable paginacao) {
        return clienteRepository.findAll(paginacao).map(p -> modelMapper.map(p, ClienteDto.class));
    }

    public ClienteDto obterPorId(Long id) {
        return modelMapper.map(clienteRepository.findById(id).orElseThrow(EntityNotFoundException::new), ClienteDto.class);
    }

    public ClienteDto criaCliente(ClienteDto dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        //set
        clienteRepository.save(cliente);

        return modelMapper.map(cliente, ClienteDto.class);
    }

    public ClienteDto atualizaCliente(Long id, ClienteDto dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        cliente.setId(id);
        cliente = clienteRepository.save(cliente);
        return modelMapper.map(cliente, ClienteDto.class);
    }

    public void excluirCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
