package com.example.src.dto;

import java.util.List;

public record AssistenteResponse(
        String mensagem,
        ComandoProduto comando,
        List<ProdutoResponse> produtos
) {
}
