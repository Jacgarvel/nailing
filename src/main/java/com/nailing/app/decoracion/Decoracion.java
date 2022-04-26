/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.decoracion;

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
import org.springframework.lang.NonNull;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "decoracion")
@EntityListeners(AuditingEntityListener.class)
public class Decoracion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre")
    @NonNull
    private NombreDecoracion nombre;
    
    @Column(name = "tiempo")
    @Positive
    private Integer tiempo;
    
    @Column(name = "coste")
    @Positive
    private Double coste;
    
    @Column(name = "siguiente_fase")
    @NonNull
    private Fases siguienteFase;
    
    @ManyToOne
    @JoinColumn(name = "centro_id")
    private Centro centro;

    public Long getId() {
        return id;
    }

    public NombreDecoracion getNombre() {
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

    public void setNombre(NombreDecoracion nombre) {
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

    public Decoracion(Long id, NombreDecoracion nombre, Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.coste = coste;
        this.siguienteFase = siguienteFase;
        this.centro = centro;
    }

    public Decoracion(Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
        this.tiempo = tiempo;
        this.coste = coste;
        this.siguienteFase = siguienteFase;
        this.centro = centro;
    }

    public Decoracion() {
        super();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.nombre);
        hash = 17 * hash + Objects.hashCode(this.tiempo);
        hash = 17 * hash + Objects.hashCode(this.coste);
        hash = 17 * hash + Objects.hashCode(this.siguienteFase);
        hash = 17 * hash + Objects.hashCode(this.centro);
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
        final Decoracion other = (Decoracion) obj;
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
        return "Decoracion{" + "id=" + id + ", nombre=" + nombre + ", tiempo=" + tiempo + ", coste=" + coste + ", siguienteFase=" + siguienteFase + ", centro=" + centro + '}';
    }

}
