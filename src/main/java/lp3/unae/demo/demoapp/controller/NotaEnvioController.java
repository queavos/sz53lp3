/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import lp3.unae.demo.demoapp.model.DetalleNotaEnvio;
import lp3.unae.demo.demoapp.model.NotaEnvio;
import lp3.unae.demo.demoapp.model.Producto;
import lp3.unae.demo.demoapp.services.NotaEnvioService;
import lp3.unae.demo.demoapp.services.ProductoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notas")
public class NotaEnvioController {

    private final NotaEnvioService notaEnvioService;

    private final ProductoService productoService;

    public NotaEnvioController(
            NotaEnvioService notaEnvioService,
            ProductoService productoService) {

        this.notaEnvioService = notaEnvioService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("title", "Notas de Envío");
        model.addAttribute("notas", notaEnvioService.listar());

        return "notas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model, HttpSession session) {

        NotaEnvio nota = obtenerNotaTemporal(session);
        List<DetalleNotaEnvio> detalles = obtenerDetalles(session);

        model.addAttribute("title", "Nueva Nota de Envío");
        model.addAttribute("nota", nota);
        model.addAttribute("detalles", detalles);
        model.addAttribute("productos", productoService.lista());

        return "notas/form";
    }

    @PostMapping("/detalle/agregar")
    public String agregarDetalleTemporal(
            @ModelAttribute NotaEnvio nota,
            @RequestParam(required = false) Long productoId,
            @RequestParam(required = false) Integer cantidad,
            @RequestParam(required = false) Double precio,
            HttpSession session) {

        nota.setVerificado(0);
        nota.setProcesado(0);
        session.setAttribute("notaTemporal", nota);

        if (productoId == null || cantidad == null || precio == null) {
            return "redirect:/notas/nueva";
        }

        if (cantidad <= 0 || precio <= 0) {
            return "redirect:/notas/nueva";
        }

        Producto producto = productoService.buscarPorId(productoId);

        if (producto == null) {
            return "redirect:/notas/nueva";
        }

        DetalleNotaEnvio detalle = new DetalleNotaEnvio();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecio(precio);

        List<DetalleNotaEnvio> detalles = obtenerDetalles(session);
        detalles.add(detalle);

        session.setAttribute("detallesNota", detalles);

        return "redirect:/notas/nueva";
    }

    @GetMapping("/detalle/eliminar/{index}")
    public String eliminarDetalleTemporal(
            @PathVariable Integer index,
            HttpSession session) {

        List<DetalleNotaEnvio> detalles = obtenerDetalles(session);

        if (index >= 0 && index < detalles.size()) {
            detalles.remove(index.intValue());
        }

        session.setAttribute("detallesNota", detalles);

        return "redirect:/notas/nueva";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute NotaEnvio nota,
            HttpSession session) {

        List<DetalleNotaEnvio> detalles = obtenerDetalles(session);

        if (detalles.isEmpty()) {
            session.setAttribute("notaTemporal", nota);
            return "redirect:/notas/nueva";
        }

        nota.setVerificado(0);
        nota.setProcesado(0);

        for (DetalleNotaEnvio detalle : detalles) {
            detalle.setNotaEnvio(nota);
        }

        nota.setDetalles(detalles);

        NotaEnvio notaGuardada = notaEnvioService.guardar(nota);

        session.removeAttribute("notaTemporal");
        session.removeAttribute("detallesNota");

        return "redirect:/notas/ver/" + notaGuardada.getId();
    }

    @GetMapping("/ver/{id}")
    public String ver(
            @PathVariable Long id,
            Model model) {

        NotaEnvio nota = notaEnvioService.buscarPorId(id);

        model.addAttribute("title", "Detalle de Nota de Envío");
        model.addAttribute("nota", nota);
        model.addAttribute("productos", productoService.lista());

        return "notas/ver";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        NotaEnvio nota = notaEnvioService.buscarPorId(id);

        model.addAttribute("title", "Editar Nota de Envío");
        model.addAttribute("nota", nota);

        return "notas/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @ModelAttribute NotaEnvio nota) {

        notaEnvioService.actualizarCabecera(id, nota);

        return "redirect:/notas/ver/" + id;
    }

    @PostMapping("/detalle/agregar-guardado/{id}")
    public String agregarDetalleGuardado(
            @PathVariable Long id,
            @RequestParam(required = false) Long productoId,
            @RequestParam(required = false) Integer cantidad,
            @RequestParam(required = false) Double precio) {

        Producto producto = productoService.buscarPorId(productoId);

        DetalleNotaEnvio detalle = new DetalleNotaEnvio();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecio(precio);

        notaEnvioService.agregarDetalleGuardado(id, detalle);

        return "redirect:/notas/ver/" + id;
    }

    @GetMapping("/detalle/eliminar-guardado/{notaId}/{detalleId}")
    public String eliminarDetalleGuardado(
            @PathVariable Long notaId,
            @PathVariable Long detalleId) {

        notaEnvioService.eliminarDetalleGuardado(notaId, detalleId);

        return "redirect:/notas/ver/" + notaId;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarNota(@PathVariable Long id) {

        notaEnvioService.eliminarNota(id);

        return "redirect:/notas";
    }

    @SuppressWarnings("unchecked")
    private List<DetalleNotaEnvio> obtenerDetalles(HttpSession session) {

        List<DetalleNotaEnvio> detalles
                = (List<DetalleNotaEnvio>) session.getAttribute("detallesNota");

        if (detalles == null) {
            detalles = new ArrayList<>();
            session.setAttribute("detallesNota", detalles);
        }

        return detalles;
    }

    private NotaEnvio obtenerNotaTemporal(HttpSession session) {

        NotaEnvio nota = (NotaEnvio) session.getAttribute("notaTemporal");

        if (nota == null) {
            nota = new NotaEnvio();
            nota.setFecha(LocalDate.now());
            nota.setVerificado(0);
            nota.setProcesado(0);
            session.setAttribute("notaTemporal", nota);
        }

        return nota;
    }

    @PostMapping("/verificar/{id}")
    public String verificar(@PathVariable Long id) {

        notaEnvioService.verificar(id);

        return "redirect:/notas/ver/" + id;
    }

    @PostMapping("/desverificar/{id}")
    public String desverificar(@PathVariable Long id) {

        notaEnvioService.desverificar(id);

        return "redirect:/notas/ver/" + id;
    }

    @PostMapping("/procesar/{id}")
    public String procesar(@PathVariable Long id) {

        notaEnvioService.procesar(id);

        return "redirect:/notas/ver/" + id;
    }
}
