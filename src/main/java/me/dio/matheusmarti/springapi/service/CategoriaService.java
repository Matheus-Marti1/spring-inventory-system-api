package me.dio.matheusmarti.springapi.service;

import me.dio.matheusmarti.springapi.dto.CategoriaRequest;
import me.dio.matheusmarti.springapi.dto.CategoriaResponse;
import me.dio.matheusmarti.springapi.model.Categoria;
import me.dio.matheusmarti.springapi.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponse criarCategoria(CategoriaRequest request) {
        if (categoriaRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new IllegalArgumentException("Já existe uma categoria com o nome: " + request.nome());
        }

        Categoria categoria = new Categoria();
        categoria.setNome(request.nome());
        categoria.setDescricao(request.descricao());

        return CategoriaResponse.fromEntity(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponse atualizarCategoria(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + id));

        if (categoriaRepository.existsByNomeAndIdNot(request.nome(), id)) {
            throw new IllegalArgumentException("Já existe uma categoria com o nome: " + request.nome());
        }

        categoria.setNome(request.nome());
        categoria.setDescricao(request.descricao());

        return CategoriaResponse.fromEntity(categoriaRepository.save(categoria));
    }

    public List<CategoriaResponse> listarTodasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponse::fromEntity)
                .toList();
    }

    public CategoriaResponse buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(CategoriaResponse::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + id));
    }

    @Transactional
    public void deletarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
