package es.unex.cum.mdai.studient.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.repository.CarpetaRepository;

@Service
public class CarpetaServiceImpl implements CarpetaService {

	private final CarpetaRepository carpetaRepository;

	public CarpetaServiceImpl(CarpetaRepository carpetaRepository) {
		
		System.out.println("\t Arrancado automático");
		this.carpetaRepository = carpetaRepository;
	}

	@Override
	public Optional<Carpeta> findCarpetaById(Long carpetaId) {
		return carpetaRepository.findById(carpetaId);
	}
	
	@Override
	public Iterable<Carpeta> findAllCarpetaByUsuarioId(Long usuarioId) {
		return carpetaRepository.findByUsuarioId(usuarioId);
	}

	@Override
	public boolean deleteCarpetaById(Long id) {
		
		boolean successful = false;
		
		int before = countCarpeta();
		carpetaRepository.deleteById(id);
		int after = countCarpeta();
		
		//Tras la eliminación debe haber un elemento menos en la BD
		if(before-1 == after)
			successful = true;
		
		return successful;
	}

	@Override
	public Iterable<Carpeta> updateCarpeta(Carpeta carpeta) {
		carpetaRepository.save(carpeta);
		return carpetaRepository.findAll();
	}

	@Override
	public boolean updateName(Long id, String nombre) {
		boolean successful=false;
		Optional<Carpeta> optional_updated = carpetaRepository.findById(id);
		if(!optional_updated.isEmpty()) {
			Carpeta updated = (Carpeta) optional_updated.get();
			updated.setNombre(nombre);
			carpetaRepository.save(updated);
			
			//COMPROBACIÓN DE LA ACTUALIZACIÓN PREVIA
			Carpeta aux = carpetaRepository.findById(id).get();
			if(aux.getNombre() == nombre)
				successful = true;
		}
		
		
		return successful;
	}

	@Override
	public int countCarpeta() {
		Iterable<Carpeta> it_c = carpetaRepository.findAll();
		int count = 0;
		
		for(Carpeta elemento : it_c) {
			count++;
		}
		
		return count;
	}


	

}
