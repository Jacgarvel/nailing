package com.nailing.app.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nailing.app.usuario.AuthoritiesConstants.*;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("/bases")
public class BaseController {
	
	@Autowired
	BaseService baseService;
	
//	mostrar todas las bases existentes en la base de datos
	@Operation(summary = "Lista todas las Bases")
	@PreAuthorize("hasAuthority('"+ ADMIN +"')")
	@GetMapping("/list")
	public ResponseEntity<List<Base>> listBases(){
		List<Base> bases = baseService.findAll();
		return new ResponseEntity<>(bases, HttpStatus.OK);
	}
	
//	borrar una base por su ID
	@Operation(summary = "Borra una Base")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@DeleteMapping("/delete/{id}")
	public void deleteBase(@PathVariable Long id) {
		baseService.removeBase(id);
	}
	
//	encontrar una base por su ID
	@Operation(summary = "Muestra una Base")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@GetMapping("/show/{id}")
	public ResponseEntity<Base> showBase(@PathVariable Long id){
		return new ResponseEntity<>(baseService.findById(id), HttpStatus.OK);
	}
	
	@Operation(summary = "Muestra Bases en funcion de Tipo y Centro")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
	@GetMapping("/{tipoId}/centro/{centroId}")
	public  ResponseEntity<List<Base>> basesByCentroTipo(@PathVariable Long tipoId, @PathVariable Long centroId){
		List<Base> bases = baseService.findBasesByCentroTipo(tipoId, centroId);
		return new ResponseEntity<>(bases, HttpStatus.OK);
	}
	
	@Operation(summary = "Muestra todas las posibles Bases")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/all")
    public ResponseEntity<List<String>> listPosibleBase(){
        List<String> bases = baseService.listPosibleBase();
        return new ResponseEntity<>(bases,HttpStatus.OK);
    }

	@Operation(summary = "Muestra las Bases asociadas a un Centro")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@GetMapping("/centro/{centroId}/list")
	public ResponseEntity<List<Base>> listByCentro(@PathVariable Long centroId){
		List<Base> bases = baseService.findByCentro(centroId);
		return new ResponseEntity<>(bases, HttpStatus.OK);
	}
    
	@Operation(summary = "Añade una Base a un Centro")
	@PreAuthorize("hasAuthority('"+ OWNER +"')")
    @PostMapping("/add/centro")
    public ResponseEntity<List<Base>> addBaseCentro(@RequestBody Map<String,List<String>> basids){
        try{
            List<Base> bases = baseService.addBaseCentro(basids);
            return new ResponseEntity<>(bases, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
