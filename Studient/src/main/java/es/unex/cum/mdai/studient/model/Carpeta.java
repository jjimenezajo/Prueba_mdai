package es.unex.cum.mdai.studient.model;

import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Carpeta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private boolean mutabilidad;
	
	public Carpeta() {
		
	}
	
	public Carpeta(String nombre) {
		super();
		this.nombre = nombre;
		this.mutabilidad = true;
	}

	public Carpeta(String nombre, boolean mutabilidad) {
		super();
		this.nombre = nombre;
		this.mutabilidad = mutabilidad;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, mutabilidad, nombre);
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
		return id == other.id && mutabilidad == other.mutabilidad && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Carpeta [id=" + id + ", nombre=" + nombre + ", mutabilidad=" + mutabilidad + "]";
	}

}
