package es.unex.cum.mdai.studient.services;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {

		System.out.println("\t Arrancado automático");
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public void insertUsers(String email, String password) {

		Usuario u = new Usuario(email, password);

		Carpeta alta = new Carpeta("Prioridad Alta", false, u);
		Carpeta baja = new Carpeta("Prioridad Baja", false, u);
		Carpeta completadas = new Carpeta("Tareas completadas", false, u);
		Carpeta nulas = new Carpeta("Tareas sin realizar", false, u);

		// colocamos las distintas carpetas en la lista de carpetas del usuario
		u.addCarpeta(alta);
		u.addCarpeta(baja);
		u.addCarpeta(completadas);
		u.addCarpeta(nulas);

		usuarioRepository.save(u);
	}

	@Override
	public Optional<Usuario> findUsuarioById(Long usuarioId) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(usuarioId);
	}

	@Override
	public Optional<Usuario> findUsuarioByCorreo(String correo) {
		return usuarioRepository.findByCorreo(correo);
	}

	@Override
	public boolean deleteUsuarioById(Long id) {
		boolean successful = false;

		int before = countUser();
		usuarioRepository.deleteById(id);
		int after = countUser();

		// Tras la eliminación debe haber un elemento menos en la BD
		if (before - 1 == after)
			successful = true;

		return successful;
	}

	@Override
	public Iterable<Usuario> updateUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
		return findAllUsers();
	}

	@Override
	public boolean updateNEmailAndContrasenaUsuario(Long id, String email, String contrasena) {

		boolean successful = false;

		Optional<Usuario> optional_updated = findUsuarioById(id);
		if (!optional_updated.isEmpty()) {
			Usuario usuario = optional_updated.get();
			usuario.setCorreo(email);
			usuario.setContrasena(contrasena);
			usuarioRepository.save(usuario);

			Usuario aux = findUsuarioById(id).get();
			if (aux.getCorreo().equals(email) && aux.getContrasena().equals(contrasena))
				successful = true;

		}

		return successful;
	}

	@Override
	public Iterable<Usuario> findAllUsers() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public int countUser() {
		Iterable<Usuario> it_c = findAllUsers();
		int count = 0;

		for (Usuario elemento : it_c) {
			count++;
		}

		return count;
	}

}
