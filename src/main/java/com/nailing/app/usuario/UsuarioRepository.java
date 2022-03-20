package com.nailing.app.usuario;

import java.io.Serializable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("usuarioRepository")
public interface UsuarioRepository extends CrudRepository<Usuario, Serializable>{

    @Query("select user from Usuario user where user.usuario = ?1")
    Usuario findByUsername(String Username);
}