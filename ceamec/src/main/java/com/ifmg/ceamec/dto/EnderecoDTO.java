package com.ifmg.ceamec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "Número é obrigatório")
        String numero,
        String complemento,
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,
        @NotBlank(message = "Estado (UF) é obrigatório")
        @Size(min = 2, max = 2)
        String estado,
        @NotBlank(message = "CEP é obrigatório")
        String cep
) {}