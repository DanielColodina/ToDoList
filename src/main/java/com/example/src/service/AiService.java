package com.example.src.service;

import com.example.src.dto.AssistenteResponse;
import com.example.src.dto.ComandoProduto;
import com.example.src.dto.ProdutoResponse;
import com.example.src.exception.ComandoInvalidoException;
import com.example.src.llm.InterpretadorComando;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final InterpretadorComando interpretadorComando;
    private final ProdutoService produtoService;

    public AiService(
            InterpretadorComando interpretadorComando,
            ProdutoService produtoService) {
        this.interpretadorComando = interpretadorComando;
        this.produtoService = produtoService;
    }

    public AssistenteResponse executar(String mensagem) {
        if (mensagem == null || mensagem.isBlank()) {
            throw new ComandoInvalidoException("Mensagem é obrigatória");
        }

        ComandoProduto comando = interpretadorComando.interpretar(mensagem);
        validarComando(comando);

        return switch (comando.acao()) {
            case CADASTRAR -> respostaComProduto(
                    "Produto cadastrado com sucesso",
                    comando,
                    produtoService.cadastrar(
                            comando.nome(),
                            comando.quantidade(),
                            comando.valor()
                    )
            );
            case LISTAR -> new AssistenteResponse(
                    "Produtos encontrados",
                    comando,
                    produtoService.listarTodos()
            );
            case BUSCAR -> respostaComProduto(
                    "Produto encontrado",
                    comando,
                    produtoService.buscarPorNome(comando.nome())
            );
            case ATUALIZAR -> respostaComProduto(
                    "Quantidade atualizada com sucesso",
                    comando,
                    produtoService.atualizarQuantidadePorNome(
                            comando.nome(),
                            comando.quantidade()
                    )
            );
            case ADICIONAR -> respostaComProduto(
                    "Quantidade adicionada com sucesso",
                    comando,
                    produtoService.adicionarQuantidade(
                            comando.nome(),
                            comando.quantidade()
                    )
            );
            case EXCLUIR -> excluirProduto(comando);
            case ESTOQUE_BAIXO -> new AssistenteResponse(
                    "Produtos com estoque igual ou menor que 5",
                    comando,
                    produtoService.listarEstoqueBaixo()
            );
        };
    }

    private AssistenteResponse excluirProduto(ComandoProduto comando) {
        produtoService.excluirPorNome(comando.nome());
        return new AssistenteResponse(
                "Produto excluído com sucesso",
                comando,
                List.of()
        );
    }

    private AssistenteResponse respostaComProduto(
            String mensagem,
            ComandoProduto comando,
            ProdutoResponse produto) {
        return new AssistenteResponse(
                mensagem,
                comando,
                List.of(produto)
        );
    }

    private void validarComando(ComandoProduto comando) {
        if (comando == null || comando.acao() == null) {
            throw new ComandoInvalidoException(
                    "Não foi possível identificar a ação solicitada"
            );
        }

        switch (comando.acao()) {
            case CADASTRAR -> {
                exigirNome(comando);
                exigirQuantidade(comando);
                if (comando.valor() == null) {
                    throw new ComandoInvalidoException(
                            "Informe o valor do produto"
                    );
                }
            }
            case BUSCAR, EXCLUIR -> exigirNome(comando);
            case ATUALIZAR, ADICIONAR -> {
                exigirNome(comando);
                exigirQuantidade(comando);
            }
            case LISTAR, ESTOQUE_BAIXO -> {
                // Estas ações não precisam de outros campos.
            }
        }
    }

    private void exigirNome(ComandoProduto comando) {
        if (comando.nome() == null || comando.nome().isBlank()) {
            throw new ComandoInvalidoException(
                    "Informe o nome do produto"
            );
        }
    }

    private void exigirQuantidade(ComandoProduto comando) {
        if (comando.quantidade() == null) {
            throw new ComandoInvalidoException(
                    "Informe a quantidade do produto"
            );
        }
    }
}
