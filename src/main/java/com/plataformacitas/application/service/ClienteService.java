package com.plataformacitas.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plataformacitas.domain.model.Cliente;
import com.plataformacitas.infrastructure.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}