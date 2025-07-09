package com.ifmg.ceamec.view;

import com.ifmg.ceamec.dto.EnderecoDTO;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class PainelEndereco extends JPanel {

    private JTextField campoCep;
    private JTextField campoLogradouro;
    private JTextField campoBairro;
    private JTextField campoNumero;
    private JTextField campoCidade;
    private JTextField campoEstado;
    private JTextField campoComplemento;

    public PainelEndereco() {
        setLayout(new MigLayout("wrap 2", "[][grow,fill]"));
        setBorder(BorderFactory.createTitledBorder("Endereço"));

        // CEP primeiro
        add(new JLabel("CEP:"));
        campoCep = new JTextField();
        add(campoCep, "growx");

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

        add(new JLabel("Complemento:"));
        campoComplemento = new JTextField();
        add(campoComplemento, "growx");

        // Evento para buscar o endereço ao digitar o CEP (quando o campo tem 8 números)
        campoCep.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String cep = campoCep.getText().replaceAll("[^0-9]", "");
                if (cep.length() == 8) {
                    buscarEnderecoPorCep(cep);
                }
            }
        });
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
        campoCep.setText("");
        campoLogradouro.setText("");
        campoBairro.setText("");
        campoNumero.setText("");
        campoCidade.setText("");
        campoEstado.setText("");
        campoComplemento.setText("");
        setCamposEnderecoEditaveis(true);
    }

    private void setCamposEnderecoEditaveis(boolean editavel) {
        campoLogradouro.setEditable(editavel);
        campoBairro.setEditable(editavel);
        campoCidade.setEditable(editavel);
        campoEstado.setEditable(editavel);
        campoComplemento.setEditable(editavel);
    }

    private void buscarEnderecoPorCep(String cep) {
        setCamposEnderecoEditaveis(false);
        campoLogradouro.setText("Buscando...");
        campoBairro.setText("Buscando...");
        campoCidade.setText("Buscando...");
        campoEstado.setText("Buscando...");
        campoComplemento.setText("");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private JSONObject json;

            @Override
            protected Void doInBackground() {
                try {
                    // Usando ViaCEP (https://viacep.com.br)
                    URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);

                    Scanner scanner = new Scanner(conn.getInputStream(), "UTF-8");
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }
                    scanner.close();
                    json = new JSONObject(response.toString());
                } catch (Exception ex) {
                    json = null;
                }
                return null;
            }

            @Override
            protected void done() {
                if (json != null && !json.has("erro")) {
                    campoLogradouro.setText(json.optString("logradouro", ""));
                    campoBairro.setText(json.optString("bairro", ""));
                    campoCidade.setText(json.optString("localidade", ""));
                    campoEstado.setText(json.optString("uf", ""));
                    campoComplemento.setText(json.optString("complemento", ""));
                    setCamposEnderecoEditaveis(false);
                    campoNumero.setEditable(true); // Número sempre editável
                } else {
                    campoLogradouro.setText("");
                    campoBairro.setText("");
                    campoCidade.setText("");
                    campoEstado.setText("");
                    campoComplemento.setText("");
                    setCamposEnderecoEditaveis(true);
                    JOptionPane.showMessageDialog(PainelEndereco.this, "CEP não encontrado.", "Endereço", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        worker.execute();
    }
}