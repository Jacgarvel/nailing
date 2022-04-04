/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.centro;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nailing.app.usuario.Usuario;

import java.time.LocalTime;

/**
 *
 * @author jaime
 */

@Entity
@Table(name = "centro")
@EntityListeners(AuditingEntityListener.class)
public class Centro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre")
	@Size(max = 1000)
	@NotBlank
	private String nombre;

	@Column(name = "imagen")
	@Size(max = 1000)
	@NotBlank
	private String imagen;
	@Column(name = "provincia")
	@Size(max = 50)
	@NotBlank
	private String provincia;

	@Column(name="apertura_am")
	private LocalTime aperturaAM;

	@Column(name="cierre_am")
	private LocalTime cierreAM;

	@Column(name="apertura_pm")
	private LocalTime aperturaPM;

	@Column(name="cierre_pm")
	private LocalTime cierrePM;
	/*
	@OneToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;*/

	@Column(name = "suscripcion")
	@NotNull
	private Suscripcion suscripcion;

	public Centro() {
	}

	public Centro(Long id, @Size(max = 1000) @NotBlank String nombre, @Size(max = 1000) @NotBlank String imagen,
			String provincia, LocalTime aperturaAM, LocalTime cierreAM, LocalTime aperturaPM, LocalTime cierrePM, Suscripcion suscripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.imagen = imagen;
		this.provincia = provincia;
		this.aperturaAM = aperturaAM;
		this.cierreAM = cierreAM;
		this.aperturaPM = aperturaPM;
		this.cierrePM = cierrePM;
		this.suscripcion = suscripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public LocalTime getAperturaAM() {
		return aperturaAM;
	}

	public void setAperturaAM(LocalTime aperturaAM) {
		this.aperturaAM = aperturaAM;
	}

	public LocalTime getCierreAM() {
		return cierreAM;
	}

	public void setCierreAM(LocalTime cierreAM) {
		this.cierreAM = cierreAM;
	}

	public LocalTime getAperturaPM() {
		return aperturaPM;
	}

	public void setAperturaPM(LocalTime aperturaPM) {
		this.aperturaPM = aperturaPM;
	}

	public LocalTime getCierrePM() {
		return cierrePM;
	}

	public void setCierrePM(LocalTime cierrePM) {
		this.cierrePM = cierrePM;
	}

	public Suscripcion getSuscripcion() {
		return suscripcion;
	}

	public void setSuscripcion(Suscripcion suscripcion) {
		this.suscripcion = suscripcion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aperturaAM, aperturaPM, cierreAM, cierrePM, id, imagen, nombre, provincia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Centro other = (Centro) obj;
		return Objects.equals(aperturaAM, other.aperturaAM) && Objects.equals(aperturaPM, other.aperturaPM)
				&& Objects.equals(cierreAM, other.cierreAM) && Objects.equals(cierrePM, other.cierrePM)
				&& Objects.equals(id, other.id) && Objects.equals(imagen, other.imagen)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(provincia, other.provincia);
	}

	@Override
	public String toString() {
		return "Centro [id=" + id + ", nombre=" + nombre + ", imagen=" + imagen + ", provincia=" + provincia
				+ ", aperturaAM=" + aperturaAM + ", cierreAM=" + cierreAM + ", aperturaPM=" + aperturaPM + ", cierrePM="
				+ cierrePM +", suscripcion="+ suscripcion +"]";
	}

}