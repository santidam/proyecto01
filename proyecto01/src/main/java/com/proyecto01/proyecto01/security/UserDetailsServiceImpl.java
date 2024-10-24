package com.proyecto01.proyecto01.security;

import dao.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + username));

        // Devolver un objeto UserDetails, que es utilizado por Spring Security
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+usuario.getTipoUsuario().name()));
        
        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getContrasena(), authorities);
    }
    
    
}
