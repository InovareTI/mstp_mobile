function onCapturePhoto2(imageData) {
    var win = function (r) {
        clearCache();
        retries = 0;
        $.alert('Foto Enviada com Sucesso!');
    }
 
    var fail = function (error) {
        if (retries == 0) {
            retries ++
            setTimeout(function() {
                onCapturePhoto2(imageData)
            }, 1000)
        } else {
            retries = 0;
            clearCache();
            $.alert('Problemas no envio da Foto!');
        }
    }
    $.ajax({
        type: "POST",
        data: {"opt":42,
              "image64":imageData},		  
        url: "http://192.168.0.170:8080/mstp_mobile/Op_Servlet",	  
        //url: "./POControl_Servlet""
        cache: false,
        dataType: "text"
        
});
    
    
}
function onCapturePhoto3(imageData) {
	//alert("chegou aqui no onCapturePhoto3");
	//console.log(imageData);
    var win = function (r) {
        clearCache();
        retries = 0;
        $.alert('Foto Enviada com Sucesso!');
    }
 //var image = document.getElementById('meu_perfil_foto');
   //console.log(fileURI);
  // image.src = fileURI;
    var fail = function (error) {
        if (retries == 0) {
            retries ++
            setTimeout(function() {
                onCapturePhoto2(fileURI)
            }, 1000)
        } else {
            retries = 0;
            clearCache();
            $.alert('Problemas no envio da Foto! - onCapturePhoto3');
        }
    }
    $.ajax({
        type: "POST",
        data: {"opt":45,
              "image64":imageData},		  
        url: "http://192.168.0.170:8080/mstp_mobile/Op_Servlet",	  
        //url: "./POControl_Servlet""
        cache: false,
        dataType: "text"
        
    });
    
 }
function foto_justificativa(){
  
  var cameraOptions = {
        // Some common settings are 20, 50, and 100
        quality: 50,
        destinationType: 0,
        // In this app, dynamically set the picture source, Camera or photo gallery
        sourceType: 1,
        encodingType: 1,
        mediaType: 0,
        targetWidth:390,
        targetHeight:390,
        correctOrientation: true  //Corrects Android orientation quirks
    };
    navigator.camera.getPicture(onCapturePhoto2, cameraError, cameraOptions);
    function cameraError(imageData) {
    		$.alert('oops sua imagem nao pode ser capturada.')
    }
}
function fotoPonto(){
	  
	  var cameraOptions = {
	        // Some common settings are 20, 50, and 100
	        quality: 50,
	        destinationType: 0,
	        // In this app, dynamically set the picture source, Camera or photo gallery
	        sourceType: 1,
	        encodingType: 1,
	        mediaType: 0,
	        targetWidth:390,
	        targetHeight:390,
	        correctOrientation: true  //Corrects Android orientation quirks
	    };
	    navigator.camera.getPicture(onCapturePhoto3, cameraError, cameraOptions);
	    function cameraError(imageData) {
	    		$.alert('oops sua imagem nao pode ser capturada.')
	    }
	}
function carrega_justificativas(){

	$.ajax({
        type: "POST",
        data: {"opt":41
        },		  
        url: "http://192.168.0.170:8080/mstp_mobile/Op_Servlet",	  
        //url: "./Op_Servlet""
        cache: false,
        dataType: "text",
        success: carregando_justificativas
});
	function carregando_justificativas(data){
		$("#div_motivo_ajuste").html(data)
	}
}