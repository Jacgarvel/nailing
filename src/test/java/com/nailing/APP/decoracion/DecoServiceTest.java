package com.nailing.APP.decoracion;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolationException;

import com.nailing.app.AppApplication;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;
import com.nailing.app.centro.Suscripcion;
import com.nailing.app.components.Fases;
import com.nailing.app.decoracion.Decoracion;
import com.nailing.app.decoracion.DecoracionService;
import com.nailing.app.decoracion.NombreDecoracion;
import com.nailing.app.disenyo.Disenyo;
import com.nailing.app.disenyo.DisenyoService;
import com.nailing.app.disenyo.NombreDisenyo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AppApplication.class)
public class DecoServiceTest {

    @Autowired
	private DecoracionService decoSer;
    @Autowired
    private DisenyoService disSer;
	@Autowired
	private CentroService centroSer;
	private Decoracion decoracion;
    private Disenyo disenyo;
	private Centro centro;
	private Centro centroAnadido;
	@BeforeEach
    public void setUp(){
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
        centro.setValoracionMedia(2.);
        centro.setValoracionTotal(2);
        centro.setNumValoraciones(1);
		centroAnadido = centroSer.addCentro(centro);
    }

    @Test
    public void shouldInsertDecoracion(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        Decoracion decoAnidada = decoSer.addDecoracion(decoracion);
        assertEquals(decoracion.getCentro(), decoAnidada.getCentro());
        assertEquals(decoracion.getCoste(), decoAnidada.getCoste());
        assertEquals(decoracion.getNombre(), decoAnidada.getNombre());
        assertEquals(decoracion.getSiguienteFase(), decoAnidada.getSiguienteFase());
        assertEquals(decoracion.getTiempo(), decoAnidada.getTiempo());
    }

    @Test
    public void shouldNotInsertDecoracion(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(-20.0);
        decoracion.setNombre(null);
        decoracion.setSiguienteFase(null);
        decoracion.setTiempo(-23);
        assertThrows(ConstraintViolationException.class, () -> decoSer.addDecoracion(decoracion));
    }
    
    @Test
    public void shouldFindDecoracionById(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        Decoracion decoAnidada = decoSer.addDecoracion(decoracion);
        Decoracion decoObtenida = decoSer.findById(decoAnidada.getId());
        assertEquals(decoObtenida.getCentro(), decoAnidada.getCentro());
        assertEquals(decoObtenida.getCoste(), decoAnidada.getCoste());
        assertEquals(decoObtenida.getNombre(), decoAnidada.getNombre());
        assertEquals(decoObtenida.getSiguienteFase(), decoAnidada.getSiguienteFase());
        assertEquals(decoObtenida.getTiempo(), decoAnidada.getTiempo());
    }

    @Test
    public void shouldNotFindDecoracionById(){
        assertThrows(NoSuchElementException.class, () -> decoSer.findById(9999999999999999L));
    }

    @Test
    public void shouldListAllDecoraciones(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        Decoracion decoAnidada = decoSer.addDecoracion(decoracion);
        List<Decoracion> listaDecos = StreamSupport.stream(decoSer.findAll().spliterator(), false).collect(Collectors.toList());
        assertTrue(listaDecos.contains(decoAnidada));
        assertTrue(listaDecos.size()>1);
    }

    @Test
    public void shouldDeleteDecoraciones(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        Decoracion decoAnidada = decoSer.addDecoracion(decoracion);
        Long id = decoAnidada.getId();
        List<Decoracion> listaDecos1 = StreamSupport.stream(decoSer.findAll().spliterator(), false).collect(Collectors.toList());
        decoSer.removeDecoracion(id);
        List<Decoracion> listaDecos2 = StreamSupport.stream(decoSer.findAll().spliterator(), false).collect(Collectors.toList());
        assertNotEquals(listaDecos1, listaDecos2);

    }

    @Test
    public void shouldNotDeleteDecoraciones(){
        assertThrows(NoSuchElementException.class, () -> decoSer.removeDecoracion(9999999999999999L));
    }

    @Test
    public void shouldDeleteDecoracionsByCentro(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        decoSer.addDecoracion(decoracion);
        List<Decoracion> listDecoCentros = decoSer.findByCentro(centroAnadido.getId());
        decoSer.removeDecoracionesbyCentro(centroAnadido.getId());
        List<Decoracion> listDecoCentros2 = decoSer.findByCentro(centroAnadido.getId());
        assertNotEquals(listDecoCentros2, listDecoCentros);
    }

