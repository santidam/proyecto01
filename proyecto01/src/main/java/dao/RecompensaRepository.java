/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Recompensa;
import model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {
    
    // Definir el método para buscar recompensas por usuario ID
    List<Recompensa> findByUsuarioId(Long usuarioId);

    // Alternativa: método para buscar recompensas usando la entidad Usuario
    List<Recompensa> findByUsuario(Usuario usuario);
}
