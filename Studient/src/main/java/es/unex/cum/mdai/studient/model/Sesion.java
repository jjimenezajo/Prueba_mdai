package es.unex.cum.mdai.studient.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Sesion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int tiempo;
	private boolean exito;
	private boolean apuesta;
	
	@ManyToOne
	Usuario usuario;
	
	public Sesion() {
		
	}
	
	public Sesion(Usuario usuario) {
		super();
		tiempo = 0;
		this.apuesta = false;
		this.exito = false;
		this.usuario = usuario;
	}
	
	
	public Sesion(int tiempo, boolean exito, Usuario usuario) {
		super();
		this.tiempo = tiempo;
		this.exito = exito;
		this.apuesta = false;
		this.usuario = usuario;
	}

	public Sesion(int tiempo, boolean exito, boolean apuesta, Usuario usuario) {
		super();
		this.tiempo = tiempo;
		this.exito = exito;
		this.apuesta = apuesta;
		this.usuario = usuario;
	}
	
	public long getId() {
		return id;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public boolean isApuesta() {
		return apuesta;
	}

	public void setApuesta(boolean apuesta) {
		this.apuesta = apuesta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(apuesta, exito, id, tiempo, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		return apuesta == other.apuesta && exito == other.exito && id == other.id && tiempo == other.tiempo
				&& Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "Sesion [id=" + id + ", tiempo=" + tiempo + ", exito=" + exito + ", apuesta=" + apuesta + ", usuario="+usuario.getId()+"]";
	}
}
