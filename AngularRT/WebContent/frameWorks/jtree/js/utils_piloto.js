// Elimina la parte izquierda de los campos a visualizar quedando solo la parte final (sin los puntos)
function quitarPuntosCamposApp(campoApp){
	return campoApp.substring(campoApp.lastIndexOf('.')+1);
}

// Variable de tipo Map en Javascript para guardar la configuracion de los campos para cada App
var mapCamposApp = new Object();
var mapCamposApp_nombres = new Object();

// Indica si el filtro de busqueda es visible
var filtroVisible = false;

// Guarda todas las apps
var listaCompletaApps = [];

// Guarda la lista actual de campos a mostrar (segun la App seleccionada)
var listaCamposAppActual = [];

// Perfil actual: filtrado de columnas en tabla
var perfilActual = "";

// Guarda los ultimos datos recibidos desde el Servidor (a traves de WebSocket)
var msg = "";
var time = null;

// Se invoca cuando cambia la seleccion de Perfil (Se pulsa el Boton)
function cambiarPerfil(enviarWs){
	
	// NOTA: multipleApps se carga desde el JSP
	
	// Recuperar lista de Aplicaciones
	//var multipleApps = $( '#div_profiles #select_apps' ).val() || [];
	var multipleAppsStr = multipleApps.join( ", " );
	
	// Info en la pagina
	//$('#infoApps').html("Perfil: " + multipleAppsStr);
	$('#infoApps').html(multipleAppsStr);
	
	// Enviar el cambio al Servlet a traves de WebSockets
	if (enviarWs){
   		if (multipleAppsStr !== '' && !(typeof multipleAppsStr === "undefined")){
       		console.log('Envio por WebSocket filtro apps: ' + multipleAppsStr);
       		ws.send('CAMBIAR_FILTRO_APP#'+multipleAppsStr);
   		}else{
       		console.log('Envio por WebSocket filtro apps: NINGUNA SELECCIONADA');
       		ws.send("QUITAR_FILTRO_APP#x");
   		}
    }
	
	// Actualizar lista de campos visibles de la tabla
	//console.log('updateListaCamposVisibles...');
	updateListaCamposVisibles();
	
	// Actualizar lista de campos del filtro de busqueda
	//console.log('updateFiltroBusquedaCampos...');
	updateFiltroBusquedaCampos();
}

// Actualiza la lista de campos visibles para el filtro de App seleccionado
function updateListaCamposVisibles(){
	
	// Vaciar lista actual
	listaCamposAppActual = [];
	
	// Vaciar la lista visual en la pantalla (que informa de los campos al usuario que ve la pantalla)
	$("#listaCamposVisibles_ul").html('');

	// Meter siempre en primer lugar los campos de COMMON
	$.each(mapCamposApp['COMMON'], function( indexCampoApp, campoApp ) {
		// Sumar este campo a la lista
		listaCamposAppActual.push(campoApp);
		// Mostrarlo visualmente (quitar los puntos)
		$("<li style='color:#0000FF'>" + quitarPuntosCamposApp(campoApp) + "</li>").appendTo("#listaCamposVisibles_ul");
	});
	
	// NOTA: multipleApps se carga desde el JSP
	
	// Recuperar lista de Aplicaciones seleccionadas
	//var multipleApps = $( '#div_profiles #select_apps' ).val() || [];
	
	// Recorrer la lista de Apps seleccionadas
	$.each(multipleApps, function( indexApp, app ) {
		//console.log('APP de filtro: ' + indexApp + ' - ' + app);

    	// Recorrer todos los campos para esta App
		$.each(mapCamposApp[app], function( indexCampoApp, campoApp ) {
			//console.log('   ' + app + '.' + indexCampoApp + ' = ' + campoApp);
			
			// Sumar este campo a la lista
			listaCamposAppActual.push(campoApp);
			
			// Mostrarlo visualmente (quitar los puntos)
			$("<li>" + quitarPuntosCamposApp(campoApp) + "</li>").appendTo("#listaCamposVisibles_ul");
		});
		
	});

}

// Implementa la funcion "replaceAll" de una cadena de texto
String.prototype.replaceAll = function(search, replace)
{
    //if replace is null, return original string otherwise it will
    //replace search string with 'undefined'.
    if(!replace) 
        return this;

    return this.replace(new RegExp('[' + search + ']', 'g'), replace);
};

