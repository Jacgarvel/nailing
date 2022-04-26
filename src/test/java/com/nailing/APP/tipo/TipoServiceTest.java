package com.nailing.APP.tipo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nailing.app.AppApplication;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;
import com.nailing.app.centro.Suscripcion;
import com.nailing.app.components.Fases;
import com.nailing.app.tipo.NombreTipo;
import com.nailing.app.tipo.Tipo;
import com.nailing.app.tipo.TipoService;

@SpringBootTest(classes = AppApplication.class)
public class TipoServiceTest {

	@Autowired
	private TipoService tipoService;
	@Autowired
	private CentroService centroService;
	
	private Centro centroAnadido;
	private Tipo tipo;
	
	@BeforeEach
	public void setUp(){
		Centro centro;
		centro = new Centro();
		centro.setId((long)6000);
		centro.setNombre("PaquitoElUnyas");
		centro.setPagado(true);
		centro.setCreditosrestantes(150);
		centro.setUltimaSuscripcion(LocalDate.now());
		centro.setSuscripcion(Suscripcion.BASIC);
		centro.setProvincia("Sevilla");
		centro.setDiasDisponible("MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY");
		centro.setImagen("https://i.imgur.com/vPk5Wv2.jpeg");
		centro.setAperturaAM(LocalTime.of(8, 30));
		centro.setCierreAM(LocalTime.of(14, 0));
		centro.setAperturaPM(LocalTime.of(17, 0));
		centro.setCierrePM(LocalTime.of(21, 0));
		centro.setValoracionMedia(0.0);
		centro.setValoracionTotal(0);
		centro.setNumValoraciones(0);
		centroAnadido = centroService.addCentro(centro);
		
		tipo = new Tipo();
		tipo.setCentro(centroAnadido);
		tipo.setNombre(NombreTipo.ESCULPIDA);
		tipo.setCoste(77.50);
		tipo.setSiguienteFase(Fases.bases);
		tipo.setTiempo(10);
	}
	@AfterEach
	public void tearDown() {
		centroService.delete(centroAnadido.getId());
	}
	
	@Test
	public void addTipoTest() {
		Tipo tipoAnadido = tipoService.addTipo(tipo);
		assertEquals(tipoAnadido.getCentro(), tipo.getCentro());
		assertEquals(tipoAnadido.getNombre(), tipo.getNombre());
		assertEquals(tipoAnadido.getCoste(), tipo.getCoste());
		assertEquals(tipoAnadido.getSiguienteFase(), tipo.getSiguienteFase());
		assertEquals(tipoAnadido.getTiempo(), tipo.getTiempo());
	}
	@Test
	public void addTipoNegativeTest() {
		Tipo tipoError = new Tipo();
		tipoError.setCentro(centroAnadido);
		tipoError.setNombre(NombreTipo.ESCULPIDA);
		tipoError.setCoste(-100.);
		tipoError.setSiguienteFase(Fases.bases);
		tipoError.setTiempo(10);
		assertThrows(ConstraintViolationException.class, () -> {
			tipoService.addTipo(tipoError);
		});
	}
	
	@Test
	public void findByIdTest() {
		Tipo tipoAnadido = tipoService.addTipo(tipo);
		Tipo tipoObtenido = tipoService.findById(tipoAnadido.getId());
		assertEquals(tipoAnadido, tipoObtenido);
	}
	@Test
	public void findByIdNegativeTest() {
		assertThrows(NoSuchElementException.class, () -> {
			tipoService.findById(100L);
		});
	}
	
	@Test
	public void findAllTest() {
		Tipo tipoAnadido = tipoService.addTipo(tipo);
		List<Tipo> tipos = (List<Tipo>) tipoService.findAll();
		assertTrue(tipos.size() > 1);
		assertTrue(tipos.contains(tipoAnadido));
	}
	
