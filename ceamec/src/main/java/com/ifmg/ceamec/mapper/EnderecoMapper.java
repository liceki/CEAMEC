package com.ifmg.ceamec.mapper;

import com.ifmg.ceamec.dto.EnderecoDTO;
import com.ifmg.ceamec.model.Endereco;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoDTO toDTO(Endereco endereco);

    Endereco toEntity(EnderecoDTO dto);
}