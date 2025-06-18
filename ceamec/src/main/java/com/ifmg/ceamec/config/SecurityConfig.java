package com.ifmg.ceamec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean do AuthenticationManager (A FORMA CORRETA E MANUAL)
     * Aqui, nós mesmos construímos o gerenciador de autenticação.
     *
     * @param userDetailsService O nosso serviço que busca usuários (Spring injeta o UserDetailsServiceImpl aqui).
     * @param passwordEncoder O nosso codificador de senhas definido acima.
     * @return um AuthenticationManager totalmente configurado e pronto para uso.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        // 2. Cria o gerenciador de autenticação, usando o provedor que acabamos de configurar.
        return new ProviderManager(authenticationProvider);
    }


}