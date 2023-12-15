package es.unex.cum.mdai.studient;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Prioridad;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.CarpetaRepository;
import es.unex.cum.mdai.studient.repository.TareaRepository;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;

@SpringBootTest
public class StudientApplicationUserTests {
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CarpetaRepository carpetaRepository;

	@Autowired
	TareaRepository tareaRepository;


	@Test
	void contextLoads() {
		
		Iterable<Carpeta> it_c;
		Iterable<Tarea> i_t;
		Iterable<Usuario> i_u;
		Optional<Usuario> it_u;

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
		Carpeta nulas = new Carpeta("Tareas sin realizar", false, u);

		// colocamos las distintas carpetas en la lista de carpetas del usuario
		u.addCarpeta(alta);
		u.addCarpeta(baja);
		u.addCarpeta(completadas);
		u.addCarpeta(nulas);

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

		it_u = usuarioRepository.findById(u.getId());
		if (it_u.isEmpty() == false) {
			System.out.println(it_u.get().toString());
			it_c = carpetaRepository.findByUsuarioId(it_u.get().getId());
			for (Carpeta elemento : it_c) {
				System.out.println(elemento.toString());
			}
		}

		// MODIFICAR INFORMACIÓN PERSONAL
		System.out.println();
		System.out.println("MODIFICACIÓN DE INFORMACIÓN PERSONAL");
		u.setCorreo("correoprueba@gmail.com");
		u.setContrasena("contrasena");

		usuarioRepository.save(u);

		it_u = usuarioRepository.findById(u.getId());

		if (it_u.isEmpty() == false) {
			System.out.println(it_u.get().toString());
			it_c = carpetaRepository.findByUsuarioId(it_u.get().getId());
			for (Carpeta elemento : it_c) {
				System.out.println(elemento.toString());
			}
		} else {
			System.out.println("No lo encuentro, no he podido actualizar bien o se ha borrado");
		}

		// CREAR CARPETA MUTABLE
		System.out.println();
		System.out.println("CREAR CARPETA MUTABLE");
		System.out.println("Antes de la inserción, el usuario tenía 4 carpetas...");

		Carpeta c1 = new Carpeta("MDAI", true, u);
		Carpeta c2 = new Carpeta("SI", true, u);
		u.addCarpeta(c1);
		u.addCarpeta(c2);

		carpetaRepository.save(c1);
		carpetaRepository.save(c2);

		int contador = 0;
		it_c = carpetaRepository.findByUsuarioId(u.getId());
		for (Carpeta elemento : it_c) {
			System.out.println(elemento.toString());
			contador++;
		}

		System.out.println("Tras la inserción el usuario tiene " + contador + " carpetas");
		
		//CREAR TAREAS
		System.out.println();
		System.out.println("CREACIÓN DE TAREAS");
		
		Tarea t1 = new Tarea(Prioridad.ALTA, "Terminar informe de costes");
		Tarea t2 = new Tarea(Prioridad.BAJA, "Terminar informe de riesgos");
		Tarea t3 = new Tarea(Prioridad.ALTA, "Montaje Arduino");
		Tarea t4 = new Tarea(Prioridad.BAJA, "Testeo de HW");
		
		//Añadimos las tareas a sus correspondientes carpetas inmutables
		if(t1.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println("INFO: La tarea "+t1.getDescripcion()+" se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t1); //get(0) es la carpeta para tareas de alta prioridad
			t1.addCarpeta(u.getCarpetas().get(0));
		}else {
			System.out.println("INFO: La tarea "+t1.getDescripcion()+" se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t1); //get(1) es la carpeta para tareas de baja prioridad
			t1.addCarpeta(u.getCarpetas().get(1));
		}
		
		if(t2.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println("INFO: La tarea "+t2.getDescripcion()+" se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t2);
			t2.addCarpeta(u.getCarpetas().get(0));
		}else {
			System.out.println("INFO: La tarea "+t1.getDescripcion()+" se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t2);
			t2.addCarpeta(u.getCarpetas().get(1));
		}
		
		if(t3.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println("INFO: La tarea "+t3.getDescripcion()+" se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t3);
			t3.addCarpeta(u.getCarpetas().get(0));
		}else {
			System.out.println("INFO: La tarea "+t1.getDescripcion()+" se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t3);
			t3.addCarpeta(u.getCarpetas().get(1));
		}
		
		if(t4.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println("INFO: La tarea "+t4.getDescripcion()+" se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t4);
			t4.addCarpeta(u.getCarpetas().get(0));
		}else {
			System.out.println("INFO: La tarea "+t4.getDescripcion()+" se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t4);
			t4.addCarpeta(u.getCarpetas().get(1));
		}
		
		//Se añaden ambas tareas a la carpeta en la que se encuentra el usuario actualmente
		c1.addTareas(t1);
		c1.addTareas(t2);
		c1.addTareas(t3);
		c1.addTareas(t4);
		t1.addCarpeta(c1);
		t2.addCarpeta(c1);
		t3.addCarpeta(c1);
		t4.addCarpeta(c1);
		
		tareaRepository.save(t1);
		tareaRepository.save(t2);
		tareaRepository.save(t3);
		tareaRepository.save(t4);
		carpetaRepository.save(c1);
		
		System.out.println("INFO: recuperación de información de la base de datos...");
		
		i_t =tareaRepository.findAllByCarpetaId(c1.getId());

		for(Tarea t : i_t) {
			System.out.println(t.toString());
		}
		
		//BORRAR UNA TAREA

		//tareaRepository.eliminar(t1.getId());

		//BORRAR TODAS LAS TAREAS DE UNA CARPETA
		
		//MODIFICAR MONEDERO DEL USUARIO
		System.out.println();
		System.out.println("MODIFICAR MONEDERO A USUARIO");
		System.out.println("Antes cumplir alguna tarea tenía "+u.getMonedero()+"...");
		
		u.setMonedero(u.getMonedero() + 50);
		usuarioRepository.save(u);
		
		it_u = usuarioRepository.findById(u.getId());
		if(!it_u.isEmpty())
			System.out.println("Ahora tengo "+it_u.get().getMonedero()+" monedas");
		else
			System.out.println("El usuario no existe");
		

		
		
		// BORRAR CUENTA
		System.out.println();
		System.out.println("BORRADO DE CUENTA");

		// intento ver lo que pasa si borro al usuario

		it_c = carpetaRepository.findByUsuarioId(u.getId());
		for (Carpeta elemento : it_c) {
			System.out.println("La carpeta "+elemento.getNombre()+" y sus tareas se eliminará");
			elemento.setTareas(tareaRepository.findAllByCarpetaId(elemento.getId()));
			elemento.getTareas().clear();
			carpetaRepository.save(elemento);
		}
		
		usuarioRepository.delete(u);

		System.out.println("INFO: no deberían mostrarse usuarios...");
		//VISUALIZAR USUARIOS
		i_u = usuarioRepository.findAll();
		for(Usuario usuario : i_u) {
			System.out.println(u.toString());
		}
		
		System.out.println("INFO: no deberían mostrarse carpetas...");
		//VISUALIZAR CARPETAS
		it_c = carpetaRepository.findAll();
		for(Carpeta c : it_c) {
			System.out.println(c.toString());
		}
		
		System.out.println("INFO: no deberían mostrarse tareas...");
		//VISUALIZAR TAREAS
		i_t = tareaRepository.findAll();
		for(Tarea t : i_t) {
			System.out.println(t.toString());
		}
		
	}
}