// Actualizar lista de campos del filtro de busqueda
function updateFiltroBusquedaCampos(){

	// VIP: es preciso sustituir los "." por "_" en el ID del elemento HTML para poder realizar la seleccion jQuery
	var idCampo = '';
	
	// Marcar todos los elementos como NO visibles
	$.each(listaCompletaApps, function( indexApp, app ) {
    	// Recorrer todos los campos para esta App
		$.each(mapCamposApp[app], function( indexCampoApp, campoApp ) {
			idCampo = '#form_'+campoApp.replaceAll(".", "_");
			//console.log('Ocultar campo: ' + idCampo);
			$(idCampo).hide();
		});
		
	});
	
	// Marcar como visibles: en primer lugar los campos de COMMON
	$.each(mapCamposApp['COMMON'], function( indexCampoApp, campoApp ) {
		idCampo = '#form_'+campoApp.replaceAll(".", "_");
		//console.log('Mostrar campo: ' + idCampo);
		$(idCampo).show();
	});
	
	// NOTA: multipleApps se carga desde el JSP
	
	// Recuperar lista de Aplicaciones seleccionadas
	//var multipleApps = $( '#div_profiles #select_apps' ).val() || [];
	
	// Recorrer la lista de Apps seleccionadas
	$.each(multipleApps, function( indexApp, app ) {
    	// Marcar como visibles: Recorrer todos los campos para esta App
		$.each(mapCamposApp[app], function( indexCampoApp, campoApp ) {
			idCampo = '#form_'+campoApp.replaceAll(".", "_");
			//console.log('Mostrar campo: ' + idCampo);
			$(idCampo).show();
		});
		
	});

}

//Mostrar/Ocultar el filtro de busquedas
function cambiarEstadoFiltro(efectoSlide){
	if (filtroVisible){
		ocultarFiltro(efectoSlide);
		$('#botonCambioFiltro').html("Mostrar Filtro  <img src='imagenes/arrow_down.png' width='14' height='14' >");
	}else{
		mostrarFiltro(efectoSlide);
		$('#botonCambioFiltro').html("Ocultar Filtro  <img src='imagenes/arrow_up.png' width='14' height='14' >");
	}
}

//Ocultar el filtro de busquedas
function ocultarFiltro(efectoSlide){
	
	if (efectoSlide){
		$('#div_filter_inner').slideUp();
		$('#div_botonesFiltro').slideUp();
		//$('#div_show_filter').slideDown();
	}else{
		$('#div_filter_inner').hide();
		$('#div_botonesFiltro').hide();
		//$('#div_show_filter').show();
	}
	
	filtroVisible = false;
}

//Ocultar el filtro de busquedas
function mostrarFiltro(efectoSlide){
	
	if (efectoSlide){
		$('#div_filter_inner').slideDown();
		$('#div_botonesFiltro').slideDown();
		//$('#div_show_filter').slideUp();
	}else{
		$('#div_filter_inner').show();
		$('#div_botonesFiltro').show();
		//$('#div_show_filter').hide();
	}
	
	filtroVisible = true;
}

// Marcar/Desmarcar todos los deals reprocesables como marcados
function reprocessAll(){
	
	if( $('#checkReprocessAll').is(':checked')){
		// Seleccionar todos los checkBoxes de reprocesamiento (incluido checkReprocessAll)
		$('#div_resultados td :input[type="checkbox"]').attr('checked', true);
	}else{
		// Deseleccionar todos los checkBoxes de reprocesamiento (incluido checkReprocessAll)
		$('#div_resultados td :input[type="checkbox"]').attr('checked', false);
	}
	
}

