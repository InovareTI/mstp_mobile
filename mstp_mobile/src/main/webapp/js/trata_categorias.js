
function add_categoria(){
	  var cat,subcat,newcat,newsubcat,update_registro_id;
	  
	  cat=document.getElementById('despesa_categoria').value;
	  subcat=document.getElementById('despesa_subcategoria').value;
	  newcat=document.getElementById('despesa_novacategoria').value;
	  newsubcat=document.getElementById('despesa_novasubcategoria').value;
	  
	  if(document.getElementById("operacao_categoria").value=="ATUALIZACAO"){
	  	  bootbox.confirm("Esta Atualização afeta os registros de Fluxo de Caixa, deseja Continuar?", function(result){
	  	  		  if(result==true){
				   var $table = $('#tabela_despesa');
			   	   if($table.bootstrapTable('getSelections').length>0){
			  	    	update_registro_id=$table.bootstrapTable('getSelections');
			  	    	update_registro_id=update_registro_id[0]['id_categoria'];
			  	    }else{
			  	    	update_registro_id="NO_UPDATE";
			  	    }
			  	    if($('input[name=categoria_tipo]:checked').val()=="Entrada"){}
			  	    else if ($('input[name=categoria_tipo]:checked').val()=="Despesa"){}
			  	    else if ($('input[name=categoria_tipo]:checked').val()=="Cliente"){}
			  	    else{
			  	    	bootbox.alert("Informe o tipo da Categoria!");
			  	    	return;
			  	    }
				  if((cat=="Nenhuma Categoria Encontrada") && (!document.getElementById("nova_categoria").checked)){
					  bootbox.alert("Crie uma nova Categoria");
					  return;
				  }
				  if (document.getElementById("nova_categoria").checked){
					  cat=newcat;
					  subcat=newsubcat;
				  }
				  $('#btn_add_despesa').addClass( "disabled" );
				  $('#btn_add_despesa').text('Aguarde a Finalização');
				  $.ajax({
					  type: "POST",
					  data: {"opt":"17",
						  "categoria":cat,
						  "subcategoria":subcat,
						  "tipo_categoria":$('input[name=categoria_tipo]:checked').val(),
						  "id_categoria":update_registro_id},
						  
					  url: "./Op_Servlet",
					  cache: false,
					  dataType: "text",
					  success: onSuccess17
					});
				function onSuccess17(data)
				{
					$('#modal_add_despesa').modal("toggle");
					bootbox.alert(data);
					$('#btn_add_despesa').removeClass( "disabled" );
					$('#btn_add_despesa').text('Adicionar Categoria');
					$('.categoria').val("");
					carrega_categorias();
					carrega_select_despesa_categoria();
					carrega_select_entrada_categoria();
					carrega_select_categoria_cliente();
					
				} 
		}else{
		   bootbox.alert("Atualização abortada");
		   enivado=0;
		   return;
		 }
	   });
	}else{
		update_registro_id="NO_UPDATE";
		if($('input[name=categoria_tipo]:checked').val()=="Entrada"){}
		  else if ($('input[name=categoria_tipo]:checked').val()=="Despesa"){}
		  else if ($('input[name=categoria_tipo]:checked').val()=="Cliente"){}
		  else{
			  bootbox.alert("Informe o tipo da Categoria!");
			  return;
		  }
		  if((cat=="Nenhuma Categoria Encontrada") && (!document.getElementById("nova_categoria").checked)){
			  bootbox.alert("Crie uma nova Categoria");
			  return;
		  }
		  if (document.getElementById("nova_categoria").checked){
			  //alert("teste");
			  cat=newcat;
			  subcat=newsubcat;
		  }
		  $('#btn_add_despesa').addClass( "disabled" );
		  $('#btn_add_despesa').text('Aguarde a Finalização');
		  $.ajax({
			  type: "POST",
			  data: {"opt":"17",
				  "categoria":cat,
				  "subcategoria":subcat,
				  "tipo_categoria":$('input[name=categoria_tipo]:checked').val(),
			      "id_categoria":update_registro_id},
				  	  
			  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess17
			});
		function onSuccess17(data)
		{
			$('#modal_add_despesa').modal("toggle");
			$('#btn_add_despesa').removeClass( "disabled" );
			$('#btn_add_despesa').text('Adicionar Categoria');
			bootbox.alert(data);
			carrega_categorias();
			$('.categoria').val("");
			carrega_select_despesa_categoria();
			carrega_select_entrada_categoria();
			carrega_select_categoria_cliente();
		}
	}
  }
