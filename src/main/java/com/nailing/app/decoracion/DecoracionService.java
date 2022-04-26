/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.decoracion;

import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroRepository;
import com.nailing.app.components.Fases;
import com.nailing.app.disenyo.Disenyo;
import com.nailing.app.disenyo.DisenyoRepository;
import com.nailing.app.disenyo.NombreDisenyo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service("decoracionService")
public class DecoracionService {
    
    @Autowired
    private DecoracionRepository decoracionRepository;
    
    @Autowired
    private DisenyoRepository disenyoRepository;
    
    @Autowired
    private CentroRepository centroRepository;
    
    public Decoracion addDecoracion(Decoracion decoracion){
        return decoracionRepository.save(decoracion);
    }
    
    public Decoracion findById(Long id){
        return decoracionRepository.findById(id).get();
    }
    
    public void removeDecoracion(Long id){
        Decoracion decoracion = findById(id);
        if(decoracion != null){
            decoracionRepository.delete(decoracion);
        }
    }
    
    public Iterable<Decoracion> findAll(){
        return decoracionRepository.findAll();
    }
    public void removeDecoracionesbyCentro(Long centroId) {
		List<Decoracion> decoraciones = decoracionRepository.findByCentro(centroId);
		for (Decoracion d : decoraciones) {
			removeDecoracion(d.getId());
		}
	}
    public List<Decoracion> findDecoracionByCentroDisenyo(Long disenyoId, Long centroId){
        Optional<Disenyo> disenyo = disenyoRepository.findById(disenyoId);
        List<Decoracion> result = new ArrayList<>();
        List<NombreDecoracion> decoraciones;
        List<Decoracion> decoracionesCentro;
        
        if(disenyo.get().getNombre().equals(NombreDisenyo.LISAS)){
            result = decoracionRepository.findByCentro(centroId);
            return result;
        }else{
            decoraciones = Arrays.asList(NombreDecoracion.DIBUJO,NombreDecoracion.DISNEY_COLOR,
                    NombreDecoracion.DISNEY_BOCETO,NombreDecoracion.TRANSFER_FOIL,NombreDecoracion.PIEDRAS,
                    NombreDecoracion.PIERCING,NombreDecoracion.PEGATINAS,NombreDecoracion.STANPING,NombreDecoracion.PAN_DE_ANGEL,NombreDecoracion.NO_DECORACION);
            decoracionesCentro = decoracionRepository.findByCentro(centroId);
            for(Decoracion d: decoracionesCentro){
                if(decoraciones.contains(d.getNombre())){
                    result.add(d);
                }
            }
        }
        return result;
    }

    public List<String> listPosibleDecoracion(){
        List<String> decoraciones = new ArrayList<>();
        decoraciones.add(NombreDecoracion.DIBUJO.toString());
        decoraciones.add(NombreDecoracion.DISNEY_COLOR.toString());
        decoraciones.add(NombreDecoracion.DISNEY_BOCETO.toString());
        decoraciones.add(NombreDecoracion.TRANSFER_FOIL.toString());
        decoraciones.add(NombreDecoracion.PIEDRAS.toString());
        decoraciones.add(NombreDecoracion.PIERCING.toString());
        decoraciones.add(NombreDecoracion.PEGATINAS.toString());
        decoraciones.add(NombreDecoracion.STANPING.toString());
        decoraciones.add(NombreDecoracion.PAN_DE_ANGEL.toString());
        decoraciones.add(NombreDecoracion.EFECTO_PIEDRA.toString());
        decoraciones.add(NombreDecoracion.EFECTO_RELIEVE.toString());
        decoraciones.add(NombreDecoracion.FRANCESA.toString());
        decoraciones.add(NombreDecoracion.BURBUJAS.toString());
        decoraciones.add(NombreDecoracion.SUGAR.toString());
        decoraciones.add(NombreDecoracion.ESPEJO.toString());
        decoraciones.add(NombreDecoracion.HOLOGRAFICO.toString());
        decoraciones.add(NombreDecoracion.MARMOL.toString());
        decoraciones.add(NombreDecoracion.NO_DECORACION.toString());
        return decoraciones;
    }
    