// Generar la tabla HTML con la lista de mensajes
function pintarListaMensajes(){
	
	// Indicar el momento de actualizacion
	var timeStr = "";
	if (time != null){
		timeStr = time.toLocaleTimeString();
	}else{
		timeStr = "No existe registro temporal";
	}
	$('#timeUpdate').html("Última: "+timeStr);

	var text = "";

	if (msg.mensajes != null && msg.mensajes.length > 0){
		
		// Cabecera de tabla HTML
        text += "<table>";
        text += "<tr>";
        text += "<th></th>";
        text += "<th><input id='checkReprocessAll' type='checkbox' value='' onclick='reprocessAll();'/></th>";
		
		/* Los campos de la cabecera se componen dinamicamente
		   basandose en la lista de campos visibles por App.
		   Quitar puntos. */
		$.each(listaCamposAppActual, function( indexCampoApp, campoApp ) {
			// Modo1: usar la ultima parte del nombre tal cual esta definido en MongoDb
			//text += "<th>"+quitarPuntosCamposApp(campoApp)+"</th>";
			// Modo2: 
			text += "<th>"+mapCamposApp_nombres[campoApp]+"</th>";
		});
        
		text += "</tr>";
    	
    	// Recorrer la lista de mensajes recibidos
        for (i=0; i < msg.mensajes.length; i++) {
       		text += "<tr>";
       		text += "<td><b>" + (i+1) + "</b></td>";
       		text += "<td><input type='checkbox' value='" +msg.mensajes[i]['root']['TechnicalData']['Deal_Id'] +"'/></td>";
       		//text += "<td><input type='checkbox' value=''/></td>";
            
    		/* Los campos a recuperar para componer la tabla
    		   se componen dinamicamente basandose en la lista
    		   de campos visibles por App */
    		$.each(listaCamposAppActual, function( indexCampoApp, campoApp ) {
    			//text += "<td bgcolor=\"#F2F5A9\">" + msg.mensajes[i][campoApp] + "</td>";
    			
    			// Quedarnos con la parte final del campo (quitando los puntos)
    			var campoAppFinal = quitarPuntosCamposApp(campoApp);
    			
    			// Obtener el campo de la parte Tecnica y Funcional
    			var valorCampoTecnico = '';
    			var valorCampoFuncional = '';
    			
    			try{ valorCampoTecnico = msg.mensajes[i]['root']['TechnicalData'][campoAppFinal]; }
    			catch (error){
    				//console.log('Error controlado TechnicalData: ' + error);
    				valorCampoTecnico = '';
    			}
    			
    			try { valorCampoFuncional = msg.mensajes[i]['root']['FunctionalData'][campoAppFinal]; }
    			catch (error){
    				//console.log('Error controlado FunctionalData: ' + error);
    				valorCampoFuncional = '';
    			}
    			
    			//console.log('Campo           : ' + campoAppFinal);
    			//console.log('Valor Tecnico   : ' + valorCampoTecnico);
    			//console.log('Valor Funcional : ' + valorCampoFuncional);
    			
    			var valorFinal = '';
    			if (valorCampoTecnico !== '' && !(typeof valorCampoTecnico === "undefined")){
    				valorFinal = valorCampoTecnico;
    			}else if (valorCampoFuncional !== '' && !(typeof valorCampoFuncional === "undefined")){
    				valorFinal = valorCampoFuncional;
    			}else{
    				valorFinal = '';
    			}
    			
    			//console.log('Valor final     : ' + valorFinal);
    			valorFinal=trascodingSalida(valorFinal,campoApp);
    			text += "<td>" + valorFinal + "</td>";
			});
            
            text += "</tr>";
        }

        text += "</table>";
	}else{
		text = "<i>Sin datos que mostrar</i>";
	}
    
    if (text == null){
    	$('#messageTable').html('RESULTADO ES NULL!!');
    }else{
    	$('#messageTable').html(text);
    }
}

// Limpiar filtro de campos
function limpiarFiltroCampos(){
		
	// Enviar orden a servidor WebSocket
	ws.send('QUITAR_FILTRO_CAMPOS#x');
		
	// Limpiar campos del filtro
	$('#formFiltroCampos :input[type="text"]').val('');
	$('#formFiltroCampos select').val('');
	
	// Devolver false para que el submit no se envie
	return false;
}

// Cambiar filtro de campos
function cambiarFiltroCampos(){
	
	var argsCampos = '';
	
	// Recuperar lista de Aplicaciones seleccionadas
	var camposFiltro = $( '#formFiltroCampos div:visible' );
	var primeroTratado = false;
	
	// Recorrer la lista de Apps seleccionadas
	$.each(camposFiltro, function() {
		
		var labelCampo = $(this).find("input").attr('name'); // Debe ser el nombre COMPLETO del campo (no el corto)
		var textCampo = $(this).find("input").val();
		
		// Comprobamos si se trata de un campo select
		if (labelCampo === '' || (typeof labelCampo === "undefined")){
			labelCampo = $(this).find("select").attr('name');
		}
		if (textCampo === '' || (typeof textCampo === "undefined")){
			textCampo = $(this).find("select").val();
		}
		
		// Incluir solamente campos con valor en el texto de filtrado
		if (textCampo !== '' && !(typeof textCampo === "undefined")){
			
			if (primeroTratado){
				argsCampos += '#';
			}
			//TODO
			argsCampos += labelCampo+'#'+textCampo;
			primeroTratado = true;
		}
	});
	
	if (argsCampos == ''){
		// Control vacio
		//alert('Debe introducir valores en los campos de filtro');
		
		// Misma funcionalidad que limpiar filtro
		limpiarFiltroCampos();
	}else{
		// Enviar la orden al servidor WebSocket
		ws.send('CAMBIAR_FILTRO_CAMPOS#'+argsCampos);
	}
	
	// Ocultar los campos de filtro
	cambiarEstadoFiltro(true);
	
	// Devolver false para que el submit no se envie
	return false;
}

