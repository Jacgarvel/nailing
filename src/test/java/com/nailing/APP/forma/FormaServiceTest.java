package com.nailing.APP.forma;

import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.Validator;

import com.nailing.app.AppApplication;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;
import com.nailing.app.centro.Suscripcion;
import com.nailing.app.components.Fases;
import com.nailing.app.forma.Forma;
import com.nailing.app.forma.FormaService;
import com.nailing.app.forma.NombreForma;



@SpringBootTest(classes = AppApplication.class)
public class FormaServiceTest {

	@Autowired
	private FormaService formaSer;
	@Autowired
	private CentroService centroSer;
	private Forma forma;
	private Centro centro;
	private Centro centroAnadido;
	@Autowired
	Validator validator;
	@BeforeEach
	public void setUp() {
		centro = new Centro();
		centro.setId((long)6000);
		centro.setNombre("PaquitoElUnyas");
		centro.setPagado(true);
		centro.setCreditosrestantes(150);
		centro.setUltimaSuscripcion(LocalDate.now());
		centro.setSuscripcion(Suscripcion.BASIC);
		centro.setProvincia("Sevilla");
		centro.setDiasDisponible("MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY");
		centro.setImagen("urlimagen");
		centro.setAperturaAM(LocalTime.of(8, 30));
		centro.setCierreAM(LocalTime.of(14, 0));
		centro.setAperturaPM(LocalTime.of(17, 0));
		centro.setCierrePM(LocalTime.of(21, 0));
		centro.setValoracionMedia(0.0);
		centro.setValoracionTotal(0);
		centro.setNumValoraciones(0);
		centroAnadido = centroSer.addCentro(centro);
	}
	//Se comprueba que los datos seteados previamente con los datos de la forma una vez añadida a la base de datos
	@Test
	public void addFormaTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		forma.setTiempo(30);
		Forma formaAnadida = formaSer.addForma(forma);
		assertEquals(forma.getCentro(), formaAnadida.getCentro());
		assertEquals(forma.getCoste(), formaAnadida.getCoste());
		assertEquals(forma.getNombre(), formaAnadida.getNombre());
		assertEquals(forma.getSiguienteFase(), formaAnadida.getSiguienteFase());
		assertEquals(forma.getTiempo(), formaAnadida.getTiempo());
	}
	//Se comprueba que si se introduce un atributo nulo, de un error de validación
	@Test
	public void addFormaNullTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setTiempo(null);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		assertThrows(ConstraintViolationException.class, new Executable() {
            
            public void execute() throws Throwable {
                formaSer.addForma(forma);
            }
        });
    }
	
	//Se comprueba que los datos de la forma que se obtiene son los mismos que los datos de la forma recientemente añadida
	@Test
	public void findByIdTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		forma.setTiempo(30);
		Forma formaAnadida = formaSer.addForma(forma);
		Forma formaObtenida = formaSer.findById(formaAnadida.getId());
		assertEquals(formaObtenida.getId(), formaAnadida.getId());
	}
	//Se comprueba que si se le pasa una id erronea da un error de validacion
	@Test
	public void notFindByIdTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		forma.setTiempo(30);
		Forma formaAnadida = formaSer.addForma(forma);
		assertThrows(NoSuchElementException.class, new Executable() {
            
            public void execute() throws Throwable {
            	formaSer.findById(formaAnadida.getId()+1);
            }
        });
		
	}
	//Se comprueba que una vez añadida la nueva forma esta se lista junto con las otras
	@Test
	public void findAllTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		forma.setTiempo(30);
		Forma formaAnadida = formaSer.addForma(forma);
		List<Forma> formasObtenidas = formaSer.findAll();
		assertTrue(formasObtenidas.contains(formaAnadida));
		
	}
	//Se comprueba que la forma no está en la lista dado que ha sido eliminada
	@Test
	public void removeFormaTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		forma.setTiempo(30);
		Forma formaAnadida = formaSer.addForma(forma);
		this.formaSer.removeForma(formaAnadida.getId());
		assertFalse(formaSer.findAll().contains(formaAnadida));
	}
	//Se comprueba que la forma que se introduce con un centro asociado se puede encontrar en base a ese centro
	@Test
	public void findFormasbyCentroTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		forma.setTiempo(30);
		Forma formaAnadida = formaSer.addForma(forma);
		List<Forma> formaCentro = formaSer.findFormasByCentroBase(centroAnadido.getId());
		assertEquals(formaAnadida.getId(), formaCentro.get(0).getId());
	}
	//Se comprueba que las formas posibles que ofrece el service son las mismas que un array creado a mano
	@Test
	public void listPosiblesFormasTest() {
		List<String> formas = new ArrayList<String>();
		formas.add("SQUARE");
		formas.add("ROUND");
		formas.add("SQUOVAL");
		formas.add("ALMOND");
		formas.add("STILETTO");
		formas.add("BALLERINA");
		List<String> formasPosibles = formaSer.listPosibleForma();
		assertEquals(formas, formasPosibles);
	}
	//Se comprueba que se eliminen todas las formas si se elimina un centro que las contenga, comprobando que no está en la lista total de formas
	@Test
	public void removeFormabyCentroTest() {
		forma = new Forma();
		forma.setCentro(centroAnadido);
		forma.setCoste(20.0);
		forma.setNombre(NombreForma.STILETTO);
		forma.setSiguienteFase(Fases.tamanyos);
		forma.setTiempo(30);
		Forma formaAnadida = formaSer.addForma(forma);
		this.formaSer.removeFormabyCentro(centroAnadido.getId());
		assertFalse(formaSer.findAll().contains(formaAnadida));
	}
	//Se comprueba que se ha añadido una forma a un centro de manera correcta
	@Test
	public void addFormaCentroTest() {
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
		persoValue.add("SQUARE");
		datos.put(centroKey, centroValue); datos.put(costeKey, costeValue);datos.put(duracionKey, duracionValue); datos.put(persoKey, persoValue);
		List<Forma> formasAnadidas = formaSer.addFormaCentro(datos);
		assertEquals(centroSer.findById(centroAnadido.getId()).get(), formasAnadidas.get(0).getCentro());
	}
	//Se comprueba que la falta de datos provoca una excepcion a la hora de añadir una forma a un centro
	@Test
	public void addFormaCentroNullTest() {
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
		persoValue.add("SQUARE");
		datos.put(centroKey, centroValue); datos.put(costeKey, costeValue);datos.put(duracionKey, duracionValue); datos.put(persoKey, persoValue); 
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            public void execute() throws Throwable {
            	formaSer.addFormaCentro(datos);
            }
        });
	}
	@AfterEach
	public void deleteData() {
		centroSer.delete(centroAnadido.getId());
	}
}