	@Test
	public void removeTipoTest() {
		Tipo tipoAnadido = tipoService.addTipo(tipo);
		tipoService.removeTipo(tipoAnadido.getId());
		List<Tipo> tipos = (List<Tipo>) tipoService.findAll();
		assertFalse(tipos.contains(tipoAnadido));
	}
	@Test
	public void removeTipoNegativeTest() {
		assertThrows(NoSuchElementException.class, () -> {
			tipoService.removeTipo(100L);
		});
	}
	
	@Test
	public void findByCentroTest() {
		Tipo tipoAnadido = tipoService.addTipo(tipo);
		List<Tipo> tipos = tipoService.findByCentro(centroAnadido.getId());
		assertEquals(tipos.get(0), tipoAnadido);
	}
	@Test
	public void findByCentroNegativeTest() {
		assertTrue(tipoService.findByCentro(100L).isEmpty());
	}
	
	@Test
	public void listByCentroTest() {
		Tipo tipoAnadido = tipoService.addTipo(tipo);
		List<Tipo> tipos = tipoService.listByCentro(centroAnadido.getId());
		assertEquals(tipos.get(0), tipoAnadido);
	}
	@Test
	public void listByCentroNegativeTest() {
		assertTrue(tipoService.listByCentro(100L).isEmpty());
	}
	
	@Test
	public void removeTiposByCentroTest() {
		Tipo tipoAnadido = tipoService.addTipo(tipo);
		tipoService.removeTiposbyCentro(centroAnadido.getId());
		List<Tipo> tipos = (List<Tipo>) tipoService.findAll();		
		assertFalse(tipos.contains(tipoAnadido));
	}

	@Test
	public void listPosibleTipoTest() {
		List<String> tipos = tipoService.listPosibleTipo();
		assertEquals(2, tipos.size());
		assertTrue(tipos.contains(NombreTipo.NATURAL.toString()));
		assertTrue(tipos.contains(NombreTipo.ESCULPIDA.toString()));
	}
	
	@Test
	public void addTipoCentroTest() {
		Map<String, List<String>> datos = new HashMap<String, List<String>>();
		
		String centroKey = "centro";
		List<String> centroValue = new ArrayList<String>();
		centroValue.add(centroAnadido.getId().toString());
		String costeKey = "coste";
		List<String> costeValue = new ArrayList<String>();
		costeValue.add("50");
		String duracionKey = "tiempo";
		List<String> duracionValue = new ArrayList<String>();
		duracionValue.add("5");
		String persoKey = "personalizaciones";
		List<String> persoValue = new ArrayList<String>();
		persoValue.add(NombreTipo.ESCULPIDA.toString());
		
		datos.put(centroKey, centroValue);
		datos.put(costeKey, costeValue);
		datos.put(duracionKey, duracionValue);
		datos.put(persoKey, persoValue);
		
		List<Tipo> tipos = tipoService.addTipoCentro(datos);
		assertEquals(centroService.findById(centroAnadido.getId()).get(), tipos.get(0).getCentro());
	}
	@Test
	public void addTipoCentroNegativeTest() {
		Map<String, List<String>> datos = new HashMap<String, List<String>>();
		String centroKey = "centro";
		List<String> centroValue = null;
		String costeKey = "coste";
		List<String> costeValue = new ArrayList<String>();
		costeValue.add("50");
		String duracionKey = "tiempo";
		List<String> duracionValue = new ArrayList<String>();
		duracionValue.add("5");
		String persoKey = "personalizaciones";
		List<String> persoValue = new ArrayList<String>();
		persoValue.add(NombreTipo.ESCULPIDA.toString());
		
		datos.put(centroKey, centroValue);
		datos.put(costeKey, costeValue);
		datos.put(duracionKey, duracionValue);
		datos.put(persoKey, persoValue);
		
		assertThrows(IllegalArgumentException.class, () -> {
            	tipoService.addTipoCentro(datos);
            
        });
	}
}
