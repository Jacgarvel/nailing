/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.securityConfiguration;

import com.nailing.app.centro.CentroRepository;
import com.nailing.app.usuario.Usuario;
import com.nailing.app.usuario.UsuarioRepository;
import com.nailing.app.usuario.UsuarioService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired

    private CentroRepository centroRep;
    @Autowired
    private UsuarioService usuarioService;

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
        Usuario usuario2 = new Usuario("usuario2",passwordEncoder.encode("usuario2"),"email2@email.com","55556555","ADMIN");
        Usuario usuario3 = new Usuario("usuario3",passwordEncoder.encode("usuario3"),"email3@email.com","555565585","OWNER",centroRep.findById((long)1).get());
        Usuario usuario4 = new Usuario("usuario4",passwordEncoder.encode("usuario4"),"email4@email.com","555565589","OWNER",centroRep.findById((long)2).get());
        Usuario usuario5 = new Usuario("usuario5",passwordEncoder.encode("usuario5"),"email5@email.com","655565589","OWNER",centroRep.findById((long)3).get());
        List<Usuario> users = Arrays.asList(usuario1,usuario2,usuario3,usuario4,usuario5);

        // Save to db
        this.usuarioRepository.saveAll(users);
    }

    //añadir-actualizar usuario
    public Usuario addUsuario(Map<String,String> map) {
        String user = map.get("user");
        List<Usuario> usuarios = usuarioService.findAll();
        for(Usuario u: usuarios){
            if(u.getUsuario().equals(user)) throw new IllegalArgumentException();
        }
        String contrasenya = map.get("password");
        String email = map.get("email");
        String telefono = map.get("telefono");
        if(user == null || contrasenya == null|| email == null|| telefono == null){
            throw new IllegalArgumentException();
        }

        Usuario usuario = new Usuario(user,passwordEncoder.encode(contrasenya),email,telefono,"USER");

        return usuarioRepository.save(usuario);
    }
    
    public Usuario save(Usuario usuarioo) {
        
        Usuario usuario = new Usuario(usuarioo.getId(),usuarioo.getUsuario(), passwordEncoder.encode(usuarioo.getContrasenya()),usuarioo.getEmail(),usuarioo.getTelefono(),usuarioo.getRol());
    	return usuarioRepository.save(usuario);
    }
    
    //encontrar usuario por usuario contrasenya
    public Usuario findByUsuarioContrasenya(String usuario, String contrasenya) {
        Usuario usuario2 = usuarioRepository.findByUsername(usuario);
        if(usuario2 != null && passwordEncoder.matches(contrasenya, usuario2.getContrasenya())){
            return usuario2;
        }
        return null;
    }
}
