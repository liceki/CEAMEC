package com.ifmg.ceamec.dto;

public record DoadorResumoDTO(// enviado para tela de seleção de doador
        Long id,
        String nome, // Representará o Nome Completo ou a Razão Social
        String cpfOuCNPJ // Representará o CPF ou o CNPJ
) {}