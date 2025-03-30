package me.dio.matheusmarti.springapi.dto;

import me.dio.matheusmarti.springapi.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PedidoResponse(
        Long id,
        LocalDate data,
        ClienteResponse cliente,
        BigDecimal total,
        List<ItemPedidoResponse> itens
) {
    public static PedidoResponse fromEntity(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getData(),
                ClienteResponse.fromEntity(pedido.getCliente()),
                pedido.getTotal(),
                pedido.getItens().stream()
                        .map(ItemPedidoResponse::fromEntity)
                        .toList()
        );
    }
}
