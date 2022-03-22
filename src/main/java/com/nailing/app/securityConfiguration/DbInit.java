/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.securityConfiguration;

import com.nailing.app.usuario.Usuario;
import com.nailing.app.usuario.UsuarioRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author jaime
 */

@Service
public class DbInit implements CommandLineRunner {
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Delete all
        this.usuarioRepository.deleteAll();

        // Crete users
        Usuario usuario1 = new Usuario("usuario1",passwordEncoder.encode("usuario1"),"email@email.com","555555555","USER");

        List<Usuario> users = Arrays.asList(usuario1);

        // Save to db
        this.usuarioRepository.saveAll(users);
    }
    
    //encontrar usuario por usuario contrasenya
    public Boolean findByUsuarioContrasenya(String usuario, String contrasenya) {
        Usuario usuario2 = usuarioRepository.findByUsername(usuario);
        if(usuario2 != null){
            if(passwordEncoder.matches(contrasenya, usuario2.getContrasenya())){
                return true;
            }
        }
        return false;
    }
}