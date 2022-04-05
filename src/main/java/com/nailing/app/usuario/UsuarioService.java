package com.nailing.app.usuario;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;


@Service("usuarioService")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    //encontrar usuario por su id
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    //encontrar usuario por su usuario
    public Usuario findByUsuario(String usuario) {
        return usuarioRepository.findByUsername(usuario);
    }
    
    public void removeUsuario(Long id) {
        Optional<Usuario> usuario = findById(id);
        if(usuario.isPresent()){
            usuarioRepository.delete(usuario.get());
        }
    }
    
    public List<Usuario> findAll(){
        return (List) usuarioRepository.findAll();
    }
    
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}
