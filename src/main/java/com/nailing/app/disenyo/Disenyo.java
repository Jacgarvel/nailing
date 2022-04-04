/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.disenyo;
import com.nailing.app.centro.Centro;
import com.nailing.app.components.Fases;
import java.util.Objects;
import javax.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
/**
 *
 * @author CANDELA
 */
@Entity
@Table(name = "disenyo")
@EntityListeners(AuditingEntityListener.class)
public class Disenyo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @Column(name = "nombre")
    @NotNull
    private NombreDisenyo nombre;

    @Positive
    @NotNull
    @Column(name = "tiempo")
    private Integer tiempo;
    
    @PositiveOrZero
    @NotNull
    @Column(name = "coste")
    private Double coste;

    @NotNull
    @Column(name = "siguiente_fase")
    private Fases siguienteFase;

    @ManyToOne
    @JoinColumn(name = "centro_id")
    private Centro centro;

    public Disenyo() {
    }

    public Disenyo(Long id, NombreDisenyo nombre, Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.coste = coste;
        this.siguienteFase = siguienteFase;
        this.centro = centro;
    }

    public Disenyo(Integer tiempo, Double coste, Fases siguienteFase, Centro centro) {
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

    public NombreDisenyo getNombre() {
        return nombre;
    }

    public void setNombre(NombreDisenyo nombre) {
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
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.tiempo);
        hash = 59 * hash + Objects.hashCode(this.coste);
        hash = 59 * hash + Objects.hashCode(this.siguienteFase);
        hash = 59 * hash + Objects.hashCode(this.centro);
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
        final Disenyo other = (Disenyo) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
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
        return "Disenyo{" + "id=" + id + ", nombre=" + nombre + ", tiempo=" + tiempo + ", coste=" + coste + ", siguienteFase=" + siguienteFase + ", centro=" + centro + '}';
    }

   
    
}
