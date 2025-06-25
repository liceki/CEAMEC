package com.ifmg.ceamec.view;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class MainFrame extends JFrame {

    // Gerenciador de layout que mostra um painel por vez
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    // --- Injeção de Dependências dos Painéis ---
    // O Spring irá fornecer a instância de PainelGestaoDoador aqui.
    private final PainelGestaoDoador painelGestaoDoador;

    // Outros painéis para Sprints futuras seriam injetados aqui também
    // private final PainelGestaoCrianca painelGestaoCrianca;

    // Construtor que recebe os painéis gerenciados pelo Spring
    public MainFrame(PainelGestaoDoador painelGestaoDoador) {
        this.painelGestaoDoador = painelGestaoDoador;

        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
    }

    // @PostConstruct garante que este metodo seja chamado após a injeção de todas as dependências
    @PostConstruct
    private void initUI() {
        setTitle("Sistema de Gestão CEAMEC");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // --- PASSO 2: ADICIONAR O PAINEL AO CARDLAYOUT ---
        // Adiciona um painel vazio como tela inicial
        mainPanel.add(createWelcomePanel(), "TELA_INICIAL");

        // Adiciona o painel de gestão de doadores com um nome único ("ID")
        mainPanel.add(painelGestaoDoador, "PAINEL_DOADORES");

        // Outros painéis seriam adicionados aqui no futuro
        // mainPanel.add(painelGestaoCriancas, "PAINEL_CRIANCAS");

        // Configura a barra de menu
        setJMenuBar(createMenuBar());

        // Define o painel com CardLayout como o conteúdo principal da janela
        setContentPane(mainPanel);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        menuBar.add(menuCadastros);

        // --- PASSO 3: ATIVAR O ITEM DE MENU ---
        JMenuItem itemGestaoDoadores = new JMenuItem("Gerenciar Doadores");

        // A ação deste item de menu é mostrar o painel correspondente no CardLayout
        itemGestaoDoadores.addActionListener(e -> cardLayout.show(mainPanel, "PAINEL_DOADORES"));

        menuCadastros.add(itemGestaoDoadores);

        // ... aqui viriam outros itens de menu para Crianças, Doações, etc.

        // (A lógica de Logout, se existir, ficaria em outro menu, como "Sistema")

        return menuBar;
    }

    // Método auxiliar para criar um painel de boas-vindas simples
    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gestão CEAMEC!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        return welcomePanel;
    }
}