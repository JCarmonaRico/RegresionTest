package properties;

import java.io.Serializable;

public class CasosPrueba implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String idCasoPrueba;
	private String nombreCasoPrueba;
	private String descripcion;
	private String operaciones;

	public String getIdCasoPrueba() {
		return idCasoPrueba;
	}

	public void setIdCasoPrueba(String idCasoPrueba) {
		this.idCasoPrueba = idCasoPrueba;
	}

	public String getNombreCasoPrueba() {
		return nombreCasoPrueba;
	}

	public void setNombreCasoPrueba(String nombreCasoPrueba) {
		this.nombreCasoPrueba = nombreCasoPrueba;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(String operaciones) {
		this.operaciones = operaciones;
	}

	@Override
	public String toString() {
		return "CasosPrueba [idCasoPrueba=" + idCasoPrueba + ", nombreCasoPrueba=" + nombreCasoPrueba + ", descripcion="
				+ descripcion + ", operaciones=" + operaciones + "]";
	}
	
	
}
