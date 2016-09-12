package properties;

import java.io.IOException;
import java.util.Properties;

import config.CTES_CONFIGURACION;

public class ServicioConfiguracion {

	//Datos conexion
	private String PUERTO;
	private String DRIVER;
	private String SID;
	private String SERVIDOR;
	private String DAO;
	private String USER_BBDD;
	private String PASS_BBDD;
	
	private String REQUEST_STATUS;
	private String EAI_MESSAGES;
	private String COMPONENT_STATUS;
	
	private String REQUEST_ID;
	private String ID;
	private String INSTRUMENTO;
	private String INSTRUMENT;
	private String ACCION;
	private String ORIGEN;
	private String ORIG;
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
	private String SENTENCIAMENSAJE;
	private String CONCATENAR;
	private String CONCATENAR2;
	private String CONCATENAR3;
	private String CONCATENAR4;
	private String CONCATENAR5;
	private String DESTMENS;
	
	private String URLGUARDARCASO;
	private String INSERT;
	private String INSERT2;
	private String SELECTID;
	private String COMPROBACIONMENSAJE;
	private String SELECCIONARDISTINTOS;
	private String SENTENCIAELIMINARVIEJOS;
	private String SENTENCIAMENSINI;
	private String SENTENCIAMENSMN;
	private String SENTENCIAMENSFIN;
	private String INSERTMENSINI;
	private String INSERTMENSMN;
	private String INSERTMENSFIN;
	private String SENTENCIABUSCARCASO;
	private String CASO_ID;
	private String CASO_NOMBRE;
	private String CASO_DESCRIPCION;
	private String CASO_OPERACIONES;
	private String TABLACASOS;
	
