/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.proyecto01.proyecto01.exceptions.ResourceNotFoundException;
import dao.ActividadRepository;
import dao.RecompensaRepository;
import dao.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import jakarta.validation.Valid;
import model.Actividad;
import model.Recompensa;
import model.Usuario;

@RestController
@RequestMapping("/usuarios/{usuarioId}/recompensas")
public class RecompensaController {

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Recompensa> asignarRecompensa(@PathVariable Long usuarioId, @Valid @RequestBody Recompensa recompensa) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        
        recompensa.setUsuario(usuario);
        Recompensa nuevaRecompensa = recompensaRepository.save(recompensa);
        return ResponseEntity.ok(nuevaRecompensa);
    }
    @GetMapping
    public ResponseEntity<List<Recompensa>> listarRecompensas(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        
        List<Recompensa> recompensas = recompensaRepository.findByUsuario(usuario);
        return ResponseEntity.ok(recompensas);
    }
}
