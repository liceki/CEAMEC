package com.ifmg.ceamec.view;

// Pacote: com.ifmg.ceamec.view.panels

import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class PainelEndereco extends JPanel {
    private JTextField campoLogradouro;
    private JTextField campoNumero;
    private JTextField campoComplemento;
    private JTextField campoBairro;
    private JTextField campoCidade;
    private JTextField campoEstado;
    private JTextField campoCep;

    public PainelEndereco() {
        // "wrap 2" = quebra a linha a cada 2 componentes (label + campo)
        // "[grow, fill]" = faz as colunas dos campos de texto crescerem para preencher o espaço
        setLayout(new MigLayout("wrap 2", "[][grow,fill]"));
        setBorder(BorderFactory.createTitledBorder("Endereço"));

        add(new JLabel("Logradouro:"));
        campoLogradouro = new JTextField();
        add(campoLogradouro, "span, growx");

        add(new JLabel("Número:"));
        campoNumero = new JTextField();
        add(campoNumero);

        add(new JLabel("Complemento:"));
        campoComplemento = new JTextField();
        add(campoComplemento);

        add(new JLabel("Bairro:"));
        campoBairro = new JTextField();
        add(campoBairro);

        add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        add(campoCidade);

        add(new JLabel("Estado (UF):"));
        campoEstado = new JTextField();
        add(campoEstado);

        add(new JLabel("CEP:"));
        campoCep = new JTextField();
        add(campoCep);
    }
}
