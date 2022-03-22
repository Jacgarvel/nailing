package com.nailing.app.usuario;

import com.nailing.app.securityConfiguration.DbInit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UsuarioController {

    @Autowired
    public UsuarioService usuarioSer;
            
    @Autowired
    public DbInit encoder;

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario){
        Usuario result = usuarioSer.addUsuario(usuario);
        if(result != null)
            return new ResponseEntity<Usuario>(result, HttpStatus.CREATED);
        return new ResponseEntity<Usuario>(result, HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> addUsuario(@RequestBody Map<String,String> usuario){
        Boolean result = encoder.findByUsuarioContrasenya(usuario.get("user"), usuario.get("password"));
        if(result){
            return new ResponseEntity<String>("Usuario encontrado", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listUsuarios(){
        List<Usuario> usuarios = usuarioSer.findAll();
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }
    
    @DeleteMapping("/usuarios/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioSer.removeUsuario(id);
    }
    
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> showUsuario(@PathVariable Long id){
        return new ResponseEntity<Usuario>(usuarioSer.findById(id).get(), HttpStatus.OK);
    }
	
}