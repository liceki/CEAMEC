package com.ifmg.ceamec.repository;

import com.ifmg.ceamec.model.Doacao;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DoacaoRepository extends JpaRepository<Doacao, Long>, JpaSpecificationExecutor<Doacao> {
    @Override
    @EntityGraph(attributePaths = {"doador"})
    List<Doacao> findAll(Specification<Doacao> spec);
}