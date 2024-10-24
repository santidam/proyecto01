/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.TipoUsuario;
import jakarta.persistence.*;
import java.util.List;
import jakarta.validation.constraints.*;



@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;
    
    @Email(message = "El correo electrónico debe ser válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;
    
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;  // SUPERADMIN, ADMIN, USUARIO_FINAL
    
    @JsonIgnore  // Evita la serialización infinita
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Actividad> actividades;
    
    @JsonIgnore  // Evita la serialización infinita
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Recompensa> recompensas;
    
    private int intentosFallidos = 0;

    private boolean bloqueado = false;

    // Getters y Setters

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }
    public void addIntentosFallidos(){
        this.intentosFallidos++;
        
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<Recompensa> getRecompensas() {
        return recompensas;
    }

    public void setRecompensas(List<Recompensa> recompensas) {
        this.recompensas = recompensas;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
}

