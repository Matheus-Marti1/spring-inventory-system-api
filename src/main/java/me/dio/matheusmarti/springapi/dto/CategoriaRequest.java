package me.dio.matheusmarti.springapi.dto;

import jakarta.validation.constraints.*;

public record CategoriaRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        String descricao
) {}
