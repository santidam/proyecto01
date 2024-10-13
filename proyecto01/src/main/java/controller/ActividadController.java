/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.proyecto01.proyecto01.exceptions.ResourceNotFoundException;
import dao.ActividadRepository;
import dao.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import model.Actividad;
import model.Usuario;

@RestController
@RequestMapping("/usuarios/{usuarioId}/actividades")
public class ActividadController {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
   public ResponseEntity<Actividad> registrarActividad(@PathVariable Long usuarioId, @Valid @RequestBody Actividad actividad) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        
        actividad.setUsuario(usuario);
        Actividad nuevaActividad = actividadRepository.save(actividad);
        return ResponseEntity.ok(nuevaActividad);
   }
    
   @GetMapping
    public ResponseEntity<List<Actividad>> listarActividades(@PathVariable Long usuarioId) {
          Usuario usuario = usuarioRepository.findById(usuarioId)
              .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));

          List<Actividad> actividades = actividadRepository.findByUsuario(usuario);
          return ResponseEntity.ok(actividades);
      }
    
}

