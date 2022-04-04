/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nailing.app.decoracion;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Usuario
 */
public interface DecoracionRepository extends CrudRepository<Decoracion, Serializable>{
    
    @Query("SELECT decoracion from Decoracion decoracion where decoracion.centro.id = ?1")
    public List<Decoracion> findByCentro(Long id);
    
}
