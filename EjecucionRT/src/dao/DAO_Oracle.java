package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import excepciones.DAOException;
import interfaces.IDAO;
import properties.CasosPrueba;
import properties.MensajeRecuperado;
import properties.OperacionesConfig;
import properties.ServicioEjecucion;

	public class DAO_Oracle implements IDAO{
		
		private Connection conexion;
		ServicioEjecucion prop = new ServicioEjecucion();
		String s="";
		
		@Override
		public Connection establecerConexionOracle(){
			
			 //Conexión con ORACLE
			try 
	        {	
			
			//Se carga el driver JDBC 
			DriverManager.registerDriver( new oracle.jdbc.OracleDriver()); 
	        //URL "jdbc:oracle:thin:@nombreServidor:numeroPuerto:SID" 
			/*
			//LOCAL
			String url = prop.getDRIVER() + prop.getSERVIDOR() + ":" + prop.getPUERTO() + ":" +  prop.getSID();
			System.out.println("LA CONEXION RESULTADO ES: " + url);
			String usuario = prop.getUSER_BBDD(); 
	        String password = prop.getPASS_BBDD();
	        */
	        
	        //DES
	        String url = prop.getDRIVER() + prop.getDESSERVIDOR() + ":" + prop.getPUERTOENTORNOS() + ":" +  prop.getDESSID();
			System.out.println("LA CONEXION RESULTADO ES: " + url);
			String usuario = prop.getUSERTIBCO(); 
	        String password = prop.getPASSWORDTIBCO();
	        

	        //Obtiene la conexion 
	        conexion = DriverManager.getConnection( url, usuario, password );  
	        
	        }catch( Exception e ){ 
	            e.printStackTrace(); 
	        } 
			return conexion;		
		}
		

		public List<CasosPrueba> findCasosPrueba(
				String nombre,
				String descripcion) {
				
			//Buscamos las operaciones según los valores que nos pasan de la interfaz.
			//creamos la query
			System.out.println(prop.getSENTENCIAFILTRO());
			
			String sentencia =prop.getSENTENCIAFILTRO();	
			//TABLA
			sentencia=sentencia.replace("{0}",prop.getTESTCASE_STATUS());
			//WHERE
			sentencia=sentencia.replace("{1}",prop.getOP_DESCRIPTION());
			sentencia=sentencia.replace("{2}",descripcion);

			ResultSet resultSet = null;
			List<CasosPrueba> casosPrueba = new ArrayList<CasosPrueba>();		
			
			//Añadimos, si existen, los valores de los campos que nos llegan de la interfaz
			if(!nombre.equals("")){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getOP_NAME());
				concatenar=concatenar.replace("{1}",nombre);
				sentencia = sentencia.concat(concatenar);
			}
			
			System.out.println("LA SENTENCIA ES: " + sentencia);
		
			try {
				//preparamos la sentencia
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
				
				while(resultSet.next()){
					CasosPrueba casoPrueba = new CasosPrueba();
					
					casoPrueba.setIdCasoPrueba(resultSet.getString(prop.getOP_ID()));
					casoPrueba.setNombreCasoPrueba(resultSet.getString(prop.getOP_NAME()));
					casoPrueba.setDescripcion(resultSet.getString(prop.getOP_DESCRIPTION()));
					//casoPrueba.setOperaciones(resultSet.getString(prop.getOP_OPERACIONES()));
					
					casosPrueba.add(casoPrueba);
				}
				
				conexion.close();
				return casosPrueba;
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
		}
		
		public List<OperacionesConfig> findOperaciones(
				String[] idOperaciones) {
				
			List<OperacionesConfig> operaciones = new ArrayList<OperacionesConfig>();		
			for (String idOP : idOperaciones) {
				//Buscamos las operaciones según los valores que nos pasan de la interfaz.
				//creamos la query
				System.out.println(prop.getSENTENCIAFILTRO2());
				
				String sentencia =prop.getSENTENCIAFILTRO2();	
				sentencia=sentencia.replace("{0}",prop.getOPERATIONS_STATUS());
				sentencia=sentencia.replace("{1}",prop.getREQUEST_ID());
				sentencia=sentencia.replace("{2}",idOP);
				
				ResultSet resultSet = null;
				System.out.println("LA SENTENCIA ES: " + sentencia);
				
				try {
					//preparamos la sentencia
					Statement stmt = conexion.createStatement();		
					resultSet=stmt.executeQuery(sentencia);
								
					while(resultSet.next()){
						OperacionesConfig ope = new OperacionesConfig();
						
						ope.setidOperacion(resultSet.getString(prop.getREQUEST_ID()));
						ope.setInstrumento(resultSet.getString(prop.getINSTRUMENTO()));
						ope.setAccion(resultSet.getString(prop.getACCION()));
						ope.setOrigen(resultSet.getString(prop.getORIGEN()));
						ope.setDestino(resultSet.getString(prop.getDESTINO()));
						ope.setMensaje(resultSet.getString(prop.getMENSAJE()));
						ope.setMensMN(resultSet.getString(prop.getMENSMN()));
						ope.setMensDestino(resultSet.getString(prop.getMENSDESTINO()));
						ope.setFechaPeticionDesde(resultSet.getString(prop.getFECHADESDE()));
						ope.setFechaPeticionHasta(resultSet.getString(prop.getFECHAHASTA()));
						//ope.setCompID(resultSet.getString(prop.getCOMPID()));
						//ope.setEstado(resultSet.getString(prop.getESTADO()));
						ope.setIdTransaccion(resultSet.getString(prop.getIDTRANSACCION()));
						
						operaciones.add(ope);
					}
					
					conexion.close();
					
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
					
				}
			}
			return operaciones;
		}


		@Override
		public String findRequestID(String idCasoPrueba) {
			// TODO Auto-generated method stub
			//Buscamos las operaciones según los valores que nos pasan de la interfaz.
			//creamos la query
			System.out.println(prop.getSENTENCIAFINDREQUESTIDS());
			
			String sentencia =prop.getSENTENCIAFINDREQUESTIDS();	
			sentencia=sentencia.replace("{0}",prop.getPETICIONES());
			sentencia=sentencia.replace("{1}",prop.getOP_ID());
			sentencia=sentencia.replace("{2}",idCasoPrueba);
			
			ResultSet resultSet = null;
			System.out.println("LA SENTENCIA ES: " + sentencia);
			String requestIDs = "";
			
			try {
				//preparamos la sentencia
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
				
				boolean primerDato = true;
				
				while(resultSet.next()){
					requestIDs = requestIDs + resultSet.getString(prop.getREQUEST_ID());
					requestIDs = requestIDs + ";";
				}
				
				conexion.close();
				
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
			return requestIDs;
		}


		@Override
		public MensajeRecuperado[] recuperarMensajes(String requestIDs) {
			// TODO Auto-generated method stub
			//Buscamos las operaciones según los valores que nos pasan de la interfaz.
			//creamos la query
			
			String[] idSeparados = requestIDs.split(";");
			String idsABuscar = "";
			int tamanio = idSeparados.length;
			MensajeRecuperado[] mensajesRecuperados = new MensajeRecuperado[tamanio];
			
			for (int i = 0; i < idSeparados.length; i++) {
				if(idSeparados.length > 1){//MAS DE 1 REQUESTID
					if(i==0){ //PRIMER REQUESTID
						idsABuscar = "'" + idSeparados[i] + "'" + ",";
					}else if(i==(idSeparados.length)-1){ //ULTIMO REQUESTID
						idsABuscar = idsABuscar + "'" + idSeparados[i] + "'";
					}else{//RESTO
						idsABuscar = idsABuscar + "'" + idSeparados[i] + "'" + ",";
					}
				}else{//SOLO 1 REQUESTID
					idsABuscar = "'" + idSeparados[i] + "'";
				}
				MensajeRecuperado mensajeRecuperado = new MensajeRecuperado();
				//String[] mensajesFinalesRecuperados = new String[tamanio];
				mensajeRecuperado.setRequestID(idSeparados[i]);
				mensajeRecuperado.setMensajeInicial("");
				mensajeRecuperado.setMensajeMN("");
				//mensajeRecuperado.setMensajeFinal(mensajesFinalesRecuperados);
				
				mensajesRecuperados[i] = mensajeRecuperado;
			}
			
			System.out.println("REQUESTID A BUSCAR: "+idsABuscar);
			
			System.out.println(prop.getSENTENCIAFINDMENSAJESTAMANIO());
			
			String sentencia =prop.getSENTENCIAFINDMENSAJESTAMANIO();	
			sentencia=sentencia.replace("{0}",prop.getMENSAJES());
			sentencia=sentencia.replace("{1}",prop.getREQUEST_ID());
			sentencia=sentencia.replace("{2}",idsABuscar);
			
			ResultSet resultSet = null;
			System.out.println("LA SENTENCIA ES: " + sentencia);
			int tamanioQuery;
	
			try {
				//preparamos la sentencia
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
				
				boolean primerDato = true;
				
				resultSet.next();
				tamanioQuery = resultSet.getInt("COUNT(*)");
				
				System.out.println("TAMANIO DEVUELTO: " + tamanioQuery);
				
				System.out.println(prop.getSENTENCIAFINDMENSAJES());
				
				sentencia =prop.getSENTENCIAFINDMENSAJES();	
				sentencia=sentencia.replace("{0}",prop.getMENSAJES());
				sentencia=sentencia.replace("{1}",prop.getREQUEST_ID());
				sentencia=sentencia.replace("{2}",idsABuscar);
				
				resultSet = null;
				System.out.println("LA SENTENCIA ES: " + sentencia);	
				resultSet=stmt.executeQuery(sentencia);
				
				
				while(resultSet.next()){
					for (int j = 0; j < mensajesRecuperados.length; j++) { //Busco la posicion a rellenar de nuestra estructura
						String idMensaje = mensajesRecuperados[j].getRequestID(); //ID de nuestra estructura
						
						String idMensajeRecuperado = resultSet.getString(prop.getREQUEST_ID()); //ID del mensaje recuperado
						String mensajeInicialRecuperado = resultSet.getString(prop.getMENSAJEINICIAL()); //ID del mensaje recuperado
						String mensajeMNRecuperado = resultSet.getString(prop.getMENSAJEMN()); //ID del mensaje recuperado
						String mensajeFinalRecuperado = resultSet.getString(prop.getMENSAJEFINAL()); //ID del mensaje recuperado
						
						System.out.println("ID MENSAJE RECUPERADO: " + idMensajeRecuperado + "MENSAJE INICIAL: " + mensajeInicialRecuperado + "MENSAJE MN: " + mensajeMNRecuperado + "MENSAJE FINAL: " + mensajeFinalRecuperado);	
						
						if(idMensaje.equals(idMensajeRecuperado)){//Comprobaciones para no machacar datos con un null
							if(mensajeInicialRecuperado!=null){
								mensajesRecuperados[j].setMensajeInicial(mensajeInicialRecuperado);
							}else if(mensajeMNRecuperado!=null){
								mensajesRecuperados[j].setMensajeMN(mensajeMNRecuperado);
							}else if(mensajeFinalRecuperado!=null){
								mensajesRecuperados[j].getMensajesFinales().add(mensajeFinalRecuperado);
							}
						}
					}
				}
				
				
				conexion.close();
				
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
			return mensajesRecuperados;
		}
	}