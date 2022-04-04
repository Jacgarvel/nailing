/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.acabado;

import com.nailing.app.centro.Centro;
import com.nailing.app.components.Fases;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name="acabado")
@EntityListeners(AuditingEntityListener.class)
public class Acabado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre")
    private NombreAcabado nombre;
    
    @Column(name = "tiempo")
    @Positive
    private Integer tiempo;
    
    @Column(name = "coste")
    @Positive
    private Double coste;
    
    @Column(name = "siguiente_fase")
    private Fases siguienteFase;
    
    @ManyToOne
    @JoinColumn(name = "centro_id")
    private Centro centro;

    public Long getId() {
        return id;
    }

    public NombreAcabado getNombre() {
        return nombre;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public Double getCoste() {
        return coste;
    }

    public Fases getSiguienteFase() {
        return siguienteFase;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(NombreAcabado nombre) {
        this.nombre = nombre;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public void setSiguienteFase(Fases siguienteFase) {
        this.siguienteFase = siguienteFase;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Acabado(Long id, NombreAcabado nombre, Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.coste = coste;
        this.siguienteFase = siguienteFase;
        this.centro = centro;
    }

    public Acabado(Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
        this.tiempo = tiempo;
        this.coste = coste;
        this.siguienteFase = siguienteFase;
        this.centro = centro;
    }
    
    public Acabado() {
        super();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.tiempo);
        hash = 29 * hash + Objects.hashCode(this.coste);
        hash = 29 * hash + Objects.hashCode(this.siguienteFase);
        hash = 29 * hash + Objects.hashCode(this.centro);
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
        final Acabado other = (Acabado) obj;
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
        return "Acabado{" + "id=" + id + ", nombre=" + nombre + ", tiempo=" + tiempo + ", coste=" + coste + ", siguienteFase=" + siguienteFase + ", centro=" + centro + '}';
    }
 
}
