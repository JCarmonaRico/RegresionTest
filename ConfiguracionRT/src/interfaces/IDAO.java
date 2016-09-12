package interfaces;

import java.sql.Connection;
import java.util.List;

import properties.CasosPrueba;
import properties.Operaciones;
import properties.OperacionesConfig;

public interface IDAO {
	
	public void borrarEliminarOpe(String requestId);
	
	public void borrarCasosPrueba(String Id);
	
	public Connection establecerConexionOracle(String entorno);

	public List<OperacionesConfig> findOperaciones(
		String idOperacion,
		String estadoPeticion,
		String fechaDesde,
		String idTransaccion,
		String origen,
		String fechaHasta,
		String instrumento,
		String destino,
		Connection con,
		String accion);
	
	public List<String> comboInstrumento();
	
	public List<String> comboDestino();
	
	public List<String> comboOrigen();
	
	public void guardarCaso(String nomCaso, String descrip, String opes,Connection con);
	
	public List<CasosPrueba> findCasosPrueba(String nombre,String descripcion);

	public List<Operaciones> findOperaciones(String idCaso);

	public void actualizarCaso(String id, String nombreCaso, String descripcion, String operaciones, Connection con);

	public List<String> comboEntorno();
}