package com.ifmg.ceamec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "doadores_fisicos")
public class DoadorFisico extends Doador {
    private String cpf;
}