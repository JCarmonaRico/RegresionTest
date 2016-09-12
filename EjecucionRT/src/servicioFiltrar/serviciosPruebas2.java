package servicioFiltrar;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.jms.*;

//import com.acc.regresiontest.com.domains.Resultados;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interfaces.IDAO;
import properties.CasosPrueba;
import properties.OperacionesConfig;
import properties.ServicioEjecucion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Path("services/serviciosPruebas2")
public class serviciosPruebas2 {
	
	 // Defines the JNDI context factory.
	 public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

	 // Defines the JMS context factory.
	 public final static String JMS_FACTORY="jms/TestConnectionFactory";

	 // Defines the queue.
	 public final static String QUEUE="prueba.queue";

	 private QueueConnectionFactory qconFactory;
	 private QueueConnection qcon;
	 private QueueSession qsession;
	 private QueueSender qsender;
	 private Queue queue;
	 private Topic topic;
	 private TextMessage msg;
	
	//Busco el caso de prueba en la BBDD
    @POST
    @Path("buscarCasosPrueba2")
    @Produces(MediaType.APPLICATION_JSON)
   
    public Response buscarCasoPrueba (String data) {
    	
    	JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
    	
    	String name = obj.get("nombre").getAsString();
        String descripcion = obj.get("descripcion").getAsString();  
                            
        try{
            //Realizo la conexion
        	ServicioEjecucion prop = new ServicioEjecucion();
        	String tipoDAO = prop.getDAO();
        	IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
        	Connection con =  idao.establecerConexionOracle();
        	System.out.println("La conexion es ..." +con);
        	
        	List<CasosPrueba> operaciones = new ArrayList<CasosPrueba>();
        	operaciones=idao.findCasosPrueba(
        			name,
        			descripcion);
        	
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
    
    //Ejecuto el caso de prueba
    @POST
    @Path("ejecutarCasoPrueba2")
    @Produces(MediaType.APPLICATION_JSON)
   
    public Response ejecutarCasoPrueba (String data) {
        
        /*-----------------------------------------------------------------------
         * QUEUES
         *  murex.export.insert.security              
        	murex.export.mxml.counterpart.queue 
        	murex.export.mxml.spotforward.queue
			murex.export.mxml.fxswap.queue
			murex.export.mxml.bsb.queue
			murex.export.mxml.repo.queue
			sibis.export.mqstring.forex.queue
			murex.export.mensajeria.bloqueo.queue
			murex.export.mensajeria.swift.queue
			murex.export.mxml.ccyirsdeals.queue
			murex.export.mxml.depoloan.queue
			murex.export.mxml.fradeals.queue
			murex.export.mensajeria.liquidaciones.queue
			murex.export.mensajeria.suc.queue
			murex.export.mxml.swaptiondeals.queue
			murex.export.mxml.inflationswaps.queue
			murex.export.mxml.irsdeals.queue
			murex.export.mxml.capfloors.queue
			murex.export.mxml.bond.queue
			murex.export.mxml.paper.queue
			murex.export.mxml.simplecashflow.queue
			murex.export.mxml.creditindexdeals.queue	
         *----------------------------------------------------------------------*/
    	JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
    	
    	String idOperacionesSinTratar = obj.get("operaciones").getAsString();  
                            
        try{
            //Realizo la conexion
        	ServicioEjecucion prop = new ServicioEjecucion();
        	String tipoDAO = prop.getDAO();
        	IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
        	Connection con =  idao.establecerConexionOracle();
        	System.out.println("La conexion es ..." +con);
        	
        	List<OperacionesConfig> operaciones = new ArrayList<OperacionesConfig>();
        	String[] idOperaciones = idOperacionesSinTratar.split(";");
        	operaciones=idao.findOperaciones(
    				idOperaciones);
        	
    	    InitialContext ic = getInitialContext("tcp://localhost:7222");
    	    serviciosPruebas2 qs = new serviciosPruebas2();
		    qs.init(ic, QUEUE);
		    qs.send("mensaje");
		    qs.close();
        	 
        	
        	// TENGO LAS OPERACIONES COMPLETAS CON SUS MENSAJES, LANZAR LA PRUEBA!!
        	for (OperacionesConfig operacion : operaciones) {
        		/*
				String mensaje = cp.getOperaciones().get(i).getMensaje().toString();
				
				
				if (cp.getOperaciones().get(i).getCola().equals("MLC.ENVIA")){
					//Base64.Encoder encoder = Base64.getEncoder();
					//mensaje =  encoder.encodeToString(mensaje.getBytes(StandardCharsets.UTF_8));
				}
				
				String toTibco[] = { mensaje };
//				TibjmsQueueSender tibjmsQueueSender = new TibjmsQueueSender(toTibco,
//						cp.getOperaciones().get(i).getCola(), url);
				
				json = bc.CompareXML(xmlAntiguo, xmlTibco);
				for(int j = 0; j<cp.getOperaciones().get(i).getMensajeDestino().size(); j++){
					nombre = cp.getOperaciones().get(i).getIdPeticion()+"_"+cp.getOperaciones().get(i).getMensajeDestino().get(j).getNombreDestino();
					Resultados result = new Resultados(nombre,json);
					resultOperaciones.add(result);
				}
				*/
			}
        	
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
    
    /**
     * Creates all the necessary objects for sending
     * messages to a JMS queue.
     *
     * @param ctx JNDI initial context
     * @param queueName name of queue
     * @exception NamingException if operation cannot be performed
     * @exception JMSException if JMS fails to initialize due to internal error
     */
    public void init(Context ctx, String queueName)
       throws NamingException, JMSException
    {
       qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
       qcon = qconFactory.createQueueConnection();
       qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
       queue = (Queue) ctx.lookup(queueName);
       qsender = qsession.createSender(queue);
       msg = qsession.createTextMessage();
       qcon.start();
    }

    /**
     * Sends a message to a JMS queue.
     *
     * @param message  message to be sent
     * @exception JMSException if JMS fails to send message due to internal error
     */
    public void send(String message) throws JMSException {
       msg.setText(message);
       qsender.send(msg);
    }

    /**
     * Closes JMS objects.
     * @exception JMSException if JMS fails to close objects due to internal error
     */
    public void close() throws JMSException {
       qsender.close();
       qsession.close();
       qcon.close();
    }
    
    private static InitialContext getInitialContext(String url)
    	    throws NamingException
    	 {
    	    Hashtable env = new Hashtable();
    	    env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
    	    env.put(Context.PROVIDER_URL, url);
    	    return new InitialContext(env);
    	 }
    
}
