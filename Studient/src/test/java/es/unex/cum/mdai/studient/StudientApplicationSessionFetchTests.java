package es.unex.cum.mdai.studient;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Sesion;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.CarpetaRepository;
import es.unex.cum.mdai.studient.repository.SesionRepository;
import es.unex.cum.mdai.studient.repository.TareaRepository;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;

@SpringBootTest
public class StudientApplicationSessionFetchTests {
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CarpetaRepository carpetaRepository;

	@Autowired
	TareaRepository tareaRepository;

	@Autowired
	SesionRepository sesionRepository;

	@Test
	void contextLoads() {

		Iterable<Carpeta> it_c;
		Optional<Usuario> it_u;
		Iterable<Sesion> it_s;

		// REGISTRO
		System.out.println();
		System.out.println("REGISTRO: PERSISTENCIA EN CASCADA");

		// Crear usuario
		Usuario usuariologin = new Usuario("pepe@gmail.com", "passwd");
		Usuario u1 = new Usuario("pepe@gmail.com", "psw");

		// intentamos insertar el usuario junto a sus carpetas

		// INICIAR SESIÓN
		System.out.println();
		System.out.println("INICIO DE SESIÓN");

		// Aqui intento obtener un usuario por su id y luego obtener las carpetas
		// asociadas a el por su id

		System.out.println(
				"INFO: simplemente se crea la sesión, cuando se cierre la aplicación web o no se completen las tareas se GUARDARÁ...");
		Sesion sesionlogin = new Sesion(usuariologin);
		usuariologin.addSesion(sesionlogin);

		// GUARDADO SESIÓN AL TERMINAR

		System.out.println("Se hizo la sesión con éxito y se apostó...");

		System.out.println(usuariologin.toString());
		System.out.println(sesionlogin.toString());

		usuarioRepository.save(usuariologin);

		// MOSTRAR SESIONES
		usuariologin = usuarioRepository.findById(1L).get();
		usuariologin.setSesiones(sesionRepository.findByUsuarioId(usuariologin.getId()));

		for (Sesion s : usuariologin.getSesiones()) {
			System.out.println(s.toString());
		}
	}
}
