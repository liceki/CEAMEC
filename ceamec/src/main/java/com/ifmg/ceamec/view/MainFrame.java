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
    private final PainelGestaoDoador painelGestaoDoador;
    private final PainelCadastroDoacao painelCadastroDoacao;
    // Outros painéis para Sprints futuras podem ser injetados aqui
    // private final PainelGestaoCrianca painelGestaoCrianca;

    // Construtor que recebe os painéis gerenciados pelo Spring
    public MainFrame(PainelGestaoDoador painelGestaoDoador, PainelCadastroDoacao painelCadastroDoacao) {
        this.painelGestaoDoador = painelGestaoDoador;
        this.painelCadastroDoacao = painelCadastroDoacao;

        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
    }

    @PostConstruct
    private void initUI() {
        setTitle("Sistema de Gestão CEAMEC");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // --- PASSO 2: ADICIONAR OS PAINÉIS AO CARDLAYOUT ---
        mainPanel.add(createWelcomePanel(), "TELA_INICIAL");
        mainPanel.add(painelGestaoDoador, "PAINEL_DOADORES");
        mainPanel.add(painelCadastroDoacao, "PAINEL_DOACOES");
        // Outros painéis podem ser adicionados aqui
        // mainPanel.add(painelGestaoCrianca, "PAINEL_CRIANCAS");

        setJMenuBar(createMenuBar());

        setContentPane(mainPanel);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        menuBar.add(menuCadastros);

        JMenuItem itemGestaoDoadores = new JMenuItem("Gerenciar Doadores");
        itemGestaoDoadores.addActionListener(
                e -> cardLayout.show(mainPanel, "PAINEL_DOADORES")
        );
        menuCadastros.add(itemGestaoDoadores);

        JMenuItem itemCadastroDoacao = new JMenuItem("Registrar Doação");
        itemCadastroDoacao.addActionListener(
                e -> cardLayout.show(mainPanel, "PAINEL_DOACOES")
        );
        menuCadastros.add(itemCadastroDoacao);

        // ... aqui viriam outros itens de menu para Crianças, etc.
        // Exemplo:
        // JMenuItem itemGestaoCriancas = new JMenuItem("Gerenciar Crianças");
        // itemGestaoCriancas.addActionListener(e -> cardLayout.show(mainPanel, "PAINEL_CRIANCAS"));
        // menuCadastros.add(itemGestaoCriancas);

        return menuBar;
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gestão CEAMEC!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        return welcomePanel;
    }
}