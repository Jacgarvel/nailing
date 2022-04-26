/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.centro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

	@Column(name="dias_disponible")
	@NotEmpty
	private String diasDisponible;
	
	@Column(name = "suscripcion")
	@NotNull
	private Suscripcion suscripcion;
	
	@Column(name = "ultimasuscripcion")
	@NotNull
	private LocalDate ultimaSuscripcion;
	
	@Column(name = "creditosrestantes")
	@NotNull
	private Integer creditosrestantes;
	
	@Column(name = "pagado")
	@NotNull
	private Boolean pagado;
        
        @Column(name = "valoracionmedia")
	@NotNull
	private Double valoracionMedia;

	@Column(name = "valoraciontotal")
	@NotNull
	private Integer valoracionTotal;

        @Column(name = "numvaloraciones")
	@NotNull
	private Integer numValoraciones;

    public Centro(Long id, String nombre, String imagen, String provincia, LocalTime aperturaAM, LocalTime cierreAM, LocalTime aperturaPM, LocalTime cierrePM, String diasDisponible, Suscripcion suscripcion, LocalDate ultimaSuscripcion, Integer creditosrestantes, Boolean pagado, Double valoracionMedia, Integer valoracionTotal, Integer numValoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.provincia = provincia;
        this.aperturaAM = aperturaAM;
        this.cierreAM = cierreAM;
        this.aperturaPM = aperturaPM;
        this.cierrePM = cierrePM;
        this.diasDisponible = diasDisponible;
        this.suscripcion = suscripcion;
        this.ultimaSuscripcion = ultimaSuscripcion;
        this.creditosrestantes = creditosrestantes;
        this.pagado = pagado;
        this.valoracionMedia = valoracionMedia;
        this.valoracionTotal = valoracionTotal;
        this.numValoraciones = numValoraciones;
    }
    
        public Centro(@Size(max = 1000) @NotBlank String nombre, @Size(max = 1000) @NotBlank String imagen,
			@Size(max = 50) @NotBlank String provincia, LocalTime aperturaAM, LocalTime cierreAM, LocalTime aperturaPM,
			LocalTime cierrePM, @NotNull Suscripcion suscripcion, @NotNull LocalDate ultimaSuscripcion,
			@NotNull Integer creditosrestantes, @NotNull Boolean pagado, @NotEmpty String diasDisponible,
                        Double valoracionMedia, Integer valoracionTotal, Integer numValoraciones) {
		super();
		this.nombre = nombre;
		this.imagen = imagen;
		this.provincia = provincia;
		this.aperturaAM = aperturaAM;
		this.cierreAM = cierreAM;
		this.aperturaPM = aperturaPM;
		this.cierrePM = cierrePM;
		this.suscripcion = suscripcion;
		this.ultimaSuscripcion = ultimaSuscripcion;
		this.creditosrestantes = creditosrestantes;
		this.pagado = pagado;
		this.diasDisponible = diasDisponible;
                this.valoracionMedia = valoracionMedia;
                this.valoracionTotal = valoracionTotal;
                this.numValoraciones = numValoraciones;
	}
        
        public Centro() {
	}

	public Boolean getPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public LocalDate getUltimaSuscripcion() {
		return ultimaSuscripcion;
	}

	public void setUltimaSuscripcion(LocalDate ultimaSuscripcion) {
		this.ultimaSuscripcion = ultimaSuscripcion;
	}

	public Integer getCreditosrestantes() {
		return creditosrestantes;
	}

	public void setCreditosrestantes(Integer creditosrestantes) {
		this.creditosrestantes = creditosrestantes;
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

	public String getDiasDisponible(){
		return diasDisponible;
	}

        public Double getValoracionMedia() {
            return valoracionMedia;
        }

        public void setValoracionMedia(Double valoracionMedia) {
            this.valoracionMedia = valoracionMedia;
        }

        public Integer getValoracionTotal() {
            return valoracionTotal;
        }

        public void setValoracionTotal(Integer valoracionTotal) {
            this.valoracionTotal = valoracionTotal;
        }

        public Integer getNumValoraciones() {
            return numValoraciones;
        }

        public void setNumValoraciones(Integer numValoraciones) {
            this.numValoraciones = numValoraciones;
        }
	
        
        
	@JsonIgnore
	public List<DayOfWeek> getListadoDiasDisponible(){
		List<DayOfWeek> result = new ArrayList<>();
		for(String dia: diasDisponible.trim().split(","))
		{
			result.add(DayOfWeek.valueOf(dia));
		}
		return result;
	}
	
	public void setDiasDisponible(String diasDisponible) {
		this.diasDisponible = diasDisponible;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(aperturaAM, aperturaPM, cierreAM, cierrePM, diasDisponible, id, imagen, nombre, provincia, valoracionMedia, valoracionTotal, numValoraciones);
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
				&& Objects.equals(nombre, other.nombre) && Objects.equals(provincia, other.provincia)
				&& Objects.equals(diasDisponible, other.diasDisponible)&& Objects.equals(valoracionMedia, other.valoracionMedia)
                                && Objects.equals(valoracionTotal, other.valoracionTotal)&& Objects.equals(numValoraciones, other.numValoraciones);
	}

    @Override
    public String toString() {
        return "Centro [id=" + id + ", nombre=" + nombre + ", imagen=" + imagen + ", provincia=" + provincia + ", aperturaAM=" + aperturaAM + ", cierreAM=" + cierreAM + ", aperturaPM=" + aperturaPM + ", cierrePM=" + cierrePM + ", diasDisponible=" + diasDisponible + ", suscripcion=" + suscripcion + ", ultimaSuscripcion=" + ultimaSuscripcion + ", creditosrestantes=" + creditosrestantes + ", pagado=" + pagado + ", valoracionMedia=" + valoracionMedia + ", valoracionTotal=" + valoracionTotal + ", numValoraciones=" + numValoraciones + "]";
    }

}