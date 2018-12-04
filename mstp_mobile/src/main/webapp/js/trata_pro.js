function carrega_contraCheque(pro){
	
	//alert('entrou no contra-cheque');
	//console.log(pro);
	document.getElementById("registros").style.display="none";
	document.getElementById("controles").style.display="block";
	document.getElementById("div_exibe_controles").style.display="none";
	document.getElementById("div_carrega_contracheque").style.display="block";
	document.getElementById("pro_id_carregado").value=pro;
	$.getJSON('./Op_Servlet?opt=60&pro='+pro, function(data) {	
		console.log(data)
		$('#tabela_dados_pro_receitas').bootstrapTable('removeAll');
		$('#tabela_dados_pro_receitas').bootstrapTable('load',data);
		$('#tabela_dados_pro_receitas').bootstrapTable('refresh');
	});
	
}