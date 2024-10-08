/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.RecompensaRepository;
import dao.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import model.Recompensa;
import model.Usuario;

@RestController
@RequestMapping("/usuarios/{usuarioId}/recompensas")
public class RecompensaController {

    @Autowired
    private RecompensaRepository recompensaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Recompensa> agregarRecompensa(@PathVariable Long usuarioId, @RequestBody Recompensa recompensa) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            recompensa.setUsuario(usuario.get());
            return ResponseEntity.ok(recompensaRepository.save(recompensa));
        }
        return ResponseEntity.notFound().build();
    }
}
