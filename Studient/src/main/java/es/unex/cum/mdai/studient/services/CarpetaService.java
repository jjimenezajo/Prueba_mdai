package es.unex.cum.mdai.studient.services;

import java.util.List;
import java.util.Optional;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Usuario;


public interface CarpetaService {
	
	public Optional<Carpeta> findCarpetaById (Long carpetaId);
	
	public Iterable<Carpeta> findAllCarpetaByUsuarioId(Long usuarioId);

	public boolean deleteCarpetaById(Long carpetaId, Long usuarioId);
	
	public Iterable<Carpeta> updateCarpeta(Carpeta carpeta);
	
	public boolean updateName(Long id, String nombre);
	
	public Carpeta findCarpetaPrioridadAltaByUsuarioId(Long id);
	
	public int countCarpeta(Long usuarioId);
	
	public Iterable<Carpeta> saveCarpeta(Carpeta carpeta);

	public Carpeta findCarpetaPrioridadBajaByUsuarioId(Long idUser);
	
	public List<Carpeta> findCarpetaByDescripcion(String descripcion);
}
