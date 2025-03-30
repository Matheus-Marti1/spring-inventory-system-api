package me.dio.matheusmarti.springapi.controller;

import me.dio.matheusmarti.springapi.dto.CategoriaRequest;
import me.dio.matheusmarti.springapi.dto.CategoriaResponse;
import me.dio.matheusmarti.springapi.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Gerenciamento de categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar uma nova categoria", description = "Cadastra uma categoria no sistema")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    public CategoriaResponse criarCategoria(@RequestBody CategoriaRequest request) {
        return categoriaService.criarCategoria(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma categoria", description = "Atualiza os dados de uma categoria existente")
    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso")
    public CategoriaResponse atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaRequest request) {
        return categoriaService.atualizarCategoria(id, request);
    }

    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias cadastradas")
    public List<CategoriaResponse> listarTodasCategorias() {
        return categoriaService.listarTodasCategorias();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna os detalhes de uma categoria específica")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    public CategoriaResponse buscarCategoriaPorId(@PathVariable Long id) {
        return categoriaService.buscarCategoriaPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar uma categoria", description = "Remove uma categoria do sistema")
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso")
    public void deletarCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
    }
}
