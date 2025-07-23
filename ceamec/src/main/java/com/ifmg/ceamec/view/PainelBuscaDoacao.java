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
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class PainelBuscaDoacao extends JPanel {

    private final DoacaoService doacaoService;
    private final DoadorService doadorService;

    private JSlider sliderQtdMin, sliderQtdMax;
    private JComboBox<TipoDoacao> comboTipos;
    private JTextField campoBuscaDoador;
    private JButton btnAdicionarDoador, btnRemoverDoador;
    private DefaultListModel<DoadorResumoDTO> modeloDoadoresSelecionados;
    private JList<DoadorResumoDTO> listaDoadoresSelecionados;
    private JTable tabelaResultados;
    private JXDatePicker pickerDataInicial, pickerDataFinal;
    private JPopupMenu popupSugestaoDoadores;
    private JList<DoadorResumoDTO> listaSugestaoDoadores;

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
        comboTipos.setSelectedItem(null);
        painelFiltros.add(comboTipos, "growx, spanx 5");

        // Filtro e seleção de doadores
        painelFiltros.add(new JLabel("Doadores (Nome):"));
        campoBuscaDoador = new JTextField();
        painelFiltros.add(campoBuscaDoador, "growx, spanx 3");

        btnAdicionarDoador = new JButton("Adicionar");
        painelFiltros.add(btnAdicionarDoador, "spanx 1");

        btnRemoverDoador = new JButton("Remover Selecionado");
        painelFiltros.add(btnRemoverDoador, "spanx 1");

        modeloDoadoresSelecionados = new DefaultListModel<>();
        listaDoadoresSelecionados = new JList<>(modeloDoadoresSelecionados);
        listaDoadoresSelecionados.setVisibleRowCount(3);
        listaDoadoresSelecionados.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.nome() + " (" + value.cpfOuCnpj() + ")");
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(new Color(184, 207, 229));
            }
            return label;
        });
        JScrollPane scrollListaDoadores = new JScrollPane(listaDoadoresSelecionados);
        painelFiltros.add(new JLabel("Selecionados:"));
        painelFiltros.add(scrollListaDoadores, "spanx 5, growx, h 50!");

        // Resultados
        tabelaResultados = new JTable();
        JScrollPane scroll = new JScrollPane(tabelaResultados);
        add(painelFiltros, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Popup de sugestão (autocomplete)
        popupSugestaoDoadores = new JPopupMenu();
        listaSugestaoDoadores = new JList<>();
        listaSugestaoDoadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaSugestaoDoadores.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.nome() + " (" + value.cpfOuCnpj() + ")");
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(new Color(184, 207, 229));
            }
            return label;
        });
        JScrollPane scrollSugestao = new JScrollPane(listaSugestaoDoadores);
        scrollSugestao.setPreferredSize(new Dimension(350, 100));
        popupSugestaoDoadores.add(scrollSugestao);

        // Listeners
        campoBuscaDoador.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { mostrarSugestoes(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { mostrarSugestoes(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { mostrarSugestoes(); }
        });


        listaSugestaoDoadores.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                if (!e.getOppositeComponent().equals(campoBuscaDoador)) {
                    SwingUtilities.invokeLater(() -> popupSugestaoDoadores.setVisible(false));
                }
            }
        });

        btnAdicionarDoador.addActionListener(e -> adicionarDoadorSelecionado());

        listaSugestaoDoadores.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    adicionarDoadorSelecionado();
                }
            }
        });

        listaSugestaoDoadores.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    adicionarDoadorSelecionado();
                }
            }
        });

        btnRemoverDoador.addActionListener(e -> {
            List<DoadorResumoDTO> selecionados = listaDoadoresSelecionados.getSelectedValuesList();
            for (DoadorResumoDTO d : selecionados) {
                modeloDoadoresSelecionados.removeElement(d);
            }
        });

        // Botões de busca
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtros");
        painelFiltros.add(btnBuscar, "spanx 2, right");
        painelFiltros.add(btnLimpar, "spanx 4, left");

        btnBuscar.addActionListener(e -> buscarDoacoes());
        btnLimpar.addActionListener(e -> limparFiltros());
    }

    private void mostrarSugestoes() {
        String nome = campoBuscaDoador.getText().trim();
        if (nome.length() < 2) {
            popupSugestaoDoadores.setVisible(false);
            return;
        }
        List<DoadorResumoDTO> sugestoes = doadorService.buscarDoadoresPorNome(nome, 0, 100).stream().toList();
        if (sugestoes.isEmpty()) {
            popupSugestaoDoadores.setVisible(false);
            return;
        }
        DefaultListModel<DoadorResumoDTO> modelo = new DefaultListModel<>();
        for (DoadorResumoDTO d : sugestoes) {
            modelo.addElement(d);
        }
        listaSugestaoDoadores.setModel(modelo);
        listaSugestaoDoadores.setSelectedIndex(0);

        // Mostrar popup logo abaixo do campo
        try {
            Rectangle rect = campoBuscaDoador.getBounds();
            popupSugestaoDoadores.setPreferredSize(new Dimension(rect.width, 100));
            popupSugestaoDoadores.show(campoBuscaDoador, 0, campoBuscaDoador.getHeight());
        } catch (Exception ex) {
            popupSugestaoDoadores.show(campoBuscaDoador, 0, campoBuscaDoador.getHeight());
        }
        // Importante: dar o foco para permitir navegação via teclado
        listaSugestaoDoadores.requestFocusInWindow();
    }

    private void adicionarDoadorSelecionado() {
        DoadorResumoDTO selecionado = listaSugestaoDoadores.getSelectedValue();
        if (selecionado != null && !contidoNoModelo(selecionado)) {
            modeloDoadoresSelecionados.addElement(selecionado);
        }
        campoBuscaDoador.setText("");
        popupSugestaoDoadores.setVisible(false);
    }

    private boolean contidoNoModelo(DoadorResumoDTO doador) {
        for (int i = 0; i < modeloDoadoresSelecionados.size(); i++) {
            if (Objects.equals(modeloDoadoresSelecionados.get(i).id(), doador.id())) return true;
        }
        return false;
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

        List<Long> doadorIds = modeloDoadoresSelecionados.isEmpty() ? null :
                Collections.list(modeloDoadoresSelecionados.elements()).stream()
                        .map(DoadorResumoDTO::id).collect(Collectors.toList());

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
                    d.doadorCpfOuCnpj()
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
        modeloDoadoresSelecionados.clear();
        tabelaResultados.setModel(new DefaultTableModel(
                new String[]{"ID", "Quantidade", "Tipo", "Observações", "Data", "Doador Nome", "CPF/CNPJ"}, 0));
    }
}