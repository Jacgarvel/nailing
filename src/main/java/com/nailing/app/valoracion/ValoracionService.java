/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.valoracion;

import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroRepository;
import com.nailing.app.usuario.Usuario;
import com.nailing.app.usuario.UsuarioRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CANDELA
 */
@Service("valoracionService")
public class ValoracionService {
    
    @Autowired
    private ValoracionRepository valoracionRepository;
    
   @Autowired
    private CentroRepository centroRepository;
   
     @Autowired
    private UsuarioRepository usuarioRepository;

    public Valoracion findById(Long id){
        return valoracionRepository.findById(id).get();
    }

    public Valoracion addValoracion(Map<String,String> valoraciones){
        Integer valoracionUsuario = null;
        Centro centro = null;
        Usuario usuario = null;
        Valoracion valoracion = null;
        final String valUser = "valoracionUsuario";
        final String centroString = "centro";
        final String usuarioString = "usuario";
        if(valoraciones.get(valUser) != null && valoraciones.get(centroString) != null && valoraciones.get(usuarioString) != null){
            valoracionUsuario = Integer.valueOf(valoraciones.get(valUser));
            centro = centroRepository.findById(Long.parseLong(valoraciones.get(centroString))).get();
            usuario = usuarioRepository.findById(Long.parseLong(valoraciones.get(usuarioString))).get();
            centro.setNumValoraciones(centro.getNumValoraciones()+1);
            centro.setValoracionTotal(centro.getValoracionTotal() + valoracionUsuario);
            Double media = Double.valueOf(centro.getValoracionTotal()) / centro.getNumValoraciones();
            centro.setValoracionMedia(media);
            centroRepository.save(centro);
            valoracion = new Valoracion(valoracionUsuario, centro, usuario);
        }else{
            throw new IllegalArgumentException( "valoracion usuario: "+ valoraciones.get(valUser)+ "; centro: "+ valoraciones.get(centroString) 
                    + "; usuario: "+ valoraciones.get(usuarioString));
        
        }
        return valoracionRepository.save(valoracion);
    }
    
    public Valoracion updateValoracion(Valoracion valoracion){
        return valoracionRepository.save(valoracion);
    }
    
    
    public Iterable<Valoracion> findAll() {
	return valoracionRepository.findAll();
    }
    
    public void delete(Valoracion valoracion){
        valoracionRepository.delete(valoracion);
    }
}
