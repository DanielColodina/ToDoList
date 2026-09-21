package com.example.freeCode.Supermecado.repository;

import com.example.freeCode.Supermecado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
