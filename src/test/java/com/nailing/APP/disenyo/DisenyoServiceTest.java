package com.nailing.APP.disenyo;

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

import com.nailing.app.AppApplication;
import com.nailing.app.base.Base;
import com.nailing.app.base.BaseService;
import com.nailing.app.base.NombreBase;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;
import com.nailing.app.centro.Suscripcion;
import com.nailing.app.components.Fases;
import com.nailing.app.disenyo.Disenyo;
import com.nailing.app.disenyo.DisenyoService;
import com.nailing.app.disenyo.NombreDisenyo;
import com.nailing.app.tamanyo.NombreTamanyo;
import com.nailing.app.tamanyo.Tamanyo;
import com.nailing.app.tamanyo.TamanyoService;

import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AppApplication.class)
public class DisenyoServiceTest {
    @Autowired
    private DisenyoService disSer;
    @Autowired
    private CentroService centroSer;
    @Autowired
    private TamanyoService tamanyoService;
    @Autowired
    private BaseService baseService;
    private Disenyo disenyo;
    private Tamanyo tamanyo;
    private Base base;
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
    public void shouldInsertDisenyo(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        Disenyo disAnidada = disSer.addDisenyo(disenyo);
        assertEquals(disenyo.getCentro(), disAnidada.getCentro());
        assertEquals(disenyo.getNombre(), disAnidada.getNombre());
        assertEquals(disenyo.getCoste(), disAnidada.getCoste());
        assertEquals(disenyo.getTiempo(), disAnidada.getTiempo());
        assertEquals(disenyo.getSiguienteFase(), disAnidada.getSiguienteFase());
    }

    @Test
    public void shouldNotInsertDisenyo(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(-20.);
        disenyo.setTiempo(-15);
        disenyo.setSiguienteFase(null);
        disenyo.setNombre(null);
        assertThrows(ConstraintViolationException.class, () -> disSer.addDisenyo(disenyo));
    }

    @Test
    public void shouldFindDisenyoById(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        Disenyo disAnidada = disSer.addDisenyo(disenyo);
        Disenyo disObtenida = disSer.findById(disAnidada.getId());
        assertEquals(disObtenida.getCentro(), disAnidada.getCentro());
        assertEquals(disObtenida.getNombre(), disAnidada.getNombre());
        assertEquals(disObtenida.getCoste(), disAnidada.getCoste());
        assertEquals(disObtenida.getTiempo(), disAnidada.getTiempo());
        assertEquals(disObtenida.getSiguienteFase(), disAnidada.getSiguienteFase());
    }

    @Test
    public void shouldNotFindDisenyoById(){
        assertThrows(NoSuchElementException.class, () -> disSer.findById(9999999999999999L));
    }

    @Test
    public void shouldListAllDisenyos(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        Disenyo disAnidada = disSer.addDisenyo(disenyo);
        List<Disenyo> listaDis = StreamSupport.stream(disSer.findAll().spliterator(), false).collect(Collectors.toList());
        assertTrue(listaDis.contains(disAnidada));
        assertTrue(listaDis.size()>1);
    }
    
    @Test
    public void shouldDeleteDisenyo(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        Disenyo disAnidada = disSer.addDisenyo(disenyo);
        Long id = disAnidada.getId();
        List<Disenyo> listaDis1 = StreamSupport.stream(disSer.findAll().spliterator(), false).collect(Collectors.toList());
        disSer.removeDisenyo(id);
        List<Disenyo> listaDis2 = StreamSupport.stream(disSer.findAll().spliterator(), false).collect(Collectors.toList());
        assertNotEquals(listaDis1, listaDis2);
    }

    @Test
    public void shouldNotDeleteDisenyo(){
        assertThrows(NoSuchElementException.class, () -> disSer.removeDisenyo(9999999999999999L));
    }

    @Test
    public void shouldDeleteDisenyoByCentro(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        disSer.addDisenyo(disenyo);
        List<Disenyo> listaDisCen1 = StreamSupport.stream(disSer.findAll().spliterator(), false).collect(Collectors.toList());
        disSer.removeDisenyosbyCentro(centroAnadido.getId());
        List<Disenyo> listaDisCen2 = StreamSupport.stream(disSer.findAll().spliterator(), false).collect(Collectors.toList());
        assertNotEquals(listaDisCen1, listaDisCen2);
    }

    @Test
    public void shouldListAllPossibleDisenyos(){
        List<String> posibles = disSer.listPosibleDisenyo();
        assertTrue(posibles.contains(NombreDisenyo.LISAS.toString()));
    }

