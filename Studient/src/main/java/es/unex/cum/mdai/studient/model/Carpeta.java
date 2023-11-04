package es.unex.cum.mdai.studient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Carpeta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private boolean mutabilidad;
	
	@ManyToOne
	Usuario usuario;
	
	@ManyToMany(cascade = CascadeType.REMOVE)
	List<Tarea> tareas;
	
	public Carpeta() {
		
	}
	
	public Carpeta(String nombre, Usuario usuario) {
		super();
		this.nombre = nombre;
		this.mutabilidad = true;
		this.usuario = usuario;
		tareas = new ArrayList<>();
	}

	public Carpeta(String nombre, boolean mutabilidad, Usuario usuario) {
		super();
		this.nombre = nombre;
		this.mutabilidad = mutabilidad;
		this.usuario = usuario;
		tareas = new ArrayList<>();
	}

	public long getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isMutabilidad() {
		return mutabilidad;
	}

	public void setMutabilidad(boolean mutabilidad) {
		this.mutabilidad = mutabilidad;
	}

	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<Tarea> getTareas() {
		return tareas;
	}
	
	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, mutabilidad, nombre, tareas, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carpeta other = (Carpeta) obj;
		return id == other.id && mutabilidad == other.mutabilidad && Objects.equals(nombre, other.nombre)
				&& Objects.equals(tareas, other.tareas) && Objects.equals(usuario, other.usuario);
	}
	
	@Override
	public String toString() {
		return "Carpeta [id=" + id + ", nombre=" + nombre + ", mutabilidad=" + mutabilidad + ", usuario=" + usuario.getId()+"]";
	}

	public void addTareas(Tarea t) {
		tareas.add(t);
	}

}
