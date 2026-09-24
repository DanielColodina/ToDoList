package com.example.src.controller;

import com.example.src.dto.AssistenteResponse;
import com.example.src.dto.MensagemRequest;
import com.example.src.service.AiService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistente")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping
    public AssistenteResponse executar(
            @Valid @RequestBody MensagemRequest request) {

        return aiService.executar(request.mensagem());
    }
}
