package com.nailing.app.tamanyo;

import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroRepository;
import com.nailing.app.components.Fases;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nailing.app.forma.Forma;
import com.nailing.app.forma.FormaRepository;
import java.util.Map;

@Service("tamanyoService")
public class TamanyoService {
	
	@Autowired
	private TamanyoRepository tamRepository;
	@Autowired
	private FormaRepository formaRepository;
        @Autowired
        private CentroRepository centroRepository;
	
//	guardar/editar
	public Tamanyo addTamanyo(Tamanyo tam) {
		
		return tamRepository.save(tam);
	}
	
//	encontrar Tamanyo por su ID
	public Tamanyo findById(Long id) {
		return tamRepository.findById(id);
	}
	
//	todos los tamanyos
	public List<Tamanyo> findAll(){
		return tamRepository.findAll();
	}
	
//	borrar un Tamanyo por su ID
	public void removeTamanyo(Long id) {
		Tamanyo tam = findById(id);
		if(tam!=null)
			tamRepository.delete(tam);
	}
	public void removeTamanyobyCentro(Long centroId) {
		List<Tamanyo> tamanyos = tamRepository.findByCentro(centroId);
		for (Tamanyo t : tamanyos) {
			removeTamanyo(t.getId());
		}
	}
//	encontrar los tamanyos que posee el centro dado según la forma seleccionada
	public List<Tamanyo> findTamanyosByCentroForma(Long formaId, Long centroId) {
		Forma forma = formaRepository.findById(formaId).get();
		List<Tamanyo> result = new ArrayList<>();
		List<NombreTamanyo> tams = null;
		List<Tamanyo> tamsCentro = tamRepository.findByCentro(centroId);
		
		switch(forma.getNombre()) {
		case ALMOND:
			tams = Arrays.asList(NombreTamanyo.XS, NombreTamanyo.S, NombreTamanyo.M, NombreTamanyo.L, NombreTamanyo.XL, NombreTamanyo.XXL,
									NombreTamanyo.RELLENO);
			break;
		
		case STILETTO: case BALLERINA:	
			tams = Arrays.asList(NombreTamanyo.M, NombreTamanyo.L, NombreTamanyo.XL, NombreTamanyo.XXL, NombreTamanyo.RELLENO);
			break;
			
		default:
			tams = Arrays.asList(NombreTamanyo.values());
			break;
			
		}
		
		for(Tamanyo t: tamsCentro) {
			if(tams.contains(t.getNombre())){
				result.add(t);
			}
		}
		
		return result;
	}
        
    public List<String> listPosibleTamanyo(){
        List<String> tamanyos = new ArrayList<>();
        tamanyos.add(NombreTamanyo.XXS.toString());
        tamanyos.add(NombreTamanyo.XS.toString());
        tamanyos.add(NombreTamanyo.S.toString());
        tamanyos.add(NombreTamanyo.M.toString());
        tamanyos.add(NombreTamanyo.L.toString());
        tamanyos.add(NombreTamanyo.XL.toString());
        tamanyos.add(NombreTamanyo.XXL.toString());
        tamanyos.add(NombreTamanyo.RELLENO.toString());
        return tamanyos;
    }
    
    public List<Tamanyo> addTamanyoCentro(Map<String,List<String>> datos){
        Centro centro = null;
        Double precio = null;
        Integer duracion = null;
        
        List<Tamanyo> result = new ArrayList<>();
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
                Tamanyo tamanyo = new Tamanyo(duracion,precio,Fases.disenyos,centro);
                switch (p){
                    case "XXS":
                        tamanyo.setNombre(NombreTamanyo.XXS);
                        break;
                    case "XS":
                        tamanyo.setNombre(NombreTamanyo.XS);
                        break;
                    case "S":
                        tamanyo.setNombre(NombreTamanyo.S);
                        break;
                    case "M":
                        tamanyo.setNombre(NombreTamanyo.M);
                        break;
                    case "L":
                        tamanyo.setNombre(NombreTamanyo.L);
                        break;
                    case "XL":
                        tamanyo.setNombre(NombreTamanyo.XL);
                        break;
                    case "XXL":
                        tamanyo.setNombre(NombreTamanyo.XXL);
                        break;
                    case "RELLENO":
                        tamanyo.setNombre(NombreTamanyo.RELLENO);
                        break;
                    default:
                        break;
                }
                if(tamanyo.getNombre()!=null){
                    Tamanyo d = tamRepository.save(tamanyo);
                    result.add(d);
                }
            }
            if(result.isEmpty()){
                throw new IllegalArgumentException("tamanyos: " + datos.get(persoKey));
            }
        }else{
            throw new IllegalArgumentException("tamanyos: " + datos.get(persoKey));
        }
        return result;
    }

    public List<Tamanyo> findByCentro(Long centroId){
        return tamRepository.findByCentro(centroId);
    }
}
