package com.ifmg.ceamec.repository;

import com.ifmg.ceamec.model.DoadorJuridico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<DoadorJuridico, Long> {
}