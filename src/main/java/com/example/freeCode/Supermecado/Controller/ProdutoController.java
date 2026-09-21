package com.example.freeCode.Supermecado.Controller;

import com.example.freeCode.Supermecado.model.Produto;
import com.example.freeCode.Supermecado.service.ProdutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
