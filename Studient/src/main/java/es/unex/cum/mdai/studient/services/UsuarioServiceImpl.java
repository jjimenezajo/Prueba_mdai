package es.unex.cum.mdai.studient.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.CarpetaRepository;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService, CommandLineRunner{
	
	private final UsuarioRepository usuarioRepository;
	

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		
		System.out.println("\t Arrancado automático");
		this.usuarioRepository = usuarioRepository;
	}
	


	@Override
	public void run(String... args) throws Exception {
		System.out.println("\t Usando CommandLineRunner");

		insertUsers("pepe@gmail.com", "pepe");
		insertUsers("alvaro@gmail.com", "alvaro");
		
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
	
}
