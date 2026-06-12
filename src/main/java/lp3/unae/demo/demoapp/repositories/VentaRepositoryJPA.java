/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lp3.unae.demo.demoapp.repositories;

/**
 *
 * @author queavos
 */
import lp3.unae.demo.demoapp.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepositoryJPA extends JpaRepository<Venta, Long> {
}
