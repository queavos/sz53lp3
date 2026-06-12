/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.services;

/**
 *
 * @author queavos
 */
import lp3.unae.demo.demoapp.model.Cliente;
import lp3.unae.demo.demoapp.repositories.ClienteRepositoryJPA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepositoryJPA repositorio;

    public ClienteService(ClienteRepositoryJPA repositorio) {
        this.repositorio = repositorio;
    }

    public List<Cliente> listar() {
        return repositorio.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Cliente guardar(Cliente cliente) {
        validar(cliente);
        return repositorio.save(cliente);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    public void validar(Cliente cliente) {

        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }

        if (cliente.getDocumento() == null || cliente.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento del cliente no puede estar vacío");
        }
    }
}