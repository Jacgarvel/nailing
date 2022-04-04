/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nailing.app.disenyo;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CANDELA
 */
@Repository
public interface DisenyoRepository extends CrudRepository<Disenyo, Serializable>{
    @Query("SELECT disenyo from Disenyo disenyo where disenyo.centro.id = ?1")
	public List<Disenyo> findByCentro(Long id);
}
