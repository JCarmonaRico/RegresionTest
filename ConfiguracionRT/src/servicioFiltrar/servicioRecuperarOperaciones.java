package servicioFiltrar;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interfaces.IDAO;
import properties.Operaciones;
import properties.ServicioConfiguracion;
	

	@Path("services/servicioRecuperarOperaciones")
	public class servicioRecuperarOperaciones {
		
		//Busco el caso de prueba en la BBDD
	    @POST
	    @Path("recuperarOperaciones")
	    @Produces(MediaType.APPLICATION_JSON)
	   
	    public Response recuperarOperaciones (String data) {
	    	
	    	JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
	    	
	    	String idCaso = obj.get("Id").getAsString();
	        
	                            
	        try{
	            //Realizo la conexion
	        	ServicioConfiguracion prop = new ServicioConfiguracion();
	        	String tipoDAO = prop.getDAO();
	        	IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
	        	Connection con =  idao.establecerConexionOracle("DES");
	        	System.out.println("La conexion es ..." +con);
	        	
	        	List<Operaciones> operaciones = new ArrayList<Operaciones>();
	        	operaciones=idao.findOperaciones(idCaso);
	        	
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
