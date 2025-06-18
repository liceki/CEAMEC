package com.ifmg.ceamec;

import com.formdev.flatlaf.FlatLightLaf;
import com.ifmg.ceamec.model.Usuario;
import com.ifmg.ceamec.repository.UsuarioRepository;
import com.ifmg.ceamec.view.TelaLogin;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.*;

//@SpringBootApplication
//public class CeamecApplication {
//
//    public static void main(String[] args) {
//
//        new SpringApplicationBuilder(CeamecApplication.class)
//                .headless(false)
//                .web(WebApplicationType.NONE)
//                .run(args);
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaLogin().setVisible(true);
//            }
//        });
//    }
//
//}

@SpringBootApplication
public class CeamecApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {

        // --- INÍCIO DA CONFIGURAÇÃO DO FLATLAF ---
        // É uma boa prática fazer isso dentro de um try-catch.
        try {
            // Configura o Look and Feel FlatLaf Light. Esta é a linha mais importante!
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar o Look and Feel. Usando o padrão.");
        }
        // --- FIM DA CONFIGURAÇÃO DO FLATLAF ---

        // O resto do seu código de inicialização do Spring...
        context = new SpringApplicationBuilder(CeamecApplication.class)
                .headless(false)
                .run(args);

        // Inicia a UI na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            context.getBean(TelaLogin.class).setVisible(true);
        });
    }

    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);
        Thread thread = new Thread(() -> {
            context.close();
            main(args.getSourceArgs());
        });
        thread.setDaemon(false);
        thread.start();
    }

    /**
     * Este Bean é executado na inicialização da aplicação.
     * Ele verifica se um usuário admin padrão existe e, se não, o cria.
     *
     * @param usuarioRepository Repositório para interagir com a tabela de usuários.
     * @param passwordEncoder O codificador de senhas que já configuramos.
     * @return A lógica de inicialização a ser executada.
     */
    @Bean
    public CommandLineRunner createDefaultAdminUser(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Define o e-mail/login do admin padrão
            String adminLogin = "admin@ceamec.com";

            // 1. Verifica se o usuário já existe no banco de dados
            if (usuarioRepository.findByLogin(adminLogin).isEmpty()) {

                System.out.println("Nenhum usuário admin encontrado, criando usuário padrão...");

                // 2. Se não existir, cria um novo objeto Usuario
                Usuario admin = new Usuario();
                admin.setLogin(adminLogin);
                admin.setEmail(adminLogin);
                admin.setNomeCompleto("Administrador do Sistema");

                // 3. Criptografa a senha padrão antes de salvar
                admin.setSenhaHash(passwordEncoder.encode("admin123"));

                // 4. Salva o novo usuário no banco de dados
                usuarioRepository.save(admin);

                System.out.println("Usuário admin padrão criado com sucesso!");
                System.out.println("Login: " + adminLogin);
                System.out.println("Senha: admin123");
            }
        };
    }
}