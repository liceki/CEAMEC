package com.ifmg.ceamec.repository;

import com.ifmg.ceamec.model.DoadorFisico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<DoadorFisico, Long> {
}