// Carga la configuracion de campos visibles para cada App
function cambiarFrecuenciaWebSocket(){
	
	var frecuencia = $( '#selectFrecuenciaWebsocket' ).val();
	
	// Enviar orden a servidor WebSocket
	ws.send('FREC_REFRESCO_WEBSOCKET#'+frecuencia);
	
	// Devolver false para que el submit no se envie
	return false;
}

//Puede ser array
function trascodingSalida(valorFinal,campoApp){
	
	var arraySeparado = valorFinal.toString().split(",");
	var salida = '';
	$.each(arraySeparado, function( indexApp, valor ) {
		
		if (salida.length > 0){
			salida += ',';
		}
		
		var resultado = trascodingSalidaSimple(valor, campoApp);
		salida += resultado;
	});
	
	return salida;
}

// Transcodificar texto
function trascodingSalidaSimple(valorFinal,campoApp){

	if (campoApp=="root.TechnicalData.Status")
	{
		switch (valorFinal){
			case 0:
			case "0":
				return "Pending";
			case 1:
			case "1":
				return "OK";
			case 2:
			case "2":
				return "Error";
			default:
				return valorFinal;
		}
	}
	if (campoApp=="root.TechnicalData.Source_System" || campoApp=="root.TechnicalData.Target_System" || campoApp=="root.TechnicalData.Error_Target")
	{
		switch (valorFinal){
			case 1:
			case "1":
				return "GTR";
			case 2:
			case "2":
				return "StarEu";
			case 3:
			case "3":
				return "Murex";
			case 4:
			case "4":
				return "MarkitWire";
			case 5:
			case "5":
				return "Calypso";
			case 6:
			case "6":
				return "Dashboard";
			case 7:
			case "7":
				return "Unknown";
			default:
				return valorFinal;
		}
	}
	
	return valorFinal;
}

//Devuelve la lista de operaciones seleccionadas de la lista
function obtenerOperacionesSeleccionadas() {
	var chek=$('#div_resultados :input[type="checkbox"]');
	var envio="";
	$.each(chek, function() {
		
		if( $(this).is(':checked')){
			
			// Controlar la primera
			if (envio.length > 0){
				envio +=  "#";
			}
			
			envio += $(this).val() ;
		}
		
	});
	return envio;
}

function reprocesing() {
	
	var envio=obtenerOperacionesSeleccionadas();
	
	if(envio!=""){
		ws.send('REPROCESING#'+envio);
	}else{
		alert('Debe selecionar al menos un proceso');
	}
	return false;
}

// Lanzar un caso de prueba
function relanzarCasoPrueba() {
	var envio=obtenerOperacionesSeleccionadas();
	
	if(envio!=""){
		
		// Orden WebSocket
		ws.send('LANZAR_CASO_PRUEBA#'+envio);
		
		// FechaHora
		var dt = new Date();
		var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
		
		// Info de la prueba
		$('#h3_ejecucion').html('Resultados de ejecucion del caso de prueba '+envio + ' ('+time+')');
		
	}else{
		alert('Debe selecionar al menos un proceso');
	}
	return false;
}
// Editar un caso de prueba
function editarCasoPrueba() {
	var envio=obtenerOperacionesSeleccionadas();
	
	if(envio!=""){
		ws.send('UPDATE_CASO_PRUEBA#'+envio);
	}else{
		alert('Debe selecionar al menos un proceso');
	}
	return false;
}
// Eliminar un caso de prueba
function eliminarCasoPrueba() {
	var envio=obtenerOperacionesSeleccionadas();
	
	if(envio!=""){
		ws.send('DELETE_CASO_PRUEBA#'+envio);
	}else{
		alert('Debe selecionar al menos un proceso');
	}
	return false;
}


