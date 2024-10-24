package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class RegistroInicioSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correo;

    private LocalDateTime fechaHora;

    private String ipSistema;

    private String resultado;  // "CORRECTO" o "FALLIDO"

    private String descripcionFallida;  // Descripción de la falla (opcional)
    
    

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String nombreUsuario) {
        this.correo = nombreUsuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getIpSistema() {
        return ipSistema;
    }

    public void setIpSistema(String ipSistema) {
        this.ipSistema = ipSistema;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDescripcionFallida() {
        return descripcionFallida;
    }

    public void setDescripcionFallida(String descripcionFallida) {
        this.descripcionFallida = descripcionFallida;
    }
}
