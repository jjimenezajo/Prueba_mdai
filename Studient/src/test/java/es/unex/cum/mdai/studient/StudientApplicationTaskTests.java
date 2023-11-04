package es.unex.cum.mdai.studient;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Estado;
import es.unex.cum.mdai.studient.model.Prioridad;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.CarpetaRepository;
import es.unex.cum.mdai.studient.repository.TareaRepository;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;

@SpringBootTest
public class StudientApplicationTaskTests {
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

		// CREAR TAREAS
		System.out.println();
		System.out.println("CREACIÓN DE TAREAS");

		Tarea t1 = new Tarea(Prioridad.ALTA, "Terminar informe de costes");
		Tarea t2 = new Tarea(Prioridad.BAJA, "Terminar informe de riesgos");
		Tarea t3 = new Tarea(Prioridad.ALTA, "Montaje Arduino");
		Tarea t4 = new Tarea(Prioridad.BAJA, "Testeo de HW");

		// Añadimos las tareas a sus correspondientes carpetas inmutables
		if (t1.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println(
					"INFO: La tarea " + t1.getDescripcion() + " se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t1); // get(0) es la carpeta para tareas de alta prioridad
			t1.addCarpeta(u.getCarpetas().get(0));
		} else {
			System.out.println(
					"INFO: La tarea " + t1.getDescripcion() + " se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t1); // get(1) es la carpeta para tareas de baja prioridad
			t1.addCarpeta(u.getCarpetas().get(1));
		}

		if (t2.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println(
					"INFO: La tarea " + t2.getDescripcion() + " se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t2);
			t2.addCarpeta(u.getCarpetas().get(0));
		} else {
			System.out.println(
					"INFO: La tarea " + t1.getDescripcion() + " se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t2);
			t2.addCarpeta(u.getCarpetas().get(1));
		}

		if (t3.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println(
					"INFO: La tarea " + t3.getDescripcion() + " se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t3);
			t3.addCarpeta(u.getCarpetas().get(0));
		} else {
			System.out.println(
					"INFO: La tarea " + t1.getDescripcion() + " se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t3);
			t3.addCarpeta(u.getCarpetas().get(1));
		}

		if (t4.getPrioridad().equals(Prioridad.ALTA)) {
			System.out.println(
					"INFO: La tarea " + t4.getDescripcion() + " se añadió  a la carpeta de tareas de ALTA prioridad");
			u.getCarpetas().get(0).addTareas(t4);
			t4.addCarpeta(u.getCarpetas().get(0));
		} else {
			System.out.println(
					"INFO: La tarea " + t4.getDescripcion() + " se añadió  a la carpeta de tareas de BAJA prioridad");
			u.getCarpetas().get(1).addTareas(t4);
			t4.addCarpeta(u.getCarpetas().get(1));
		}

		// Se añaden ambas tareas a la carpeta en la que se encuentra el usuario
		// actualmente
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
		carpetaRepository.save(alta);
		carpetaRepository.save(baja);

		System.out.println("INFO: recuperación de información de la base de datos...");

		i_t = carpetaRepository.findAllTareaByCarpetaId(c1.getId());

		for (Tarea t : i_t) {
			System.out.println(t.toString());
		}

		// MOSTRAR LAS TAREAS DE UNA CARPETA EN UN DETERMINADO ORDEN
		System.out.println();
		System.out.println("RECUPERAR TAREAS DE UNA CARPETA");
		System.out.println("INFO: tareas según su estado y prioridad en la carpeta " + c1.getNombre() + "...");

		i_t = carpetaRepository.orderByTaskPriority(c1.getId());

		for (Tarea t : i_t) {
			System.out.println(t.toString());
		}

		// MODIFICAR UNA TAREA (USUARIO)
		System.out.println();
		System.out.println("POSIBILIDADES DE MODIFICACIÓN DE UNA TAREA POR PARTE DEL USUARIO");

		// modificar un atributo
		t3.setDescripcion("Revisar informes");
		t3.setPrioridad(Prioridad.BAJA);

		tareaRepository.save(t3);

		i_t = carpetaRepository.orderByTaskPriority(c1.getId());

		for (Tarea t : i_t) {
			System.out.println(t.toString());
		}

		// TAREAS COMPLETAS Y SIN REALIZAR
		System.out.println();
		System.out.println("RECUPERAR TAREAS DE UNA CARPETA");
		System.out.println("INFO: suponemos que el usuario ha completado y ha dejado sin realizar varias tareas...");
		System.out.println("INFO: tareas según su estado y prioridad en la carpeta " + c1.getNombre() + "...");

		// modificar una tarea (página web)
		t2.setEstado(Estado.COMPLETADO);
		t2.setPrioridad(null);

		t3.setEstado(Estado.NULO);
		t3.setPrioridad(null);

		// Primero elimino la relación de la tarea con su antigua carpeta
		// y luego la agrego a una nueva carpeta
		baja = carpetaRepository.findById(2L).get();
		baja.setTareas(carpetaRepository.findAllTareaByCarpetaId(baja.getId()));
		baja.getTareas().remove(0);
		completadas.addTareas(t2);
		alta = carpetaRepository.findById(1L).get();
		alta.setTareas(carpetaRepository.findAllTareaByCarpetaId(alta.getId()));
		alta.getTareas().remove(1);
		nulas.addTareas(t3);

		tareaRepository.save(t2);
		tareaRepository.save(t3);
		carpetaRepository.save(alta);
		carpetaRepository.save(baja);
		carpetaRepository.save(completadas);
		carpetaRepository.save(nulas);

		it_c = carpetaRepository.findAll();
		for (Carpeta c : it_c) {
			System.out.println(c.toString());
			i_t = carpetaRepository.findAllTareaByCarpetaId(c.getId());
			for (Tarea t : i_t) {
				System.out.println(t.toString());
			}
		}

		i_t = carpetaRepository.orderByTaskPriority(c1.getId());

		for (Tarea t : i_t) {
			System.out.println(t.toString());
		}

		// mover de carpeta
		System.out.println();
		System.out.println("MOVER UNA TAREA DE UNA CARPETA A OTRA");
		System.out.println("Movemos la tarea informe de costes de la carpeta GPT a MDADM...");

		Carpeta mdai = carpetaRepository.findById(5L).get();
		mdai.setTareas(carpetaRepository.findAllTareaByCarpetaId(mdai.getId()));

		mdai.getTareas().remove(0); // elimino la relación con la tarea

		carpetaRepository.save(mdai);

		Carpeta c3 = new Carpeta("MDADM", true, u);
		u.addCarpeta(c3); // incluyo la tarea en la nueva carpeta
		c3.addTareas(t1);

		carpetaRepository.save(c3);

		it_c = carpetaRepository.findAll();
		for (Carpeta c : it_c) {
			System.out.println(c.toString());
			i_t = carpetaRepository.findAllTareaByCarpetaId(c.getId());
			for (Tarea t : i_t) {
				System.out.println(t.toString());
			}
		}

		// BORRAR UNA TAREA
		
		System.out.println();
		System.out.println("BORRAR UNA TAREA");
		System.out.println("INFO: la tarea 'informe de costes' se eliminará de la carpeta 'MDADM'...");
		
		Carpeta mdadm = carpetaRepository.findById(7L).get();
		mdadm.setTareas(carpetaRepository.findAllTareaByCarpetaId(mdadm.getId()));

		mdadm.getTareas().remove(0); // elimino la relación entre la carpeta y la tarea

		alta = carpetaRepository.findById(1L).get();
		alta.setTareas(carpetaRepository.findAllTareaByCarpetaId(alta.getId()));

		alta.getTareas().remove(0);// elimino la relación entre la carpeta y la tarea

		carpetaRepository.save(mdadm);
		carpetaRepository.save(alta);
		tareaRepository.delete(t1); // elimino la tarea

		it_c = carpetaRepository.findAll();
		for (Carpeta c : it_c) {
			System.out.println(c.toString());
			i_t = carpetaRepository.findAllTareaByCarpetaId(c.getId());
			for (Tarea t : i_t) {
				System.out.println(t.toString());
			}
		}

		// BORRAR TODAS LAS TAREAS DE UNA CARPETA

		System.out.println();
		System.out.println("BORRAR TODAS LAS TAREAS DE UNA CARPETA MUTABLE");
		System.out.println("INFO: esta operación conlleva la eliminación automática de las tareas de las"
				+ "carpeta inmutables...");
		
		System.out.println("INFO: estas son las tareas existentes");
		
		i_t = tareaRepository.findAll();
		for (Tarea t : i_t) {
			System.out.println(t.toString());
		}
		
		mdai = carpetaRepository.findById(5L).get();
		mdai.setTareas(carpetaRepository.findAllTareaByCarpetaId(mdai.getId()));
		alta = carpetaRepository.findById(1L).get();
		alta.setTareas(carpetaRepository.findAllTareaByCarpetaId(alta.getId()));
		baja = carpetaRepository.findById(2L).get();
		baja.setTareas(carpetaRepository.findAllTareaByCarpetaId(baja.getId()));
		completadas = carpetaRepository.findById(3L).get();
		completadas.setTareas(carpetaRepository.findAllTareaByCarpetaId(completadas.getId()));
		nulas = carpetaRepository.findById(4L).get();
		nulas.setTareas(carpetaRepository.findAllTareaByCarpetaId(nulas.getId()));
		i_t = carpetaRepository.findAllTareaByCarpetaId(mdai.getId());

		//
		mdai.getTareas().clear(); // quito todas las relaciones con las tareas
		carpetaRepository.save(mdai);

		for (Tarea t : i_t) {
			
			switch (t.getEstado()) {

			case COMPLETADO -> {
				System.out.println("La tarea '"+t.getDescripcion()+"' se eliminará de la carpeta de tareas completas");
				completadas.getTareas().remove(t);
				carpetaRepository.save(completadas);
			}

			case NULO -> {
				System.out.println("La tarea '"+t.getDescripcion()+"' se eliminará de la carpeta tareas no realizadas");
				nulas.getTareas().remove(t);
				carpetaRepository.save(nulas);
			}

			case PENDIENTE -> {
				
				if (t.getPrioridad().equals(Prioridad.ALTA)) {
					System.out.println("La tarea '"+t.getDescripcion()+"' se eliminará de la carpeta de prioridad alta");
					alta.getTareas().remove(t);
					carpetaRepository.save(alta);
				}else {
					System.out.println("La tarea '"+t.getDescripcion()+"' se eliminará de la carpeta de prioridad baja");
					baja.getTareas().remove(t);
					carpetaRepository.save(baja);
				}	
			}

			}

			tareaRepository.delete(t); // elimino todas las tareas
		}

		it_c = carpetaRepository.findAll();
		for (Carpeta c : it_c) {
			System.out.println(c.toString());
			i_t = carpetaRepository.findAllTareaByCarpetaId(c.getId());
			for (Tarea t : i_t) {
				System.out.println(t.toString());
			}
		}
	}

}
