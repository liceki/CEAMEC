package com.ifmg.ceamec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "criancas")
public class Crianca extends Pessoa {
    private Date dataNascimento;
}