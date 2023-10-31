package es.unex.cum.mdai.studient.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String correo;
	private String contrasena;
	private int monedero;
	
	@OneToMany (mappedBy = "usuario", cascade = CascadeType.PERSIST)
	List<Carpeta> carpetas;
	
	public Usuario() {
		
	}

	
	public Usuario(String email, String password) {
		super();
		this.correo = email;
		this.contrasena = password;
		this.monedero = 100;
	}


	public long getId() {
		return id;
	}

	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String email) {
		this.correo = email;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public int getMonedero() {
		return monedero;
	}


	public void setMonedero(int monedero) {
		this.monedero = monedero;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", correo=" + correo + ", contrasena=" + contrasena + ", monedero=" + monedero + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(correo, id, monedero, contrasena);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(correo, other.correo) && id == other.id && monedero == other.monedero
				&& Objects.equals(contrasena, other.contrasena);
	}
	
	
}
