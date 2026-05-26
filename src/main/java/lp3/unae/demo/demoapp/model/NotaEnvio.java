/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ossva
 */
@Entity
public class NotaEnvio {

    /* 
    Id
    fecha
    nro
    detalles
    confirmado
    procesado
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(nullable = false, length = 100);
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private Long nro;
    
    @OneToMany(
            mappedBy="notaEnvio",
            cascade =CascadeType.ALL,
            fetch= FetchType.EAGER
    )
    private List<DetalleNotaEnvio> detalles = new ArrayList<>();
    private Boolean confirmado;
    private Boolean porcesado;

    public NotaEnvio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getNro() {
        return nro;
    }

    public void setNro(Long nro) {
        this.nro = nro;
    }

    public List<DetalleNotaEnvio> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleNotaEnvio> detalles) {
        this.detalles = detalles;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Boolean getPorcesado() {
        return porcesado;
    }

    public void setPorcesado(Boolean porcesado) {
        this.porcesado = porcesado;
    }
    
    
}
