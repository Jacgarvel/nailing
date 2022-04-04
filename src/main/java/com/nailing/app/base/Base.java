package com.nailing.app.base;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.nailing.app.centro.Centro;
import com.nailing.app.components.Fases;
import javax.persistence.GenerationType;

@Entity
@Table(name = "base")
public class Base {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre")
	@NotNull
	private NombreBase nombre;

	@Positive
	@NotNull
	@Column(name = "tiempo")
	private Integer tiempo;

	@PositiveOrZero
	@NotNull
	@Column(name = "coste")
	private Double coste;

	@Column(name = "siguiente_fase")
	@NotNull
	private Fases siguienteFase;

	@ManyToOne
	@JoinColumn(name = "centro_id")
	private Centro centro;

	public Base() {
		super();
	}

	public Base(Long id, @Size(max = 50) @NotBlank NombreBase nombre, @Positive @NotNull Integer tiempo,
			@PositiveOrZero @NotNull Double coste, @NotBlank Fases fases, Centro centro) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tiempo = tiempo;
		this.coste = coste;
		this.siguienteFase = fases;
		this.centro = centro;
	}

    public Base(Integer tiempo, Double coste, Centro centro) {
        this.tiempo = tiempo;
        this.coste = coste;
        this.centro = centro;
    }
        

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NombreBase getNombre() {
		return nombre;
	}

	public void setNombre(NombreBase nombre) {
		this.nombre = nombre;
	}

	public Integer getTiempo() {
		return tiempo;
	}

	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}

	public Double getCoste() {
		return coste;
	}

	public void setCoste(Double coste) {
		this.coste = coste;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centros) {
		this.centro = centros;
	}

	public Fases getSiguienteFase() {
		return siguienteFase;
	}

	public void setSiguienteFase(Fases siguienteFase) {
		this.siguienteFase = siguienteFase;
	}

	@Override
	public int hashCode() {
		return Objects.hash(centro, coste, id, nombre, siguienteFase, tiempo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Base other = (Base) obj;
		return Objects.equals(centro, other.centro) && Objects.equals(coste, other.coste)
				&& Objects.equals(id, other.id) && nombre == other.nombre && siguienteFase == other.siguienteFase
				&& Objects.equals(tiempo, other.tiempo);
	}

	@Override
	public String toString() {
		return "Base [id=" + id + ", nombre=" + nombre + ", tiempo=" + tiempo + ", coste=" + coste + ", siguienteFase="
				+ siguienteFase + "]";
	}
}
