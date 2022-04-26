/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.cita;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nailing.app.acabado.Acabado;
import com.nailing.app.acabado.AcabadoService;
import com.nailing.app.base.Base;
import com.nailing.app.base.BaseService;
import com.nailing.app.centro.Centro;
import com.nailing.app.centro.CentroService;
import com.nailing.app.decoracion.Decoracion;
import com.nailing.app.decoracion.DecoracionService;
import com.nailing.app.disenyo.Disenyo;
import com.nailing.app.disenyo.DisenyoService;
import com.nailing.app.forma.Forma;
import com.nailing.app.forma.FormaService;
import com.nailing.app.tamanyo.Tamanyo;
import com.nailing.app.tamanyo.TamanyoService;
import com.nailing.app.tipo.Tipo;
import com.nailing.app.tipo.TipoService;
import com.nailing.app.usuario.Usuario;
import com.nailing.app.usuario.UsuarioService;

/**
 *
 * @author Usuario
 */
@Service("citaService")
public class CitaService {

	@Autowired
	private CitaRepository citaRepository;

	@Autowired
	private DecoracionService decoracionService;

	@Autowired
	private DisenyoService disenyoService;

	@Autowired
	private BaseService baseService;

	@Autowired
	private AcabadoService acabadoService;

	@Autowired
	private CentroService centroService;

	@Autowired
	private TamanyoService tamanyoService;

	@Autowired
	private TipoService tipoService;

	@Autowired
	private FormaService formaService;

	@Autowired
	private UsuarioService usuarioService;

	public Cita addCita(Map<String, String> ids) {
		Decoracion decoracion = null;
		Disenyo disenyo = null;
		Base base = null;
		Acabado acabado = null;
		Tamanyo tamanyo = null;
		Forma forma = null;
		Tipo tipo = null;
		Usuario usuario;
		Centro centro;
		Double precio;
		Integer tiempo;
		LocalDateTime horaInicio;
		LocalDateTime horaFin;
		
		final String usuarioKey = "usuario";
		final String centroKey = "centro";
		final String tiempoKey = "tiempo";
		final String precioKey = "precio";
		final String fechaKey = "fecha";
		if (ids.get(usuarioKey) == null || ids.get(centroKey) == null || ids.get(precioKey) == null
				|| ids.get(tiempoKey) == null || ids.get(fechaKey) == null) {
			throw new IllegalArgumentException(
					"usuario: " + ids.get(usuarioKey) + "; centro: " + ids.get(centroKey) + "; precio: "
							+ ids.get(precioKey) + "; fecha: " + ids.get(fechaKey) + "; duracion: " + ids.get(tiempoKey));
		}

		if (ids.get("decoracion") != null) {
			decoracion = decoracionService.findById(Long.parseLong(ids.get("decoracion")));
		}
		if (ids.get("disenyo") != null) {
			disenyo = disenyoService.findById(Long.parseLong(ids.get("disenyo")));
		}
		if (ids.get("base") != null) {
			base = baseService.findById(Long.parseLong(ids.get("base")));
		}
		if (ids.get("acabado") != null) {
			acabado = acabadoService.findById(Long.parseLong(ids.get("acabado")));
		}
		if (ids.get("tamanyo") != null) {
			tamanyo = tamanyoService.findById(Long.parseLong(ids.get("tamanyo")));
		}
		if (ids.get("forma") != null) {
			forma = formaService.findById(Long.parseLong(ids.get("forma")));
		}
		if (ids.get("tipo") != null) {
			tipo = tipoService.findById(Long.parseLong(ids.get("tipo")));
		}

		usuario = usuarioService.findById(Long.parseLong(ids.get(usuarioKey))).get();
		centro = centroService.findById(Long.parseLong(ids.get(centroKey))).get();
		precio = Double.valueOf(ids.get(precioKey));

		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		horaInicio = LocalDateTime.parse(ids.get(fechaKey), dt);

		tiempo = Integer.valueOf(ids.get(tiempoKey));
		horaFin = horaInicio.plusMinutes(tiempo);

		Cita cita = new Cita(precio, horaInicio, horaFin, decoracion, acabado, base, tipo, disenyo, tamanyo, forma,
				usuario, centro);
		Centro citacentro = cita.getCentro();
		citacentro.setCreditosrestantes(citacentro.getCreditosrestantes()-1);
		return citaRepository.save(cita);
	}
	

	public List<String> findDisponibles(String fecha, Integer tiempo, Long centroId) {
		LocalDateTime fin;
		Boolean apto;

		Centro centro = centroService.findById(centroId).get();
		
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
		LocalDateTime inicio = LocalDateTime.parse(fecha, dt);

		List<Cita> citas = this.findCitasPendientes(centroId, inicio);
		List<Integer> tramos = Arrays.asList(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55);
		List<String> libres = new ArrayList<>();

		

		for (Integer tramo : tramos) {
			fin = inicio.plusMinutes(Long.valueOf(tramo) +  tiempo);
			apto = true;

//				comprobar si la cita es seleccionada en el descanso del mediodia del centro si lo tiene
			boolean checkMediodia = centro.getCierreAM().isBefore(fin.toLocalTime())
					&& centro.getAperturaPM().isAfter(inicio.toLocalTime());
//				comprobar si la cita acaba o empieza despues de la hora de cierre del centro
			boolean checkCierre = centro.getCierrePM().isBefore(fin.toLocalTime());
//				comprobar si cita empieza antes de la hora de apertura del centro
			boolean checkApertura = centro.getAperturaAM().isAfter(inicio.toLocalTime());
//				comprobar si la cita es para un día disponible para el centro
			boolean checkDia = !centro.getListadoDiasDisponible().contains(inicio.getDayOfWeek());
			
			

//			si se cumple alguna condición la cita no puede empezar ese día a esa hora y minutos
			if (checkMediodia || checkCierre || checkApertura || checkDia) {
				apto = false;
				continue;
			}

			for (Cita cita : citas) {
//				comprobar si la cita ocupa el tiempo de alguna otra cita
				Boolean checkCitas = (cita.getHoraInicio().isBefore(fin) && cita.getHoraFin().isAfter(inicio));

//				si se cumple alguna condición la cita no puede empezar a esa hora y minutos
				if (Boolean.TRUE.equals(checkCitas)) {
					apto = false;
					
				}
			}

			if (Boolean.TRUE.equals(apto)) {
				String tramoString = tramo.toString();

				if (tramo < 10) {
					tramoString = "0" + tramoString;
				}

				libres.add(tramoString);
			}
		}

		return libres;
	}

	public Cita findById(Long id) {
		return citaRepository.findById(id).get();
	}

	public List<Cita> findByUsuario(Long id) {
		return citaRepository.findByUsuario(id);
	}

	public Iterable<Cita> findAll() {
		return citaRepository.findAll();
	}

	public List<Cita> findCitasPendientes(Long centroId, LocalDateTime fecha) {
		return citaRepository.findCitasByCentro(centroId).stream()
				.filter(cita -> cita.getHoraInicio().toLocalDate().equals(fecha.toLocalDate()))
				.collect(Collectors.toList());

	}

	public void removeCita(Long id) {
		Cita cita = findById(id);
		if (cita != null) {
			citaRepository.delete(cita);
		}
	}

	public void removeCitaByUser(Long userId, Long citaId) {
		List<Cita> citas = findByUsuario(userId);
		for (Cita c : citas) {
			if (c.getId().equals(citaId)) {
				citaRepository.delete(c);
			}
		}
	}
}
