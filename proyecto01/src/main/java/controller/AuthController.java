package controller;

import com.proyecto01.proyecto01.security.AuthenticationResponse;
import com.proyecto01.proyecto01.security.JwtUtil;
import com.proyecto01.proyecto01.security.UserDetailsServiceImpl;
import dao.UsuarioRepository;
import java.util.Optional;
import model.AuthRequest;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    
//    @PostMapping("/authenticate")
//    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
//        try {
//            // Autenticar al usuario con su correo y contraseña
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getContrasena())
//            );
//        } catch (AuthenticationException e) {
//            
//            throw new Exception("Usuario o contraseña incorrectos");
//        }
//
//        // Cargar los detalles del usuario y generar el token JWT
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getCorreo());
//        return jwtUtil.generateToken(userDetails.getUsername());  // Devuelve el token JWT
//    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getContrasena())
            );
        } catch (BadCredentialsException e) {
            // Si las credenciales son incorrectas, incrementa los intentos fallidos
            Usuario usuario = usuarioRepository.findByCorreo(authRequest.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            if (usuario.isBloqueado()) {
                return ResponseEntity.status(HttpStatus.LOCKED).body("El cuenta está bloqueada");
            }

            usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);

            // Bloquea la cuenta si los intentos superan 3
            if (usuario.getIntentosFallidos() >= 3) {
                usuario.setBloqueado(true);
            }

            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        // Restablecer los intentos fallidos si el inicio de sesión es exitoso
        Usuario usuario = usuarioRepository.findByCorreo(authRequest.getCorreo())
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        usuario.setIntentosFallidos(0);
        usuarioRepository.save(usuario);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getCorreo());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    
}
