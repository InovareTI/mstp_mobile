function foto_checklist(idCampo,campo_nome,idRelatorio,id_vistoria){
	
	var recid = sessionStorage.getItem("checklist_recid_aux");
	var milestone = sessionStorage.getItem("checklist_milestone_aux");
	var site =  sessionStorage.getItem("checklist_site_aux");
	
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
	    navigator.camera.getPicture(function(imageData){

	        var win = function (r) {
	            clearCache();
	            retries = 0;
	            carrega_checklist_campos(idRelatorio);
	        }
	     
	        var fail = function (error) {
	            if (retries == 0) {
	                retries ++
	                setTimeout(function() {
	                    onCapturePhoto3(imageData)
	                }, 1000)
	            } else {
	                retries = 0;
	                clearCache();
	                $.alert('Problemas no envio da Foto! - onCapturePhoto3');
	            }
	        }
	        $.ajax({
	            type: "POST",
	            data: {"opt":53,
	                  "image64":imageData,
	                  "idCampo":idCampo,
	                  "idRelatorio":idRelatorio,
	                  "CampoNome":campo_nome,
	                  "recid":recid,
	                  "milestone":milestone,
	                  "site":site,
	                  "idvistoria":id_vistoria},		  
	            url: "http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
	            //url: "./POControl_Servlet""
	            cache: false,
	            dataType: "text"
	            
	    });
	       
	        
	    }, cameraError, cameraOptions);
	    function cameraError(imageData) {
	    		$.alert('oops sua imagem nao pode ser capturada.')
	    }
}
function elimina_checklist(id,relatorio){
	$.confirm({
		title:"Eliminar Checklist Atual!",
		content:"confirma eliminar checklist atual?",
		type:"red",
		buttons:{
			Confirma:function(){
				 $.ajax({
					  type: "POST",
					  data: {"opt":56,"idVistoria":id},		  
					  url: "http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
					  //url: "./POControl_Servlet""
					  cache: false,
					  dataType: "text",
					  success: onSuccesseliminaChecklist,
				 	});
				 function onSuccesseliminaChecklist(data){
					 $.alert(data.toString());
					 carrega_checklist_campos(relatorio);
				 }
			},
			Cancelar:function(){
				
			}
		}
	});
}
function atualiza_texto_checklist(valor,campoID,campoNome,relatorioID,idVistoria){
	var recid = sessionStorage.getItem("checklist_recid_aux");
	var milestone = sessionStorage.getItem("checklist_milestone_aux");
	var site =  sessionStorage.getItem("checklist_site_aux");
	
	 $.ajax({
		  type: "POST",
		  data: {"opt":58,
			  "idVistoria":idVistoria,
			  "valor":valor,
			  "campoID":campoID,
		  		"campoNome":campoNome,
		  		"relatorioID":relatorioID,
		  		"recid":recid,
		  		"milestone":milestone,
		  		"site":site},		  
		  url: "http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
		  //url: "./POControl_Servlet""
		  cache: false,
		  dataType: "text",
		  success: onSuccesseliminaChecklist,
	 	});
	 function onSuccesseliminaChecklist(data){
		 //$.alert(data.toString());
		 carrega_checklist_campos(relatorioID);
	 }
	
}
function submete_checklist(idchecklist){
	$.confirm({
		title:"Submeter Checklist",
		content:"Confirma submeter checklist para aprovação?",
		type:"green",
		buttons:{
			Confirma:function (){
				$.ajax({
					  type: "POST",
					  data: {"opt":57,"idVistoria":idchecklist},		  
					  url: "http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
					  //url: "./POControl_Servlet""
					  cache: false,
					  dataType: "text",
					  success: onSuccesseliminaChecklist,
				 	});
				 function onSuccesseliminaChecklist(data){
					 $.alert({
						 content:"Checklist Enviado para Aprovação!",
						 type:"green"
					 });
					 navega('Atividades_info');
				 }
			},
			Cancelar:function(){
				
			}
		}
		
	});
}
function deleta_foto(id,relatorio){
	 $.ajax({
		  type: "POST",
		  data: {"opt":55,"id_dados":id},		  
		  url: "http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
		  //url: "./POControl_Servlet""
		  cache: false,
		  dataType: "text",
		  success: onSuccessdeletafoto,
	 	});
	 function onSuccessdeletafoto(data){
		 carrega_checklist_campos(relatorio);
	 }
}
function onCapturePhoto4(fileURI) {
    var win = function (r) {
        clearCache();
        retries = 0;
        $.alert('Foto Enviada com Sucesso!');
    }
 
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
    options.params = {"opt":53,"idCampo":idcampo,"idRelatorio":idrelatorio}; // if we need to send parameters to the server request
    var ft = new FileTransfer();
    ft.upload(fileURI, encodeURI("http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet"), win, fail, options);
}