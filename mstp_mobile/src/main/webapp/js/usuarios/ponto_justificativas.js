function onCapturePhoto2(fileURI) {
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
            $.alert('Problemas no envio da Foto! - onCapturePhoto2');
        }
    }
 
    var options = new FileUploadOptions();
    options.fileKey = "foto";
    options.fileName = fileURI.substr(fileURI.lastIndexOf('/') + 1);
    options.mimeType = "image/png";
    options.params = {"opt":42}; // if we need to send parameters to the server request
    var ft = new FileTransfer();
    ft.upload(fileURI, encodeURI("http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet?opt=42"), win, fail, options);
}
function onCapturePhoto3(fileURI) {
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
 
    var options = new FileUploadOptions();
    options.fileKey = "foto";
    options.fileName = fileURI.substr(fileURI.lastIndexOf('/') + 1);
    options.mimeType = "image/png";
    options.params = {"opt":45}; // if we need to send parameters to the server request
    var ft = new FileTransfer();
    ft.upload(fileURI, encodeURI("http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet?opt=45"), win, fail, options);
}
function foto_justificativa(){
  
  var cameraOptions = {
        // Some common settings are 20, 50, and 100
        quality: 50,
        destinationType: 1,
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
	        destinationType: 1,
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
        url: "http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
        //url: "./Op_Servlet""
        cache: false,
        dataType: "text",
        success: carregando_justificativas
});
	function carregando_justificativas(data){
		$("#div_motivo_ajuste").html(data)
	}
}