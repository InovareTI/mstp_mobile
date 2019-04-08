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
  if(geral.mes_calendario<9){
    if(geral.mes_calendario==1){
     range_start = (d.getFullYear()-1)+"-"+"12"+"-01";
     range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }else{
      range_start = d.getFullYear()+"-"+"0"+(geral.mes_calendario-1)+"-01";
      range_fim= d.getFullYear()+"-"+"0"+(geral.mes_calendario)+"-"+hoje_aux;
    }
  }else{
    if(geral.mes_calendario==9 || geral.mes_calendario==10){
       range_start = d.getFullYear()+"-"+"0"+(geral.mes_calendario-1)+"-01";
    }else{
      range_start = d.getFullYear()+"-"+(geral.mes_calendario-1)+"-01";
    }
    if(geral.mes_calendario<12){
    range_fim= d.getFullYear()+"-"+(geral.mes_calendario)+"-"+hoje_aux;
    }else{
      range_fim= d.getFullYear()+"-"+(geral.mes_calendario)+"-"+hoje_aux;
    }
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
                  url: "http://192.168.0.31:8080/mstp_mobile/Op_Servlet",	  
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
            
          }
      },
      events: {
        url: 'http://192.168.0.31:8080/mstp_mobile/Op_Servlet',
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
	        url: 'http://192.168.0.31:8080/mstp_mobile/Op_Servlet',
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
        url: 'http://192.168.0.31:8080/mstp_mobile/Op_Servlet',
        type: 'POST',
        data: {
          opt: '23',
          mes:  geral.mes_calendario
        }
   }});
   $('#calendar').fullCalendar('rerenderEvents');
  }