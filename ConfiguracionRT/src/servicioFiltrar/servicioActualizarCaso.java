package servicioFiltrar;

import java.sql.Connection;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interfaces.IDAO;
import properties.ServicioConfiguracion;

@Path("services/servicioActualizarCaso")
public class servicioActualizarCaso {

	//Busco el usuario en la BBDD
    @POST
    @Path("ActualizarCaso")
    @Produces(MediaType.APPLICATION_JSON)
   
    
    public Response guardarCaso(String data) {
    	
    	
    	JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
    	
    	String id = obj.get("id").getAsString(); 
    	String nombreCaso = obj.get("nombreCaso").getAsString();
        String descripcion = obj.get("descripcion").getAsString();  
        String entorno = obj.get("entorno").getAsString(); 
        String operaciones  = obj.get("operaciones").getAsString(); 
        
    	
        try{
            //Realizo la conexion
        	System.out.println("Los datos del caso son:");
        	System.out.println("las operaciones son :" + nombreCaso);
        	System.out.println("las operaciones son :" + descripcion);
        	System.out.println("las operaciones son :" + entorno);
        	System.out.println("las operaciones son :" + operaciones);
        	System.out.println("el id es :" + id);
        	
        	ServicioConfiguracion prop = new ServicioConfiguracion();
        	String tipoDAO = prop.getDAO();
        	IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
        	Connection con =  idao.establecerConexionOracle("DES");
        	
        	idao.actualizarCaso(id, nombreCaso, descripcion, operaciones, con);
        	
        	
            return Response
                    .status(Response.Status.OK)
                    .entity("")
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
