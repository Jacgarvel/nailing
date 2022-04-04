/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.acabado;

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
 * @author Usuario
 */
@Service("acabadoService")
public class AcabadoService {
    
    @Autowired
    private AcabadoRepository acabadoRepository;
    
    @Autowired
    private CentroRepository centroRepository;
    
    public Acabado addAcabado(Acabado acabado){
        return acabadoRepository.save(acabado);
    }
    
    public Acabado findById(Long id){
        return acabadoRepository.findById(id).get();
    }
    
    public Iterable<Acabado> findAll(){
        return acabadoRepository.findAll();
    }
    
    public void removeAcabado(Long id){
        Acabado acabado = findById(id);
        if(acabado != null){
            acabadoRepository.delete(acabado);
        }
    }
    
    public List<String> listPosibleAcabado(){
        List<String> acabados = new ArrayList<>();
        acabados.add(NombreAcabado.BRILLO.toString());
        acabados.add(NombreAcabado.MATE.toString());
        return acabados;
    }
    
    public List<Acabado> addAcabadoCentro(Map<String,List<String>> datos){
        Centro centro = null;
        Double precio = null;
        Integer duracion = null;
        
        List<Acabado> result = new ArrayList<>();
        
        if(!(datos.get("centro") == null || datos.get("centro").isEmpty() || datos.get("centro").get(0) == null)){
            centro = centroRepository.findById(Long.parseLong(datos.get("centro").get(0))).get();
        }else{
            throw new IllegalArgumentException("centro: " + datos.get("centro"));
        }
        
        if(!(datos.get("coste") == null || datos.get("coste").isEmpty() || datos.get("coste").get(0) == null)){
            precio = Double.valueOf(datos.get("coste").get(0));
        }else{
            throw new IllegalArgumentException("coste: " + datos.get("coste"));
        }
        
        if(!(datos.get("tiempo") == null || datos.get("tiempo").isEmpty() || datos.get("tiempo").get(0) == null)){
            duracion = Integer.valueOf(datos.get("tiempo").get(0));
        }else{
            throw new IllegalArgumentException("tiempo: " + datos.get("tiempo"));
        }
        
        if(!(datos.get("personalizaciones") == null || datos.get("personalizaciones").isEmpty() || datos.get("personalizaciones").get(0) == null)){
            for(String p:datos.get("personalizaciones")){
                Acabado acabado = new Acabado(duracion,precio,Fases.fin,centro);
                switch (p){
                    case "MATE":
                        acabado.setNombre(NombreAcabado.MATE);
                        break;
                    case "BRILLO":
                        acabado.setNombre(NombreAcabado.BRILLO);
                        break;
                }
                if(acabado.getNombre()!=null){
                    Acabado a = acabadoRepository.save(acabado);
                    result.add(a);
                }
            }
            if(result.isEmpty()){
                throw new IllegalArgumentException("acabados: " + datos.get("personalizaciones"));
            }
        }else{
            throw new IllegalArgumentException("acabados: " + datos.get("personalizaciones"));
        }
        return result;
    }
    
    public List<Acabado> findAcabadoByCentro(Long centroId){
        return acabadoRepository.findByCentro(centroId);        
    }

    public List<Acabado> findByCentro(Long centroId){
        return acabadoRepository.findByCentro(centroId);        
    }
    
    public void removeAcabadosbyCentro(Long centroId) {
		List<Acabado> acabados = acabadoRepository.findByCentro(centroId);
		for (Acabado a : acabados) {
			removeAcabado(a.getId());
		}
	}
}
