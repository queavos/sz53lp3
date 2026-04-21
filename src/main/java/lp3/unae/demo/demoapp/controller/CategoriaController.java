/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.controller;

import java.util.ArrayList;
import java.util.List;
import lp3.unae.demo.demoapp.model.Categoria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author ossva
 */
/**
 * Controller class responsible for managing category operations.
 * Handles HTTP requests related to listing, creating, editing, 
 * and deleting {@link Categoria} entities.
 */
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private int siguienteId = 6;
    private final List<Categoria> categorias = new ArrayList<>();

    public CategoriaController() {
        this.cargarDatos();
    }

    @GetMapping("/")
    public String mostrarCategoria(Model model) {
        model.addAttribute("categorias", categorias);
        model.addAttribute("title", "Lista Categorias");
        return "categoria/index";
    }
    //   @GetMapping("/prueba")

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Categoria cat = new Categoria();
        model.addAttribute("modoedicion", false);
        model.addAttribute("categoria", cat);
        model.addAttribute("title", "Nueva Categoria");
        return "categoria/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria) {
        categoria.setId(siguienteId++);
        categorias.add(categoria);
        return "redirect:/categorias/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        categorias.removeIf(c -> c.getId() == id);
        return "redirect:/categorias/";
    }

    @GetMapping("/editar/{id}")
    private String Editar(@PathVariable int id, Model model) {
        Categoria cat = buscarPorId(id);
        model.addAttribute("modoedicion", true);
        model.addAttribute("categoria", cat);
        model.addAttribute("title", "Editar Categoria");
        return "categoria/form";
    }
    @PostMapping("/actualizar")
    private String actualizar(@ModelAttribute Categoria categoria)
    {
     Categoria catOriginal= buscarPorId(categoria.getId());
     if (catOriginal!=null)
     {
     catOriginal.setNombre(categoria.getNombre());
     }
    return "redirect:/categorias/";
    }

    private void cargarDatos() {
        categorias.add(new Categoria(1, "Utiles"));
        categorias.add(new Categoria(2, "Manuales"));
        categorias.add(new Categoria(3, "Tinas"));
        categorias.add(new Categoria(4, "Lapices"));
        categorias.add(new Categoria(5, "Boligrafos"));
    }


    private Categoria buscarPorId(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }


}
