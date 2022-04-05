/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.tipo;


import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroRepository;
import com.nailing.app.components.Fases;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CANDELA
 */
@Service("tipoService")
public class TipoService {
    
    @Autowired
    private TipoRepository tiporepository;
    @Autowired
    private CentroRepository centroRepository;
    
    public Tipo addTipo(Tipo tipo){
        return tiporepository.save(tipo);
    }
    
    public Tipo findById(Long id){
        return tiporepository.findById(id).get();
    }
    
    public Iterable<Tipo> findAll(){
        return tiporepository.findAll();
    }
    
    public void removeTipo(Long id){
        Tipo tipo = findById(id);
        if(tipo != null){
            tiporepository.delete(tipo);
        }
    }
    
    public List<Tipo> findByCentro(Long id){
        return tiporepository.findByCentro(id);
    }
    
    public void removeTiposbyCentro(Long centroId) {
		List<Tipo> tipos = tiporepository.findByCentro(centroId);
		for (Tipo t : tipos) {
			removeTipo(t.getId());
		}
	}
    
    public List<String> listPosibleTipo(){
        List<String> tipos = new ArrayList<>();
        tipos.add(NombreTipo.NATURAL.toString());
        tipos.add(NombreTipo.ESCULPIDA.toString());
        return tipos;
    }
    
    public List<Tipo> addTipoCentro(Map<String,List<String>> datos){
        Centro centro = null;
        Double precio = null;
        Integer duracion = null;
        
        List<Tipo> result = new ArrayList<>();
        final String centroKey = "centro";
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
                Tipo tipo = new Tipo(duracion,precio,Fases.bases,centro);
                switch (p){
                    case "NATURAL":
                        tipo.setNombre(NombreTipo.NATURAL);
                        break;
                    case "ESCULPIDA":
                        tipo.setNombre(NombreTipo.ESCULPIDA);
                        break;
                    default:
                        break;
                }
                if(tipo.getNombre()!=null){
                    Tipo d = tiporepository.save(tipo);
                    result.add(d);
                }
            }
            if(result.isEmpty()){
                throw new IllegalArgumentException("tipos: " + datos.get(persoKey));
            }
        }else{
            throw new IllegalArgumentException("tipos: " + datos.get(persoKey));
        }
        return result;
    }

    public List<Tipo> listByCentro(Long centroId){
        return tiporepository.findByCentro(centroId);
    }
}
