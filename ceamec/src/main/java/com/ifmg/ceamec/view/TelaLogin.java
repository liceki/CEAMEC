package com.ifmg.ceamec.view;

import com.formdev.flatlaf.FlatLightLaf;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class TelaLogin extends JFrame {
    private final AuthenticationManager authenticationManager;
    private final MainFrame mainFrame;

    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public TelaLogin(AuthenticationManager authenticationManager, MainFrame mainFrame) {
        this.authenticationManager = authenticationManager;
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        FlatLightLaf.setup();
        setTitle("Login - Sistema CEAMEC");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("E-mail (Login):"), gbc);

        gbc.gridx = 1;
        campoLogin = new JTextField(20);
        add(campoLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        campoSenha = new JPasswordField(20);
        add(campoSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        botaoEntrar = new JButton("Entrar");
        add(botaoEntrar, gbc);

        botaoEntrar.addActionListener(e -> autenticar());
        campoLogin.setText("admin@ceamec.com");
        campoSenha.setText("admin123");
    }

    // Este metodo integra a View com o Service/Security para validar as credenciais
    private void autenticar() {
        String login = campoLogin.getText();
        String senha = new String(campoSenha.getPassword());

        if (login.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o e-mail e a senha.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Cria o token de autenticação com as credenciais da tela
            var token = new UsernamePasswordAuthenticationToken(login, senha);

            // Tenta autenticar usando o AuthenticationManager do Spring Security
            Authentication authentication = authenticationManager.authenticate(token);

            // Se a autenticação for bem-sucedida, armazena os dados do usuário na sessão
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Exibe a tela principal e fecha a tela de login
            mainFrame.setVisible(true);
            this.dispose();

        } catch (BadCredentialsException e) {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