// Generar la tabla HTML con la lista de casos de prueba
function pintarDefinicionCasosPrueba(){
	
	var text = "";
	var i = 0;

	if (msg.mensajes != null && msg.mensajes.length > 0){
		
		// Cabecera de tabla HTML
        text += "<table>";
        text += "<tr>";
        text += "<th></th>";
        text += "<th><input id='checkReprocessAll' type='checkbox' value='' onclick='reprocessAll();'/></th>";

        text += "<th>  Caso de prueba  </th>";
        text += "<th>  Descripción  </th>";
        text += "<th>  Operaciones  </th>";
        text += "<th>  Sistemas  </th>";
//        text += "<th>  Instrumento  </th>";
//        text += "<th>  Estado última ejecución  </th>";
        
		text += "</tr>";
    	
    	// Recorrer la lista de mensajes recibidos
        for (i=0; i < msg.mensajes.length; i++) {
       		text += "<tr>";
       		text += "<td><b>" + (i+1) + "</b></td>";
       		text += "<td><input type='checkbox' value='" +msg.mensajes[i]['root']['TechnicalData']['TestId'] +"'/></td>";
       		
       		var testId = msg.mensajes[i]['root']['TechnicalData']['TestId'];
       		
       		text += "<td>" + testId + "</td>";
       		text += "<td>" + msg.mensajes[i]['root']['TechnicalData']['TestDescription'] + "</td>";
       		text += "<td>" + msg.mensajes[i]['root']['TechnicalData']['Deal_Id'] + "</td>";
       		text += "<td>" + trascodingSalida(msg.mensajes[i]['root']['FunctionalData']['TargetSystem'], "root.TechnicalData.Target_System"); + "</td>";
//       		text += "<td>" + msg.mensajes[i]['root']['FunctionalData']['Instrument'] + "</td>";
//       		text += "<td><label class='estado_casoPrueba_parado' id='label_estado'>Parado</label></td>";
       		
            text += "</tr>";
        }

        text += "</table>";
	}else{
		text = "<i>Sin datos que mostrar</i>";
	}
    
    if (text == null){
    	$('#messageTable').html('RESULTADO ES NULL!!');
    }else{
    	$('#messageTable').html(text);
    }
}
function pintarEjecucionesCasosPrueba(){
	
	var text = "";
	var i = 0;

	if (msg.ejecuciones != null && msg.ejecuciones.length > 0){
		
		// Cabecera de tabla HTML
        text += "<table>";
        text += "<tr>";
        text += "<th></th>";

        text += "<th>  Num Boleta  </th>";
        text += "<th>  Instrumento  </th>";
        text += "<th>  Mesa  </th>";
        text += "<th>  Portfolio  </th>";
        text += "<th>  Evento  </th>";
        text += "<th>  Descripcion de la ultima prueba  </th>";
        text += "<th>  Ver resultados </th>";
        text += "<th>  Estado </th>";
        
		text += "</tr>";
    	
    	// Recorrer la lista de mensajes recibidos
        for (i=0; i < msg.ejecuciones.length; i++) {
       		text += "<tr>";
       		text += "<td><b>" + (i+1) + "</b></td>";
       		
       		var testId = msg.ejecuciones[i]['root']['TechnicalData']['Deal_Id'];
       		
       		try{text += "<td>" + testId + "</td>";}
       		catch (Exception){text += "<td>Sin Datos</td>";}

       		try{text += "<td>" + msg.ejecuciones[i]['root']['FunctionalData']['Instrument'] + "</td>";}
       		catch (Exception){text += "<td>Sin Datos</td>";}

       		try{text += "<td>" + msg.ejecuciones[i]['root']['FunctionalData']['Desk'] + "</td>";}
       		catch (Exception){text += "<td>Sin Datos</td>";}

       		try{text += "<td>" + msg.ejecuciones[i]['root']['FunctionalData']['Portfolio'] + "</td>";}
       		catch (Exception){text += "<td>Sin Datos</td>";}

       		try{text += "<td>" + msg.ejecuciones[i]['root']['FunctionalData']['Version'] + "</td>";}
       		catch (Exception){text += "<td>Sin Datos</td>";}
       		
			var labelDesPrueba  = "label_desPrueba_"+testId;
  			var fotoDesPrueba   = "foto_desPrueba_"+testId;
  			var estadoDesPrueba = "label_estado_"+testId;

       		text += "<td width='350px'><label class='des_casoPrueba_"+testId+"' id='"+labelDesPrueba+"'></label></td>";
       		text += "<td class='foto_casoPrueba_"+testId+"' id='"+fotoDesPrueba+"'></td>";
       		text += "<td width='60px'><label class='estado_casoPrueba_parado_"+testId+"' id='"+estadoDesPrueba+"'>Parado</label></td>";
       		
            text += "</tr>";
        }

        text += "</table>";
	}else{
		text = "<i>Sin datos que mostrar</i>";
	}
    
    if (text == null){
    	$('#messageTable_lanzamientos').html('RESULTADO ES NULL!!');
    	$('#messageTable_lanzamientos').hide();
    }else{
    	
    	// Setear al contenido de la tabla
    	$('#messageTable_lanzamientos').html(text);
    	
    	// Ocultar y volver a mostrar con un efecto animado
    	$('#messageTable_lanzamientos').hide();
    	var options = {};
    	$('#messageTable_lanzamientos').show( 'blind', options, 1500 );
    	
    }
}