    public List<Decoracion> addDecoracionCentro(Map<String,List<String>> datos){
        Centro centro = null;
        Double precio = null;
        Integer duracion = null;
        
        List<Decoracion> result = new ArrayList<>();
        
        final String centroKey= "centro";
        if(!(datos.get(centroKey) == null || datos.get(centroKey).isEmpty() || datos.get(centroKey).get(0) == null)){
            centro = centroRepository.findById(Long.parseLong(datos.get(centroKey).get(0))).get();
        }else{
            throw new IllegalArgumentException("centro: " + datos.get(centroKey));
        }
        final String costeKey = "coste";
        if(!(datos.get(costeKey) == null || datos.get(costeKey).isEmpty() || datos.get(costeKey).get(0) == null)){
            precio = Double.valueOf(datos.get(costeKey).get(0));
        }else{
            throw new IllegalArgumentException("precio: " + datos.get("precio"));
        }
        final String tiempoKey = "tiempo";
        if(!(datos.get(tiempoKey) == null || datos.get(tiempoKey).isEmpty() || datos.get(tiempoKey).get(0) == null)){
            duracion = Integer.valueOf(datos.get(tiempoKey).get(0));
        }else{
            throw new IllegalArgumentException("tiempo: " + datos.get(tiempoKey));
        }
        final String persoKey = "personalizaciones";
        if(!(datos.get(persoKey) == null || datos.get(persoKey).isEmpty() || datos.get(persoKey).get(0) == null)){
            for(String p:datos.get(persoKey)){
                Decoracion decoracion = new Decoracion(duracion,precio,Fases.acabados,centro);
                switch (p){
                    case "DIBUJO":
                        decoracion.setNombre(NombreDecoracion.DIBUJO);
                        break;
                    case "DISNEY_COLOR":
                        decoracion.setNombre(NombreDecoracion.DISNEY_COLOR);
                        break;
                    case "DISNEY_BOCETO":
                        decoracion.setNombre(NombreDecoracion.DISNEY_BOCETO);
                        break;
                    case "TRANSFER_FOIL":
                        decoracion.setNombre(NombreDecoracion.TRANSFER_FOIL);
                        break;
                    case "PIEDRAS":
                        decoracion.setNombre(NombreDecoracion.PIEDRAS);
                        break;
                    case "PIERCING":
                        decoracion.setNombre(NombreDecoracion.PIERCING);
                        break;
                    case "PEGATINAS":
                        decoracion.setNombre(NombreDecoracion.PEGATINAS);
                        break;
                    case "STANPING":
                        decoracion.setNombre(NombreDecoracion.STANPING);
                        break;
                    case "PAN_DE_ANGEL":
                        decoracion.setNombre(NombreDecoracion.PAN_DE_ANGEL);
                        break;
                    case "EFECTO_PIEDRA":
                        decoracion.setNombre(NombreDecoracion.EFECTO_PIEDRA);
                        break;
                    case "EFECTO_RELIEVE":
                        decoracion.setNombre(NombreDecoracion.EFECTO_RELIEVE);
                        break;
                    case "FRANCESA":
                        decoracion.setNombre(NombreDecoracion.FRANCESA);
                        break;
                    case "BURBUJAS":
                        decoracion.setNombre(NombreDecoracion.BURBUJAS);
                        break;
                    case "SUGAR":
                        decoracion.setNombre(NombreDecoracion.SUGAR);
                        break;
                    case "ESPEJO":
                        decoracion.setNombre(NombreDecoracion.ESPEJO);
                        break;
                    case "HOLOGRAFICO":
                        decoracion.setNombre(NombreDecoracion.HOLOGRAFICO);
                        break;
                    case "MARMOL":
                        decoracion.setNombre(NombreDecoracion.MARMOL);
                        break;
                    case "NO_DECORACION":
                        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
                        break;
                    default:
                        break;
                }
                if(decoracion.getNombre()!=null){
                    Decoracion d = decoracionRepository.save(decoracion);
                    result.add(d);
                }
            }
            if(result.isEmpty()){
                throw new IllegalArgumentException("decoraciones: " + datos.get(persoKey));
            }
        }else{
            throw new IllegalArgumentException("decoraciones: " + datos.get(persoKey));
        }
        return result;
    }

    public List<Decoracion> findByCentro(Long centroId){
        return decoracionRepository.findByCentro(centroId);
    }
}
