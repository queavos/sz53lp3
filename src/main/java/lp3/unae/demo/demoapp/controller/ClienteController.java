/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.controller;

/**
 *
 * @author queavos
 */
import lp3.unae.demo.demoapp.model.Cliente;
import lp3.unae.demo.demoapp.services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService servicio;

    public ClienteController(ClienteService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("title", "Clientes");
        model.addAttribute("clientes", servicio.listar());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("title", "Nuevo cliente");
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        servicio.guardar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Cliente cliente = servicio.buscarPorId(id);

        if (cliente == null) {
            return "redirect:/clientes";
        }

        model.addAttribute("title", "Editar cliente");
        model.addAttribute("cliente", cliente);

        return "clientes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/clientes";
    }
}