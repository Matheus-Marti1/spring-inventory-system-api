package me.dio.matheusmarti.springapi.dto;

import me.dio.matheusmarti.springapi.model.Categoria;

public record CategoriaResponse(
        Long id,
        String nome,
        String descricao
) {
    public static CategoriaResponse fromEntity(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}
