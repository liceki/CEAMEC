package com.ifmg.ceamec.repository;


import com.ifmg.ceamec.model.Doador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {
    Page<Doador> findByNomeStartsWithIgnoreCase(String nome, Pageable pageable);
}
