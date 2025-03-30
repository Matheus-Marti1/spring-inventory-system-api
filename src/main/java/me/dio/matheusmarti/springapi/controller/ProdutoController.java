package me.dio.matheusmarti.springapi.controller;

import me.dio.matheusmarti.springapi.dto.ProdutoRequest;
import me.dio.matheusmarti.springapi.dto.ProdutoResponse;
import me.dio.matheusmarti.springapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar um novo produto", description = "Cadastra um produto no sistema")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    public ProdutoResponse criarProduto(@RequestBody ProdutoRequest request) {
        return produtoService.criarProduto(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto", description = "Atualiza os dados de um produto existente")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    public ProdutoResponse atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        return produtoService.atualizarProduto(id, request);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados")
    public List<ProdutoResponse> listarTodosProdutos() {
        return produtoService.listarTodosProdutos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto específico")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ProdutoResponse buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar um produto", description = "Remove um produto do sistema")
    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso")
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
    }
}
