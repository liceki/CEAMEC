package com.ifmg.ceamec.repository;

import com.ifmg.ceamec.model.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
}