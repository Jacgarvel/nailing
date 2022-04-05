package com.nailing.app.usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        return StreamSupport.stream(usuarioRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}
