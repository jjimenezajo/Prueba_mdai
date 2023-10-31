package es.unex.cum.mdai.studient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import es.unex.cum.mdai.studient.repository.*;

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
	
		
		
		
		
		
	}

}
