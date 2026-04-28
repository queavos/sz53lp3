/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.repositories;

import java.util.ArrayList;
import java.util.List;
import lp3.unae.demo.demoapp.model.Categoria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ossva
 */
@Repository
public class CategoriaRepository {

    private int siguienteId = 6;
    private final List<Categoria> categorias = new ArrayList<>();

    public CategoriaRepository() {
        this.cargarDatos();
    }

    private void cargarDatos() {
        categorias.add(new Categoria(1, "Utiles"));
        categorias.add(new Categoria(2, "Manuales"));
        categorias.add(new Categoria(3, "Tinas"));
        categorias.add(new Categoria(4, "Lapices"));
        categorias.add(new Categoria(5, "Boligrafos"));
    }

    public List<Categoria> findAll() {
        return categorias;
    }

    public Categoria findById(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public void save(Categoria categoria) {
        categoria.setId(siguienteId++);
        categorias.add(categoria);
    }
    public void deleteById(int id) {
        categorias.removeIf(c -> c.getId() == id);
    }
    public void update(Categoria categoria) {
       Categoria cat=this.findById(categoria.getId());
       if (cat!=null){
           cat.setNombre(categoria.getNombre());
        }
    }

    public Categoria findByNombre(String nombre){
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }


}
