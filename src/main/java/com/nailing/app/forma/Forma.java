/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.forma;

import com.nailing.app.centro.Centro;
import com.nailing.app.components.Fases;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 * @author jaime
 */

@Entity
@Table(name = "forma")
public class Forma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    @NotNull
    private NombreForma nombre;

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

    public Forma() {
    }

    public Forma(Long id, NombreForma nombre, Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.coste = coste;
        this.siguienteFase = siguienteFase;
        this.centro = centro;
    }

    public Forma(Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
        this.tiempo = tiempo;
        this.coste = coste;
        this.siguienteFase = siguienteFase;
        this.centro = centro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NombreForma getNombre() {
        return nombre;
    }

    public void setNombre(NombreForma nombre) {
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

    public Fases getSiguienteFase() {
        return siguienteFase;
    }

    public void setSiguienteFase(Fases siguienteFase) {
        this.siguienteFase = siguienteFase;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.nombre);
        hash = 73 * hash + Objects.hashCode(this.tiempo);
        hash = 73 * hash + Objects.hashCode(this.coste);
        hash = 73 * hash + Objects.hashCode(this.siguienteFase);
        hash = 73 * hash + Objects.hashCode(this.centro);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Forma other = (Forma) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.nombre != other.nombre) {
            return false;
        }
        if (!Objects.equals(this.tiempo, other.tiempo)) {
            return false;
        }
        if (!Objects.equals(this.coste, other.coste)) {
            return false;
        }
        if (this.siguienteFase != other.siguienteFase) {
            return false;
        }
        return Objects.equals(this.centro, other.centro);
    }

    @Override
    public String toString() {
        return "Forma{" + "id=" + id + ", nombre=" + nombre + ", tiempo=" + tiempo + ", coste=" + coste + ", siguienteFase=" + siguienteFase + ", centro=" + centro + '}';
    }
        
    
}
