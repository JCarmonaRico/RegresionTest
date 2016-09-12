var regresiontest = angular.module('AngularRT', []);

regresiontest.config(function ($httpProvider) {
	  $httpProvider.defaults.headers.common = {};
	  $httpProvider.defaults.headers.post = {};
	  $httpProvider.defaults.headers.put = {};
	  $httpProvider.defaults.headers.patch = {};
	});
//------------------------------ Controlador para el Login ------------------------------
regresiontest.controller("loginController", function($scope, $http) {
	
	// -------- VARIABLES -------- //
	$scope.response;
	$scope.nombre = "";
	$scope.pass = "";
	$scope.profile = "";
	
	//Funcion encargada de buscar en la BBDD por usuario/contraseña y devolver el profile
	$scope.loginUser = function() {
		var nombre = $scope.nombre;
		var pass = $scope.pass;
		var perfil;
		

		$http({
			  method: 'POST',
			  url: 'http://172.18.215.121:8290/LoginRT/services/serviciologin/login',
			  data: "{'nombre':'"+ nombre +"','pass':'"+ pass +"'}"
			}).then(function successCallback(response) {
				
				perfil = response.data.perfil;
				sessionStorage.setItem("perfil", $scope.pass);
				if(perfil=="Config"){
					var indexWindow= window.open("vistaAltaNueva.html",target="_self"); 
				}else if(perfil=="Tester"){
					var indexWindow= window.open("vistaEjecucion.html",target="_self"); 
				}else{
					var indexWindow= window.open("index.html",target="_self"); 
				}
			  }, function errorCallback(response) {				  
				  console.log("POST FALLO LOGIN");
				  window.alert("LOGIN O CONTRASEÑA ERRONEOS"); 
			  });
	}	
});

//------------------------------ Controlador para menu principal ------------------------------

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
	  var classes = event.target.className.split(' ');
	  var found = false; var i = 0;
	  while (i < classes.length && !found) {
	      if (classes[i]=='dropbtn') found = true;
	      else ++i;
	  }
	  if (!found) {
	    var dropdowns = document.getElementsByClassName("dropdown-content");
	    var i;
	    for (i = 0; i < dropdowns.length; i++) {
	      var openDropdown = dropdowns[i];
	      if (openDropdown.classList.contains('show')) {
	        openDropdown.classList.remove('show');
	      }
	    }
	  }
	}

// Close the dropdown menu if the user clicks outside of it
/* window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}*/

