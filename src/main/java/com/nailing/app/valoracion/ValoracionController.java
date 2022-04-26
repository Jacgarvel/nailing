/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.valoracion;

import static com.nailing.app.usuario.AuthoritiesConstants.ADMIN;
import static com.nailing.app.usuario.AuthoritiesConstants.OWNER;
import static com.nailing.app.usuario.AuthoritiesConstants.USER;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CANDELA
 */
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/valoraciones")
public class ValoracionController {
    
    @Autowired
    ValoracionService valoracionService;
    
    @Operation(summary = "Lista todos las valoraciones")
    @PreAuthorize("hasAuthority('"+ OWNER +"') or hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @GetMapping("/list")
    public ResponseEntity<List<Valoracion>> listValoraciones(){
	List<Valoracion> vals = StreamSupport.stream(valoracionService.findAll().spliterator(), false)
                        .collect(Collectors.toList());
	return new ResponseEntity<>(vals, HttpStatus.OK);
	}
    
    @Operation(summary = "Añade una valoracion a un Centro")
    @PreAuthorize("hasAuthority('"+ OWNER +"') or hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @PostMapping("/add/centro")
    public ResponseEntity<Valoracion> addValoracionCentro(@RequestBody Map<String,String> valoracion){
        try{
            Valoracion val= valoracionService.addValoracion(valoracion);
            return new ResponseEntity<>(val, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
			Valoracion val = valoracionService.addValoracion(valoracion);
            return new ResponseEntity<>(val, HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @Operation(summary = "Edita una valoracion")
    @PreAuthorize("hasAuthority('"+ OWNER +"') or hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @PutMapping("/edit")
    public ResponseEntity<Valoracion> updateValoracionCentro(@RequestBody Valoracion valoracion){
        try{
            Valoracion val= valoracionService.updateValoracion(valoracion);
            return new ResponseEntity<>(val, HttpStatus.OK);
        }catch(IllegalArgumentException e){
            Valoracion val = valoracionService.updateValoracion(valoracion);
            return new ResponseEntity<>(val, HttpStatus.BAD_REQUEST);
        }
       
    }
}
