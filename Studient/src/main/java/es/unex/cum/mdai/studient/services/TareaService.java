package es.unex.cum.mdai.studient.services;

import java.util.Optional;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Tarea;


public interface TareaService {
	
	public Optional<Tarea> findTareaById (Long tareaId);
	
	public Iterable<Tarea> findAllTareaByCarpetaId(Long tareaId);

	public boolean deleteTareaById(Long tareaId);
	
	public Iterable<Tarea> updateTarea(Tarea tarea);
	
	public boolean updateDescription(Long id, String descripcion); //Falta el resto de atributos por actualizar
		
	public Iterable<Tarea> orderByTaskPriority(Long tareaId);
	
	public Iterable<Tarea> saveTarea(Tarea tarea);
}
