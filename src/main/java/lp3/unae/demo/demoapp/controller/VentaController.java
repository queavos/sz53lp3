/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.controller;

/**
 *
 * @author queavos
 */
import jakarta.servlet.http.HttpSession;
import lp3.unae.demo.demoapp.model.Cliente;
import lp3.unae.demo.demoapp.model.DetalleVenta;
import lp3.unae.demo.demoapp.model.Producto;
import lp3.unae.demo.demoapp.model.Venta;
import lp3.unae.demo.demoapp.services.ClienteService;
import lp3.unae.demo.demoapp.services.ProductoService;
import lp3.unae.demo.demoapp.services.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    public VentaController(VentaService ventaService,
                           ClienteService clienteService,
                           ProductoService productoService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("title", "Ventas");
        model.addAttribute("ventas", ventaService.listar());
        return "ventas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model, HttpSession session) {

        session.setAttribute("detalleVenta", new ArrayList<DetalleVenta>());

        model.addAttribute("title", "Nueva venta");
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("detalleVenta", obtenerDetalle(session));

        return "ventas/form";
    }

    @GetMapping("/formulario")
    public String formulario(Model model, HttpSession session) {

        model.addAttribute("title", "Nueva venta");
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("detalleVenta", obtenerDetalle(session));

        return "ventas/form";
    }

    @PostMapping("/agregar-detalle")
    public String agregarDetalle(@RequestParam Long productoId,
                                 @RequestParam Integer cantidad,
                                 HttpSession session) {

        Producto producto = productoService.buscarPorId(productoId);

        if (producto == null) {
            return "redirect:/ventas/formulario";
        }

        if (cantidad == null || cantidad <= 0) {
            return "redirect:/ventas/formulario";
        }

        if (producto.getStock() == null || producto.getStock() < cantidad) {
            return "redirect:/ventas/formulario";
        }

        List<DetalleVenta> detalleVenta = obtenerDetalle(session);

        DetalleVenta detalle = new DetalleVenta(producto, cantidad, producto.getPrecio());

        detalleVenta.add(detalle);

        session.setAttribute("detalleVenta", detalleVenta);

        return "redirect:/ventas/formulario";
    }

    @GetMapping("/quitar-detalle/{index}")
    public String quitarDetalle(@PathVariable int index, HttpSession session) {

        List<DetalleVenta> detalleVenta = obtenerDetalle(session);

        if (index >= 0 && index < detalleVenta.size()) {
            detalleVenta.remove(index);
        }

        session.setAttribute("detalleVenta", detalleVenta);

        return "redirect:/ventas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long clienteId,
                          HttpSession session) {

        Cliente cliente = clienteService.buscarPorId(clienteId);

        if (cliente == null) {
            return "redirect:/ventas/formulario";
        }

        List<DetalleVenta> detalleVenta = obtenerDetalle(session);

        if (detalleVenta.isEmpty()) {
            return "redirect:/ventas/formulario";
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);

        for (DetalleVenta detalle : detalleVenta) {
            venta.agregarDetalle(detalle);
        }

        ventaService.guardar(venta);

        session.removeAttribute("detalleVenta");

        return "redirect:/ventas";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {

        Venta venta = ventaService.buscarPorId(id);

        if (venta == null) {
            return "redirect:/ventas";
        }

        model.addAttribute("title", "Consultar venta");
        model.addAttribute("venta", venta);

        return "ventas/ver";
    }

    @GetMapping("/procesar/{id}")
    public String procesar(@PathVariable Long id) {
        ventaService.procesar(id);
        return "redirect:/ventas/ver/" + id;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return "redirect:/ventas";
    }

    @SuppressWarnings("unchecked")
    private List<DetalleVenta> obtenerDetalle(HttpSession session) {

        List<DetalleVenta> detalleVenta =
                (List<DetalleVenta>) session.getAttribute("detalleVenta");

        if (detalleVenta == null) {
            detalleVenta = new ArrayList<>();
            session.setAttribute("detalleVenta", detalleVenta);
        }

        return detalleVenta;
    }
}
