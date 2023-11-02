package es.unex.cum.mdai.studient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;

@Entity
public class Tarea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Prioridad prioridad;
	private String descripcion;
	private Estado estado;
	
	@ManyToMany(mappedBy = "tareas")
	List<Carpeta> carpetas;
	
	
	public Tarea() {
		
	}
	
	public Tarea(Prioridad prioridad, String descripcion) {
		super();
		this.prioridad = prioridad;
		this.descripcion = descripcion;
		this.estado = Estado.PENDIENTE;
	    this.carpetas = new ArrayList<>();
	}
	
	public long getId() {
		return id;
	}
	public Prioridad getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public List<Carpeta> getCarpetas() {
		return carpetas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, estado, id, prioridad);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		return Objects.equals(descripcion, other.descripcion) && estado == other.estado && id == other.id
				&& prioridad == other.prioridad;
	}
	@Override
	public String toString() {
		return "Tarea [id=" + id + ", prioridad=" + prioridad + ", descripcion=" + descripcion + ", estado=" + estado
				+ "]";
	}
	
	public void addCarpeta(Carpeta c) {
		carpetas.add(c);
	}
	
	
}
