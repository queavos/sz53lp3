/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.services;

/**
 *
 * @author queavos
 */
import lp3.unae.demo.demoapp.model.DetalleVenta;
import lp3.unae.demo.demoapp.model.Producto;
import lp3.unae.demo.demoapp.model.Venta;
import lp3.unae.demo.demoapp.repositories.ProductoRepositoryJPA;
import lp3.unae.demo.demoapp.repositories.VentaRepositoryJPA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    private final VentaRepositoryJPA ventaRepository;
    private final ProductoRepositoryJPA productoRepository;

    public VentaService(VentaRepositoryJPA ventaRepository,
                        ProductoRepositoryJPA productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta guardar(Venta venta) {

        if (venta.getCliente() == null) {
            throw new RuntimeException("La venta debe tener un cliente.");
        }

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un producto.");
        }

        for (DetalleVenta detalle : venta.getDetalles()) {
            detalle.setVenta(venta);
            detalle.calcularImportes();
        }

        venta.calcularTotales();

        return ventaRepository.save(venta);
    }

    public void procesar(Long ventaId) {

        Venta venta = buscarPorId(ventaId);

        if (venta == null) {
            throw new RuntimeException("La venta no existe.");
        }

        if (venta.estaProcesada()) {
            throw new RuntimeException("La venta ya fue procesada.");
        }

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new RuntimeException("No se puede procesar una venta sin detalles.");
        }

        validarStockDisponible(venta);
        descontarStock(venta);

        venta.calcularTotales();
        venta.setProcesado(1);

        ventaRepository.save(venta);
    }

    private void validarStockDisponible(Venta venta) {

        for (DetalleVenta detalle : venta.getDetalles()) {

            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a cero.");
            }

            Producto producto = detalle.getProducto();

            if (producto == null || producto.getId() == null) {
                throw new RuntimeException("El detalle contiene un producto inválido.");
            }

            Producto productoBD = productoRepository.findById(producto.getId()).orElse(null);

            if (productoBD == null) {
                throw new RuntimeException("El producto no existe.");
            }

            if (productoBD.getStock() == null) {
                throw new RuntimeException("El producto no tiene stock definido: " + productoBD.getNombre());
            }

            if (productoBD.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + productoBD.getNombre());
            }
        }
    }

    private void descontarStock(Venta venta) {

        for (DetalleVenta detalle : venta.getDetalles()) {

            Producto productoBD = productoRepository.findById(detalle.getProducto().getId()).orElse(null);

            productoBD.setStock(productoBD.getStock() - detalle.getCantidad());

            productoRepository.save(productoBD);
        }
    }

    public void eliminar(Long id) {

        Venta venta = buscarPorId(id);

        if (venta == null) {
            return;
        }

        if (venta.estaProcesada()) {
            throw new RuntimeException("No se puede eliminar una venta procesada.");
        }

        ventaRepository.deleteById(id);
    }
}
