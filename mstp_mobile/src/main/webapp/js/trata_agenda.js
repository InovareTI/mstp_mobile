function carrega_tabela_eventos(profissional){
	var dia;
	dia=$('#dia_selecionado_agenda').val();
	 $.getJSON('./Op_Servlet?opt=41&pro='+profissional+'&dia='+dia, function(data) {	
		 //alert(data);
			$('#tabela_compromissos_profissionais').bootstrapTable('resetView');
			$('#tabela_compromissos_profissionais').bootstrapTable('load',data);
	 });
 

}
var clinicos="";
function carrega_eventos_clinico_selecionado(){
	var quantidade=document.getElementById('quatidade_chk').value;
	clinicos="";
	for(i=1;i<quantidade;i++){
		if($("#chk_"+i).is(':checked')){
			
		}else{
			clinicos=clinicos+$("#chk_"+i).val()+",";
		}
	}
	if(clinicos.length==0){
		clinicos="*";
	}else{
		clinicos=clinicos.substring(0,clinicos.length-1);
		
	}
	//alert(clinicos);
	$('#calendar').fullCalendar('rerenderEvents');
	//carrega_calendario(clinicos);
}
function carrega_eventos_medico(inicio,fim){
	$.ajax({
		  type: "POST",
		  data: {"opt":"45",
			  "start":moment(inicio).format(),
			  "end":moment(fim).format()},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess45
		});
	function onSuccess45(data)
	{
		
		$("#mostra_eventos").html("<br><br>"+data)
		
	}
}
function carrega_calendario(){
	
		 $('#calendar').fullCalendar( 'destroy' );
		 $('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,basicWeek,basicDay,listWeek'
		},
		locale: 'pt-br',
		navLinks: true,
		
		editable: true,
		eventLimit: true,
		selectable: true,
		selectHelper: true,
		select: function(start, end) {
			//alert(start);
			$('#dia_selecionado_agenda').val(moment(start).format('L'));
			$('#select_equipe_agendamento').val('');
			$('#cliente_agendamento').val('');
			$('#cliente_agendamento_servico').val('');
			$('#operacao_agendamento').val('novo');
			$('#modal_agendamento').modal("toggle");
			
			$('#calendar').fullCalendar('unselect');
		},
		events: './Op_Servlet?opt=42',
		viewRender: function(view,element){
			carrega_eventos_medico(view.start,view.end);
		},
		eventClick: function(calEvent, jsEvent, view) {
			var view = $('#calendar').fullCalendar('getView');
			//alert("The view's title is " + view.title);
			var dialog = bootbox.dialog({
			    title: '<a href="#" data-dismiss="modal" onclick="carrega_dados_cliente('+calEvent.id_cliente+')">'+calEvent.title+'</a>',
			    message: '<p><i class="fa fa-spin fa-spinner"></i> Buscando Informações...</p>'
			});
			dialog.init(function(){
			    setTimeout(function(){
			    	//var dia_aux=String(calEvent.start._d);
			        dialog.find(".bootbox-body").html("<label class=\"control-label\">Serviço:</label><label>"+calEvent.servico+"</label><br><br>"+
			        		"<label class=\"control-label\">horario:</label><label>"+calEvent.hora+"</label><br><br>"+
			        		"<label class=\"control-label\">Clinico:</label><label>"+calEvent.usuario+"</label><br><br>"+
			        		"<a href=\"#\" class=\"btn\" role=\"button\" data-dismiss=\"modal\" onclick=\"edita_agenda('"+calEvent.title+"','"+calEvent.id+"','"+calEvent.usuario+"','"+calEvent.servico+"','"+calEvent.start_aux+"')\">Editar</a>"+
			        		"<a href=\"#\" class=\"btn btn-success\" role=\"button\" data-dismiss=\"modal\" onclick=\"inicia_atendimento('"+calEvent.id_cliente+"','"+calEvent.id+"')\">Iniciar Atendimento</a>"+
			        		"<button class=\"btn btn-danger\" data-dismiss=\"modal\" onclick=\"remove_evento('"+calEvent.id+"')\">Remover</button>");
			    }, 1000);
			});
	        $(this).css('border-color', 'red');

	    },
	    eventRender: function eventRender( event, element, view ) {
	    	//alert(clinicos);
	    	if(clinicos=="" || clinicos=="*"){
	    		return "";
	    	}else{
	    		var aux=clinicos.split(",");
	    		for(var i=0;i<aux.length;i++){
	    			if(event.usuario==aux[i]){
	    				return event.usuario;
	    			}
	    		}
	    	}
	    	//return ['*', event.usuario].indexOf(function(){
	    	//	$.each(clinicos.split(","),function(){
	    	//		return $(this).val();
	    	//	})
	    	//})>=0;
	    	
	    },
	    eventDrop: function(event, delta, revertFunc) {
	    	//console.log(event);
	    	var view = $('#calendar').fullCalendar('getView');
	    	alert("The view's title is " + view.title);
	        alert(event.title + " was dropped on " + event.start.format());

	        if (!confirm("Tem certeza que deseja realizar esse reagendamento?")) {
	            revertFunc();
	        }else{
	        	//alert(event.start.format());
	        	//alert(event.id);
	        	$.ajax({
	      		  type: "POST",
	      		  data: {"opt":"57",
	      			  "dia":event.start.format(),
	      			  "id":event.id},		  
	      		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
	      		  url: "./Op_Servlet",
	      		  cache: false,
	      		  dataType: "text",
	      		  
	      		});
	      	
	        }
	        event.start_aux=event.start.format('L');
	        $('#calendar').fullCalendar('updateEvent', event);
	    }
		});
	//$('#calendar').fullCalendar( 'refetchEvents' );
	$('#calendar').fullCalendar('render');
	//console.log(data);
	 
	 
}
function edita_agenda(cliente,agenda,usuario,servico,dia){
	//alert(usuario);
	//alert(dia);
	$('#select_equipe_agendamento').val(usuario);
	$('#dia_selecionado_agenda').val(dia);
	$('#cliente_agendamento').val(cliente);
	$('#cliente_agendamento_servico').val(servico);
	$('#operacao_agendamento').val('atualizacao_'+agenda);
	
	$('#modal_agendamento').modal("toggle");
	carrega_tabela_eventos(usuario);
	
}
function remove_evento(id_evento){
	$.ajax({
		  type: "POST",
		  data: {"opt":"43",
			  "id_evento":id_evento},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess43
		});
	function onSuccess43(data)
	{
		$('#calendar').fullCalendar( 'removeEvents',id_evento );
		bootbox.alert(data);
	}
}
function add_evento(){
	
	var dia,hora,pro,cliente,servico,id_cliente,operacao;
	operacao=$('#operacao_agendamento').val();
	dia=$('#dia_selecionado_agenda').val();
	hora=$('#horario_selecionado_agenda').val();
	pro=$('#select_equipe_agendamento').val();
	cliente=$('#cliente_agendamento').val();
	if($("#cliente_agendamento").typeahead("getActive").name==$("#cliente_agendamento").val()){
		//console.log($("#cliente_agendamento").typeahead("getActive"));
		cliente=$("#cliente_agendamento").typeahead("getActive").name;
		id_cliente=$("#cliente_agendamento").typeahead("getActive").id;
	}else{
		cliente=$("#cliente_agendamento").val();
		id_cliente='X';
	}
	if($("#cliente_agendamento_servico").typeahead("getActive").name==$("#cliente_agendamento_servico").val()){
		servico=$("#cliente_agendamento_servico").typeahead("getActive").name;
		
	}else{
		servico=$("#cliente_agendamento_servico").val();
	}
	if(hora==""){
		bootbox.alert("Favor informar a hora");
		return;
	}
	hora_aux=hora.split(":");
	if(hora_aux[0]>23 || hora_aux[0]<0){
		bootbox.alert("Horário Inválido");
		return;
	}
	if(hora_aux.length>1){
	if(hora_aux[1]>59 || hora_aux[1]<0){
		bootbox.alert("Horário Inválido");
		return;
	}}
	if(cliente==""){
		bootbox.alert("Favor informar o Cliente");
		return;
	}
	if(pro==""){
		bootbox.alert("Favor informar o Profissional");
		return;
	}
	$.ajax({
		  type: "POST",
		  data: {"opt":"44",
			  "dia":dia,
			  "hora":hora,
			  "pro":pro,
			  "cliente":cliente,
			  "id_cliente":id_cliente,
			  "servico":servico,
			  "operacao":operacao},		  
		  //url: "http://localhost:8080/odontoFlow/Op_Servlet",	  
		  url: "./Op_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess44
		});
	function onSuccess44(data)
	{
		dia_aux=dia;
		dia=moment(dia, 'DD/MM/YYYY', true).format();
	 var eventData = {
				title: cliente,
				start: dia.substring(0, 10)+"T"+hora.trim(),
				start_aux:dia_aux,
				id:data,
				servico:servico,
				hora:hora,
				usuario:pro
			};
	    $('#modal_agendamento').modal("toggle");
	    if(operacao=="Novo"){
	    	$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
	    }else{
	    	//alert(operacao.substring(operacao.indexOf("_")+1));
	    	
	    	$('#calendar').fullCalendar( 'removeEvents',operacao.substring(operacao.indexOf("_")+1) );
	    	
	    	$('#calendar').fullCalendar('renderEvent', eventData, true);
	    	 
	    }
	    carrega_clientes_atendimento();
		bootbox.alert("Agendamento efetuado com sucesso:"+data);
	}
}