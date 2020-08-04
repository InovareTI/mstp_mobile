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
                  url: "https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
                  //url: "./POControl_Servlet""
                  cache: false,
                  dataType: "text",
                  success: onSuccess_updateRollout
          });
          function onSuccess_updateRollout(data){
        	  modal.hide();
            $.alert(data.toString());
            carrega_minhas_atividades();
            carrega_atividades_quantidade_label_fechadas();
            carrega_minhas_atividades_fechadas();
          }
	
}
function carrega_minhas_atividades(){
	   var timestamp = Date.now();
	            $.ajax({
	                  type: "POST",
	                  data: {"opt":32,
	                  "_":timestamp},		  
	                  url: "https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
	                  //url: "./POControl_Servlet""
	                  cache: false,
	                  dataType: "text",
	                  success: onSuccess_minhas_atividades
	          });
	          function onSuccess_minhas_atividades(data){
	            $('#lista_atividades').html(data);
	          }
	 }
function carrega_minhas_atividades_fechadas(){
	   var timestamp = Date.now();
	            $.ajax({
	                  type: "POST",
	                  data: {"opt":62,
	                  "_":timestamp},		  
	                  url: "https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
	                  //url: "./POControl_Servlet""
	                  cache: false,
	                  dataType: "text",
	                  success: onSuccess_minhas_atividades_fechdas
	          });
	          function onSuccess_minhas_atividades_fechdas(data){
	            $('#lista_atividades_fechadas').html(data);
	          }
	 }

function carrega_atividades_quantidade_label_fechadas(){
	 
	   var timestamp = Date.now();
	       $.ajax({
			  type: "POST",
			  data: {"opt":61,
	             "_":timestamp},		  
			  url: "https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
			  //url: "./POControl_Servlet""
			  cache: false,
			  dataType: "text",
			  success: onSuccessSaldoAtividades_label_fechadas,
	      error: onerror_saldo_atividades_fechadas
			});
	    function onSuccessSaldoAtividades_label_fechadas(data){
	    	document.getElementById('ons-tab1').setAttribute('badge', data);
	    	document.getElementById('ons-tab0').setAttribute('badge', sessionStorage.getItem("label_atividades"));
	      //animateCSS('quantidade_atividades_label', 'tada');
	    }
	    function onerror_saldo_atividades_fechadas(data){
	      alert("Parece que estamos com problemas na conexao com a internet.Reinicie o app");
	    }
	}
function carrega_atividades_quantidade_label(){
	 
	   var timestamp = Date.now();
	       $.ajax({
			  type: "POST",
			  data: {"opt":31,
	             "_":timestamp},		  
			  url: "https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
			  //url: "./POControl_Servlet""
			  cache: false,
			  dataType: "text",
			  success: onSuccessSaldoAtividades_label,
	      error: onerror_saldo_atividades
			});
	    function onSuccessSaldoAtividades_label(data){
	      $("#quantidade_atividades_label").html(data);
	      sessionStorage.setItem("label_atividades",data);
	      //animateCSS('quantidade_atividades_label', 'tada');
	    }
	    function onerror_saldo_atividades(data){
	      alert("Parece que estamos com problemas na conexao com a internet.Reinicie o app");
	    }
	}