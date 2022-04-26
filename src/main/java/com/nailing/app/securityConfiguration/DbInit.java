/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.securityConfiguration;

import com.nailing.app.usuario.Authorities;
import com.nailing.app.centro.CentroRepository;
import com.nailing.app.cita.Cita;
import com.nailing.app.cita.CitaRepository;
import com.nailing.app.usuario.Usuario;
import com.nailing.app.usuario.UsuarioRepository;
import com.nailing.app.usuario.UsuarioService;
import com.nailing.app.valoracion.Valoracion;
import com.nailing.app.valoracion.ValoracionRepository;
import java.time.LocalDateTime;
import java.time.Month;

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
    @Autowired
    private ValoracionRepository valRep;
    @Autowired
    private CitaRepository citaRep;

    public DbInit(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Delete all
        this.usuarioRepository.deleteAll();

        // Crete users
        Usuario usuario1 = new Usuario("usuario1",passwordEncoder.encode("usuario1"),"email@email.com","555555555",Authorities.USER);
        Usuario usuario2 = new Usuario("usuario2",passwordEncoder.encode("usuario2"),"email2@email.com","55556555",Authorities.ADMIN);
        Usuario usuario3 = new Usuario("usuario3",passwordEncoder.encode("usuario3"),"email3@email.com","555565585",Authorities.OWNER,centroRep.findById((long)1).get());
        Usuario usuario4 = new Usuario("usuario4",passwordEncoder.encode("usuario4"),"email4@email.com","555565589",Authorities.OWNER,centroRep.findById((long)2).get());
        Usuario usuario5 = new Usuario("usuario5",passwordEncoder.encode("usuario5"),"email5@email.com","655565589",Authorities.OWNER,centroRep.findById((long)3).get());
        Usuario usuario6 = new Usuario("usuario6",passwordEncoder.encode("usuario6"),"email5@email.com","655565589",Authorities.OWNER,centroRep.findById((long)4).get());
        Usuario usuario7 = new Usuario("usuario7",passwordEncoder.encode("usuario7"),"email5@email.com","655565589",Authorities.OWNER,centroRep.findById((long)5).get());
        Usuario usuario8 = new Usuario("usuario8",passwordEncoder.encode("usuario8"),"email5@email.com","655565589",Authorities.OWNER,centroRep.findById((long)6).get());
        List<Usuario> users = Arrays.asList(usuario1,usuario2,usuario3,usuario4,usuario5,usuario6,usuario7,usuario8);

        // Save to db
        this.usuarioRepository.saveAll(users);
        
        Valoracion val1 = new Valoracion(4, centroRep.findById((long)1).get(),this.usuarioRepository.findById((long)3).get());
        Valoracion val2 = new Valoracion(3, centroRep.findById((long)2).get(),this.usuarioRepository.findById((long)4).get());
        Valoracion val3 = new Valoracion(2, centroRep.findById((long)3).get(),this.usuarioRepository.findById((long)5).get());
        List<Valoracion> vals = Arrays.asList(val1,val2,val3);
        valRep.saveAll(vals);
        
        Cita cit1 = new Cita(41.0,LocalDateTime.of(2022, Month.SEPTEMBER, 24, 9, 0),LocalDateTime.of(2022, Month.SEPTEMBER, 24, 9, 30),null,null,null,null,null,null,null,this.usuarioRepository.findById((long)3).get(),centroRep.findById((long)1).get());
        Cita cit2 = new Cita(41.0,LocalDateTime.of(2022, Month.SEPTEMBER, 24, 9, 35),LocalDateTime.of(2022, Month.SEPTEMBER, 24, 10, 5),null,null,null,null,null,null,null,this.usuarioRepository.findById((long)4).get(),centroRep.findById((long)1).get());
        Cita cit3 = new Cita(41.0,LocalDateTime.of(2022, Month.SEPTEMBER, 24, 8, 30),LocalDateTime.of(2022, Month.SEPTEMBER, 24, 9, 0),null,null,null,null,null,null,null,this.usuarioRepository.findById((long)5).get(),centroRep.findById((long)2).get());
        List<Cita> citas = Arrays.asList(cit1,cit2,cit3);
        citaRep.saveAll(citas);
        
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

        Usuario usuario = new Usuario(user,passwordEncoder.encode(contrasenya),email,telefono,Authorities.USER);

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
