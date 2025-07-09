package com.ifmg.ceamec.dto;

import com.ifmg.ceamec.model.TipoDoacao;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record DoadorRequestDTO(
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank String cpfOuCnpj,
        boolean juridico, // true: PJ, false: PF
        EnderecoDTO endereco

) {
}
