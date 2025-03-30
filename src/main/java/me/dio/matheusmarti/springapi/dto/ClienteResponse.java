package me.dio.matheusmarti.springapi.dto;

import me.dio.matheusmarti.springapi.model.Cliente;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String telefone
) {
    public static ClienteResponse fromEntity(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }
}
