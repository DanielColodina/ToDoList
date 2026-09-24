package com.example.src.repository;

import com.example.src.model.ProdutoGpt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoGpt, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    Optional<ProdutoGpt> findByNomeIgnoreCase(String nome);

    List<ProdutoGpt> findByQuantidadeLessThanEqualOrderByQuantidadeAscNomeAsc(
            Integer quantidade);
}
