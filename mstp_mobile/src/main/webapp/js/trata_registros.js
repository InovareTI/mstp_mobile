function carrega_tabela_registro_servico(){
	$.getJSON('./Op_Servlet?opt=58&ids='+$("#select_subtiposervico").multiselect().val(), function(data) {
		//alert(data);
		$("#jsGrid").jsGrid("option", "data",data);
		$("#jsGrid").jsGrid("refresh");
	 });
	}
function add_tabela_auxiliar(){
	var status_evento;
	var pagamento_tipo=[
		{id:1,tipo:"dinheiro",Name:"Dinheiro"},
		{id:2,tipo:"debito",Name:"Débito"},
		{id:3,tipo:"credito",Name:"Crédito"},
		{id:4,tipo:"plano",Name:"Plano"}
	];
	var status_reg=[
		{id:1,tipo:"recebido",Name:"Recebido"},
		{id:2,tipo:"a_receber",Name:"À Receber"},
		
	];
		
		 //alert(data);
			
		//	$('#tabela_registro_auxiliar').bootstrapTable('load',data);
		//	$('#tabela_registro_auxiliar').bootstrapTable('refresh');
	
		status_evento=0;
		jsGrid.locale("pt-br");
		
		function FloatNumberField(config) {
	        jsGrid.NumberField.call(this, config);
	    }

	    FloatNumberField.prototype = new jsGrid.NumberField({

	        filterValue: function() {
	            return parseFloat(this.filterControl.val());
	        },

	        insertValue: function() {
	            return parseFloat(this.insertControl.val());
	        },

	        editValue: function() {
	            return parseFloat(this.editControl.val());
	        },
	        itemTemplate: function(value) {
	        	
	            return parseFloat(value).toFixed(2);
	        }
	    });

	    jsGrid.fields.floatNumber = FloatNumberField;
    $("#jsGrid").jsGrid({
        width: "100%",
       
        editing: true,
        data: [],
        onItemUpdated: function(args) {
        	//alert("atuaizando");
        	$("#jsGrid").unbind("onItemUpdated");
        	args.item.vf_registro_aux=parseFloat(((100.00-args.item.desconto_registro_aux)/100)*(args.item.valor_registro_aux));
        	$("#jsGrid").jsGrid("editItem",args.item);
        	
        	$("#jsGrid").jsGrid("refresh");
        	$("#jsGrid").bind("onItemUpdated");
        	ajusta_valor_final();
        },
        onItemDeleted: function(args) {
        	ajusta_valor_final();
        },
        fields: [
        	{ type: "control" },
            { name: "servico_registro_aux", title: "Serviço",type: "text", width: 140 },
            { name: "valor_registro_aux", title: "Valor",type: "floatNumber", width: 50 },
            { name: "desconto_registro_aux", title: "Desconto %",type: "floatNumber", width: 60},
            { name: "vf_registro_aux", title: "Valor Final",type: "floatNumber", width: 50},
            { name: "status_reg", title: "Status",type: "select",items:status_reg, valueField: "tipo", textField: "Name", width: 50,validate: "required" },
          	{ name: "obs_reg", title: "Observações",type: "text", width: 80}
            
        ]
    });
	
	
	
}
function add_tabela_auxiliar2(){
	var status_evento;
	status_evento=0;
	var pagamento_tipo=[
		{id:1,tipo:"dinheiro",Name:"Dinheiro"},
		{id:2,tipo:"debito",Name:"Débito"},
		{id:3,tipo:"credito",Name:"Crédito"},
		{id:4,tipo:"plano",Name:"Plano"}
	];
	var status_reg=[
		{id:1,tipo:"pago",Name:"Recebido"},
		{id:2,tipo:"a_receber",Name:"À Receber"},
		
	];
	
		jsGrid.locale("pt-br");
    $("#jsGrid2").jsGrid({
        width: "100%",
        onItemInserting: function(args) {
        	//alert("entrou no inserting evento");
        	
        	if(status_evento==1){
        		return;
        	}else{
        		if(args.item.pagamento_tipo=="credito"){
        			valor=args.item.valor_reg/args.item.parcelas_reg;
        			valor=valor.toFixed(2);
        			for(var i=0;i<args.item.parcelas_reg-1;i++){
        				//alert("entrou no for de insert");
        				status_evento=1;
        				$("#jsGrid2").jsGrid("insertItem", { pagamento_tipo: args.item.pagamento_tipo, status_reg: args.item.status_reg,valor_reg:valor , parcelas_reg:i+1 })
        				
        			}
        			status_evento=0;
        			args.item.valor_reg=valor;
        		}
        	}
        },
        editing: true,
        data: [],
        inserting: true,
        fields: [
        	{ type: "control" },
            { name: "pagamento_tipo", title: "Pagamento",type: "select",items:pagamento_tipo,valueField: "tipo", textField: "Name",  width: 50,validate: "required" },
            { name: "status_reg", title: "Status",type: "select",items:status_reg, valueField: "tipo", textField: "Name", width: 50,validate: "required" },
            { name: "valor_reg", title: "Valor",type: "floatNumber", width: 50,validate: "required" },
            { name: "parcelas_reg", title: "Parcelas",type: "number", width: 50,validate: "required" },
            { name: "obs_reg", title: "Observações",type: "text", width: 80}
            
        ]
    
	 });

	
}
function add_tabela_auxiliar3(){
	
	var status_evento;
	status_evento=0;
	moment.locale('pt-BR');
	var status_saida=[
		{id:1,tipo:"pago",Name:"Pago"},
		{id:2,tipo:"a_pagar",Name:"À Pagar"},
		
	];
	
		jsGrid.locale("pt-br");
		var MyDateField = function(config) {
	        jsGrid.Field.call(this, config);
	    };
	    var Categoria_campo = function(config) {
	        jsGrid.Field.call(this, config);
	    };
	    var SubCategoria_campo = function(config) {
	        jsGrid.Field.call(this, config);
	    };
	    MyDateField.prototype = new jsGrid.Field({
	        sorter: function(date1, date2) {
	            return new Date(date1) - new Date(date2);
	        },
	        itemTemplate: function(value) {
	        	return value;
	        	
	        },
	        insertTemplate: function(value) {
	        	//alert("insertTemplate:"+value);
	            return this._insertPicker = $("<input>").datepicker({
	            	defaultDate: new Date(),
	                format: "dd/mm/yyyy",
	                language: "pt-BR",
	                autoclose: true,
	                todayHighlight: true,
	                daysOfWeekHighlighted: "0,6",
	                calendarWeeks: true
	            }).datepicker("setDate", moment(new Date(value)).format('L'));
	        },
	        editTemplate: function(value) {
	        	//alert("edit template:"+value);
	            return this._editPicker = $("<input>").datepicker({
	            	defaultDate: value,
	             format: "dd/mm/yyyy",
	                language: "pt-BR",
	                autoclose: true,
	                todayHighlight: true,
	                daysOfWeekHighlighted: "0,6",
	                calendarWeeks: true
	            });
	        },
	        insertValue: function() {
	        	//console.log(this);
	        	 return moment(this._insertPicker.datepicker("getDate")).format('L');
	        	
	        },
	        editValue: function() {
	        	
	        	return moment(this._editPicker.datepicker("getDate")).format('L');
	        }
	    });
	    Categoria_campo.prototype = new jsGrid.Field({
	        
	        itemTemplate: function(value) {
	        	
	        	return value;
	        },
	        insertTemplate: function(value) {
	        	carrega_select_despesa_categoria();
	            return $("<select id='select_categoria_despesa_template' class='form-control registro' onchange='carrega_dados_categoria(this,0)'>"+categorias_despesas+"</select>");
	            
	        },
	        editTemplate: function(value) {
	        	return $("<select id='select_categoria_despesa_item' class='form-control registro' onchange='carrega_dados_categoria(this,0)'>"+categorias_despesas+"</select>");
	            
	        },
	        insertValue: function() {
	        	return $('#select_categoria_despesa_template').val();;
	        },
	        editValue: function() {
	        	
	        	return  $('#select_categoria_despesa_item').val();
	        	
	        }
	    });
	    	SubCategoria_campo.prototype = new jsGrid.Field({
	        
	        itemTemplate: function(value) {
	        	
	        	return value;
	        },
	        insertTemplate: function(value) {
	        	
	            return $("<select id='select_subcategoria_despesa_template' class='form-control registro'></select>");
	            
	        },
	        editTemplate: function(value) {
	        	return $("<select id='select_subcategoria_despesa_item' class='form-control registro'></select>");
	            
	        },
	        insertValue: function() {
	        	return $('#select_subcategoria_despesa_template').val();;
	        },
	        editValue: function() {
	        	
	        	return  $('#select_subcategoria_despesa_item').val();
	        	
	        }
	    });
	    jsGrid.fields.date = MyDateField;
	    jsGrid.fields.categoria = Categoria_campo;
	    jsGrid.fields.subcategoria = SubCategoria_campo;
    $("#jsGrid3").jsGrid({
        width: "100%",
        editing: true,
        data: [],
        inserting: true,
        fields: [
        	{ type: "control" },
            { name: "categoria_saida", title: "Categoria",type: "categoria",  width: 50,validate: "required"},
            { name: "sub_categoria_saida", title: "SubCategoria",type: "subcategoria", width: 50,validate: "required" },
            { name: "hoje_saida", title: "Data",type: "date", width: 50,validate: "required" },
            { name: "valor_saida", title: "Valor",type: "number", width: 50,validate: "required" },
            { name: "status_saida", title: "Status",type: "select",items:status_saida,valueField: "tipo",textField: "Name",validate: "required", width: 80},
            { name: "descricao_saida", title: "Descrição",type: "text",validate: "required", width: 80}
            
        ]
    
	 });
   
}
function ajusta_valor_final(){
	
	var dados=$("#jsGrid").jsGrid("option", "data");
	var contador=0;
	//console.log(dados);
	for(var i=0;i<dados.length;i++){
		contador=contador+parseFloat(dados[i].vf_registro_aux);
	}
	 document.getElementById("valor_preco").value=accounting.formatMoney(contador);
}

  function sumFormatter(data) {
    var field = this.field;

    var total_sum = data.reduce(function(sum, row) {
            console.log(sum);
        return (sum) + (parseInt(row[field]) || 0);
    }, 0);
    return total_sum;
}
  function carrega_controle_registros(){
	  var $table = $('#tabela_registro');
	  var $table2 = $('#tabela_fluxo_pg');
			$button = $('#cancelar_registro');
		    $(function () {
		        $button.click(function () {
		        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
		                return row.id_registro;
		            });
		            $table.bootstrapTable('remove', {
		                field: 'id_registro',
		                values: ids
		            });
		            //alert(ids);
		            cancela_registros(String(ids));
		        });
		    });
		    $button2 = $('#btn_editar_registro');
			$(function () {
		        $button2.click(function () {
		        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
		                return row.id_registro;
		            });
		        	if(String(ids)==""){
		        		bootbox.alert("Favor selecionar um registro da tabela de registros");
		        		return;
		        		}else{
		            edita_registro(String(ids));}
		        });
		    });
			$button3 = $('#btn_efetivar_flow');
		    $(function () {
		        $button3.click(function () {
		        	var ids = $.map($table2.bootstrapTable('getSelections'), function (row) {
		                return row.id_fuxo_sys;
		            });
		            
		            //alert(ids);
		            efetiva_pagamentos(String(ids));
		        });
		    });
			//alert("funçoes tabela de registro com sucesso");
			add_tabela_auxiliar();
			add_tabela_auxiliar2();
			add_tabela_auxiliar3();
			$('#modal_add_registro').on('hidden.bs.modal', function (e) {
				 $("#jsGrid").jsGrid("destroy");
				 $("#jsGrid2").jsGrid("destroy");
				 $("#jsGrid3").jsGrid("destroy");
				 add_tabela_auxiliar();
				 add_tabela_auxiliar2();
				 add_tabela_auxiliar3();
				 $('#btn_add_registro').removeClass( "disabled" );
				 $('#btn_add_registro').text('Registrar');
				 $('.registro').val('');
				 //$("#select_subtiposervico").multiselect().val("");
				 $('option', $('#select_subtiposervico')).each(function(element) {
		                $(this).removeAttr('selected').prop('selected', false);
				});
				 $('option', $('#select_servico')).each(function(element) {
		                $(this).removeAttr('selected').prop('selected', false);
				});
				 $('#select_subtiposervico').multiselect('refresh');
				 $('#select_servico').multiselect('refresh');
				 document.getElementById("valor_preco").value="";
				 document.getElementById("servico_profissional").value="";
				 document.getElementById("servico_clinica").value="";
				});
  }
  function cancela_registros(id){
		//alert(po_number);
		$.ajax({
			  type: "POST",
			  data: {"opt":"20",
				     "ids":id},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess20
			});
		function onSuccess20(data)
		{
			
			//carrega_ITEM();
			bootbox.alert("Registros Cancelados");
		}

	}
  function efetiva_pagamentos(id){
		//alert(po_number);
		$.ajax({
			  type: "POST",
			  data: {"opt":"61",
				     "ids":id},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess61
			});
		function onSuccess61(data)
		{
			
			//carrega_ITEM();
			bootbox.alert("Pagamentos Efetivados com Sucesso");
		}

	}
  function add_registro(){
	  var servico,subtipo,data,profissional_nome,percentual_pro,percentual_cli,cpf,clinica,valor,tipo_registro,nme_cliente,forma_pg;
	  var categoria,subcategoria,data,desc,clinica,valor_despesa,despesa_status,desc;
	  var $table = $('#tabela_registro');
	  var dados_geral={
			  categoria:'',
			  subcategoria:'',
			  data_exec:'',
			  tipo_registro:'',
			  update_registro:'',
			  pro:'',
			  cli:'',
			  percent_pro:'',
			  percent_cli:'',
			  nome_cliente:'',
			  valor:'',
			  cpf:'',
			  id_servico:'',
			  tabela1:[],
			  tabela2:[],
			  tabela3:[]
	  };
	  
	  
	  if($table.bootstrapTable('getSelections').length>0){
	    	
	    	update_registro_id=$table.bootstrapTable('getSelections');
	    	update_registro_id=update_registro_id[0]['id_registro'];
	    	dados_geral.update_registro=update_registro_id;
	    }else{
	    	update_registro_id="NO_UPDATE";
	    	dados_geral.update_registro=update_registro_id;
	    }
	  if($("#radio_entrada").is(":checked")){
	  	tipo_registro="Entrada";
	  	dados_geral.tipo_registro=tipo_registro;
	  	$("#nome_cliente").typeahead("lookup");
		
		if($("#nome_cliente").typeahead("getActive").name==$("#nome_cliente").val()){
			nme_cliente=$("#nome_cliente").typeahead("getActive").name;
			dados_geral.nome_cliente=nme_cliente;
		}else{
			nme_cliente=$("#nome_cliente").val();
			dados_geral.nome_cliente=nme_cliente;
		}
		subtipo= String($("#select_subtiposervico").multiselect().val());
		dados_geral.id_servico=subtipo;
		
		data= $("#hoje_entrada").val();
		dados_geral.data_exec=data;
		
		profissional_nome= $('#select_equipe').val();
		dados_geral.pro=profissional_nome;
		percentual_pro=$("#servico_profissional").val();
		dados_geral.percent_pro=percentual_pro;
		percentual_cli=$("#servico_clinica").val();
		dados_geral.percent_cli=percentual_cli;
		cpf= $("#cpf_cliente").val();
		dados_geral.cpf=cpf;
		clinica=$("#select_clinica_entrada").val();
		dados_geral.cli=clinica;
		
		valor= $("#valor_preco").val();
		dados_geral.valor=valor;
		categoria=$("#select_categoria_registro_entrada").val();
		
		subcategoria=$("#select_subcategoria_registro_entrada").val();
		dados_geral.subcategoria=subcategoria;
		dados_geral.categoria=categoria;
	  }else{
		  tipo_registro="Saida";
		  dados_geral.tipo_registro=tipo_registro;
	  }
	  	
		dados_geral.tabela1=$("#jsGrid").jsGrid("option", "data");
		dados_geral.tabela2=$("#jsGrid2").jsGrid("option", "data");
		dados_geral.tabela3=$("#jsGrid3").jsGrid("option", "data");
		//console.log(dados_geral.tabela3);
		if(dados_geral.tipo_registro=="Entrada"){
			soma=0.00;
			for(i=0;i<dados_geral.tabela2.length;i++){
				soma=soma+parseFloat(dados_geral.tabela2[i].valor_reg);
			}
			soma=soma.toFixed(2);
			valor_aux=dados_geral.valor;
			valor_aux=valor_aux.replace("R$","");
			valor_aux=valor_aux.replace(".","");
			valor_aux=valor_aux.replace(",",".");
			if(parseFloat(soma)!=parseFloat(valor_aux)){
				bootbox.alert("Total do fluxo de pagamento não confere com valor total do registro:" + soma +" difere de "+valor_aux);
				return;
			}
		}
		$('#btn_add_registro').addClass( "disabled" );
		$('#btn_add_registro').text('Aguarde a Finalização...');
		$.ajax({
			  type: "POST",
			  data: {"opt":"12",
				"tipo_reg":tipo_registro,
				"servico":servico, 
				"subtipo":subtipo, 
				"percentual_pro":percentual_pro,
				"percentual_cli":percentual_cli,
				"cpf":cpf,
				"nme_cliente":nme_cliente,
				"clinica_id":clinica,
				"data":data,
				"valor":valor,
				"categoria":categoria,
				"subcategoria":subcategoria,
				"update_reg":update_registro_id,
				"profissional":profissional_nome,
				"tabela1":JSON.stringify(dados_geral)
				//"tabela2":JSON.stringify(JSON.parse(dados2,'[]'))
				},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./Op_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess12
			});
	  
		
		function onSuccess12(data)
		{
			if(update_registro_id=="NO_UPDATE"){
			aux=JSON.parse(data);
			bootbox.alert("Registro Efetuado.");
			$('#tabela_registro').bootstrapTable('prepend', aux);
			$('#tabela_registro').bootstrapTable('scrollTo', 'bottom');
			$('#btn_add_registro').removeClass( "disabled" );
			$('#btn_add_registro').text('Registrar');
			$('.registro').val('');
			$('#modal_add_registro').modal("toggle");
			}else{
				bootbox.alert(data);
				$('#btn_add_registro').removeClass( "disabled" );
				$('#btn_add_registro').text('Registrar');
				$('.registro').val('');
				$('#modal_add_registro').modal("toggle");
			}
		}
	  
	  
  }
  