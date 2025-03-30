package me.dio.matheusmarti.springapi.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        String descricao,

        @NotNull(message = "Preço é obrigatório")
        @DecimalMin(value = "0.01", message = "Preço deve ser maior que 0")
        BigDecimal preco,

        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId,

        @Min(value = 0, message = "Estoque não pode ser negativo")
        int estoque
) {}
