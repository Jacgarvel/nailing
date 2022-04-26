/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.valoracion;

import com.nailing.app.centro.Centro;
import com.nailing.app.usuario.Usuario;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author CANDELA
 */
@Entity
@Table(name = "valoracion")
@EntityListeners(AuditingEntityListener.class)
public class Valoracion{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull
    @Column(name = "valoracion")
    private Integer valoracionUsuario;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "centro_id")
    private Centro centro;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValoracionUsuario() {
        return valoracionUsuario;
    }

    public void setValoracionUsuario(Integer valoracionUsuario) {
        this.valoracionUsuario = valoracionUsuario;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Valoracion() {
    }

    public Valoracion(Integer valoracionUsuario, Centro centro, Usuario usuario) {
        this.valoracionUsuario = valoracionUsuario;
        this.centro = centro;
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.valoracionUsuario);
        hash = 59 * hash + Objects.hashCode(this.centro);
        hash = 59 * hash + Objects.hashCode(this.usuario);
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
        final Valoracion other = (Valoracion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.valoracionUsuario, other.valoracionUsuario)) {
            return false;
        }
        if (!Objects.equals(this.centro, other.centro)) {
            return false;
        }
        return Objects.equals(this.usuario, other.usuario);
    }

    @Override
    public String toString() {
        return "Valoracion[ id=" + id + ", valoracionUsuario=" + valoracionUsuario + ", centro=" + centro + ", usuario=" + usuario + "]";
    }
}
