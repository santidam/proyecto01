/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Actividad;
import model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    
    // Definir el método personalizado para buscar actividades por usuario
    List<Actividad> findByUsuarioId(Long usuarioId);

    // Opcional: otro método para buscar actividades por entidad Usuario
    List<Actividad> findByUsuario(Usuario usuario);
    
    
}
