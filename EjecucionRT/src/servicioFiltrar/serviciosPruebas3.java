package servicioFiltrar;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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

@Path("services/serviciosPruebas3")
public class serviciosPruebas3 {
	
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
        	
        	String serverUrl = "tcp://localhost:7222";
    		String userName = "";
    		String password = "";
     
    		String queueName = "queue.Sample";

    		// Objetos a obtener para usar JMS: 
    		// - TopicConnectionFactory
    		// - TopicConection
    		// - Topic
    		// - TopicSession
    		// - TopicSubscriber
    		// - TopicPublisher
    		TopicConnectionFactory connectionFactory = new com.tibco.tibjms.TibjmsTopicConnectionFactory(serverUrl);
    		TopicConnection connection = connectionFactory.createTopicConnection();
    		
    		// Obtener el Topic en el cual se publicarán y del cual se recibirán los mensajes
    		/*
    		javax.jms.Topic topic = (javax.jms.Topic) ic.lookup("prueba2.topic");

    		// Preparar el publicador y subscriptor al Topic
    		Subscriber subscriber1 = new Subscriber(connection, topic);
    		Subscriber subscriber2 = new Subscriber(connection, topic);
    		Publisher publisher = new Publisher(connection, topic);
        	 */
        	
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
    /*
private static class Subscriber implements MessageListener {
		
		private TopicSession session;
		private TopicSubscriber subscriber;
		
		public Subscriber(TopicConnection connection, javax.jms.Topic topic) throws Exception {
			this.session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			this.subscriber = this.session.createSubscriber(topic, null, false);
			this.subscriber.setMessageListener(this);
		}
		
		public void close() throws Exception  {
			subscriber.close();
			session.close();
		}
		
		@Override
		public void onMessage(Message message) {
			try {
				TextMessage text = (TextMessage) message;
				System.out.printf("Suscriptor (%s): El publicador dice: «%s»\n", this, text.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class Publisher implements Runnable {
		
		private TopicSession session;
		private TopicPublisher publisher;
		
		public Publisher(TopicConnection connection, javax.jms.Topic topic) throws Exception {
			this.session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			this.publisher = this.session.createPublisher(topic);
		}
		
		public void close() throws Exception  {
			publisher.close();
			session.close();
		}
		
		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; ++i) {
					Message mensaje = session.createTextMessage(String.format("¡Hola mundo! (%d)", i));
					publisher.publish(mensaje);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/
    
}
