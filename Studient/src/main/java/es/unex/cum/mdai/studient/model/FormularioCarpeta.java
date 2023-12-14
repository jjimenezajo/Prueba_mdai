package es.unex.cum.mdai.studient.model;

public class FormularioCarpeta {
	private String descripcion;
	private Long id;
	private String editar;
	private Long idEditar;
	
	public FormularioCarpeta(String descripcion, Long id, String editar, Long idEditar) {
		this.descripcion = descripcion;
		this.id = id;
		this.editar = editar;
		this.idEditar = idEditar;
	}
	public FormularioCarpeta() {
		
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEditar() {
		return editar;
	}
	public void setEditar(String editar) {
		this.editar = editar;
	}
	public Long getIdEditar() {
		return idEditar;
	}
	public void setIdEditar(Long idEditar) {
		this.idEditar = idEditar;
	}
	
	
}
