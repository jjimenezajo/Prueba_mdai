package es.unex.cum.mdai.studient.model;

public class FormularioTarea {
	private String nombre;
	private String prioridad;
	private Long id;
	private Long idCarpeta;
	
	public FormularioTarea() {
		
	}
	public FormularioTarea(String nombre, String prioridad, Long id, Long idCarpeta) {
		super();
		this.nombre = nombre;
		this.prioridad = prioridad;
		this.id = id;
		this.idCarpeta = idCarpeta;
	}

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCarpeta() {
		return idCarpeta;
	}

	public void setIdCarpeta(Long idCarpeta) {
		this.idCarpeta = idCarpeta;
	}
	
}
