package properties;

import java.io.IOException;
import java.util.Properties;

import config.CTES_CONFIGURACION;

public class ServicioEjecucion {

	//Datos conexion
	private String PUERTO;
	private String DRIVER;
	private String SID;
	private String SERVIDOR;
	private String DAO;
	private String USER_BBDD;
	private String PASS_BBDD;
	
	private String TESTCASE_STATUS;
	private String OP_ID;
	private String OP_NAME;
	private String OP_DESCRIPTION;
	private String OP_OPERACIONES;
	
	private String PETICIONES;
	private String MENSAJES;
	
	private String MENSAJEINICIAL;
	private String MENSAJEMN;
	private String MENSAJEFINAL;
	
	private String OPERATIONS_STATUS;
	private String REQUEST_ID;
	private String INSTRUMENTO;
	private String ACCION;
	private String ORIGEN;
	private String ESTADO;
	private String FECHADESDE;
	private String IDTRANSACCION;
	private String FECHAHASTA;
	private String DESTINO;
	private String MENSAJE;
	private String MENSMN;
	private String MENSDESTINO;
	private String COMPID;

	private String DES;
	private String DESSID;
	private String DESSERVIDOR;
	private String PRE;
	private String PRESID;
	private String PRESERVIDOR;
	private String PRO;
	private String PROSID;
	private String PROSERVIDOR;
	private String GOS;
	private String GOSSID;
	private String GOSSERVIDOR;
	private String LOCAL;
	private String PUERTOENTORNOS;
	
	private String USERTIBCO;
	private String PASSWORDTIBCO;
	private String SENTENCIAFILTRO;
	private String SENTENCIAFILTRO2;
	private String SENTENCIAFINDREQUESTIDS;
	private String SENTENCIAFINDMENSAJES;
	private String SENTENCIAFINDMENSAJESTAMANIO;
	private String CONCATENAR;
	private String CONCATENAR2;
	
	
	public ServicioEjecucion(){
		obtener();
		
	}
	
