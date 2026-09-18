package com.example.todolist.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todolist.livraria.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
//Definir Contratos