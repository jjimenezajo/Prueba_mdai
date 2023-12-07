package es.unex.cum.mdai.studient.services;

import java.util.Optional;

import es.unex.cum.mdai.studient.model.Usuario;


public interface UsuarioService {

	public void insertUsers(String email, String password);
	
	public Optional<Usuario> findUsuarioById (Long usuarioId);

	public Iterable<Usuario> deleteUsuarioById(Long id);
	
	public Iterable<Usuario> updateUsuario(Usuario usuario);
	
	public Iterable<Usuario> updateNEmailAndContrasenaUsuario(Long id, String email, String contrasena);
	
	public Iterable <Usuario> findAllUsers();
}
