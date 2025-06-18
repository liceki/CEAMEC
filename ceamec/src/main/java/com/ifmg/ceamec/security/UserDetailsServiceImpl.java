package com.ifmg.ceamec.security;

import com.ifmg.ceamec.model.Usuario;
import com.ifmg.ceamec.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + login));

        // Como o sistema possui apenas o perfil "Admin", atribuímos esse papel.
        Set<SimpleGrantedAuthority> authorities = Stream.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                .collect(Collectors.toSet());

        return new User(
                usuario.getLogin(),
                usuario.getSenhaHash(),
                authorities
        );
    }
}