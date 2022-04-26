package com.nailing.app.tamanyo;

import static com.nailing.app.usuario.AuthoritiesConstants.ADMIN;
import static com.nailing.app.usuario.AuthoritiesConstants.OWNER;
import static com.nailing.app.usuario.AuthoritiesConstants.USER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("/tamanyos")
public class TamanyoController {

	@Autowired
	TamanyoService tamService;
		
//	mostrar todos los tamaños existentes en la base de datos
	@Operation(summary = "Lista todos los Tamaños")
	@PreAuthorize("hasAuthority('"+ ADMIN +"')")
	@GetMapping("/list")
	public ResponseEntity<List<Tamanyo>> listTamanyos(){
		List<Tamanyo> tams = tamService.findAll();
		return new ResponseEntity<>(tams, HttpStatus.OK);
	}
	
//	borrar un tamaño por su ID
	@Operation(summary = "Borra un Tamaño")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@DeleteMapping("/delete/{id}")
	public void deleteBase(@PathVariable Long id) {
		tamService.removeTamanyo(id);
	}
	
//	encontrar un tamanyo por su ID
	@Operation(summary = "Muestra un Tamaño")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@GetMapping("/show/{id}")
	public ResponseEntity<Tamanyo> showTamanyo(@PathVariable Long id){
		return new ResponseEntity<>(tamService.findById(id), HttpStatus.OK);
	}
	
	@Operation(summary = "Muestra Tamaños en funcion de Centro y Forma")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
	@GetMapping("/{formaId}/centro/{centroId}")
	public  ResponseEntity<List<Tamanyo>> tamanyosByCentroForma(@PathVariable Long formaId, @PathVariable Long centroId){
		List<Tamanyo> tams = tamService.findTamanyosByCentroForma(formaId, centroId);
		return new ResponseEntity<>(tams, HttpStatus.OK);
	}
    
	@Operation(summary = "Muestra todos los Tamaños")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/all")
    public ResponseEntity<List<String>> listPosibleTamanyo(){
        List<String> tamanyos = tamService.listPosibleTamanyo();
        return new ResponseEntity<>(tamanyos,HttpStatus.OK);
    }

	@Operation(summary = "Lista todos los Tamaños de un Centro")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@GetMapping("/centro/{centroId}/list")
	public ResponseEntity<List<Tamanyo>> listByCentro(@PathVariable Long centroId){
		List<Tamanyo> tamanyos = tamService.findByCentro(centroId);
		return new ResponseEntity<>(tamanyos, HttpStatus.OK);
	}
    
	@Operation(summary = "Añade un Tamaño a un Centro")
	@PreAuthorize("hasAuthority('"+ OWNER +"')")
    @PostMapping("/add/centro")
    public ResponseEntity<List<Tamanyo>> addTamanyoCentro(@RequestBody Map<String,List<String>> tamids){
        try{
            List<Tamanyo> tamanyos = tamService.addTamanyoCentro(tamids);
            return new ResponseEntity<>(tamanyos, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
			List<Tamanyo> tamanyos = new ArrayList<>();
            return new ResponseEntity<>(tamanyos, HttpStatus.BAD_REQUEST);
        }
    }
}
