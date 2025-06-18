package com.ifmg.ceamec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doadores")
public class Doador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_fisica_id", referencedColumnName = "id")
    private PessoaFisica pessoaFisica;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_juridica_id", referencedColumnName = "id")
    private PessoaJuridica pessoaJuridica;

    @Column(nullable = false)
    private String tipoDoador; // "Pessoa Física" ou "Pessoa Jurídica"
}