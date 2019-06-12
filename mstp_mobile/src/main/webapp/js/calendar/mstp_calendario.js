function ativa_calendar(){
  var d = new Date();
  var hoje= d.getDate();
  var hoje_aux;
  geral.mes_calendario = d.getMonth() + 1;
  if(hoje<10){
      hoje_aux="0"+d.getDate();
 }else{
   hoje_aux=d.getDate();
 }
  var range_start;
  var range_fim;
  
    if(geral.mes_calendario==1){
     range_start = (d.getFullYear()-1)+"-"+"10"+"-01";
     range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==2){
    	 range_start = (d.getFullYear()-1)+"-"+"11"+"-01";
         range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==3){
    	 range_start = (d.getFullYear()-1)+"-"+"12"+"-01";
         range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==4){
   	 range_start = d.getFullYear()+"-"+"01"+"-01";
     range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==5){
      	 range_start = d.getFullYear()+"-"+"02"+"-01";
         range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==6){
      	 range_start = d.getFullYear()+"-"+"03"+"-01";
         range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==7){
      	 range_start = d.getFullYear()+"-"+"04"+"-01";
         range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==8){
      	 range_start = d.getFullYear()+"-"+"05"+"-01";
         range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==9){
      	 range_start = d.getFullYear()+"-"+"06"+"-01";
         range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==10){
      	 range_start = d.getFullYear()+"-"+"07"+"-01";
         range_fim= d.getFullYear()+"-"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==11){
      	 range_start = d.getFullYear()+"-"+"08"+"-01";
         range_fim= d.getFullYear()+"-"+(geral.mes_calendario)+"-"+hoje_aux;
    }else if(geral.mes_calendario==12){
      	 range_start = d.getFullYear()+"-"+"09"+"-01";
         range_fim= d.getFullYear()+"-"+(geral.mes_calendario)+"-"+hoje_aux;
    }
 
   
  
  console.log("inicio:"+range_start);
  console.log("fim:"+range_fim);
  $('#calendar').fullCalendar(
    {
      locale: 'pt-br',
      header: {
  left:   'title',
  center: '',
  right:  'prev,next'
},
validRange: {
    start: range_start,
    end: range_fim
  },
      selectable:true,
      dayClick: function(date) {
      //alert('clicked ' + date.format());
      document.getElementById("div_ajuste_ponto").style.display = "block";
      document.getElementById("data_invisivel").value=date.format('L');
        $.ajax({
                  type: "POST",
                  data: {"opt":25,
                        "data":date.format('L')},		  
                  url: "http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet",	  
                  cache: false,
                  dataType: "text",
                  success: onSuccess_mostra_registro
          });
          function onSuccess_mostra_registro(data){
            var aux_ajuste=JSON.parse(data);
            $('#data_ajuste_ponto').html("<label style='font-size:24px'>"+date.format('L')+"</label>");
            //alert(aux_ajuste[1]);
            document.getElementById('hora_entrada').value=aux_ajuste[0];
            document.getElementById('hora_ini_inter').value=aux_ajuste[1];
            document.getElementById('hora_fim_inter').value=aux_ajuste[2];
            document.getElementById('hora_saida').value=aux_ajuste[3];
            document.getElementById('input_localidade_ajuste').value= geral.ultimo_registro_localidade + "-" + geral.ultimo_registro_localidade_site;
          }
      },
      events: {
        url: 'http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet',
        type: 'POST',
        data: {
          opt: '23',
          mes:  geral.mes_calendario,
          inicio: range_start,
          fim: range_fim
        }
   }
      }
   );
  $('#calendar').fullCalendar('rerenderEvents');
}
function calendario_prev() {
	   var timestamp = Date.now();
	    $('#calendar').fullCalendar('prev'); // call method
	     geral.mes_calendario--;
	     alert(geral.mes_calendario);
	     $('#calendar').fullCalendar({
	     events: {
	        url: 'http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet',
	        type: 'POST',
	        cache:false,
	        data: {
	          opt: '23',
	          mes:  geral.mes_calendario,
	          _: timestamp
	        }
	   }});
	   $('#calendar').fullCalendar('rerenderEvents');
	  }
function calendario_next() {
    $('#calendar').fullCalendar('next'); // call method
     geral.mes_calendario++;
      $('#calendar').fullCalendar({
     events: {
        url: 'http://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet',
        type: 'POST',
        data: {
          opt: '23',
          mes:  geral.mes_calendario
        }
   }});
   $('#calendar').fullCalendar('rerenderEvents');
  }