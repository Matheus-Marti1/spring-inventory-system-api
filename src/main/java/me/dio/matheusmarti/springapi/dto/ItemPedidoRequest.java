package me.dio.matheusmarti.springapi.dto;

import jakarta.validation.constraints.*;

public record ItemPedidoRequest(
        @NotNull(message = "Produto é obrigatório")
        Long produtoId,

        @Min(value = 1, message = "Quantidade mínima é 1")
        int quantidade
) {}
