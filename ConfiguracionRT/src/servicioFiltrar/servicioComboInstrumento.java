package servicioFiltrar;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import interfaces.IDAO;
import properties.ServicioConfiguracion;



@Path("services/servicioComboInstrumento")
public class servicioComboInstrumento {
	
	

	 
	//Busco el usuario en la BBDD
    @GET
    @Path("instrumento")
    @Produces(MediaType.APPLICATION_JSON)
   
    
    public Response comboInstrumento(){
                            
        try{
            //Realizo la conexion
        	ServicioConfiguracion prop = new ServicioConfiguracion();
        	String tipoDAO = prop.getDAO();
        	IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
        	String Local = "DES";
        	Connection con =  idao.establecerConexionOracle(Local);
        	
        	List<String> operacion = new ArrayList<String>();
        	operacion=idao.comboInstrumento();
        	
       	
            return Response
                    .status(Response.Status.OK)
                    .entity(operacion)
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
