
package lp3.unae.demo.demoapp.services;
/*
import java.util.List;

import lp3.unae.demo.demoapp.model.DetalleNotaEnvio;
import lp3.unae.demo.demoapp.model.NotaEnvio;
import lp3.unae.demo.demoapp.repositories.DetalleNotaEnvioRepositoryJPA;
import lp3.unae.demo.demoapp.repositories.NotaEnvioRepositoryJPA;

import org.springframework.stereotype.Service;

@Service
public class NotaEnvioService {

    private final NotaEnvioRepositoryJPA notaEnvioRepository;

    private final DetalleNotaEnvioRepositoryJPA detalleRepository;

    public NotaEnvioService(
            NotaEnvioRepositoryJPA notaEnvioRepository,
            DetalleNotaEnvioRepositoryJPA detalleRepository) {

        this.notaEnvioRepository = notaEnvioRepository;
        this.detalleRepository = detalleRepository;
    }

    public List<NotaEnvio> listar() {
        return notaEnvioRepository.findAll();
    }

    public NotaEnvio buscarPorId(Long id) {
        return notaEnvioRepository.findById(id).orElse(null);
    }

    public NotaEnvio guardar(NotaEnvio nota) {
        return notaEnvioRepository.save(nota);
    }

    public Boolean esNuevaEditable(NotaEnvio nota) {
        return nota != null
                && nota.getVerificado() != null
                && nota.getProcesado() != null
                && nota.getVerificado() == 0
                && nota.getProcesado() == 0;
    }

    public void actualizarCabecera(Long id, NotaEnvio datosFormulario) {

        NotaEnvio nota = buscarPorId(id);

        if (!esNuevaEditable(nota)) {
            return;
        }

        nota.setFecha(datosFormulario.getFecha());
        nota.setNro(datosFormulario.getNro());
        nota.setProveedor(datosFormulario.getProveedor());
        nota.setObservacion(datosFormulario.getObservacion());

        notaEnvioRepository.save(nota);
    }

    public void agregarDetalleGuardado(Long notaId, DetalleNotaEnvio detalle) {

        NotaEnvio nota = buscarPorId(notaId);

        if (!esNuevaEditable(nota)) {
            return;
        }

        if (detalle == null || detalle.getProducto() == null) {
            return;
        }

        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            return;
        }

        if (detalle.getPrecio() == null || detalle.getPrecio() <= 0) {
            return;
        }

        detalle.setNotaEnvio(nota);
        nota.getDetalles().add(detalle);

        notaEnvioRepository.save(nota);
    }

    public void eliminarDetalleGuardado(Long notaId, Long detalleId) {

        NotaEnvio nota = buscarPorId(notaId);

        if (!esNuevaEditable(nota)) {
            return;
        }

        DetalleNotaEnvio detalle = detalleRepository.findById(detalleId).orElse(null);

        if (detalle == null) {
            return;
        }

        if (detalle.getNotaEnvio() == null || detalle.getNotaEnvio().getId() == null) {
            return;
        }

        if (!detalle.getNotaEnvio().getId().equals(notaId)) {
            return;
        }

        detalleRepository.delete(detalle);
    }

    public void eliminarNota(Long id) {

        NotaEnvio nota = buscarPorId(id);

        if (!esNuevaEditable(nota)) {
            return;
        }

        notaEnvioRepository.delete(nota);
    }
}
*/

import java.util.List;

import lp3.unae.demo.demoapp.model.DetalleNotaEnvio;
import lp3.unae.demo.demoapp.model.NotaEnvio;
import lp3.unae.demo.demoapp.model.Producto;
import lp3.unae.demo.demoapp.repositories.DetalleNotaEnvioRepositoryJPA;
import lp3.unae.demo.demoapp.repositories.NotaEnvioRepositoryJPA;

import org.springframework.stereotype.Service;

@Service
public class NotaEnvioService {

    private final NotaEnvioRepositoryJPA notaEnvioRepository;

    private final DetalleNotaEnvioRepositoryJPA detalleRepository;

    private final ProductoService productoService;

    public NotaEnvioService(
            NotaEnvioRepositoryJPA notaEnvioRepository,
            DetalleNotaEnvioRepositoryJPA detalleRepository,
            ProductoService productoService) {

        this.notaEnvioRepository = notaEnvioRepository;
        this.detalleRepository = detalleRepository;
        this.productoService = productoService;
    }

    public List<NotaEnvio> listar() {
        return notaEnvioRepository.findAll();
    }

