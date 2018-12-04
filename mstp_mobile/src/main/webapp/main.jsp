<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>OdontoFlow</title>
    
  <!-- FontAwesome -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

  <!-- Twitter Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
  <!--  <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">--> 
  <link rel="stylesheet" href="css/tether.min.css" type="text/css">
  <link rel="stylesheet" href="js/date_picker_bootstrap/css/bootstrap-datepicker3.css">
  <link rel="stylesheet" href="css/bootstrap-table.css" type="text/css">
  <link rel="stylesheet" href="css/bootstrap-vertical-menu.css">
  <link rel="stylesheet" href="css/fileinput.min.css" type="text/css">
  <link rel="stylesheet" href="css/bootstrap-multiselect.css">
   <link rel="stylesheet" href="css/jsgrid-theme.min.css" type="text/css">
   <link rel="stylesheet" href="css/jsgrid.min.css" type="text/css">
  <link href='js/calendar/fullcalendar.min.css' rel='stylesheet' />
  <link href='js/calendar/fullcalendar.print.css' rel='stylesheet' media='print' />
  <link rel="stylesheet" href="css/input-preco.css">
  <link rel="stylesheet" href="css/colorbox.css" />
  <script src='js/jquery-2.1.3.min.js'></script>
  <script src="js/jquery.mask.js" type="text/javascript"></script>
  <script src="js/jquery.colorbox.js"></script>
   <script src="js/highcharts.js"></script>
   <script src="js/modules/drilldown.js"></script>
   <script src="js/modules/data.js"></script>
   <script src="js/tether.min.js" type="text/javascript"></script> 
   <script src="js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
	<script src="js/fileinput.min.js" type="text/javascript"></script>
	<script src="js/fileinput_locale_pt-BR.js" type="text/javascript"></script>
  <script src="js/bootstrap.min.js" type="text/javascript"></script>
  
  
  <script src="js/moment-with-locales.min.js"></script>
  <script src="js/bootstrap-table.js"></script>
  <script src="js/bootstrap-table-toolbar.min.js"></script>
  <script src="js/bootstrap-table-filter-control.js"></script>
  <script src="js/bootstrapTableLocale.js"></script>
  <script src="js/jsgrid.min.js"></script>
  <script src="js/locale/jsgrid-pt-br.js"></script>
  <script src="js/bootbox.min.js"></script>
 
  
  <script src="js/bootstrap3-typeahead.js" type="text/javascript"></script>
  <script src="js/bootstrap3-typeahead.min.js" type="text/javascript"></script>
  <script src="js/bootstrap-multiselect.js"></script>
  <script type="text/javascript" src="js/password-score.js"></script>
  <script type="text/javascript" src="js/password-score-options.js"></script>
  <script src="js/bootstrap-strength-meter.js"></script>
  
  <script src="js/accounting.js"></script>
 <script src="js/carregador_graficos.js"></script>
  <script src="js/date_picker_bootstrap/js/bootstrap-datepicker.js"></script>
  <script src="js/date_picker_bootstrap/locales/bootstrap-datepicker.pt-BR.min.js"></script>
  
   <script src="js/locale/pt-br.js"></script>
    <script src="js/senha.js"></script>
   <script src="js/info_usuario.js"></script>
   <script src="js/trata_servico.js"></script>
   <script src="js/trata_categorias.js"></script>
   <script src="js/trata_agenda.js"></script>
   <script src="js/trata_pro.js"></script>
   <script src="js/trata_atendimento.js"></script>
    <script src="js/trata_registros.js"></script>

<script src='js/calendar/fullcalendar.min.js'></script>
<script src='js/calendar/locale-all.js'></script>

