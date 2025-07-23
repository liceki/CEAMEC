package com.ifmg.ceamec.view;

import com.ifmg.ceamec.dto.DoacaoDTO;
import com.ifmg.ceamec.dto.DoacaoFilterDTO;
import com.ifmg.ceamec.dto.DoadorResumoDTO;
import com.ifmg.ceamec.model.TipoDoacao;
import com.ifmg.ceamec.service.DoacaoService;
import com.ifmg.ceamec.service.DoadorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXDatePicker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.*;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class PainelBuscaDoacao extends JPanel {

    private final DoacaoService doacaoService;
    private final DoadorService doadorService;

    // Filtros
    private JSlider sliderQtdMin, sliderQtdMax;
    private JComboBox<TipoDoacao> comboTipos;
    private JTextField campoBuscaDoador;
    private JButton btnBuscar, btnLimpar;
    private JTable tabelaResultados;
    private JXDatePicker pickerDataInicial, pickerDataFinal;
    private JList<String> listaSugestaoDoadores;
    private JScrollPane scrollSugestaoDoadores;

    // Estado interno para autocomplete do doador
    private List<DoadorResumoDTO> sugestaoDoadores = List.of();
    private DoadorResumoDTO doadorSelecionado = null;

    @PostConstruct
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Buscar Doações"));

        JPanel painelFiltros = new JPanel(new MigLayout("wrap 6", "[][grow,fill][][][grow,fill][]", "[]10[]10[]"));

        // Quantidade
        painelFiltros.add(new JLabel("Qtd. Mínima:"));
        sliderQtdMin = new JSlider(0, 1000, 0);
        sliderQtdMin.setMajorTickSpacing(250);
        sliderQtdMin.setPaintTicks(true);
        sliderQtdMin.setPaintLabels(true);
        painelFiltros.add(sliderQtdMin, "growx");

        painelFiltros.add(new JLabel("Qtd. Máxima:"));
        sliderQtdMax = new JSlider(0, 1000, 1000);
        sliderQtdMax.setMajorTickSpacing(250);
        sliderQtdMax.setPaintTicks(true);
        sliderQtdMax.setPaintLabels(true);
        painelFiltros.add(sliderQtdMax, "growx, spanx 3");

        // Datas
        painelFiltros.add(new JLabel("Data Inicial:"));
        pickerDataInicial = new JXDatePicker();
        pickerDataInicial.setFormats("dd/MM/yyyy");
        pickerDataInicial.setDate(java.sql.Date.valueOf(LocalDate.now().minusYears(1)));
        painelFiltros.add(pickerDataInicial, "growx");

        painelFiltros.add(new JLabel("Data Final:"));
        pickerDataFinal = new JXDatePicker();
        pickerDataFinal.setFormats("dd/MM/yyyy");
        pickerDataFinal.setDate(new Date());
        painelFiltros.add(pickerDataFinal, "growx, spanx 3");

        // Tipo de doação
        painelFiltros.add(new JLabel("Tipo de Doação:"));
        comboTipos = new JComboBox<>(TipoDoacao.values());
        comboTipos.setSelectedItem(null); // sem filtro padrão
        painelFiltros.add(comboTipos, "growx, spanx 5");

        // Busca de doador
        painelFiltros.add(new JLabel("Doador (nome/CPF/CNPJ):"));
        campoBuscaDoador = new JTextField();
        painelFiltros.add(campoBuscaDoador, "growx, spanx 5");

        // Sugestão de doadores (autocomplete)
        listaSugestaoDoadores = new JList<>();
        listaSugestaoDoadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollSugestaoDoadores = new JScrollPane(listaSugestaoDoadores);
        scrollSugestaoDoadores.setVisible(false);
        painelFiltros.add(scrollSugestaoDoadores, "skip 1, spanx 5, growx, h 60!");

        // Botões
        btnBuscar = new JButton("Buscar");
        btnLimpar = new JButton("Limpar Filtros");
        painelFiltros.add(btnBuscar, "spanx 2, right");
        painelFiltros.add(btnLimpar, "spanx 4, left");

        add(painelFiltros, BorderLayout.NORTH);

        // Resultados
        tabelaResultados = new JTable();
        JScrollPane scroll = new JScrollPane(tabelaResultados);
        add(scroll, BorderLayout.CENTER);

        // Listeners
        btnBuscar.addActionListener(e -> buscarDoacoes());
        btnLimpar.addActionListener(e -> limparFiltros());

        campoBuscaDoador.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { buscarSugestao(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { buscarSugestao(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { buscarSugestao(); }
        });
        listaSugestaoDoadores.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaSugestaoDoadores.getSelectedIndex() != -1) {
                String selecionado = listaSugestaoDoadores.getSelectedValue();
                doadorSelecionado = sugestaoDoadores.get(listaSugestaoDoadores.getSelectedIndex());
                campoBuscaDoador.setText(selecionado);
                scrollSugestaoDoadores.setVisible(false);
            }
        });
        campoBuscaDoador.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                scrollSugestaoDoadores.setVisible(false);
            }
        });
    }

    private void buscarSugestao() {
        String termo = campoBuscaDoador.getText().trim();
        doadorSelecionado = null;
        if (termo.length() < 2) {
            scrollSugestaoDoadores.setVisible(false);
            return;
        }
        sugestaoDoadores = doadorService.buscarDoadoresPorNome(termo, 0, 100).stream().toList();
        if (sugestaoDoadores.isEmpty()) {
            scrollSugestaoDoadores.setVisible(false);
            return;
        }
        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (DoadorResumoDTO d : sugestaoDoadores) {
            modelo.addElement(d.nome() + " (" + d.cpfOuCnpj() + ")");
        }
        listaSugestaoDoadores.setModel(modelo);
        scrollSugestaoDoadores.setVisible(true);
        listaSugestaoDoadores.setSelectedIndex(0);
    }

    private void buscarDoacoes() {
        Double qtdMin = (double) sliderQtdMin.getValue();
        Double qtdMax = (double) sliderQtdMax.getValue();

        Date dataIni = pickerDataInicial.getDate();
        Date dataFim = pickerDataFinal.getDate();
        LocalDateTime dataMin = dataIni == null ? null : LocalDateTime.ofInstant(dataIni.toInstant(), java.time.ZoneId.systemDefault()).with(LocalTime.MIN);
        LocalDateTime dataMax = dataFim == null ? null : LocalDateTime.ofInstant(dataFim.toInstant(), java.time.ZoneId.systemDefault()).with(LocalTime.MAX);

        TipoDoacao tipo = (TipoDoacao) comboTipos.getSelectedItem();
        List<TipoDoacao> tiposSelecionados = tipo == null ? null : List.of(tipo);

        List<Long> doadorIds = (doadorSelecionado != null) ? List.of(doadorSelecionado.id()) : null;

        DoacaoFilterDTO filtro = new DoacaoFilterDTO(
                qtdMin, qtdMax,
                dataMin, dataMax,
                tiposSelecionados,
                doadorIds
        );

        List<DoacaoDTO> resultados = doacaoService.buscarDoacoes(filtro);
        atualizarTabela(resultados);
    }

    private void atualizarTabela(List<DoacaoDTO> doacoes) {
        String[] colunas = {"ID", "Quantidade", "Tipo", "Observações", "Data", "Doador Nome", "CPF/CNPJ"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (DoacaoDTO d : doacoes) {
            model.addRow(new Object[]{
                    d.id(), d.quantidade(),
                    d.tipo() != null ? d.tipo().name().replace('_', ' ') : "",
                    d.observacoes(),
                    d.data() != null ? sdf.format(java.sql.Timestamp.valueOf(d.data())) : "",
                    d.doadorNome(),
                    d.doadorCpfOuCnpj() // ajuste o DTO para ter esse campo!
            });
        }
        tabelaResultados.setModel(model);
    }

    private void limparFiltros() {
        sliderQtdMin.setValue(0);
        sliderQtdMax.setValue(1000);
        pickerDataInicial.setDate(java.sql.Date.valueOf(LocalDate.now().minusYears(1)));
        pickerDataFinal.setDate(new Date());
        comboTipos.setSelectedItem(null);
        campoBuscaDoador.setText("");
        doadorSelecionado = null;
        tabelaResultados.setModel(new DefaultTableModel(
                new String[]{"ID", "Quantidade", "Tipo", "Observações", "Data", "Doador Nome", "CPF/CNPJ"}, 0));
    }
}