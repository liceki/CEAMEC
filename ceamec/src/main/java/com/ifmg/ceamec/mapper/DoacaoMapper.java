package com.ifmg.ceamec.mapper;

import com.ifmg.ceamec.dto.DoacaoPostRequestDTO;
import com.ifmg.ceamec.model.Doacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DoacaoMapper {
    // Ignoramos o doadorId, pois ele será tratado no serviço
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doador", ignore = true)
    @Mapping(target = "usuarioRegistrou", ignore = true)
    @Mapping(target = "dataHora", expression = "java(java.time.LocalDateTime.now())")
    Doacao toEntity(DoacaoPostRequestDTO dto);
}