	private String SELECTREQUEST;
	private String DELELTEMENS;
	private String DELETEPET;
	private String DELETEPET2;
	private String DELETECASO;
	private String SELECTPET;
	
	

	
	
	
	public ServicioConfiguracion(){
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
		setREQUEST_ID(propiedades.getProperty(CTES_CONFIGURACION.REQUEST_ID));
		setID(propiedades.getProperty(CTES_CONFIGURACION.ID));
		setINSTRUMENTO(propiedades.getProperty(CTES_CONFIGURACION.INSTRUMENTO));
		setINSTRUMENT(propiedades.getProperty(CTES_CONFIGURACION.INSTRUMENT));
		setACCION(propiedades.getProperty(CTES_CONFIGURACION.ACCION));
		setORIGEN(propiedades.getProperty(CTES_CONFIGURACION.ORIGEN));
		setORIG(propiedades.getProperty(CTES_CONFIGURACION.ORIG));
		setREQUEST_STATUS(propiedades.getProperty(CTES_CONFIGURACION.REQUEST_STATUS));
		setESTADO(propiedades.getProperty(CTES_CONFIGURACION.ESTADO));
		setFECHADESDE(propiedades.getProperty(CTES_CONFIGURACION.FECHAINI));
		setIDTRANSACCION(propiedades.getProperty(CTES_CONFIGURACION.IDTRANSACCION));
		setFECHAHASTA(propiedades.getProperty(CTES_CONFIGURACION.FECHAFIN));
		setDESTINO(propiedades.getProperty(CTES_CONFIGURACION.DESTINO));
		setCOMPONENT_STATUS(propiedades.getProperty(CTES_CONFIGURACION.COMPONENT_STATUS));
		setEAI_MESSAGES(propiedades.getProperty(CTES_CONFIGURACION.EAIMESSAGES));
		setMENSAJE(propiedades.getProperty(CTES_CONFIGURACION.MENSAJE));
		setMENSMN(propiedades.getProperty(CTES_CONFIGURACION.MENSMN));
		setMENSDESTINO(propiedades.getProperty(CTES_CONFIGURACION.MENSDESTINO));
		setCOMPID(propiedades.getProperty(CTES_CONFIGURACION.COMPID));
		
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
		setSENTENCIAMENSAJE(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAMENSAJE));
		setCONCATENAR(propiedades.getProperty(CTES_CONFIGURACION.CONCATENAR));
		setCONCATENAR2(propiedades.getProperty(CTES_CONFIGURACION.CONCATENAR2));
		setCONCATENAR3(propiedades.getProperty(CTES_CONFIGURACION.CONCATENAR3));
		setCONCATENAR4(propiedades.getProperty(CTES_CONFIGURACION.CONCATENAR4));
		setCONCATENAR5(propiedades.getProperty(CTES_CONFIGURACION.CONCATENAR5));
		setDESTMENS(propiedades.getProperty(CTES_CONFIGURACION.DESTMENS));
		setURLGUARDARCASO(propiedades.getProperty(CTES_CONFIGURACION.URLGUARDARCASO));
		setINSERT(propiedades.getProperty(CTES_CONFIGURACION.INSERT));
		setINSERT2(propiedades.getProperty(CTES_CONFIGURACION.INSERT2));
		setSELECTID(propiedades.getProperty(CTES_CONFIGURACION.SELECTID));
		setSELECCIONARDISTINTOS(propiedades.getProperty(CTES_CONFIGURACION.SELECCIONARDISTINTOS));
		setCOMPROBACIONMENSAJE(propiedades.getProperty(CTES_CONFIGURACION.COMPROBACIONMENSAJE));
		setSENTENCIAELIMINARVIEJOS(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAELIMINARVIEJOS));
		setSENTENCIAMENSINI(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAMENSINI));
		setSENTENCIAMENSMN(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAMENSMN));
		setSENTENCIAMENSFIN(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIAMENSFIN));
		setINSERTMENSINI(propiedades.getProperty(CTES_CONFIGURACION.INSERTMENSINI));
		setINSERTMENSMN(propiedades.getProperty(CTES_CONFIGURACION.INSERTMENSMN));
		setINSERTMENSFIN(propiedades.getProperty(CTES_CONFIGURACION.INSERTMENSFIN));
		setSENTENCIABUSCARCASO(propiedades.getProperty(CTES_CONFIGURACION.SENTENCIABUSCARCASO));
		
		setCASO_ID(propiedades.getProperty(CTES_CONFIGURACION.CASO_ID));
		setCASO_NOMBRE(propiedades.getProperty(CTES_CONFIGURACION.CASO_NOMBRE));
		setCASO_DESCRIPCION(propiedades.getProperty(CTES_CONFIGURACION.CASO_DESCRIPCION));
		setCASO_OPERACIONES(propiedades.getProperty(CTES_CONFIGURACION.CASO_OPERACIONES));
		setTABLACASOS(propiedades.getProperty(CTES_CONFIGURACION.TABLACASOS));
		setSELECTREQUEST(propiedades.getProperty(CTES_CONFIGURACION.SELECTREQUEST));
		setDELELTEMENS(propiedades.getProperty(CTES_CONFIGURACION.DELETEMENS));
		setDELETEPET(propiedades.getProperty(CTES_CONFIGURACION.DELETEPET));
		setDELETEPET2(propiedades.getProperty(CTES_CONFIGURACION.DELETEPET2));
		setDELETECASO(propiedades.getProperty(CTES_CONFIGURACION.DELETECASO));
		setSELECTPET(propiedades.getProperty(CTES_CONFIGURACION.SELECTPET));
		
		
		
		
		
		
			
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

	public String getREQUEST_STATUS() {
		return REQUEST_STATUS;
	}

	public void setREQUEST_STATUS(String rEQUEST_STATUS) {
		REQUEST_STATUS = rEQUEST_STATUS;
	}

	public String getESTADO() {
		return ESTADO;
	}

	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}

	public String getIDTRANSACCION() {
		return IDTRANSACCION;
	}

	public void setIDTRANSACCION(String iDTRANSACCION) {
		IDTRANSACCION = iDTRANSACCION;
	}

	public String getFECHADESDE() {
		return FECHADESDE;
	}

	public void setFECHADESDE(String fECHADESDE) {
		FECHADESDE = fECHADESDE;
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

	public String getCOMPONENT_STATUS() {
		return COMPONENT_STATUS;
	}

	public void setCOMPONENT_STATUS(String cOMPONENT_STATUS) {
		COMPONENT_STATUS = cOMPONENT_STATUS;
	}

	public String getEAI_MESSAGES() {
		return EAI_MESSAGES;
	}

	public void setEAI_MESSAGES(String eAI_MESSAGES) {
		EAI_MESSAGES = eAI_MESSAGES;
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

	public String getSENTENCIAMENSAJE() {
		return SENTENCIAMENSAJE;
	}

	public void setSENTENCIAMENSAJE(String sENTENCIAMENSAJE) {
		SENTENCIAMENSAJE = sENTENCIAMENSAJE;
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

	public String getCONCATENAR3() {
		return CONCATENAR3;
	}

	public void setCONCATENAR3(String cONCATENAR3) {
		CONCATENAR3 = cONCATENAR3;
	}

	public String getDESTMENS() {
		return DESTMENS;
	}

	public void setDESTMENS(String dESTMENS) {
		DESTMENS = dESTMENS;
	}

	public String getCONCATENAR4() {
		return CONCATENAR4;
	}

	public void setCONCATENAR4(String cONCATENAR4) {
		CONCATENAR4 = cONCATENAR4;
	}

	public String getCONCATENAR5() {
		return CONCATENAR5;
	}

	public void setCONCATENAR5(String cONCATENAR5) {
		CONCATENAR5 = cONCATENAR5;
	}

	public String getURLGUARDARCASO() {
		return URLGUARDARCASO;
	}

	public void setURLGUARDARCASO(String uRLGUARDARCASO) {
		URLGUARDARCASO = uRLGUARDARCASO;
	}

	public String getINSERT() {
		return INSERT;
	}

	public void setINSERT(String iNSERT) {
		INSERT = iNSERT;
	}

	public String getINSERT2() {
		return INSERT2;
	}

	public void setINSERT2(String iNSERT2) {
		INSERT2 = iNSERT2;
	}

	public String getSELECTID() {
		return SELECTID;
	}

	public void setSELECTID(String sELECTID) {
		SELECTID = sELECTID;
	}

	public String getSENTENCIAMENSINI() {
		return SENTENCIAMENSINI;
	}

	public void setSENTENCIAMENSINI(String sENTENCIAMENSINI) {
		SENTENCIAMENSINI = sENTENCIAMENSINI;
	}

	public String getSENTENCIAMENSMN() {
		return SENTENCIAMENSMN;
	}

	public void setSENTENCIAMENSMN(String sENTENCIAMENSMN) {
		SENTENCIAMENSMN = sENTENCIAMENSMN;
	}

	public String getSENTENCIAMENSFIN() {
		return SENTENCIAMENSFIN;
	}

	public void setSENTENCIAMENSFIN(String sENTENCIAMENSFIN) {
		SENTENCIAMENSFIN = sENTENCIAMENSFIN;
	}

	public String getINSERTMENSINI() {
		return INSERTMENSINI;
	}

	public void setINSERTMENSINI(String iNSERTMENSINI) {
		INSERTMENSINI = iNSERTMENSINI;
	}

	public String getINSERTMENSMN() {
		return INSERTMENSMN;
	}

	public void setINSERTMENSMN(String iNSERTMENSMN) {
		INSERTMENSMN = iNSERTMENSMN;
	}

	public String getINSERTMENSFIN() {
		return INSERTMENSFIN;
	}

	public void setINSERTMENSFIN(String iNSERTMENSFIN) {
		INSERTMENSFIN = iNSERTMENSFIN;
	}

	public String getSENTENCIABUSCARCASO() {
		return SENTENCIABUSCARCASO;
	}

	public void setSENTENCIABUSCARCASO(String sENTENCIABUSCARCASO) {
		SENTENCIABUSCARCASO = sENTENCIABUSCARCASO;
	}

	public String getCASO_ID() {
		return CASO_ID;
	}

	public void setCASO_ID(String cASO_ID) {
		CASO_ID = cASO_ID;
	}

	public String getCASO_NOMBRE() {
		return CASO_NOMBRE;
	}

	public void setCASO_NOMBRE(String cASO_NOMBRE) {
		CASO_NOMBRE = cASO_NOMBRE;
	}

	public String getCASO_DESCRIPCION() {
		return CASO_DESCRIPCION;
	}

	public void setCASO_DESCRIPCION(String cASO_DESCRIPCION) {
		CASO_DESCRIPCION = cASO_DESCRIPCION;
	}

	public String getCASO_OPERACIONES() {
		return CASO_OPERACIONES;
	}

	public void setCASO_OPERACIONES(String cASO_OPERACIONES) {
		CASO_OPERACIONES = cASO_OPERACIONES;
	}

	public String getTABLACASOS() {
		return TABLACASOS;
	}

	public void setTABLACASOS(String tABLACASOS) {
		TABLACASOS = tABLACASOS;
	}

	public String getSELECTREQUEST() {
		return SELECTREQUEST;
	}

	public void setSELECTREQUEST(String sELECTREQUEST) {
		SELECTREQUEST = sELECTREQUEST;
	}

	public String getDELELTEMENS() {
		return DELELTEMENS;
	}

	public void setDELELTEMENS(String dELELTEMENS) {
		DELELTEMENS = dELELTEMENS;
	}

	public String getDELETEPET() {
		return DELETEPET;
	}

	public void setDELETEPET(String dELETEPET) {
		DELETEPET = dELETEPET;
	}

	public String getDELETECASO() {
		return DELETECASO;
	}

	public void setDELETECASO(String dELETECASO) {
		DELETECASO = dELETECASO;
	}

	public String getSELECTPET() {
		return SELECTPET;
	}

	public void setSELECTPET(String sELECTPET) {
		SELECTPET = sELECTPET;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getINSTRUMENT() {
		return INSTRUMENT;
	}

	public void setINSTRUMENT(String iNSTRUMENT) {
		INSTRUMENT = iNSTRUMENT;
	}

	public String getORIG() {
		return ORIG;
	}

	public void setORIG(String oRIG) {
		ORIG = oRIG;
	}

	public String getDELETEPET2() {
		return DELETEPET2;
	}

	public void setDELETEPET2(String dELETEPET2) {
		DELETEPET2 = dELETEPET2;
	}

	public String getCOMPROBACIONMENSAJE() {
		return COMPROBACIONMENSAJE;
	}

	public void setCOMPROBACIONMENSAJE(String cOMPROBACIONMENSAJE) {
		COMPROBACIONMENSAJE = cOMPROBACIONMENSAJE;
	}

	public String getSELECCIONARDISTINTOS() {
		return SELECCIONARDISTINTOS;
	}

	public void setSELECCIONARDISTINTOS(String sELECCIONARDISTINTOS) {
		SELECCIONARDISTINTOS = sELECCIONARDISTINTOS;
	}

	public String getSENTENCIAELIMINARVIEJOS() {
		return SENTENCIAELIMINARVIEJOS;
	}

	public void setSENTENCIAELIMINARVIEJOS(String sENTENCIAELIMINARVIEJOS) {
		SENTENCIAELIMINARVIEJOS = sENTENCIAELIMINARVIEJOS;
	}

	
}	