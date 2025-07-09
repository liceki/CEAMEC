package com.ifmg.ceamec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contatos")
public abstract class Contato extends Pessoa {
    private String email;
    private String telefone;
}
