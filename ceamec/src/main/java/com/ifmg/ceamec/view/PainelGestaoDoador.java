package com.ifmg.ceamec.view;

import com.ifmg.ceamec.dto.DoadorRequestDTO;
import com.ifmg.ceamec.dto.DoadorResumoDTO;
import com.ifmg.ceamec.dto.EnderecoDTO;
import com.ifmg.ceamec.service.DoadorService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.miginfocom.swing.MigLayout;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PainelGestaoDoador extends JPanel {

    private final DoadorService doadorService;

    private JComboBox<String> comboTipoDoador;
    private JLabel labelNomeRazaoSocial;
    private JTextField campoNomeRazaoSocial;
    private JLabel labelCpfCnpj;
    private JTextField campoCpfCnpj;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private PainelEndereco painelEndereco;
    @Getter
    private JButton botaoSalvar;
    private JButton botaoLimpar;

    @PostConstruct
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[][grow,fill]"));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Doador"));

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

        painelEndereco = new PainelEndereco();

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoLimpar = new JButton("Limpar");
        botaoSalvar = new JButton("Salvar Doador");
        actionsPanel.add(botaoLimpar);
        actionsPanel.add(botaoSalvar);

        add(formPanel, BorderLayout.NORTH);
        add(painelEndereco, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);

        comboTipoDoador.addActionListener(e -> atualizarLabels());
        botaoSalvar.addActionListener(e -> salvarDoador());
        botaoLimpar.addActionListener(e -> limparCampos());

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

    public Optional<DoadorResumoDTO> salvarDoadorRetornandoResumo() {
        DoadorRequestDTO dto = criarRequestDTO();
        try {
            DoadorResumoDTO salvo = doadorService.salvarNovoDoador(dto);
            JOptionPane.showMessageDialog(this, "Doador salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            return Optional.of(salvo);
        } catch (ConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Dados inválidos: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar doador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return Optional.empty();
    }

    private void salvarDoador() {
        salvarDoadorRetornandoResumo();
    }

    public void limparCampos() {
        campoNomeRazaoSocial.setText("");
        campoCpfCnpj.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        painelEndereco.limparCampos();
    }

    private DoadorRequestDTO criarRequestDTO() {
        String nome = campoNomeRazaoSocial.getText().trim();
        String cpfOuCnpj = campoCpfCnpj.getText().trim();
        String email = campoEmail.getText().trim();
        String telefone = campoTelefone.getText().trim();
        boolean juridico = "Pessoa Jurídica".equals(comboTipoDoador.getSelectedItem());
        EnderecoDTO enderecoDTO = painelEndereco.getEnderecoDTO();
        return new DoadorRequestDTO(nome, email, telefone, cpfOuCnpj, juridico, enderecoDTO);
    }
}