package com.example.todolist.todolistDois.controller;


import com.example.todolist.todolistDois.model.Lista;
import com.example.todolist.todolistDois.repository.ListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lista")
public class ListaController {

    @Autowired
    private ListaRepository listaRepository;

    @GetMapping
    public List<Lista> todaLista() {
        return listaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lista> procuraID(@PathVariable Long id) {
        Optional<Lista> lista = listaRepository.findById(id);
        return lista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lista> AdicionarId(@RequestBody Lista lista) {
        Lista listasalva = listaRepository.save(lista);
        return ResponseEntity.status(HttpStatus.CREATED).body(listasalva);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Lista> adicionarID(@PathVariable Long id, @RequestBody Lista CorpoLista) {
        Optional<Lista> adicionar = listaRepository.findById(id);
        if (adicionar.isPresent()) {
            Lista listExists = adicionar.get();
            listExists.setNome(CorpoLista.getNome());
            listExists.setDescricao(CorpoLista.getDescricao());
            listExists.setAtivo(CorpoLista.isAtivo());

            Lista listaAtualizada = listaRepository.save(listExists);
            return ResponseEntity.ok(listaAtualizada);
        } else {
         return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Lista> exluirID(@PathVariable Long id) {
        Optional<Lista> VerificarID = listaRepository.findById(id);
        if (VerificarID.isPresent()) {
             listaRepository.delete(VerificarID.get());
            return ResponseEntity.noContent().build();
        } else {
           return ResponseEntity.notFound().build();
        }
    }
}
