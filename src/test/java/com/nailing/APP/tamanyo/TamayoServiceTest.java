package com.nailing.APP.tamanyo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.nailing.app.AppApplication;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;
import com.nailing.app.centro.Suscripcion;
import com.nailing.app.components.Fases;
import com.nailing.app.tamanyo.NombreTamanyo;
import com.nailing.app.tamanyo.Tamanyo;
import com.nailing.app.tamanyo.TamanyoService;


@SpringBootTest(classes = AppApplication.class)
public class TamayoServiceTest {

	@Autowired
	private TamanyoService tamanyoSer;
	@Autowired
	private CentroService centroSer;
	private Tamanyo tamanyo;
	private Centro centro;
	private Centro centroAnadido;
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
	public void addTamanyoTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(NombreTamanyo.M);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		Tamanyo tamanyoAnadido = tamanyoSer.addTamanyo(tamanyo);
		assertEquals(tamanyo.getCentro(), tamanyoAnadido.getCentro());
		assertEquals(tamanyo.getCoste(), tamanyoAnadido.getCoste());
		assertEquals(tamanyo.getNombre(), tamanyoAnadido.getNombre());
		assertEquals(tamanyo.getSiguienteFase(), tamanyoAnadido.getSiguienteFase());
		assertEquals(tamanyo.getTiempo(), tamanyoAnadido.getTiempo());
	}
	//Se comprueba que si se introduce un atributo nulo, de un error de validación
	@Test
	public void addFormaNullTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(null);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		assertThrows(ConstraintViolationException.class, new Executable() {
            
            public void execute() throws Throwable {
                tamanyoSer.addTamanyo(tamanyo);
            }
        });
    }
	//Se comprueba que los datos del tamanyo que se obtiene son los mismos que los datos del tamanyo recientemente añadida
	@Test
	public void findByIdTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(NombreTamanyo.M);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		Tamanyo tamanyoAnadido = tamanyoSer.addTamanyo(tamanyo);
		Tamanyo tamanyoObtendio = tamanyoSer.findById(tamanyoAnadido.getId());
		assertEquals(tamanyoObtendio.getId(), tamanyoAnadido.getId());
	
	}
	//Se comprueba que si se le pasa una id erronea da un error de validacion
	@Test
	public void notFindByIdTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(NombreTamanyo.M);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		Tamanyo tamanyoAnanido = tamanyoSer.addTamanyo(tamanyo);
		assertNull(tamanyoSer.findById(tamanyoAnanido.getId()+1));
	}
	//Se comprueba que una vez añadida el nuevo tamanyo esta se lista junto con las otras
	@Test
	public void findAllTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(NombreTamanyo.M);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		Tamanyo tamanyoAnanida = tamanyoSer.addTamanyo(tamanyo);
		List<Tamanyo> tamanyosObtenidas = tamanyoSer.findAll();
		Tamanyo tamanyoUltimo = tamanyosObtenidas.get(tamanyosObtenidas.size()-1);
		assertEquals(tamanyoUltimo.getId(), tamanyoAnanida.getId());
		
	}
	//Se comprueba que el ultimo elemento de la lista no debe ser igual que el elemento eliminado de la lista (y que seria el ultimo)
	@Test
	public void removeTamanyoTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(NombreTamanyo.S);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		Tamanyo tamanyoAnanido = tamanyoSer.addTamanyo(tamanyo);
		this.tamanyoSer.removeTamanyo(tamanyoAnanido.getId());
		assertFalse(tamanyoSer.findAll().contains(tamanyoAnanido));
	}
	//Se comprueba que los tamanyos que se introduce con un centro asociado se puede encontrar en base a ese centro
	@Test
	public void findTamanyosbyCentroTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(NombreTamanyo.S);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		Tamanyo tamanyoAnanido = tamanyoSer.addTamanyo(tamanyo);
		List<Tamanyo> tamanyoCentro = tamanyoSer.findByCentro(centroAnadido.getId());
		assertEquals(tamanyoAnanido.getId(), tamanyoCentro.get(0).getId());
	}
	//Se comprueba que los tamanyos posibles que ofrece el service son las mismas que un array creado a mano
	@Test
	public void listPosiblesTamanyosTest() {
		List<String> tamanyos = new ArrayList<String>();
		tamanyos.add("XXS");
		tamanyos.add("XS");
		tamanyos.add("S");
		tamanyos.add("M");
		tamanyos.add("L");
		tamanyos.add("XL");
		tamanyos.add("XXL");
		tamanyos.add("RELLENO");
		List<String> tamanyosPosibles = tamanyoSer.listPosibleTamanyo();
		assertEquals(tamanyos, tamanyosPosibles);
	}
	@Test
	public void removeTamanyobyCentroTest() {
		tamanyo = new Tamanyo();
		tamanyo.setCentro(centroAnadido);
		tamanyo.setCoste(20.0);
		tamanyo.setNombre(NombreTamanyo.S);
		tamanyo.setSiguienteFase(Fases.disenyos);
		tamanyo.setTiempo(30);
		Tamanyo tamanyoAnadido = tamanyoSer.addTamanyo(tamanyo);
		this.tamanyoSer.removeTamanyobyCentro(centroAnadido.getId());
		assertFalse(tamanyoSer.findAll().contains(tamanyoAnadido));
	}
	//Se comprueba que la falta de datos provoca una excepcion a la hora de añadir una forma a un centro
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
		persoValue.add("XXS");
		datos.put(centroKey, centroValue); datos.put(costeKey, costeValue);datos.put(duracionKey, duracionValue); datos.put(persoKey, persoValue);
		List<Tamanyo> formasAnadidas = tamanyoSer.addTamanyoCentro(datos);
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
		persoValue.add("XXS");
		datos.put(centroKey, centroValue); datos.put(costeKey, costeValue);datos.put(duracionKey, duracionValue); datos.put(persoKey, persoValue); 
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            public void execute() throws Throwable {
            	tamanyoSer.addTamanyoCentro(datos);
            }
        });
	}
	@AfterEach
	public void deleteData() {
		centroSer.delete(centroAnadido.getId());
	}
}
