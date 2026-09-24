const produtoForm = document.querySelector("#produto-form");
const produtoBotao = document.querySelector("#produto-botao");
const produtoStatus = document.querySelector("#produto-status");
const assistenteForm = document.querySelector("#assistente-form");
const assistenteBotao = document.querySelector("#assistente-botao");
const assistenteStatus = document.querySelector("#assistente-status");
const assistenteResposta = document.querySelector("#assistente-resposta");
const produtosCorpo = document.querySelector("#produtos-corpo");
const produtosVazio = document.querySelector("#produtos-vazio");

async function chamarApi(url, opcoes = {}) {
    const resposta = await fetch(url, opcoes);
    const texto = await resposta.text();
    const conteudo = texto ? JSON.parse(texto) : null;

    if (!resposta.ok) {
        throw new Error(conteudo?.mensagem || "Não foi possível concluir a operação.");
    }

    return conteudo;
}

function exibirStatus(elemento, mensagem, tipo = "sucesso") {
    elemento.textContent = mensagem;
    elemento.className = `mensagem-status ${tipo}`;
    elemento.hidden = false;
}

async function carregarProdutos() {
    try {
        const produtos = await chamarApi("/api/produtos");
        produtosCorpo.replaceChildren();

        produtos.forEach(produto => {
            const linha = document.createElement("tr");
            const valores = [
                produto.id,
                produto.nome,
                produto.quantidade,
                Number(produto.valor).toLocaleString("pt-BR", {
                    style: "currency",
                    currency: "BRL"
                })
            ];

            valores.forEach(valor => {
                const celula = document.createElement("td");
                celula.textContent = valor;
                linha.appendChild(celula);
            });

            produtosCorpo.appendChild(linha);
        });

        produtosVazio.hidden = produtos.length > 0;
    } catch (erro) {
        produtosVazio.textContent = erro.message;
        produtosVazio.hidden = false;
    }
}

produtoForm.addEventListener("submit", async evento => {
    evento.preventDefault();
    produtoBotao.disabled = true;
    exibirStatus(produtoStatus, "Cadastrando produto...", "processando");

    const dados = new FormData(produtoForm);
    const produto = {
        nome: dados.get("nome"),
        quantidade: Number(dados.get("quantidade")),
        valor: Number(dados.get("valor"))
    };

    try {
        const cadastrado = await chamarApi("/api/produtos", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(produto)
        });

        exibirStatus(produtoStatus, `${cadastrado.nome} cadastrado com sucesso.`);
        produtoForm.reset();
        await carregarProdutos();
    } catch (erro) {
        exibirStatus(produtoStatus, erro.message, "erro");
    } finally {
        produtoBotao.disabled = false;
    }
});

assistenteForm.addEventListener("submit", async evento => {
    evento.preventDefault();
    const mensagem = new FormData(assistenteForm).get("mensagem");

    assistenteBotao.disabled = true;
    assistenteResposta.hidden = true;
    exibirStatus(
        assistenteStatus,
        "O Ollama está interpretando o comando. Isso pode levar alguns minutos...",
        "processando"
    );

    try {
        const resultado = await chamarApi("/api/assistente", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({mensagem})
        });

        exibirStatus(assistenteStatus, resultado.mensagem);
        assistenteResposta.textContent = JSON.stringify(resultado, null, 2);
        assistenteResposta.hidden = false;
        assistenteForm.reset();
        await carregarProdutos();
    } catch (erro) {
        exibirStatus(assistenteStatus, erro.message, "erro");
    } finally {
        assistenteBotao.disabled = false;
    }
});

document.querySelectorAll("[data-mensagem]").forEach(botao => {
    botao.addEventListener("click", () => {
        document.querySelector("#mensagem").value = botao.dataset.mensagem;
        document.querySelector("#mensagem").focus();
    });
});

document.querySelector("#atualizar-produtos")
        .addEventListener("click", carregarProdutos);

carregarProdutos();
