package com.ifmg.ceamec.dto;

import com.ifmg.ceamec.model.TipoDoacao;

import java.time.LocalDateTime;

public record DoacaoDTO(
        Long id,
        double quantidade,
        String observacoes,
        TipoDoacao tipo,
        Long doadorId
) {}