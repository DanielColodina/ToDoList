package com.example.todolist.livraria.controller;


import com.example.todolist.livraria.model.Livro;
import com.example.todolist.livraria.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livro")
public class LivroController {

    //Construtor
    @Autowired
    private LivroRepository livroRepository;

    @GetMapping
    public List<Livro> todosLivros() {
        return livroRepository.findAll();
    }

    //Busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> livroID(@PathVariable long id) {
        Optional<Livro> existiID = livroRepository.findById(id);
        return existiID.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{id}") //Adicionar o corpo
    public ResponseEntity<Livro> adicionaLivro(@RequestBody Livro livro) {
        Livro livrosalvo = livroRepository.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livrosalvo);
    }

    @PutMapping("{id}")
    public ResponseEntity<Livro> alterarLivro(@PathVariable long id, @RequestBody Livro LivroCorpo) {
        Optional<Livro> LivroExisti = livroRepository.findById(id);
        if (LivroExisti.isPresent()) {
            Livro livroAdicionar = LivroExisti.get();
            livroAdicionar.setNome(LivroCorpo.getNome());
            livroAdicionar.setAutor(LivroCorpo.getAutor());
            livroAdicionar.setDescricao(LivroCorpo.getDescricao());
            livroAdicionar.setDisponivel(LivroCorpo.isDisponivel());

            Livro livroAtualizado = livroRepository.save(livroAdicionar);
            return ResponseEntity.ok(livroAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Livro> excluir(@PathVariable long id) {
        Optional<Livro> VerificaID = livroRepository.findById(id);
        if (VerificaID.isPresent()) {
            livroRepository.delete(VerificaID.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();

        }

    }
}
