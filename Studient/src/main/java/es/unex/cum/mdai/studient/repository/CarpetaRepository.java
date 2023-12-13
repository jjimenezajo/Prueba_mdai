package es.unex.cum.mdai.studient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Tarea;



public interface CarpetaRepository extends CrudRepository<Carpeta, Long>{

	public List<Carpeta> findByUsuarioId(Long id);
	
	public List<Carpeta> findByNombre(String descripcion);

}
