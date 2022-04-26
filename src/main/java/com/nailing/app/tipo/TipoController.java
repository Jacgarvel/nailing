/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.tipo;

import static com.nailing.app.usuario.AuthoritiesConstants.*;

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

/**
 *
 * @author CANDELA
 */
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("/tipos")
public class TipoController {
    
    @Autowired
    TipoService tipoService;
    
    @Operation(summary = "Lista todos los Tipos")
    @PreAuthorize("hasAuthority('"+ ADMIN +"')")
    @GetMapping("/list")
    public ResponseEntity<List<Tipo>> listTipos(){
        List<Tipo> tipos = (List<Tipo>) tipoService.findAll();
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }
    
    @Operation(summary = "Borra un Tipo")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @DeleteMapping("/delete/{id}")
	public void deleteTipo(@PathVariable Long id) {
		tipoService.removeTipo(id);
	}
    
    @Operation(summary = "Muestra un Tipo")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/show/{id}")
	public ResponseEntity<Tipo> showTipo(@PathVariable Long id){
		return new ResponseEntity<>(tipoService.findById(id), HttpStatus.OK);
	}

    @Operation(summary = "Lista Tipos en funcion de Centro")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @GetMapping("/centro/{centroId}")
	public  ResponseEntity<List<Tipo>> findByCentro(@PathVariable Long centroId){
		List<Tipo> tipos = tipoService.findByCentro(centroId);
		return new ResponseEntity<>(tipos, HttpStatus.OK);
	}

    @Operation(summary = "Lista todos los posibles Tipos")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/all")
    public ResponseEntity<List<String>> listPosibleTipo(){
        List<String> tipos = tipoService.listPosibleTipo();
        return new ResponseEntity<>(tipos,HttpStatus.OK);
    }

    @Operation(summary = "Lista Todos los Tipos de un Centro")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/centro/{centroId}/list")
    public ResponseEntity<List<Tipo>> listByCentro(@PathVariable Long centroId){
        List<Tipo> tipos = tipoService.listByCentro(centroId);
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }
    
    @Operation(summary = "Añade un Tipo a un Centro")
    @PreAuthorize("hasAuthority('"+ OWNER +"')")
    @PostMapping("/add/centro")
    public ResponseEntity<List<Tipo>> addTipoCentro(@RequestBody Map<String,List<String>> tipids){
        try{
            List<Tipo> tipos = tipoService.addTipoCentro(tipids);
            return new ResponseEntity<>(tipos, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            List<Tipo> tipos = new ArrayList<>();
            return new ResponseEntity<>(tipos, HttpStatus.BAD_REQUEST);
        }
    }
}
