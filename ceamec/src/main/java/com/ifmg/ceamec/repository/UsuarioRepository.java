package com.ifmg.ceamec.repository;

import com.ifmg.ceamec.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo seu campo de login (e-mail) para o processo de autenticação.
     * @param login O e-mail do usuário a ser buscado.
     * @return um Optional contendo o usuário, se encontrado.
     */
    Optional<Usuario> findByLogin(String login);
}