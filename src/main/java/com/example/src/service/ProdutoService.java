package com.example.src.service;

import com.example.src.dto.ProdutoResponse;
import com.example.src.exception.ProdutoJaExisteException;
import com.example.src.exception.ProdutoNaoEncontradoException;
import com.example.src.model.ProdutoGpt;
import com.example.src.repository.ProdutoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private static final int LIMITE_ESTOQUE_BAIXO = 5;

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponse cadastrar(
            String nome,
            Integer quantidade,
            Double valor) {

        String nomeNormalizado = normalizarNome(nome);
        validarQuantidade(quantidade);
        validarValor(valor);

        if (produtoRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new ProdutoJaExisteException(nomeNormalizado);
        }

        ProdutoGpt produto = new ProdutoGpt();
        produto.setNome(nomeNormalizado);
        produto.setQuantidade(quantidade);
        produto.setValor(valor);

        try {
            return converterParaResponse(produtoRepository.save(produto));
        } catch (DataIntegrityViolationException exception) {
            throw new ProdutoJaExisteException(nomeNormalizado, exception);
        }
    }

    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public ProdutoResponse buscarPorId(Long id) {
        return converterParaResponse(buscarEntidadePorId(id));
    }

    public ProdutoResponse buscarPorNome(String nome) {
        return converterParaResponse(buscarEntidadePorNome(nome));
    }

    public ProdutoResponse atualizarQuantidade(
            Long id,
            Integer novaQuantidade) {

        validarQuantidade(novaQuantidade);

        ProdutoGpt produto = buscarEntidadePorId(id);
        produto.setQuantidade(novaQuantidade);

        return converterParaResponse(produtoRepository.save(produto));
    }

    public ProdutoResponse atualizarQuantidadePorNome(
            String nome,
            Integer novaQuantidade) {

        validarQuantidade(novaQuantidade);

        ProdutoGpt produto = buscarEntidadePorNome(nome);
        produto.setQuantidade(novaQuantidade);

        return converterParaResponse(produtoRepository.save(produto));
    }

    public ProdutoResponse adicionarQuantidade(
            String nome,
            Integer quantidadeAdicionada) {

        validarQuantidadePositiva(quantidadeAdicionada);

        ProdutoGpt produto = buscarEntidadePorNome(nome);
        int novaQuantidade;

        try {
            novaQuantidade = Math.addExact(
                    produto.getQuantidade(),
                    quantidadeAdicionada
            );
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException(
                    "Quantidade final excede o limite permitido",
                    exception
            );
        }

        produto.setQuantidade(novaQuantidade);
        return converterParaResponse(produtoRepository.save(produto));
    }

    public List<ProdutoResponse> listarEstoqueBaixo() {
        return produtoRepository
                .findByQuantidadeLessThanEqualOrderByQuantidadeAscNomeAsc(
                        LIMITE_ESTOQUE_BAIXO
                )
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public void excluir(Long id) {
        produtoRepository.delete(buscarEntidadePorId(id));
    }

    public void excluirPorNome(String nome) {
        produtoRepository.delete(buscarEntidadePorNome(nome));
    }

    private ProdutoGpt buscarEntidadePorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id do produto é obrigatório");
        }

        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    private ProdutoGpt buscarEntidadePorNome(String nome) {
        String nomeNormalizado = normalizarNome(nome);

        return produtoRepository.findByNomeIgnoreCase(nomeNormalizado)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(nomeNormalizado)
                );
    }

    private String normalizarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        return nome.trim();
    }

    private void validarQuantidade(Integer quantidade) {
        if (quantidade == null) {
            throw new IllegalArgumentException("Quantidade é obrigatória");
        }

        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "Quantidade não pode ser negativa"
            );
        }
    }

    private void validarQuantidadePositiva(Integer quantidade) {
        validarQuantidade(quantidade);

        if (quantidade == 0) {
            throw new IllegalArgumentException(
                    "Quantidade adicionada deve ser maior que zero"
            );
        }
    }

    private void validarValor(Double valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Valor é obrigatório");
        }

        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException(
                    "Valor deve ser finito e não negativo"
            );
        }
    }

    private ProdutoResponse converterParaResponse(ProdutoGpt produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getValor()
        );
    }
}
