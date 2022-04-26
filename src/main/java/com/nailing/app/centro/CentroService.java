/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.centro;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nailing.app.acabado.AcabadoService;
import com.nailing.app.base.BaseService;
import com.nailing.app.decoracion.DecoracionService;
import com.nailing.app.disenyo.DisenyoService;
import com.nailing.app.forma.FormaService;
import com.nailing.app.securityConfiguration.DbInit;
import com.nailing.app.tamanyo.TamanyoService;
import com.nailing.app.tipo.TipoService;
import com.nailing.app.usuario.Authorities;
import com.nailing.app.usuario.Usuario;
import com.nailing.app.usuario.UsuarioService;
import com.nailing.app.valoracion.Valoracion;
import com.nailing.app.valoracion.ValoracionService;

/**
 *
 * @author jaime
 */

@Service
public class CentroService {
    
    @Autowired
    private CentroRepository centroRepository;
    @Autowired
    private AcabadoService acabSer;
    @Autowired
    private BaseService basSer;
    @Autowired
    private DecoracionService decSer;
    @Autowired
    private DisenyoService disSer;
    @Autowired 
    private FormaService forSer;
    @Autowired
    private TamanyoService tamSer;
    @Autowired
    private TipoService tipoSer;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ValoracionService valoracionService;
    @Autowired
    public DbInit encoder;
    public Optional<Centro> findById(Long id){
        return centroRepository.findById(id);
    }
    
    public Centro save(Centro centro){
        return centroRepository.save(centro);
    }
    
    public List<Centro> findAll(){
        List<Centro> centros =  StreamSupport.stream(centroRepository.findAll().spliterator(), false).collect(Collectors.toList());
        List<Centro> premium = new ArrayList<>();
        List<Centro> noPremium = new ArrayList<>();
        for(Centro c : centros){
            if(c.getSuscripcion().equals(Suscripcion.PREMIUM)){
                premium.add(c);
            }else{
                noPremium.add(c);
            }
        }
        return Stream.concat(premium.stream(), noPremium.stream()).collect(Collectors.toList());
    }
    
    public void delete(Long id){
        Optional<Centro> centro = findById(id);
        if(centro.isPresent()){
        	acabSer.removeAcabadosbyCentro(id);
        	basSer.removeBasesbyCentro(id);
        	decSer.removeDecoracionesbyCentro(id);
        	disSer.removeDisenyosbyCentro(id);
        	forSer.removeFormabyCentro(id);
        	tamSer.removeTamanyobyCentro(id);
        	tipoSer.removeTiposbyCentro(id);
        	for(Usuario u : usuarioService.findAll()) {
        		if (centro.get().equals(u.getCentro())) {
        			u.setCentro(null);
                    usuarioService.save(u);
        		}
         	}
                for(Valoracion v : valoracionService.findAll()) {
        		if (centro.get().equals(v.getCentro())) {
        			valoracionService.delete(v);
        		}
         	}
            centroRepository.delete(centro.get());
        }
    }
  
    public Centro addCentro(Centro centro) {    	
        if(centro != null && centro.getDiasDisponible() != null){
    		try
    		{
	        	String[] dias = centro.getDiasDisponible().replaceAll("\\s+","").toUpperCase().split(",");
	    		for(String d: dias) 
	    		{
	    			DayOfWeek.valueOf(d);
	    		}
	    		centro.setDiasDisponible(centro.getDiasDisponible().replaceAll("\\s+","").toUpperCase());
    		}
    		catch(Exception e)
    		{
    			throw new IllegalArgumentException();
    		}
	    		
        	if(Boolean.TRUE.equals(!centroTieneCambios(centro)) && Boolean.TRUE.equals(centro.getPagado())) {
				centro.setUltimaSuscripcion(LocalDate.now());
				if(centro.getSuscripcion() == Suscripcion.BASIC){
					centro.setCreditosrestantes(150);
				}
				else if(centro.getSuscripcion() == Suscripcion.MEDIUM){
					centro.setCreditosrestantes(200);
				}
				else if(centro.getSuscripcion() == Suscripcion.ADVANCED){
					centro.setCreditosrestantes(300);
				}
				else if(centro.getSuscripcion() == Suscripcion.PREMIUM){
					centro.setCreditosrestantes(400);
				} 
        	}
                if(centro.getAperturaAM().isAfter(centro.getCierreAM()) || centro.getAperturaPM().isAfter(centro.getCierrePM()) 
                        || centro.getAperturaAM().isAfter(centro.getAperturaPM()) || centro.getAperturaPM().isBefore(centro.getCierreAM())){
                    throw new IllegalArgumentException();
                }
	        return centroRepository.save(centro);
        }else{
            throw new IllegalArgumentException();
        }
    }
    public void fechacumplida(Centro centro) {
    	LocalDate fechaActual = LocalDate.now();
    	LocalDate fechaSuscripcion = centro.getUltimaSuscripcion();
    	if(fechaSuscripcion.plusMonths(1).isAfter(fechaActual) ) {
    		centro.setPagado(false);
    	}
    }
    public void comprobacionCentros() {
    	for(Centro c : findAll()) {
    		fechacumplida(c);
    	}
    }
  
    public Usuario asociarCentroUsuario(Usuario usuario, Centro centro) {
    	Centro cent = addCentro(centro);
    	usuario.setCentro(cent);
        usuario.setRol(Authorities.OWNER);
    	return usuarioService.save(usuario);
    }
	private Boolean centroTieneCambios(Centro centro) {
		List<Centro> centros = findAll();
		Boolean result = false;
		for(Centro c: centros) {
			if(c.getId().equals(centro.getId()) && 
			(!centro.getNombre().equals(c.getNombre()) ||
			c.getImagen().equals(centro.getImagen()) ||
			c.getAperturaAM().equals(centro.getAperturaAM()) ||
			c.getAperturaPM().equals(centro.getAperturaPM()) || c.getCierreAM().equals(centro.getCierreAM()) ||
			c.getCierrePM().equals(centro.getCierrePM()))) {
				result = true;
			}
		}
		return result;
	}
   
   public Centro updateCentroImage(long idCentro, String uri) {
	   Optional<Centro> centro = findById(idCentro);
	   if(!centro.isPresent())
		   return null;
	   else 
	   {
		   Centro result = centro.get();
		   result.setImagen(uri);
		   centroRepository.save(result);
		   return result;
		   
	   }
   }
}
