package com.example.src.dto;

public record ProdutoResponse(
        Long id,
        String nome,
        Integer quantidade,
        Double valor
) {
}
