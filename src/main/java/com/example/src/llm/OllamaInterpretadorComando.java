package com.example.src.llm;

import com.example.src.dto.ComandoProduto;
import com.example.src.exception.ComandoInvalidoException;
import com.example.src.exception.OllamaIndisponivelException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class OllamaInterpretadorComando implements InterpretadorComando {

    private static final String PROMPT_SISTEMA = """
            Você interpreta comandos de um supermercado.
            Responda somente conforme o schema JSON fornecido.

            Ações:
            - CADASTRAR: cadastrar produto; exige nome, quantidade e valor.
            - LISTAR: listar todos os produtos.
            - BUSCAR: consultar produto; exige nome.
            - ATUALIZAR: definir o estoque para uma quantidade exata; exige nome e quantidade.
            - ADICIONAR: somar unidades ao estoque atual; exige nome e quantidade.
            - EXCLUIR: excluir produto; exige nome.
            - ESTOQUE_BAIXO: listar produtos que estão acabando.

            Use null nos campos que não se aplicam.
            Preserve o nome do produto informado pelo usuário.
            Não invente valores ausentes.
            Em frases como "unidades de feijão", feijão é o nome do produto.

            Exemplos:
            "cadastre 20 arroz por 25 reais"
            -> {"acao":"CADASTRAR","nome":"Arroz","quantidade":20,"valor":25.0}

            "quantos arroz ainda temos?"
            -> {"acao":"BUSCAR","nome":"Arroz","quantidade":null,"valor":null}

            "adicione 15 unidades de feijão"
            -> {"acao":"ADICIONAR","nome":"Feijão","quantidade":15,"valor":null}

            "defina o estoque de leite para 12"
            -> {"acao":"ATUALIZAR","nome":"Leite","quantidade":12,"valor":null}

            "quais produtos estão acabando?"
            -> {"acao":"ESTOQUE_BAIXO","nome":null,"quantidade":null,"valor":null}

            "exclua o produto leite"
            -> {"acao":"EXCLUIR","nome":"Leite","quantidade":null,"valor":null}
            """;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String modelo;

    public OllamaInterpretadorComando(
            ObjectMapper objectMapper,
            @Value("${ollama.base-url}") String baseUrl,
            @Value("${ollama.model}") String modelo) {

        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
        this.objectMapper = objectMapper;
        this.modelo = modelo;
    }

    @Override
    public ComandoProduto interpretar(String mensagem) {
        OllamaChatRequest request = new OllamaChatRequest(
                modelo,
                List.of(
                        new OllamaMessage("system", PROMPT_SISTEMA),
                        new OllamaMessage("user", mensagem)
                ),
                false,
                criarSchemaComando(),
                Map.of("temperature", 0)
        );

        try {
            OllamaChatResponse response = restClient.post()
                    .uri("/api/chat")
                    .body(request)
                    .retrieve()
                    .body(OllamaChatResponse.class);

            if (response == null
                    || response.message() == null
                    || response.message().content() == null) {
                throw new ComandoInvalidoException(
                        "O Ollama retornou uma resposta vazia"
                );
            }

            return objectMapper.readValue(
                    response.message().content(),
                    ComandoProduto.class
            );
        } catch (JacksonException exception) {
            throw new ComandoInvalidoException(
                    "O Ollama retornou um comando fora do formato esperado",
                    exception
            );
        } catch (RestClientException exception) {
            throw new OllamaIndisponivelException(
                    "Não foi possível acessar o Ollama local. "
                            + "Verifique se ele está ativo e se o modelo '"
                            + modelo + "' está instalado.",
                    exception
            );
        }
    }

    private Map<String, Object> criarSchemaComando() {
        Map<String, Object> propriedades = new LinkedHashMap<>();
        propriedades.put("acao", Map.of(
                "type", "string",
                "enum", List.of(
                        "CADASTRAR",
                        "LISTAR",
                        "BUSCAR",
                        "ATUALIZAR",
                        "ADICIONAR",
                        "EXCLUIR",
                        "ESTOQUE_BAIXO"
                )
        ));
        propriedades.put("nome", Map.of(
                "type", List.of("string", "null")
        ));
        propriedades.put("quantidade", Map.of(
                "type", List.of("integer", "null")
        ));
        propriedades.put("valor", Map.of(
                "type", List.of("number", "null")
        ));

        Map<String, Object> schema = new LinkedHashMap<>();
        schema.put("type", "object");
        schema.put("properties", propriedades);
        schema.put(
                "required",
                List.of("acao", "nome", "quantidade", "valor")
        );
        schema.put("additionalProperties", false);
        return schema;
    }

    private record OllamaChatRequest(
            String model,
            List<OllamaMessage> messages,
            boolean stream,
            Map<String, Object> format,
            Map<String, Object> options
    ) {
    }

    private record OllamaMessage(String role, String content) {
    }

    private record OllamaChatResponse(OllamaMessage message) {
    }
}
