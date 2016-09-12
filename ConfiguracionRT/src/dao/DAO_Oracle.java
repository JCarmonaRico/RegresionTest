package dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import excepciones.DAOException;
import interfaces.IDAO;
import properties.CasosPrueba;
import properties.Operaciones;
import properties.OperacionesConfig;
import properties.ServicioConfiguracion;

	public class DAO_Oracle implements IDAO{
		
		private Connection conexion;
		ServicioConfiguracion prop = new ServicioConfiguracion();
		String s="";
		
		public void borrarEliminarOpe(String requestId){
			
			//CREAMOS LA SENTENCIA PARA RECUPERAR REQUESTID Y BORRAR EN LA TABLA
			String deleteMensajes =prop.getDELELTEMENS();	
			deleteMensajes=deleteMensajes.replace("{0}",requestId);
			String deletePeticiones =prop.getDELETEPET2();
			deletePeticiones=deletePeticiones.replace("{0}",requestId);
			
			System.out.println("la sent es " + deleteMensajes);
			System.out.println("la sent es " + deletePeticiones);
				
			try {			
			Statement stmt = conexion.createStatement();			
			
			//BORRAMOS EN LAS 2 TABLAS
			stmt.executeQuery(deleteMensajes);
			stmt.executeQuery(deletePeticiones);		
			
																		
			} catch (SQLException e) {
			throw new DAOException(e.getMessage());
			}
		}

		
		public List<Operaciones> findOperaciones(String idCaso){
			ResultSet resultSet = null;
			String sentencia =prop.getSELECTPET();	
			sentencia=sentencia.replace("{0}",idCaso);
			
			
			try {
				//PREPARAMOS LA SENTENCIA
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
				List<Operaciones> operaciones = new ArrayList<Operaciones>();					
				
				while(resultSet.next()){
					Operaciones operacion = new Operaciones();
					
					operacion.setId(resultSet.getString(prop.getID()));
					
					operacion.setRequestId(resultSet.getString(prop.getREQUEST_ID()));
					
					operacion.setInstrumento(resultSet.getString(prop.getINSTRUMENT()));
					
					operacion.setOrigen(resultSet.getString(prop.getORIG()));
					
					operaciones.add(operacion);							
				}
				resultSet.close();
				stmt.close();			
				conexion.close();
				return operaciones;
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
				
		}
		
		public List<CasosPrueba> findCasosPrueba(String nombre,String descripcion){
			
			//BUSCAMOS LOS CASOS SEGUN LOS VALORES QUE NOS PASAN.		
			ResultSet resultSet = null;
			
			
			//CREAMOS LA QUERY	
			String sentencia =prop.getSENTENCIABUSCARCASO();	
			sentencia=sentencia.replace("{0}",prop.getTABLACASOS());
			sentencia=sentencia.replace("{1}",prop.getCASO_DESCRIPCION());
			sentencia=sentencia.replace("{2}",descripcion);

							
			//AÑADIMOS, SOLO SI EXISTEN, LOS VALORES QUE NOS VIENEN DE LA INTERFAZ
			if(!nombre.equals("")){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getCASO_NOMBRE());
				concatenar=concatenar.replace("{1}",nombre);
				sentencia = sentencia.concat(concatenar);
			}
			
			System.out.println("LA SENTENCIA ES: " + sentencia);
			try {
				//PREPARAMOS LA SENTENCIA
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
				List<CasosPrueba> casosPrueba = new ArrayList<CasosPrueba>();
						
				while(resultSet.next()){
					CasosPrueba casoPrueba = new CasosPrueba();
					casoPrueba.setIdCasoPrueba(resultSet.getString(prop.getCASO_ID()));
					casoPrueba.setNombreCasoPrueba(resultSet.getString(prop.getCASO_NOMBRE()));
					casoPrueba.setDescripcion(resultSet.getString(prop.getCASO_DESCRIPCION()));
					
					casosPrueba.add(casoPrueba);							
				}
							
				conexion.close();
				return casosPrueba;
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
			
			
		}
		
		public Connection establecerConexionOracle(String entorno){
			
			 //Conexión con ORACLE
			try 
	        {	
			if(entorno.equals(prop.getLOCAL())){
			//Se carga el driver JDBC 
			DriverManager.registerDriver( new oracle.jdbc.OracleDriver()); 
	        //URL "jdbc:oracle:thin:@nombreServidor:numeroPuerto:SID" 
	        String url = prop.getDRIVER() + prop.getSERVIDOR() + ":" + prop.getPUERTO() + ":" +  prop.getSID(); 
	        System.out.println("LA CONEXION CONFIGURACION ES: " + url);
	        //Nombre usuario y password 
	        String usuario = prop.getUSER_BBDD(); 
	        String password = prop.getPASS_BBDD(); 

	        //Obtiene la conexion 
	        conexion = DriverManager.getConnection( url, usuario, password );
	        }		
			else if(entorno.equals(prop.getDES())){
				DriverManager.registerDriver( new oracle.jdbc.OracleDriver());
				String url = prop.getDRIVER() + prop.getDESSERVIDOR() + ":" + prop.getPUERTOENTORNOS() + ":" +  prop.getDESSID();
				System.out.println("LA CONEXION CONFIGURACION ES: " + url);
				String usuario = prop.getUSERTIBCO(); 
		        String password = prop.getPASSWORDTIBCO();
		        conexion = DriverManager.getConnection( url, usuario, password );
			}
			else if(entorno.equals(prop.getPRO())){
				DriverManager.registerDriver( new oracle.jdbc.OracleDriver());
				String url = prop.getDRIVER() + prop.getPROSERVIDOR() + ":" + prop.getPUERTOENTORNOS() + ":" +  prop.getPROSID();
				System.out.println("LA CONEXION CONFIGURACION ES: " + url);
				String usuario = prop.getUSERTIBCO(); 
		        String password = prop.getPASSWORDTIBCO();
		        conexion = DriverManager.getConnection( url, usuario, password );
			}
			else if(entorno.equals(prop.getPRE())){
				DriverManager.registerDriver( new oracle.jdbc.OracleDriver());
				String url = prop.getDRIVER() + prop.getPRESERVIDOR() + ":" + prop.getPUERTOENTORNOS() + ":" +  prop.getPRESID();
				System.out.println("LA CONEXION CONFIGURACION ES: " + url);
				String usuario = prop.getUSERTIBCO(); 
		        String password = prop.getPASSWORDTIBCO();
		        conexion = DriverManager.getConnection( url, usuario, password );
			}
			else if(entorno.equals(prop.getGOS())){
				DriverManager.registerDriver( new oracle.jdbc.OracleDriver());
				String url = prop.getDRIVER() + prop.getGOSSERVIDOR() + ":" + prop.getPUERTOENTORNOS() + ":" +  prop.getGOSSID();
				System.out.println("LA CONEXION CONFIGURACION ES: " + url);
				String usuario = prop.getUSERTIBCO(); 
		        String password = prop.getPASSWORDTIBCO();
		        conexion = DriverManager.getConnection( url, usuario, password );
			}
	            
			
	        }catch( Exception e ){ 
	            e.printStackTrace(); 
	        } 
			return conexion;		
		}

		public List<OperacionesConfig> findOperaciones(
				String idOperacion,
				String estadoPeticion,
				String fechaDesde,
				String idTransaccion,
				String origen,
				String fechaHasta,
				String instrumento,
				String destino,
				Connection conn,
				String accion) {
				
//			
			
			//Buscamos las operaciones según los valores que nos pasan de la interfaz.
			//creamos la query
			System.out.println(prop.getSENTENCIAFILTRO());
			System.out.println("idOperacion" + idOperacion);
			
			String sentencia =prop.getSENTENCIAFILTRO();	
			sentencia=sentencia.replace("{0}",prop.getREQUEST_ID());
			sentencia=sentencia.replace("{1}",prop.getESTADO());
			sentencia=sentencia.replace("{2}",prop.getFECHADESDE());
			//sentencia=sentencia.replace("{3}",prop.getIDTRANSACCION());
			sentencia=sentencia.replace("{4}",prop.getORIGEN());
			sentencia=sentencia.replace("{5}",prop.getFECHAHASTA());
			sentencia=sentencia.replace("{6}",prop.getINSTRUMENTO());
			sentencia=sentencia.replace("{7}",prop.getDESTINO());
			sentencia=sentencia.replace("{8}",prop.getACCION());
			sentencia=sentencia.replace("{9}",prop.getREQUEST_STATUS());
			sentencia=sentencia.replace("{10}",prop.getREQUEST_ID());
			sentencia=sentencia.replace("{11}",idOperacion);
			
			
			List<OperacionesConfig> operaciones = new ArrayList<OperacionesConfig>();		
			
			//Añadimos, si existen, los valores de los campos que nos llegan de la interfaz
			if(instrumento.length() > 0){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getINSTRUMENTO());
				concatenar=concatenar.replace("{1}",instrumento);
				sentencia = sentencia.concat(concatenar);
			}
			
			/*
			if(idOperacion.length() > 0){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getREQUEST_ID());
				concatenar=concatenar.replace("{1}",idOperacion);
				sentencia = sentencia.concat(concatenar);
			}
			*/
			
			if(!origen.equals("")){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getORIGEN());
				concatenar=concatenar.replace("{1}",origen);
				sentencia = sentencia.concat(concatenar);
				
			}
			if(!fechaDesde.equals("") && !fechaDesde.equals("undefined")){
				String concatenar=prop.getCONCATENAR4();
				concatenar=concatenar.replace("{0}",prop.getFECHADESDE());
				concatenar=concatenar.replace("{1}",fechaDesde);
				sentencia = sentencia.concat(concatenar);
			}
			if(!fechaHasta.equals("") && !fechaHasta.equals("undefined")){
				String concatenar=prop.getCONCATENAR5();
				concatenar=concatenar.replace("{0}",prop.getFECHAHASTA());
				concatenar=concatenar.replace("{1}",fechaHasta);
				sentencia = sentencia.concat(concatenar);
			}
			if(accion.length() >0){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getACCION());
				concatenar=concatenar.replace("{1}",accion);
				sentencia = sentencia.concat(concatenar);
			}
			/*if(!idTransaccion.equals("undefined")){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getIDTRANSACCION());
				concatenar=concatenar.replace("{1}",idTransaccion);
				sentencia = sentencia.concat(concatenar);
			}*/
			if(estadoPeticion.length() >0){
				String concatenar=prop.getCONCATENAR();
				concatenar=concatenar.replace("{0}",prop.getESTADO());
				concatenar=concatenar.replace("{1}",estadoPeticion);
				sentencia = sentencia.concat(concatenar);
			}
			if(!destino.equals("")){
				String concatenar=prop.getCONCATENAR2();
				concatenar=concatenar.replace("{0}",prop.getREQUEST_ID());
				concatenar=concatenar.replace("{1}",prop.getREQUEST_ID());
				concatenar=concatenar.replace("{2}",prop.getCOMPONENT_STATUS());
				concatenar=concatenar.replace("{3}",prop.getREQUEST_ID());
				concatenar=concatenar.replace("{4}",prop.getREQUEST_ID());
				concatenar=concatenar.replace("{5}",prop.getDESTINO());
				concatenar=concatenar.replace("{6}",destino);
				sentencia = sentencia.concat(concatenar);								
			}
			String concatenar=prop.getCONCATENAR3();
			concatenar=concatenar.replace("{0}",prop.getFECHADESDE());
			sentencia = sentencia.concat(concatenar);
			
			System.out.println("LA SENTENCIA ES : !!!!!!" + sentencia);
		
			ResultSet resultSet = null;
			
			
			try {
				//preparamos la sentencia
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
							
				while(resultSet.next()){
					OperacionesConfig ope = new OperacionesConfig();
					
					ope.setRequestId(resultSet.getString(prop.getREQUEST_ID()));
					String[] requestIdSinTimeStamp = new String[10];
					requestIdSinTimeStamp = resultSet.getString(prop.getREQUEST_ID()).split("_");
					ope.setRequestIdSinTimeStamp(requestIdSinTimeStamp[0]); // Dado un requestID "xxxxxxxx_mmmmm", la primera parte siempre sera el id sin timestamp
					//ope.setIdTransaccion(resultSet.getString(prop.getIDTRANSACCION()));
					ope.setInstrumento(resultSet.getString(prop.getINSTRUMENTO()));
					ope.setAccion(resultSet.getString(prop.getACCION()));
					ope.setOrigen(resultSet.getString(prop.getORIGEN()));
					//ope.setDestino(resultSet.getString(prop.getDESTINO()));					
					
					if(idOperacion.length() > 0){
						if(ope.getRequestIdSinTimeStamp().contains(idOperacion)){
							operaciones.add(ope);
						}
					}else{
						operaciones.add(ope);
					}
				}
				
				stmt.close();
				conexion.close();
				return operaciones;
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
		}
					
		public List<String> comboInstrumento(){
			ResultSet resultSet = null;
			List<String> operacion = new ArrayList<String>();
			operacion.add("");
			try {
				//preparamos la sentencia
				String sentencia ="SELECT * FROM INSTRUMENTO";
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
				
				while(resultSet.next()){			
					operacion.add(resultSet.getString("INSTRUMENT"));																
				}		
				stmt.close();
				conexion.close();
				return operacion;
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
			
		}
		
			public List<String> comboEntorno(){
					ResultSet resultSet = null;
					List<String> operacion = new ArrayList<String>();
					operacion.add("");
					try {
						//preparamos la sentencia
						String sentencia ="SELECT * FROM ENTORNOS";
						Statement stmt = conexion.createStatement();		
						resultSet=stmt.executeQuery(sentencia);
						
						while(resultSet.next()){			
							operacion.add(resultSet.getString("ENTORNO"));																
						}		
						stmt.close();
						conexion.close();
						return operacion;
					} catch (SQLException e) {
						throw new DAOException(e.getMessage());
						
					}
					
				} 

		
		public List<String> comboOrigen(){

			ResultSet resultSet = null;
			List<String> operacion = new ArrayList<String>();	
			operacion.add("");
			try {
				//preparamos la sentencia
				String sentencia ="SELECT * FROM ORIGEN";
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
							
				while(resultSet.next()){		
					operacion.add(resultSet.getString("ORIGENES"));																	
				}
				stmt.close();
				conexion.close();
				return operacion;
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
			
		}
		
		public List<String> comboDestino(){
			ResultSet resultSet = null;
			List<String> operacion = new ArrayList<String>();	
			operacion.add("");
			try {
				//preparamos la sentencia
				String sentencia ="SELECT * FROM DESTINO";
				Statement stmt = conexion.createStatement();		
				resultSet=stmt.executeQuery(sentencia);
							
				while(resultSet.next()){	
					operacion.add(resultSet.getString("DESTINOS"));
																							
				}	
				stmt.close();
				conexion.close();
				return operacion;
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
				
			}
			
		}
		
		public void guardarCaso(String nomCaso, String descrip, String opes,Connection con){
						 				 	
			try {			
				
				// PARTE EN LA QUE SE HACE EL INSERT DEL NOMBRE Y DESCRIPCION DEL CASO DE PRUEBA
			    String sentencia = prop.getINSERT();
			    sentencia=sentencia.replace("{1}",nomCaso);
			    sentencia=sentencia.replace("{2}",descrip);
			    
				PreparedStatement stmt = con.prepareStatement(sentencia);
				stmt.executeUpdate();
				stmt.close();	
							
				
				// SELECCIONAR EL ID DE CASOSPRUEBA
				String  ID=null;			
				ResultSet rs = null;	
				String sentencia3 = prop.getSELECTID();
				Statement stmt3 = conexion.createStatement();
				rs=stmt3.executeQuery(sentencia3);
				
				while(rs.next()){				
				 ID = rs.getString("MAX(ID)");
				}
				
				
				
				// VARIABLES 			
				String REQUEST_ID = null;
				String sentenciaExisteMensaje=null;
				String sentenciaMensIni=null;
				String sentenciaMensMN=null; 
				String sentenciaMensFin=null;
				
				
				
				// RECORTAMOS EL STRING OPES QUE CONTIENE LAS DIFERENTES OPERACIONES
				StringTokenizer st = new StringTokenizer(opes,";");
				while (st.hasMoreTokens()){
					String sentencia2 = prop.getINSERT2();	
					sentencia2=sentencia2.replace("{0}",ID);
					
					REQUEST_ID=st.nextToken();
				    sentencia2=sentencia2.replace("{1}",REQUEST_ID);
				    
				    // COMPROBAMOS SI EXISTE MENSAJE PARA ESTE REQUESTID
				    sentenciaExisteMensaje = prop.getCOMPROBACIONMENSAJE();	    
				    sentenciaExisteMensaje=sentenciaExisteMensaje.replace("{0}",REQUEST_ID);
				    
				    ResultSet resultExisteMensaje = null;
					Statement stmtMensaje = conexion.createStatement();
					resultExisteMensaje=stmtMensaje.executeQuery(sentenciaExisteMensaje);	
					
					resultExisteMensaje.next();
					int numeroMensajes = resultExisteMensaje.getInt("COUNT(*)");
					
					
					if(numeroMensajes == 0){
			    		// RECUPERAMOS MENSAJE INICIAL
					    sentenciaMensIni = prop.getSENTENCIAMENSINI();	    
						sentenciaMensIni=sentenciaMensIni.replace("{0}",REQUEST_ID);
									
						ResultSet resultSetIni = null;
						Statement stmt5 = conexion.createStatement();
						resultSetIni=stmt5.executeQuery(sentenciaMensIni);				
				
						String mensajeInicial=null;
						String sentMensInicial=null;
						String compId=null;
						
						// INSERTAMOS MENSAJE INICIAL
						while(resultSetIni.next()){				
							 mensajeInicial = resultSetIni.getString("MESSAGE");
							 compId = resultSetIni.getString("COMPID");
							 sentMensInicial = prop.getINSERTMENSINI();
							 //sentMensInicial=sentMensInicial.replace("{0}","?");
							 sentMensInicial=sentMensInicial.replace("{0}",compId);
							 sentMensInicial=sentMensInicial.replace("{1}",REQUEST_ID);
							 
							PreparedStatement statement = con.prepareStatement(sentMensInicial);
							System.out.println("hasta aquiiii " + sentMensInicial);
							System.out.println("mensaje inicial " + mensajeInicial);
							System.out.println("longitud mensaje inicial " + mensajeInicial.length());
							statement.setString(1,mensajeInicial);
							statement.executeUpdate();
							System.out.println("hasta aco");
							statement.close();	
						}
						

						
						// RECUPERAMOS MENSAJE MODELO NEUTRO
						sentenciaMensMN = prop.getSENTENCIAMENSMN();				
						sentenciaMensMN=sentenciaMensMN.replace("{0}",REQUEST_ID);
										
						ResultSet resultSetMN = null;
						Statement stmt6 = conexion.createStatement();
						resultSetMN=stmt6.executeQuery(sentenciaMensMN);	
						
						System.out.println("La sentencia es : "+ sentenciaMensMN);
						String mensajeMN=null;
						String sentMensMN=null;
						
						// INSERTAMOS MENSAJE MODELO NEUTRO
						while(resultSetMN.next()){				
							 mensajeMN = resultSetMN.getString("MESSAGE");
							 compId = resultSetMN.getString("COMPID");
							 sentMensMN = prop.getINSERTMENSMN();
							 //sentMensMN=sentMensMN.replace("{0}","?");
							 sentMensMN=sentMensMN.replace("{0}",compId);
							 sentMensMN=sentMensMN.replace("{1}",REQUEST_ID);
							 
							 PreparedStatement statementMN = con.prepareStatement(sentMensMN);
							statementMN.setString(1,mensajeMN);
							statementMN.executeUpdate();
							statementMN.close();
						}
						
						
						// RECUPERAMOS MENSAJE FINAL 
						sentenciaMensFin = prop.getSENTENCIAMENSFIN();
						sentenciaMensFin=sentenciaMensFin.replace("{0}",REQUEST_ID);
									
						ResultSet resultSetFin = null;
						String sentMensFin=null;
						Statement stmt7 = conexion.createStatement();
						resultSetFin=stmt7.executeQuery(sentenciaMensFin);	
		
						System.out.println("La sentencia es : "+ sentenciaMensFin);
						String mensajeFin=null;
						
						// INSERTAMOS MENSAJE FINAL
						while(resultSetFin.next()){				
							 mensajeFin = resultSetFin.getString("MESSAGE");
							 compId = resultSetFin.getString("COMPID");
							 sentMensFin = prop.getINSERTMENSFIN();
							 //sentMensFin=sentMensFin.replace("{0}","?");
							 sentMensFin=sentMensFin.replace("{0}",compId);
							 sentMensFin=sentMensFin.replace("{1}",REQUEST_ID);
							 
							 PreparedStatement statementFin = con.prepareStatement(sentMensFin);
							statementFin.setString(1,mensajeFin);
							statementFin.executeUpdate();
							statementFin.close();	
						}
						
					}		
					
				    sentencia2=sentencia2.replace("{2}",st.nextToken());
				    sentencia2=sentencia2.replace("{3}",st.nextToken());
				    
					PreparedStatement stmt2 = con.prepareStatement(sentencia2);
					stmt2.executeUpdate();
					stmt2.close();		
							
				
				}					
			
								
				con.close();
				}catch (SQLException e) {
				throw new DAOException(e.getMessage());				
			}
			
		}
	
		public void borrarCasosPrueba(String Id){
			
			//CREAMOS LAS SENTENCIAS PARA RECUPERAR REQUESTID Y BORRAR EN LAS 3 TABLAS
			String selectRequestId =prop.getSELECTREQUEST();
			selectRequestId=selectRequestId.replace("{0}",Id);
			String deleteCaso =prop.getDELETECASO();
			deleteCaso=deleteCaso.replace("{0}",Id);
			String deletePeticiones =prop.getDELETEPET();
			deletePeticiones=deletePeticiones.replace("{0}",Id);
				
			//OBTENEMOS EL REQUESTID PARA PODER BORRAR LOS MENSAJES DEL CASO
			try {
			ResultSet resultSet = null;
			Statement stmt = conexion.createStatement();
			resultSet=stmt.executeQuery(selectRequestId);
			String requestId=null;
			while(resultSet.next()){
				requestId=resultSet.getString(prop.getREQUEST_ID());
			}
			
			String deleteMensajes =prop.getDELELTEMENS();	
			deleteMensajes=deleteMensajes.replace("{0}",requestId);
			
			//BORRAMOS EN LAS 3 TABLAS
			stmt.executeQuery(deleteMensajes);
			stmt.executeQuery(deletePeticiones);
			stmt.executeQuery(deleteCaso);			
			
																		
			} catch (SQLException e) {
			throw new DAOException(e.getMessage());
			}
		}


		@Override
		public void actualizarCaso(String id, String nombreCaso, String descripcion, String operaciones,
				Connection con) {
			try {			
				
				// VARIABLES 			
				String REQUEST_ID = null;
				String sentenciaMensIni=null;
				String sentenciaMensMN=null; 
				String sentenciaMensFin=null;
				
				
				
				// RECORTAMOS EL STRING OPES QUE CONTIENE LAS DIFERENTES OPERACIONES
				StringTokenizer stAux = new StringTokenizer(operaciones,";");
				List<Operaciones> operacionesFront = new ArrayList<Operaciones>();	
				while (stAux.hasMoreTokens()){
					Operaciones operacionFront = new Operaciones();
					operacionFront.setId(id);
					String requestId = stAux.nextToken();
					operacionFront.setRequestId(requestId);
					String instrumento = stAux.nextToken();
					operacionFront.setInstrumento(instrumento);
					String origen = stAux.nextToken();
					operacionFront.setOrigen(origen);
					
					operacionesFront.add(operacionFront);
				}
				
				StringTokenizer st = new StringTokenizer(operaciones,";");
				while (st.hasMoreTokens()){
					
					REQUEST_ID=st.nextToken();
					System.out.println("COGEMOS REQUESTID: " + REQUEST_ID);
					
		    		// RECUPERAMOS MENSAJE INICIAL
				    sentenciaMensIni = prop.getSENTENCIAMENSINI();	    
					sentenciaMensIni=sentenciaMensIni.replace("{0}",REQUEST_ID);
								
					ResultSet resultSetIni = null;
					Statement stmt5 = conexion.createStatement();
					resultSetIni=stmt5.executeQuery(sentenciaMensIni);	
					
					System.out.println("SETENCIA RECUPERACION MENSAJE INICIAL : "+ sentenciaMensIni);
			
					String sentenciaExisteMensaje=null;
					String mensajeInicial=null;
					String sentMensInicial=null;
					String compId=null;
					
					
					// COMPROBAMOS SI EXISTE MENSAJE PARA ESTE REQUESTID
				    sentenciaExisteMensaje = prop.getCOMPROBACIONMENSAJE();	    
				    sentenciaExisteMensaje=sentenciaExisteMensaje.replace("{0}",REQUEST_ID);
				    
				    ResultSet resultExisteMensaje = null;
					Statement stmtMensaje = conexion.createStatement();
					resultExisteMensaje=stmtMensaje.executeQuery(sentenciaExisteMensaje);	
					
					resultExisteMensaje.next();
					int numeroMensajes = resultExisteMensaje.getInt("COUNT(*)");
					
					
					if(numeroMensajes == 0){
						// INSERTAMOS MENSAJE INICIAL
						while(resultSetIni.next()){				
							 mensajeInicial = resultSetIni.getString("MESSAGE");
							 compId = resultSetIni.getString("COMPID");
							 sentMensInicial = prop.getINSERTMENSINI();
							 //sentMensInicial=sentMensInicial.replace("{0}","?");
							 sentMensInicial=sentMensInicial.replace("{0}",compId);
							 sentMensInicial=sentMensInicial.replace("{1}",REQUEST_ID);
							 
							 PreparedStatement statement = con.prepareStatement(sentMensInicial);
							System.out.println("SENTENCIA INSERT MENSAJE INICIAL: " + sentMensInicial);
							//System.out.println("mensaje inicial " + mensajeInicial);
							//System.out.println("longitud mensaje inicial " + mensajeInicial.length());
							statement.setString(1,mensajeInicial);
							statement.executeUpdate();
							statement.close();	
							}
						
						
						
						// RECUPERAMOS MENSAJE MODELO NEUTRO
						sentenciaMensMN = prop.getSENTENCIAMENSMN();				
						sentenciaMensMN=sentenciaMensMN.replace("{0}",REQUEST_ID);
										
						ResultSet resultSetMN = null;
						Statement stmt6 = conexion.createStatement();
						resultSetMN=stmt6.executeQuery(sentenciaMensMN);	
						
						System.out.println("SETENCIA RECUPERACION MENSAJE NEUTRO : "+ sentenciaMensMN);
						String mensajeMN=null;
						String sentMensMN=null;
						
						// INSERTAMOS MENSAJE MODELO NEUTRO
						while(resultSetMN.next()){				
							 mensajeMN = resultSetMN.getString("MESSAGE");
							 compId = resultSetMN.getString("COMPID");
							 sentMensMN = prop.getINSERTMENSMN();
							 //sentMensMN=sentMensMN.replace("{0}","?");
							 sentMensMN=sentMensMN.replace("{0}",compId);
							 sentMensMN=sentMensMN.replace("{1}",REQUEST_ID);
							 
							 System.out.println("SENTENCIA INSERT MENSAJE NEUTRO : "+ sentMensMN);
							 PreparedStatement statementMN = con.prepareStatement(sentMensMN);
							statementMN.setString(1,mensajeMN);
							statementMN.executeUpdate();
							statementMN.close();
							}
						
						
						// RECUPERAMOS MENSAJE FINAL 
						sentenciaMensFin = prop.getSENTENCIAMENSFIN();
						sentenciaMensFin=sentenciaMensFin.replace("{0}",REQUEST_ID);
									
						ResultSet resultSetFin = null;
						String sentMensFin=null;
						Statement stmt7 = conexion.createStatement();
						resultSetFin=stmt7.executeQuery(sentenciaMensFin);	
		
						System.out.println("SENTENCIA RECUPERACION MENSAJE FINAL : "+ sentenciaMensFin);
						String mensajeFin=null;
						
						// INSERTAMOS MENSAJE FINAL
						while(resultSetFin.next()){				
							 mensajeFin = resultSetFin.getString("MESSAGE");
							 compId = resultSetFin.getString("COMPID");
							 sentMensFin = prop.getINSERTMENSFIN();
							 //sentMensFin=sentMensFin.replace("{0}","?");
							 sentMensFin=sentMensFin.replace("{0}",compId);
							 sentMensFin=sentMensFin.replace("{1}",REQUEST_ID);
							 
							 System.out.println("SENTENCIA INSERT MENSAJE FINAL : "+ sentMensFin);
							 PreparedStatement statementFin = con.prepareStatement(sentMensFin);
							statementFin.setString(1,mensajeFin);
							statementFin.executeUpdate();
							statementFin.close();	
							}
					}	
						
						
						
					String recuperarDatosBack = prop.getSELECCIONARDISTINTOS();	
					recuperarDatosBack=recuperarDatosBack.replace("{0}",id);
					
					ResultSet resultSetBack = null;
					Statement distintos = conexion.createStatement();
					resultSetBack=distintos.executeQuery(recuperarDatosBack);	
					
					List<String> requestIDsBack = new ArrayList<String>();		
					
					while(resultSetBack.next()){				
						requestIDsBack.add(resultSetBack.getString("REQUESTID"));
					}
					
					boolean esNuevo = false;
					boolean esNuevo2 = false;
				
					for (Operaciones operacionFront : operacionesFront) {
						esNuevo = true;
						for (String requestIDBack : requestIDsBack) {
							if(operacionFront.getRequestId().equals(requestIDBack)){
								esNuevo = false;
							}
						}
						if(esNuevo){
							// NUEVOS
							String sentencia2 = prop.getINSERT2();	
							sentencia2=sentencia2.replace("{0}",id);
						    sentencia2=sentencia2.replace("{1}",operacionFront.getRequestId());
						    sentencia2=sentencia2.replace("{2}",operacionFront.getInstrumento());
						    sentencia2=sentencia2.replace("{3}",operacionFront.getOrigen());
						    
							PreparedStatement stmt2 = con.prepareStatement(sentencia2);
							
							System.out.println("NUEVO ENCONTRADO!! SENTENCIA: " + sentencia2);
							stmt2.executeUpdate();
							stmt2.close();	
							
							esNuevo2 = true;
						}else{
							/*
							REQUEST_ID=st.nextToken();
							System.out.println("SALTAMOS: " + REQUEST_ID);
							REQUEST_ID=st.nextToken();
							System.out.println("SALTAMOS: " + REQUEST_ID);
							REQUEST_ID=st.nextToken();
							System.out.println("SALTAMOS: " + REQUEST_ID);
							*/
						}
					}
					
					boolean esViejo = false;
					boolean esViejo2 = false;
					
					for (String requestIDBack : requestIDsBack) {
						esViejo = true;
						for (Operaciones operacionFront : operacionesFront) {
							if(requestIDBack.equals(operacionFront.getRequestId())){
								esViejo = false;
							}
						}
						if(esViejo){
							//VIEJOS
							String sentenciaEliminarViejos = prop.getSENTENCIAELIMINARVIEJOS();	
							sentenciaEliminarViejos=sentenciaEliminarViejos.replace("{0}",id);
							sentenciaEliminarViejos=sentenciaEliminarViejos.replace("{1}",requestIDBack);
							
							PreparedStatement stmtViejo = con.prepareStatement(sentenciaEliminarViejos);
							System.out.println("VIEJO ENCONTRADO!! SENTENCIA: " + sentenciaEliminarViejos);
							stmtViejo.executeUpdate();
							stmtViejo.close();	
/*
							REQUEST_ID=st.nextToken();
							System.out.println("SALTAMOS: " + REQUEST_ID);
							REQUEST_ID=st.nextToken();
							System.out.println("SALTAMOS: " + REQUEST_ID);
							
							esViejo2 = true;
							*/
						}
					}
					
					/*
					// NUEVOS
					String sentencia2 = prop.getINSERT2();	
					sentencia2=sentencia2.replace("{0}",id);
				    sentencia2=sentencia2.replace("{1}",REQUEST_ID);
				    sentencia2=sentencia2.replace("{2}",st.nextToken());
				    sentencia2=sentencia2.replace("{3}",st.nextToken());
				    
					PreparedStatement stmt2 = con.prepareStatement(sentencia2);
					stmt2.executeUpdate();
					stmt2.close();		
					*/
					
					/*
					if(!esNuevo2 && !esViejo2){
						System.out.println("COMUN");
						REQUEST_ID=st.nextToken();
						REQUEST_ID=st.nextToken();
					}
					*/
					REQUEST_ID=st.nextToken();
					REQUEST_ID=st.nextToken();
					
				}	
				
				
				
								
				con.close();
				}catch (SQLException e) {
				throw new DAOException(e.getMessage());				
			}
			
		}

	}