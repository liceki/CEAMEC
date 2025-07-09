package com.ifmg.ceamec.dto;
import com.ifmg.ceamec.model.TipoDoacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record DoacaoPostRequestDTO(
        @NotNull(message = "O ID do doador é obrigatório.")
        Long doadorId,

        @NotNull(message = "O tipo da doação é obrigatório.")
        TipoDoacao tipoDoacao,

        @Positive(message = "O valor da doação deve ser positivo.")
        Double quantidade,

        @Size(max = 255, message = "Observações muito longas.")
        String observacoes
) {}
