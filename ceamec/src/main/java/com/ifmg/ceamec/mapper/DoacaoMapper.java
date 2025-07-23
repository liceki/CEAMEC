package com.ifmg.ceamec.mapper;

import com.ifmg.ceamec.dto.DoacaoDTO;
import com.ifmg.ceamec.model.Doacao;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoacaoMapper {

    @Mapping(source = "doador.id", target = "doadorId")
    @Mapping(source = "doador.nome", target = "doadorNome")
    @Mapping(target = "doadorCpfOuCnpj", expression = "java(doacao.getDoador() != null ? doacao.getDoador().getDocumento() : null)")
    DoacaoDTO toDTO(Doacao doacao);

    @Mapping(target = "doador", ignore = true)
    Doacao toEntity(DoacaoDTO dto);
}