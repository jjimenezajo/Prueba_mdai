package es.unex.cum.mdai.studient;

import java.util.Iterator;
import java.util.Optional;

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
		//colocamos las distintas carpetas en la lista de carpetas del usuario
		u.addCarpeta(alta);
		u.addCarpeta(baja);
		u.addCarpeta(completadas);
		u.addCarpeta(nulas);
		//intentamos insertar el usuario junto a sus carpetas
		usuarioRepository.save(u);
		
		//Intento obtener todas las carpetas
		Iterable<Carpeta> it_c =carpetaRepository.findAll();
		Iterator i= it_c.iterator();
		while (i.hasNext()) {
			Carpeta c_aux = (Carpeta) i.next();
			System.out.println(c_aux.toString());
		}
		//Aqui intento obtener un usuario por su id y luego obtener las carpetas asociadas a el por su id
		Optional<Usuario> it_u = usuarioRepository.findById(1L);
		if (it_u.isEmpty()==false) {
			System.out.println(it_u.toString());
			it_c =carpetaRepository.findByUsuarioId(it_u.get().getId());
			i = it_c.iterator();
			while (i.hasNext()) {
				Carpeta carp_aux = (Carpeta) i.next();
				System.out.println(carp_aux.toString());
			}
		}

		//intento ver lo que pasa si borro al usuario
		/**
		 * 
		 
		usuarioRepository.delete(u);
		
		it_c =carpetaRepository.findAll();
		i= it_c.iterator();
		while (i.hasNext()) {
			System.out.println("Carpeta encontrada");
			Carpeta c_aux = (Carpeta) i.next();
			System.out.println(c_aux.toString());
		}
		//Si pongo el cascade type como all entonces se borran tambien las carpetas, si no entonces no
		 * */
		
		//aqui voy a probar el actualizar. Si borras el comentario del codigo anterior no saldria con el id 1 (creo)
		u.setCorreo("correoprueba@gmail.com");
		u.setMonedero(120);
		usuarioRepository.save(u);
		it_u = usuarioRepository.findById(1L);
		if (it_u.isEmpty()==false) {
			System.out.println(it_u.toString());
			it_c =carpetaRepository.findByUsuarioId(it_u.get().getId());
			i = it_c.iterator();
			while (i.hasNext()) {
				Carpeta carp_aux = (Carpeta) i.next();
				System.out.println(carp_aux.toString());
			}
		}else {
			System.out.println("No lo encuentro, no he podido actualizar bien o se ha borrado");
		}
	}

}
