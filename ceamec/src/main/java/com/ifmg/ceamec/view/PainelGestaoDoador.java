package com.ifmg.ceamec.view;

// Pacote: com.ifmg.ceamec.view

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@RequiredArgsConstructor
public class PainelGestaoDoador extends JPanel {

    // --- Componentes da UI ---
    private JComboBox<String> comboTipoDoador;
    private JLabel labelNomeRazaoSocial;
    private JTextField campoNomeRazaoSocial;
    private JLabel labelCpfCnpj;
    private JTextField campoCpfCnpj;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private PainelEndereco painelEndereco;
    private JButton botaoSalvar;
    private JButton botaoLimpar;

    @PostConstruct
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // -- Painel do Formulário --
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[][grow,fill]"));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Doador"));

        // Campos do formulário conforme RF002
        formPanel.add(new JLabel("Tipo de Doador:"));
        comboTipoDoador = new JComboBox<>(new String[]{"Pessoa Física", "Pessoa Jurídica"});
        formPanel.add(comboTipoDoador, "span, growx");

        labelNomeRazaoSocial = new JLabel("Nome Completo:");
        formPanel.add(labelNomeRazaoSocial);
        campoNomeRazaoSocial = new JTextField();
        formPanel.add(campoNomeRazaoSocial, "span, growx");

        labelCpfCnpj = new JLabel("CPF:");
        formPanel.add(labelCpfCnpj);
        campoCpfCnpj = new JTextField();
        formPanel.add(campoCpfCnpj, "span, growx");

        formPanel.add(new JLabel("E-mail:"));
        campoEmail = new JTextField();
        formPanel.add(campoEmail, "span, growx");

        formPanel.add(new JLabel("Telefone:"));
        campoTelefone = new JTextField();
        formPanel.add(campoTelefone, "span, growx");

        // Adiciona o painel de endereço reutilizável
        painelEndereco = new PainelEndereco();

        // -- Painel de Ações --
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoLimpar = new JButton("Limpar");
        botaoSalvar = new JButton("Salvar Doador");
        actionsPanel.add(botaoLimpar);
        actionsPanel.add(botaoSalvar);

        // -- Adicionando painéis ao painel principal --
        add(formPanel, BorderLayout.NORTH);
        add(painelEndereco, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);

        // --- Lógica de Eventos ---
        comboTipoDoador.addActionListener(e -> atualizarLabels());
        botaoSalvar.addActionListener(e -> salvarDoador());
        botaoLimpar.addActionListener(e -> limparCampos());

        // Inicializa os labels
        atualizarLabels();
    }

    private void atualizarLabels() {
        String tipoSelecionado = (String) comboTipoDoador.getSelectedItem();
        if ("Pessoa Física".equals(tipoSelecionado)) {
            labelNomeRazaoSocial.setText("Nome Completo:");
            labelCpfCnpj.setText("CPF:");
        } else {
            labelNomeRazaoSocial.setText("Razão Social:");
            labelCpfCnpj.setText("CNPJ:");
        }
    }

    private void salvarDoador() {
        // 1. Coletar todos os dados dos campos de texto (incluindo os do painelEndereco).
        // 2. Criar os DTOs (DoadorRequestDTO e EnderecoDTO).
        // 3. Chamar o doadorService.salvarNovoDoador(dto) dentro de um bloco try-catch.
        // 4. Capturar ConstraintViolationException para erros de validação e exibir JOptionPane.
        // 5. Capturar outras exceções e exibir JOptionPane.
        // 6. Se tudo der certo, exibir um JOptionPane de sucesso e chamar limparCampos().

        JOptionPane.showMessageDialog(this, "Funcionalidade de Salvar a ser implementada, conectando os campos ao DoadorService.", "Em Desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limparCampos() {
        // Lógica para limpar todos os JTextFields do formulário
        campoNomeRazaoSocial.setText("");
        campoCpfCnpj.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        // Limpar campos de endereço...
    }
}
