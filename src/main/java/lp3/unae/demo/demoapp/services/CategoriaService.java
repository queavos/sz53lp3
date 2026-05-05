/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.services;

import lp3.unae.demo.demoapp.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import lp3.unae.demo.demoapp.model.Categoria;
import lp3.unae.demo.demoapp.repositories.CategoriaRepositoryJPA;

/**
 *
 * @author ossva
 */
@Service
public class CategoriaService {
    private final CategoriaRepositoryJPA catRepo;

    public CategoriaService(CategoriaRepositoryJPA catRepo) {
        this.catRepo = catRepo;
    }
    /*
    listar
    buscarporid
            crear
 actualizar
                    eliminar
      */
    public List<Categoria> listar(){
        return catRepo.findAllByOrderByNombreAsc();
    }
    public Categoria buscarpoid(int id)
    {
        //catRepo.findById(id)
        return catRepo.findById(id).get();
    }
    public void crear(Categoria cat){
        this.validarCategoria(cat);
        cat.setNombre(cat.getNombre().trim());
        catRepo.save(cat);
    }
        public void actualizar(Categoria cat){
        this.validarCategoria(cat);
        cat.setNombre(cat.getNombre().trim());
        catRepo.save (cat);
    }
         public void eliminar(int id){
        catRepo.deleteById(id);
        
    }   
        public void validarCategoria(Categoria cat)
    {
        if (cat == null || cat.getNombre() == null || cat.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        
        if (catRepo.findByNombre(cat.getNombre())!= null){
        throw new IllegalArgumentException("El nombre de la categoría ya existe");
        }
         
    }
}
