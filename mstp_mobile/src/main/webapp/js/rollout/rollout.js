function atualiza_rollout(recid,milestone,status,siteid){
	
	//alert("testando dialog 4 ");
	$('#modal_conteudo').html("<ons-progress-circular indeterminate></ons-progress-circular>")
	var modal = document.querySelector('ons-modal');
	modal.show();
		
	
    $.ajax({
                  type: "POST",
                  data: {"opt":33,
                  "recid":recid,
                  "milestone":milestone,
                  "status":status,
                  "siteid":siteid
                          },		  
                  url: "http://172.20.10.3:8080/mstp_mobile/Op_Servlet",	  
                  //url: "./POControl_Servlet""
                  cache: false,
                  dataType: "text",
                  success: onSuccess_updateRollout
          });
          function onSuccess_updateRollout(data){
        	  modal.hide();
            $.alert(data.toString());
            carrega_minhas_atividades();
          }
	
}
function carrega_minhas_atividades(){
	   var timestamp = Date.now();
	            $.ajax({
	                  type: "POST",
	                  data: {"opt":32,
	                  "_":timestamp},		  
	                  url: "http://172.20.10.3:8080/mstp_mobile/Op_Servlet",	  
	                  //url: "./POControl_Servlet""
	                  cache: false,
	                  dataType: "text",
	                  success: onSuccess_minhas_atividades
	          });
	          function onSuccess_minhas_atividades(data){
	            $('#lista_atividades').html(data);
	          }
	 }

function carrega_atividades_quantidade_label(){
	   var timestamp = Date.now();
	       $.ajax({
			  type: "POST",
			  data: {"opt":31,
	             "_":timestamp},		  
			  url: "http://172.20.10.3:8080/mstp_mobile/Op_Servlet",	  
			  //url: "./POControl_Servlet""
			  cache: false,
			  dataType: "text",
			  success: onSuccessSaldoAtividades_label,
	      error: onerror_saldo_atividades
			});
	    function onSuccessSaldoAtividades_label(data){
	      $("#quantidade_atividades_label").html(data);
	    }
	    function onerror_saldo_atividades(data){
	      alert("Parece que estamos com problemas na conexao com a internet.Reinicie o app")
	    }
	}