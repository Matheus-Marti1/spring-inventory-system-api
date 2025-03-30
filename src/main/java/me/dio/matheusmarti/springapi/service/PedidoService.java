package me.dio.matheusmarti.springapi.service;

import me.dio.matheusmarti.springapi.dto.ItemPedidoRequest;
import me.dio.matheusmarti.springapi.dto.PedidoRequest;
import me.dio.matheusmarti.springapi.dto.PedidoResponse;
import me.dio.matheusmarti.springapi.model.Cliente;
import me.dio.matheusmarti.springapi.model.ItemPedido;
import me.dio.matheusmarti.springapi.model.Pedido;
import me.dio.matheusmarti.springapi.model.Produto;
import me.dio.matheusmarti.springapi.repository.ClienteRepository;
import me.dio.matheusmarti.springapi.repository.PedidoRepository;
import me.dio.matheusmarti.springapi.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + request.clienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());

        for (ItemPedidoRequest itemRequest : request.itens()) {
            Produto produto = produtoRepository.findById(itemRequest.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + itemRequest.produtoId()));

            if (produto.getEstoque() < itemRequest.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemRequest.quantidade());
            item.setPrecoUnitario(produto.getPreco()); // Usa o preço atual do produto

            pedido.addItem(item);

            produto.setEstoque(produto.getEstoque() - itemRequest.quantidade());
            produtoRepository.save(produto);
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return PedidoResponse.fromEntity(pedidoSalvo);
    }

    public List<PedidoResponse> listarTodosPedidos() {
        return pedidoRepository.findAllWithItens().stream()
                .map(PedidoResponse::fromEntity)
                .toList();
    }

    public PedidoResponse buscarPedidoPorId(Long id) {
        return pedidoRepository.findByIdWithItens(id)
                .map(PedidoResponse::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));
    }
}
