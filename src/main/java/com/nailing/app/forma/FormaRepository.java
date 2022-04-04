/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nailing.app.forma;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jaime
 */
public interface FormaRepository extends CrudRepository<Forma, Serializable>{
    //	encontrar las formas de un centro concreto por su ID
    @Query("SELECT forma from Forma forma where forma.centro.id = ?1")
    public List<Forma> findByCentro(Long id);
}
