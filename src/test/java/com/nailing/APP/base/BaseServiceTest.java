/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.APP.base;

import com.nailing.app.AppApplication;
import com.nailing.app.base.Base;
import com.nailing.app.base.BaseService;
import com.nailing.app.base.NombreBase;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;
import com.nailing.app.centro.Suscripcion;
import com.nailing.app.components.Fases;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.Validator;

@SpringBootTest(classes = AppApplication.class)
public class BaseServiceTest {
    
    
        @Autowired
	private BaseService baseService;
	@Autowired
	private CentroService centroService;
	private Base base;
	private Centro centro;
	private Centro centroCreado;
	@Autowired
	Validator validator;
	@BeforeEach
	public void setUp() {
		centro = new Centro();
		centro.setId((long)8000);
		centro.setNombre("UnyasMariCarmen");
		centro.setPagado(true);
		centro.setCreditosrestantes(150);
		centro.setUltimaSuscripcion(LocalDate.now());
		centro.setSuscripcion(Suscripcion.BASIC);
		centro.setProvincia("Sevilla");
		centro.setDiasDisponible("MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY");
		centro.setImagen("urlimagen");
		centro.setAperturaAM(LocalTime.of(9, 30));
		centro.setCierreAM(LocalTime.of(14, 0));
		centro.setAperturaPM(LocalTime.of(17, 0));
		centro.setCierrePM(LocalTime.of(21, 0));
                centro.setValoracionMedia(2.);
                centro.setValoracionTotal(2);
                centro.setNumValoraciones(1);
		centroCreado = centroService.addCentro(centro);
	}   
        
        @Test
	public void addBaseTest() {
		base = new Base();
		base.setCentro(centroCreado);
		base.setCoste(15.0);
		base.setNombre(NombreBase.GEL);
		base.setSiguienteFase(Fases.formas);
		base.setTiempo(30);
		Base baseAnyadida = baseService.addBase(base);
		assertEquals(base.getCentro(), baseAnyadida.getCentro());
		assertEquals(base.getCoste(), baseAnyadida.getCoste());
		assertEquals(base.getNombre(), baseAnyadida.getNombre());
		assertEquals(base.getSiguienteFase(), baseAnyadida.getSiguienteFase());
		assertEquals(base.getTiempo(), baseAnyadida.getTiempo());
	}  
        
        
        @Test
	public void addBaseNullTest() {
		base = new Base();
		base.setCentro(centroCreado);
		base.setCoste(15.0);
		base.setTiempo(null);
		base.setNombre(NombreBase.GEL);
		base.setSiguienteFase(Fases.formas);
		assertThrows(ConstraintViolationException.class, new Executable() {
            
            public void execute() throws Throwable {
                baseService.addBase(base);
            }
        });
    }
        
        @Test
	public void findByIdTest() {
		base = new Base();
		base.setCentro(centroCreado);
		base.setCoste(15.0);
		base.setNombre(NombreBase.GEL);
		base.setSiguienteFase(Fases.formas);
		base.setTiempo(30);
		Base baseAnyadida = baseService.addBase(base);
		Base baseBuscada = baseService.findById(baseAnyadida.getId());
		assertEquals(baseBuscada.getId(), baseAnyadida.getId());
	}
        
        
        @Test
	public void findAllTest() {
		base = new Base();
		base.setCentro(centroCreado);
		base.setCoste(15.0);
		base.setNombre(NombreBase.GEL);
		base.setSiguienteFase(Fases.formas);
		base.setTiempo(30);
		Base baseAnyadida = baseService.addBase(base);
		assertTrue(StreamSupport.stream(baseService.findAll().spliterator(), false)
                    .anyMatch(baseAnyadida::equals));
	}
        
        @Test
	public void removeBaseTest() {
		base = new Base();
		base.setCentro(centroCreado);
		base.setCoste(15.0);
		base.setNombre(NombreBase.GEL);
		base.setSiguienteFase(Fases.formas);
		base.setTiempo(30);
		Base baseAnyadida = baseService.addBase(base);
		this.baseService.removeBase(baseAnyadida.getId());
		assertFalse(StreamSupport.stream(baseService.findAll().spliterator(), false)
                    .anyMatch(baseAnyadida::equals));
	}
        
        @Test
	public void removeBasebyCentroTest() {
		base = new Base();
		base.setCentro(centroCreado);
		base.setCoste(15.0);
		base.setNombre(NombreBase.GEL);
		base.setSiguienteFase(Fases.formas);
		base.setTiempo(30);
		Base baseAnyadida = baseService.addBase(base);
		this.baseService.removeBasesbyCentro(centroCreado.getId());
		assertFalse(StreamSupport.stream(baseService.findAll().spliterator(), false)
                    .anyMatch(baseAnyadida::equals));
	}
     
        @Test
	public void findBasebyCentroTest() {
		base = new Base();
		base.setCentro(centroCreado);
		base.setCoste(15.0);
		base.setNombre(NombreBase.GEL);
		base.setSiguienteFase(Fases.formas);
		base.setTiempo(30);
		Base baseAnyadida = baseService.addBase(base);
		List<Base> bases = baseService.findByCentro(centroCreado.getId());
		assertEquals(baseAnyadida.getId(), bases.get(0).getId());
	}
        
        
        @AfterEach
	public void deleteData() {
		centroService.delete(centroCreado.getId());
	}
        
}
