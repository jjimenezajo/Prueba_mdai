package es.unex.cum.mdai.studient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private int inicios_consecutivos;

	@OneToMany (mappedBy = "usuario", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	List<Carpeta> carpetas;
	
	@OneToMany (mappedBy="usuario", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	List<Sesion> sesiones;
	
	public Usuario() {
		this.correo = "";
		this.contrasena = "";
		this.monedero = 100;
		this.inicios_consecutivos = 0;
		this.carpetas= new ArrayList<>();
		this.sesiones = new ArrayList<>();
	}

	
	public Usuario(String email, String password) {
		super();
		this.correo = email;
		this.contrasena = password;
		this.monedero = 100;
		this.inicios_consecutivos = 0;
		this.carpetas= new ArrayList<>();
		this.sesiones = new ArrayList<>();
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
	
	public List<Carpeta> getCarpetas() {
		return carpetas;
	}

	public void setCarpetas(List<Carpeta> carpetas) {
		this.carpetas = carpetas;
	}


	public long getId() {
		return id;
	}

	public List<Sesion> getSesiones() {
		return sesiones;
	}


	public void setSesiones(List<Sesion> sesiones) {
		this.sesiones = sesiones;
	}

	public int getInicios_consecutivos() {
		return inicios_consecutivos;
	}


	public void setInicios_consecutivos(int inicios_consecutivos) {
		this.inicios_consecutivos = inicios_consecutivos;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(carpetas, contrasena, correo, id, inicios_consecutivos, monedero, sesiones);
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
		return Objects.equals(carpetas, other.carpetas) && Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(correo, other.correo) && id == other.id
				&& inicios_consecutivos == other.inicios_consecutivos && monedero == other.monedero
				&& Objects.equals(sesiones, other.sesiones);
	}
	
	


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", correo=" + correo + ", contrasena=" + contrasena + ", monedero=" + monedero
				+ ", inicios_consecutivos=" + inicios_consecutivos+ "]";
	}


	public void addCarpeta(Carpeta d) {
		carpetas.add(d);
	}
	
	public void addSesion(Sesion s) {
		sesiones.add(s);
	}
	
}
