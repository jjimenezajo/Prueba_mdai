package es.unex.cum.mdai.studient.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.unex.cum.mdai.studient.model.Tarea;

public interface TareaRepository extends CrudRepository<Tarea, Long>{

	
}
