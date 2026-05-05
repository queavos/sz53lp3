/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.controller;

import lp3.unae.demo.demoapp.services.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ossva
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService servicio;

    public ProductoController(ProductoService servicio) {
        this.servicio = servicio;
    }
    @GetMapping("/")
    public String listar (Model model)
    {
    model.addAttribute("datos",servicio.lista());
    model.addAttribute("title", "Lista de Productos");
    return "productos/lista";
    }
    
    
    
}
