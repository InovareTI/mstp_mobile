function g1 (opcao,local) {
				
			
			 moment().locale('pt-br');
			 moment().format('L');
			  var s= document.getElementById('from');
			  var p= document.getElementById('to');	
			  
			if (opcao==0){
			     var date = new Date();
			     var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
			     var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
			     
			     document.getElementById('to').value=lastDay.toLocaleDateString();
			     document.getElementById('from').value=firstDay.toLocaleDateString();
			     
			    
			     s.value=firstDay.toLocaleDateString();
			     p.value=lastDay.toLocaleDateString();
			     
			}
				
			  $.getJSON('./Op_Servlet?opt=7&dtfrom='+s.value+'&dtto='+p.value, function(data) {	
			    	
			    	$('#'+local).highcharts({
			            chart: {
			                type: 'column'
			            },
			            title: {
			                text: 'Volume Financeiro por Serviço'
			            },
			            subtitle: {
			                text: 'Clque nas barras ver mais detalhes'
			            },
			            xAxis: {
			                type: 'category'
			            },
			            yAxis: {
			                title: {
			                    text: 'Volume Financeiro'
			                }

			            },
			            legend: {	
			                enabled: false
			            },
			            plotOptions: {
			                series: {
			                    borderWidth: 0,
			                    dataLabels: {
			                        enabled: true,
			                        formatter: function () {
				                    	//alert(this.y);
				                        return  accounting.formatMoney(this.y);
				                    }
			                    }
			                }
			            },

			            tooltip: {
			                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
			                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b><br/>'
			            },

			            series: data[0]['data'],
			            drilldown: {
			            	drillUpButton: {
			                    relativeTo: 'spacingBox',
			                    position: {
			                        y: 0,
			                        x: 0
			                    },
			                    theme: {
			                        fill: 'white',
			                        'stroke-width': 1,
			                        stroke: 'silver',
			                        r: 0,
			                        states: {
			                            hover: {
			                                fill: '#bada55'
			                            },
			                            select: {
			                                stroke: '#039',
			                                fill: '#bada55'
			                            }
			                        }
			                    }

			                },
			                series: data[1]['data']
			            }
			        });
			    });

			}
function atualiza_ano_fluxo(operacao){
	
	var ano = parseInt(document.getElementById("ano_fluxo").value);
	if(operacao==1){
		ano=ano+1;
		document.getElementById("ano_fluxo").value=ano;
		
	}else{
		ano=ano-1;
		
		document.getElementById("ano_fluxo").value=ano;
	}
	
	g2(ano,'grafico_registros');
}
function g2(opcao,local) {
	//alert("entrei no g2");
	if(opcao==""){
		var d = new Date();
		opcao = d.getFullYear();
		document.getElementById("ano_fluxo").value=opcao;
	}else{
		opcao=document.getElementById("ano_fluxo").value;
	}
	
var options = {
        chart: {
            renderTo: local,
            type: 'line'
        },
        title: {
            text: "<button type='button' class='btn' onclick='atualiza_ano_fluxo(0)'><</button><label id='ano_fluxo'>"+opcao+"</label><button type='button' class='btn' onclick='atualiza_ano_fluxo(1)'>></button>",
            x: -20, //center
            useHTML: true
        },
        xAxis:
        {
        	categories: [],
        	labels:{rotation: 45}
        },
        plotOptions: {
        	
            series: {
                dataLabels: {
                    enabled: true,
                    borderRadius: 5,
                    backgroundColor: 'rgba(252, 255, 197, 0.7)',
                    borderWidth: 1,
                    borderColor: '#AAA',
                    y: -6,
                    formatter: function () {
                    	//alert(this.y);
                        return  accounting.formatMoney(this.y);
                    }
                },
                tooltip: {
		        	pointFormatter: function(){ return accounting.formatMoney(this.y);}
                    
                }
                
            }
            
        },
        series: [{}]
    };
	//var e = document.getElementById("year");
	//var strUser = e.options[e.selectedIndex].value;
	//alert(strUser);
    //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=2&ano='+strUser, function(data) {
    $.getJSON('./Op_Servlet?opt=13&ano='+opcao, function(data) {	
    	options.xAxis.categories = data[0]['data'];
    	
    	for(var d=0;d<data.length-1;d++){
    	options.series[d] = data[d+1];
    	}
    	//options.series[1] = data[2];
    	//options.series[2] = data[3];
    	//options.series[3] = data[4];
    	
    	
       
        var chart2 = new Highcharts.Chart(options);
    });

}
function g3 (opcao,local) {
	
	//alert(local);
	moment().locale('pt-br');
	
	  var s= document.getElementById('from');
	  var p= document.getElementById('to');	
	  
	if (opcao==0){
	     var date = new Date();
	     var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	     var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	     
	     document.getElementById('from').value=moment(firstDay).format('L');
	     document.getElementById('to').value=moment(lastDay).format('L');
	
	     s.value=moment(firstDay).format('L');
	     p.value=moment(lastDay).format('L');
	}
		
	  $.getJSON('./Op_Servlet?opt=9&dtfrom='+s.value+'&dtto='+p.value, function(data) {	
		  //alert(local);
	    	$('#'+local).highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Volume Financeiro por Usuário'
	            },
	            subtitle: {
	                text: 'Clque nas barras ver mais detalhes'
	            },
	            xAxis: {
	                type: 'category'
	            },
	            yAxis: {
	                title: {
	                    text: 'Volume Financeiro'
	                }

	            },
	            legend: {	
	                enabled: false
	            },
	            plotOptions: {
	                series: {
	                    borderWidth: 0,
	                    dataLabels: {
	                        enabled: true,
	                        formatter: function () {
		                    	//alert(this.y);
		                        return  accounting.formatMoney(this.y);
		                    }
	                    }
	                }
	            },

	            tooltip: {
	                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b><br/>'
	            },

	            series: data[0]['data'],
	            drilldown: {
	            	drillUpButton: {
	                    relativeTo: 'spacingBox',
	                    position: {
	                        y: 0,
	                        x: 0
	                    },
	                    theme: {
	                        fill: 'white',
	                        'stroke-width': 1,
	                        stroke: 'silver',
	                        r: 0,
	                        states: {
	                            hover: {
	                                fill: '#bada55'
	                            },
	                            select: {
	                                stroke: '#039',
	                                fill: '#bada55'
	                            }
	                        }
	                    }

	                },
	                series: data[1]['data']
	            }
	        });
	    });

	}