    @Test
    public void shouldListByCentro(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        disSer.addDisenyo(disenyo);
        List<Disenyo> listDisCentros = disSer.findByCentro(centroAnadido.getId());
        assertTrue(listDisCentros.contains(disenyo));
    }

    @Test
    public void shouldNotListByCentro(){
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        disSer.addDisenyo(disenyo);
        List<Disenyo> listDisCentros = disSer.findByCentro(1L);
        assertFalse(listDisCentros.contains(disenyo));
    }

    @Test
    public void shouldListByCentroAndTamanyo(){
        tamanyo = new Tamanyo();
        tamanyo.setCentro(centroAnadido);
        tamanyo.setCoste(20.);
        tamanyo.setNombre(NombreTamanyo.XL);
        tamanyo.setSiguienteFase(Fases.disenyos);
        tamanyo.setTiempo(15);
        Tamanyo tamanyoAnidado = tamanyoService.addTamanyo(tamanyo);
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        disSer.addDisenyo(disenyo);
        List<Disenyo> listDisTamCentro = disSer.findDisenyosByCentroTamanyo(tamanyoAnidado.getId(), centroAnadido.getId());
        assertTrue(listDisTamCentro.contains(disenyo));
    }

    @Test
    public void shouldListByCentroAndBase(){
        base = new Base();
        base.setCentro(centroAnadido);
        base.setCoste(20.);
        base.setNombre(NombreBase.SEMIPERMANENTE);
        base.setSiguienteFase(Fases.formas);
        base.setTiempo(15);
        Base baseAnidada = baseService.addBase(base);
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        disSer.addDisenyo(disenyo);
        List<Disenyo> listDisTamCentro = disSer.findDisenyosByCentroBase(baseAnidada.getId(), centroAnadido.getId());
        assertTrue(listDisTamCentro.contains(disenyo));
    }

    @Test
    public void shouldNotListByCentroAndBase(){
        base = new Base();
        base.setCentro(centroAnadido);
        base.setCoste(20.);
        base.setNombre(NombreBase.ACRYGEL);
        base.setSiguienteFase(Fases.formas);
        base.setTiempo(15);
        Base baseAnidada = baseService.addBase(base);
        disenyo = new Disenyo();
        disenyo.setCentro(centroAnadido);
        disenyo.setCoste(20.);
        disenyo.setTiempo(15);
        disenyo.setSiguienteFase(Fases.decoraciones);
        disenyo.setNombre(NombreDisenyo.LISAS);
        disSer.addDisenyo(disenyo);
        List<Disenyo> listDisTamCentro = disSer.findDisenyosByCentroBase(baseAnidada.getId(), centroAnadido.getId());
        assertFalse(listDisTamCentro.contains(disenyo));
    }

    @Test
    public void shouldAddDisByCentro(){
        List<String> tiempo = new ArrayList<>();
        tiempo.add("20");
        List<String> personalizaciones = new ArrayList<>();
        personalizaciones.add(NombreDisenyo.LISAS.toString());
        List<String> coste = new ArrayList<>();
        coste.add("25");
        List<String> centroList = new ArrayList<>();
        centroList.add(centroAnadido.getId().toString());
        Map<String,List<String>> entrada = new HashMap<>();
        entrada.put("tiempo", tiempo);
        entrada.put("personalizaciones", personalizaciones);
        entrada.put("centro", centroList);
        entrada.put("coste", coste);
        List<Disenyo> list1 = disSer.addDisenyoCentro(entrada);
        List<Disenyo> list2 = disSer.findByCentro(centroAnadido.getId());
        assertTrue(list2.contains(list1.get(0)));
    }

    @Test
    public void shouldNotAddDisByCentro(){
        List<String> tiempo = new ArrayList<>();
        tiempo.add("-20");
        List<String> personalizaciones = new ArrayList<>();
        personalizaciones.add("");
        List<String> coste = new ArrayList<>();
        coste.add("-25");
        List<String> centroList = new ArrayList<>();
        centroList.add(null);
        Map<String,List<String>> entrada = new HashMap<>();
        entrada.put("tiempo", tiempo);
        entrada.put("personalizaciones", personalizaciones);
        entrada.put("centro", centroList);
        entrada.put("coste", coste);
        assertThrows(IllegalArgumentException.class, () -> disSer.addDisenyoCentro(entrada));
    }
}
