package me.dio.matheusmarti.springapi.dto;

import me.dio.matheusmarti.springapi.model.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id,
        ProdutoResponse produto,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
    public static ItemPedidoResponse fromEntity(ItemPedido item) {
        return new ItemPedidoResponse(
                item.getId(),
                ProdutoResponse.fromEntity(item.getProduto()), // Requer ProdutoResponse.fromEntity()
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())) // Calcula o subtotal
        );
    }
}
