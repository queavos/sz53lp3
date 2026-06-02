/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.controller;

import java.util.List;
import lp3.unae.demo.demoapp.model.Categoria;
import lp3.unae.demo.demoapp.model.Producto;
import lp3.unae.demo.demoapp.services.CategoriaService;
import lp3.unae.demo.demoapp.services.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ossva
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService servicio;
    private final CategoriaService catServ;

    public ProductoController(ProductoService servicio, CategoriaService catServ ) {
        this.servicio = servicio;
       // this.catServ = null;
        this.catServ = catServ;
    }

    @GetMapping("/")
    public String listar(Model model) {
        List<Producto> datos = servicio.lista();
        model.addAttribute("datos", datos);
        model.addAttribute("title", "Lista de Productos");
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Producto dato = new Producto();
        List<Categoria> categorias = catServ.listar();
        model.addAttribute("modoedicion", false);
        model.addAttribute("dato", dato);
        model.addAttribute("categorias", categorias);

        model.addAttribute("title", "Nueva Producto");
        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto dato, Model model) {
        try {
            servicio.guardar(dato);
            return "redirect:/productos/";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("modoedicion", false);
            model.addAttribute("dato", dato);
            model.addAttribute("title", "Nueva Producto");
            return "productos/form";
        }
        // productos.setId(siguienteId++);
        //productoss.add(productos);

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        // productoss.removeIf(c -> c.getId() == id);
        servicio.eliminar(id);
        return "redirect:/productos/";
    }

    @GetMapping("/editar/{id}")
    private String Editar(@PathVariable Long id, Model model) {
        Producto dato = servicio.buscarPorId(id);
        List<Categoria> categorias = catServ.listar();
        model.addAttribute("modoedicion", true);
        model.addAttribute("dato", dato);
        model.addAttribute("categorias", categorias);
        model.addAttribute("title", "Editar Producto");
        return "productos/form";
    }

    @PostMapping("/actualizar")
    private String actualizar(@ModelAttribute Producto dato, Model model) {
        try {
            servicio.guardar(dato);
            return "redirect:/productos/";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("modoedicion", true);
            model.addAttribute("dato", dato);
            model.addAttribute("title", "Nueva Producto");
            return "productos/form";
        }

    }

}
