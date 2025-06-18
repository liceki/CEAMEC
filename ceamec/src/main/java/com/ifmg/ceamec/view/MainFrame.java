package com.ifmg.ceamec.view;

// Pacote: com.ceamec.orfanato.view

import com.ifmg.ceamec.CeamecApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    // Injeção de dependências de todos os painéis que este frame irá gerenciar
    //private final PainelGestaoDoador painelGestaoDoador;
    // private final PainelGestaoCrianca painelGestaoCrianca; // Seria injetado na Sprint 2
    // private final PainelRegistroDoacao painelRegistroDoacao; // Seria injetado na Sprint 2

    private final ConfigurableApplicationContext springContext;

    public MainFrame(/*PainelGestaoDoador painelGestaoDoador,*/ ConfigurableApplicationContext springContext) {
        //this.painelGestaoDoador = painelGestaoDoador;
        this.springContext = springContext;
        // this.painelGestaoCrianca = painelGestaoCrianca; // Exemplo para o futuro

        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);

        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestão CEAMEC");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Adiciona os painéis ao CardLayout com um nome único para cada um
        mainPanel.add(new JPanel(), "VAZIO"); // Um painel vazio para o início
        //mainPanel.add(painelGestaoDoador, "GESTAO_DOADORES");
        // mainPanel.add(painelGestaoCrianca, "GESTAO_CRIANCAS"); // Adicionado na Sprint 2
        // mainPanel.add(painelRegistroDoacao, "REGISTRO_DOACOES"); // Adicionado na Sprint 2

        setJMenuBar(createMenuBar());
        setContentPane(mainPanel);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Menu Cadastros ---
        JMenu menuCadastros = new JMenu("Cadastros");
        menuBar.add(menuCadastros);

        JMenuItem itemGestaoDoadores = new JMenuItem("Gestão de Doadores");
        itemGestaoDoadores.addActionListener(e -> cardLayout.show(mainPanel, "GESTAO_DOADORES"));
        menuCadastros.add(itemGestaoDoadores);

        // Exemplo de como seriam os próximos itens
        JMenuItem itemGestaoCriancas = new JMenuItem("Gestão de Crianças");
        // itemGestaoCriancas.addActionListener(e -> cardLayout.show(mainPanel, "GESTAO_CRIANCAS"));
        menuCadastros.add(itemGestaoCriancas);

        JMenuItem itemRegistroDoacoes = new JMenuItem("Registro de Doações");
        // itemRegistroDoacoes.addActionListener(e -> cardLayout.show(mainPanel, "REGISTRO_DOACOES"));
        menuCadastros.add(itemRegistroDoacoes);

        // --- Menu Sistema ---
        JMenu menuSistema = new JMenu("Sistema");
        menuBar.add(menuSistema);

        JMenuItem itemLogout = new JMenuItem("Sair (Logout)");
        itemLogout.addActionListener(e -> logout());
        menuSistema.add(itemLogout);

        return menuBar;
    }

    private void logout() {
        // Exibe uma caixa de confirmação
        int result = JOptionPane.showConfirmDialog(
                this,
                "Você tem certeza que deseja sair do sistema?",
                "Confirmar Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            // Cumpre o requisito [RF001] de revogar o acesso no logout
            this.dispose(); // Fecha a janela principal
            CeamecApplication.restart(); // Reinicia a aplicação para mostrar a tela de login
        }
    }
}