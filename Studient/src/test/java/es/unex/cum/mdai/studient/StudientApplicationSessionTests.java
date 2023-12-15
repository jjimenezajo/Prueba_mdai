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
public class StudientApplicationSessionTests {

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
		Usuario u = new Usuario("pepe@gmail.com", "passwd");
		Usuario u1 = new Usuario("pepe@gmail.com", "psw");

		// Crear carpetas
		Carpeta alta = new Carpeta("Prioridad Alta", false, u);
		Carpeta baja = new Carpeta("Prioridad Baja", false, u);
		Carpeta completadas = new Carpeta("Tareas completadas", false, u);

		// colocamos las distintas carpetas en la lista de carpetas del usuario
		u.addCarpeta(alta);
		u.addCarpeta(baja);
		u.addCarpeta(completadas);

		// intentamos insertar el usuario junto a sus carpetas
		it_u = usuarioRepository.findById(u.getId());
		if (it_u.isEmpty())
			usuarioRepository.save(u);
		else
			System.out.println("ERROR: usuario repetido");

		// intentamos insertar un usuario repetido

		it_u = usuarioRepository.findById(u.getId());
		if (it_u.isEmpty())
			usuarioRepository.save(u);
		else
			System.out.println("ERROR: usuario " + u1.getCorreo() + " ya está registrado en el sistema");

		// INICIAR SESIÓN
		System.out.println();
		System.out.println("INICIO DE SESIÓN");

		// Aqui intento obtener un usuario por su id y luego obtener las carpetas
		// asociadas a el por su id
		Usuario usuariologin;
		it_u = usuarioRepository.findById(1L);

		if ((usuariologin = it_u.get()) != null) {
			System.out.println(usuariologin.toString());
			// CARGAMOS LAS CARPETAS
			usuariologin.setCarpetas(carpetaRepository.findByUsuarioId(usuariologin.getId()));
			for (Carpeta elemento : u.getCarpetas()) {
				System.out.println(elemento.toString());
			}

			// CARGAMOS LAS SESIONES
			usuariologin.setSesiones(sesionRepository.findByUsuarioId(usuariologin.getId()));
			for (Sesion s : usuariologin.getSesiones()) {
				System.out.println(s.toString());
			}

		}

		System.out.println(
				"INFO: simplemente se crea la sesión, cuando se cierre la aplicación web o no se completen las tareas se GUARDARÁ...");
		Sesion sesionlogin = new Sesion();
		sesionlogin.setUsuario(usuariologin);
		usuariologin.addSesion(sesionlogin);

		// GUARDADO SESIÓN AL TERMINAR
		System.out.println();
		System.out.println("GUARDAR SESIÓN AL TERMINAR");
		System.out.println("Se hizo la sesión con éxito y se apostó...");
		sesionlogin.setApuesta(true);
		sesionlogin.setExito(true);
		System.out.println(usuariologin.toString());
		System.out.println(sesionlogin.toString());

		sesionRepository.save(sesionlogin);

		// MOSTRAR SESIONES
		usuariologin = usuarioRepository.findById(1L).get();
		usuariologin.setSesiones(sesionRepository.findByUsuarioId(usuariologin.getId()));

		System.out.println(usuariologin.toString());
		for (Sesion s : usuariologin.getSesiones()) {
			System.out.println(s.toString());
		}

		// CÁLCULO DE LA APUESTA CON SUMA DE FRACASOS Y ÉXITOS
		System.out.println();
		System.out.println("CÁLCULO CUOTA DE APUESTA");
		System.out.println("INFO: se recuperá el número de sesiones exitosas y fracasadas...");
		// vamos a añadir más sesiones para hacer la prueba
		Sesion sesionlogin1 = new Sesion(50, true, usuariologin);
		usuariologin.addSesion(sesionlogin1);
		sesionRepository.save(sesionlogin1);

		Sesion sesionlogin2 = new Sesion(90, true, true, usuariologin);
		usuariologin.addSesion(sesionlogin2);
		sesionRepository.save(sesionlogin2);

		Sesion sesionlogin3 = new Sesion(20, false, usuariologin);
		usuariologin.addSesion(sesionlogin3);
		sesionRepository.save(sesionlogin3);

		//mostramos sesiones
		usuariologin = usuarioRepository.findById(1L).get();
		usuariologin.setSesiones(sesionRepository.findByUsuarioId(usuariologin.getId()));

		System.out.println(usuariologin.toString());
		for (Sesion s : usuariologin.getSesiones()) {
			System.out.println(s.toString());
		}
		
		int exitos = sesionRepository.findSuccessfulSessions(usuariologin.getId());
		int fracasos = sesionRepository.findFailedSessions(usuariologin.getId());
		
		System.out.println("El número de sesiones exitosas es: "+exitos);
		System.out.println("El número de sesiones fracasadas es: "+fracasos);

	}

}
