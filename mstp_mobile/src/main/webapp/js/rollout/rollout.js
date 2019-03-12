function atualiza_rollout(recid,milestone,status,siteid){
    $.ajax({
                  type: "POST",
                  data: {"opt":33,
                  "recid":recid,
                  "milestone":milestone,
                  "status":status,
                  "siteid":siteid
                          },		  
                  url: "http://172.20.10.10:8080/mstp_mobile/Op_Servlet",	  
                  //url: "./POControl_Servlet""
                  cache: false,
                  dataType: "text",
                  success: onSuccess_updateRollout
          });
          function onSuccess_updateRollout(data){
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
	                  url: "http://172.20.10.10:8080/mstp_mobile/Op_Servlet",	  
	                  //url: "./POControl_Servlet""
	                  cache: false,
	                  dataType: "text",
	                  success: onSuccess_minhas_atividades
	          });
	          function onSuccess_minhas_atividades(data){
	            $('#lista_atividades').html(data);
	          }
	 }