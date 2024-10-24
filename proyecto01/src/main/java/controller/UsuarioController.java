/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.proyecto01.proyecto01.exceptions.ResourceNotFoundException;
import dao.UsuarioRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import model.Usuario;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;
  /*  
    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está en uso");
        }
        
        validarContrasena(usuario.getContrasena());

        // Codificar contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }
*/
    @PostMapping("/registro")
   public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        // Verificación de correo duplicado
//        if (usuario.getCorreo().trim().equals("") || usuario.getCorreo().isEmpty()) {
//            return ResponseEntity.badRequest().body("Introduce un correo valido");
//        }
//        if (usuario.getNombre().trim().equals("") || usuario.getNombre().isEmpty()) {
//            return ResponseEntity.badRequest().body("Introduce un nombre valido");
//        }
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está en uso");
        }
        if(!validarContrasena(usuario.getContrasena())){
            return ResponseEntity.badRequest().body("La contraseña introducida no cumple las condicines necesarias:  almenys un número, una majúscula, una minúscula i un dels següents símbols: $ ; . _ - *");
        }
        
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }
   public boolean validarContrasena(String contrasena) {
    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$;._\\-*]).{6,}$";
    return contrasena.matches(regex);
}


    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    
    ///////////////////////////METODOS NUEVOS ///////////////////////////////////
     @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        usuario.setTipoUsuario(usuarioActualizado.getTipoUsuario());

        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);
        return ResponseEntity.ok("Usuario eliminado");
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    @PostMapping("/desbloquear-usuario/{id}")
    public ResponseEntity<?> desbloquearUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setBloqueado(false);
        usuario.setIntentosFallidos(0);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario desbloqueado");
    }

}
