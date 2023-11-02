package es.unex.cum.mdai.studient;

import java.util.Iterator;
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
class StudientApplicationTests {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CarpetaRepository carpetaRepository;

	@Autowired
	TareaRepository tareaRepository;

	@Test
	void contextLoads() {

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
		Optional<Usuario> it_u = usuarioRepository.findById(u.getId());
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

		Iterable<Carpeta> it_c;

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

		//MODIFICAR CARPETA
		System.out.println();
		System.out.println("MODIFICAR CARPETA");
		
		c1.setNombre("GPT");
		carpetaRepository.save(c1);
		
		Optional<Carpeta> carpeta= carpetaRepository.findById(c1.getId());
		
		if(carpeta.isEmpty())
			System.out.println("La carpeta no se ha encontrado");
		else
			System.out.println(carpeta.get().toString());
		
		//BORRAR CARPETA SIN TAREAS
		System.out.println();
		carpetaRepository.delete(c2);
		
		System.out.println("Antes de la inserción, el usuario tenía 6 carpetas...");
		
		contador=0;
		
		it_c = carpetaRepository.findByUsuarioId(u.getId());
		for (Carpeta elemento : it_c) {
			System.out.println(elemento.toString());
			contador++;
		}
		
		System.out.println("Tras el borrado el usuario tiene " + contador + " carpetas");
		
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
		
		carpetaRepository.save(c1);
		
		System.out.println("INFO: recuperación de información de la base de datos...");
		
		carpeta = carpetaRepository.findById(c1.getId());
		
		Iterator<Tarea> i_tareas;
		i_tareas = carpeta.get().getTareas().iterator();
		while(i_tareas.hasNext()) {
			Tarea t = i_tareas.next();
			System.out.println(t.toString());
			
		}
		
		//MOSTRAR LAS TAREAS DE UNA CARPETA EN UN DETERMINADO ORDEN
		System.out.println();
		System.out.println("RECUPERAR TAREAS DE UNA CARPETA");
		System.out.println("INFO: tareas según su estado y prioridad en la carpeta "+c1.getNombre()+"...");
		
		
		Iterable <Tarea> i_t;
		i_t=carpetaRepository.orderByTaskPriority(c1.getId());
		
		for(Tarea t: i_t) {
			System.out.println(t.toString());
		}
		
		//MODIFICAR UNA TAREA (USUARIO)
		System.out.println();
		System.out.println("POSIBILIDADES DE MODIFICACIÓN DE UNA TAREA POR PARTE DEL USUARIO");
		
		//modificar un atributo
		t3.setDescripcion("Revisar informes");
		t3.setPrioridad(Prioridad.BAJA);
		
		tareaRepository.save(t3);
		
		i_t = carpetaRepository.orderByTaskPriority(c1.getId());
		
		for(Tarea t: i_t) {
			System.out.println(t.toString());
		}
		
		//TAREAS COMPLETAS Y SIN REALIZAR
		System.out.println();
		System.out.println("RECUPERAR TAREAS DE UNA CARPETA");
		System.out.println("INFO: suponemos que el usuario ha completado y ha dejado sin realizar varias tareas...");
		System.out.println("INFO: tareas según su estado y prioridad en la carpeta "+c1.getNombre()+"...");
		
		t2.setEstado(Estado.COMPLETADO);
		t2.setPrioridad(null);
		
		t3.setEstado(Estado.NULO);
		t3.setPrioridad(null);
		
		tareaRepository.save(t2);
		tareaRepository.save(t3);
		
		i_t=carpetaRepository.orderByTaskPriority(c1.getId());
		
		for(Tarea t: i_t) {
			System.out.println(t.toString());
		}
		
		
		
		//mover de carpeta
		/*Carpeta c3 = new Carpeta("MDADM", true, u);
		u.addCarpeta(c3);
		c3.addTareas(t1);
		
		carpetaRepository.save(c3);
		
		c1.getTareas().remove(t1);
		carpetaRepository.save(c1);
		
		it_c = carpetaRepository.findAll();
		for(Carpeta c: it_c) {
			System.out.println(c.toString());
			i_tareas = c.getTareas().iterator();
			while(i_tareas.hasNext()) {
				Tarea tr = i_tareas.next();
				System.out.println(tr.toString());
			}
		}*/
		
		//MODIFICAR UNA TAREA (PÁGINA WEB)
		
		
		
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
		/*System.out.println("BORRADO DE CUENTA");

		// intento ver lo que pasa si borro al usuario
		usuarioRepository.delete(u);

		System.out.println("INFO: NO DEBERÍA HABER CARPETAS SI SE BORRARON EN CASCADA");
		it_c = carpetaRepository.findAll();
		for (Carpeta elemento : it_c) {
			System.out.println(elemento.toString());
			contador++;
		}*/

		
		
		
		
	}
}
