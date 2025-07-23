package com.ifmg.ceamec.service;

import com.ifmg.ceamec.dto.DoacaoDTO;
import com.ifmg.ceamec.dto.DoacaoFilterDTO;
import com.ifmg.ceamec.mapper.DoacaoMapper;
import com.ifmg.ceamec.model.Doacao;
import com.ifmg.ceamec.model.TipoDoacao;
import com.ifmg.ceamec.repository.DoacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DoacaoServiceTest {

    @Mock
    private DoacaoRepository doacaoRepository;

    @Mock
    private DoacaoMapper doacaoMapper;

    @InjectMocks
    private DoacaoService doacaoService;

    private Doacao doacaoValida;
    private DoacaoDTO doacaoDTOValida;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Cria objetos simulados para as doações
        doacaoValida = new Doacao();
        doacaoValida.setId(1L);
        doacaoValida.setQuantidade(20.0);
        doacaoValida.setTipo(TipoDoacao.DINHEIRO);
        doacaoValida.setData(LocalDateTime.of(2024, 6, 1, 12, 0));
        // Adicione mais campos se necessário

        doacaoDTOValida = new DoacaoDTO(
                1L,
                20.0,
                TipoDoacao.DINHEIRO,
                "Observação",
                LocalDateTime.of(2024, 6, 1, 12, 0),
                1L,
                "José",
                "111.111.111-11"
        );

        // Sempre que o mapper for chamado, devolve o DTO simulado
        when(doacaoMapper.toDTO(any(Doacao.class))).thenReturn(doacaoDTOValida);
    }

    @Test
    void deveFiltrarPorQuantidadeMinimaEMaxima() {
        DoacaoFilterDTO filtro = new DoacaoFilterDTO(10.0, 50.0, null, null, null, null);

        when(doacaoRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(doacaoValida));

        List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

        assertEquals(1, result.size());
        assertEquals(20.0, result.get(0).quantidade());
    }

    @Test
    void deveFiltrarPorPeriodoDeData() {
        LocalDateTime inicio = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 12, 31, 23, 59);
        DoacaoFilterDTO filtro = new DoacaoFilterDTO(null, null, inicio, fim, null, null);

        when(doacaoRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(doacaoValida));

        List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

        assertEquals(1, result.size());
        assertTrue(result.get(0).data().isAfter(inicio.minusDays(1)));
        assertTrue(result.get(0).data().isBefore(fim.plusDays(1)));
    }

    @Test
    void deveFiltrarPorTipoDeDoacao() {
        DoacaoFilterDTO filtro = new DoacaoFilterDTO(null, null, null, null, List.of(TipoDoacao.DINHEIRO), null);

        when(doacaoRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(doacaoValida));

        List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

        assertEquals(1, result.size());
        assertEquals(TipoDoacao.DINHEIRO, result.get(0).tipo());
    }

    @Test
    void deveFiltrarPorIdsDeDoadores() {
        Long doadorId = 1L;
        DoacaoFilterDTO filtro = new DoacaoFilterDTO(null, null, null, null, null, List.of(doadorId));

        // Aqui você pode adicionar lógica de mock para o doador se necessário

        when(doacaoRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(doacaoValida));

        List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

        assertEquals(1, result.size());
        // Adicione mais asserts se necessário para validar o doador
    }

    @Test
    void deveRetornarListaVaziaSeNaoEncontrar() {
        DoacaoFilterDTO filtro = new DoacaoFilterDTO(9999.0, 99999.0, null, null, null, null);

        when(doacaoRepository.findAll(any(Specification.class)))
                .thenReturn(List.of());

        List<DoacaoDTO> result = doacaoService.buscarDoacoes(filtro);

        assertTrue(result.isEmpty());
    }
}