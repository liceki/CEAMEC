package com.ifmg.ceamec.service;

import com.ifmg.ceamec.dto.DoacaoDTO;
import com.ifmg.ceamec.model.Doacao;
import com.ifmg.ceamec.model.Doador;
import com.ifmg.ceamec.repository.DoacaoRepository;
import com.ifmg.ceamec.repository.DoadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final DoadorRepository doadorRepository;

    public DoacaoDTO registrarNovaDoacao(DoacaoDTO dto) {
        Doacao doacao = new Doacao();
        doacao.setQuantidade(dto.quantidade());
        doacao.setObservacoes(dto.observacoes());
        doacao.setData(LocalDateTime.now());
        doacao.setTipo(dto.tipo());
        Doador doador = doadorRepository.findById(dto.doadorId())
                .orElseThrow(() -> new IllegalArgumentException("Doador não encontrado"));
        doacao.setDoador(doador);
        Doacao salva = doacaoRepository.save(doacao);
        return new DoacaoDTO(
                salva.getId(),
                salva.getQuantidade(),
                salva.getObservacoes(),
                salva.getData(),
                salva.getTipo(),
                doador.getId()
        );
    }
}