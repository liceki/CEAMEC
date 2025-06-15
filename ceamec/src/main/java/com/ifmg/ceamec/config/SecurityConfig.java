package com.ifmg.ceamec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Pacote: com.ceamec.orfanato.config
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Adicione aqui rotas públicas se houver (ex: telas de ajuda)
                        // .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição
                )
                .formLogin(formLogin -> formLogin
                        // Se fosse web, aqui configuraria a página de login.
                        // Para Swing, a lógica de login é chamada manualmente,
                        // mas a configuração ainda é necessária para o Spring Security funcionar.
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        // Para aplicações não-web (como a sua Swing), é comum desabilitar o CSRF.
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define o BCrypt como o algoritmo para codificar senhas.
        return new BCryptPasswordEncoder();
    }
}