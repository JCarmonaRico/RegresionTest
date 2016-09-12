package properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MensajesEnviados implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String mensajeID;
	private String textoEnviado;
	private String textoSeEsperaRecibir;
	
	public String getMensajeID() {
		return mensajeID;
	}
	public void setMensajeID(String mensajeID) {
		this.mensajeID = mensajeID;
	}
	public String getTextoEnviado() {
		return textoEnviado;
	}
	public void setTextoEnviado(String textoEnviado) {
		this.textoEnviado = textoEnviado;
	}
	public String getTextoSeEsperaRecibir() {
		return textoSeEsperaRecibir;
	}
	public void setTextoSeEsperaRecibir(String textoSeEsperaRecibir) {
		this.textoSeEsperaRecibir = textoSeEsperaRecibir;
	}

}