    @Test
    public void shouldListAllPossibleDecoraciones(){
        List<String> posibles = decoSer.listPosibleDecoracion();
        assertTrue(posibles.contains(NombreDecoracion.BURBUJAS.toString()));
    }

    @Test
    public void shouldListByCentro(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        decoSer.addDecoracion(decoracion);
        List<Decoracion> listDecoCentros = decoSer.findByCentro(centroAnadido.getId());
        assertTrue(listDecoCentros.contains(decoracion));
    }

    @Test
    public void shouldNotListByCentro(){
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        decoSer.addDecoracion(decoracion);
        List<Decoracion> listDecoCentros = decoSer.findByCentro(1L);
        assertFalse(listDecoCentros.contains(decoracion));
    }

    @Test
    public void shouldListByCentroAndDisenyo(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.0);
        disenyo.setNombre(NombreDisenyo.LISAS);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setTiempo(30);
        Disenyo disenyoAnidado = disSer.addDisenyo(disenyo);
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.NO_DECORACION);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        decoSer.addDecoracion(decoracion);
        List<Decoracion> listDecoDisCentros = decoSer.findDecoracionByCentroDisenyo(disenyoAnidado.getId(),centroAnadido.getId());
        assertTrue(listDecoDisCentros.contains(decoracion));
    }

    @Test
    public void shouldListByCentroAndDisenyo2(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.0);
        disenyo.setNombre(NombreDisenyo.BABY_BOOMER);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setTiempo(30);
        Disenyo disenyoAnidado = disSer.addDisenyo(disenyo);
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.DIBUJO);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        decoSer.addDecoracion(decoracion);
        List<Decoracion> listDecoDisCentros = decoSer.findDecoracionByCentroDisenyo(disenyoAnidado.getId(),centroAnadido.getId());
        assertTrue(listDecoDisCentros.contains(decoracion));
    }
    @Test
    public void shouldNotListByCentroAndDisenyo2(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.0);
        disenyo.setNombre(NombreDisenyo.BABY_BOOMER);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setTiempo(30);
        Disenyo disenyoAnidado = disSer.addDisenyo(disenyo);
        decoracion = new Decoracion();
        decoracion.setCentro(centroAnadido);
        decoracion.setCoste(20.0);
        decoracion.setNombre(NombreDecoracion.BURBUJAS);
        decoracion.setSiguienteFase(Fases.tipos);
        decoracion.setTiempo(30);
        decoSer.addDecoracion(decoracion);
        List<Decoracion> listDecoDisCentros = decoSer.findDecoracionByCentroDisenyo(disenyoAnidado.getId(),centroAnadido.getId());
        assertFalse(listDecoDisCentros.contains(decoracion));
    }
    @Test
    public void shouldAddDecoByCentro(){
        List<String> tiempo = new ArrayList<>();
        tiempo.add("20");
        List<String> personalizaciones = new ArrayList<>();
        personalizaciones.add(NombreDecoracion.BURBUJAS.toString());
        List<String> coste = new ArrayList<>();
        coste.add("25");
        List<String> centroList = new ArrayList<>();
        centroList.add(centroAnadido.getId().toString());
        Map<String,List<String>> entrada = new HashMap<>();
        entrada.put("tiempo", tiempo);
        entrada.put("personalizaciones", personalizaciones);
        entrada.put("centro", centroList);
        entrada.put("coste", coste);
        List<Decoracion> list1 = decoSer.addDecoracionCentro(entrada);
        List<Decoracion> list2 = decoSer.findByCentro(centroAnadido.getId());
        assertTrue(list2.contains(list1.get(0)));
    }
    @Test
    public void shouldNotAddDecoByCentro(){
        List<String> tiempo = new ArrayList<>();
        tiempo.add("");
        List<String> personalizaciones = new ArrayList<>();
        personalizaciones.add(NombreDecoracion.BURBUJAS.toString());
        List<String> coste = new ArrayList<>();
        coste.add("-25");
        List<String> centroList = new ArrayList<>();
        centroList.add(null);
        Map<String,List<String>> entrada = new HashMap<>();
        entrada.put("tiempo", tiempo);
        entrada.put("personalizaciones", personalizaciones);
        entrada.put("centro", centroList);
        entrada.put("coste", coste);
        assertThrows(IllegalArgumentException.class, () -> decoSer.addDecoracionCentro(entrada));
    }
}
