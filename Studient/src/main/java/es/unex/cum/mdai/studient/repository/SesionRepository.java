package es.unex.cum.mdai.studient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.unex.cum.mdai.studient.model.Sesion;

public interface SesionRepository extends CrudRepository<Sesion, Long>{

	public List<Sesion> findByUsuarioId(Long id);
	
	@Query("SELECT COUNT(sesion) FROM Sesion sesion WHERE sesion.exito = true and sesion.usuario.id = ?1")
	public int findSuccessfulSessions(Long id);	
	
	@Query("SELECT COUNT(sesion) FROM Sesion sesion WHERE sesion.exito = false and sesion.usuario.id = ?1")
	public int findFailedSessions(Long id);	
	
	
}
