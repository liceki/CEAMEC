package com.ifmg.ceamec.dto;

public record DoadorResumoDTO(Long id, String nome, String cpfOuCnpj) {
    @Override
    public String toString() { return nome + " (" + cpfOuCnpj + ")"; }
}