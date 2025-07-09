package com.ifmg.ceamec.model;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "pessoas")
@Inheritance(strategy = InheritanceType.JOINED) // Estratégia de herança: uma tabela para cada classe
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}