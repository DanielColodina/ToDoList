package com.example.todolist.livraria.controller;

import com.example.todolist.livraria.model.Livro;
import com.example.todolist.livraria.repository.LivroRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livrocontroller")
public class LivroControllers {

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping
    public List<Livro> listagemLivro(){
        return livroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> livrosID(@PathVariable long id) {
        Optional<Livro> livroID = livroRepository.findById(id);
        return livroID.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }



}
