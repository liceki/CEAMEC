package com.ifmg.ceamec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario extends Pessoa {
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senhaHash;
}
