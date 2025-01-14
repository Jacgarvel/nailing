/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.disenyo;

import com.nailing.app.base.Base;
import com.nailing.app.base.BaseRepository;
import com.nailing.app.base.NombreBase;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroRepository;
import com.nailing.app.components.Fases;
import com.nailing.app.tamanyo.Tamanyo;
import com.nailing.app.tamanyo.TamanyoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CANDELA
 */
@Service("disenyoService")
public class DisenyoService {
    @Autowired
    private DisenyoRepository disenyorepository;
    @Autowired
    private TamanyoRepository tamanyorepository;
    @Autowired
    private BaseRepository baserepository;
    @Autowired
    private CentroRepository centroRepository;

    public Disenyo addDisenyo(Disenyo disenyo){
        return disenyorepository.save(disenyo);
    }

    public Disenyo findById(Long id){
        return disenyorepository.findById(id).get();
    }

    public Iterable<Disenyo> findAll(){
        return disenyorepository.findAll();
    }

    public void removeDisenyo(Long id){
        Disenyo disenyo = findById(id);
        if(disenyo != null){
            disenyorepository.delete(disenyo);
        }
    }
    public void removeDisenyosbyCentro(Long centroId) {
		List<Disenyo> disenyos = disenyorepository.findByCentro(centroId);
		for (Disenyo d : disenyos) {
			removeDisenyo(d.getId());
		}
	}
    public List<Disenyo> findDisenyosByCentroTamanyo(Long tamanyoId, Long centroId){
        Tamanyo tamanyo = tamanyorepository.findById(tamanyoId);
        List<Disenyo> result = new ArrayList<>();
        List<NombreDisenyo> disenyos;
        List<Disenyo> disenyosCentro;
        
        switch(tamanyo.getNombre()){
            case XXS: case XS: case S: case M: case L: case XL: case XXL: case RELLENO:
                disenyos = Arrays.asList(NombreDisenyo.BABY_BOOMER,NombreDisenyo.DEGRADADO_COLOR,NombreDisenyo.ENCAPSULADO,NombreDisenyo.FRANCESA_REVERSA, NombreDisenyo.LISAS);
                disenyosCentro = disenyorepository.findByCentro(centroId);
                for(Disenyo d: disenyosCentro){
                    if(disenyos.contains(d.getNombre()))
                        result.add(d);
                }
                break;
            default:
                break;
        }
        return result;
    }
    
    public List<Disenyo> findDisenyosByCentroBase(Long baseId, Long centroId){
        Base base = baserepository.findById(baseId);
        List<Disenyo> result = new ArrayList<>();
        List<NombreDisenyo> disenyos;
        List<Disenyo> disenyosCentro;   
        if(base.getNombre().equals(NombreBase.SEMIPERMANENTE) || base.getNombre().equals(NombreBase.SEMIPERMANENTE_REFUERZO)){
                disenyos = Arrays.asList(NombreDisenyo.LISAS);
                disenyosCentro = disenyorepository.findByCentro(centroId);
                for(Disenyo d: disenyosCentro){
                    if(disenyos.contains(d.getNombre()))
                        result.add(d);
                }
        }
        return result;
    }
        
    public List<String> listPosibleDisenyo(){
        List<String> disenyos = new ArrayList<>();
        disenyos.add(NombreDisenyo.FRANCESA_REVERSA.toString());
        disenyos.add(NombreDisenyo.BABY_BOOMER.toString());
        disenyos.add(NombreDisenyo.DEGRADADO_COLOR.toString());
        disenyos.add(NombreDisenyo.ENCAPSULADO.toString());
        disenyos.add(NombreDisenyo.LISAS.toString());
        return disenyos;
    }
    
    public List<Disenyo> addDisenyoCentro(Map<String,List<String>> datos){
        Centro centro = null;
        Double precio = null;
        Integer duracion = null;
        
        List<Disenyo> result = new ArrayList<>();
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
                Disenyo disenyo = new Disenyo(duracion,precio,Fases.decoraciones,centro);
                switch (p){
                    case "FRANCESA_REVERSA":
                        disenyo.setNombre(NombreDisenyo.FRANCESA_REVERSA);
                        break;
                    case "BABY_BOOMER":
                        disenyo.setNombre(NombreDisenyo.BABY_BOOMER);
                        break;
                    case "DEGRADADO_COLOR":
                        disenyo.setNombre(NombreDisenyo.DEGRADADO_COLOR);
                        break;
                    case "ENCAPSULADO":
                        disenyo.setNombre(NombreDisenyo.ENCAPSULADO);
                        break;
                    case "LISAS":
                        disenyo.setNombre(NombreDisenyo.LISAS);
                        break;
                    default:
                        break;
                }
                if(disenyo.getNombre()!=null){
                    Disenyo d = disenyorepository.save(disenyo);
                    result.add(d);
                }
            }
            if(result.isEmpty()){
                throw new IllegalArgumentException("disenyos: " + datos.get(persoKey));
            }
        }else{
            throw new IllegalArgumentException("disenyos: " + datos.get(persoKey));
        }
        return result;
    }

    public List<Disenyo> findByCentro(Long centroId){
        return disenyorepository.findByCentro(centroId);
    }
}
