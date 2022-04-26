package com.nailing.APP.usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nailing.app.AppApplication;
import com.nailing.app.usuario.Authorities;
import com.nailing.app.usuario.Usuario;
import com.nailing.app.usuario.UsuarioService;


@SpringBootTest(classes = AppApplication.class)
public class UsuarioServiceTest {

	@Autowired
	UsuarioService usuarioService;
	
	private static Usuario usuario;
	
	@BeforeAll
	public static void setUp(){
		usuario = new Usuario();
		usuario.setUsuario("TestingUser");
		usuario.setContrasenya("TestingUser");
		usuario.setEmail("testingmail@gmail.com");
		usuario.setTelefono("123456789");
		usuario.setRol(Authorities.ADMIN);
		usuario.setCentro(null);
	}
	
	@Test
	public void saveTest() {
		Usuario usuarioAnadido = usuarioService.save(usuario);
		
		assertEquals(usuarioAnadido.getUsuario(), usuario.getUsuario());
		assertEquals(usuarioAnadido.getContrasenya(), usuario.getContrasenya());
		assertEquals(usuarioAnadido.getEmail(), usuario.getEmail());
		assertEquals(usuarioAnadido.getTelefono(), usuario.getTelefono());
		assertEquals(usuarioAnadido.getRol(), usuario.getRol());
		assertEquals(usuarioAnadido.getCentro(), usuario.getCentro());
	}
	@Test
	public void saveNegativeTest() {
		Usuario usuarioError = new Usuario();
		usuarioError.setUsuario("TestingUser");
		usuarioError.setContrasenya("TestingUser");
		usuarioError.setEmail("none");
		usuarioError.setTelefono("123456789");
		usuarioError.setRol(Authorities.ADMIN);
		usuarioError.setCentro(null);
		assertThrows(ConstraintViolationException.class, () -> {
			usuarioService.save(usuarioError);
		});
	}
	
	@Test
	public void findByIdTest() {
		Usuario usuarioAnadido = usuarioService.save(usuario);
		Usuario usuarioObtenido = usuarioService.findById(usuarioAnadido.getId()).get();
		assertEquals(usuarioObtenido, usuarioAnadido);
	}
	@Test
	public void findByIdNegativeTest() {
		Optional<Usuario> usuarioObtenido = usuarioService.findById(100L);
		assertFalse(usuarioObtenido.isPresent());
	}
	
	@Test
	public void findByUserNameTest() {
		Usuario usuarioAnadido = usuarioService.save(usuario);
		Usuario usuarioObtenido = usuarioService.findByUsuario(usuarioAnadido.getUsuario());
		assertEquals(usuarioObtenido, usuarioAnadido);
	}
	@Test
	public void findByUserNameNegativeTest() {
		Usuario usuarioObtenido = usuarioService.findByUsuario("NotAnUserName");
		assertNull(usuarioObtenido);
	}
	
	@Test
	public void findAllTest() {
		Usuario usuarioAnadido = usuarioService.save(usuario);
		List<Usuario> usuarios = usuarioService.findAll();
		assertTrue(usuarios.size() > 1);
		assertTrue(usuarios.contains(usuarioAnadido));
	}
	
	@Test
	public void removeUsuarioTest() {
		Usuario usuarioAnadido = usuarioService.save(usuario);
		usuarioService.removeUsuario(usuarioAnadido.getId());
		usuario.setId(null);
		List<Usuario> usuarios = usuarioService.findAll();
		assertFalse(usuarios.contains(usuarioAnadido));
	}
}
