package com.example.src.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Quantidade é obrigatória")
        @Min(value = 0, message = "Quantidade não pode ser negativa")
        Integer quantidade,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.0", message = "Valor não pode ser negativo")
        Double valor
) {
}
