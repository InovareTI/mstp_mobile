function initDespesasTable(){
	if ( ! $.fn.DataTable.isDataTable( '#tabela_lista_despesas' ) ) {
  $('#tabela_lista_despesas').DataTable({
	  "language": {
          "url": "http://172.20.10.3:8080/mstp_mobile/js/despesas/Portuguese-Brasil.json"
      },
	  "processing": true,
      "order": [],
      "ajax": "http://172.20.10.3:8080/mstp_mobile/DiariasServletMobile?opt=4",
      
      "lengthMenu": [5]
  });
  var table = $('#tabela_lista_despesas').DataTable();
 
  $('#tabela_lista_despesas tbody').on( 'click', 'tr', function () {
      if ( $(this).hasClass('selected') ) {
          $(this).removeClass('selected');
      }
      else {
          table.$('tr.selected').removeClass('selected');
          $(this).addClass('selected');
          console.log( table.row( this ).data() );
          showTemplateDialogDespesaprogress();
         
      }
  } );
	}else{
		var table = $('#tabela_lista_despesas').DataTable();
		table.destroy();
		$('#tabela_lista_despesas').DataTable({
			  "language": {
		          "url": "http://172.20.10.3:8080/mstp_mobile/js/despesas/Portuguese-Brasil.json"
		      },
			  "processing": true,
		      "order": [],
		      "ajax": "http://172.20.10.3:8080/mstp_mobile/DiariasServletMobile?opt=4",
		      "lengthMenu": [5]
		  });
		$('#tabela_lista_despesas tbody').on( 'click', 'tr', function () {
		      if ( $(this).hasClass('selected') ) {
		          $(this).removeClass('selected');
		      }
		      else {
		          table.$('tr.selected').removeClass('selected');
		          $(this).addClass('selected');
		          console.log( table.row( this ).data() );
		          showTemplateDialogDespesaprogress();
		          

		      }
		  } );
	}
}
function foto_notaDespesa(){
	var cameraOptions = {
	        // Some common settings are 20, 50, and 100
	        quality: 50,
	        destinationType: 0,
	        // In this app, dynamically set the picture source, Camera or photo gallery
	        sourceType: 1,
	        encodingType: 1,
	        mediaType: 0,
	        targetWidth:250,
	        targetHeight:350,
	        correctOrientation: true  //Corrects Android orientation quirks
	    };
	if(sessionStorage.getItem("ultimaDespesa")){
	   navigator.camera.getPicture(UploadFotoNotaDesepesa, cameraErrorDespesa, cameraOptions);
	}else{
		$.alert("Registre sua Despesa primeiro. Depois registro a foto.");
	}
	function cameraErrorDespesa(imageData) {
	   $.alert('oops sua imagem nao pode ser capturada.')
	}
}

function UploadFotoNotaDesepesa(imageData){
	 var win = function (r) {
	        clearCache();
	        retries = 0;
	        //alert('Done!');
	    }
	 //var image = document.getElementById('meu_perfil_foto');
	   //console.log(fileURI);
	   //image.src = "data:image/jpeg;base64," + imageData;
	    var fail = function (error) {
	        if (retries == 0) {
	            retries ++
	            setTimeout(function() {
	            	UploadFotoNotaDesepesa(imageData)
	            }, 1000)
	        } else {
	            retries = 0;
	            clearCache();
	            alert('Ups. Something wrong happens!');
	        }
	    }
	     $.ajax({
	                  type: "POST",
	                  data: {"opt":3,
	                	  	"idDespesa": sessionStorage.getItem("ultimaDespesa"),
	                        "image64":imageData},		  
	                  url: "http://172.20.10.3:8080/mstp_mobile/DiariasServletMobile",	  
	                  //url: "./POControl_Servlet""
	                  cache: false,
	                  dataType: "text",
	                  success:limpaFotoCache
	          });
	     function limpaFotoCache(data){
	    	 sessionStorage.setItem("ultimaDespesa","");
	     }
}
function NovaDespesa(){
  
  var valor,data,site,projeto,motivo,categoria,obs;
  valor=document.getElementById('valorDespesa').value;
  data=document.getElementById('dataDespesa').value;
  site=document.getElementById('siteDespesa').value;
  projeto=document.getElementById('projetoDespesa').value;
  categoria=document.getElementById('categoriaDespesa').value;
  motivo=document.getElementById('motivoDespesa').value;
  obs=document.getElementById('obsDespesa').value;
  $.confirm({title:'Lançamento de Despesa',
   content: valor +" | "+ data +" | "+ site +" | "+projeto+" | "+categoria+" | "+motivo,
   type:"orange",
    buttons: {
        Confirma: function () {
          $.ajax({
                            type: "POST",
                            data: {"opt":2,
                            "valorDespesa":valor.toString(),
                            "dataDespesa":data.toString(),
                            "siteDespesa":site.toString(),
                            "projetoDespesa":projeto.toString(),
                            "categoriaDespesa":categoria.toString(),
                            "motivoDespesa":motivo.toString(),
                            "obsDespesa":obs.toString()
                                    },		  
                            url: "http://172.20.10.3:8080/mstp_mobile/DiariasServletMobile",	  
                            //url: "./POControl_Servlet""
                            cache: false,
                            dataType: "text",
                            success:registraDespesa
                    });
                    function registraDespesa(data){
                      $.alert(data.toString());
                      sessionStorage.setItem("ultimaDespesa",data.toString());
                      document.getElementById('valorDespesa').value="";
                      document.getElementById('dataDespesa').value="";
                      document.getElementById('siteDespesa').value="";
                      document.getElementById('projetoDespesa').value="";
                      document.getElementById('categoriaDespesa').value="";
                      document.getElementById('motivoDespesa').value="";
                      document.getElementById('obsDespesa').value="";
                      initDespesasTable();
                    }
        }
    }
   });
   
}