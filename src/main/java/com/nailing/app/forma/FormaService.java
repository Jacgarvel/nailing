/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.forma;

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
 * @author jaime
 */

@Service
public class FormaService {
    @Autowired
    private FormaRepository formaRepository;
    @Autowired
    private CentroRepository centroRepository;

    public Forma addForma(Forma forma) {
		
    	return formaRepository.save(forma);
    }
	
//	encontrar base por su ID
    public Forma findById(Long id) {
	return formaRepository.findById(id).get();
    }
	
//	todas las bases
    public List<Forma> findAll(){
	return (List) formaRepository.findAll();
    }
	
//	borrar una base por su ID
    public void removeForma(Long id) {
	Forma forma = findById(id);
	if(forma!=null){
            formaRepository.delete(forma);
        }
    }
    public void removeFormabyCentro(Long centroId) {
		List<Forma> formas = formaRepository.findByCentro(centroId);
		for (Forma f : formas) {
			removeForma(f.getId());
		}
	}
//	encontrar las bases que posee el centro dado según el tipo seleccionado
    public List<Forma> findFormasByCentroBase(Long centroId) {	
        return formaRepository.findByCentro(centroId);
    }
    
    public List<String> listPosibleForma(){
        List<String> formas = new ArrayList<>();
        formas.add(NombreForma.SQUARE.toString());
        formas.add(NombreForma.ROUND.toString());
        formas.add(NombreForma.SQUOVAL.toString());
        formas.add(NombreForma.ALMOND.toString());
        formas.add(NombreForma.STILETTO.toString());
        formas.add(NombreForma.BALLERINA.toString());
        return formas;
    }
    
    public List<Forma> addFormaCentro(Map<String,List<String>> datos){
        Centro centro = null;
        Double precio = null;
        Integer duracion = null;
        
        List<Forma> result = new ArrayList<>();
        
        if(!(datos.get("centro") == null || datos.get("centro").isEmpty() || datos.get("centro").get(0) == null)){
            centro = centroRepository.findById(Long.parseLong(datos.get("centro").get(0))).get();
        }else{
            throw new IllegalArgumentException("centro: " + datos.get("centro"));
        }
        
        if(!(datos.get("coste") == null || datos.get("coste").isEmpty() || datos.get("coste").get(0) == null)){
            precio = Double.valueOf(datos.get("coste").get(0));
        }else{
            throw new IllegalArgumentException("precio: " + datos.get("precio"));
        }
        
        if(!(datos.get("tiempo") == null || datos.get("tiempo").isEmpty() || datos.get("tiempo").get(0) == null)){
            duracion = Integer.valueOf(datos.get("tiempo").get(0));
        }else{
            throw new IllegalArgumentException("tiempo: " + datos.get("tiempo"));
        }
        
        if(!(datos.get("personalizaciones") == null || datos.get("personalizaciones").isEmpty() || datos.get("personalizaciones").get(0) == null)){
            for(String p:datos.get("personalizaciones")){
                Forma forma = new Forma(duracion,precio,Fases.tamanyos,centro);
                switch (p){
                    case "SQUARE":
                        forma.setNombre(NombreForma.SQUARE);
                        break;
                    case "ROUND":
                        forma.setNombre(NombreForma.ROUND);
                        break;
                    case "SQUOVAL":
                        forma.setNombre(NombreForma.SQUOVAL);
                        break;
                    case "ALMOND":
                        forma.setNombre(NombreForma.ALMOND);
                        break;
                    case "STILETTO":
                        forma.setNombre(NombreForma.STILETTO);
                        break;
                    case "BALLERINA":
                        forma.setNombre(NombreForma.BALLERINA);
                        break;
                }
                if(forma.getNombre()!=null){
                    Forma f = formaRepository.save(forma);
                    result.add(f);
                }
            }
            if(result.isEmpty()){
                throw new IllegalArgumentException("formas: " + datos.get("personalizaciones"));
            }
        }else{
            throw new IllegalArgumentException("formas: " + datos.get("personalizaciones"));
        }
        return result;
    }

    public List<Forma> findByCentro(Long centroId) {	
        return formaRepository.findByCentro(centroId);
    }
}
