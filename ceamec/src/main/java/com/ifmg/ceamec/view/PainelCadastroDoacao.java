package com.ifmg.ceamec.view;

import com.ifmg.ceamec.dto.DoacaoDTO;
import com.ifmg.ceamec.dto.DoadorResumoDTO;
import com.ifmg.ceamec.dto.EnderecoDTO;
import com.ifmg.ceamec.dto.DoadorRequestDTO;
import com.ifmg.ceamec.model.TipoDoacao;
import com.ifmg.ceamec.service.DoacaoService;
import com.ifmg.ceamec.service.DoadorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PainelCadastroDoacao extends JPanel {

    private final DoadorService doadorService;
    private final DoacaoService doacaoService;
    private final PainelGestaoDoador painelGestaoDoador;

    // UI Components
    private JTextField campoBuscaDoador;
    private JButton botaoBuscarDoador;
    private JTable tabelaDoadores;
    private DoadorResumoTableModel tabelaModelDoadores;
    private JButton botaoNovoDoador;
    private JLabel labelDoadorSelecionado;

    private JTextField campoQuantidade;
    private JComboBox<TipoDoacao> comboTipoDoacao;
    private JTextArea campoObservacoes;
    private JButton botaoSalvar;
    private JButton botaoLimpar;

    // Dados do doador selecionado
    private DoadorResumoDTO doadorSelecionado;

    @PostConstruct
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de seleção do doador
        JPanel painelBusca = new JPanel(new MigLayout("wrap 3", "[][grow,fill][grow,fill]"));
        painelBusca.setBorder(BorderFactory.createTitledBorder("Seleção de Doador"));

        painelBusca.add(new JLabel("Buscar por Nome:"));
        campoBuscaDoador = new JTextField();
        painelBusca.add(campoBuscaDoador, "growx");
        botaoBuscarDoador = new JButton("Buscar");
        painelBusca.add(botaoBuscarDoador);

        // Tabela de resultados
        tabelaModelDoadores = new DoadorResumoTableModel();
        tabelaDoadores = new JTable(tabelaModelDoadores);
        JScrollPane scrollTabela = new JScrollPane(tabelaDoadores);

        // Botão Novo Doador
        botaoNovoDoador = new JButton("Cadastrar Novo Doador");

        // Label do doador selecionado
        labelDoadorSelecionado = new JLabel("Nenhum doador selecionado.");

        // Painel formulário doação
        JPanel painelForm = new JPanel(new MigLayout("wrap 2", "[][grow,fill]"));
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados da Doação"));

        painelForm.add(new JLabel("Doador Selecionado:"));
        painelForm.add(labelDoadorSelecionado);

        painelForm.add(new JLabel("Quantidade:"));
        campoQuantidade = new JTextField();
        painelForm.add(campoQuantidade, "growx");

        painelForm.add(new JLabel("Tipo de Doação:"));
        comboTipoDoacao = new JComboBox<>(TipoDoacao.values());
        painelForm.add(comboTipoDoacao, "growx");

        painelForm.add(new JLabel("Observações:"));
        campoObservacoes = new JTextArea(3, 20);
        painelForm.add(new JScrollPane(campoObservacoes), "growx");

        // Painel de ações
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoSalvar = new JButton("Salvar Doação");
        botaoLimpar = new JButton("Limpar");
        painelAcoes.add(botaoLimpar);
        painelAcoes.add(botaoSalvar);

        // Monta painel principal
        JPanel painelCentro = new JPanel(new BorderLayout(8, 8));
        painelCentro.add(painelBusca, BorderLayout.NORTH);
        painelCentro.add(scrollTabela, BorderLayout.CENTER);
        JPanel painelBuscaInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBuscaInferior.add(botaoNovoDoador);
        painelCentro.add(painelBuscaInferior, BorderLayout.SOUTH);

        add(painelCentro, BorderLayout.WEST);
        add(painelForm, BorderLayout.CENTER);
        add(painelAcoes, BorderLayout.SOUTH);

        // Eventos
        botaoBuscarDoador.addActionListener(e -> buscarDoadores());
        tabelaDoadores.getSelectionModel().addListSelectionListener(e -> atualizarDoadorSelecionado());
        botaoNovoDoador.addActionListener(e -> abrirDialogCadastroDoador());
        botaoSalvar.addActionListener(e -> salvarDoacao());
        botaoLimpar.addActionListener(e -> limparCampos());
    }

    private void buscarDoadores() {
        String busca = campoBuscaDoador.getText().trim();
        if (busca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome para buscar!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Page<DoadorResumoDTO> page = doadorService.buscarDoadoresPorNome(busca, 0, 15);
        List<DoadorResumoDTO> doadores = page.getContent();
        tabelaModelDoadores.setDoadores(doadores);
        if (doadores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum doador encontrado. Cadastre um novo!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void atualizarDoadorSelecionado() {
        int row = tabelaDoadores.getSelectedRow();
        if (row >= 0) {
            doadorSelecionado = tabelaModelDoadores.getDoador(row);
            labelDoadorSelecionado.setText(doadorSelecionado.nome() + " (" + doadorSelecionado.cpfOuCNPJ() + ")");
        }
    }

    private void abrirDialogCadastroDoador() {
        // PainelGestaoDoador é o formulário de cadastro
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Novo Doador", Dialog.ModalityType.APPLICATION_MODAL);
        PainelGestaoDoador painel = painelGestaoDoador;
        painel.limparCampos(); // Garante que o painel está limpo
        dialog.getContentPane().add(painel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        // Substituir o listener padrão do botão salvar por um que fecha o dialog e retorna o doador cadastrado
        for (ActionListener al : painel.getBotaoSalvar().getActionListeners()) {
            painel.getBotaoSalvar().removeActionListener(al);
        }
        painel.getBotaoSalvar().addActionListener(evt -> {
            Optional<DoadorResumoDTO> novo = painel.salvarDoadorRetornandoResumo();
            if (novo.isPresent()) {
                dialog.dispose();
                doadorSelecionado = novo.get();
                labelDoadorSelecionado.setText(doadorSelecionado.nome() + " (" + doadorSelecionado.cpfOuCNPJ() + ")");
                buscarDoadores(); // Atualiza tabela (opcional)
            }
        });

        dialog.setVisible(true);
    }

    private void salvarDoacao() {
        if (doadorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione ou cadastre um doador!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double quantidade;
        try {
            quantidade = Double.parseDouble(campoQuantidade.getText().replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TipoDoacao tipo = (TipoDoacao) comboTipoDoacao.getSelectedItem();
        String observacoes = campoObservacoes.getText();
        DoacaoDTO dto = new DoacaoDTO(null, quantidade, observacoes, null, tipo, doadorSelecionado.id());
        try {
            doacaoService.registrarNovaDoacao(dto);
            JOptionPane.showMessageDialog(this, "Doação registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar doação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoQuantidade.setText("");
        comboTipoDoacao.setSelectedIndex(0);
        campoObservacoes.setText("");
        labelDoadorSelecionado.setText("Nenhum doador selecionado.");
        tabelaModelDoadores.setDoadores(List.of());
        doadorSelecionado = null;
    }

    // TableModel para exibir doadores
    private static class DoadorResumoTableModel extends AbstractTableModel {
        private List<DoadorResumoDTO> doadores = List.of();
        private final String[] colunas = {"ID", "Nome/Razão Social", "CPF/CNPJ"};

        public void setDoadores(List<DoadorResumoDTO> doadores) {
            this.doadores = doadores;
            fireTableDataChanged();
        }

        public DoadorResumoDTO getDoador(int row) {
            return doadores.get(row);
        }

        @Override public int getRowCount() { return doadores.size(); }
        @Override public int getColumnCount() { return colunas.length; }
        @Override public String getColumnName(int column) { return colunas[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex) {
            DoadorResumoDTO d = doadores.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> d.id();
                case 1 -> d.nome();
                case 2 -> d.cpfOuCNPJ();
                default -> "";
            };
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}