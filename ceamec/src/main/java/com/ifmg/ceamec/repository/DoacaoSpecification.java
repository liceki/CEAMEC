package com.ifmg.ceamec.repository;

import com.ifmg.ceamec.dto.DoacaoFilterDTO;
import com.ifmg.ceamec.model.Doacao;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class DoacaoSpecification {

    public static Specification<Doacao> filterBy(DoacaoFilterDTO filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.quantidadeMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("quantidade"), filtro.quantidadeMin()));
            }
            if (filtro.quantidadeMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("quantidade"), filtro.quantidadeMax()));
            }
            if (filtro.dataMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("data"), filtro.dataMin()));
            }
            if (filtro.dataMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("data"), filtro.dataMax()));
            }
            if (filtro.tipos() != null && !filtro.tipos().isEmpty()) {
                predicates.add(root.get("tipo").in(filtro.tipos()));
            }
            if (filtro.doadorIds() != null && !filtro.doadorIds().isEmpty()) {
                predicates.add(root.get("doador").get("id").in(filtro.doadorIds()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}