	public void obtener(){
		try{ 
		Properties propiedades = new Properties();
		propiedades.load(getClass().getResourceAsStream("Config.properties"));
		
		//Conexiones Oracle
		setPUERTO(propiedades.getProperty(CTES_CONFIGURACION.PUERTO));
		setDRIVER(propiedades.getProperty(CTES_CONFIGURACION.DRIVER));
		setSID(propiedades.getProperty(CTES_CONFIGURACION.SID));
		setSERVIDOR(propiedades.getProperty(CTES_CONFIGURACION.SERVIDOR));
		setDAO(propiedades.getProperty(CTES_CONFIGURACION.DAO));
		setUSER_BBDD(propiedades.getProperty(CTES_CONFIGURACION.USER_BBDD));
		setPASS_BBDD(propiedades.getProperty(CTES_CONFIGURACION.PASS_BBDD));
		setOP_ID(propiedades.getProperty(CTES_CONFIGURACION.OP_ID));
		setREQUEST_ID(propiedades.getProperty(CTES_CONFIGURACION.REQUEST_ID));
		setOP_NAME(propiedades.getProperty(CTES_CONFIGURACION.OP_NAME));
		setOP_DESCRIPTION(propiedades.getProperty(CTES_CONFIGURACION.OP_DESCRIPTION));
		setOP_OPERACIONES(propiedades.getProperty(CTES_CONFIGURACION.OP_OPERACIONES));
		setTESTCASE_STATUS(propiedades.getProperty(CTES_CONFIGURACION.TESTCASE_STATUS));
		setOPERATIONS_STATUS(propiedades.getProperty(CTES_CONFIGURACION.OPERATIONS_STATUS));
		setMENSAJEINICIAL(propiedades.getProperty(CTES_CONFIGURACION.MENSAJEINICIAL));
		setMENSAJEMN(propiedades.getProperty(CTES_CONFIGURACION.MENSAJEMN));
		setMENSAJEFINAL(propiedades.getProperty(CTES_CONFIGURACION.MENSAJEFINAL));
		
		
		setPETICIONES(propiedades.getProperty(CTES_CONFIGURACION.PETICIONES));
		setMENSAJES(propiedades.getProperty(CTES_CONFIGURACION.MENSAJES));
		
		setUSERTIBCO(propiedades.getProperty(CTES_CONFIGURACION.USERTIBCO));
		setPASSWORDTIBCO(propiedades.getProperty(CTES_CONFIGURACION.PASSWORDTIBCO));
		setDES(propiedades.getProperty(CTES_CONFIGURACION.DES));
		setDESSID(propiedades.getProperty(CTES_CONFIGURACION.DESSID));
		setDESSERVIDOR(propiedades.getProperty(CTES_CONFIGURACION.DESSERVIDOR));
		setPRE(propiedades.getProperty(CTES_CONFIGURACION.PRE));
		setPRESID(propiedades.getProperty(CTES_CONFIGURACION.PRESID));
		setPRESERVIDOR(propiedades.getProperty(CTES_CONFIGURACION.PRESERVIDOR));
		setPRO(propiedades.getProperty(CTES_CONFIGURACION.PRO));
		setPROSID(propiedades.getProperty(CTES_CONFIGURACION.PROSID));
		setPROSERVIDOR(propiedades.getProperty(CTES_CONFIGURACION.PROSERVIDOR));
		setGOS(propiedades.getProperty(CTES_CONFIGURACION.GOS));
		setGOSSID(propiedades.getProperty(CTES_CONFIGURACION.GOSSID));
		setGOSSERVIDOR(propiedades.getProperty(CTES_CONFIGURACION.GOSSERVIDOR));
		setLOCAL(propiedades.getProperty(CTES_CONFIGURACION.LOCAL));
		setPUERTOENTORNOS(propiedades.getProperty(CTES_CONFIGURACION.PUERTOENTORNOS));
		setSENTENCIAFILTRO(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAFILTRO));
		setSENTENCIAFILTRO2(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAFILTRO2));
		setSENTENCIAFINDREQUESTIDS(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAFINDREQUESTIDS));
		setSENTENCIAFINDMENSAJES(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAFINDMENSAJES));
		setSENTENCIAFINDMENSAJESTAMANIO(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAFINDMENSAJESTAMANIO));
		setCONCATENAR(propiedades.getProperty(CTES_CONFIGURACION.CONCATENAR));
		setCONCATENAR2(propiedades.getProperty(CTES_CONFIGURACION.CONCATENAR2));
			
		}
		catch (IOException ex) {
          System.out.println(ex);
        }
		
	}

	public String getPUERTO() {
		return PUERTO;
	}

	public void setPUERTO(String pUERTO) {
		PUERTO = pUERTO;
	}

	public String getDRIVER() {
		return DRIVER;
	}

	public void setDRIVER(String dRIVER) {
		DRIVER = dRIVER;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getSERVIDOR() {
		return SERVIDOR;
	}

	public void setSERVIDOR(String sERVIDOR) {
		SERVIDOR = sERVIDOR;
	}

	public String getDAO() {
		return DAO;
	}

	public void setDAO(String dAO) {
		DAO = dAO;
	}

	public String getUSER_BBDD() {
		return USER_BBDD;
	}

	public void setUSER_BBDD(String uSER_BBDD) {
		USER_BBDD = uSER_BBDD;
	}

	public String getPASS_BBDD() {
		return PASS_BBDD;
	}

	public void setPASS_BBDD(String pASS_BBDD) {
		PASS_BBDD = pASS_BBDD;
	}

	public String getDES() {
		return DES;
	}

	public void setDES(String dES) {
		DES = dES;
	}

	public String getDESSID() {
		return DESSID;
	}

	public void setDESSID(String dESSID) {
		DESSID = dESSID;
	}

	public String getDESSERVIDOR() {
		return DESSERVIDOR;
	}

	public void setDESSERVIDOR(String dESSERVIDOR) {
		DESSERVIDOR = dESSERVIDOR;
	}

	public String getPRE() {
		return PRE;
	}

	public void setPRE(String pRE) {
		PRE = pRE;
	}

	public String getPRESID() {
		return PRESID;
	}

	public void setPRESID(String pRESID) {
		PRESID = pRESID;
	}

	public String getPRO() {
		return PRO;
	}

	public void setPRO(String pRO) {
		PRO = pRO;
	}

	public String getPRESERVIDOR() {
		return PRESERVIDOR;
	}

	public void setPRESERVIDOR(String pRESERVIDOR) {
		PRESERVIDOR = pRESERVIDOR;
	}

	public String getPROSID() {
		return PROSID;
	}

	public void setPROSID(String pROSID) {
		PROSID = pROSID;
	}

	public String getPROSERVIDOR() {
		return PROSERVIDOR;
	}

	public void setPROSERVIDOR(String pROSERVIDOR) {
		PROSERVIDOR = pROSERVIDOR;
	}

	public String getGOS() {
		return GOS;
	}

	public void setGOS(String gOS) {
		GOS = gOS;
	}

	public String getGOSSID() {
		return GOSSID;
	}

	public void setGOSSID(String gOSSID) {
		GOSSID = gOSSID;
	}

	public String getGOSSERVIDOR() {
		return GOSSERVIDOR;
	}

	public void setGOSSERVIDOR(String gOSSERVIDOR) {
		GOSSERVIDOR = gOSSERVIDOR;
	}

	public String getLOCAL() {
		return LOCAL;
	}

	public void setLOCAL(String lOCAL) {
		LOCAL = lOCAL;
	}

	public String getPUERTOENTORNOS() {
		return PUERTOENTORNOS;
	}

	public void setPUERTOENTORNOS(String pUERTOENTORNOS) {
		PUERTOENTORNOS = pUERTOENTORNOS;
	}

	public String getUSERTIBCO() {
		return USERTIBCO;
	}

	public void setUSERTIBCO(String uSERTIBCO) {
		USERTIBCO = uSERTIBCO;
	}

	public String getPASSWORDTIBCO() {
		return PASSWORDTIBCO;
	}

	public void setPASSWORDTIBCO(String pASSWORDTIBCO) {
		PASSWORDTIBCO = pASSWORDTIBCO;
	}

	public String getSENTENCIAFILTRO() {
		return SENTENCIAFILTRO;
	}

	public void setSENTENCIAFILTRO(String sENTENCIAFILTRO) {
		SENTENCIAFILTRO = sENTENCIAFILTRO;
	}

	public String getCONCATENAR() {
		return CONCATENAR;
	}

	public void setCONCATENAR(String cONCATENAR) {
		CONCATENAR = cONCATENAR;
	}

	public String getCONCATENAR2() {
		return CONCATENAR2;
	}

	public void setCONCATENAR2(String cONCATENAR2) {
		CONCATENAR2 = cONCATENAR2;
	}

	public String getOP_NAME() {
		return OP_NAME;
	}

	public void setOP_NAME(String oP_NAME) {
		OP_NAME = oP_NAME;
	}

	public String getOP_DESCRIPTION() {
		return OP_DESCRIPTION;
	}

	public void setOP_DESCRIPTION(String oP_DESCRIPTION) {
		OP_DESCRIPTION = oP_DESCRIPTION;
	}

	public String getTESTCASE_STATUS() {
		return TESTCASE_STATUS;
	}

	public void setTESTCASE_STATUS(String tESTCASE_STATUS) {
		TESTCASE_STATUS = tESTCASE_STATUS;
	}

	public String getOP_ID() {
		return OP_ID;
	}

	public void setOP_ID(String oP_ID) {
		OP_ID = oP_ID;
	}

	public String getOP_OPERACIONES() {
		return OP_OPERACIONES;
	}

	public void setOP_OPERACIONES(String oP_OPERACIONES) {
		OP_OPERACIONES = oP_OPERACIONES;
	}

	public String getREQUEST_ID() {
		return REQUEST_ID;
	}

	public void setREQUEST_ID(String rEQUEST_ID) {
		REQUEST_ID = rEQUEST_ID;
	}

	public String getINSTRUMENTO() {
		return INSTRUMENTO;
	}

	public void setINSTRUMENTO(String iNSTRUMENTO) {
		INSTRUMENTO = iNSTRUMENTO;
	}

	public String getACCION() {
		return ACCION;
	}

	public void setACCION(String aCCION) {
		ACCION = aCCION;
	}

	public String getORIGEN() {
		return ORIGEN;
	}

	public void setORIGEN(String oRIGEN) {
		ORIGEN = oRIGEN;
	}

	public String getESTADO() {
		return ESTADO;
	}

	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}

	public String getFECHADESDE() {
		return FECHADESDE;
	}

	public void setFECHADESDE(String fECHADESDE) {
		FECHADESDE = fECHADESDE;
	}

	public String getIDTRANSACCION() {
		return IDTRANSACCION;
	}

	public void setIDTRANSACCION(String iDTRANSACCION) {
		IDTRANSACCION = iDTRANSACCION;
	}

	public String getFECHAHASTA() {
		return FECHAHASTA;
	}

	public void setFECHAHASTA(String fECHAHASTA) {
		FECHAHASTA = fECHAHASTA;
	}

	public String getDESTINO() {
		return DESTINO;
	}

	public void setDESTINO(String dESTINO) {
		DESTINO = dESTINO;
	}

	public String getMENSAJE() {
		return MENSAJE;
	}

	public void setMENSAJE(String mENSAJE) {
		MENSAJE = mENSAJE;
	}

	public String getMENSMN() {
		return MENSMN;
	}

	public void setMENSMN(String mENSMN) {
		MENSMN = mENSMN;
	}

	public String getMENSDESTINO() {
		return MENSDESTINO;
	}

	public void setMENSDESTINO(String mENSDESTINO) {
		MENSDESTINO = mENSDESTINO;
	}

	public String getCOMPID() {
		return COMPID;
	}

	public void setCOMPID(String cOMPID) {
		COMPID = cOMPID;
	}

	public String getSENTENCIAFILTRO2() {
		return SENTENCIAFILTRO2;
	}

	public void setSENTENCIAFILTRO2(String sENTENCIAFILTRO2) {
		SENTENCIAFILTRO2 = sENTENCIAFILTRO2;
	}

	public String getOPERATIONS_STATUS() {
		return OPERATIONS_STATUS;
	}

	public void setOPERATIONS_STATUS(String oPERATIONS_STATUS) {
		OPERATIONS_STATUS = oPERATIONS_STATUS;
	}

	public String getSENTENCIAFINDREQUESTIDS() {
		return SENTENCIAFINDREQUESTIDS;
	}

	public void setSENTENCIAFINDREQUESTIDS(String sENTENCIAFINDREQUESTIDS) {
		SENTENCIAFINDREQUESTIDS = sENTENCIAFINDREQUESTIDS;
	}

	public String getPETICIONES() {
		return PETICIONES;
	}

	public void setPETICIONES(String pETICIONES) {
		PETICIONES = pETICIONES;
	}

	public String getSENTENCIAFINDMENSAJES() {
		return SENTENCIAFINDMENSAJES;
	}

	public void setSENTENCIAFINDMENSAJES(String sENTENCIAFINDMENSAJES) {
		SENTENCIAFINDMENSAJES = sENTENCIAFINDMENSAJES;
	}

	public String getSENTENCIAFINDMENSAJESTAMANIO() {
		return SENTENCIAFINDMENSAJESTAMANIO;
	}

	public void setSENTENCIAFINDMENSAJESTAMANIO(String sENTENCIAFINDMENSAJESTAMANIO) {
		SENTENCIAFINDMENSAJESTAMANIO = sENTENCIAFINDMENSAJESTAMANIO;
	}

	public String getMENSAJES() {
		return MENSAJES;
	}

	public void setMENSAJES(String mENSAJES) {
		MENSAJES = mENSAJES;
	}

	public String getMENSAJEINICIAL() {
		return MENSAJEINICIAL;
	}

	public void setMENSAJEINICIAL(String mENSAJEINICIAL) {
		MENSAJEINICIAL = mENSAJEINICIAL;
	}

	public String getMENSAJEMN() {
		return MENSAJEMN;
	}

	public void setMENSAJEMN(String mENSAJEMN) {
		MENSAJEMN = mENSAJEMN;
	}

	public String getMENSAJEFINAL() {
		return MENSAJEFINAL;
	}

	public void setMENSAJEFINAL(String mENSAJEFINAL) {
		MENSAJEFINAL = mENSAJEFINAL;
	}

	
}	