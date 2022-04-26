/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.APP.centro;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nailing.app.centro.CentroService;

import java.time.LocalDate;

import com.nailing.app.AppApplication;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.Suscripcion;
import com.nailing.app.usuario.UsuarioService;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author jaime
 */

@SpringBootTest(classes = AppApplication.class)
public class CentroServiceTest {
    
    @Autowired
    private CentroService centroService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Test
    public void shouldFindById(){
        assertNotNull(centroService.findById(1L).get());
    }
    
    @Test
    public void shouldFindAll(){
        Centro centro = new Centro("Nails by Claudia","https://i.ibb.co/qkFKLsm/F253-E9-DA-6780-4-A9-D-9-EAB-804784-BBEE21.jpg","Málaga",LocalTime.of(8, 30),LocalTime.of(14, 0),LocalTime.of(17, 0),LocalTime.of(21, 0),Suscripcion.BASIC,LocalDate.now(),150,true,"MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY",0.,0,0);
        centroService.addCentro(centro);
        assertTrue(centroService.findAll().size()>1);
        assertTrue(centroService.findAll().contains(centro));
    }
    
    @Test
    public void ShouldDeleteCentro(){
        Integer nc = centroService.findAll().size();
        centroService.delete(3L);
        Integer ncn = centroService.findAll().size();
        assertEquals(ncn,nc-1);
    }
    
    @Test
    public void ShouldAddCentro(){
        Integer nc = centroService.findAll().size();
        Centro centro = new Centro("Nails by Claudia","https://i.ibb.co/qkFKLsm/F253-E9-DA-6780-4-A9-D-9-EAB-804784-BBEE21.jpg","Málaga",LocalTime.of(8, 30),LocalTime.of(14, 0),LocalTime.of(17, 0),LocalTime.of(21, 0),Suscripcion.BASIC,LocalDate.now(),150,true,"MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY",0.,0,0);
        centroService.addCentro(centro);
        Integer ncn = centroService.findAll().size();
        assertEquals(ncn,nc+1);
    }
    
    @Test
    public void ShouldAsociarCentroUsuario(){
        centroService.asociarCentroUsuario(usuarioService.findByUsuario("usuario1"), centroService.findById(1L).get());
        assertEquals(usuarioService.findByUsuario("usuario1").getCentro(),centroService.findById(1L).get());
    }
    
    @Test
    public void ShouldUpdateImage(){
        centroService.updateCentroImage(1L, "https://uridetest.com");
        assertEquals("https://uridetest.com",centroService.findById(1L).get().getImagen());
    }
    
}
