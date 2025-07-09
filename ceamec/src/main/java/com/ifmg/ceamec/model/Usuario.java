package com.ifmg.ceamec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class Usuario extends Contato {
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senhaHash;
}