//------------------------------ Controlador para el Filtrado ------------------------------
 regresiontest.controller("filtroController" , function($scope,$http,$compile){
	 
	 $scope.logOut = function() {
			var indexWindow= window.open("index.html",target="_self");
		} 
	 
	 $scope.vaciarCamposAlta = function() {
			$scope.response;
			$scope.idOperacion = "";
			$scope.estadoPeticion = "";
			$scope.fecaPeticionDesde = "";
			$scope.idTransaccion = "";
			$scope.origen = "";
			$scope.fechaPeticionHasta = "";
			$scope.instrumento = "";
			$scope.destino = "";
			$scope.accion = "";
			$scope.entorno = "";
	
			$scope.nombreCaso = "";
			$scope.responseOperaciones = [];
			$scope.opCasosPrueba = [];
			$scope.master;
			$scope.seleccionados = [];
			$scope.nombre = "";
			$scope.descripcion = "";
			$scope.resultadosJson = "";
			$scope.nombreResultado = "";
			$scope.nombreCasoOperacionesMostradas = "";
			$scope.nomCaso = "";
		} 
	 
	 
	 	$scope.response;
		$scope.idOperacion = "";
		$scope.estadoPeticion = "";
		$scope.fecaPeticionDesde = "";
		$scope.idTransaccion = "";
		$scope.origen = "";
		$scope.fechaPeticionHasta = "";
		$scope.instrumento = "";
		$scope.destino = "";
		$scope.accion = "";
		$scope.entorno = "";
		
		$scope.idOperacionModificar = "";
		$scope.estadoPeticionModificar = "";
		$scope.fecaPeticionDesdeModificar = "";
		$scope.idTransaccionModificar = "";
		$scope.origenModificar = "";
		$scope.fechaPeticionHastaModificar = "";
		$scope.instrumentoModificar = "";
		$scope.destinoModificar = "";
		$scope.accionModificar = "";
		$scope.entornoModificar = "";
		$scope.nombreModificar = "";
		$scope.descripcionModificar = "";
		$scope.responseOperacionesModificar = []
		$scope.idCasoPrueba = "";
		
		$scope.master;
		$scope.seleccionados = [];
		$scope.seleccionadosModificar = [];
		$scope.entornosModificar = [];
		$scope.entornos = [];
		$scope.nombre = "";
		$scope.descripcion = "";
		$scope.descrip = "";
		$scope.showMenuOption = 1;
		$scope.showMenuPestana = 0;
		$scope.mostrarBarraPestanasResultados = 0;
		$scope.resultadosJson = "";
		$scope.nombreResultado = "";
		$scope.listaResultadosGuardados = [];
		$scope.idResultadoSeleccionado = -1;
		$scope.nombreCasoOperacionesMostradas = "";
		$scope.nomCaso = "";
		$scope.opCasosPruebaAModificar = [];
		$scope.enviarADestinoFinal = [];
		
		//BOTONES VISTA ALTA
		$scope.botonAbrirCerrarFiltrosAlta = "-"; //Comenzamos mostrando los parametros de filtrado
		$scope.botonAbrirCerrarListaPeticionesAlta = "+";
		$scope.botonAbrirCerrarCasosPruebaAlta = "+";
		
		//BOTONES VISTA MODIFICAR
		$scope.botonAbrirCerrarFiltrosModificar = "-"; //Comenzamos mostrando los parametros de filtrado
		$scope.botonAbrirCerrarListaPeticionesModificar = "+";
		$scope.botonAbrirCerrarCasosPruebaModificar = "+";
		
		//BOTONES VISTA CONSULTA
		$scope.botonAbrirCerrarFiltrosConsulta = "-"; //Comenzamos mostrando los parametros de filtrado
		$scope.botonAbrirCerrarCasosPruebaConsultaOperaciones = "+";
		$scope.botonAbrirCerrarCasosPruebaConsulta = "+";
		
		//BOTONES VISTA EJECUCION
		$scope.botonAbrirCerrarFiltrosEjecucion = "-"; //Comenzamos mostrando los parametros de filtrado
		$scope.botonAbrirCerrarCasosPruebaEjecucion = "+";
		
		//BOTONES VISTA RESULTADO
		$scope.botonAbrirCerrarListaResultadosGuardados = "-"; //Comenzamos mostrando los parametros de filtrado
		$scope.botonAbrirCerrarResultadoEjecucion = "+";
		
		
	 
	$scope.filtrarOperaciones = function(){
	var idOperacion = $scope.idOperacion;
	var estadoPeticion = $scope.estadoPeticion;
	var fechaPeticionDesde =$scope.fechaPeticionDesde; 
	var idTransaccion = $scope.idTransaccion;
	var origen = $scope.origen;
	var fechaPeticionHasta = $scope.fechaPeticionHasta;
	var instrumento = $scope.instrumento;
	var destino = $scope.destino;
	var accion = $scope.accion;
	var entorno = $scope.entorno;
	$scope.abrirCerrarFiltrosAlta();
	$scope.abrirCerrarListaPeticionesAlta();
	
	

		$http({
			  method: 'POST',
			  url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioFiltrar/Filtrar',
			  data: "{'idOperacion':'"+ idOperacion +"'," +
			  		"'estadoPeticion':'"+ estadoPeticion +"',"+
			  		"'fechaPeticionDesde':'"+ fechaPeticionDesde +"',"+
			  		"'idTransaccion':'"+ idTransaccion +"',"+
			  		"'origen':'"+ origen +"',"+
			  		"'fechaPeticionHasta':'"+ fechaPeticionHasta +"',"+
			  		"'instrumento':'"+ instrumento +"',"+
			  		"'destino':'"+ destino +"',"+
			  		"'entorno':'"+ entorno +"',"+
			  		"'accion':'"+ accion +"'}"	
			  		
			}).then(function successCallback(response) {
				
				$scope.responseOperaciones = response.data;
				for (var int = 0; int < $scope.responseOperaciones.length; int++) {
					$scope.seleccionados[int] = false;
				}
				
				$scope.comprobarIncluidos();
				
			  }, function errorCallback(response) {				  
				  console.log("POST FALLO FILTRAR OPERACIONES ALTA");
			  });
	}	
	
	$scope.filtrarOperacionesModificar = function(){
		var idOperacion = $scope.idOperacionModificar;
		var estadoPeticion = $scope.estadoPeticionModificar;
		var fechaPeticionDesde =$scope.fechaPeticionDesdeModificar; 
		var idTransaccion = $scope.idTransaccionModificar;
		var origen = $scope.origenModificar;
		var fechaPeticionHasta = $scope.fechaPeticionHastaModificar;
		var instrumento = $scope.instrumentoModificar;
		var destino = $scope.destinoModificar;
		var accion = $scope.accionModificar;
		var entorno = $scope.entornoModificar;
		$scope.abrirCerrarListaPeticionesModificar();
		
		

			$http({
				  method: 'POST',
				  url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioFiltrar/Filtrar',
				  data: "{'idOperacion':'"+ idOperacion +"'," +
				  		"'estadoPeticion':'"+ estadoPeticion +"',"+
				  		"'fechaPeticionDesde':'"+ fechaPeticionDesde +"',"+
				  		"'idTransaccion':'"+ idTransaccion +"',"+
				  		"'origen':'"+ origen +"',"+
				  		"'fechaPeticionHasta':'"+ fechaPeticionHasta +"',"+
				  		"'instrumento':'"+ instrumento +"',"+
				  		"'destino':'"+ destino +"',"+
				  		"'entorno':'"+ entorno +"',"+
				  		"'accion':'"+ accion +"'}"	
				  		
				}).then(function successCallback(response) {
					
					$scope.responseOperacionesModificar = response.data;
					for (var int = 0; int < $scope.responseOperacionesModificar.length; int++) {
						$scope.seleccionadosModificar[int] = false;
					}
					
					$scope.comprobarIncluidosModificar();
					
				  }, function errorCallback(response) {				  
					  console.log("POST FALLO FILTRAR OPERACIONES MODIFICAR");
				  });
		}	
	
	$scope.buscarPruebas = function(){
		var nombre = $scope.nombre;
		var descripcion = $scope.descrip;
		$scope.abrirCerrarFiltrosEjecucion();
		$scope.abrirCerrarCasosPruebaEjecucion();
		
			$http({
				  method: 'POST',
				  url: 'http://172.18.215.121:8290/EjecucionRT/services/serviciosPruebas/buscarCasosPrueba',
				  data: "{'nombre':'"+ nombre +"'," +
				  		"'descripcion':'"+ descripcion +"'}"	
				  		
				}).then(function successCallback(response) {
					
					$scope.responseCasosPrueba = response.data;
					
					for (var int = 0; int < $scope.responseCasosPrueba.length; int++) {
						$scope.enviarADestinoFinal[int] = false;
					}					
					
				  }, function errorCallback(response) {				  
					  console.log("POST FALLO BUSCAR CASOS PRUEBA EJECUCION");
				  });
		}
	
	$scope.ejecutarPrueba = function(id,enviarADestinoFinal){
		var idCasoPrueba = id;
		var destinoFinal = enviarADestinoFinal;
		$scope.showMenuOption = 4;
		
			$http({
				  method: 'POST',
				  url: 'http://172.18.215.121:8290/EjecucionRT/services/serviciosPruebas/ejecutarCasoPrueba',
				  data: "{'idCasoPrueba':'"+ idCasoPrueba +"'," +
			  		"'destinoFinal':'"+ destinoFinal +"'}"
				  		
				}).then(function successCallback(response) {
					$scope.resultadosJson = response.data;
					var resultados = response.data.split(";");
					vaciaDiv();
					for (var int = 0; int < resultados.length; int++) {
						var html="<li id='pestana" + int + "' ng-class='{active:showMenuPestana==" + int + "}'><a ng-click='showMenuPestana=" + int + "'>OPERACION_" + int + "</a></li>";
						angular.element(document.getElementById('pestanasResultados')).append($compile(html)($scope))
						  
						var jsonId = "jsonviews" + int;
						var jsonIdElement = "#"+jsonId;
						var divContent="<div id='" + jsonId + "' ng-show='showMenuPestana==" + int + "'></div>";
						angular.element(document.getElementById('json')).append($compile(divContent)($scope))
						verJson(resultados[int],int,jsonIdElement);
					}
					$scope.mostrarBarraPestanasResultados = 1;
				  }, function errorCallback(response) {				  
					  console.log("POST FALLO EJECUTAR PRUEBA");
				  });
		}
	
	$scope.guardarResultado = function(){
		var nombre = $scope.nombreResultado;
		var resultadoAux = $scope.resultadosJson;
		var resultado = resultadoAux.replace(/'/g,"\\'");
	
			$http({
				  method: 'POST',
				  url: 'http://172.18.215.121:8290/ResultadoRT/services/servicioResultado/guardarResultado',
				  data: "{'nombre':'"+ nombre +"'," +
			  		"'resultado':'"+ resultado +"'}"	
				  		
				}).then(function successCallback(response) {
					console.log("EXITO " + nombre);
					$scope.nombreResultado = "";
					//$scope.listaResultadosGuardados.push(nombre);
					$scope.cargarListadoResultadosGuardados();
					
				  }, function errorCallback(response) {				  
					  console.log("POST FALLO GUARDAR RESULTADO PRUEBA");
				  });
			
			
		}
	
	$scope.cargarListadoResultadosGuardados = function(){
		var nombre = $scope.nombreResultado;
		var resultadoAux = $scope.resultadosJson;
		var resultado = resultadoAux.replace(/'/g,"\\'");
	
			$http({
				  method: 'POST',
				  url: 'http://172.18.215.121:8290/ResultadoRT/services/servicioResultado/cargarListadoResultadosGuardados'	
				}).then(function successCallback(response) {
					$scope.listaResultadosGuardados = response.data;
					
				  }, function errorCallback(response) {				  
					  console.log("POST FALLO CARGAR LISTADO RESULTADOS GUARDADOS");
				  });
			
			
		}


	$scope.modificarCasoPrueba = function(ope){
		$scope.idCasoPrueba = ope.idCasoPrueba;
		var id = ope.idCasoPrueba;
		var nombreCaso = $scope.nomCaso;
		var descripcion = $scope.descripcion;
		$scope.showMenuOption = 5;
		$scope.nombreCasoOperacionesMostradas = ope.nombreCasoPrueba;

         $http({
                  method: 'POST',
                  url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioRecuperarOperaciones/recuperarOperaciones',
                  data: "{'Id':'"+ id +"'}"                                          
                                                                                                                                                                                                                                                             
                }).then(function successCallback(response) {
                       
                       $scope.opCasosPruebaAModificar = response.data;
                       
                       for (var int = 0; int < $scope.responseOperacionesModificar.length; int++) {
	   						$scope.seleccionadosModificar[int] = false;
	   					}
	   					
	   					
	   					$scope.comprobarIncluidosModificar();
                       
                  }, function errorCallback(response) {                        
                         console.log("POST FALLO CARGAR OPERACIONES CASO PRUEBA A MODIFICAR");
                  });
		}
	
	
	$scope.visualizarPrueba = function(idResultadoPrueba){
		if(idResultadoPrueba!=null && idResultadoPrueba != "" && idResultadoPrueba != -1){
			$scope.abrirCerrarListaResultadosGuardados();
			$http({
				  method: 'POST',
				  url: 'http://172.18.215.121:8290/ResultadoRT/services/servicioResultado/visualizarResultadosGuardados',
				  data: "{'idResultadoPrueba':'"+ idResultadoPrueba +"'}"	
				  		
				}).then(function successCallback(response) {
					$scope.resultadosJson = response.data.resultadoJson;
					var resultados = response.data.resultadoJson.split(";");
					vaciaDiv();
					for (var int = 0; int < resultados.length; int++) {
						var html="<li id='pestana" + int + "' ng-class='{active:showMenuPestana==" + int + "}'><a ng-click='showMenuPestana=" + int + "'>OPERACION_" + int + "</a></li>";
						angular.element(document.getElementById('pestanasResultados')).append($compile(html)($scope))
						  
						var jsonId = "jsonviews" + int;
						var jsonIdElement = "#"+jsonId;
						var divContent="<div id='" + jsonId + "' ng-show='showMenuPestana==" + int + "'></div>";
						angular.element(document.getElementById('json')).append($compile(divContent)($scope))
						verJson(resultados[int],int,jsonIdElement);
					}
					$scope.mostrarBarraPestanasResultados = 1;
				  }, function errorCallback(response) {				  
					  console.log("POST FALLO VISUALIZAR PRUEBA");
				  });
		}
	}
	
	
	$scope.eliminarResultado = function(idResultadoPrueba){
		if(idResultadoPrueba!=null && idResultadoPrueba != "" && idResultadoPrueba != -1){
			$http({
				  method: 'POST',
				  url: 'http://172.18.215.121:8290/ResultadoRT/services/servicioResultado/eliminarResultadosGuardados',
				  data: "{'idResultadoPrueba':'"+ idResultadoPrueba +"'}"	
				  		
				}).then(function successCallback(response) {
					console.log("EXITO ");
					$scope.cargarListadoResultadosGuardados();
				  }, function errorCallback(response) {				  
					  console.log("POST FALLO ELIMINAR RESULTADO GUARDADO");
				  });
			$scope.idResultadoSeleccionado = -1;
		}
	}
	
	$scope.actualizarListadoResultados = function(nombre){
		$scope.listaResultadosGuardados.push(nombre);
	}
	
	
	//------------------------------ Metodo para el combo de Instrumento ------------------------------  
    
    $http({
            method: 'GET',
            url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioComboInstrumento/instrumento'
          }).then(function successCallback(response) {
                 $scope.opcion = response.data;          
                 $scope.opcionModificar = response.data;     
                  
            }, function errorCallback(response) {                       
                    console.log("GET FALLO CARGAR COMBO INSTRUMENTO");
            });
    
    
    
  //------------------------------ Controlador para el combo del Entorno ------------------------------ 
    $http({
              method: 'GET',
              url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioComboEntorno/entorno'
            }).then(function successCallback(response) {
                   $scope.entornos = response.data;   
                   $scope.entornosModificar = response.data;   
                   
              }, function errorCallback(response) {                       
                     console.log("GET FALLO CARGAR COMBO ENTORNO");
              }); 

//------------------------------ Metodo para el combo de Origen ------------------------------
   $http({
            method: 'GET',
            url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioComboOrigen/origen'
          }).then(function successCallback(response) {
                 $scope.origenes = response.data;
                 $scope.origenesModificar = response.data; 
                  
            }, function errorCallback(response) {                       
                    console.log("GET FALLO CARGAR COMBO ORIGEN");
            });
   
    
//------------------------------ Controlador para el combo de Destino ------------------------------ 
   $http({
             method: 'GET',
             url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioComboDestino/destino'
           }).then(function successCallback(response) {
                  $scope.destinos = response.data;   
                  $scope.destinosModificar = response.data;   
                  
             }, function errorCallback(response) {                       
                    console.log("GET FALLO CARGAR COMBO DESTINO");
             });
    


//------------------------------ Controlador Añadir ------------------------------    

            $scope.opCasosPrueba=[];
           
            
            $scope.mostrar = function (ope,index) {
                  
                if ($scope.seleccionados[index]) {
                   $scope.opCasosPrueba.push(ope);
                }else {
                	for (var int = 0; int < $scope.opCasosPrueba.length; int++) {
						if($scope.opCasosPrueba[int].requestId==ope.requestId){
							$scope.opCasosPrueba.splice(int, 1);
						}
					}
                    //var index = $scope.opCasosPrueba.indexOf(ope.requestId);
                    //$scope.opCasosPrueba.splice(index, 1);
                }
           
           } 
            
            
           
            
            $scope.mostrarModificar = function (ope,index) {
                  
                if ($scope.seleccionadosModificar[index]) {
                   $scope.opCasosPruebaAModificar.push(ope);
                }else {
                	for (var int = 0; int < $scope.opCasosPruebaAModificar.length; int++) {
						if($scope.opCasosPruebaAModificar[int].requestId==ope.requestId){
							$scope.opCasosPruebaAModificar.splice(int, 1);
						}
					}
                    //var index = $scope.opCasosPruebaAModificar.requesId.indexOf(ope.requestId);
                    //$scope.opCasosPruebaAModificar.splice(index, 1);
                }
           
           } 
            
            //------------------------------ Controlador Eliminar ------------------------------   
                                                                 
            $scope.eliminar = function (ope,index) {    
            	
            	for (var int = 0; int < $scope.opCasosPrueba.length; int++) {
					if($scope.opCasosPrueba[int].requestId==ope.requestId){
						$scope.opCasosPrueba.splice(int, 1);
					}
				}   
            	for (var int = 0; int < $scope.responseOperaciones.length; int++) {
					if($scope.responseOperaciones[int].requestId==ope.requestId){
						$scope.seleccionados[int] = false;
					}
				}  
            } 
            
            //------------------------------ Controlador Eliminar Modificar ------------------------------   
            
            $scope.eliminarModificar = function (ope,index) {      
            	
            	for (var int = 0; int < $scope.opCasosPruebaAModificar.length; int++) {
					if($scope.opCasosPruebaAModificar[int].requestId==ope.requestId){
						$scope.opCasosPruebaAModificar.splice(int, 1);
					}
				}     
            	
            	for (var int = 0; int < $scope.responseOperacionesModificar.length; int++) {
					if($scope.responseOperacionesModificar[int].requestId==ope.requestId){
						$scope.seleccionadosModificar[int] = false;
					}
				}    
            } 
            
          //------------------------------ Controlador Comprobar Incluidos ------------------------------   
            
            $scope.comprobarIncluidos = function (ope,index) {    
            	
            	for (var int = 0; int < $scope.opCasosPrueba.length; int++) {
            		for (var j = 0; j < $scope.responseOperaciones.length; j++) {
            			if($scope.opCasosPrueba[int].requestId==$scope.responseOperaciones[j].requestId){
            				$scope.seleccionados[j] = true;
    					}
            		}
				}   
            	
            } 
            
//------------------------------ Controlador Comprobar Incluidos Modificar ------------------------------   
            
            $scope.comprobarIncluidosModificar = function (ope,index) {    
            	
            	for (var int = 0; int < $scope.opCasosPruebaAModificar.length; int++) {
            		for (var j = 0; j < $scope.responseOperacionesModificar.length; j++) {
            			if($scope.opCasosPruebaAModificar[int].requestId==$scope.responseOperacionesModificar[j].requestId){
            				$scope.seleccionadosModificar[j] = true;
    					}
            		}
				}   
            	
            } 
            
           
  //------------------------------ 172.18.215.121:8290 Caso ------------------------------
           
            $scope.guardarCasoPrueba = function(){
                         var nombreCaso = $scope.nombreCaso;
                         var descripcion = $scope.descripcion;
                         var entorno = $scope.entorno;
                         var ope = $scope.opCasosPrueba;
                         var index=ope.length;
                         var prueba="";
                  
                           angular.forEach($scope.opCasosPrueba, function(ope){                          
                                 prueba = prueba + ope.requestId + ";" + ope.instrumento + ";" + ope.origen +";";                                      
                             })

                               $http({
                                        method: 'POST',
                                        url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioGuardarCaso/GuardarCaso',
                                        data: "{'nombreCaso':'"+ nombreCaso +"'," +
                                                   "'operaciones':'"+ prueba +"',"+  
                                                   "'entorno':'"+ entorno +"',"+     
                                                   "'descripcion':'"+ descripcion +"'}"
                                                    
                                                   
                                      }).then(function successCallback(response) {
                                             
                                             //vista o mensaje de insercion correcta
                                    	  		window.alert("CASO DE PRUEBA GUARDADO CON EXITO");
                                    	  		$scope.vaciarCamposAlta();
                                             
                                        }, function errorCallback(response) {                        
                                               console.log("POST FALLO GUARDAR CASO PRUEBA");
                                        });
           }
            
            //------------------------------ Actualizar Caso ------------------------------
            
            $scope.actualizarCasoPrueba = function(){
        		 var id = $scope.idCasoPrueba;
		         var nombreCaso = $scope.nombreCasoModificar;
		         var descripcion = $scope.descripcionModificar;
		         var entorno = $scope.entornoModificar;
		         var ope = $scope.opCasosPruebaAModificar;
		         var index=ope.length;
		         var prueba="";
		  
		           angular.forEach($scope.opCasosPruebaAModificar, function(ope){                          
		                 prueba = prueba + ope.requestId + ";" + ope.instrumento + ";" + ope.origen +";";                                      
		             })
		
		               $http({
		                        method: 'POST',
		                        url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioActualizarCaso/ActualizarCaso',
		                        data: "{'nombreCaso':'"+ nombreCaso +"'," +
		                        			"'id':'"+ id +"',"+ 
		                                   "'operaciones':'"+ prueba +"',"+  
		                                   "'entorno':'"+ entorno +"',"+     
		                                   "'descripcion':'"+ descripcion +"'}"
		                                    
		                                   
		                      }).then(function successCallback(response) {

	                      	  		window.alert("CASO DE PRUEBA ACTUALIZADO CON EXITO");
		                             
		                        }, function errorCallback(response) {                        
		                               console.log("POST FALLO ACTUALZIAR CASO PRUEBA");
		                        });
           }
            
            /*********FUNCIONES PARA BOTONES VISTA ALTA **********/
            
            //Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarFiltrosAlta = function(){
        		if($scope.botonAbrirCerrarFiltrosAlta=="+"){
        			$scope.botonAbrirCerrarFiltrosAlta = "-";
        		}else{
        			$scope.botonAbrirCerrarFiltrosAlta = "+";
        		}
        	}
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarListaPeticionesAlta = function(){
        		if($scope.botonAbrirCerrarListaPeticionesAlta=="+"){
        			$scope.botonAbrirCerrarListaPeticionesAlta = "-";
        		}else{
        			$scope.botonAbrirCerrarListaPeticionesAlta = "+";
        		}
        	}
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarCasosPruebaAlta = function(){
        		if($scope.botonAbrirCerrarCasosPruebaAlta=="+"){
        			$scope.botonAbrirCerrarCasosPruebaAlta = "-";
        		}else{
        			$scope.botonAbrirCerrarCasosPruebaAlta = "+";
        		}
        	}
        	
        	/*********FUNCIONES PARA BOTONES VISTA MODIFICAR **********/
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarFiltrosModificar = function(){
        		if($scope.botonAbrirCerrarFiltrosModificar=="+"){
        			$scope.botonAbrirCerrarFiltrosModificar = "-";
        		}else{
        			$scope.botonAbrirCerrarFiltrosModificar = "+";
        		}
        	}
        	
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarListaPeticionesModificar = function(){
        		if($scope.botonAbrirCerrarListaPeticionesModificar=="+"){
        			$scope.botonAbrirCerrarListaPeticionesModificar = "-";
        		}else{
        			$scope.botonAbrirCerrarListaPeticionesModificar = "+";
        		}
        	}
        	
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarCasosPruebaModificar = function(){
        		if($scope.botonAbrirCerrarCasosPruebaModificar=="+"){
        			$scope.botonAbrirCerrarCasosPruebaModificar = "-";
        		}else{
        			$scope.botonAbrirCerrarCasosPruebaModificar = "+";
        		}
        	}
        	
        	/*********FUNCIONES PARA BOTONES VISTA CONSULTA **********/
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarFiltrosConsulta = function(){
        		if($scope.botonAbrirCerrarFiltrosConsulta=="+"){
        			$scope.botonAbrirCerrarFiltrosConsulta = "-";
        		}else{
        			$scope.botonAbrirCerrarFiltrosConsulta = "+";
        		}
        	}
        	
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarCasosPruebaConsultaOperaciones = function(){
        		if($scope.botonAbrirCerrarCasosPruebaConsultaOperaciones=="+"){
        			$scope.botonAbrirCerrarCasosPruebaConsultaOperaciones = "-";
        		}else{
        			$scope.botonAbrirCerrarCasosPruebaConsultaOperaciones = "+";
        		}
        	}
        	
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarCasosPruebaConsulta = function(){
        		if($scope.botonAbrirCerrarCasosPruebaConsulta=="+"){
        			$scope.botonAbrirCerrarCasosPruebaConsulta = "-";
        		}else{
        			$scope.botonAbrirCerrarCasosPruebaConsulta = "+";
        		}
        	}
        	
        	
        	/*********FUNCIONES PARA BOTONES VISTA EJECUCION **********/
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarFiltrosEjecucion = function(){
        		if($scope.botonAbrirCerrarFiltrosEjecucion=="+"){
        			$scope.botonAbrirCerrarFiltrosEjecucion = "-";
        		}else{
        			$scope.botonAbrirCerrarFiltrosEjecucion = "+";
        		}
        	}
        	
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarCasosPruebaEjecucion = function(){
        		if($scope.botonAbrirCerrarCasosPruebaEjecucion=="+"){
        			$scope.botonAbrirCerrarCasosPruebaEjecucion = "-";
        		}else{
        			$scope.botonAbrirCerrarCasosPruebaEjecucion = "+";
        		}
        	}
        	
        	/*********FUNCIONES PARA BOTONES VISTA RESULTADOS **********/
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarListaResultadosGuardados = function(){
        		if($scope.botonAbrirCerrarListaResultadosGuardados=="+"){
        			$scope.botonAbrirCerrarListaResultadosGuardados = "-";
        		}else{
        			$scope.botonAbrirCerrarListaResultadosGuardados = "+";
        		}
        	}
        	
        	
        	//Funcion para mostar u ocultar los parametros de busqueda
        	$scope.abrirCerrarResultadoEjecucion = function(){
        		if($scope.botonAbrirCerrarResultadoEjecucion=="+"){
        			$scope.botonAbrirCerrarResultadoEjecucion = "-";
        		}else{
        			$scope.botonAbrirCerrarResultadoEjecucion = "+";
        		}
        	}
 });
 
//------------------------------ Buscar Caso Prueba ------------------------------         

 regresiontest.controller("CasosController", function($scope, $http) {           
 
  $scope.buscarCasos = function(){
               var nombreCaso = $scope.nomCaso;
               var descripcion = $scope.descripcion;
               $scope.abrirCerrarFiltrosConsulta();
               $scope.abrirCerrarCasosPruebaConsulta();
               

                     $http({
                              method: 'POST',
                              url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioRecuperarCaso/recuperarCaso',
                              data: "{'nombreCaso':'"+ nombreCaso +"'," +
                                         "'descripcion':'"+ descripcion +"'}"
                                         
                                         
                            }).then(function successCallback(response) {
                                   
                                   $scope.responseCasos = response.data;
                                   
                              }, function errorCallback(response) {                        
                                     console.log("POST FALLO BUSCAR CASOS");
                              });
 }     
 
  //------------------------------ Eliminar Caso Prueba ------------------------------
 
 $scope.eliminarCasos = function (ope,index) {              
   var Id = ope.idCasoPrueba;
   var nombreCaso = $scope.nomCaso;
        var descripcion = $scope.descripcion;

                     $http({
                              method: 'POST',
                              url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioBorrarCaso/borrarCaso',
                              data: "{'Id':'"+ Id +"'," +
                                         "'nombreCaso':'"+ nombreCaso +"',"+     
                                         "'descripcion':'"+ descripcion +"'}"
                                     
                                     
                                                                                                                                                                                                                                                                         
                            }).then(function successCallback(response) {
                                   
                                   $scope.responseCasos = response.data;
                                   window.alert("CASO DE PRUEBA ELIMINADO CORRECTAMENTE");
                                   //$scope.vaciarCamposAlta();
                                   $scope.nombreCasoOperacionesMostradas = "";
                                   $scope.responsePeticiones = [];
                                   
                              }, function errorCallback(response) {                        
                                     console.log("POST FALLO ELIMINAR CASOS");
                              });
 }     
 
  
  
  
  
  
  //------------------------------ Mostrar opes ------------------------------
        
 $scope.mostrarOperaciones = function (ope,index) {                
   var Id = ope.idCasoPrueba;
   $scope.nombreCasoOperacionesMostradas = ope.nombreCasoPrueba;

                     $http({
                              method: 'POST',
                              url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioRecuperarOperaciones/recuperarOperaciones',
                              data: "{'Id':'"+ Id +"'}"                                          
                                                                                                                                                                                                                                                                         
                            }).then(function successCallback(response) {
                                   
                                   $scope.responsePeticiones = response.data;
                                   
                              }, function errorCallback(response) {                        
                                     console.log("POST FALLO MOSTRAR OPERACIONES CASO PRUEBA");
                              });
 }     
 
  
  
  
  
 //------------------------------ Eliminar Peticion de Caso Prueba ------------------------------
        
 $scope.eliminarOpe = function (ope,index) {                
   var requestId = ope.requestId;

                     $http({
                              method: 'POST',
                              url: 'http://172.18.215.121:8290/ConfiguracionRT/services/servicioEliminarOpe/eliminarOpe',
                              data: "{'requestId':'"+ requestId +"'}" 
                                                                                                                                                
                                                                                                                                                                                                                                                                         
                            }).then(function successCallback(response) {
                                   
                            
                                   
                              }, function errorCallback(response) {                        
                                     console.log("POST FALLO ELIMINAR OPERACIONES CASO PRUEBA");
                              });
 }     
 
  
//-----   
 });

 
//------------------------------ Controlador por retocar ------------------------------
 regresiontest.controller("monitorController" , function($scope){
	 
 });

//------------------------------ Controlador para los combos ------------------------------
 regresiontest.controller("comboControl", function($scope, $http) {
	
	 $scope.rellenarCombo = function(){
 		$http({
 			  method: 'GET',
 			  url: 'http://172.18.215.121:8290/LoginRT/services/serviciocombo/combo',
 			}).then(function successCallback(response) {
 				$scope.respuesta = response.data.respuesta;				
				
 			  }, function errorCallback(response) {				  
 				  console.log("GET FALLO");
 			  });
 	}}
 );
 
 regresiontest.directive("visualizarjson", function() {

		return {

			restrict : 'E',
			templateUrl : "templates/visualizarjson.html",

			controller : function() {

			},

			controllerAs : 'visualizarjson'
		};
	});

 function verJson(json,number,jsonIdElement){

		/*
	 	var pestanaContent = "";
	 	pestanaContent +="<li><a id='pestana" + number + "'><a ng-click='showMenuPestana=" + number + "'>RESULTADO" + number + "</a></li>";
	 	$('#pestanasResultados').append(pestanaContent);
	 	
	 	
		var divContent = "";
		divContent +="<div id='jsonviews" + number + "' ng-show='showMenuPestana==" + number + "'>";
		divContent += "<script>";
		divContent +="$('#jsonviews" + number + "').jstree(";
		divContent += "{ 'core' : { 'data' : [";
		divContent += json;
		divContent += "]} }";
		divContent += ");</script></div>";
		$('#json').append(divContent);
		*/
	 
	 	var divContent = "";
		divContent += "<script>";
		divContent +="$('#jsonviews" + number + "').jstree(";
		divContent += "{ 'core' : { 'data' : [";
		divContent += json;
		divContent += "]} }";
		divContent += ");</script>";
		$(jsonIdElement).append(divContent);
}
 
function vaciaDiv(){
	var valor = $('#pestanasResultados').html();
	if( valor != null || valor != ""){
		$('#pestanasResultados').empty();
	}
	
	var valor = $('#json').html();
	if( valor != null || valor != ""){
		$('#json').empty();
	}
}
 
