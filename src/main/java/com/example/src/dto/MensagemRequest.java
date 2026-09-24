package com.example.src.dto;

import jakarta.validation.constraints.NotBlank;

public record MensagemRequest(
        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem
) {
}
