package com.ifmg.ceamec.view;

import com.ifmg.ceamec.dto.EnderecoDTO;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;

public class PainelEndereco extends JPanel {

    private JTextField campoLogradouro;
    private JTextField campoBairro;
    private JTextField campoNumero;
    private JTextField campoCidade;
    private JTextField campoEstado;
    private JTextField campoCep;
    private JTextField campoComplemento;

    public PainelEndereco() {
        setLayout(new MigLayout("wrap 2", "[][grow,fill]"));
        setBorder(BorderFactory.createTitledBorder("Endereço"));

        add(new JLabel("Logradouro:"));
        campoLogradouro = new JTextField();
        add(campoLogradouro, "growx");

        add(new JLabel("Bairro:"));
        campoBairro = new JTextField();
        add(campoBairro, "growx");

        add(new JLabel("Número:"));
        campoNumero = new JTextField();
        add(campoNumero, "growx");

        add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        add(campoCidade, "growx");

        add(new JLabel("Estado:"));
        campoEstado = new JTextField();
        add(campoEstado, "growx");

        add(new JLabel("CEP:"));
        campoCep = new JTextField();
        add(campoCep, "growx");

        add(new JLabel("Complemento:"));
        campoComplemento = new JTextField();
        add(campoComplemento, "growx");
    }

    public EnderecoDTO getEnderecoDTO() {
        return new EnderecoDTO(
                campoLogradouro.getText().trim(),
                campoBairro.getText().trim(),
                campoNumero.getText().trim(),
                campoCidade.getText().trim(),
                campoEstado.getText().trim(),
                campoCep.getText().trim(),
                campoComplemento.getText().trim()
        );
    }

    public void limparCampos() {
        campoLogradouro.setText("");
        campoBairro.setText("");
        campoNumero.setText("");
        campoCidade.setText("");
        campoEstado.setText("");
        campoCep.setText("");
        campoComplemento.setText("");
    }
}