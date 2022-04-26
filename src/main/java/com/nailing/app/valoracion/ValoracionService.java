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
        
        if(valoraciones.get("valoracionUsuario") != null && valoraciones.get("centro") != null && valoraciones.get("usuario") != null){
            valoracionUsuario = Integer.valueOf(valoraciones.get("valoracionUsuario"));
            centro = centroRepository.findById(Long.parseLong(valoraciones.get("centro"))).get();
            usuario = usuarioRepository.findById(Long.parseLong(valoraciones.get("usuario"))).get();
            centro.setNumValoraciones(centro.getNumValoraciones()+1);
            centro.setValoracionTotal(centro.getValoracionTotal() + valoracionUsuario);
            Double media = Double.valueOf(centro.getValoracionTotal()) / centro.getNumValoraciones();
            centro.setValoracionMedia(media);
            centroRepository.save(centro);
            valoracion = new Valoracion(valoracionUsuario, centro, usuario);
        }else{
            throw new IllegalArgumentException( "valoracion usuario: "+ valoraciones.get("valoracionUsuario")+ "; centro: "+ valoraciones.get("centro") 
                    + "; usuario: "+ valoraciones.get("usuario"));
        
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
