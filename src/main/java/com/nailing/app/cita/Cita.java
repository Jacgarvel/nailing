/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.cita;

import java.time.LocalDateTime;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nailing.app.acabado.Acabado;
import com.nailing.app.base.Base;
import com.nailing.app.centro.Centro;
import com.nailing.app.decoracion.Decoracion;
import com.nailing.app.disenyo.Disenyo;
import com.nailing.app.forma.Forma;
import com.nailing.app.tamanyo.Tamanyo;
import com.nailing.app.tipo.Tipo;
import com.nailing.app.usuario.Usuario;

/**
 *
 * @author Jacgarvel
 */
@Entity
@Table(name = "cita")
@EntityListeners(AuditingEntityListener.class)
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "coste")
    @PositiveOrZero
    private Double coste;
    
    @NotNull
    @Column(name = "hora_inicio")
    private LocalDateTime horaInicio;
    
    @NotNull
    @Column(name = "hora_fin")
    private LocalDateTime horaFin;
    
    @ManyToOne
    @JoinColumn(name = "decoracion_id")
    private Decoracion decoracion;

    @ManyToOne
    @JoinColumn(name = "acabado_id")
    private Acabado acabado;

    @ManyToOne
    @JoinColumn(name="base_id")
    private Base base;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "disenyo_id")
    private Disenyo disenyo;
    
    @ManyToOne
    @JoinColumn(name = "tamanyo_id")
    private Tamanyo tamanyo;
    
    @ManyToOne
    @JoinColumn(name = "forma_id")
    private Forma forma;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "centro_id")
    private Centro centro;

    public Cita() {
    }

    public Cita(Double coste, LocalDateTime horaInicio, LocalDateTime horaFin, Decoracion decoracion, Acabado acabado, Base base, Tipo tipo, Disenyo disenyo, Tamanyo tamanyo, Forma forma, Usuario usuario, Centro centro) {
        this.coste = coste;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.decoracion = decoracion;
        this.acabado = acabado;
        this.base = base;
        this.tipo = tipo;
        this.disenyo = disenyo;
        this.tamanyo = tamanyo;
        this.forma = forma;
        this.usuario = usuario;
        this.centro = centro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public Decoracion getDecoracion() {
        return decoracion;
    }

    public void setDecoracion(Decoracion decoracion) {
        this.decoracion = decoracion;
    }

    public Acabado getAcabado() {
        return acabado;
    }

    public void setAcabado(Acabado acabado) {
        this.acabado = acabado;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Disenyo getDisenyo() {
        return disenyo;
    }

    public void setDisenyo(Disenyo disenyo) {
        this.disenyo = disenyo;
    }

    public Tamanyo getTamanyo() {
        return tamanyo;
    }

    public void setTamanyo(Tamanyo tamanyo) {
        this.tamanyo = tamanyo;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.coste);
        hash = 41 * hash + Objects.hashCode(this.horaInicio);
        hash = 41 * hash + Objects.hashCode(this.horaFin);
        hash = 41 * hash + Objects.hashCode(this.decoracion);
        hash = 41 * hash + Objects.hashCode(this.acabado);
        hash = 41 * hash + Objects.hashCode(this.base);
        hash = 41 * hash + Objects.hashCode(this.tipo);
        hash = 41 * hash + Objects.hashCode(this.disenyo);
        hash = 41 * hash + Objects.hashCode(this.tamanyo);
        hash = 41 * hash + Objects.hashCode(this.forma);
        hash = 41 * hash + Objects.hashCode(this.usuario);
        hash = 41 * hash + Objects.hashCode(this.centro);
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
        final Cita other = (Cita) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.coste, other.coste)) {
            return false;
        }
        if (!Objects.equals(this.horaInicio, other.horaInicio)) {
            return false;
        }
        if (!Objects.equals(this.horaFin, other.horaFin)) {
            return false;
        }
        if (!Objects.equals(this.decoracion, other.decoracion)) {
            return false;
        }
        if (!Objects.equals(this.acabado, other.acabado)) {
            return false;
        }
        if (!Objects.equals(this.base, other.base)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.disenyo, other.disenyo)) {
            return false;
        }
        if (!Objects.equals(this.tamanyo, other.tamanyo)) {
            return false;
        }
        if (!Objects.equals(this.forma, other.forma)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return Objects.equals(this.centro, other.centro);
    }

    @Override
    public String toString() {
        return "Cita{" + "id=" + id + ", coste=" + coste + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", decoracion=" + decoracion + ", acabado=" + acabado + ", base=" + base + ", tipo=" + tipo + ", disenyo=" + disenyo + ", tamanyo=" + tamanyo + ", forma=" + forma + ", usuario=" + usuario + ", centro=" + centro + '}';
    }

    
    
}