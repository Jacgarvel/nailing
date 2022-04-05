package com.nailing.app.usuario;

import com.nailing.app.securityConfiguration.DbInit;

import static com.nailing.app.usuario.AuthoritiesConstants.*;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class UsuarioController {

    @Autowired
    public UsuarioService usuarioSer;
            
    @Autowired
    public DbInit encoder;

    @Operation(summary = "Registra a un Usuario")
    @PostMapping("/signUp")
    public ResponseEntity<Usuario> signUsuario(@RequestBody Map<String,String> map){
        Usuario result = encoder.addUsuario(map);
        if(result != null)
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    
    @Operation(summary = "Inicia la sesion de un Usuario")
    @PostMapping("/login")
    public ResponseEntity<Usuario> logUsuario(@RequestBody Map<String,String> usuario){
        Usuario result = encoder.findByUsuarioContrasenya(usuario.get("user"), usuario.get("password"));
        if(result!=null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
    }

    @Operation(summary = "Lista todos los Usuarios")
    @PreAuthorize("hasAuthority('"+ ADMIN +"')")
    @GetMapping("/usuarios/list")
    public ResponseEntity<List<Usuario>> listUsuarios(){
        List<Usuario> usuarios = usuarioSer.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @Operation(summary = "Borra un Usuario")
    @PreAuthorize("hasAuthority('"+ ADMIN +"')")
    @DeleteMapping("/usuarios/delete/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioSer.removeUsuario(id);
    }
    
    @Operation(summary = "Muestra un Usuario")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @GetMapping("/usuarios/show/{id}")
    public ResponseEntity<Usuario> showUsuario(@PathVariable Long id){
        return new ResponseEntity<>(usuarioSer.findById(id).get(), HttpStatus.OK);
    }
    
    @Operation(summary = "Edita un Usuario")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @PutMapping("/usuarios/edit")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario){
        Usuario u = null;
        try{
            return new ResponseEntity<>(encoder.save(usuario), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
        }

    }	
}
