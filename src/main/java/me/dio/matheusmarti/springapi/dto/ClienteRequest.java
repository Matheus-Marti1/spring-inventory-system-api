package me.dio.matheusmarti.springapi.dto;

import jakarta.validation.constraints.*;

public record ClienteRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @Email(message = "Email inválido")
        String email,
        String telefone
) {}
