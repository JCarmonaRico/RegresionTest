
// Enviar al Servlet la orden de crear caso de prueba
function enviarOrdenCrearCasoPrueba(idCasoPrueba, desCasoPrueba, listaOperaciones) {
	
	if(idCasoPrueba!="" && desCasoPrueba!="" && listaOperaciones !=""){
		ws.send('CREAR_CASO_PRUEBA#'+idCasoPrueba+'#'+desCasoPrueba+'#'+listaOperaciones);
		$( '#dialog-modal-okCasoPrueba' ).dialog( 'open' );
	}else{
		$( '#dialog-modal-errorCasoPrueba' ).dialog( 'open' );
	}
}

// DIALOGO: crear caso de prueba

function lanzarCrearCasoPrueba(){
	if (obtenerOperacionesSeleccionadas() === ""){
		// Warning: debe seleccionarse una operacion
		$( '#dialog-modal-warningCasoPrueba' ).dialog( 'open' );
	}else{
		// OK: lanzar la ventana de creacion de caso de prueba
		$( '#dialog-form-crearCasoPrueba' ).dialog( 'open' );
	}
}

$(function() {
    var idCasoPrueba = $( "#idCasoPrueba" ),
      desCasoPrueba = $( "#desCasoPrueba" ),
      allFields = $( [] ).add( idCasoPrueba ).add( desCasoPrueba ),
      tips = $( ".validateTips" );
 
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
    function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Longitud de " + n + " debe estar entre " +
          min + " y " + max + "." );
        return false;
      } else {
        return true;
      }
    }
 
    function checkRegexp( o, regexp, n ) {
      if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
      } else {
        return true;
      }
    }
 
    $( "#dialog-form-crearCasoPrueba" ).dialog({
      autoOpen: false,
      height: 300,
      width: 350,
      modal: true,
      buttons: {
        "Crear": function() {
          var bValid = true;
          allFields.removeClass( "ui-state-error" );
 
          bValid = bValid && checkLength( idCasoPrueba, "idCasoPrueba", 2, 20 );
          bValid = bValid && checkLength( desCasoPrueba, "desCasoPrueba", 5, 50 );
 
          bValid = bValid && checkRegexp( idCasoPrueba, /^[a-zA-Z]([0-9a-z_])+$/i, "ID de prueba debe ser alfanumerico sin espacios empezando con letra" );
          bValid = bValid && checkRegexp( desCasoPrueba, /^[a-zA-Z]([0-9a-z_ ])+$/i, "ID de prueba debe ser alfanumerico empezando con letra" );
          
          if ( bValid ) {
        	  // Procesar la orden de crear caso de prueba
        	  enviarOrdenCrearCasoPrueba(idCasoPrueba.val(), desCasoPrueba.val(), obtenerOperacionesSeleccionadas());
        	  // Cerrar la ventana
            $( this ).dialog( "close" );
          }
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      },
      open: function() {
    	  
    	  // Acciones a realizar al abrir la ventana
    	  
      },
      close: function() {
          allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
  });


// DIALOGO: confirmar creacion de caso de prueba

$(function() {
 $( "#dialog-modal-okCasoPrueba" ).dialog({
   autoOpen: false,
   height: 140,
   modal: true,
   open: function() {
 	  // Acciones a realizar al abrir la ventana
   },
   close: function() {
	  // Acciones a realizar al cerrar la ventana
   }
 });
});
$(function() {
 $( "#dialog-modal-errorCasoPrueba" ).dialog({
   autoOpen: false,
   height: 140,
   modal: true,
   open: function() {
 	  // Acciones a realizar al abrir la ventana
   },
   close: function() {
	  // Acciones a realizar al cerrar la ventana
   }
 });
});
$(function() {
	 $( "#dialog-modal-warningCasoPrueba" ).dialog({
	   autoOpen: false,
	   height: 140,
	   modal: true,
	   open: function() {
	 	  // Acciones a realizar al abrir la ventana
	   },
	   close: function() {
		  // Acciones a realizar al cerrar la ventana
	   }
	 });
	});

// Info de lanzamiento de un caso de prueba
function lanzarInfoCasoPrueba(){
	
	
	$( '#dialog-modal-infoCasoPrueba' ).dialog( 'open' );

	
	
	$('#jstree_demo_div').jstree({ 'core' : {
	    'data' : [
	       {
	         'text' : 'Resultados Test Case TC1',
	         'children' : [
	           { 'text' : 'personas[1]',
	  	         'children' : [
	  	      	           { 'text' : 'persona[1]',
	  	    	  	         'children' : [
	  	    		  	      	           { 'text' : 'nombre',
	  	    		  	      	        	 'icon' : 'imagenes/files_icon.png',
	  	    		  	    	  	         'children' : [
	  	    		  	    		  	      	           { 'text' : 'Valor esperado: Pepe',
		  	    		  	    		  	      	         'icon' : 'imagenes/ok_icon.png' },
	  	    		  	    		  	      	           { 'text' : 'Valor encontrado: Manolo',
			  	    		  	    		  	      	         'icon' : 'imagenes/error_icon.png' }
	  	    		  	    		  	      	           	] },
	  	    		  	      	           { 'text' : 'edad',
	  	    			  	    		  	 'icon' : 'imagenes/files_icon.png',
		  	    		  	    	  	         'children' : [
		  	    		  	    		  	      	           { 'text' : 'Valor esperado: 25',
				  	    		  	    		  	      	         'icon' : 'imagenes/ok_icon.png' },
		  	    		  	    		  	      	           { 'text' : 'Valor encontrado: 21',
				  	    		  	    		  	      	         'icon' : 'imagenes/error_icon.png' }
		  	    		  	    		  	      	           	] }
	  	    		  	      	           	] },
	  	      	           { 'text' : 'persona[2]',
	  	    	  	         'children' : [
	  	    		  	      	           { 'text' : 'nombre',
		  	    			  	    		  	 'icon' : 'imagenes/files_icon.png',
		  	    		  	    	  	         'children' : [
		  	    		  	    		  	      	           { 'text' : 'Valor esperado: Eva',
				  	    		  	    		  	      	         'icon' : 'imagenes/ok_icon.png' },
		  	    		  	    		  	      	           { 'text' : 'Valor encontrado: pepin',
				  	    		  	    		  	      	         'icon' : 'imagenes/error_icon.png' }
		  	    		  	    		  	      	           	] },
	  	    		  	      	           { 'text' : 'edad',
		  	    			  	    		 'icon' : 'imagenes/files_icon.png',
		  	    		  	    	  	         'children' : [
		  	    		  	    		  	      	           { 'text' : 'Valor esperado: 29',
				  	    		  	    		  	      	         'icon' : 'imagenes/ok_icon.png' },
		  	    		  	    		  	      	           { 'text' : 'Valor encontrado: 123',
				  	    		  	    		  	      	         'icon' : 'imagenes/error_icon.png' }
		  	    		  	    		  	      	           	] }
	  	    		  	      	           	] }
	  	      	           	]
	           }
	         ]
	      }
	    ]
	} });
	
}
$(function() {
	 $( "#dialog-modal-infoCasoPrueba" ).dialog({
	   autoOpen: false,
	   height: 200,
	   width: 750,
	   modal: true,
	   open: function() {
	 	  // Acciones a realizar al abrir la ventana
	   },
	   close: function() {
		  // Acciones a realizar al cerrar la ventana
	   }
	 });
	});
