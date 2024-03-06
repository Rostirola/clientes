package com.sorveteria.clientes.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClienteDto {

    private Long id;
    private String nome;
    private String sobrenome;
}
