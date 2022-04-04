/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nailing.app.tipo;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author CANDELA
 */
@Repository
public interface TipoRepository extends CrudRepository<Tipo, Serializable>{
    @Query("SELECT tipo from Tipo tipo where tipo.centro.id = ?1")
    public List<Tipo> findByCentro(Long id);
}
