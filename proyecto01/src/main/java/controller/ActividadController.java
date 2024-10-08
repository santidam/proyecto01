/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ActividadRepository;
import dao.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
    public ResponseEntity<Actividad> registrarActividad(@PathVariable Long usuarioId, @RequestBody Actividad actividad) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            actividad.setUsuario(usuario.get());
            return ResponseEntity.ok(actividadRepository.save(actividad));
        }
        return ResponseEntity.notFound().build();
    }
}
