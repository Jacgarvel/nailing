/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.centro;

import static com.nailing.app.usuario.AuthoritiesConstants.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import com.nailing.app.usuario.UsuarioService;

/**
 *
 * @author jaime
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("/centros")
@EnableScheduling
public class CentroController {
    
    @Autowired
    private CentroService centroService;
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Añade un Centro asociado a un Usuario")
    @PreAuthorize("hasAuthority('"+ USER +"')")
    @PostMapping("/add/{idUser}")
    public ResponseEntity<Centro> addCentro(@RequestBody Centro centro, @PathVariable int idUser){
        centroService.asociarCentroUsuario(usuarioService.findById((long) idUser).get(), centro);
        if(centro == null)
            return new ResponseEntity<>(centro, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(centro, HttpStatus.CREATED);
    }

    
   
    @Operation(summary = "Lista todos los Centros")
    @GetMapping("/list")
    public ResponseEntity<List<Centro>> findAll(){
	List<Centro> centros = centroService.findAll();
	return new ResponseEntity<>(centros, HttpStatus.OK);
    }
    
    @Operation(summary = "Borra un Centro")
    @PreAuthorize("hasAuthority('"+ ADMIN +"')")
    @DeleteMapping("/delete/{id}")
    public void deleteCentro(@PathVariable Long id) {
    	centroService.delete(id);
    }

    @Operation(summary = "Muestra un Centro")
    @GetMapping("/show/{id}")
    public ResponseEntity<Centro> findById(@PathVariable Long id){
	return new ResponseEntity<>(centroService.findById(id).get(), HttpStatus.OK);
    }
    
    @Operation(summary = "Edita un Centro")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @PutMapping("/edit")
    public ResponseEntity<Centro> updateCentro(@RequestBody Centro centro){
        Centro c = null;
        try{
            return new ResponseEntity<>(centroService.addCentro(centro), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
       
    }
    
    @Operation(summary = "Cambia la imagen de un centro")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @PutMapping("/edit/{idCentro}/image")
    public ResponseEntity<Centro> updateCentroImage(@PathVariable long idCentro, @RequestParam(value = "uri", required = true) String uri){
    	Centro centro = centroService.updateCentroImage(idCentro, uri);
    	if(centro == null)
    		return new ResponseEntity<>(centro, HttpStatus.BAD_REQUEST);
    	else
    		return new ResponseEntity<>(centro, HttpStatus.OK);
    }
    
    @Operation(summary = "Llamada automatica para comprobación de centros")
    @Scheduled(fixedRate = 86400000)
    @GetMapping("/comprobacionCentros")
    public void comprobacionCentros() {
    	centroService.comprobacionCentros();
    }
    
}
