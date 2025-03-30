package me.dio.matheusmarti.springapi.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record PedidoRequest(
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotEmpty(message = "O pedido deve ter pelo menos um item")
        List<ItemPedidoRequest> itens
) {}
