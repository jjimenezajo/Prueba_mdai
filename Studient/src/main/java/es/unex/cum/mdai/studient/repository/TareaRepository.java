package es.unex.cum.mdai.studient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.unex.cum.mdai.studient.model.Tarea;

public interface TareaRepository extends CrudRepository<Tarea, Long>{

	@Query("SELECT tarea FROM Carpeta c JOIN c.tareas tarea WHERE c.id = ?1 ORDER BY tarea.estado desc, tarea.prioridad")
	public List<Tarea> orderByTaskPriority(Long id);
	
	@Query("SELECT tarea FROM Carpeta c JOIN c.tareas tarea WHERE c.id = ?1")
	public List<Tarea> findAllByCarpetaId(Long id);
}