function opera_categoria(operacao){
	document.getElementById("operacao_categoria").value=operacao;
	$('#modal_add_despesa').modal("toggle");
}
   function carrega_categorias(){
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"14"},		  
			  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess14
			});
		function onSuccess14(data)
		{
			$("#div_tabela_despesas").html("<div id=\"toolbar_tabela_despesa\" role=\"toolbar\" class=\"btn-toolbar\">"+
	        		
	    			"<button id=\"btn_nova_categoria\" type=\"button\" class=\"btn btn-info\" data-toggle=\"button\" onclick=\"opera_categoria('NOVO')\">Nova Categoria</button>"+
	    			"<button id=\"rm_categoria\" type=\"button\" class=\"btn btn-danger\">Remover Categoria</button>"+
	    			"<button id=\"edit_categoria\" type=\"button\" class=\"btn btn-danger\">Editar Categoria</button>"+
	    			"</div>" + data);
			$('#tabela_despesa').bootstrapTable();
			var $table = $('#tabela_despesa');
			$button = $('#rm_categoria');
		    $(function () {
		        $button.click(function () {
		        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
		                return row.id_categoria;
		            });
		        	
		            $table.bootstrapTable('remove', {
		                field: 'id_categoria',
		                values: ids
		            });
		            //alert(ids);
		            remove_categoria(String(ids));
		        });
		    });
		    $button2 = $('#edit_categoria');
			$(function () {
		        $button2.click(function () {
		        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
		                return row.id_categoria;
		            });
		        	if(String(ids)==""){
		        		alert("Favor selecionar um registro");
		        		return;
		        		}
		            edita_categoria(String(ids));
		        });
		    });
		}

	}
   function edita_categoria(id){
		$.ajax({
			  type: "POST",
			  data: {"opt":"36",
				     "ids":id},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess36
			});
		function onSuccess36(data)
		{
			if(data=='Selecione apenas um registro para edição'){
				bootbox.alert(data);
			}else{
			info=JSON.parse(data);
			//$('input[name=categoria_tipo]:checked').val(info['tipo_categoria']);
			var $radios = $('input:radio[name=categoria_tipo]');
			 $radios.filter('[value='+info['tipo_categoria']+']').prop('checked', true);
			$('#despesa_categoria').val(info['categoria']);
			$('#despesa_subcategoria').val(info['subcategoria']);
			opera_categoria('ATUALIZACAO');
			}
		}
 }
   function remove_categoria(id){
		//alert(po_number);
			$.ajax({
				  type: "POST",
				  data: {"opt":"35",
					     "ids":id},		  
				  //url: "http://localhost:8080/DashTM/D_Servlet",	  
				  url: "./Op_Servlet",
				  cache: false,
				  dataType: "text",
				  success: onSuccess35
				});
			function onSuccess35(data)
			{
				
				//carrega_ITEM();
				bootbox.alert("Categoria Removida! Os registros do Fluxo de Caixa para com as categorias removidas não sofrem alterações.");
				carrega_select_despesa_categoria();
				carrega_select_entrada_categoria();
				carrega_select_categoria_cliente();
			}

		}
	function carrega_dados_categoria(categoria,origem){
	  var aux;
	  if(origem==0){
		  aux=categoria.value;
	  }else{
		  aux=categoria;
	  }
	  $.ajax({
		  type: "POST",
		  data: {"opt":"16","categoria":aux},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess16
		});
	  function onSuccess16(data){
		  $("#select_subcategoria_despesa_template").html(data);
		  $("#select_subcategoria_despesa_item").html(data);
		  
	  }
  }
  function carrega_dados_categoria_entrada(categoria){
	  
	  $.ajax({
		  type: "POST",
		  data: {"opt":"19","categoria":categoria.value},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess19
		});
	  function onSuccess19(data){
		  $("#select_subcategoria_registro_entrada").html(data);
	  }
}
 var categorias_despesas;
 function carrega_select_despesa_categoria(){
	 $.ajax({
		  type: "POST",
		  data: {"opt":"15"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  async: false,
		  cache: false,
		  dataType: "text",
		  success: function(data){
			  $("#despesa_categoria").html(data);
			  categorias_despesas=data;
			  
			}
		});
  }
 
  function carrega_select_entrada_categoria(){
	  //alert("teste");
	  $.ajax({
		  type: "POST",
		  data: {"opt":"18"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess18
		});
	  function onSuccess18(data){
		  //$("#despesa_categoria").html(data);
		  $("#select_categoria_registro_entrada").html(data);
		  
	  }
  }
  function mostra_campos_categoria(){
	  var data,cat,categoria;
	  data="";
	  cat=String($('#select_categoria_cliente').multiselect().val());
	  if(cat==null){
		  data="";
	  }else{
	  if(cat.indexOf(",")>0){
	  	categoria=cat.split(",");
	  	for(var i=0;i<categoria.length;i++){
			  data=data+"<label class='control-label'>"+categoria[i]+"</label><input type=text id='"+categoria[i]+"' class='form-control'><br>"+"\n";
		  }
	  }else{
		  categoria=cat;
		  data=data+"<label class='control-label'>"+categoria+"</label><input type=text id='"+categoria+"' class='form-control'><br>"+"\n";
	  }
	  }
	  //alert(data);
	  $("#campos_especialidade_cliente").html(data);
  }
  function carrega_select_categoria_cliente(){
	  $.ajax({
		  type: "POST",
		  data: {"opt":"32"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess32
		});
	  function onSuccess32(data){
		  $("#select_categoria_cliente").html(data);
		  $('#select_categoria_cliente').multiselect({nonSelectedText: 'Selecione uma Categoria',allSelectedText: 'Todos'});
	  }
  }