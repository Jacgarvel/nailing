package com.nailing.app.base;

import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroRepository;
import com.nailing.app.components.Fases;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nailing.app.tipo.Tipo;
import com.nailing.app.tipo.TipoRepository;
import java.util.Map;

@Service("baseService")
public class BaseService {

	@Autowired
	private BaseRepository baseRepository;
	@Autowired
	private TipoRepository tipoRepository;
        
    @Autowired
    private CentroRepository centroRepository;
	
//	guardar/editar
	public Base addBase(Base base) {
		
		return baseRepository.save(base);
	}
	
//	encontrar base por su ID
	public Base findById(Long id) {
		return baseRepository.findById(id);
	}
	
//	todas las bases
	public List<Base> findAll(){
		return baseRepository.findAll();
	}
	
//	borrar una base por su ID
	public void removeBase(Long id) {
		Base base = findById(id);
		if(base!=null)
			baseRepository.delete(base);
	}
	public void removeBasesbyCentro(Long centroId) {
		List<Base> bases = baseRepository.findByCentro(centroId);
		for (Base b : bases) {
			removeBase(b.getId());
		}
	}
//	encontrar las bases que posee el centro dado según el tipo seleccionado
	public List<Base> findBasesByCentroTipo(Long tipoId, Long centroId) {
		Optional<Tipo> tipo = tipoRepository.findById(tipoId);
		List<Base> result = new ArrayList<>();
		List<NombreBase> bases;
		List<Base> basesCentro;
		
		switch (tipo.get().getNombre()) {
		case ESCULPIDA:
			bases = Arrays.asList(NombreBase.DUAL_SYSTEM, NombreBase.GEL, NombreBase.ACRILICO, NombreBase.ACRYGEL);
			basesCentro = baseRepository.findByCentro(centroId);
			
			for(Base b: basesCentro) {
				if(bases.contains(b.getNombre()))
					result.add(b);
			}
			break;

		case NATURAL:
			bases = Arrays.asList(NombreBase.SEMIPERMANENTE, NombreBase.SEMIPERMANENTE_REFUERZO, NombreBase.JAPONESA);
			basesCentro = baseRepository.findByCentro(centroId);
			
			for(Base b: basesCentro) {
				if(bases.contains(b.getNombre()))
					result.add(b);
			}
			break;
		}
		
		return result;
	}
        
        public List<String> listPosibleBase(){
        List<String> bases = new ArrayList<>();
        bases.add(NombreBase.DUAL_SYSTEM.toString());
        bases.add(NombreBase.GEL.toString());
        bases.add(NombreBase.ACRILICO.toString());
        bases.add(NombreBase.ACRYGEL.toString());
        bases.add(NombreBase.SEMIPERMANENTE.toString());
        bases.add(NombreBase.SEMIPERMANENTE_REFUERZO.toString());
        bases.add(NombreBase.JAPONESA.toString());
        return bases;
    }
    
    public List<Base> addBaseCentro(Map<String,List<String>> datos){
        Centro centro = null;
        Double precio = null;
        Integer duracion = null;
        
        List<Base> result = new ArrayList<>();
        
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
            throw new IllegalArgumentException("tiempo: " + datos.get("coste"));
        }
        
        if(!(datos.get("personalizaciones") == null || datos.get("personalizaciones").isEmpty() || datos.get("personalizaciones").get(0) == null)){
            for(String p:datos.get("personalizaciones")){
                Base base = new Base(duracion,precio,centro);
                switch (p){
                    case "DUAL_SYSTEM":
                        base.setNombre(NombreBase.DUAL_SYSTEM);
                        base.setSiguienteFase(Fases.formas);
                        break;
                    case "GEL":
                        base.setNombre(NombreBase.GEL);
                        base.setSiguienteFase(Fases.formas);
                        break;
                    case "ACRILICO":
                        base.setNombre(NombreBase.ACRILICO);
                        base.setSiguienteFase(Fases.formas);
                        break;
                    case "ACRYGEL":
                        base.setNombre(NombreBase.ACRYGEL);
                        base.setSiguienteFase(Fases.formas);
                        break;
                    case "SEMIPERMANENTE":
                        base.setNombre(NombreBase.SEMIPERMANENTE);
                        base.setSiguienteFase(Fases.disenyos);
                        break;
                    case "SEMIPERMANENTE_REFUERZO":
                        base.setNombre(NombreBase.SEMIPERMANENTE_REFUERZO);
                        base.setSiguienteFase(Fases.disenyos);
                        break;
                    case "JAPONESA":
                        base.setNombre(NombreBase.JAPONESA);
                        base.setSiguienteFase(Fases.fin);
                        break;
                }
                if(base.getNombre()!=null){
                    Base b = baseRepository.save(base);
                    result.add(b);
                }
                
            }
            if(result.isEmpty()){
                throw new IllegalArgumentException("bases: " + datos.get("personalizaciones"));
            }
        }else{
            throw new IllegalArgumentException("bases: " + datos.get("personalizaciones"));
        }
        return result;
    }

    public List<Base> findByCentro(Long centroId){
        return baseRepository.findByCentro(centroId);
    }
}
