/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.disenyo;

import static com.nailing.app.usuario.AuthoritiesConstants.*;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

/**
 *
 * @author CANDELA
 */
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class DisenyoController {
    @Autowired
	DisenyoService disenyoService;
    
	@Operation(summary = "Lista todos los Diseños")
	@PreAuthorize("hasAuthority('"+ ADMIN +"')")
	@GetMapping("/disenyos/list")
	public ResponseEntity<List<Disenyo>> listDisenyos(){
		List<Disenyo> disenyos = (List<Disenyo>) disenyoService.findAll();
		return new ResponseEntity<>(disenyos, HttpStatus.OK);
	}
	
	@Operation(summary = "Borra un Diseño")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@DeleteMapping("/disenyos/delete/{id}")
	public void deleteDisenyo(@PathVariable Long id) {
		disenyoService.removeDisenyo(id);
	}
	
	@Operation(summary = "Muestra un Diseño")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@GetMapping("/disenyos/show/{id}")
	public ResponseEntity<Disenyo> showDisenyo(@PathVariable Long id){
		return new ResponseEntity<>(disenyoService.findById(id), HttpStatus.OK);
	}    
    
	@Operation(summary = "Muestra Diseños en función de Centro y Tamaño")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @GetMapping("/disenyos/{tamanyoId}/centro/{centroId}")
	public  ResponseEntity<List<Disenyo>> findDisenyosByCentroTamanyo(@PathVariable Long tamanyoId, @PathVariable Long centroId){
		List<Disenyo> disenyos = disenyoService.findDisenyosByCentroTamanyo(tamanyoId, centroId);
		return new ResponseEntity<>(disenyos, HttpStatus.OK);
	}

	@Operation(summary = "Muestra Diseños Naturales en función de Centro y Base")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @GetMapping("/disenyosNaturales/{baseId}/centro/{centroId}")
	public  ResponseEntity<List<Disenyo>> findDisenyosByCentroBase(@PathVariable Long baseId, @PathVariable Long centroId){
		List<Disenyo> disenyos = disenyoService.findDisenyosByCentroBase(baseId, centroId);
		return new ResponseEntity<>(disenyos, HttpStatus.OK);
	}
    
	@Operation(summary = "Lista todos los posibles Diseños")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/disenyos/all")
    public ResponseEntity<List<String>> listPosibleDisenyo(){
        List<String> disenyos = disenyoService.listPosibleDisenyo();
        return new ResponseEntity<>(disenyos,HttpStatus.OK);
    }

	@Operation(summary = "Lista los Diseños de un Centro")
	@PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
	@GetMapping("/disenyos/centro/{centroId}/list")
	public ResponseEntity<List<Disenyo>> findByCentro(@PathVariable Long centroId){
		List<Disenyo> disenyos = disenyoService.findByCentro(centroId);
		return new ResponseEntity<>(disenyos,HttpStatus.OK);
	}
    
	@Operation(summary = "Añade un Diseño a un Centro")
	@PreAuthorize("hasAuthority('"+ OWNER +"')")
    @PostMapping("/disenyos/add/centro")
    public ResponseEntity<List<Disenyo>> addDisenyoCentro(@RequestBody Map<String,List<String>> disids){
        try{
            List<Disenyo> disenyos = disenyoService.addDisenyoCentro(disids);
            return new ResponseEntity<>(disenyos, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
			List<Disenyo> disenyos = disenyoService.addDisenyoCentro(disids);
            return new ResponseEntity<>(disenyos, HttpStatus.BAD_REQUEST);
        }
    }
        
}
