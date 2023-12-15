package es.unex.cum.mdai.studient.services;

import java.util.Optional;

import es.unex.cum.mdai.studient.model.Usuario;


public interface UsuarioService {

	public void insertUsers(String email, String password);
	
	public Optional<Usuario> findUsuarioById (Long usuarioId);

	public boolean deleteUsuarioById(Long id);
	
	public Iterable<Usuario> updateUsuario(Usuario usuario);
	
	public boolean updateNEmailAndContrasenaUsuario(Long id, String email, String contrasena);
	
	public Iterable <Usuario> findAllUsers();
	
	public Optional<Usuario> findUsuarioByCorreo(String correo);
	
	public Iterable<Usuario> saveUsuario(Usuario usuario);
	
	public int countUser();
}
