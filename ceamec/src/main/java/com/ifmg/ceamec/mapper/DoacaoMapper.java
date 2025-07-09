package com.ifmg.ceamec.mapper;

import com.ifmg.ceamec.dto.DoacaoDTO;
import com.ifmg.ceamec.model.Doacao;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoacaoMapper {

    @Mapping(source = "doador.id", target = "doadorId")
    DoacaoDTO toDTO(Doacao doacao);

    @Mapping(target = "doador", ignore = true)
    Doacao toEntity(DoacaoDTO dto);
}