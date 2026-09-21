package com.example.freeCode.Supermecado.Controller;

import com.example.freeCode.Supermecado.model.Produto;
import com.example.freeCode.Supermecado.service.ProdutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supermecado")
public class ProdutoController {

    @Autowired
    private ProdutoreService produtoreService;

    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {

        return produtoreService.cadastrar(
                produto.getNome(),
                produto.getQuantidade(),
                produto.getValor()
        );
    }
    @GetMapping
    public List<Produto> listartodos() {
        return produtoreService.listartodos();
    }
}
