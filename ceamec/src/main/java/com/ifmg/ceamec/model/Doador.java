package com.ifmg.ceamec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "doadores")
@Inheritance(strategy = InheritanceType.JOINED) // <-- ADIÇÃO IMPORTANTE
public abstract class Doador extends Contato {

    @OneToMany(mappedBy = "doador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doacao> doacoes = new ArrayList<>();

    public abstract String getDocumento();
}