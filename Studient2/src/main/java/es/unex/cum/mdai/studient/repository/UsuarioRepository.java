package es.unex.cum.mdai.studient.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import es.unex.cum.mdai.studient.model.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	public Optional<Usuario> findByCorreo(String correo);
	
}
