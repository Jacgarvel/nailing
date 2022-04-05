package com.nailing.app.usuario;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nailing.app.centro.Centro;

@Entity
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario")
    @Size(max = 100)
    @NotBlank
    private String usuario;

    @Column(name = "contrasenya")
    @Size(max = 100)
    @NotBlank
    private String contrasenya;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "telefono")
    @NotBlank
    private String telefono;

    @Column(name = "rol")
    private Authorities rol;
    @OneToOne
    @JoinColumn(name = "centro")
    private Centro centro;
    
    public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

    public Usuario() {
        super();
    }
    
	public Usuario(String usuario, String contrasenya, String email, String telefono, Authorities rol,Centro centro) {
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.centro =centro;
    }

	public Usuario(String usuario, String contrasenya, String email, String telefono, Authorities rol) {
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }

    public Usuario(Long id, String usuario, String contrasenya, String email, String telefono, Authorities rol) {
        this.id = id;
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Authorities getRol() {
        return rol;
    }

    public void setRol(Authorities rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.usuario);
        hash = 59 * hash + Objects.hashCode(this.contrasenya);
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.telefono);
        hash = 59 * hash + Objects.hashCode(this.rol);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.contrasenya, other.contrasenya)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.rol, other.rol)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", usuario=" + usuario + ", email=" + email + ", telefono=" + telefono + ", rol=" + rol + '}';
    }
	

}
