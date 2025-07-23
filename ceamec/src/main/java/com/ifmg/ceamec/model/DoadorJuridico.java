package com.ifmg.ceamec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "doadores_juridicos")
public class DoadorJuridico extends Doador {
    private String cnpj;

    @Override
    public String getDocumento() {
        return cnpj;
    }
}