function busca_minha_info(){

		$.ajax({
			  type: "POST",
			  data: {"opt":"24"
				     },		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess24
			});
		function onSuccess24(data)
		{
			info=JSON.parse(data);
			    $('#info_usuario').val(info['usuario']);
				$("#info_nome").val(info['nome']);
				$("#info_email").val(info['email']);
				$("#info_cro").val(info['cro']);
				$("#info_cel").val(info['contato']);
				
		}



}
function atualiza_minha_info(){
	
	var nome,cro,email,usuario,contato;
	
	nome=$("#info_nome").val();
	usuario=$('#info_usuario').val();
	email=$("#info_email").val();
	cro=$("#info_cro").val();
	contato=$("#info_cel").val();
	if(nome==""){
		alert("Insirir Nome!");
		return;
	}
	if(usuario==""){
		alert("Insirir Usuário!");
		return;
	}
	if(email==""){
		alert("Insirir Email!");
		return;
	}
	$.ajax({
		  type: "POST",
		  data: {"opt":"25",
			  "usuario":usuario,
			  "nome":nome,
				"cro":cro,
				"contato":contato,
				"email":email
			     },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess25
		});
	function onSuccess25(data)
	{
		alert(data);
		    $('#info_usuario').val('');
			$("#info_nome").val('');
			$("#info_email").val('');
			$("#info_cro").val('');
			$("#info_cel").val('');
			
	}
}