package es.unex.cum.mdai.studient.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.repository.TareaRepository;

@Service
public class TareaServiceImpl implements TareaService {

	private final TareaRepository tareaRepository;

	public TareaServiceImpl(TareaRepository tareaRepository) {
		
		System.out.println("\t Arrancado automático");
		this.tareaRepository = tareaRepository;
	}

	@Override
	public Optional<Tarea> findTareaById(Long tareaId) {
		return tareaRepository.findById(tareaId);
	}
	
	@Override
	public Iterable<Tarea> findAllTareaByCarpetaId(Long carpetaId) {
		return tareaRepository.findAllByCarpetaId(carpetaId);
	}

	@Override
	public boolean deleteTareaById(Long tareaId) {
		
		boolean successful = false;
		
		tareaRepository.deleteById(tareaId);
			
		return successful;
	}

	@Override
	public Iterable<Tarea> updateTarea(Tarea tarea) {
		tareaRepository.save(tarea);
		return tareaRepository.findAll();
	}

	@Override
	public boolean updateDescription(Long id, String descripcion) {
		boolean successful=false;
		Optional<Tarea> optional_updated = findTareaById(id);
		if(!optional_updated.isEmpty()) {
			Tarea updated = (Tarea) optional_updated.get();
			updated.setDescripcion(descripcion);
			tareaRepository.save(updated);
			
			//COMPROBACIÓN DE LA ACTUALIZACIÓN PREVIA
			Tarea aux = findTareaById(id).get();
			if(aux.getDescripcion() == descripcion)
				successful = true;
		}
		
		return successful;
	}

	@Override
	public int countTarea(Long carpetaId) {
		Iterable<Tarea> it_c = findAllTareaByCarpetaId(carpetaId);
		int count = 0;
		
		for(Tarea elemento : it_c) {
			count++;
		}
		
		return count;
	}

	@Override
	public Iterable<Tarea> orderByTaskPriority(Long tareaId) {
		
		return tareaRepository.orderByTaskPriority(tareaId);
	}

	@Override
	public Iterable<Tarea> saveTarea(Tarea tarea) {
		tareaRepository.save(tarea);
		
		return tareaRepository.findAll();
	}
	

}
