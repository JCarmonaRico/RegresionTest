package properties;

import java.io.Serializable;

public class OperacionesConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String requestId;
	private String requestIdSinTimeStamp;
	private String estadoPeticion;
	private String fechaPeticionDesde;
	private String idTransaccion;
	private String Origen;
	private String FechaPeticionHasta;
	private String Instrumento;
	private String Destino;
	private String Accion;
	
	private String Mensaje;
	private String MensMN;
	private String mensDestino;
	
	public OperacionesConfig() {
		// TODO Auto-generated constructor stub
	}

	public OperacionesConfig(String request_ID, String instrumento, String accion, String origen, String destino, String mensaje, String mensMN, String mensDestino) {
		super();
		this.setRequestId(request_ID);
		Instrumento = instrumento;
		Accion = accion;
		Origen = origen;
		Destino = destino;
		Mensaje = mensaje;
		MensMN = mensMN;
		this.mensDestino = mensDestino;
		
	}
	

	public String getEstadoPeticion() {
		return estadoPeticion;
	}
	public void setEstadoPeticion(String estadoPeticion) {
		this.estadoPeticion = estadoPeticion;
	}
	public String getFechaPeticionDesde() {
		return fechaPeticionDesde;
	}
	public void setFechaPeticionDesde(String fechaPeticionDesde) {
		this.fechaPeticionDesde = fechaPeticionDesde;
	}
	public String getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	public String getOrigen() {
		return Origen;
	}
	public void setOrigen(String origen) {
		Origen = origen;
	}
	public String getFechaPeticionHasta() {
		return FechaPeticionHasta;
	}
	public void setFechaPeticionHasta(String fechaPeticionHasta) {
		FechaPeticionHasta = fechaPeticionHasta;
	}
	public String getInstrumento() {
		return Instrumento;
	}
	public void setInstrumento(String instrumento) {
		Instrumento = instrumento;
	}
	public String getDestino() {
		return Destino;
	}
	public void setDestino(String destino) {
		Destino = destino;
	}
	public String getAccion() {
		return Accion;
	}
	public void setAccion(String accion) {
		Accion = accion;
	}
	public String getMensaje() {
		return Mensaje;
	}
	public void setMensaje(String mensaje) {
		Mensaje = mensaje;
	}
	public String getMensMN() {
		return MensMN;
	}
	public void setMensMN(String mensMN) {
		MensMN = mensMN;
	}
	public String getMensDestino() {
		return mensDestino;
	}
	public void setMensDestino(String mensDestino) {
		this.mensDestino = mensDestino;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestIdSinTimeStamp() {
		return requestIdSinTimeStamp;
	}

	public void setRequestIdSinTimeStamp(String requestIdSinTimeStamp) {
		this.requestIdSinTimeStamp = requestIdSinTimeStamp;
	}
	
	
	
}
