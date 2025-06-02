package me.dio.matheusmarti.springapi.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import me.dio.matheusmarti.springapi.dto.PedidoRequest;
import me.dio.matheusmarti.springapi.dto.PedidoResponse;
import me.dio.matheusmarti.springapi.dto.PedidoResumoResponse;
import me.dio.matheusmarti.springapi.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar um novo pedido", description = "Registra um pedido no sistema")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Estoque insuficiente ou dados inválidos")
    public PedidoResponse criarPedido(@RequestBody PedidoRequest request) {
        return pedidoService.criarPedido(request);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista de todos os pedidos registrados")
    public List<PedidoResponse> listarTodosPedidos() {
        return pedidoService.listarTodosPedidos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna os detalhes de um pedido específico")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public PedidoResponse buscarPedidoPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id);
    }

    @Operation(
            summary = "Consulta avançada de pedidos",
            description = "Consulta pedidos por cliente, período e faixa de valor total usando stored procedure. Todos os parâmetros são opcionais.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de pedidos resumidos",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    array = @ArraySchema(schema = @Schema(implementation = PedidoResumoResponse.class))
                            )
                    )
            }
    )
    @GetMapping("/consulta-avancada")
    public List<PedidoResumoResponse> consultarPedidosAvancado(
            @Parameter(description = "ID do cliente (opcional)")
            @RequestParam(required = false) Long clienteId,

            @Parameter(description = "Data inicial do pedido (opcional, formato yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,

            @Parameter(description = "Data final do pedido (opcional, formato yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,

            @Parameter(description = "Valor mínimo total do pedido (opcional)")
            @RequestParam(required = false) BigDecimal totalMin,

            @Parameter(description = "Valor máximo total do pedido (opcional)")
            @RequestParam(required = false) BigDecimal totalMax
    ) {
        return pedidoService.consultarPedidosAvancado(clienteId, dataInicio, dataFim, totalMin, totalMax);
    }
}
