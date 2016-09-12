package properties;

import java.io.Serializable;

public class Operaciones implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String requestId;
	private String requestIdSinTimeStamp;
	private String instrumento;
	private String origen;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getInstrumento() {
		return instrumento;
	}
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}
	public String getRequestIdSinTimeStamp() {
		return requestIdSinTimeStamp;
	}
	public void setRequestIdSinTimeStamp(String requestIdSinTimeStamp) {
		this.requestIdSinTimeStamp = requestIdSinTimeStamp;
	}

	
}
