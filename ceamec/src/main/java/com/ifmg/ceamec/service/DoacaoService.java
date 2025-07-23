package com.ifmg.ceamec.service;

import com.ifmg.ceamec.dto.DoacaoDTO;
import com.ifmg.ceamec.dto.DoacaoFilterDTO;
import com.ifmg.ceamec.mapper.DoacaoMapper;
import com.ifmg.ceamec.model.Doacao;
import com.ifmg.ceamec.model.Doador;
import com.ifmg.ceamec.repository.DoacaoRepository;
import com.ifmg.ceamec.repository.DoacaoSpecification;
import com.ifmg.ceamec.repository.DoadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final DoadorRepository doadorRepository;
    private final DoacaoMapper doacaoMapper;

    public DoacaoDTO registrarNovaDoacao(DoacaoDTO dto) {
        // Mapeia o DTO para entidade
        Doacao doacao = doacaoMapper.toEntity(dto);

        // Associa o doador se necessário
        if (dto.doadorId() != null) {
            Doador doador = doadorRepository.findById(dto.doadorId())
                    .orElseThrow(() -> new IllegalArgumentException("Doador não encontrado com ID: " + dto.doadorId()));
            doacao.setDoador(doador);
        }

        // Salva a nova doação
        Doacao salva = doacaoRepository.save(doacao);

        // Retorna o DTO da entidade salva (agora com ID e dados atualizados)
        return doacaoMapper.toDTO(salva);
    }

    public List<DoacaoDTO> buscarDoacoes(DoacaoFilterDTO filtro) {
        List<Doacao> doacoes = doacaoRepository.findAll(DoacaoSpecification.filterBy(filtro));
        return doacoes.stream()
                .map(doacaoMapper::toDTO)
                .toList();
    }



}