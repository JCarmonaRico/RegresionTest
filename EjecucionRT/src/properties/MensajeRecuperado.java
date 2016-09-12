package properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MensajeRecuperado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String requestID;
	private String mensajeInicial;
	private String mensajeMN;
	private List<String> mensajesFinales = new ArrayList<String>();
	
	public String getMensajeInicial() {
		return mensajeInicial;
	}
	public void setMensajeInicial(String mensajeInicial) {
		this.mensajeInicial = mensajeInicial;
	}
	public String getMensajeMN() {
		return mensajeMN;
	}
	public void setMensajeMN(String mensajeMN) {
		this.mensajeMN = mensajeMN;
	}
	
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public List<String> getMensajesFinales() {
		return mensajesFinales;
	}
	public void setMensajesFinales(List<String> mensajesFinales) {
		this.mensajesFinales = mensajesFinales;
	}
		
}