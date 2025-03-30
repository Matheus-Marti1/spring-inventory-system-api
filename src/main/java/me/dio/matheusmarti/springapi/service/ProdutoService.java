package me.dio.matheusmarti.springapi.service;

import me.dio.matheusmarti.springapi.dto.ProdutoRequest;
import me.dio.matheusmarti.springapi.dto.ProdutoResponse;
import me.dio.matheusmarti.springapi.model.Categoria;
import me.dio.matheusmarti.springapi.model.Produto;
import me.dio.matheusmarti.springapi.repository.CategoriaRepository;
import me.dio.matheusmarti.springapi.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public ProdutoResponse criarProduto(ProdutoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + request.categoriaId()));

        Produto produto = new Produto();
        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        produto.setEstoque(request.estoque());
        produto.setCategoria(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);
        return ProdutoResponse.fromEntity(produtoSalvo);
    }

    @Transactional
    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + request.categoriaId()));

        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        produto.setEstoque(request.estoque());
        produto.setCategoria(categoria);

        return ProdutoResponse.fromEntity(produtoRepository.save(produto));
    }

    public List<ProdutoResponse> listarTodosProdutos() {
        return produtoRepository.findAll().stream()
                .map(ProdutoResponse::fromEntity)
                .toList();
    }

    public ProdutoResponse buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoResponse::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
    }

    @Transactional
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}
