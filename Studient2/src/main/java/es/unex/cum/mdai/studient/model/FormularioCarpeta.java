package es.unex.cum.mdai.studient.model;

public class FormularioCarpeta {
	private String descripcion;
	private Long id;
	
	public FormularioCarpeta(String descripcion, Long id) {
		this.descripcion = descripcion;
		this.id = id;
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
	
}
