package com.ifmg.ceamec.dto;

import com.ifmg.ceamec.model.TipoDoacao;
import java.time.LocalDateTime;

public record DoacaoDTO(
        Long id,
        Double quantidade,
        TipoDoacao tipo,
        String observacoes,
        LocalDateTime data,
        Long doadorId,
        String doadorNome,
        String doadorCpfOuCnpj
) {}