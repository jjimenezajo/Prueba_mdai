package es.unex.cum.mdai.studient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.unex.cum.mdai.studient.model.Carpeta;
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
		//Crear usuario
		Usuario u= new Usuario("pepe@gmail.com","passwd");
		//Insertarlo en la base de datos
		Carpeta alta = new Carpeta("Prioridad Alta",false);
		alta.setUsuario(u);
		Carpeta baja = new Carpeta("Prioridad Baja",false);
		baja.setUsuario(u);
		Carpeta completadas = new Carpeta("Tareas completadas",false);
		completadas.setUsuario(u);
		Carpeta nulas = new Carpeta("Tareas sin realizar",false);
		nulas.setUsuario(u);
		
		usuarioRepository.save(u);
		carpetaRepository.save(alta);
		carpetaRepository.save(baja);
		

		
		
	}

}
