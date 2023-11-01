package es.unex.cum.mdai.studient.repository;

import org.springframework.data.repository.CrudRepository;

import es.unex.cum.mdai.studient.model.Carpeta;



public interface CarpetaRepository extends CrudRepository<Carpeta, Long>{

	Iterable<Carpeta> findByUsuarioId(long id);

}