    public NotaEnvio buscarPorId(Long id) {
        return notaEnvioRepository.findById(id).orElse(null);
    }

    public NotaEnvio guardar(NotaEnvio nota) {
        return notaEnvioRepository.save(nota);
    }

    public Boolean esNuevaEditable(NotaEnvio nota) {
        return nota != null
                && nota.getVerificado() != null
                && nota.getProcesado() != null
                && nota.getVerificado() == 0
                && nota.getProcesado() == 0;
    }

    public Boolean esVerificadaNoProcesada(NotaEnvio nota) {
        return nota != null
                && nota.getVerificado() != null
                && nota.getProcesado() != null
                && nota.getVerificado() == 1
                && nota.getProcesado() == 0;
    }

    public Boolean esProcesada(NotaEnvio nota) {
        return nota != null
                && nota.getVerificado() != null
                && nota.getProcesado() != null
                && nota.getVerificado() == 1
                && nota.getProcesado() == 1;
    }

    public void actualizarCabecera(Long id, NotaEnvio datosFormulario) {

        NotaEnvio nota = buscarPorId(id);

        if (!esNuevaEditable(nota)) {
            return;
        }

        nota.setFecha(datosFormulario.getFecha());
        nota.setNro(datosFormulario.getNro());
        nota.setProveedor(datosFormulario.getProveedor());
        nota.setObservacion(datosFormulario.getObservacion());

        notaEnvioRepository.save(nota);
    }

    public void agregarDetalleGuardado(Long notaId, DetalleNotaEnvio detalle) {

        NotaEnvio nota = buscarPorId(notaId);

        if (!esNuevaEditable(nota)) {
            return;
        }

        if (detalle == null || detalle.getProducto() == null) {
            return;
        }

        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            return;
        }

        if (detalle.getPrecio() == null || detalle.getPrecio() <= 0) {
            return;
        }

        detalle.setNotaEnvio(nota);
        nota.getDetalles().add(detalle);

        notaEnvioRepository.save(nota);
    }

    public void eliminarDetalleGuardado(Long notaId, Long detalleId) {

        NotaEnvio nota = buscarPorId(notaId);

        if (!esNuevaEditable(nota)) {
            return;
        }

        DetalleNotaEnvio detalle = detalleRepository.findById(detalleId).orElse(null);

        if (detalle == null) {
            return;
        }

        if (detalle.getNotaEnvio() == null || detalle.getNotaEnvio().getId() == null) {
            return;
        }

        if (!detalle.getNotaEnvio().getId().equals(notaId)) {
            return;
        }

        detalleRepository.delete(detalle);
    }

    public void eliminarNota(Long id) {

        NotaEnvio nota = buscarPorId(id);

        if (!esNuevaEditable(nota)) {
            return;
        }

        notaEnvioRepository.delete(nota);
    }

    public void verificar(Long id) {

        NotaEnvio nota = buscarPorId(id);

        if (!esNuevaEditable(nota)) {
            return;
        }

        if (nota.getDetalles() == null || nota.getDetalles().isEmpty()) {
            return;
        }

        nota.setVerificado(1);
        nota.setProcesado(0);

        notaEnvioRepository.save(nota);
    }

    public void desverificar(Long id) {

        NotaEnvio nota = buscarPorId(id);

        if (!esVerificadaNoProcesada(nota)) {
            return;
        }

        nota.setVerificado(0);
        nota.setProcesado(0);

        notaEnvioRepository.save(nota);
    }

    public void procesar(Long id) {

        NotaEnvio nota = buscarPorId(id);

        if (!esVerificadaNoProcesada(nota)) {
            return;
        }

        if (nota.getDetalles() == null || nota.getDetalles().isEmpty()) {
            return;
        }

        for (DetalleNotaEnvio detalle : nota.getDetalles()) {

            Producto producto = detalle.getProducto();

            if (producto == null) {
                continue;
            }

            Integer stockActual = producto.getStock();

            if (stockActual == null) {
                stockActual = 0;
            }

            Integer cantidadRecibida = detalle.getCantidad();

            if (cantidadRecibida == null) {
                cantidadRecibida = 0;
            }

            producto.setStock(stockActual + cantidadRecibida);

            if (detalle.getPrecio() != null) {
                producto.setPrecio(detalle.getPrecio());
            }

            productoService.guardar(producto);
        }

        nota.setVerificado(1);
        nota.setProcesado(1);

        notaEnvioRepository.save(nota);
    }
}

