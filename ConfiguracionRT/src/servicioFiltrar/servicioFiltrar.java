package servicioFiltrar;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interfaces.IDAO;
import properties.OperacionesConfig;
import properties.ServicioConfiguracion;



@Path("services/servicioFiltrar")
public class servicioFiltrar {
	
	

	 
	//Busco el usuario en la BBDD
    @POST
    @Path("Filtrar")
    @Produces(MediaType.APPLICATION_JSON)
   
    
    public Response alta (String data) {
    	
    	JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
    	
    	String idOperacion = obj.get("idOperacion").getAsString();
        String estadoPeticion = obj.get("estadoPeticion").getAsString();  
        String fechaDesde = obj.get("fechaPeticionDesde").getAsString();
        String idTransaccion = obj.get("idTransaccion").getAsString();
        String origen = obj.get("origen").getAsString();
        String fechaHasta = obj.get("fechaPeticionHasta").getAsString();
        String instrumento = obj.get("instrumento").getAsString();
        String destino = obj.get("destino").getAsString();
        String entorno = obj.get("entorno").getAsString();
        String accion = obj.get("accion").getAsString();
      
        try{
            //Realizo la conexion
        	ServicioConfiguracion prop = new ServicioConfiguracion();
        	String tipoDAO = prop.getDAO();
        	IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
        	Connection con =  idao.establecerConexionOracle(entorno);
        
        	List<OperacionesConfig> operaciones = new ArrayList<OperacionesConfig>();
        	operaciones=idao.findOperaciones(
        			idOperacion,
        			estadoPeticion, 
        			fechaDesde, 
        			idTransaccion, 
        			origen, 
        			fechaHasta, 
        			instrumento, 
        			destino, 
        			con, 
        			accion);
        	
            return Response
                    .status(Response.Status.OK)
                    .entity(operaciones)
                    .build();
            
        }catch(Exception e){
           System.out.println(e);
         
        }    
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("ERROR")
                .build();
      
    }


}
