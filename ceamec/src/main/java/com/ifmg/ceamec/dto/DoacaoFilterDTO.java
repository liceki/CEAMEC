package com.ifmg.ceamec.dto;

import com.ifmg.ceamec.model.TipoDoacao;
import java.time.LocalDateTime;
import java.util.List;

public record DoacaoFilterDTO(
        Double quantidadeMin,
        Double quantidadeMax,
        LocalDateTime dataMin,
        LocalDateTime dataMax,
        List<TipoDoacao> tipos,
        List<Long> doadorIds
) {}