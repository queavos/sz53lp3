package lp3.unae.demo.demoapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class NotaEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Long nro;

    private String proveedor;

    private String observacion;

    private Integer verificado = 0;

    private Integer procesado = 0;

    @OneToMany(
        mappedBy = "notaEnvio",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<DetalleNotaEnvio> detalles = new ArrayList<>();

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

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getVerificado() {
        return verificado;
    }

    public void setVerificado(Integer verificado) {
        this.verificado = verificado;
    }

    public Integer getProcesado() {
        return procesado;
    }

    public void setProcesado(Integer procesado) {
        this.procesado = procesado;
    }

    public List<DetalleNotaEnvio> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleNotaEnvio> detalles) {
        this.detalles = detalles;
    }

    @Transient
    public String getEstadoTexto() {

        if (verificado == null || procesado == null) {
            return "Sin estado";
        }

        if (verificado == 0 && procesado == 0) {
            return "Nueva";
        }

        if (verificado == 1 && procesado == 0) {
            return "Verificada";
        }

        if (verificado == 1 && procesado == 1) {
            return "Procesada";
        }

        return "Estado inconsistente";
    }

    @Transient
    public Boolean getEditable() {
        return verificado != null
                && procesado != null
                && verificado == 0
                && procesado == 0;
    }

    @Override
    public String toString() {
        return "NotaEnvio{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", nro=" + nro +
                ", proveedor=" + proveedor +
                ", observacion=" + observacion +
                ", verificado=" + verificado +
                ", procesado=" + procesado +
                '}';
    }
}
