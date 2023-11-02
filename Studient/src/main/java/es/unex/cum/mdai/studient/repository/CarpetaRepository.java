package es.unex.cum.mdai.studient.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Tarea;



public interface CarpetaRepository extends CrudRepository<Carpeta, Long>{

	public Iterable<Carpeta> findByUsuarioId(Long id);
	
	@Query("SELECT tarea FROM Carpeta c JOIN c.tareas tarea WHERE c.id = ?1 ORDER BY tarea.estado desc, tarea.prioridad")
	public Iterable<Tarea> orderByTaskPriority(Long id);

}
