package com.ifmg.ceamec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pessoas_fisicas")
public class PessoaFisica extends Pessoa {
}
