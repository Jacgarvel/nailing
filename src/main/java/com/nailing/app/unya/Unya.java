/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nailing.app.unya;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Jacgarvel
 */
@Entity
@Table(name = "unya")
@EntityListeners(AuditingEntityListener.class)
public class Unya {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "tiempo")
    @NotNull
    private Double tiempo;
    
    @Column(name = "coste_total")
    @NotNull
    private Double costeTotal;

    public Long getId() {
        return id;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public Double getCosteTotal() {
        return costeTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }

    public void setCosteTotal(Double costeTotal) {
        this.costeTotal = costeTotal;
    }
    
    
}
