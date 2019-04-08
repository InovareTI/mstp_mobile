function carrega_justificativas(){
 alert("chamando justificativas 3");
	$.ajax({
        type: "POST",
        data: {"opt":41
        },		  
        url: "http://192.168.0.31:8080/mstp_mobile/Op_Servlet",	  
        //url: "./Op_Servlet""
        cache: false,
        dataType: "text",
        success: carregando_justificativas
});
	function carregando_justificativas(data){
		$("#div_motivo_ajuste").html(data)
	}
}