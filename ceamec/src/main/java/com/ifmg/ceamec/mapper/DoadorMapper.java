package com.ifmg.ceamec.mapper;

import com.ifmg.ceamec.dto.DoadorResumoDTO;
import com.ifmg.ceamec.dto.DoadorRequestDTO;
import com.ifmg.ceamec.model.Doador;
import com.ifmg.ceamec.model.DoadorFisico;
import com.ifmg.ceamec.model.DoadorJuridico;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoadorMapper {

    @Mapping(target = "cpfOuCnpj", expression = "java(getCpfOuCnpj(doador))")
    DoadorResumoDTO toResumoDTO(Doador doador);

    // Mapeia o DTO para entidade, decide o tipo de doador por campo "juridico"
    default Doador toEntity(DoadorRequestDTO dto) {
        if (dto.juridico()) {
            DoadorJuridico pj = new DoadorJuridico();
            pj.setCnpj(dto.cpfOuCnpj());
            pj.setNome(dto.nome());
            pj.setEmail(dto.email());
            pj.setTelefone(dto.telefone());
            pj.setEndereco(dto.endereco().toEntity());
            return pj;
        } else {
            DoadorFisico pf = new DoadorFisico();
            pf.setCpf(dto.cpfOuCnpj());
            pf.setNome(dto.nome());
            pf.setEmail(dto.email());
            pf.setTelefone(dto.telefone());
            pf.setEndereco(dto.endereco().toEntity());
            return pf;
        }
    }

    // Auxiliar para extrair CPF ou CNPJ da entidade
    default String getCpfOuCnpj(Doador doador) {
        if (doador instanceof DoadorFisico pf) {
            return pf.getCpf();
        } else if (doador instanceof DoadorJuridico pj) {
            return pj.getCnpj();
        }
        return null;
    }
}