package interfaces;

import java.sql.Connection;
import java.util.List;

import properties.CasosPrueba;
import properties.MensajeRecuperado;
import properties.OperacionesConfig;

public interface IDAO {
	public Connection establecerConexionOracle();

	public List<CasosPrueba> findCasosPrueba(
		String nombre,
		String descripcion);
	
	public List<OperacionesConfig> findOperaciones(
			String[] idOperaciones);

	public String findRequestID(String idCasoPrueba);

	public MensajeRecuperado[] recuperarMensajes(String requestIDs);
}