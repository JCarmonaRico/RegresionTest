package servicioFiltrar;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interfaces.IDAO;
import properties.ServicioConfiguracion;
	

	@Path("services/servicioEliminarOpe")
	public class servicioEliminarOpe {
		
		//Busco el caso de prueba en la BBDD
	    @POST
	    @Path("eliminarOpe")
	    @Produces(MediaType.APPLICATION_JSON)
	   
	    public Response eliminarOpe (String data) {
	    	
	    	JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
	    	
	    	String requestId = obj.get("requestId").getAsString();
	       
	                            
	        try{
	            //Realizo la conexion
	        	ServicioConfiguracion prop = new ServicioConfiguracion();
	        	String tipoDAO = prop.getDAO();
	        	IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
	        	Connection con =  idao.establecerConexionOracle("DES");
	        	
	        	System.out.println("El requestId es " + requestId);
	        	idao.borrarEliminarOpe(requestId);
	        	
	            return Response
	                    .status(Response.Status.OK)
	                    .entity(requestId)
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
