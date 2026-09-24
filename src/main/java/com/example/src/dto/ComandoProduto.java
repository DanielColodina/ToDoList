package com.example.src.dto;

import com.example.src.enums.AcaoProduto;

public record ComandoProduto(
        AcaoProduto acao,
        String nome,
        Integer quantidade,
        Double valor
) {
}
