package me.dio.matheusmarti.springapi.service;

import me.dio.matheusmarti.springapi.dto.ClienteRequest;
import me.dio.matheusmarti.springapi.dto.ClienteResponse;
import me.dio.matheusmarti.springapi.model.Cliente;
import me.dio.matheusmarti.springapi.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponse criarCliente(ClienteRequest request) {
        if (clienteRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Já existe um cliente com o email: " + request.email());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());

        return ClienteResponse.fromEntity(clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteResponse atualizarCliente(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));

        if (clienteRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new IllegalArgumentException("Já existe um cliente com o email: " + request.email());
        }

        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());

        return ClienteResponse.fromEntity(clienteRepository.save(cliente));
    }

    public List<ClienteResponse> listarTodosClientes() {
        return clienteRepository.findAll().stream()
                .map(ClienteResponse::fromEntity)
                .toList();
    }

    public ClienteResponse buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteResponse::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
