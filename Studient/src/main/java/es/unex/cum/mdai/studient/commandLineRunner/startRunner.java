package es.unex.cum.mdai.studient.commandLineRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Prioridad;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.CarpetaRepository;
import es.unex.cum.mdai.studient.repository.TareaRepository;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;

//@Order (1) Permite la ejecución en ordenada de (1 a N) de multiples commandLineRunner.
@Component
public class startRunner implements CommandLineRunner{
	
	@Autowired
	private UsuarioRepository usuarioRepository;	
	
	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private CarpetaRepository carpetaRepository;
		
//	public startRunner(UsuarioRepository usuarioRepository) {		
//		this.usuarioRepository = usuarioRepository;		
//		// TODO Auto-generated constructor stub
//	}
	
	public startRunner() {						
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("\t Usando CommandLineRunner");
		insertUsers("pepe@gmail.com", "pepe");
		insertUsers("alvaro@gmail.com", "alvaro");

	}

	public void insertUsers(String email, String password) {

		Usuario u = new Usuario(email, password);

		Carpeta alta = new Carpeta("Prioridad Alta", false, u);
		Carpeta baja = new Carpeta("Prioridad Baja", false, u);
		Carpeta completadas = new Carpeta("Tareas completadas", false, u);
		Carpeta nulas = new Carpeta("Tareas sin realizar", false, u);
		Carpeta mdai = new Carpeta("Desarrollo de Aplicaciones para Dispositivos Móviles", true, u);

		// colocamos las distintas carpetas en la lista de carpetas del usuario
		u.addCarpeta(alta);
		u.addCarpeta(baja);
		u.addCarpeta(completadas);
		u.addCarpeta(nulas);
		u.addCarpeta(mdai);
		
		Tarea t1 = new Tarea(Prioridad.ALTA, "Terminar informe de costes");
		Tarea t2 = new Tarea(Prioridad.ALTA, "Terminar informe de riesgos");
		Tarea t3 = new Tarea(Prioridad.ALTA, "Montaje Arduino");
		Tarea t4 = new Tarea(Prioridad.ALTA, "Testeo de HW");

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

		t1.addCarpeta(mdai);
		t2.addCarpeta(mdai);
		t3.addCarpeta(mdai);
		t4.addCarpeta(mdai);
		
		mdai.addTareas(t1);
		mdai.addTareas(t2);
		mdai.addTareas(t3);
		mdai.addTareas(t4);
		
		tareaRepository.save(t1);
		tareaRepository.save(t2);
		tareaRepository.save(t3);
		tareaRepository.save(t4);

		usuarioRepository.save(u);
		
		Iterable<Carpeta> it_c = carpetaRepository.findAll();
		for (Carpeta c : it_c) {
			System.out.println(c.toString());
			Iterable<Tarea> i_t = tareaRepository.findAllByCarpetaId(c.getId());
			for (Tarea t : i_t) {
				System.out.println(t.toString());
			}
		}
		
	}

}
