function carrega_clientes_atendimento() {
	  $.ajax({
		  type: "POST",
		  data: {"opt":"49"},		  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess49
		});
	function onSuccess49(data)
	{
		$("#atendimento_lista").html(data);
		//alert(data);
		var quantidade=document.getElementById("cliente_quantidade_atendimento").value;
		//alert(quantidade);
		for(var i=1;i<=quantidade;i++){
			atualiza_foto_cliente("imagem_cliente_"+i,document.getElementById("cliente_atendimento_"+i).value);
		}
	}
  }
function inicia_atendimento(id_cliente,id_agendamento){
	//alert(id);
	document.getElementById("atendimento_lista").style.display="none";
	document.getElementById("agenda").style.display="none";
	document.getElementById("atendimento").style.display="block";
	document.getElementById("atendimento_div").style.display="block";
	document.getElementById("id_agendamento_atendimento").value=id_agendamento;
	document.getElementById("cliente_id_carregado").value=id_cliente;
	atendimento_todos_os_arquivos();
	carrega_historico_consultas();
}
function carrega_historico_consultas(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"53",
			  "cliente":$("#cliente_id_carregado").val(),
			  "agendamento":$("#id_agendamento_atendimento").val()		  
		  },
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess53
		});
	function onSuccess53(data)
	{
		
		$("#tabela_de_consultas2").html(data);
		
	}
}
function atendimento_todos_os_arquivos() {
	//console.log(index);
	 
	$.ajax({
		  type: "POST",
		  data: {"opt":"52",
			  "cliente":$("#cliente_id_carregado").val(),
			  "agendamento":$("#id_agendamento_atendimento").val()		  
		  },
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess52
		});
	function onSuccess52(data)
	{
		
		$("#tabela_de_todos_os_arquivos").html(data);
		
		$('#tabela_arquivosCliente_todos').bootstrapTable();

		        
		       
		
	}
	
}
function carrega_atendimento_arquivos_tabela() {
	  $.ajax({
		  type: "POST",
		  data: {"opt":"50",
			  "cliente":$("#cliente_id_carregado").val(),
			  "agendamento":$("#id_agendamento_atendimento").val()		  
		  },
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess50
		});
	function onSuccess50(data)
	{
		$("#tabela_atendimento_arquivos_div").html(data);
		$('#tabela_atendimento_arquivos').bootstrapTable();
		//$(".group2").colorbox({rel:'group2', transition:"fade",inline:true, href:$(this).attr('href')});
		
	}
}
function exibe_arquivo(opt,id){
	 $.colorbox({
	        open: true,
	        scrolling: false,
	        photo:true,
	        //innerWidth:'400',
	        //innerHeight:'400',
	        href:"./Op_Servlet?opt=51&id="+id,
	        //data:{opt:opt,id:id},
	        onClosed:function(){
	            //Do something on close.
	        }
	    });
	
}
function gerar_ficha_atendimento(){
	bootbox.alert("Função em Desenvolvimento. Em breve estara disponivel!");
}
function salva_procedimento_atendimento(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"55",
			  "cliente":$("#cliente_id_carregado").val(),
			  "agendamento":$("#id_agendamento_atendimento").val(),
			  "procedimento":$("#procedimento").val()
		  },
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess55
		});
	function onSuccess55(data)
	{
		bootbox.alert(data);
		
		
	}
}