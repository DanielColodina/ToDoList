package com.example.src.service;

import com.example.src.dto.ProdutoResponse;
import com.example.src.exception.ProdutoJaExisteException;
import com.example.src.model.ProdutoGpt;
import com.example.src.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private ProdutoService produtoService;

    @BeforeEach
    void configurar() {
        produtoService = new ProdutoService(produtoRepository);
    }

    @Test
    void deveCadastrarProdutoValido() {
        when(produtoRepository.existsByNomeIgnoreCase("Arroz"))
                .thenReturn(false);
        when(produtoRepository.save(any(ProdutoGpt.class)))
                .thenAnswer(invocacao -> {
                    ProdutoGpt produto = invocacao.getArgument(0);
                    produto.setId(1L);
                    return produto;
                });

        ProdutoResponse response = produtoService.cadastrar(
                "  Arroz  ",
                20,
                25.0
        );

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Arroz");
        assertThat(response.quantidade()).isEqualTo(20);
        assertThat(response.valor()).isEqualTo(25.0);
    }

    @Test
    void naoDeveCadastrarNomeDuplicado() {
        when(produtoRepository.existsByNomeIgnoreCase("Arroz"))
                .thenReturn(true);

        assertThatThrownBy(() ->
                produtoService.cadastrar("Arroz", 20, 25.0)
        ).isInstanceOf(ProdutoJaExisteException.class);

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void naoDeveAceitarQuantidadeNula() {
        assertThatThrownBy(() ->
                produtoService.cadastrar("Arroz", null, 25.0)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantidade é obrigatória");

        verify(produtoRepository, never())
                .existsByNomeIgnoreCase(any());
    }

    @Test
    void deveAdicionarQuantidadeAoEstoqueAtual() {
        ProdutoGpt produto = new ProdutoGpt(
                1L,
                "Feijão",
                10,
                8.0
        );
        when(produtoRepository.findByNomeIgnoreCase("Feijão"))
                .thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        ProdutoResponse response = produtoService.adicionarQuantidade(
                "Feijão",
                15
        );

        assertThat(response.quantidade()).isEqualTo(25);
    }

    @Test
    void naoDeveAceitarValorNaoFinito() {
        assertThatThrownBy(() ->
                produtoService.cadastrar("Arroz", 20, Double.NaN)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Valor deve ser finito e não negativo");
    }
}
