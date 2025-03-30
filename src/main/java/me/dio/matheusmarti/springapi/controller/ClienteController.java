package me.dio.matheusmarti.springapi.controller;

import me.dio.matheusmarti.springapi.dto.ClienteRequest;
import me.dio.matheusmarti.springapi.dto.ClienteResponse;
import me.dio.matheusmarti.springapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar um novo cliente", description = "Cadastra um cliente no sistema")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    public ClienteResponse criarCliente(@RequestBody ClienteRequest request) {
        return clienteService.criarCliente(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    public ClienteResponse atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest request) {
        return clienteService.atualizarCliente(id, request);
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados")
    public List<ClienteResponse> listarTodosClientes() {
        return clienteService.listarTodosClientes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente específico")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ClienteResponse buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar um cliente", description = "Remove um cliente do sistema")
    @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso")
    public void deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
    }
}
