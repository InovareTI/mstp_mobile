
function carrega_dados_cliente(id){
	document.getElementById("div_clientes_tabela").style.display="none";
	document.getElementById("agenda").style.display="none";
	document.getElementById("clientes").style.display="block";
	document.getElementById("div_carrega_cliente").style.display="block";
	document.getElementById("cliente_id_carregado").value=id;
	//alert(id);
	$.ajax({
		  type: "POST",
		  data: {"opt":"30",
			  "ids":id
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess30_2
		});
	function onSuccess30_2(data)
	{
		var aux="";
		info=JSON.parse(data);
		$('#cliente_nome_completo').val(info['nome']);
		$('#cliente_cpf').val(info['cpf']);
		$('#cliente_sexo').val(info['sexo']);
		$('#cliente_dt_nascimento').val(info['dt_nasc']);
		$('#cliente_estado_civil').val(info['estado_civil']);
		$('#cliente_pl_saude').val(info['plano_saude']);
		$('#cliente_numero_plano').val(info['numero_plano']);
		$('#cliente_bairro').val(info['bairro']);
		$('#cliente_cidade').val(info['cidade']);
		$('#cliente_endereco').val(info['endereco']);
		$('#cliente_celular').val(info['contato']);
		$('#cliente_email').val(info['email']);
		$('#cliente_cep').val(info['cep']);
		$('#cliente_complemento').val(info['complemento']);
		$('#cliente_UF').val(info['uf']);
		if(info['categorias_quantidade']>0){
		datahtml="<div class=\"modal-footer\">"+
	        		"<button id=\"btn_add_cliente\" type=\"button\" class=\"btn btn-info add_clientebt\" onclick=\"add_cliente()\">Salvar Alterações</button>"+
	      		"</div><table>";
		for(var i=1;i<=info['categorias_quantidade'];i++){
			$('#select_categoria_cliente').multiselect('select', [info[i+'_categoria']]);
			  datahtml=datahtml+"<tr><td style=\"padding: 5px\"><label class='control-label'>"+info[i+'_categoria']+"</label></td><td style=\"padding: 5px\"><input type=text id='"+info[i+'_categoria']+"' class='form-control' value='"+info[i+'_categoria_valor']+"'></td></tr>"+"\n";
			  aux=aux+info[i+'_categoria']+",";
		}
		document.getElementById("quantidade_categorias_cliente").value=aux.substring(0, aux.length-1);
		datahtml=datahtml+"</table>";
		}else{
			document.getElementById("quantidade_categorias_cliente").value="0";
			datahtml="<label class='control-label'>Nehuma informação encontrada. Para Adicionar detalhes aos clientes vá em Controles> Categorias. Crie Categorias do Tipo : Cliente</label>";
		}
		$("#atributos_cliente").html(datahtml);
		atualiza_foto_cliente("",id);
		opera_cliente('ATUALIZACAO');
	}
	$.ajax({
		  type: "POST",
		  data: {"opt":"53",
			  "cliente":id,
			  "agendamento":"0"
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess47
		});
	function onSuccess47(data)
	{
		$("#tabela_de_consultas").html(data);
		//$('#tabela_de_consultas').bootstrapTable();
		
	}
	 $.ajax({
		  type: "POST",
		  data: {"opt":"50",
			  "cliente":$("#cliente_id_carregado").val(),
			  "agendamento":"0"		  
		  },
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess50_2
		});
	function onSuccess50_2(data)
	{
		$("#tabela_cliente_arquivos_div").html(data);
		$('#tabela_atendimento_arquivos').bootstrapTable();
		//$(".group2").colorbox({rel:'group2', transition:"fade",inline:true, href:$(this).attr('href')});
		
	}
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"52",
			  "cliente":$("#cliente_id_carregado").val(),
			  "agendamento":0		  
		  },
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess52_b
		});
	function onSuccess52_b(data)
	{
		
		$("#arquivosGeral_clienteExibir").html(data);
		$('#tabela_arquivosCliente_todos').bootstrapTable();
	}
}
function remove_arquivo_cliente(id_arquivo,linha_tabela){
	$.ajax({
		  type: "POST",
		  data: {"opt":"54",
			  "id_arquivo":id_arquivo
			  	  
		  },
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess54
		});
	function onSuccess54(data)
	{
		//alert(JSON.stringify($("#tabela_arquivosCliente_todos").bootstrapTable('getData')));
		//alert(linha_tabela);
		aux="'"+linha_tabela+"'";
		//alert(aux)
	    $("#tabela_arquivosCliente_todos").bootstrapTable('remove', {
            field: 'id_linha',
            values: aux
        });
	}
}
function atualiza_foto_cliente(local,id){
	//alert(local);
	//alert(id);
	if(local==""){
		document.getElementById("image_cliente").src="./Op_Servlet?opt=48&ids="+id;
	}else{
		document.getElementById(local).src="./Op_Servlet?opt=48&ids="+id;
	}
	
}
function carrega_dados_cliente_registro(){
	//alert("entrou no typehead");
	$.getJSON('./Op_Servlet?opt=31', function(data) {	
		//alert("pegou o data");
		$("#nome_cliente").typeahead({ source:data});
		$("#cliente_agendamento").typeahead({ source:data });
		$("#nome_cliente").blur(function() {
			if($("#nome_cliente").typeahead("getActive").name==$("#nome_cliente").val()){
				$("#cpf_cliente").val($("#nome_cliente").typeahead("getActive").cpf);
			}else{
				$("#cpf_cliente").val('0');
			}
			
		});
	});
}
function opera_cliente(operacao){
	document.getElementById("operacao_cliente").value=operacao;
	if(operacao=='NOVO'){
		document.getElementById("div_clientes_tabela").style.display="none";
		document.getElementById("div_carrega_cliente").style.display="block";
		//document.getElementById("operacao_cliente").value='0';
		$('.cliente').val('');
		$('#tabela_de_consultas').bootstrapTable('removeAll');
		$('#tabela_arquivosCliente_todos').bootstrapTable('removeAll');
		$('#tabela_atendimento_arquivos').bootstrapTable('removeAll');
	}
}
	
	function add_cliente(opcao){

	  var nome,cpf,sexo,dt_nasc,plano,numeroplano,estado_civil,bairro,cidade,endereco,contato,email,operacao,update_registro_id,atributo,cat,categorias;
	  var cep,complemento,UF;
	  	operacao=document.getElementById("operacao_cliente").value;
	  	if(operacao=='ATUALIZACAO'){
	  	  
	  		update_registro_id=$("#cliente_id_carregado").val();
	  	}else{
	  		update_registro_id="NO_UPDATE";
	  	}
		  nome=$('#cliente_nome_completo').val();
		  if(nome==""){
		  	  alert("Inserir nome");
		  	  return;
		  }
		  cpf=$('#cliente_cpf').val();
		  if(cpf==""){
		  	  alert("Inserir nome");
		  	  return;
		  }
		  cat=String(document.getElementById("quantidade_categorias_cliente").value);
		  if(cat=="0"){
			  categorias="vazio";
			  cat="VAZIO";
			  atributo="VAZIO"
		  }else{
		  if(cat.indexOf(",")>0){
		  	categorias=cat.split(",");
		  	atributo="";
		  	for(var i=0;i<categorias.length;i++){
		  		atributo=atributo+categorias[i]+"="+$('#'+categorias[i]).val()+",";
		  	}
		  }else{
			  atributo=cat+"="+$('#'+cat).val()+",";
		  }
		  atributo=atributo.substring(0,atributo.length-1);
		  }
		  //alert(cat);
		 // alert(atributo);
		 // alert(operacao);
		  $('.add_clientebt').addClass( "disabled" );
		  $('.add_clientebt').text('Aguarde Finalização');
		  sexo=$('#cliente_sexo').val();
		  dt_nasc=$('#cliente_dt_nascimento').val();
		  estado_civil=$('#cliente_estado_civil').val();
		  plano=$('#cliente_pl_saude').val();
		  numeroplano=$('#cliente_numero_plano').val();
		  bairro=$('#cliente_bairro').val();
		  cidade=$('#cliente_cidade').val();
		  endereco=$('#cliente_endereco').val();
		  contato=$('#cliente_celular').val();
		  email=$('#cliente_email').val();
		  cep=$('#cliente_cep').val();
		  complemento=$('#cliente_complemento').val();
		  UF=$('#cliente_UF').val();
		  $.ajax({
			  type: "POST",
			  data: {"opt":"29",
				     "nome":nome,
				     "cpf":cpf, 
				     "data_nasc":dt_nasc,
				     "civil":estado_civil,
				     "plano":plano,
				     "numeroplano":numeroplano,
				     "bairro":bairro,
				     "cidade":cidade,
				     "endereco":endereco,
				     "celular":contato,
				     "email":email,
				     "operacao":operacao,
				     "id_atualiza":update_registro_id,
				     "sexo":sexo,
				     "categorias":cat,
				     "atributo_categorias":atributo,
				     "cep":cep,
				     "complemento":complemento,
				     "uf":UF
			  },		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess29
			});
		function onSuccess29(data)
		{
			
			//carrega_ITEM();
			bootbox.alert(data);
			//$('#modal_add_cliente').modal("toggle");
			$('.add_clientebt').removeClass( "disabled" );
			$('.add_clientebt').text('Salvar Alterações');
			//$('.cliente').val('');
			carrega_clientes();
		}
	  
  }
  
  
  function cancela_clientes(id){
	  $.ajax({
		  type: "POST",
		  data: {"opt":"28",
			     "ids":id},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess28
		});
	function onSuccess28(data)
	{
		
		//carrega_ITEM();
		bootbox.alert("Registros Cancelados");
		carrega_clientes();
	}
  }
  
  
  function sincronizar_cliente_fx(){
	  $('#btn_sync_fx').button();
	  $('#btn_sync_fx').addClass( "disabled" );
	  $('#btn_sync_fx').text('Aguarde a Finalização...');
	  $.ajax({
		  type: "POST",
		  data: {"opt":"27"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess27
		});
	function onSuccess27(data)
	{
		bootbox.alert(data);
		carrega_clientes();
		$('#btn_sync_fx').removeClass( "disabled" );
		$('#btn_sync_fx').text('Sincronizar do Fluxo de Caixa');
	}
  }
  function carrega_clientes(){
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"26"},		  
			  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess26
			});
		function onSuccess26(data)
		{
			//alert(data);
			$("#div_clientes_tabela").html("<div id=\"toolbar_tabela_clientes\" role=\"toolbar\" class=\"btn-toolbar\">"+
	        		
	    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"opera_cliente('NOVO')\">Novo Cliente</button>"+
	    			
	    			"<button id=\"btn_sync_fx\" type=\"button\" class=\"btn btn-info\" onclick=\"sincronizar_cliente_fx()\">Sincronizar do Fluxo de Caixa</button>"+
	    			"<button id=\"cancelar_cliente\" type=\"button\" class=\"btn btn-danger\">Remover</button>"+
	    			
	    			
	    		    "</div>" + data);
			$('#tabela_clientes').bootstrapTable();
			//alert("carregou clientes com sucesso");
			var $table = $('#tabela_clientes');
			$button = $('#cancelar_cliente');
		    $(function () {
		        $button.click(function () {
		        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
		                return row.id_cliente;
		            });
		            $table.bootstrapTable('remove', {
		                field: 'id_cliente',
		                values: ids
		            });
		            //alert(ids);
		            cancela_clientes(String(ids));
		        });
		    });
		    $button3 = $('#btn_editar_cliente');
		   // $button2.button();
			$(function () {
		        $button3.click(function () {
		        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
		                return row.id_cliente;
		            });
		        	if(String(ids)==""){
		        		bootbox.alert("Favor selecionar um registro da tabela de Clientes");
		        		return;
		        		}else{
		        			edita_cliente(String(ids));
		        		}
		        });
		    });
			
		}
	}
  function ativa_cep(){
	  $("#cliente_cep").blur(function() {

          //Nova variável "cep" somente com dígitos.
          var cep = $(this).val().replace(/\D/g, '');

          //Verifica se campo cep possui valor informado.
          if (cep != "") {

              //Expressão regular para validar o CEP.
              var validacep = /^[0-9]{8}$/;

              //Valida o formato do CEP.
              if(validacep.test(cep)) {

                  //Preenche os campos com "..." enquanto consulta webservice.
                  $("#cliente_endereco").val("...");
                  $("#cliente_bairro").val("...");
                  $("#cliente_cidade").val("...");
                  $("#cliente_UF").val("...");
                 // $("#ibge").val("...");

                  //Consulta o webservice viacep.com.br/
                  $.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                      if (!("erro" in dados)) {
                          //Atualiza os campos com os valores da consulta.
                          $("#cliente_endereco").val(dados.logradouro);
                          $("#cliente_bairro").val(dados.bairro);
                          $("#cliente_cidade").val(dados.localidade);
                          $("#cliente_UF").val(dados.uf);
                          //$("#ibge").val(dados.ibge);
                      } //end if.
                      else {
                          //CEP pesquisado não foi encontrado.
                          limpa_formulário_cep();
                          bootbox.alert("CEP não encontrado.");
                      }
                  });
              } //end if.
              else {
                  //cep é inválido.
                  limpa_formulário_cep();
                  bootbox.alert("Formato de CEP inválido.");
              }
          } //end if.
          else {
              //cep sem valor, limpa formulário.
              limpa_formulário_cep();
          }
      });
  }
  function limpa_formulário_cep() {
      // Limpa valores do formulário de cep.
      $("#cliente_endereco").val("");
      $("#cliente_bairro").val("");
      $("#cliente_cidade").val("");
      $("#cliente_UF").val("");
      $("#cliente_complemento").val("");
  }
  function edita_cliente(id){
		$.ajax({
			  type: "POST",
			  data: {"opt":"30",
				     "ids":id},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess30
			});
		function onSuccess30(data)
		{
			//alert(data);
			if(data=='Selecione apenas um registro para edição'){
				bootbox.alert(data);
			}else{
			info=JSON.parse(data);
			
			$('#cliente_nome_completo').val(info['nome']);
			$('#cliente_cpf').val(info['cpf']);
			$('#cliente_sexo').val(info['sexo']);
			$('#cliente_dt_nascimento').val(info['dt_nasc']);
			$('#cliente_estado_civil').val(info['estado_civil']);
			$('#cliente_pl_saude').val(info['plano_saude']);
			$('#cliente_numero_plano').val(info['numero_plano']);
			$('#cliente_bairro').val(info['bairro']);
			$('#cliente_cidade').val(info['cidade']);
			$('#cliente_endereco').val(info['endereco']);
			$('#cliente_celular').val(info['contato']);
			$('#cliente_email').val(info['email']);
			$('#cliente_cep').val(info['cep']);
			$('#cliente_complemento').val(info['complemento']);
			$('#cliente_UF').val(info['uf']);
			datahtml="";
			for(var i=1;i<info['categorias_quantidade'];i++){
				$('#select_categoria_cliente').multiselect('select', [info[i+'_categoria']]);
				  datahtml=datahtml+"<label class='control-label'>"+info[i+'_categoria']+"</label><input type=text id='"+info[i+'_categoria']+"' class='form-control' value='"+info[i+'_categoria_valor']+"'><br>"+"\n";
			}
			$("#campos_especialidade_cliente").html(datahtml);
			opera_cliente('ATUALIZACAO');
			}
		}
  }