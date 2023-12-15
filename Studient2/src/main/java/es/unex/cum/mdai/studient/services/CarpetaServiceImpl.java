package es.unex.cum.mdai.studient.services;

import java.util.List;
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
	public boolean deleteCarpetaById(Long carpetaId, Long usuarioId) {
		
		boolean successful = false;
		
		int before = countCarpeta(usuarioId);
		carpetaRepository.deleteById(carpetaId);
		int after = countCarpeta(usuarioId);
		
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
		Optional<Carpeta> optional_updated = findCarpetaById(id);
		if(!optional_updated.isEmpty()) {
			Carpeta updated = (Carpeta) optional_updated.get();
			updated.setNombre(nombre);
			carpetaRepository.save(updated);
			
			//COMPROBACIÓN DE LA ACTUALIZACIÓN PREVIA
			Carpeta aux = findCarpetaById(id).get();
			if(aux.getNombre() == nombre)
				successful = true;
		}
		
		
		return successful;
	}

	@Override
	public int countCarpeta(Long usuarioId) {
		Iterable<Carpeta> it_c = findAllCarpetaByUsuarioId(usuarioId);
		int count = 0;
		
		for(Carpeta elemento : it_c) {
			count++;
		}
		
		return count;
	}

	@Override
	public Carpeta findCarpetaPrioridadAltaByUsuarioId(Long id) {
		Iterable<Carpeta> carpetas = findAllCarpetaByUsuarioId(id);
		Carpeta alta = new Carpeta();
		
		for(Carpeta elemento : carpetas) {
			if(elemento.getNombre().equals("Prioridad Alta")) {
				alta = elemento;
			}
		}
		
		return alta;
	}

	@Override
	public Iterable<Carpeta> saveCarpeta(Carpeta carpeta) {
		carpetaRepository.save(carpeta);
		
		return carpetaRepository.findAll();
	}

	@Override
	public Carpeta findCarpetaPrioridadBajaByUsuarioId(Long idUser) {
		Iterable<Carpeta> carpetas = findAllCarpetaByUsuarioId(idUser);
		Carpeta alta = new Carpeta();
		
		for(Carpeta elemento : carpetas) {
			if(elemento.getNombre().equals("Prioridad Baja")) {
				alta = elemento;
			}
		}
		
		return alta;
	}

	@Override
	public List<Carpeta> findCarpetaByDescripcion(String descripcion) {
		return carpetaRepository.findByNombre(descripcion);
	}


	

}
