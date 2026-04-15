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

/**
 *
 * @author ossva
 */
@Controller
public class CategoriaController {
    @GetMapping("/categoria")
    public String mostrarCategoria(Model model) 
        {
        //Categoria cat= new Categoria(1,"Utiles");
        List<Categoria> categorias= new ArrayList<>();
        categorias.add(new Categoria(1,"Utiles"));
        categorias.add(new Categoria(2,"Manuales"));
        categorias.add(new Categoria(3,"Tinas"));
        model.addAttribute("categorias", categorias);
        model.addAttribute("title", "Categorias");
        return "categoria/index";    
        } 
        @GetMapping("/prueba")
    public String prueba() 
        {
        return "index";    
        } 
    
}
