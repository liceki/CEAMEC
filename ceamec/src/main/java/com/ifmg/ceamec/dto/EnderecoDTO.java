package com.ifmg.ceamec.dto;


import com.ifmg.ceamec.model.Endereco;

public record EnderecoDTO(
        String logradouro,
        String bairro,
        String numero,
        String cidade,
        String estado,
        String cep,
        String complemento
) {
    public Endereco toEntity() {
        return null;
    }
}