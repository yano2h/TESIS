package cl.uv.model.base.core.beans;

public class Aplicacion {

	private Integer id;
	private String glosa;
	private String codigoProyecto;
	private String descripcion;
	private String url;
	private String imagen;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGlosa() {
		return this.glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa.trim();
	}

	public String getCodigoProyecto() {
		return this.codigoProyecto;
	}

	public void setCodigoProyecto(String codigoProyecto) {
		this.codigoProyecto = codigoProyecto.trim();
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url.trim();
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen.trim();
	}
}