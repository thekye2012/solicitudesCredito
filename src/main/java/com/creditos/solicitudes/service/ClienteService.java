package com.creditos.solicitudes.service;

import com.creditos.solicitudes.dto.ClienteDTO;
import com.creditos.solicitudes.entities.Cliente;
import com.creditos.solicitudes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // Buscar cliente por datos personales
    public Cliente buscarCliente(String nombre, String apellidoPaterno, String apellidoMaterno) {
        return clienteRepository.findByNombreAndApellidoPaternoAndApellidoMaterno(nombre, apellidoPaterno, apellidoMaterno);
    }

    public Cliente altaCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellidoPaterno(clienteDTO.getApellidoPaterno());
        cliente.setApellidoMaterno(clienteDTO.getApellidoMaterno());
        return clienteRepository.save(cliente);
    }
}