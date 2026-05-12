/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lp3.unae.demo.demoapp.repositories;

import java.util.List;
import lp3.unae.demo.demoapp.model.Categoria;
import lp3.unae.demo.demoapp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ossva
 */
public interface ProductoRepositoryJPA extends JpaRepository<Producto, Integer> {
    Producto findByNombre(String nombre);
    List<Producto> findAllByOrderByIdAsc();
    List<Producto> findAllByOrderByIdDesc();
    List<Producto> findAllByOrderByNombreAsc();
    List<Producto> findAllByOrderByNombreDesc();
    List<Producto> findByCategoriaOrderByNombreAsc(Categoria categoria);
    
    
}