function g4(opcao,local){
	
	$("#grafico_registros").html("<div id=\"grafico_registros_sub1\" style=\"min-width: 30%; height: 380px;float:left; margin: 0 auto\"></div><div id=\"grafico_registros_sub2\" style=\"min-width: 70%; height: 380px; float:left;margin: 0 auto\"></div>");
	if(opcao=='Entrada'){
		g5(1,'grafico_registros_sub1');
	}else if(opcao=='Saida'){
		g7(1,'grafico_registros_sub1');
	}
	
}
function g5(opcao,local){
	 var s= document.getElementById('from');
	 var p= document.getElementById('to');	
	var options = {
	        chart: {
	            renderTo: local,
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false

	        },
	        title: {
	            text: 'Categorias de Entrada',
	            x: -20 //center
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    
	                },
                    showInLegend: true
	            }
	        },
	        series: [{
	        	type: 'pie',
				name:'Categorias',		
	            data:[],
	            events: {
                    click: function (event) {
                    	
                    	//g6(this.data[this.index].name,'grafico_registros_sub2');
                    }
                },
                point: {
                    events: {
                        click: function () {
                     	   //console.log(this);
                            //alert('Category: ' + this.category + ', value: ' + this.options.name);
                            g6(this.options.name,'grafico_registros_sub2');
                        }
                    }
                }
	        	}]
	    };

	        //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=3', function(data) {
	    	$.getJSON('./Op_Servlet?opt=37&dtfrom='+s.value+'&dtto='+p.value, function(data) {
	    	 //alert(data);
	    		options.series[0].data = data;
	    	 var chart3 = new Highcharts.Chart(options);
	    	 
	    });

}
function g6 (opcao,local) {
	
	
	moment().locale('pt-br');
	
	  var s= document.getElementById('from');
	  var p= document.getElementById('to');	
	  
	if (opcao==0){
	     var date = new Date();
	     var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	     var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	     
	     document.getElementById('from').value=moment(firstDay).format('L');
	     document.getElementById('to').value=moment(lastDay).format('L');
	
	     s.value=moment(firstDay).format('L');
	     p.value=moment(lastDay).format('L');
	}
		
	  $.getJSON('./Op_Servlet?opt=38&categoria='+opcao+'&dtfrom='+s.value+'&dtto='+p.value, function(data) {	
	    	
	    	$('#'+local).highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Volume Financeiro por SubCategoria'
	            },
	            subtitle: {
	                text: 'Clque nas barras ver mais detalhes'
	            },
	            xAxis: {
	                type: 'category'
	            },
	            yAxis: {
	                title: {
	                    text: 'Volume Financeiro'
	                }

	            },
	            legend: {	
	                enabled: false
	            },
	            plotOptions: {
	                series: {
	                    borderWidth: 0,
	                    dataLabels: {
	                        enabled: true,
	                        formatter: function () {
		                    	//alert(this.y);
		                        return  accounting.formatMoney(this.y);
		                    }
	                    }
	                }
	            },

	            tooltip: {
	                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b><br/>'
	            },

	            series: data[0]['data'],
	            drilldown: {
	            	drillUpButton: {
	                    relativeTo: 'spacingBox',
	                    position: {
	                        y: 0,
	                        x: 0
	                    },
	                    theme: {
	                        fill: 'white',
	                        'stroke-width': 1,
	                        stroke: 'silver',
	                        r: 0,
	                        states: {
	                            hover: {
	                                fill: '#bada55'
	                            },
	                            select: {
	                                stroke: '#039',
	                                fill: '#bada55'
	                            }
	                        }
	                    }

	                },
	                series: data[1]['data']
	            }
	        });
	    });

	}
