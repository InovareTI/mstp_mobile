 function trocadeSenha(){
		 var atual = $("#senha_atual").val();
	     var nova = $("#nova_senha").val();
	     var confirma = $("#confirma_senha").val();
	    
	     if(atual==""){
	    	 alert("Senha Atual é Obrigatório!");
	    	 return;
	     }else if(nova!=confirma){
	    	 alert("Confirmação da Senha não confere com nova Senha!");
	    	 return;
	     }
	     
	     $.ajax({
			  type: "POST",
			  data: {"opt":"23",
				"atual":atual,  
				"nova":nova, 
				"confirma":confirma
				},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess23
			});
		
		function onSuccess23(data)
		{
			
			alert(data);
			$("#senha_atual").val('');
			$("#nova_senha").val('');
			$("#confirma_senha").val('');
		}
	     
	 }