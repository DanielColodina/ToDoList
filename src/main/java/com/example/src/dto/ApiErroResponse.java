package com.example.src.dto;

import java.time.Instant;

public record ApiErroResponse(
        Instant instante,
        int status,
        String erro,
        String mensagem
) {
}