<link rel="stylesheet" href="css/aux_css.css">
    <script>
    
    $(document).ready(function() {
    	
    	
      $('.link').click(function(event) {
    	  
    	$('.link').removeClass('selected');
        $(this).addClass('selected');
        var href = $(this).attr('href');
        $("#principal").children().hide();
        $(href).toggle();
        //alert(href);
        if(href=="#agenda"){
        	//alert(href);
        	$('#calendar').fullCalendar('render');
        }else if(href=="#clientes"){
        	document.getElementById("div_clientes_tabela").style.display="block";
        	document.getElementById("div_carrega_cliente").style.display="none";
        }else if(href=="#atendimento"){
        	document.getElementById("atendimento").style.display="block";
        	document.getElementById("atendimento_lista").style.display="block";
        	document.getElementById("atendimento_div").style.display="none";
        }else if(href=="#controles"){
        	document.getElementById("div_exibe_controles").style.display="block";
        	//document.getElementById("atendimento_lista").style.display="block";
        	document.getElementById("div_carrega_contracheque").style.display="none";
        }
        event.preventDefault();
      });
      $("#input_2").fileinput({
    	    language: "pt-BR",
		    uploadUrl: "./Upload_servlet?opt=1", 
		    uploadAsync: false,
		    allowedFileExtensions: ['png', 'jpg'],
		    maxFileCount: 1,
		    uploadExtraData:function (event,data,previewId, index) {
		        var obj = {cliente:$("#cliente_id_carregado").val()};
		        //obj[0]=$("#cliente_carrega_po").val();
		        //obj[1]=$("#projeto_carrega_po").val()
		        return obj;
		        
		    }
		}).on('fileuploaded', function(event, data, previewId, index) {
			atualiza_foto_cliente("",document.getElementById("cliente_id_carregado").value);
	}).on('filebatchuploadsuccess', function(event, data, previewId, index) {
			atualiza_foto_cliente("",document.getElementById("cliente_id_carregado").value);
	});
      $("#input_3").fileinput({
  	    language: "pt-BR",
		    uploadUrl: "./Upload_servlet?opt=2", 
		    uploadAsync: false,
		    allowedFileExtensions: ['png', 'jpg','pdf','doc','docx'],
		    maxFileCount: 5,
		    uploadExtraData:function (event,data,previewId, index) {
		        var obj = {cliente:$("#cliente_id_carregado").val(),agendamento:$("#id_agendamento_atendimento").val()};
		        //obj[0]=$("#cliente_carrega_po").val();
		        //obj[1]=$("#projeto_carrega_po").val()
		        return obj;
		        
		    }
		}).on('fileuploaded', function(event, data, previewId, index) {
			carrega_atendimento_arquivos_tabela();
	}).on('filebatchuploadsuccess', function(event, data, previewId, index) {
			carrega_atendimento_arquivos_tabela();
	});
      $('#valor_despesa').mask('000.000.000.000.000,00', {reverse: true});
      $('#servico_preco').mask('000.000.000.000.000,00', {reverse: true});
      $('#valor_preço').mask('000.000.000.000.000,00', {reverse: true});
      $('#horario_selecionado_agenda').mask('00:00');
      $('#from').datepicker({
          format: "dd/mm/yyyy",
          language: "pt-BR",
          autoclose: true,
          daysOfWeekHighlighted: "0,6",
          calendarWeeks: true
      });
      $('#to').datepicker({
          format: "dd/mm/yyyy",
          language: "pt-BR",
          autoclose: true,
          daysOfWeekHighlighted: "0,6",
          calendarWeeks: true
      });
      $('#hoje_entrada').datepicker({
          format: "dd/mm/yyyy",
          language: "pt-BR",
          autoclose: true,
          todayHighlight: true,
          daysOfWeekHighlighted: "0,6",
          calendarWeeks: true
      });
      $('#hoje_saida').datepicker({
          format: "dd/mm/yyyy",
          language: "pt-BR",
          autoclose: true,
          todayHighlight: true,
          daysOfWeekHighlighted: "0,6",
          calendarWeeks: true
      });
      $('#senha_atual').strengthMeter('text', {
          container: $('#senha_atual_texto'),
          hierarchy: {
              '0': ['text-danger', 'Assim não funciona :('],
              '5': ['text-warning', 'Fraacaaa ...'],
              '15': ['text-warning', 'tá bom, mas pode melhorar ...'],
              '20': ['text-success', 'Muito bom!']
          }
      });
      $('#nova_senha').strengthMeter('text', {
          container: $('#nova_senha_texto'),
          hierarchy: {
              '0': ['text-danger', 'Assim não funciona :('],
              '5': ['text-warning', 'Fraacaaa ...'],
              '15': ['text-warning', 'tá bom, mas pode melhorar ...'],
              '20': ['text-success', 'Muito bom!']
          }
      }); 
      $('#confirma_senha').strengthMeter('text', {
          container: $('#confirma_senha_texto'),
          hierarchy: {
              '0': ['text-danger', 'Assim não funciona :('],
              '5': ['text-warning', 'Fraacaaa ...'],
              '15': ['text-warning', 'tá bom, mas pode melhorar ...'],
              '20': ['text-success', 'Muito bom!']
          }
      });  
      carrega_servicos();
      carrega_controle_registros();
      carrega_clientes();
      carrega_dados_cliente_registro();
      g1(0,"grafico_registros");
      carrega_select_servico();
      carrega_meus_pagamentos();
      carrega_select_clinica();
      carrega_categorias();
      carrega_select_subtipo_servico(0,0);
      carrega_clientes_atendimento();
      ativa_cep();
      carrega_select_despesa_categoria();
      carrega_select_entrada_categoria();
      carrega_select_categoria_cliente();
      carrega_select_equipe();
      carrega_calendario('');
      carrega_dados_agendamento_servico();
      document.getElementById("defaultOpen").click();
      carrega_minha_equipe();
      $('#hoje_entrada').datepicker('update');
      $('.input-daterange').datepicker({
      });
      accounting.settings = {
				currency: {
					symbol : "R$",   // default currency symbol is '$'
					format: "%s%v", // controls output: %s = symbol, %v = value/number (can be object: see below)
					decimal : ",",  // decimal point separator
					thousand: ".",  // thousands separator
					precision : 2   // decimal places
				},
				number: {
					precision : 0,  // default precision on numbers is 0
					thousand: ".",
					decimal : ","
				}
			}
    });
  </script>
  <script src="js/trata_cliente.js"></script>
  <script>
 
  
  
   
 
  
  
  function opera_registro(operacao){
	  document.getElementById("operacao_registro").value=operacao;
		$('#modal_add_registro').modal("toggle");
  }
  
  
  function edita_registro(id){
		$.ajax({
			  type: "POST",
			  data: {"opt":"21",
				     "ids":id},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess21
			});
		function onSuccess21(data)
		{
			
			info=JSON.parse(data);
			var e,tipoc,tipoa,nome,desc,lista,categoria;
			if(info['tipo_registro']=="Entrada"){
				document.getElementById("radio_entrada").checked =true;
				cor('entrada');
				
				
				document.getElementById("hoje_entrada").value = info['dt_execucao'];
				$('#select_servico').multiselect('select', info['especialidade'].split(","));
				carrega_select_subtipo_servico(info['especialidade'],info['id_servico']);
				//console.log(info['id_servico']);
				//console.log(info['id_servico'].split(","));
				
				$('#select_subtiposervico').multiselect('select', info['id_servico'].split(","));
				//$('#select_subtiposervico').multiselect('rebuild');
				$('#select_equipe').val(info['usuario_execucao']);
				$("#servico_profissional").val(info['percentual_usuario']);
				$("#servico_clinica").val(info['percentual_clinica']);
				$("#cpf_cliente").val(info['dados_cliente1']);
				$("#nome_cliente").val(info['dados_cliente2']);
				$("#select_clinica_entrada").val(info['id_clinica']);
				$("#valor_preco").val(info['valor']);
				
				if(info['forma_pagamento']=='1'){
					
					$('#dinheiro_check').prop('checked', true);
					$('#cd_check').prop('checked', false);
					$('#cc_check').prop('checked', false);
					
				}else if (info['forma_pagamento']=='2'){
					$('#dinheiro_check').prop('checked', false);
					$('#cd_check').prop('checked', true);
					$('#cc_check').prop('checked', false);
				}else if (info['forma_pagamento']=='3'){
					$('#dinheiro_check').prop('checked', false);
					$('#cd_check').prop('checked', false);
					$('#cc_check').prop('checked', true);
					
				}
				
			}else{
				document.getElementById("radio_saida").checked =true;
				document.getElementById("radio_entrada").checked =false;
				cor('saida');
				tipo_registro="Saida";
				categoria=info['Categoria'];
				//alert(info['Categoria']);
				//alert(categoria);
				$("#select_categoria_registro").val(info['Categoria']);
				//alert(categoria);
				carrega_dados_categoria(categoria);
				$("#select_subcategoria_registro").change();
				$("#select_subcategoria_registro").val(info['SubCategoria']);
				$("#select_subcategoria_registro").change();
				$("#hoje_saida").val(info['dt_execucao']);
				$("#select_clinica_saida").val(info['id_clinica']);
				$("#valor_despesa").val(info['valor']);
				$("#despesa_status").val(info['status']);
				$("#despesa_descricao").val(info['servico']);
				
			}
			//document.getElementById("tipo_campo_rollout").value=info['tipo_Campo'];
			
		//		mostra_div(info['tipo_Campo']);
			
			//tipoc=e.options[e.selectedIndex].text;
			//
			//document.getElementById("select_servico").value = info['nome_campo'];
			//document.getElementById("hoje_entrada").value = info['nome_campo'];
			//document.getElementById("tipo_atributo_rollout").value=info['sub_tipo'];
			//document.getElementById("trigger_pagamento").checked=info['trigger'];
			//document.getElementById('percent_pagamento').value=info['percent']
			//	mostra_div_lista(info['sub_tipo']);
			
			//$('#Modal_campos_rollout').modal('show');
			opera_registro("ATUALIZACAO");
		}

	}
  function carrega_meus_pagamentos(){
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"62"},		  
			  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess62
			});
		function onSuccess62(data)
		{
			if(data=="UNC"){
				$('#modal_UNC').modal("show");
			}else{
				$("#div_meus_pagamentos").html(data);
				$('#tabela_meus_pagamentos').bootstrapTable();
				}
		    
		}

	}
  
  function carrega_minha_equipe(){
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"5"},		  
			  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess5
			});
		function onSuccess5(data)
		{
			$("#div_minha_equipe").html("<div id=\"toolbar_minha_equipe\" role=\"toolbar\" class=\"btn-toolbar\">"+
	        		
	    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal_convida_equipe\" onclick=\"carrega_pessoas()\">Convidar +</button>"+
	    			"<button id=\"validar_equipe\" type=\"button\" class=\"btn btn-danger\">Remover da Equipe</button>"+
	    			
	    		    "</div>" + data);
			$('#tabela_minha_equipe').bootstrapTable();
		    
		}

	}
  function carrega_pessoas(){
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"6"},		  
			  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess6
			});
		function onSuccess6(data)
		{
			$("#div_pessoas").html("<div id=\"toolbar_pessoas\" role=\"toolbar\" class=\"btn-toolbar\">"+
	        		
	    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#\">Convidar</button>"+
	    			
	    			
	    		    "</div>" + data);
			$('#tabela_pessoas').bootstrapTable();
		    
		}

	}
 

  function carrega_select_clinica(){
	  //alert("buscando clinica");
	  $.ajax({
		  type: "POST",
		  data: {"opt":"22"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess22
		});
	  function onSuccess22(data){
		  $("#select_clinica_entrada").html(data);
		  $("#select_clinica_saida").html(data);
	  }
  }
 
 
  
  function carrega_select_equipe(){
	  $.ajax({
		  type: "POST",
		  data: {"opt":"8"},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess8
		});
	  function onSuccess8(data){
		  $("#select_equipe").html(data);
		  $("#select_equipe_agendamento").html(data);
	  }
  }
  
  function sair(){
		$.ajax({
			  type: "POST",
			  data: {"opt":"99"
				},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  
			});
	}
 
  </script>
</head>
<body>
<div style="position: absolute;z-index:10">
  <nav id="menu" class="navbar navbar-vertical-left">
    <ul class="nav navbar-nav">
      <li class="link_atendimento" >
        <a href="#atendimento" class="link" style="color:black;" >
          <i class="fa fa-fw fa-lg fa-user-md"></i> 
          <span>Atendimento</span>
        </a>
      </li>
      <li>
        <a href="#registros" class="link">
          <i class="fa fa-fw fa-lg fa-exchange"></i> 
          <span>Registros</span>
        </a>
      </li>
      <li>
        <a href="#clientes" class="link">
          <i class="fa fa-fw fa-lg fa-users"></i> 
          <span>Clientes</span>
        </a>
      </li>
      <li>
        <a href="#agenda" class="link">
          <i class="fa fa-fw fa-lg fa-calendar"></i> 
          <span>Agenda</span>
        </a>
      </li>
      <li>
        <a href="#controles" class="link">
          <i class="fa fa-fw fa-lg fa-cog"></i> 
          <span>Controles</span>
        </a>
      </li>
    </ul>
  </nav>
  </div>
<div id="principal" class="container">
	
    <div id="agenda" style="display:none">
    <table style="width:100%;"><tr>
    	<td style="text-align: left;"><label style="font-size:xx-large;"><%= session.getAttribute("nome_empresa")%></label><label style="color:orange">©OdontoFlow</label></td>
    	<td style="text-align: right;"><a href="index.html"><img alt="Sair" title="Sair" src="img/logout.png" onclick="sair()" ></a></td>
    </tr></table>
    	<div id="mostra_eventos" style="float:left;padding:10px">
    	
		
		</div><div id="calendar" style="float:left;"></div>
    </div>
    <div id="atendimento" style="display:none">
    <table style="width:100%;"><tr>
    	<td style="text-align: left;"><label style="font-size:xx-large;"><%= session.getAttribute("nome_empresa")%></label><label style="color:orange">©OdontoFlow</label></td>
    	<td style="text-align: right;"><a href="index.html"><img alt="Sair" title="Sair" src="img/logout.png" onclick="sair()" ></a></td>
    </tr></table>
    <div id="atendimento_lista" ></div>
    <div id="atendimento_div" style="display:none">
    <input type="hidden" id="id_agendamento_atendimento" value="">
    <div id="accordion2" role="tablist" aria-multiselectable="false">
    	<div class="card">
		    <div class="card-header" role="tab" id="titulodados_atendimento">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion2" href="#dados_atendimento" aria-expanded="false" aria-controls="dados_atendimento">
		          Atendimento
		        </a>
		      </h5>
		    </div>
		    <div id="dados_atendimento" class="collapse" role="tabpanel" aria-labelledby="titulodados_atendimento">
		      <div class="card-block" id="dados_atendimento2">
		       <textarea id="procedimento" class="form-control name="textarea" rows="14" style="font-size: 20px;" placeholder="Procedimento/Medicação"></textarea>
		       <a href="#" data-toggle="modal" data-target="#modal_carrega_arquivos">Carregar Arquivos para esse atendimento</a>
		       <div id="tabela_atendimento_arquivos_div"></div>
		       <div class="modal-footer">
	        			<button id="btn_ficha_atendimento" type="button" class="btn btn-info" onclick="gerar_ficha_atendimento()">Ficha do Procedimento</button><button id="btn_salva_procedimento_atendimento" type="button" class="btn btn-info" onclick="salva_procedimento_atendimento()">Salvar</button>
	      		</div>
		      </div>
		    </div>
		  </div>
    	<div class="card">
		    <div class="card-header" role="tab" id="tituloconsultas_anteriores">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#consultas_anteriores" aria-expanded="false" aria-controls="consultas_anteriores">
		          Consultas Anteriores
		        </a>
		      </h5>
		    </div>
		    <div id="consultas_anteriores" class="collapse" role="tabpanel" aria-labelledby="tituloconsultas_anteriores">
		      <div class="card-block" id="tabela_de_consultas2">
		        
		      </div>
		    </div>
		  </div>
		  <div class="card">
		    <div class="card-header" role="tab" id="headingThree_5">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion2" href="#todos_os_arquivos" aria-expanded="false" aria-controls="todos_os_arquivos">
		          Todos os Arquivos
		        </a>
		      </h5>
		    </div>
		    <div id="todos_os_arquivos" class="collapse" role="tabpanel" aria-labelledby="headingThree_5">
		      <div class="card-block" id="tabela_de_todos_os_arquivos">
		        ...
		      </div>
		    </div>
		  </div>
    </div>
    </div>
    </div>
	<div id="clientes" style="display:none">
	<table style="width:100%;"><tr>
    	<td style="text-align: left;"><label style="font-size:xx-large;"><%= session.getAttribute("nome_empresa")%></label><label style="color:orange">©OdontoFlow</label></td>
    	<td style="text-align: right;"><a href="index.html"><img alt="Sair" title="Sair" src="img/logout.png" onclick="sair()" ></a></td>
    </tr></table>
	<div id="div_clientes_tabela"></div>
	<div id="div_carrega_cliente" style="display:none">
	<input type="hidden" id="cliente_id_carregado" value="">
		<img class="img-rounded img-responsive" id="image_cliente" src="img/homem.png" alt="Sem Foto" width="15%" height="12%"><a href="#" data-toggle="modal" data-target="#modal_carrega_foto">Alterar Imagem</a>
		<div id="accordion" role="tablist" aria-multiselectable="false">
			  <div class="card">
			    <div class="card-header" role="tab" id="headingOne">
			      <h5 class="mb-0">
			        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
			          Dados Pessoais
			        </a>
			      </h5>
			    </div>

			    <div id="collapseOne" class="collapse in" role="tabpanel" aria-labelledby="headingOne">
			      <div class="card-block">
			        <input type="hidden" id="operacao_cliente" value="">
			        <div class="modal-footer">
	        			<button id="btn_add_cliente" type="button" class="btn btn-info add_clientebt" onclick="add_cliente()">Salvar Alterações</button>
	      			</div>
	      			<table >
			      		<tr>
			      			<td style="padding: 5px"><label class="control-label">Nome Completo</label></td>
			      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_nome_completo"></td>
			      		</tr>
			      		<tr>
			      			<td style="padding: 5px"><label class="control-label">CPF</label></td>
			      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_cpf"></td>
			      		</tr>
			      		
			      		<tr>
			      			<td style="padding: 5px"><label class="control-label">Sexo</label></td>
			      			<td style="padding: 5px"><select  class="form-control cliente" id="cliente_sexo"><option value="Masculino">Masculino</option><option value="Feminino">Feminino</option></select></td>
			      		</tr>
			      		<tr>
			      			<td style="padding: 5px"><label class="control-label">Data Nascimento</label></td>
			      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_dt_nascimento"></td>
			      		</tr>
			      		<tr>
			      			<td style="padding: 5px"><label class="control-label">Estado Civil</label></td>
			      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_estado_civil"></td>
			      		</tr>
	      			</table>
			      </div>
    		</div>
  		</div>
		  <div class="card">
		    <div class="card-header" role="tab" id="headingTwo">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
		          Endereço e Contato
		        </a>
		      </h5>
		    </div>
		    <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="headingTwo">
		      <div class="card-block">
		      	<div class="modal-footer">
	        		<button id="btn_add_cliente" type="button" class="btn btn-info add_clientebt" onclick="add_cliente()">Salvar Alterações</button>
	      		</div>
		        <table>
			        <tr>
		      			<td style="padding: 5px"><label class="control-label">Celular</label></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_celular"></td>
		      		</tr>
		      		<tr>
		      			<td style="padding: 5px"><label class="control-label">Email</label></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_email"></td>
		      		</tr>
		      		<tr>
		      			<td style="padding: 5px"><label class="control-label">CEP</label></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_cep"></td>
		      		</tr>
		      		<tr>
		      			<td style="padding: 5px"><label class="control-label">Bairro</label></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_bairro"></td>
		      		</tr>
		      		<tr>
		      			<td style="padding: 5px"><label class="control-label">Cidade</label></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_cidade"></td>
		      		</tr>
		      		<tr>
		      			<td style="padding: 5px"><label class="control-label">Endereço</label></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_endereco"></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_complemento" placeholder="Numero,Casa,Apto,Bloco"></td>
		      		</tr>
		      		<tr>
		      			<td style="padding: 5px"><label class="control-label">UF</label></td>
		      			<td style="padding: 5px"><input type="text" class="form-control cliente" id="cliente_UF"></td>
		      		</tr>
	      		</table>
		      </div>
		    </div>
		  </div>
		  <div class="card">
		    <div class="card-header" role="tab" id="headingThree">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
		          Consultas
		        </a>
		      </h5>
		    </div>
		    <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
		      <div class="card-block" id="tabela_de_consultas">
		        ...
		      </div>
		    </div>
		  </div>
		   <div class="card">
		    <div class="card-header" role="tab" id="arquivosGeral_clientetitulo">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#arquivosGeral_cliente" aria-expanded="false" aria-controls="arquivosGeral_cliente">
		          Arquivos
		        </a>
		      </h5>
		    </div>
		    <div id="arquivosGeral_cliente" class="collapse" role="tabpanel" aria-labelledby="arquivosGeral_clientetitulo">
		    <input type="hidden" id="quantidade_categorias_cliente" value="">
		      <div class="card-block" id="arquivosGeral_clienteUpload">
		        <a href="#" class="btn btn-info" style="width:100%; 24px;z-index: auto;" data-toggle="modal" data-target="#modal_carrega_arquivos">Upload de Arquivos</a><p></p>
		      <div id="arquivosGeral_clienteExibir"></div>
		      </div>
		      
		    </div>
		  </div>
		   <div class="card">
		    <div class="card-header" role="tab" id="headingThree">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapsefour" aria-expanded="false" aria-controls="collapseThree">
		          Mais Informações
		        </a>
		      </h5>
		    </div>
		    <div id="collapsefour" class="collapse" role="tabpanel" aria-labelledby="headingThree">
		    <input type="hidden" id="quantidade_categorias_cliente" value="">
		      <div class="card-block" id="atributos_cliente">
		        Categorias
		      </div>
		    </div>
		  </div>
		  <div class="card">
		    <div class="card-header" role="tab" id="headingThree">
		      <h5 class="mb-0">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapsefive" aria-expanded="false" aria-controls="collapseThree">
		          Estatisticas
		        </a>
		      </h5>
		    </div>
		    <div id="collapsefive" class="collapse" role="tabpanel" aria-labelledby="headingThree">
		      <div class="card-block">
		        Previsao de entrega desta Funcão: 30/11/2016
		      </div>
		    </div>
		  </div>
	</div>
	</div>
	</div>
	<div id="registros" style="diplay:none">
	<table style="width:100%;"><tr>
    	<td style="text-align: left;"><label style="font-size:xx-large;"><%= session.getAttribute("nome_empresa")%></label><label style="color:orange">©OdontoFlow</label></td>
    	<td style="text-align: right;"><a href="index.html"><img alt="Sair" title="Sair" src="img/logout.png" onclick="sair()" ></a></td>
    </tr></table>
	<div id="tab_registros" >
		<ul class="tab">
		    <li><a href="#" class="tablinks" id="defaultOpen" onclick="openCity(event, 'fluxodecaixa')">Fluxo de Caixa</a></li>
		    <li><a href="#" class="tablinks" onclick="openCity(event, 'areceber')">À Receber</a></li>
	    </ul>
	   
		<div role="tabpanel" class="tabcontent" id="fluxodecaixa">
		<div id="toolbar_tabela_registros" role="toolbar" class="btn-toolbar">
	    			<button id="btn_novo_registro" type="button" class="btn btn-primary" onclick="opera_registro('NOVO')">Novo Registro</button>
	    			<button id="btn_editar_registro" type="button" class="btn btn-info" >Editar Registro</button>
	    			<button id="cancelar_registro" type="button" class="btn btn-danger">Cancelar Registro</button>
	    </div>
		<table id="tabela_registro" 
			data-use-row-attr-func="true" 
			data-toolbar="#toolbar_tabela_registros" 
			data-reorderable-rows="true" 
			data-show-refresh="true" 
			data-toggle="table"  
			data-locale="pt-BR" 
			data-url="./Op_Servlet?opt=2" 
			data-side-pagination="server"
			data-filter-control="true" 
			data-click-to-select="true" 
			data-pagination="true" 
			data-page-size="5" 
			data-page-list="[5, 10, 20, 50, 100, 200]"
			data-search="true" 
			data-sort-name="id_registro" 
			data-sort-order="desc" 
			data-show-toggle="true" 
			data-show-columns="true">
				<thead>
				<tr>
					<th data-checkbox="true" ></th>
					<th data-field="id_registro" data-sortable="true" >ID</th>
					<th data-field="servico" data-sortable="true" data-filter-control="select" >Servico-Despesa</th>
					<th data-field="dt_execucao" class="span1">Data</th>
					<th data-field="status" data-visible="false" data-sortable="true" >Status</th>
					<th data-field="valor" data-formatter="nameFormatter">Valor</th>
					<th data-field="tipo_registro" >Fluxo</th>
					<th data-field="usuario_execucao" >Usuário Execução</th>
					<th data-field="clinica" >Clínica</th>
					<th data-field="Categoria" >Categoria</th>
					<th data-field="SubCategoria" >SubCategoria</th>
					<th data-field="dados_cliente2" data-formatter="nameFormatter">Cliente</th>
					<th data-field="usuario_abertura" data-visible="false" >Usuario Registro</th>
					<th data-field="forma_pagamento" data-visible="false" >Pagamento</th>
				</tr>
			</thead>
		</table>
		<script>
    function nameFormatter(value, row) {
        
        return '<div class="coluna_larga">' + value + '</div>' ;
    }
    </script>
		</div>
		<div role="tabpanel" class="tabcontent" id="areceber">
			<div id="toolbar_tabela_fluxo_pg" role="toolbar" class="btn-toolbar">
	    			<button id="btn_efetivar_flow" type="button" class="btn btn-primary">Efetivar Pagamento</button>
	    			
	    </div>
		<table id="tabela_fluxo_pg" 
			data-use-row-attr-func="true" 
			data-toolbar="#toolbar_tabela_fluxo_pg" 
			data-reorderable-rows="true" 
			data-show-refresh="true" 
			data-toggle="table"  
			data-locale="pt-BR" 
			data-url="./Op_Servlet?opt=59" 
			data-side-pagination="server"
			data-filter-control="true" 
			data-click-to-select="true" 
			data-pagination="true" 
			data-page-size="5" 
			data-page-list="[5, 10, 20, 50, 100, 200]"
			data-search="true" 
			data-sort-name="id_registro" 
			data-sort-order="desc" 
			data-show-toggle="true" 
			data-show-columns="true">
				<thead>
				<tr>
					<th data-checkbox="true" ></th>
					<th data-field="id_fuxo_sys" data-sortable="true" >ID</th>
					<th data-field="tipo_pagamento" data-visible="true" data-sortable="true" >Tipo Pagamento</th>
					<th data-field="id_fluxo_cli" data-visible="true" data-sortable="true">ID Registros</th>
					<th data-field="parcelas" data-sortable="true" data-filter-control="select" >Parcelas</th>
					<th data-field="valor" data-formatter="nameFormatter">Valor</th>
					<th data-field="status" class="span1">Status</th>
				</tr>
			</thead>
		</table>
		
		</div>
	
	</div>
		<div id="controle_grafico">
			<div>
	          <!-- Input ao qual foi designado a função para exibir o calendário, que vai ser selecionado com jquery na função abaixo. -->
	          <table>
	          	<tr>
	          		<td><div class="input-daterange input-group" id="datepicker">
    					<input type="text" class="input-sm form-control" name="start" id="from"/>
    					<span class="input-group-addon">até</span>
    					<input type="text" class="input-sm form-control" name="end" id="to"/>
					</div></td>
	          		
	          		
	          		<td><button type="button" class="btn btn-info"  onclick="g1(1,'grafico_registros')">Serviço</button></td>
	          		<td><button type="button" class="btn btn-info"  onclick="g3(1,'grafico_registros')">Equipe</button></td>
	          		
	          		<td><div class="btn-group">
  <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Fluxo de Caixa <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="#" onclick="g2('','grafico_registros')">Mensal</a></li>
    <li><a href="#" onclick="g9(1,'grafico_registros')">Semanal</a></li>
    
  </ul>
  <input type="hidden" id="ano_fluxo" value="">
</div></td>
	          		<td><button type="button" class="btn btn-info"  onclick="g4('Entrada','grafico_registros')">Categoria de Entrada</button></td>
	          		<td><button type="button" class="btn btn-info"  onclick="g4('Saida','grafico_registros')">Categoria de Saida</button></td>
	          	</tr>
	          </table>
	          
	        
	        </div>
	     </div>
		<div id="grafico_registros" style="min-width: 310px; height: 400px; margin: 0 auto"></div>	
	</div>
	<div id="controles" style="display:none">
			<table style="width:100%;"><tr>
    	<td style="text-align: left;"><label style="font-size:xx-large;"><%= session.getAttribute("nome_empresa")%></label><label style="color:orange">©OdontoFlow</label></td>
    	<td style="text-align: right;"><a href="index.html"><img alt="Sair" title="Sair" src="img/logout.png" onclick="sair()" ></a></td>
    </tr></table>
    	<div id="div_exibe_controles">
		  <!-- Nav tabs -->
		  <ul class="tab">
		    <li><a href="#" class="tablinks" onclick="openCity(event, 'servicos')">Serviços</a></li>
		    <li><a href="#" class="tablinks" onclick="openCity(event, 'despesas')">Categorias</a></li>
		    <li><a href="#" class="tablinks" onclick="openCity(event, 'equipe')">Equipe</a></li>
		    <li><a href="#" class="tablinks" onclick="openCity(event, 'mercado_pago')">Meus Pagamentos</a></li>
		    <li><a href="#" class="tablinks" onclick="openCity(event, 'clinica')">Minha Clínica</a></li>
		    <li><a href="#" class="tablinks" onclick="openCity(event, 'minhas_info')">Informações</a></li>
            <li><a href="#" class="tablinks" onclick="openCity(event, 'troca_senha')">Senha</a></li>
           	<li><a href="#" class="tablinks" onclick="openCity(event, 'settings')">Aprovações</a></li>
		  </ul>

		  <!-- Tab panes -->
		 <div class="tab-content">
		    <div class="tabcontent" id="servicos">
		    	<div id="div_tabela_servico"></div>
		    </div>
		    <div class="tabcontent" id="despesas">
		    	<div id="div_tabela_despesas"></div>
		    </div>
		    <div class="tabcontent" id="minhas_info">
			    <table>
			   		<tr><td style="padding: 10px"><label class="control-label">Download</label></td><td style="padding: 10px"><button type="button" class="btn btn-info" id="info_carregar" onclick="busca_minha_info()"><span class="glyphicon glyphicon-download"></span></button></td></tr>
			    	<tr><td style="padding: 10px"><label class="control-label">Usuário</label></td><td style="padding: 10px"><input type="text" id="info_usuario" class="form-control"></td></tr>
			    	<tr><td style="padding: 10px"><label class="control-label">Nome</label></td><td style="padding: 10px"><input type="text" id="info_nome" class="form-control"></td></tr>
			    	<tr><td style="padding: 10px"><label class="control-label">Email</label></td><td style="padding: 10px"><input type="text" id="info_email" class="form-control"></td></tr>
			    	<tr><td style="padding: 10px"><label class="control-label">CRO</label></td><td style="padding: 10px"><input type="text" id="info_cro" class="form-control"></td></tr>
			    	<tr><td style="padding: 10px"><label class="control-label">Cel</label></td><td style="padding: 10px"><input type="text" id="info_cel" class="form-control"></td></tr>
			    	<tr><td style="padding: 10px"><label class="control-label"></label></td><td style="padding: 10px"><button type="button" class="btn btn-primary" id="info_update" onclick="atualiza_minha_info()">Atualizar Informações</button></td></tr>
			    </table>
		    </div>
		    <div class="tabcontent" id="troca_senha">
		    <br>
		    <br>
	        	<div class="form-group">
	        	<div class="form-group">
	    			<label class="form-label col-sm-2">Senha Atual</label>
			        <div class="col-sm-4">
			            <input type="password" data-togle="password" placeholder="Senha Atual..." class="form-control" id="senha_atual" />
			        </div>
        			<div class="col-sm-6" id="senha_atual_texto" style="font-weight:bold;padding:6px 12px;"></div>
        		</div>
        		<div class="form-group">
        			<label class="form-label col-sm-2">Nova Senha</label>
			        <div class="col-sm-4">
			            <input type="password" data-togle="password" placeholder="Nova Senha ..." class="form-control" id="nova_senha" />
			        </div>
        			<div class="col-sm-6" id="nova_senha_texto" style="font-weight:bold;padding:6px 12px;"></div>
        		</div>
        		<div class="form-group">
        			<label class="form-label col-sm-2">Confirma Senha</label>
			        <div class="col-sm-4">
			            <input type="password" data-togle="password" placeholder="Confirma Senha ..." class="form-control" id="confirma_senha" />
			        </div>
        			<div class="col-sm-6" id="confirma_senha_texto" style="font-weight:bold;padding:6px 12px;"></div>
        		</div>
				      <table width=100%>
				      		
					        <tr><td style="padding: 10px"><button id="troca_senha_btn" type="button" class="btn btn-info" onclick="trocadeSenha()">Trocar Senha</button></td></tr>	
				      	</table>
				
	      		</div>
        </div>
		    <div class="tabcontent" id="equipe">
		    	<div id="div_minha_equipe"></div>
		    </div>
		    <div class="tabcontent" id="mercado_pago">
		    	<div id="div_meus_pagamentos"> Pagamentos do Mercado Pago</div>
		    </div>
		    <div class="tabcontent" id="clinica">
		    	<table >
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Nome Completo</label></td>
	      			<td style="padding: 5px"><label class="control-label">Nome Fantasia</label></td>
	      			<td style="padding: 5px"><label class="control-label">Tipo</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_nome_completo"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_nome_fantasia"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_tipo"></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">CNPJ</label></td>
	      			<td style="padding: 5px"><label class="control-label">I.E.</label></td>
	      			<td style="padding: 5px"><label class="control-label">Insc. Municipal</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_cnpj"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_ie"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_im"></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Endereço</label></td>
	      			<td style="padding: 5px"><label class="control-label">Numero</label></td>
	      			<td style="padding: 5px"><label class="control-label">Bairro</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinca_end"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinca_num"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinca_bairro"></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Municipio</label></td>
	      			<td style="padding: 5px"><label class="control-label">Estado</label></td>
	      			<td style="padding: 5px"></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_municipal"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="clinica_estado"></td>
	      			<td style="padding: 5px"></td>
	      		</tr>
	      	</table>
		    
		    </div>
		    <div class="tabcontent" id="settings">...</div>
  		</div>
  		</div>
	<input type="hidden" value="" id="pro_id_carregado">
	<div id="div_carrega_contracheque" style="display:none">
		<h1 class="extrato_titulo">Extrato de Serviços e Renda</h1>
		<div id="div_dados_pro">
			<img class="img-rounded img-responsive" id="image_pro" src="img/homem.png" alt="Sem Foto" width="15%" height="12%"><a href="#" data-toggle="modal" data-target="#modal_carrega_foto">Alterar Imagem</a>
		</div>
		<div id="div_dados_pro_receitas">
		<table id="tabela_dados_pro_receitas" 
			data-use-row-attr-func="true" 
			data-reorderable-rows="true" 
			data-toggle="table"  
			data-locale="pt-BR" 
			
			data-detail-view="true"
			>
				<thead>
				<tr>
					<th data-field="pro_servico" >Categoria do Serviço</th>
					<th data-field="valor" data-formatter="nameFormatter">Valor a receber</th>
				</tr>
			</thead>
		</table>
		
		</div>
		<div id="div_dados_pro_custos">Carregando Tabelas de Custos</div>
		<div id="div_dados_pro_total">Totalizando</div>
	</div>
	</div>
	</div>
	<div id="modal_add_servico" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Adicionar Novo Servico</h4>
	      </div>
	      <div class="modal-body">
	      	<div class="form-group">
	      	<input type="hidden" id="operacao_servico" value="">
	      	<table >
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Nome </label></td>
	      			<td style="padding: 5px"><label class="control-label">Tipo</label></td>
	      			<td style="padding: 5px"><label class="control-label">Especialidade</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="servico_nome_completo"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="servico_tipo"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="servico_especialidade"></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Preço</label></td>
	      			<td style="padding: 5px"><label class="control-label">% Profissional</label></td>
	      			<td style="padding: 5px"><label class="control-label">Código</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="servico_preco"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="servico_percent"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control" id="servico_codigo"></td>
	      		</tr>
	      		
	      	</table>
				<hr>
		      
			</div>
	      </div>
	      <div class="modal-footer">
	        <button id="btn_add_servico" type="button" class="btn btn-info" data-dismiss="modal" onclick="add_servico()">Adicionar Servico</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	
	  </div>
	</div>
	
	
	
	<div id="modal_add_despesa" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Adicionar Nova Categoria</h4>
	      </div>
	      <div class="modal-body">
	      	<div class="form-group">
	      	<div class="form-group">
	      	<label class="form-check-inline">
  				<input class="form-check-input" type="radio" name="categoria_tipo" id="Entrada_radio" value="Entrada" onclick="carrega_select_entrada_categoria()"> Entrada
			</label>
			<label class="form-check-inline">
  				<input class="form-check-input" type="radio" name="categoria_tipo" id="Despesa_radio" value="Despesa" onclick="carrega_select_despesa_categoria()"> Despesa
			</label>
			<label class="form-check-inline">
  				<input class="form-check-input" type="radio" name="categoria_tipo" id="Despesa_radio" value="Cliente" onclick="carrega_select_despesa_categoria()"> Cliente
			</label>
	      	</div>
	      	<input type="hidden" id="operacao_categoria" value="">
	      	<table >
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Categoria Existente</label></td>
	      			<td style="padding: 5px"><label class="control-label">Sub Categoria</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><select class="form-control categoria" id="despesa_categoria"><option>teste</option></select></td>
	      			<td style="padding: 5px"><input type="text" class="form-control categoria" id="despesa_subcategoria"></td>
	      			
	      		</tr>
	      	</table>
	      	<label class="form-check-label"><input type="checkbox" class="form-check-input" id="nova_categoria" onclick="mostra_tabela_nova_categoria()"> Nova Categoria</label>
	      	<script>function mostra_tabela_nova_categoria(){if (document.getElementById("nova_categoria").checked){ document.getElementById("tabela_nova_categoria").style.display="block";}else{document.getElementById("tabela_nova_categoria").style.display="none";}}</script>
	      	<table id="tabela_nova_categoria" style="display:none">
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label ">Nova Categoria</label></td>
	      			<td style="padding: 5px"><label class="control-label ">Sub Categoria</label></td>
	      		
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control categoria" id="despesa_novacategoria"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control categoria" id="despesa_novasubcategoria"></td>
	      			
	      		</tr>
	      		
	      	</table>
				<hr>
		      
			</div>
	      </div>
	      <div class="modal-footer">
	        <button id="btn_add_despesa" type="button" class="btn btn-info" onclick="add_categoria()">Adicionar Categoria</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	
	  </div>
	</div>
	
	
	
	
	<div id="modal_add_registro" class="modal fade"  role="dialog">
	  <div class="modal-dialog modal-lg">
	
	    <!-- Modal content-->
	    
	    <div class="modal-content">
	      <div class="modal-header" id="cabecalho_registro" style="background-color: #99c2ff">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Adicionar Registro</h4></label><input type="text" class="input-preco" id="valor_preco">
	      </div>
	      <div class="modal-body">
	      	<div class="modal-body" id="tipo_registro">
	      	<input type="hidden" id="operacao_registro" value="">
		      	<fieldset class="form-group">
				    
				    <div class="radio-inline">
				      <label>
				        <input type="radio" name="optionsRadios" id="radio_entrada" value="Entrada" checked onClick="cor('entrada')">
				       Entrada
				      </label>
				    </div>
				    <div class="radio-inline">
				      <label>
				        <input type="radio" name="optionsRadios" id="radio_saida" value="Saida" onClick="cor('saida')">
				        Saida
				      </label>
				    </div>
				    
	  		</fieldset>
	      	<script>function cor(tipo){if (tipo=="entrada"){$("#cabecalho_registro").css("background-color","#99c2ff");$("#campos_registro_saida").hide();$("#campos_registro_entrada").show();carrega_select_entrada_categoria();}else{$("#cabecalho_registro").css("background-color","#ff9999");$("#campos_registro_entrada").hide();$("#campos_registro_saida").show();}}</script>
	      	</div>
	      	<div class="form-group" id="campos_registro_saida" style="display:none">
	      	<div id="jsGrid3"></div>
	      		
				<hr>
	      	
	      	</div>
	      	<div class="form-group" id="campos_registro_entrada">
	      	
	      	<table >
	      			
	      		<tr>
	      			<td style="padding: 5px"><select class="form-control registro" id="select_servico" multiple="multiple"></select></td>
	      			<td style="padding: 5px"><select id="select_subtiposervico" class="form-control registro" multiple="multiple"></select></td>
	      			<td style="padding: 5px"><button type="button" class="btn btn-info" onclick="carrega_tabela_registro_servico()">Adicionar Serviço(s)</button></td>
	      		</tr>
	      		</table>
	      		<div id="jsGrid"></div>
				<br><br>
				<div id="jsGrid2"></div>
	      		<table>
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Categoria</label></td>
	      			<td style="padding: 5px"><label class="control-label">Sub Categoria</label></td>
	      			<td style="padding: 5px"><label class="control-label">Data do Serviço</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><select id="select_categoria_registro_entrada" class="form-control registro" onchange="carrega_dados_categoria_entrada(this)"><option>RJ</option><option>SP</option></select></td>
	      			<td style="padding: 5px"><select class="form-control registro" id="select_subcategoria_registro_entrada"><option>------</option></select></td>
	      			<td style="padding: 5px"><input type="text" id="hoje_entrada" class="form-control registro" ></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Profissional</label></td>
	      			<td style="padding: 5px"><label class="control-label">% Profissional</label></td>
	      			<td style="padding: 5px"><label class="control-label">% Clinica</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><select class="form-control" id="select_equipe"><option>carregando</option></select></td>
	      			<td style="padding: 5px"><input type="text" class="form-control registro" id="servico_profissional"></td>
	      			<td style="padding: 5px"><input type="text" class="form-control registro" id="servico_clinica"></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><label class="control-label">Nome Cliente</label></td>
	      			<td style="padding: 5px"><label class="control-label">CPF Cliente</label></td>
	      			<td style="padding: 5px"><label class="control-label">Clínica</label></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 5px"><input type="text" class="form-control registro" data-provide="typeahead" id="nome_cliente" ></td>
	      			<td style="padding: 5px"><input type="text" class="form-control registro" id="cpf_cliente" ></td>
	      			<td style="padding: 5px"><select class="form-control registro" id="select_clinica_entrada"><option>RJ</option><option>SP</option></select></td>
	      		</tr>
	      		
	      	</table>
				<hr>
		      
			</div>
	      </div>
	      <div class="modal-footer">
	        <button id="btn_add_registro" type="button" class="btn btn-info" onclick="add_registro()">Registrar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	
	  </div>
	</div>
	
	<div id="modal_convida_equipe" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Adicionar Membro na Equipe</h4>
	      </div>
	      <div class="modal-body">
	      	<div class="form-group">
	      	<div id="div_pessoas"></div>
				<hr>
		      
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	
	  </div>
	</div>
	
	<div id="modal_carrega_foto" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Carregar Foto</h4>
      </div>
      <div class="modal-body">
      	<input id="input_2" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 
<div id="modal_carrega_arquivos" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Carregar Arquivos</h4>
      </div>
      <div class="modal-body">
      	<input id="input_3" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 
<div id="modal_UNC" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Cadastre-se no MercapPago</h4>
      </div>
      <div class="modal-body">
      	<a mp-mode="dftl" href="http://mpago.la/gVm9" name="MP-payButton" class='blue-ar-l-rn-none'>Assinar</a>
<script type="text/javascript">
    (function() {
        function $MPC_load() {
            window.$MPC_loaded !== true && (function() {
                var s = document.createElement("script");
                s.type = "text/javascript";
                s.async = true;
                s.src = document.location.protocol + "//secure.mlstatic.com/mptools/render.js";
                var x = document.getElementsByTagName('script')[0];
                x.parentNode.insertBefore(s, x);
                window.$MPC_loaded = true;
            })();
        }
        window.$MPC_loaded !== true ? (window.attachEvent ? window.attachEvent('onload', $MPC_load) : window.addEventListener('load', $MPC_load, false)) : null;
    })();
</script>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 	
	<div id="modal_agendamento" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Agendamento</h4>
	      </div>
	      <div class="modal-body">
	      <input type="hidden" id="operacao_agendamento" value="">
	      	<div class="form-group">
	      	<table>
	      		<tr>
	      			<td style="padding: 10px">Dia Selecionado</td>
	      			<td style="padding: 10px"><input type="text" id="dia_selecionado_agenda" class="form-control" readonly></td>
	      			<td style="padding: 10px"><input type="text" id="horario_selecionado_agenda" class="form-control" placeholder="Horário hh:mm"></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 10px">Profissional</td>
	      			<td style="padding: 10px"><select class="form-control" id="select_equipe_agendamento" onchange="carrega_tabela_eventos(this.value)"><option>carregando</option></select></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 10px">Cliente</td>
	      			<td style="padding: 10px"><input type="text" id="cliente_agendamento" class="form-control" ></td>
	      		</tr>
	      		<tr>
	      			<td style="padding: 10px">Serviço</td>
	      			<td style="padding: 10px"><input type="text" id="cliente_agendamento_servico" class="form-control" ></td>
	      		</tr>
	      	</table>
	      	<table id="tabela_compromissos_profissionais" data-use-row-attr-func="true" data-locale="pt-BR" data-reorderable-rows="true" data-toggle="table" data-click-to-select="true" data-pagination="true" data-page-size="5" data-search="true" data-sort-name="hora" data-sort-order="asc" data-show-columns="true">
	      	<thead><tr>
	      	<th data-field="hora">Horário</th>
	      	<th data-field="cliente">Cliente</th>
	      	</tr>
	      	</thead>
	      	<tbody></tbody>
	      	</table>
	      </div>
	      <div class="modal-footer">
	         <button type="button" class="btn btn-success" onclick="add_evento()">Agendar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	
	  </div>
	</div>
	
	
</div>
<script>
function openCity(evt, cityName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the link that opened the tab
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}
</script>
</body>
</html>