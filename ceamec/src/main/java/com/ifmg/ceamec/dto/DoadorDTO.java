package com.ifmg.ceamec.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record DoadorDTO(
        Long id,
        @NotBlank(message = "Nome ou Razão Social não pode ser vazio")
        String nome,
        @NotNull(message = "Tipo de doador é obrigatório")
        String tipoDoador, // "Pessoa Física" ou "Pessoa Jurídica"
        @NotBlank(message = "CPF ou CNPJ é obrigatório")
        String documento, // CPF ou CNPJ
        @Email(message = "Formato de e-mail inválido")
        @NotBlank
        String email,
        String telefone,
        @NotNull @Valid // Valida o objeto EnderecoDTO em cascata
        EnderecoDTO endereco
) {}
