package com.ifmg.ceamec.service;

import com.ifmg.ceamec.dto.DoadorResumoDTO;
import com.ifmg.ceamec.dto.DoadorRequestDTO;
import com.ifmg.ceamec.model.Doador;
import com.ifmg.ceamec.model.DoadorFisico;
import com.ifmg.ceamec.model.DoadorJuridico;
import com.ifmg.ceamec.repository.DoadorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoadorService {

    private final DoadorRepository doadorRepository;

    public Page<DoadorResumoDTO> buscarDoadoresPorNome(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doadorRepository.findByNomeStartsWithIgnoreCase(nome, pageable)
                .map(this::toResumoDTO);
    }

    public List<DoadorResumoDTO> buscarTodosResumidos() {
        return doadorRepository.findAll()
                .stream()
                .map(this::toResumoDTO)
                .toList();
    }

    public DoadorResumoDTO salvarNovoDoador(@Valid DoadorRequestDTO dto) {
        Doador entity;
        if (dto.juridico()) {
            DoadorJuridico pj = new DoadorJuridico();
            pj.setCnpj(dto.cpfOuCnpj());
            entity = pj;
        } else {
            DoadorFisico pf = new DoadorFisico();
            pf.setCpf(dto.cpfOuCnpj());
            entity = pf;
        }
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setEndereco(dto.endereco().toEntity());
        
        Doador salvo = doadorRepository.save(entity);
        return toResumoDTO(salvo);
    }

    private DoadorResumoDTO toResumoDTO(Doador doador) {
        String cpfOuCNPJ = null;
        if (doador instanceof DoadorFisico pf) {
            cpfOuCNPJ = pf.getCpf();
        } else if (doador instanceof DoadorJuridico pj) {
            cpfOuCNPJ = pj.getCnpj();
        }
        return new DoadorResumoDTO(doador.getId(), doador.getNome(), cpfOuCNPJ);
    }
}