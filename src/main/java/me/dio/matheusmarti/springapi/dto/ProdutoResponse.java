package me.dio.matheusmarti.springapi.dto;

import me.dio.matheusmarti.springapi.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        int estoque,
        CategoriaResponse categoria
) {
    public static ProdutoResponse fromEntity(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                CategoriaResponse.fromEntity(produto.getCategoria())
        );
    }
}
