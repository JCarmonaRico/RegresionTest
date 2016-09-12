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

import com.acc.regresiontest.com.comparator.bussineslogic.BussinesLogicComparator;
import com.google.gson.Gson;
//import com.acc.regresiontest.com.domains.Resultados;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interfaces.IDAO;
import properties.CasosPrueba;
import properties.MensajeRecuperado;
import properties.MensajesEnviados;
import properties.OperacionesConfig;
import properties.ServicioEjecucion;

import com.tibco.tibjms.*;

@Path("services/serviciosPruebas")
public class serviciosPruebas {

	//Busco el caso de prueba en la BBDD
	@POST
	@Path("buscarCasosPrueba")
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
			//System.out.println("La conexion es ..." +con);

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
	@Path("ejecutarCasoPrueba")
	@Produces(MediaType.APPLICATION_JSON)

	public Response ejecutarCasoPrueba (String data) {

		BussinesLogicComparator bc = new BussinesLogicComparator();


		/*-----------------------------------------------------------------------
		 * Parameters Producer
		 *----------------------------------------------------------------------*/

		String          serverUrl    = "tcp://es5ti02bsab:7222";
		String          userName     = null;
		String          password     = null;
		String          name         = "murex.export.mxml.ccyirsdeals.queue";
		Vector<String>  dato         = new Vector<String>();
		boolean         useQueue     = true;
		boolean         useAsync     = false;

		/*-----------------------------------------------------------------------
		 * Variables Producer
		 *----------------------------------------------------------------------*/
		javax.jms.Connection      connection   = null;
		Session         session      = null;
		MessageProducer msgProducer  = null;
		Destination     destination  = null;


		/*-----------------------------------------------------------------------
		 * Parameters
		 *----------------------------------------------------------------------*/

		String           nameConsumer        = "regresionTestReceiver.queue";
		int              ackMode     = Session.AUTO_ACKNOWLEDGE;

		/*-----------------------------------------------------------------------
		 * Variables
		 *----------------------------------------------------------------------*/
		MessageConsumer msgConsumer = null;

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

		String idCasoPrueba = obj.get("idCasoPrueba").getAsString();
		String enviarADestinoFinal = obj.get("destinoFinal").getAsString(); 

		try{
			//Realizo la conexion

			ServicioEjecucion prop = new ServicioEjecucion();
			String tipoDAO = prop.getDAO();
			IDAO idao = (IDAO)Class.forName(tipoDAO).newInstance(); 
			Connection con =  idao.establecerConexionOracle();
			System.out.println("La conexion es ..." +con);

			//RECUPERO LOS REQUESTID ASOCIADOS A LOS CASOS DE PRUEBA
			String requestIDs = idao.findRequestID(
					idCasoPrueba);

			System.out.println("SE HAN RECUPERADO LOS SIGUIENTES REQUESTID: " + requestIDs);

			con =  idao.establecerConexionOracle();

			//RECUPERO LOS MENSAJES ASOCIADOS A LOS REQUESTID
			MensajeRecuperado[] mensajesRecuperados = idao.recuperarMensajes(
					requestIDs);


			int contadorMensajesSeEsperanRecibir = 0;

			TextMessage msg;
			//TIBCO-----------------------------
			System.out.println("Publishing to destination '"+name+"'\n");


			ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory (serverUrl);

			connection = factory.createConnection();

			/* create the session */
			session = connection.createSession(useAsync, javax.jms.Session.AUTO_ACKNOWLEDGE);


			destination = session.createQueue(name);
			/* create the producer */
			msgProducer = session.createProducer(null);
			//TIBCO-----------------------------

			/* create text message */
			msg = session.createTextMessage();
			List<MensajesEnviados> mensajesEnviados = new ArrayList<MensajesEnviados>();          

			for (int i = 0; i < mensajesRecuperados.length; i++) {
				MensajesEnviados mensajeEnviado = new MensajesEnviados();          
				msg = session.createTextMessage();

				String mensajeInicialRecuperado = mensajesRecuperados[i].getMensajeInicial(); //ID del mensaje recuperado
				String mensajeMNRecuperado =  mensajesRecuperados[i].getMensajeMN(); //ID del mensaje recuperado
				List<String> mensajesMFinalesRecuperados =  mensajesRecuperados[i].getMensajesFinales(); //ID del mensaje recuperado

				//contadorMensajesSeEsperanRecibir = contadorMensajesSeEsperanRecibir + 1 + mensajesMFinalesRecuperados.size();
				// Los mensajes que se esperan recibir son MMN + MFs, por cada peticion


				/* set message text */
				//if(mensajeInicialRecuperado!=null){

				msg.setText(mensajeInicialRecuperado);
				//msg.setStringProperty("marcaRegresionTest", "TRUE");
				//msg.setStringProperty("idCorrelacion", Integer.toString(contadorMensajesSeEsperanRecibir));
				//msg.setJMSType("marcaRegresionTest");
				//msg.setJMSCorrelationID(Integer.toString(contadorMensajesSeEsperanRecibir));
				//mensajeEnviado.setTextoEnviado(mensajeInicialRecuperado);
				//mensajeEnviado.setTextoSeEsperaRecibir(mensajeMNRecuperado);
				//mensajeEnviado.setMensajeID(Integer.toString(contadorMensajesSeEsperanRecibir));
				contadorMensajesSeEsperanRecibir++;
				msgProducer.send(destination, msg);
				System.out.println("MENSAJE ENVIADO: "+msg+"\n");
				System.out.println("TEXTO ENVIADO: "+msg.getText()+"\n");
				//mensajesEnviados.add(mensajeEnviado);
				//mensajesMFinalesRecuperados.size();

				contadorMensajesSeEsperanRecibir = contadorMensajesSeEsperanRecibir + mensajesMFinalesRecuperados.size();



				/*
                           ConnectionFactory factoryTSB = new com.tibco.tibjms.TibjmsConnectionFactory ("tcp://172:7222");

                           connection = factory.createConnection();


                           session = connection.createSession(useAsync, javax.jms.Session.AUTO_ACKNOWLEDGE);

                             for (String mensajeMFinalRecuperado : mensajesMFinalesRecuperados) {
                                                                              destination = session.createQueue("adaptiv.import.nm.swapdeals.queue");
                                msg = session.createTextMessage();
                                mensajeEnviado = new MensajesEnviados();  
                                msg.setText(mensajeMNRecuperado);
                                msg.setJMSCorrelationID(Integer.toString(contadorMensajesSeEsperanRecibir));
                                                mensajeEnviado.setTextoEnviado(mensajeMNRecuperado);
                                                mensajeEnviado.setTextoSeEsperaRecibir(mensajeMFinalRecuperado);
                                                mensajeEnviado.setMensajeID(Integer.toString(contadorMensajesSeEsperanRecibir));
                                                contadorMensajesSeEsperanRecibir++;
                                                msgProducer.send(destination, msg);
                                                System.out.println("MENSAJE ENVIADO: "+msg+"\n");
                                            System.out.println("TEXTO ENVIADO: "+msg.getText()+"\n");
                                            mensajesEnviados.add(mensajeEnviado);
                                                               }
				 */
				//}

				/* set message text */
				/*
                                 if(mensajeMNRecuperado!=null){
                                                msg.setText(mensajeMNRecuperado);
                                                msg.setStringProperty("messageID", Integer.toString(contadorMensajesEnviados));
                                                mensajeEnviado.setTextoEnviado(mensajeInicialRecuperado);
                                                mensajeEnviado.setTextoSeEsperaRecibir(mensajeMFinalRecuperado);
                                                mensajeEnviado.setMensajeID(Integer.toString(contadorMensajesEnviados));
                                                contadorMensajesEnviados++;
                                                msgProducer.send(destination, msg);
                                                System.out.println("MENSAJE ENVIADO: "+msg+"\n");
                                            System.out.println("TEXTO ENVIADO: "+msg.getText()+"\n");
                                            mensajesEnviados.add(mensajeEnviado);
                                 }
				 */
			}

			//COMIENZO DE ESPERA PARA RECIBIR MENSAJES
			TextMessage[] msgRecibidos = new  TextMessage[contadorMensajesSeEsperanRecibir];
			TextMessage msgRecibido;
			Message       msgReceive         = null;
			String        msgType     = "UNKNOWN";

			System.out.println("Subscribing to destination: "+nameConsumer+"\n");

			ConnectionFactory factoryConsumer = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);

			/* create the connection */
			connection = factoryConsumer.createConnection();

			/* create the session */
			session = connection.createSession(useAsync, ackMode);

			/* create the destination */
			destination = session.createQueue(nameConsumer);

			/* create the consumer */
			msgConsumer = session.createConsumer(destination);

			/* start the connection */
			connection.start();

			int contadorMensajesRecibidos = 0;

			double condicionSalida = 0;

			while(contadorMensajesRecibidos < contadorMensajesSeEsperanRecibir){
				/* receive the message */
				msgRecibido = (TextMessage) msgConsumer.receive();
				System.out.println("MENSAJE RECIBIDO: "+msgRecibido);
				System.out.println("TEXTO RECIBIDO: "+msgRecibido.getText());
				msgRecibidos[contadorMensajesRecibidos] = msgRecibido; //Voy guardando los mensajes recibidos

				contadorMensajesRecibidos++;

				if (ackMode == Session.CLIENT_ACKNOWLEDGE ||
						ackMode == Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE ||
						ackMode == Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE)
				{
					msg.acknowledge();
				} 
				//condicionSalida++;
			}

			System.out.println("RECIBIDOS TODOS LOS MENSAJES");

			boolean  primeraComparacion = true;
			String jsonComparacionConcatenados = "";
			for (int i = 0; i < msgRecibidos.length; i++) {
				//for (MensajesEnviados mensajeEnviado : mensajesEnviados) {
					//String messageCorrelationID = msgRecibidos[i].getJMSCorrelationID();
					//if(messageCorrelationID.equals(mensajeEnviado.getMensajeID())){
					//for (String textoSeEsperaRecibir : mensajeEnviado.getTextosSeEsperanRecibir()) {
					
					String textoSeEsperaRecibir = mensajesEnviados.get(i).getTextoSeEsperaRecibir();
					String textoMensajeRecibido = msgRecibidos[i].getText();

					//textoMensajeRecibido = textoMensajeRecibido.replaceAll("Counterparty", "EntidadFinanciera");


					System.out.println("TEXTO SE ESPERA RECIBIR: "+textoSeEsperaRecibir);
					System.out.println("TEXTO RECIBIDO: "+textoMensajeRecibido);

					textoSeEsperaRecibir = textoSeEsperaRecibir.replace("\\\"", "\"");
					textoMensajeRecibido = textoMensajeRecibido.replace("\\\"", "\"");

					System.out.println("TEXTO SE ESPERA RECIBIR22222: "+textoSeEsperaRecibir);

					//textoSeEsperaRecibir = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Grupo><NTMMessageGeneric><NTMMessage version=\"1.03\"><NTMHeader><MessageID>0100000000025939766</MessageID><MessageSource><SystemID>Tibco</SystemID><Location>Sabadell</Location></MessageSource><Timezone>GMT</Timezone><DateFormat format=\"ISO8601\"/><GeneratedTime>2015-09-29T17:34:50.897+02:00</GeneratedTime><PublishResponse value=\"JustLog\"/></NTMHeader><NTMTrade><TradeHeader><SysTradeID><SystemID>Adaptiv</SystemID><Location>Sabadell</Location><TradeID>010000000002593974</TradeID></SysTradeID><TradeDate>2015-09-28T12:36:19</TradeDate><OriginEntity><Entity role=\"Owner\"><Organization>BS</Organization><Trader>sa</Trader></Entity></OriginEntity><OtherEntity><Entity role=\"Counterparty\"><Organization>JPMSGBXXX</Organization><Trader>mgr</Trader></Entity></OtherEntity><TradeDescription>Mx://</TradeDescription><TradeRole value=\"Potential\"/><TradeMessageRole value=\"New\"/></TradeHeader><TradeTags><TradingArea>DE</TradingArea><AccountingArea>IRS</AccountingArea><Extensions><Extension><ExtName>TradeGroup</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>UserTradeDate</ExtName><ExtValue datatype=\"Date\">2015-09-28T12:36:19</ExtValue></Extension></Extensions></TradeTags><IRSwap><PaySide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"6m\"/></PayFreq><ResetFreq><Frequency value=\"6m\"/></ResetFreq><ObserveFreq><Frequency value=\"6m\"/></ObserveFreq><DateRolls value=\"Standard\"/><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>EUR</CCY><Principal>40000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Float\"/><RateIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></RateIndexID><DayCount value=\"ACT:360\">ACT:360</DayCount><Spread>0</Spread><FirstFixIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></FirstFixIndexID><LastFixIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></LastFixIndexID><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><RateFormula/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FloatingInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>0</Amount><CCY>EUR</CCY></CashflowPayment><FloatingCashflow><RateIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>WEEEEEEAAAAAAA</RateSource></RateIndexID><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Principal>-400000000</Principal><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount><ResetDate>2015-10-28</ResetDate><Spread>0</Spread><ForwardPeriod><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount></ForwardPeriod></FloatingCashflow></Cashflow></Cashflows></TradeLeg></PaySide><ReceiveSide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"12m\"/></PayFreq><ResetFreq><Frequency value=\"12m\"/></ResetFreq><ObserveFreq><Frequency value=\"12m\"/></ObserveFreq><DateRolls value=\"Standard\">Standard</DateRolls><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>EUR</CCY><Principal>400000000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Fixed\"/><Rate>0.0275</Rate><DayCount value=\"30:360\">30:360</DayCount><Spread>0</Spread><FirstFixRate>0.0275</FirstFixRate><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FixedInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>54694.44444444</Amount><CCY>EUR</CCY></CashflowPayment><FixedCashflow><Rate>0.0275</Rate><Principal>400000000</Principal><Frequency value=\"12m\"/><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><DayCount value=\"30:360\">30:360</DayCount></FixedCashflow></Cashflow></Cashflows></TradeLeg></ReceiveSide></IRSwap><TradeTransactionCosts/><Extensions><Extension><ExtName>Market1</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Market2</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Branch</ExtName><ExtValue datatype=\"String\">TRADING_FO</ExtValue></Extension><Extension><ExtName>Portfolio</ExtName><ExtValue datatype=\"String\">VOL_IR</ExtValue></Extension><Extension><ExtName>Folder</ExtName><ExtValue datatype=\"String\">IRVOL_IRS</ExtValue></Extension><Extension><ExtName>BreakClauseSchedule</ExtName><ExtValue datatype=\"String\"/></Extension><Extension><ExtName>BreakClauseDefn</ExtName><ExtValue datatype=\"String\"/></Extension></Extensions></NTMTrade></NTMMessage></NTMMessageGeneric></Grupo>";
					//textoMensajeRecibido = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Grupo><NTMMessageGeneric><NTMMessage version=\"1.03\"><NTMHeader><MessageID>0100000000025939766</MessageID><MessageSource><SystemID>Tibco</SystemID><Location>Sabadell</Location></MessageSource><Timezone>GMT</Timezone><DateFormat format=\"ISO8601\"/><GeneratedTime>2015-09-29T17:34:50.897+02:00</GeneratedTime><PublishResponse value=\"JustLog\"/></NTMHeader><NTMTrade><TradeHeader><SysTradeID><SystemID>Adaptiv</SystemID><Location>Sabadell</Location><TradeID>010000000002593974</TradeID></SysTradeID><TradeDate>2015-09-28T12:36:19</TradeDate><OriginEntity><Entity role=\"Owner\"><Organization>TSB</Organization><Trader>sa</Trader></Entity></OriginEntity><OtherEntity><Entity role=\"Counterparty\"><Organization>JPMSGBXXX</Organization><Trader>mgr</Trader></Entity></OtherEntity><TradeDescription>Mx://</TradeDescription><TradeRole value=\"Potential\"/><TradeMessageRole value=\"New\"/></TradeHeader><TradeTags><TradingArea>DE</TradingArea><AccountingArea>IRS</AccountingArea><Extensions><Extension><ExtName>TradeGroup</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>UserTradeDate</ExtName><ExtValue datatype=\"Date\">2015-09-28T12:36:19</ExtValue></Extension></Extensions></TradeTags><IRSwap><PaySide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"6m\"/></PayFreq><ResetFreq><Frequency value=\"6m\"/></ResetFreq><ObserveFreq><Frequency value=\"6m\"/></ObserveFreq><DateRolls value=\"Standard\"/><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>GBP</CCY><Principal>40000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Float\"/><RateIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></RateIndexID><DayCount value=\"ACT:360\">ACT:360</DayCount><Spread>0</Spread><FirstFixIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></FirstFixIndexID><LastFixIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></LastFixIndexID><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><RateFormula/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FloatingInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>0</Amount><CCY>GBP</CCY></CashflowPayment><FloatingCashflow><RateIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>WEEEEEEAAAAAAA</RateSource></RateIndexID><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Principal>-400000000</Principal><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount><ResetDate>2015-10-28</ResetDate><Spread>0</Spread><ForwardPeriod><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount></ForwardPeriod></FloatingCashflow></Cashflow></Cashflows></TradeLeg></PaySide><ReceiveSide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"12m\"/></PayFreq><ResetFreq><Frequency value=\"12m\"/></ResetFreq><ObserveFreq><Frequency value=\"12m\"/></ObserveFreq><DateRolls value=\"Standard\">Standard</DateRolls><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>GBP</CCY><Principal>400000000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Fixed\"/><Rate>0.0275</Rate><DayCount value=\"30:360\">30:360</DayCount><Spread>0</Spread><FirstFixRate>0.0275</FirstFixRate><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FixedInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>54694.44444444</Amount><CCY>GBP</CCY></CashflowPayment><FixedCashflow><Rate>0.0275</Rate><Principal>400000000</Principal><Frequency value=\"12m\"/><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><DayCount value=\"30:360\">30:360</DayCount></FixedCashflow></Cashflow></Cashflows></TradeLeg></ReceiveSide></IRSwap><TradeTransactionCosts/><Extensions><Extension><ExtName>Market1</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Market2</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Branch</ExtName><ExtValue datatype=\"String\">TRADING_FO</ExtValue></Extension><Extension><ExtName>Portfolio</ExtName><ExtValue datatype=\"String\">VOL_IR</ExtValue></Extension><Extension><ExtName>Folder</ExtName><ExtValue datatype=\"String\">IRVOL_IRS</ExtValue></Extension><Extension><ExtName>BreakClauseSchedule</ExtName><ExtValue datatype=\"String\"/></Extension><Extension><ExtName>BreakClauseDefn</ExtName><ExtValue datatype=\"String\"/></Extension></Extensions></NTMTrade></NTMMessage></NTMMessageGeneric></Grupo>";

					String jsonComparacion = bc.CompareXML(textoSeEsperaRecibir, textoMensajeRecibido);

					jsonComparacion = jsonComparacion.replaceAll("\\'children\\'","\\'state\\' : {\\'opened\\' : true},\\'children\\'");

					if(primeraComparacion){
						jsonComparacionConcatenados = jsonComparacion;
						primeraComparacion = false;
					}else{
						jsonComparacionConcatenados = jsonComparacionConcatenados + ";" + jsonComparacion;
					}
					//}
					//}
				//}

			}
			Gson gson = new Gson();
			String resultado = gson.toJson(jsonComparacionConcatenados);

			/* receive the message */
			/*
                                msgRecibido = (TextMessage) msgConsumer.receive();

                         if (ackMode == Session.CLIENT_ACKNOWLEDGE ||
                             ackMode == Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE ||
                             ackMode == Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE)
                         {
                             msg.acknowledge();
                         }

                         System.out.println("MENSAJE RECIBIDO: "+msgRecibido+"\n");
                         System.out.println("TEXTO RECIBIDO: "+msgRecibido.getText()+"\n");
			 */


			/* close the connection */
			connection.close();

			/*
                         String mensajeEnviado = msg.getText();
                         String mensajeRecibido = null;
                         if (msgRecibido!=null){
                                 mensajeRecibido = msgRecibido.getText();
                                 mensajeRecibido = mensajeRecibido.replaceAll("Counterparty", "EntidadFinanciera");
                         }

                               String xmlAntiguo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Grupo><NTMMessageGeneric><NTMMessage version=\"1.03\"><NTMHeader><MessageID>0100000000025939766</MessageID><MessageSource><SystemID>Tibco</SystemID><Location>Sabadell</Location></MessageSource><Timezone>GMT</Timezone><DateFormat format=\"ISO8601\"/><GeneratedTime>2015-09-29T17:34:50.897+02:00</GeneratedTime><PublishResponse value=\"JustLog\"/></NTMHeader><NTMTrade><TradeHeader><SysTradeID><SystemID>Adaptiv</SystemID><Location>Sabadell</Location><TradeID>010000000002593974</TradeID></SysTradeID><TradeDate>2015-09-28T12:36:19</TradeDate><OriginEntity><Entity role=\"Owner\"><Organization>BS</Organization><Trader>sa</Trader></Entity></OriginEntity><OtherEntity><Entity role=\"Counterparty\"><Organization>JPMSGBXXX</Organization><Trader>mgr</Trader></Entity></OtherEntity><TradeDescription>Mx://</TradeDescription><TradeRole value=\"Potential\"/><TradeMessageRole value=\"New\"/></TradeHeader><TradeTags><TradingArea>DE</TradingArea><AccountingArea>IRS</AccountingArea><Extensions><Extension><ExtName>TradeGroup</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>UserTradeDate</ExtName><ExtValue datatype=\"Date\">2015-09-28T12:36:19</ExtValue></Extension></Extensions></TradeTags><IRSwap><PaySide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"6m\"/></PayFreq><ResetFreq><Frequency value=\"6m\"/></ResetFreq><ObserveFreq><Frequency value=\"6m\"/></ObserveFreq><DateRolls value=\"Standard\"/><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>EUR</CCY><Principal>40000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Float\"/><RateIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></RateIndexID><DayCount value=\"ACT:360\">ACT:360</DayCount><Spread>0</Spread><FirstFixIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></FirstFixIndexID><LastFixIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></LastFixIndexID><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><RateFormula/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FloatingInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>0</Amount><CCY>EUR</CCY></CashflowPayment><FloatingCashflow><RateIndexID><CCY>EUR</CCY><IndexID>EURIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>WEEEEEEAAAAAAA</RateSource></RateIndexID><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Principal>-400000000</Principal><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount><ResetDate>2015-10-28</ResetDate><Spread>0</Spread><ForwardPeriod><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount></ForwardPeriod></FloatingCashflow></Cashflow></Cashflows></TradeLeg></PaySide><ReceiveSide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"12m\"/></PayFreq><ResetFreq><Frequency value=\"12m\"/></ResetFreq><ObserveFreq><Frequency value=\"12m\"/></ObserveFreq><DateRolls value=\"Standard\">Standard</DateRolls><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>EUR</CCY><Principal>400000000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Fixed\"/><Rate>0.0275</Rate><DayCount value=\"30:360\">30:360</DayCount><Spread>0</Spread><FirstFixRate>0.0275</FirstFixRate><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FixedInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>54694.44444444</Amount><CCY>EUR</CCY></CashflowPayment><FixedCashflow><Rate>0.0275</Rate><Principal>400000000</Principal><Frequency value=\"12m\"/><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><DayCount value=\"30:360\">30:360</DayCount></FixedCashflow></Cashflow></Cashflows></TradeLeg></ReceiveSide></IRSwap><TradeTransactionCosts/><Extensions><Extension><ExtName>Market1</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Market2</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Branch</ExtName><ExtValue datatype=\"String\">TRADING_FO</ExtValue></Extension><Extension><ExtName>Portfolio</ExtName><ExtValue datatype=\"String\">VOL_IR</ExtValue></Extension><Extension><ExtName>Folder</ExtName><ExtValue datatype=\"String\">IRVOL_IRS</ExtValue></Extension><Extension><ExtName>BreakClauseSchedule</ExtName><ExtValue datatype=\"String\"/></Extension><Extension><ExtName>BreakClauseDefn</ExtName><ExtValue datatype=\"String\"/></Extension></Extensions></NTMTrade></NTMMessage></NTMMessageGeneric></Grupo>";
                                               String xmlTibco = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Grupo><NTMMessageGeneric><NTMMessage version=\"1.03\"><NTMHeader><MessageID>0100000000025939766</MessageID><MessageSource><SystemID>Tibco</SystemID><Location>Sabadell</Location></MessageSource><Timezone>GMT</Timezone><DateFormat format=\"ISO8601\"/><GeneratedTime>2015-09-29T17:34:50.897+02:00</GeneratedTime><PublishResponse value=\"JustLog\"/></NTMHeader><NTMTrade><TradeHeader><SysTradeID><SystemID>Adaptiv</SystemID><Location>Sabadell</Location><TradeID>010000000002593974</TradeID></SysTradeID><TradeDate>2015-09-28T12:36:19</TradeDate><OriginEntity><Entity role=\"Owner\"><Organization>TSB</Organization><Trader>sa</Trader></Entity></OriginEntity><OtherEntity><Entity role=\"Counterparty\"><Organization>JPMSGBXXX</Organization><Trader>mgr</Trader></Entity></OtherEntity><TradeDescription>Mx://</TradeDescription><TradeRole value=\"Potential\"/><TradeMessageRole value=\"New\"/></TradeHeader><TradeTags><TradingArea>DE</TradingArea><AccountingArea>IRS</AccountingArea><Extensions><Extension><ExtName>TradeGroup</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>UserTradeDate</ExtName><ExtValue datatype=\"Date\">2015-09-28T12:36:19</ExtValue></Extension></Extensions></TradeTags><IRSwap><PaySide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"6m\"/></PayFreq><ResetFreq><Frequency value=\"6m\"/></ResetFreq><ObserveFreq><Frequency value=\"6m\"/></ObserveFreq><DateRolls value=\"Standard\"/><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>GBP</CCY><Principal>40000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Float\"/><RateIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></RateIndexID><DayCount value=\"ACT:360\">ACT:360</DayCount><Spread>0</Spread><FirstFixIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></FirstFixIndexID><LastFixIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>BBA</RateSource></LastFixIndexID><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><RateFormula/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FloatingInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>0</Amount><CCY>GBP</CCY></CashflowPayment><FloatingCashflow><RateIndexID><CCY>GBP</CCY><IndexID>GBPIBORMM</IndexID><IndexTerm value=\"6m\">6m</IndexTerm><RateSource>WEEEEEEAAAAAAA</RateSource></RateIndexID><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Principal>-400000000</Principal><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount><ResetDate>2015-10-28</ResetDate><Spread>0</Spread><ForwardPeriod><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><Frequency value=\"6m\"/><DayCount value=\"ACT:360\">ACT:360</DayCount></ForwardPeriod></FloatingCashflow></Cashflow></Cashflows></TradeLeg></PaySide><ReceiveSide><TradeLeg><TradeLegDetails><TradeLegDates><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><PayFreq><Frequency value=\"12m\"/></PayFreq><ResetFreq><Frequency value=\"12m\"/></ResetFreq><ObserveFreq><Frequency value=\"12m\"/></ObserveFreq><DateRolls value=\"Standard\">Standard</DateRolls><PaymentOffset dayType=\"Business\">0</PaymentOffset><AdjustCouponDates value=\"Yes\"/><PayHolidays><Holidays><DateAdjMethod value=\"ModifiedFwds\"/></Holidays></PayHolidays></TradeLegDates><TradeLegPrincipal><CCY>GBP</CCY><Principal>400000000</Principal><StartPrincipalActOrNot value=\"Notional\"/><EndPrincipalActOrNot value=\"Notional\"/></TradeLegPrincipal><TradeLegInterest><InterestType value=\"Fixed\"/><Rate>0.0275</Rate><DayCount value=\"30:360\">30:360</DayCount><Spread>0</Spread><FirstFixRate>0.0275</FirstFixRate><ResetLag dayType=\"Business\">2</ResetLag><ResetAtStartOrEnd value=\"Start\"/><PayUpfrontOrArrears value=\"Arrears\"/><PayParOrDisc value=\"Par\"/><CompoundingMethod value=\"None\"/><AveragingDetails><AveragingMethod AveragingMethod=\"Unweighted\"/><CompoundObservations YesOrNo=\"No\"/></AveragingDetails></TradeLegInterest></TradeLegDetails><Cashflows><Sorted YesOrNo=\"No\"/><CashflowsAreUserDefined YesOrNo=\"Yes\"/><Cashflow CFLType=\"FixedInterest\"><CashflowID>100000000025939742016-04-29</CashflowID><CashflowPayment><PayDate>2016-04-29</PayDate><Amount>54694.44444444</Amount><CCY>GBP</CCY></CashflowPayment><FixedCashflow><Rate>0.0275</Rate><Principal>400000000</Principal><Frequency value=\"12m\"/><Period><StartDate>2015-10-30</StartDate><EndDate>2016-04-29</EndDate></Period><DayCount value=\"30:360\">30:360</DayCount></FixedCashflow></Cashflow></Cashflows></TradeLeg></ReceiveSide></IRSwap><TradeTransactionCosts/><Extensions><Extension><ExtName>Market1</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Market2</ExtName><ExtValue datatype=\"String\">IRS6M</ExtValue></Extension><Extension><ExtName>Branch</ExtName><ExtValue datatype=\"String\">TRADING_FO</ExtValue></Extension><Extension><ExtName>Portfolio</ExtName><ExtValue datatype=\"String\">VOL_IR</ExtValue></Extension><Extension><ExtName>Folder</ExtName><ExtValue datatype=\"String\">IRVOL_IRS</ExtValue></Extension><Extension><ExtName>BreakClauseSchedule</ExtName><ExtValue datatype=\"String\"/></Extension><Extension><ExtName>BreakClauseDefn</ExtName><ExtValue datatype=\"String\"/></Extension></Extensions></NTMTrade></NTMMessage></NTMMessageGeneric></Grupo>";

            String json = bc.CompareXML(mensajeEnviado, mensajeRecibido);
            String json2 = bc.CompareXML(xmlAntiguo, xmlTibco);

            //String nombre = cp.getOperaciones().get(i).getIdPeticion()+"_"+cp.getOperaciones().get(i).getMensajeDestino().get(j).getNombreDestino();
                String nombre = "";
                                               //Resultados result = new Resultados(nombre,json);
                                               //resultOperaciones.add(result);

                Gson gson = new Gson();
                                               String datos1 = gson.toJson(json);                                                                        
                                               String datos2 = json + ";" + json2;            
                                               String datos3 = gson.toJson(datos2);     
			 */
			return Response
					.status(Response.Status.OK)
					.entity(resultado)
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