function g7(opcao,local){
	 var s= document.getElementById('from');
	 var p= document.getElementById('to');	
	var options = {
	        chart: {
	            renderTo: local,
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false

	        },
	        title: {
	            text: 'Categorias de Saída',
	            x: -20 //center
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    
	                },
                   showInLegend: true
	            }
	        },
	        series: [{
	        	type: 'pie',
				name:'Categorias',		
	            data:[],
	            events: {
                   click: function (event) {
                	  // alert(this.getX());
                   	//console.log(this);
                   	//g8(this.data[this.getX()].name,'grafico_registros_sub2');
                   }
               },
               point: {
                   events: {
                       click: function () {
                    	   //console.log(this);
                           //alert('Category: ' + this.category + ', value: ' + this.options.name);
                           g8(this.options.name,'grafico_registros_sub2');
                       }
                   }
               }
	        	}]
	    };

	        //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=3', function(data) {
	    	$.getJSON('./Op_Servlet?opt=39&dtfrom='+s.value+'&dtto='+p.value, function(data) {
	    	 //alert(data);
	    		options.series[0].data = data;
	    	 var chart3 = new Highcharts.Chart(options);
	    	 
	    });

}
function g8 (opcao,local) {
	
	//alert(opcao);
	moment().locale('pt-br');
	
	  var s= document.getElementById('from');
	  var p= document.getElementById('to');	
	  
	if (opcao==0){
	     var date = new Date();
	     var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	     var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	     
	     document.getElementById('from').value=moment(firstDay).format('L');
	     document.getElementById('to').value=moment(lastDay).format('L');
	
	     s.value=moment(firstDay).format('L');
	     p.value=moment(lastDay).format('L');
	}
		
	  $.getJSON('./Op_Servlet?opt=40&categoria='+opcao+'&dtfrom='+s.value+'&dtto='+p.value, function(data) {	
	    	
	    	$('#'+local).highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Volume Financeiro por SubCategoria'
	            },
	            
	            xAxis: {
	                type: 'category'
	            },
	            yAxis: {
	                title: {
	                    text: 'Volume Financeiro'
	                }

	            },
	            legend: {	
	                enabled: false
	            },
	            plotOptions: {
	                series: {
	                    borderWidth: 0,
	                    dataLabels: {
	                        enabled: true,
	                        formatter: function () {
		                    	//alert(this.y);
		                        return  accounting.formatMoney(this.y);
		                    }
	                    }
	                }
	            },

	            tooltip: {
	                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b><br/>'
	            },

	            series: data[0]['data']
	            
	        });
	    });

	}
function g9(opcao,local) {
	var inicio,fim;
	inicio=document.getElementById('from').value;
    fim=document.getElementById('to').value;
var options = {
        chart: {
            renderTo: local,
            type: 'line'
        },
        title: {
            text: 'Fluxo de Caixa',
            x: -20 //center
        },
        xAxis:
        {
        categories: [],
        labels:{
        	rotation: 45
        	
        }
        },
        plotOptions: {
        	
            series: {
                dataLabels: {
                    enabled: true,
                    borderRadius: 5,
                    backgroundColor: 'rgba(252, 255, 197, 0.7)',
                    borderWidth: 1,
                    borderColor: '#AAA',
                    y: -6,
                    formatter: function () {
                    	//alert(this.y);
                        return  accounting.formatMoney(this.y);
                    }
                },
                tooltip: {
		        	pointFormatter: function(){ return accounting.formatMoney(this.y);}
                    
                }
                
            }
            
        },
        series: [{}]
    };
	//var e = document.getElementById("year");
	//var strUser = e.options[e.selectedIndex].value;
	//alert(strUser);
    //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=2&ano='+strUser, function(data) {
    $.getJSON('./Op_Servlet?opt=56&inicio='+inicio+'&fim='+fim, function(data) {	
    	options.xAxis.categories = data[0]['data'];
    	
    	for(var d=0;d<data.length-1;d++){
    	options.series[d] = data[d+1];
    	}
    	//options.series[1] = data[2];
    	//options.series[2] = data[3];
    	//options.series[3] = data[4];
    	
    	
       
        var chart2 = new Highcharts.Chart(options);
    });

}
