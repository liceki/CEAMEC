package com.ifmg.ceamec.repository;


import com.ifmg.ceamec.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {
    // Para a Sprint 1, os métodos CRUD básicos fornecidos pelo JpaRepository são suficientes.
}
