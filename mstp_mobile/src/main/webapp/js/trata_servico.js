function carrega_dados_agendamento_servico(){
	//alert("entrou no typehead");
	$.getJSON('./Op_Servlet?opt=46', function(data) {	
		//alert("pegou o data");
		$("#cliente_agendamento_servico").typeahead({ source:data,autoSelect: true });
		});
}

function add_servico(){
	  var nomecompleto,tipo,preco,percentual,especialidade,codigo;
	  nomecompleto= document.getElementById("servico_nome_completo").value;
		tipo= document.getElementById("servico_tipo").value;
		preco= document.getElementById("servico_preco").value;
		percentual=document.getElementById("servico_percent").value;
		especialidade= document.getElementById("servico_especialidade").value;
		codigo=document.getElementById("servico_codigo").value;
	   if(document.getElementById("operacao_servico").value=="ATUALIZACAO"){
		   bootbox.confirm("Esta Atualização afeta os registros de Fluxo de Caixa, deseja Continuar?", function(result){
			   if(result==true){
				   var $table = $('#tabela_servico');
			   	   if($table.bootstrapTable('getSelections').length>0){
			  	    	update_registro_id=$table.bootstrapTable('getSelections');
			  	    	update_registro_id=update_registro_id[0]['id_servico'];
			  	    }else{
			  	    	update_registro_id="NO_UPDATE";
			  	    }
				   $.ajax({
						  type: "POST",
						  data: {"opt":"3",
							"serviconomecompleto":nomecompleto,  
							"tipo":tipo, 
							"preco":preco, 
							"percentual":percentual,
							"especialidade":especialidade, 
							"codigo":codigo,
							"id_update":update_registro_id
							
						  },		  
						  //url: "http://localhost:8080/DashTM/D_Servlet",	  
						  url: "./Op_Servlet",
						  cache: false,
						  dataType: "text",
						  success: onSuccess3
						});
					
					function onSuccess3(data)
					{
						bootbox.alert(data);
					
					carrega_servicos();
					carrega_select_servico();
					}
			   }else{
				   bootbox.alert("Atualização abortada");
				   return;
				   }
			   });
	   	   
	   }else{
	  	    	update_registro_id="NO_UPDATE";
	  	    
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"3",
				"serviconomecompleto":nomecompleto,  
				"tipo":tipo, 
				"preco":preco, 
				"percentual":percentual,
				"especialidade":especialidade, 
				"codigo":codigo,
				"id_update":update_registro_id
				
			  },		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess3
			});
		
		function onSuccess3(data)
		{
		bootbox.alert(data);
		carrega_servicos();
		carrega_select_servico();
		}
	   }
	  
  }
function edita_servico(id){
		$.ajax({
			  type: "POST",
			  data: {"opt":"34",
				     "ids":id},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess34
			});
		function onSuccess34(data)
		{
			if(data=='Selecione apenas um registro para edição'){
				bootbox.alert(data);
			}else{
			info=JSON.parse(data);
			
			$('#servico_nome_completo').val(info['nome_servico']);
			$('#servico_tipo').val(info['subtipo_servico']);
			$('#servico_preco').val(info['servico_preco']);
			$('#servico_percent').val(info['percent']);
			$('#servico_especialidade').val(info['especialidade']);
			$('#servico_codigo').val(info['cod_servico']);
			
			opera_servico('ATUALIZACAO');
			}
		}
}
function opera_servico(operacao){
	document.getElementById("operacao_servico").value=operacao;
	$('#modal_add_servico').modal("toggle");
}
 function remove_servico(id){
	//alert(po_number);
		$.ajax({
			  type: "POST",
			  data: {"opt":"33",
				     "ids":id},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess33
			});
		function onSuccess33(data)
		{
			
			//carrega_ITEM();
			bootbox.alert("Serviços Cancelados! Os registros do Fluxo de Caixa para os serviços removidos não sofrem alterações.");
			carrega_select_servico();
		}

	}
function carrega_servicos(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"1"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess1
		});
	function onSuccess1(data)
	{
		$("#div_tabela_servico").html("<div id=\"toolbar_tabela_servico\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" onclick=\"opera_servico('NOVO')\">Novo Serviço</button>"+
    			"<button id=\"rm_servico\" type=\"button\" class=\"btn btn-danger\">Remover Serviço</button>"+
    			"<button id=\"edit_servico\"type=\"button\" class=\"btn btn-primary\" onclick=\"\">Editar Serviço</button>"+
    		    "</div>" + data);
		$('#tabela_servico').bootstrapTable();
	    //alert("Serviço Carrgado com sucesso");
		var $table = $('#tabela_servico');
		$button = $('#rm_servico');
	    $(function () {
	        $button.click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.id_servico;
	            });
	            $table.bootstrapTable('remove', {
	                field: 'id_servico',
	                values: ids
	            });
	            //alert(ids);
	            remove_servico(String(ids));
	        });
	    });
	    $button2 = $('#edit_servico');
		$(function () {
	        $button2.click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.id_servico;
	            });
	        	if(String(ids)==""){
	        		alert("Favor selecionar um registro");
	        		return;
	        		}
	            edita_servico(String(ids));
	        });
	    });
	}

}
function carrega_select_servico(){
	  $.ajax({
		  type: "POST",
		  data: {"opt":"4"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess4
		});
	  function onSuccess4(data){
		  $("#select_servico").html(data);
		  $("#select_servico").multiselect({
			  nonSelectedText: 'Selecione uma Especialidade',
			  includeSelectAllOption: true,
			  selectAllText: 'Todos',
			  allSelectedText: 'Todos',
			  onChange: function(option, checked, select) {
				  carrega_select_subtipo_servico($("#select_servico").multiselect().val(),0);
				
			  }
		  });
	  }
  }
   function carrega_select_subtipo_servico(especialidade,ids){
	   
	   var espec=String(especialidade);
	   //alert("Servico Selecionado: "+servicos);
	  $.ajax({
		  type: "POST",
		  data: {"opt":"11",
			  "especialidade":espec,
			  "id_servico":ids},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess11
		});
	  function onSuccess11(data){
		 
		  $("#select_subtiposervico").html(data);
		 
		  $("#select_subtiposervico").multiselect({
			  nonSelectedText: 'Selecione o serviço',
			 
			  includeSelectAllOption: true,
			  selectAllText: 'Todos',
			  allSelectedText: 'Todos',
			  onChange: function(option, checked, select) {
				  //alert($("#select_subtiposervico").multiselect().val());
				  carrega_dados_servico($("#select_subtiposervico").multiselect().val());
				 
			  }
				  });
		  $("#select_subtiposervico").multiselect('rebuild');
		  
	  }
  }
 function carrega_dados_servico(servico){
		  aux=String(servico);
		  $.ajax({
			  type: "POST",
			  data: {"opt":"10","servico":aux},		  
			  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess10
			});
		  function onSuccess10(data){
			 var valores=data.split(';');
			 document.getElementById("valor_preco").value=valores[0];
			 document.getElementById("servico_profissional").value=valores[1]+'%';
			 document.getElementById("servico_clinica").value=(100-valores[1])+'%';
			 //alert(valores[0]);
			 //carrega_select_subtipo_servico(valores[0]);
		  }
	  }