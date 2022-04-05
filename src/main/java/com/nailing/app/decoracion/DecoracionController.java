/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.decoracion;

import static com.nailing.app.usuario.AuthoritiesConstants.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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
 * @author Usuario
 */
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("/decoraciones")
public class DecoracionController {
    
    @Autowired
    DecoracionService decoracionService;
    
    @Operation(summary = "Añade una Decoracion")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @PostMapping("/add")
    public ResponseEntity<Decoracion> addDecoracion(@RequestBody Decoracion decoracion){
        Decoracion deco = decoracionService.addDecoracion(decoracion);
        if(deco == null)
            return new ResponseEntity<>(deco, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(deco, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Borra una Decoracion")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @DeleteMapping("/delete/{id}")
    public void deleteDecoracion(@PathVariable Long id){
        decoracionService.removeDecoracion(id);
    }
    
    @Operation(summary = "Muestra una Decoracion")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/show/{id}")
    public ResponseEntity<Decoracion> showDecoracion(@PathVariable Long id){
        try{
            Decoracion deco = decoracionService.findById(id);
            return new ResponseEntity<>(deco,HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(decoracionService.findById(id),HttpStatus.BAD_REQUEST);
        }       
    }
    
    @Operation(summary = "Lista todas las Decoraciones")
    @PreAuthorize("hasAuthority('"+ ADMIN +"')")
    @GetMapping("/list")
    public ResponseEntity<List<Decoracion>> listDecoraciones(){
        List<Decoracion> decos = StreamSupport.stream(decoracionService.findAll()
                .spliterator(), false).collect(Collectors.toList());
        return new ResponseEntity<>(decos,HttpStatus.OK);
    }
    
    @Operation(summary = "Muestra Decoraciones en funcion de Centro y Diseño")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ USER +"')")
    @GetMapping("{disenyoId}/centro/{centroId}")
    public ResponseEntity<List<Decoracion>> decoracionesByCentroDisenyo(@PathVariable Long disenyoId, @PathVariable Long centroId){
        List<Decoracion> decoraciones = decoracionService.findDecoracionByCentroDisenyo(disenyoId, centroId);
        return new ResponseEntity<>(decoraciones, HttpStatus.OK);
    }
    
    @Operation(summary = "Muestra las posibles Decoraciones")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/all")
    public ResponseEntity<List<String>> listPosibleDecoracion(){
        List<String> decoraciones = decoracionService.listPosibleDecoracion();
        return new ResponseEntity<>(decoraciones,HttpStatus.OK);
    }

    @Operation(summary = "Lista todas las Decoraciones de un Centro")
    @PreAuthorize("hasAuthority('"+ ADMIN +"') or hasAuthority('"+ OWNER +"')")
    @GetMapping("/centro/{centroId}/list")
    public ResponseEntity<List<Decoracion>> listByCentro(@PathVariable Long centroId){
        List<Decoracion> decoraciones = decoracionService.findByCentro(centroId);
        return new ResponseEntity<>(decoraciones,HttpStatus.OK);
    }

    @Operation(summary = "Añade una Decoracion a un Centro")
    @PreAuthorize("hasAuthority('"+ OWNER +"')")
    @PostMapping("/add/centro")
    public ResponseEntity<List<Decoracion>> addDecoracionCentro(@RequestBody Map<String,List<String>> decids){
        try{
            List<Decoracion> decoraciones = decoracionService.addDecoracionCentro(decids);
            return new ResponseEntity<>(decoraciones, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            List<Decoracion> decoraciones = decoracionService.addDecoracionCentro(decids);
            return new ResponseEntity<>(decoraciones, HttpStatus.BAD_REQUEST);
        }
    }
}
