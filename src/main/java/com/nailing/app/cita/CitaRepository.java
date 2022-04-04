/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nailing.app.cita;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/**
 *
 * @author Usuario
 */
public interface CitaRepository extends CrudRepository<Cita, Serializable>{
    
    @Query("SELECT cita from Cita cita where cita.usuario.id = ?1")
    public List<Cita> findByUsuario(Long id);
    
    @Query("SELECT cita from Cita cita where cita.centro.id = :id")
    public List<Cita> findCitasByCentro(Long id);
    
}
