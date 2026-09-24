package com.example.src.llm;

import com.example.src.dto.ComandoProduto;

public interface InterpretadorComando {

    ComandoProduto interpretar(String mensagem);
}
