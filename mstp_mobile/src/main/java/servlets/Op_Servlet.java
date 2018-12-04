package servlets;


import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailException;

import Classes.Agenda;
import Classes.Cliente;
import Classes.Conexao;
import Classes.Feriado;
import Classes.MercadoPago;
import Classes.Pessoa;

import Classes.Semail;

/**
 * Servlet implementation class Op_Servlet
 */
@WebServlet("/Op_Servlet")
public class Op_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Op_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		operacoes_mstp_mobile(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		operacoes_mstp_mobile(request, response);
	}
	
	 public void operacoes_mstp_mobile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	ResultSet rs ;
			ResultSet rs2,rs3 ;
			String dados_tabela;
			String opt;
			String insere;
			String param1;
			String param2;
			String param3;
			String param4;
			String param5;
			String param6;
			String param7;
			String param8;
			String param9;
			String param10;
			String param11;
			String query;
			String query2 = "";
			String param12;
			String param13;
			String param14;
			String param15;
			String aux;
			int last_id;
			String array_string_aux[];
			last_id=0;
			param1="";
			query="";
			param2="";
			param3="";
			param4="";
			param5="";
			param6="";
			param7="";
			param8="";
			param9="";
			param10="";
			param11="";
			param12="";
			param13="";
			param14="";
			param15="";
			aux="";
			insere="";
			opt="";
			dados_tabela="";
			double money; 
			NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
			String moneyString;
			rs2=null;
			Locale locale_ptBR = new Locale( "pt" , "BR" ); 
			Locale.setDefault(locale_ptBR);
			HttpSession session = req.getSession(true);
			Conexao conn = (Conexao) session.getAttribute("conexao");
			Pessoa p= (Pessoa) session.getAttribute("pessoa");
			Feriado feriado= new Feriado();
			//Cliente c= new Cliente();
			//Agenda agenda=new Agenda();
			Calendar d = Calendar.getInstance();
			Date data = d.getTime();
			d.get(Calendar.MONTH);
			
			//System.out.println(data.);
			Locale brasil = new Locale("pt", "BR");
			DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, brasil);
			DateFormat f3 = DateFormat.getDateTimeInstance();
			Timestamp time = new Timestamp(System.currentTimeMillis());
			java.sql.Date date_sql = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			opt=req.getParameter("opt");
			
			try {
				System.out.println(p.get_PessoaUsuario()+" - Chegou no servlet de Operações do MSTP Mobile - "+f3.format(time)+" Opção:"+opt);
				if(opt.equals("1")) {
					System.out.println("Inserindo Registro - "+f3.format(time));
					param1=req.getParameter("latitudemobile");
					param2=req.getParameter("longitudemobile");
					param3=req.getParameter("timestam");
					param4=req.getParameter("distancia");
					param5=req.getParameter("datetime");
					String [] HH=new String[4];
					param7=req.getParameter("localidade");
					param6=req.getParameter("_");
					array_string_aux=param7.split(",");
					System.out.println("valor do param7 é :" +param7);
					//System.out.println("valor do param8 é :" +param8);
					//time = Timestamp.valueOf(param3);
					//System.out.println("hora corrente é " + d.get(Calendar.MINUTE));
					Calendar mobile_time = Calendar.getInstance();
					mobile_time.setTimeInMillis(Long.parseLong(param6));
					//System.out.println(d.getTimeInMillis());
					//System.out.println(mobile_time.getTimeInMillis());
					long diff_mstp_mobile=Math.abs(d.getTimeInMillis() - mobile_time.getTimeInMillis());
					//System.out.println(Math.round(((diff_mstp_mobile % 86400000) % 3600000) / 60000));
					//System.out.println("Registro não autorizado para usuário:"+p.get_PessoaUsuario()+" em "+ f3.format(time) +" . Tempo de firença em minutos: "+ diff_mstp_mobile/(1000*60*60*60));
					if((Math.round(((diff_mstp_mobile % 86400000) % 3600000) / 60000))>5) {
						//System.out.println(Math.round(((diff_mstp_mobile % 86400000) % 3600000) / 60000));
						System.out.println("Registro não autorizado para usuário:"+p.get_PessoaUsuario()+" em "+ f3.format(time) +" . Tempo de firença em minutos: "+ Math.round(((diff_mstp_mobile % 86400000) % 3600000) / 60000));
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Datas inconsistentes entre seu aparelho e nosso servidor. Por favor verifique suas configurações de data e Hora!");
						return;
					}
					int aux_hora=d.get(Calendar.HOUR_OF_DAY);
					int aux_min=d.get(Calendar.MINUTE);
					String tipo_registro="";
					
					
					//System.out.println("Obeto timestamp " +time);
					//System.out.println("Paramentro timestamp " +param3);
					//System.out.println("Paramentro datetime " +param5);
	    				//query="SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' order by datetime_servlet desc limit 1";
					//System.out.println(query);
	    				//rs=conn.Consulta(query);
					/*if(rs.next()) {
						if(rs.getString("tipo_registro").equals("Entrada")) {
							tipo_registro="Inicio_intervalo";
						}else if(rs.getString("tipo_registro").equals("Inicio_intervalo"))  {
							tipo_registro="Fim_intervalo";
						}else if(rs.getString("tipo_registro").equals("Fim_intervalo"))  {
							tipo_registro="Saída";
						}else {
							tipo_registro="Entrada";
						}
					}else {*/
						tipo_registro=array_string_aux[3];
					//}
					insere="";
	    				insere="INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes,timeStamp_mobile) VALUES ('1','"+p.getEmpresa()+"','"+p.get_PessoaUsuario()+"','"+param1+"','"+param2+"','"+f2.format(time)+"',"+param4+",'"+param5+"','"+time+"',"+aux_hora+","+aux_min+",'"+tipo_registro+"','"+array_string_aux[0]+"','"+array_string_aux[1]+"','"+array_string_aux[2]+"',"+(d.get(Calendar.MONTH)+1)+",'"+param3+"')";
	    				//System.out.println(insere);
	    				if(conn.Inserir_simples(insere)){
				    		//System.out.println("Registro Cadastrado");
				    		rs=conn.Consulta("Select * from registros order by id_sistema desc limit 1");
				    		if(rs.next()){
				    			last_id=rs.getInt(1);
				    			resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								//System.out.println("Registro tipo: "+tipo_registro);
								out.print(tipo_registro+";"+rs.getString("datetime_mobile"));
				    		}
				    
				    	
		    			}
	    				if(tipo_registro.equals("Saída")) {
	    					String saida;
	    					String entrada;
	    					String HH_tipo="";
	    					time = new Timestamp(System.currentTimeMillis());
	    					NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
    						DecimalFormat numberFormat = (DecimalFormat)nf;
    						numberFormat.applyPattern("#0.00");
    						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    						Date d1 = null;
	    					//Date d2 = null;
	    					Calendar entrada_cal=Calendar.getInstance();
	    					Calendar saida_cal=Calendar.getInstance();
	    					System.out.println("Analisando horas extras de "+p.get_PessoaUsuario());
	    					try {
	    					query="SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								entrada=rs2.getString("datetime_servlet");
								d1= format.parse(entrada);
								entrada_cal.setTime(d1);
							}else {
								entrada="";
							}
							query="SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								saida=rs2.getString("datetime_servlet");
								d1= format.parse(saida);
								saida_cal.setTime(d1);
							}else {
								saida="";
							}
							//System.out.println(entrada);
							//System.out.println(saida);
							HH=calcula_hh(entrada,saida);
							//System.out.println("Total de Horas"+HH[1]);
							if(Double.parseDouble(HH[1])>0) {
								query="SELECT * FROM horas_extras WHERE id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"' order by he_data desc limit 1";
		    					rs=conn.Consulta(query);
		    					if(HH[3].equals("Sábado") || HH[3].equals("Domingo")) {
	    							HH_tipo="Horas Extra";
	    						}else {
	    							HH_tipo="Banco";
	    						}
		    					
		    					if(rs.next()) {
		    						query2="update horas_extras set he_quantidade="+numberFormat.format(Double.parseDouble(HH[1]))+",horas_noturnas="+numberFormat.format(Double.parseDouble(HH[2]))+",entrada='"+f3.format(entrada_cal.getTime())+"',saida='"+f3.format(saida_cal.getTime())+"',origen='Automatico - MSTP MOBILE',aprovada='N',compensada='N',tipo_HH='"+HH_tipo+"',mes_HH='"+(entrada_cal.get(Calendar.MONTH)+1)+"' where id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
		    						conn.Alterar(query2);
		    					}else {
		    						insere="";
		    						
		    						insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add,tipo_HH,mes_HH,empresa) values('"+p.get_PessoaUsuario()+"','"+f2.format(time)+"',"+numberFormat.format(Double.parseDouble(HH[1]))+",'"+f3.format(entrada_cal.getTime())+"','"+f3.format(saida_cal.getTime())+"',"+numberFormat.format(Double.parseDouble(HH[2]))+",'N','Automatico - MSTP MOBILE','N','"+f3.format(time)+"','"+HH_tipo+"','"+entrada_cal.get(Calendar.MONTH)+1+"',"+p.getEmpresaObj().getEmpresa_id()+")";
		    						
		    						conn.Inserir_simples(insere);
		    					
		    					}
							}else if(Double.parseDouble(HH[1])<0){
								query="SELECT * FROM horas_extras WHERE id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"' order by he_data desc limit 1";
		    					rs=conn.Consulta(query);
		    					if(HH[3].equals("Sábado") || HH[3].equals("Domingo")) {
	    							HH_tipo="Horas Extra";
	    						}else {
	    							HH_tipo="Banco";
	    						}
		    					
		    					if(rs.next()) {
		    						query2="update horas_extras set he_quantidade="+numberFormat.format(Double.parseDouble(HH[1]))+",horas_noturnas="+numberFormat.format(Double.parseDouble(HH[2]))+",entrada='"+f3.format(entrada_cal.getTime())+"',saida='"+f3.format(saida_cal.getTime())+"',origen='Automatico - MSTP MOBILE',aprovada='Y',compensada='N',tipo_HH='"+HH_tipo+"',mes_HH='"+(entrada_cal.get(Calendar.MONTH)+1)+"' where id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
		    						conn.Alterar(query2);
		    					}else {
		    						insere="";
		    						
		    						insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add,tipo_HH,mes_HH,empresa) values('"+p.get_PessoaUsuario()+"','"+f2.format(time)+"',"+numberFormat.format(Double.parseDouble(HH[1]))+",'"+f3.format(entrada_cal.getTime())+"','"+f3.format(saida_cal.getTime())+"',"+numberFormat.format(Double.parseDouble(HH[2]))+",'Y','Automatico - MSTP MOBILE','N','"+f3.format(time)+"','"+HH_tipo+"','"+(entrada_cal.get(Calendar.MONTH)+1)+"',"+p.getEmpresaObj().getEmpresa_id()+")";
		    						
		    						conn.Inserir_simples(insere);
		    					
		    					}
							}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    					/*System.out.println("entrou no calculo de horas");
	    					String dateStart = "";
	    					String dateStop = "";
	    					String exp_entrada="";
	    					String exp_saida="";
	    					query="SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
	    					rs=conn.Consulta(query);
	    					if(rs.next()) {
	    						dateStart=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-4);
	    						exp_entrada=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
	    						//exp_entrada=exp_entrada+" 08:10:00";
	    					}*/
	    					/*
	    					query="SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
	    					rs=conn.Consulta(query);
	    					if(rs.next()) {
	    						dateStop=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-4);
	    						exp_saida=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
	    						//exp_saida=exp_saida+" 18:30:00";
	    					}*/
	    					/*
	    					query="SELECT entrada,saida FROM expediente";
	    					rs=conn.Consulta(query);
	    					if(rs.next()) {
	    						exp_entrada=exp_entrada+" " + rs.getString(1) +":00";
	    						exp_saida=exp_saida+" " + rs.getString(2) +":00";
	    					}*/

	    					// Custom date format
	    					/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

	    					Date d1 = null;
	    					Date d2 = null;
	    					Date expd1= null;
	    					Date expd2=null;*/
	    					//System.out.println("hora inicio:"+dateStart);
	    					//System.out.println("hora fim:"+dateStop);
	    					/*try {
	    						double horas_normais=0.0;
	    						NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
	    						DecimalFormat numberFormat = (DecimalFormat)nf;
	    						numberFormat.applyPattern("#0.00");
	    						
	    						double horas_noturnas=0.0;
	    						double horas_tot=0.0;
	    						double min_noturnas=0.0;
	    						double hh_saida;
	    						double hh_entrada;
	    					    d1 = format.parse(dateStart);
	    					    Calendar hora_entrada=Calendar.getInstance();
	    					    hora_entrada.setTime(d1);
	    					    //System.out.println("teste fabio: "+hora_entrada.get(Calendar.HOUR_OF_DAY));
	    					    hh_entrada=hora_entrada.get(Calendar.HOUR_OF_DAY);
	    					    hh_entrada=hh_entrada + (hora_entrada.get(Calendar.MINUTE) / 60.0);
	    					    d2 = format.parse(dateStop);
	    					    Calendar hora_saida=Calendar.getInstance();
	    					    hora_saida.setTime(d2);
	    					    hh_saida=hora_saida.get(Calendar.HOUR_OF_DAY);
	    					    hh_saida=hh_saida + (hora_saida.get(Calendar.MINUTE) / 60.0);
	    					    horas_tot=hh_saida-hh_entrada;
	    					    //System.out.println(hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE));
	    					    //System.out.println((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)) / 60.0);
	    					    //horas_tot=horas_tot+((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)) / 60.0);
	    					    if(horas_tot>10) {
	    					    if(hh_entrada>=18.5 && hh_saida>=22 ) {
	    					    	System.out.println("opcao 1");
	    					    	//System.out.println(horas_tot);
	    					    	horas_noturnas=horas_noturnas+(hh_saida-22);
	    					    	min_noturnas=hora_saida.get(Calendar.MINUTE);
	    					    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
	    					    	horas_normais=horas_tot-horas_noturnas;
	    					    }else if(hh_entrada<=5 && hh_saida<=5){
	    					    	System.out.println("opcao 2");
	    					    	horas_noturnas=hh_saida-hh_entrada;
	    					    	min_noturnas=hora_saida.get(Calendar.MINUTE)+hora_entrada.get(Calendar.MINUTE);
	    					    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
	    					    	horas_normais=horas_tot-horas_noturnas;
	    					    }else if(hh_entrada<=5 && hh_saida>=22){
	    					    	System.out.println("opcao 3");
	    					    	horas_tot=horas_tot-10;
	    					    	horas_noturnas=horas_noturnas + (hh_saida - 22);
	    					    	horas_noturnas=horas_noturnas + (5 - hh_entrada);
	    					    	min_noturnas=hora_saida.get(Calendar.MINUTE)+hora_entrada.get(Calendar.MINUTE);
	    					    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
	    					    	horas_normais=horas_tot-horas_noturnas;
	    					    }else if(hh_entrada>=18.5){
	    					    	System.out.println("opcao 4");
	    					    	//horas_tot=hh_saida-hh_entrada;
	    					    	if(hh_saida>=22) {
	    					    		System.out.println("opcao 4.1");
	    					    		horas_noturnas=horas_noturnas + (hh_saida - 22);
	    					    		min_noturnas=hora_saida.get(Calendar.MINUTE);
		    					    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
		    					    	horas_normais=horas_tot-horas_noturnas;
	    					    	}else {
	    					    		System.out.println("opcao 4.2");
	    					    		//System.out.println((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)));
	    					    		horas_normais=horas_tot+((Math.abs(hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE))) / 60.0);
	    					    	}
	    					    }else {
	    					    	System.out.println("opcao 5");
	    					    	if(hh_saida>=18.5 && hh_saida<=22) {
	    					    		System.out.println("opcao 5.1");
	    					    		horas_noturnas=0;
	    					    		horas_normais=hh_saida-18.5;
	    					    		min_noturnas=hora_saida.get(Calendar.MINUTE);
	    					    		horas_normais=horas_normais+(min_noturnas / 60.0);
	    					    	}
	    					    }
	    					    }else {
	    					    	if(hh_entrada > 8) {
	    					    		horas_normais=horas_tot - 10.33;
	    					    		horas_noturnas=0.0;
	    					    	}else if(hh_saida<18.5) {
	    					    		horas_normais=horas_tot - 10.33;
		    					    	horas_noturnas=0.0;
	    					    	}
	    					    }
	    					    System.out.println("Analisando HH para "+p.get_PessoaUsuario());
	    					    System.out.println("Hora Entrada mais cedo "+f3.format(hora_entrada.getTime()));
	    					    System.out.println("Hora Entrada maid tardia "+f3.format(hora_saida.getTime()));
	    					    System.out.println("Total "+numberFormat.format(horas_tot));
		    					System.out.println("Total de Horas Extras Normais "+numberFormat.format(horas_normais));
		    					System.out.println("Total de horas noturnas "+numberFormat.format(horas_noturnas));
	    					if(horas_normais>0 || horas_noturnas>0) {
	    					
	    					query="SELECT * FROM horas_extras WHERE id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"' order by he_data desc limit 1";
	    					rs=conn.Consulta(query);
	    					
	    					
	    					if(rs.next()) {
	    						query2="update horas_extras set he_quantidade="+numberFormat.format(horas_normais)+",horas_noturnas="+numberFormat.format(horas_noturnas)+",entrada='"+f3.format(hora_entrada.getTime())+"',saida='"+f3.format(hora_saida.getTime())+"',origen='Automatico - MSTP MOBILE',aprovada='N',compensada='N' where id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
	    						conn.Alterar(query2);
	    					}else {
	    						insere="";
	    						insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add) values('"+p.get_PessoaUsuario()+"','"+f2.format(time)+"',"+numberFormat.format(horas_normais)+",'"+f3.format(hora_entrada.getTime())+"','"+f3.format(hora_saida.getTime())+"',"+numberFormat.format(horas_noturnas)+",'N','Automatico - MSTP MOBILE','N','"+f3.format(time)+"')";
	    						
	    						conn.Inserir_simples(insere);
	    					
	    					}
	    					}
	    					} catch (ParseException e) {
	    					    e.printStackTrace();
	    					} */
	    				}
				}else if(opt.equals("2")) {
					param1=req.getParameter("func");
					//System.out.println("funcionario é " + param1);
					if(param1.equals("todos")) {
						query="select * from registros order by datetime_servlet desc";
					}else {
						query="select * from registros where usuario='"+param1+"' and data_dia='"+f2.format(time)+"' order by datetime_servlet desc";
					}
					//System.out.println(query);
					rs=conn.Consulta(query);
					if(rs.next()) {
						aux=rs.getString("data_dia");
						
						dados_tabela="<ons-list>"+"\n";
						dados_tabela=dados_tabela+"<ons-list-header>"+rs.getString("data_dia")+"</ons-list-header>"+"\n";
						rs2=conn.Consulta("select * from usuarios where id_usuario='"+rs.getString("usuario")+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'");
						if(rs2.next()) {
						rs.beforeFirst();
						while(rs.next()) {
							if(aux.equals(rs.getString("data_dia"))){
							
								dados_tabela=dados_tabela+
										"<ons-list-item id=\""+rs.getInt(1)+"\">Nome:"+rs2.getString("nome")+" <br>CPF:"+rs2.getString("cpf")+" <br>PIS:"+rs2.getString("pis")+" <br>GPS:"+rs.getString("latitude")+" , "+rs.getString("longitude")+" <br> "+rs.getString("local_registro")+" : "+rs.getString("tipo_local_registro")+"<br>Operadora: "+rs.getString("site_operadora_registro")+"<br>Distancia: "+rs.getInt("distancia")+" km <br> Data e hora: "+rs.getString("datetime_servlet")+"<br>Tipo Registro: "+rs.getString("tipo_registro")+"<br>Empresa:"+p.getEmpresaObj().getNome()+"<br>"+p.getEmpresaObj().getEndereco()+"</ons-list-item>"+"\n";
								
								}else {
									dados_tabela=dados_tabela+"<ons-list-header>"+rs.getString("data_dia")+"</ons-list-header>"+"\n";
									dados_tabela=dados_tabela+
											"<ons-list-item id=\""+rs.getInt(1)+"\">"+rs.getString("latitude")+" , "+rs.getString("longitude")+"</ons-list-item>"+"\n";
									aux=rs.getString("data_dia");
								}
								//System.out.println(dados_tabela);
							}
						}
						}
				      
						dados_tabela=dados_tabela+"</ons-list>";
					
					//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else if(opt.equals("3")) {
					System.out.println("Alterando Expediente");
					param1=req.getParameter("entrada");
					param2=req.getParameter("saida");
					query="update expediente set entrada='"+param1+"',saida='"+param2+"'";
					if(conn.Update_simples(query)) {
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Dados Alterados com sucesso!");
					}
					}else if(opt.equals("4")) {
						System.out.println("Buscando Expediente");
						query="select * from expediente";
						rs=conn.Consulta(query);
						if(rs.next()) {
							dados_tabela="[[\""+rs.getString("entrada")+"\"],[\""+rs.getString("saida")+"\"]]";
							//System.out.println(dados_tabela);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}
						
					}else if(opt.equals("5")) {
						System.out.println("Atualizando Hora Extra e Banco");
						param1=req.getParameter("hora_extra");
						param2=req.getParameter("banco");
						param3=req.getParameter("reg_distancia");
						param4=req.getParameter("vinculo");
						query="update expediente set hora_extra='"+param1+"',banco_de_hora='"+param2+"',reg_distancia='"+param3+"',vinculo='"+param4+"'";
						if(conn.Update_simples(query)) {
							query="update usuarios set controle_vinculo='"+param4+"' where empresa='"+p.getEmpresa()+"'";
							conn.Update_simples(query);
							//System.out.println(query);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Dados Alterados com sucesso!");
						}
					}else if(opt.equals("6")) {
						System.out.println("Buscando Expediente");
						query="select * from expediente";
						rs=conn.Consulta(query);
						if(rs.next()) {
							dados_tabela="[["+rs.getString("hora_extra")+"],["+rs.getString("banco_de_hora")+"],["+rs.getString("reg_distancia")+"],["+rs.getString("vinculo")+"]]";
							//System.out.println(dados_tabela);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}
						
					}else if(opt.equals("7")) {
						param1=req.getParameter("versao_atual");
						System.out.println("Buscando Versao");
						query="select * from versao order by versao_atual desc limit 1";
						rs=conn.Consulta(query);
						if(rs.next()) {
							if(rs.getString("versao_atual").equals(param1)) {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("notupdate");
							}else {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("update");
							}
						}
						
					}else if(opt.equals("8")) {
						
						if(p.get_PessoaPerfil().equals("ADM")) {
						param1=req.getParameter("latitudemobile");
						param2=req.getParameter("longitudemobile");
						param3=req.getParameter("timestam");
						param4=req.getParameter("distancia");
						param5=req.getParameter("datetime");
						param6=req.getParameter("usuario");
						param7=req.getParameter("nome_ponto");
						
						insere="";
						insere="insert into pontos (latitude_ponto,longitude_ponto,nome_ponto,usuario_registro,ativo) values('"+param1+"','"+param2+"','"+param7+"','"+param6+"','Y')";
						if(conn.Inserir_simples(insere)) {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("ok");
						}else {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("falha no registro do novo ponto");
						}
						}else {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Voce não possui privilégios para essa operação");
						}
						
					}else if(opt.equals("9")) {
						System.out.println("Buscando Funcionários");
						if(p.get_PessoaPerfil().equals("ADM")) {
							rs=conn.Consulta("select distinct usuario from registros");
						}else {
							rs=conn.Consulta("select distinct usuario from registros where usuario='"+p.get_PessoaUsuario()+"'");
						}
						if(rs.next()){
		    				rs.beforeFirst();
		    				dados_tabela="<ons-select id=\"select_test\" onchange='carrega_registros(this.value)'>";
		    	             
		    	          
		    				
		    				while(rs.next()){
		    					if(rs.getString("usuario").equals(p.get_PessoaUsuario())) {
		    					dados_tabela=dados_tabela+"<option value=\""+rs.getString("usuario")+"\" selected>Meus Registros</option>";	
		    					}else {
		    						dados_tabela=dados_tabela+"<option value=\""+rs.getString("usuario")+"\">"+rs.getString("usuario")+"</option>";
		    					}
		    				}
		    				
		    				dados_tabela=dados_tabela+"</ons-select>";
						}
		    				
		    			
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
					}else if(opt.equals("10")) {
						System.out.println("Encerrando sessão de "+p.get_PessoaUsuario() +" em  - "+f3.format(time));
						conn.fecharConexao();
						session.invalidate();
					}else if(opt.equals("11")) {
						String ponto="";
						query="select * from pontos where ativo='Y'";
						param1=req.getParameter("usuario");
						rs=conn.Consulta(query);
						rs2=conn.Consulta("select id_ponto from usuario_ponto where id_usuario='"+param1+"'");
						if(rs2.next()) {
							ponto=rs2.getString(1);
						}
						if(rs.next()) {
							dados_tabela="[";
							rs.beforeFirst();
							while(rs.next()) {
								if(rs.getString("id_ponto").equals(ponto)) {
								dados_tabela=dados_tabela+"[\""+rs.getString("nome_ponto")+"\",\""+rs.getString(2)+"\",\""+rs.getString(3)+"\",\"checked\",[\""+rs.getInt(1)+"\"]],\n";
								}else {
									dados_tabela=dados_tabela+"[\""+rs.getString("nome_ponto")+"\",\""+rs.getString(2)+"\",\""+rs.getString(3)+"\",\" \",[\""+rs.getInt(1)+"\"]],\n";
								}
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
							dados_tabela=dados_tabela+"]";
							//System.out.println(dados_tabela);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}
					}else if(opt.equals("12")) {
						param1=req.getParameter("usuario");
						param2=req.getParameter("ponto");
						//param3=req.getParameter("timestam");
						rs=conn.Consulta("select * from usuario_ponto where id_usuario='"+param1+"'");
						if(rs.next()) {
							if(conn.Update_simples("update usuario_ponto set id_ponto='"+param2+"' where id_usuario='"+param1+"'")){
								rs2=conn.Consulta("select * from pontos where id_ponto="+param2);
								if(rs2.next()) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("[[\"Local de registro atualizado!\"],[\""+rs2.getString(2)+"\"],[\""+rs2.getString(3)+"\"],[\""+rs2.getString(4)+"\"]]");
								}
								
							}else {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("[[\"Operacao falhou!\"],[\"null\"],[\"null\"],[\"null\"]]");
							}
						}else {
							if(conn.Inserir_simples("insert into usuario_ponto (id_usuario,id_ponto) values ('"+param1+"','"+param2+"')")) {
								rs2=conn.Consulta("select * from pontos where id_ponto="+param2);
								if(rs2.next()) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("[[\"Local de registro atualizado!\"],[\""+rs2.getString(2)+"\"],[\""+rs2.getString(3)+"\"],[\""+rs2.getString(4)+"\"]]");
								}
							}else {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("[[\"Operacao falhou!\"],[\"null\"],[\"null\"],[\"null\"]]");
							}
						}
					}else if(opt.equals("13")) {
						param1=req.getParameter("usuario");
						param2=req.getParameter("ponto");
						if(p.get_PessoaPerfil().equals("ADM")) {
							rs=conn.Consulta("select * from usuario_ponto where id_ponto='"+param2+"'");
							if(rs.next()) {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Operação nao pode ser executada enquanto houver usuarios ativos nesse ponto!");
							}else {
								if(conn.Update_simples("update  pontos set ativo='N' where id_ponto="+param2)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Operação executada com Sucesso!");
								}
							}
						}else {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Voce não possui privilégios para essa operação");
						}
					}else if(opt.equals("14")) {
						param1=req.getParameter("usuario");
						param2=req.getParameter("ponto");
						query="select * from horas_extras where id_usuario='"+param1+"' and aprovada='Y' and compensada='N'";
						rs=conn.Consulta(query);
						if(rs.next()) {
							rs.beforeFirst();
							//dados_tabela="[";
							dados_tabela="<ons-list>";
							
							while(rs.next()) {
								//dados_tabela=dados_tabela+"[[\""+rs.getString("he_data")+"\"],[\""+rs.getString("entrada")+"\"],[\""+rs.getString("saida")+"\"],[\""+rs.getString("he_quantidade")+"\"]],";
								dados_tabela=dados_tabela+
								"<ons-list-header>"+rs.getString("he_data")+"</ons-list-header>"+
								 
							    "<ons-list-item expandable>"+
							    "<div class=\"left\">"+rs.getString("he_data")+"</div>"+
							     
							    "<div class=\"expandable-content\">Total horas:"+rs.getString("he_quantidade")+"<br>Total Horas Noturnas:"+rs.getString("horas_noturnas")+"</div>"+
							    "</ons-list-item>\n";
							}
							dados_tabela=dados_tabela+"</ons-list>\n";
							//dados_tabela=dados_tabela.substring(0, dados_tabela.length()-1);
							//dados_tabela=dados_tabela+"]";
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}
						
					}else if(opt.equals("15")) {
						//System.out.println("Realizando consulta de sites");
						param1=req.getParameter("usuario");
						param2=req.getParameter("operadora");
						if(param2.contains(";")) {
						String[]aux1=param2.split(";");
						System.out.println(aux1[0]);
						System.out.println(aux1[1]);
						if(aux1[0].toUpperCase().equals("VIVO") || aux1[0].toUpperCase().equals("TIM") || aux1[0].toUpperCase().equals("OI") || aux1[0].toUpperCase().equals("CLARO")) {
							query="select * from sites where site_operadora='"+aux1[0]+"' and site_uf='"+aux1[1]+"' and site_ativo='Y'";
						}else {
							query="select * from sites where site_id like '%"+aux1[0]+"%' and site_ativo='Y'";
						}
						}else {
							query="select * from sites where site_id like '%"+param2+"%' and site_ativo='Y'";
						}
						
						//System.out.println(query);
						rs=conn.Consulta(query);
						if(rs.next()) {
							dados_tabela="[";
							rs.beforeFirst();
							while(rs.next()) {
								
									dados_tabela=dados_tabela+"[\""+rs.getString("site_id")+"\",\""+rs.getString("site_latitude").replace(",", ".")+"\",\""+rs.getString("site_longitude").replace(",", ".")+"\",\""+rs.getString("site_operadora")+"\",[\""+rs.getInt(1)+"\"]],\n";
								
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
							dados_tabela=dados_tabela+"]";
							//System.out.println("arquivo finalizado");
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}else {
							dados_tabela="[]";
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}
					}else if(opt.equals("16")) {
						System.out.println("Carregando escritorios no mapa");
						rs=conn.Consulta("Select * from pontos where ativo ='Y' and latitude_ponto<>'' and longitude_ponto<>'' limit 50");
						//System.out.println("Carregando sites no mapa2");
						if(rs.next()){
							//System.out.println("Carregando sites no mapa3");
							dados_tabela="";
							dados_tabela=  "[";
									 
								      
								    		
								        
							rs.beforeFirst();
							while (rs.next()) {
								dados_tabela=dados_tabela+"[[\""+rs.getString("longitude_ponto")+"\"],[\""+rs.getString("latitude_ponto")+"\"]],";
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
							dados_tabela=dados_tabela+"]";
							
									
							//System.out.println(dados_tabela);
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}else {
							PrintWriter out = resp.getWriter();
							out.print("vazia");
						}
					}else if(opt.equals("17")) {

						param1=req.getParameter("tipo");
						param5=req.getParameter("datetime");
						System.out.println("Lançando "+param1+" via Mobile para usuário "+p.get_PessoaUsuario() + " - "+f3.format(time));
						if(param1.equals("folga")) {
						query="select id_ajuste_ponto,aprovada from ajuste_ponto where motivo='FOLGA' and other2='"+param5+"' and usuario='"+p.get_PessoaUsuario()+"'";
						rs=conn.Consulta(query);
						if(rs.next()) {
							if(rs.getString(2).equals("N")) {
								resp.setContentType("application/json");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Folga já lançada para hoje, aguardando aprovação");
							}else if(rs.getString(2).equals("Y")) {
								resp.setContentType("application/json");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Folga já lançada e aprovada");
							}else {
								resp.setContentType("application/json");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Folga já lançada e reprovada");
							}
						}else {
						query="insert into ajuste_ponto (dt_solicitado,dt_entrada,dt_saida,local,motivo,aprovada,usuario,other2,empresa) values ('"+f3.format(time)+"','','','','FOLGA','N','"+p.get_PessoaUsuario()+"','"+param5+"',"+p.getEmpresa()+")";
						if(conn.Inserir_simples(query)) {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Folga requisitada com Sucesso!");
						}else {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Falha na requisição de Folga !");
						}
						}
						}else {
							query="select id_ajuste_ponto,aprovada from ajuste_ponto where motivo='Banco de Horas - Consumo de 8h' and other2='"+param5+"' and usuario='"+p.get_PessoaUsuario()+"'";
							rs=conn.Consulta(query);
							if(rs.next()) {
								if(rs.getString(2).equals("N")) {
									resp.setContentType("application/json");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Compensação já lançada para hoje, aguardando aprovação");
								}else if(rs.getString(2).equals("Y")) {
									resp.setContentType("application/json");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Compensação já lançada e aprovada");
								}else {
									resp.setContentType("application/json");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Compensação já lançada e reprovada");
								}
							}else {
							query="insert into ajuste_ponto (dt_solicitado,dt_entrada,dt_saida,local,motivo,aprovada,usuario,other2,empresa) values ('"+f3.format(time)+"','','','','Banco de Horas - Consumo de 8h','N','"+p.get_PessoaUsuario()+"','"+param5+"',"+p.getEmpresa()+")";
							if(conn.Inserir_simples(query)) {
								resp.setContentType("application/json");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Compensação inserida com sucesso!");
							}else {
								resp.setContentType("application/json");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Falha na requisição da compensação!");
							}
							}
						}
					}else if(opt.equals("18")) {
						System.out.println("Buscando Operadoras");
						query="select distinct site_operadora,site_uf from sites where site_uf<>'' and site_operadora<>'' and site_ativo='Y'";
						rs=conn.Consulta(query);
						if(rs.next()) {
							rs.beforeFirst();
							dados_tabela="";
							while(rs.next()) {
								dados_tabela=dados_tabela+"<ons-card onclick=\"navega('consulta_sites_page');consulta_sites('"+rs.getString(1)+";"+ rs.getString(2)+"')\">\n"+
							      "<div class=\"title\">"+rs.getString(1)+" - "+ rs.getString(2)+"</div>\n"+
							      "<div class=\"content\">Sites "+rs.getString(1)+" - "+ rs.getString(2)+"</div>\n"+
							    "</ons-card>\n";
							}
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}else {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Acesse www.mstp.com.br para carregar sites");
						}
					}else if(opt.equals("19")){
						double horas;
						query="select sum(he_quantidade),sum(horas_noturnas) from horas_extras where id_usuario='"+p.get_PessoaUsuario()+"' and aprovada='Y' and compensada='N' and other1=''";
						System.out.println("Consultando Horas de  "+p.get_PessoaUsuario()+" em "+f3.format(time));
						rs=conn.Consulta(query);
						if(rs.next()) {
							horas=rs.getDouble(1)+rs.getDouble(2);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(horas);
						}
					}else if(opt.equals("20")){
						query="";
						if(p.get_PessoaEmail().contains("BancoHHApprover")) {
							query="select * from horas_extras where aprovada='N'";
							rs=conn.Consulta(query);
							if(rs.next()) {
								rs.beforeFirst();
								dados_tabela="";
								while(rs.next()) {
									dados_tabela=dados_tabela+"<ons-card>"+
								    "<div class=\"title\">"+
								    rs.getString("id_usuario")+
								    "</div>"+
								    "<div class=\"content\">"+
								     " <div>"+
								     "   <ons-button onclick=aprova_hh_mobile()><ons-icon icon=\"ion-thumbsup\"></ons-icon></ons-button>"+
								     "   <ons-button onclick=aprova_hh_mobile()><ons-icon icon=\"ion-share\"></ons-icon></ons-button>"+
								     " </div>"+
								      
								   " </div>"+
								  "</ons-card>\n";
								}
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
							}
						}
					}else if(opt.equals("21")){
						query="";
						query="select id_message,titulo,message from updates_message where usuario='"+p.get_PessoaUsuario()+"' and messagem_lida='N'";
						rs=conn.Consulta(query);
						if(rs.next()) {
							
							dados_tabela="<p>"+rs.getString("titulo")+"</p>";
							dados_tabela=dados_tabela+"<input type='hidden' value='"+rs.getString("id_message")+"' id='message_id'</>";
							rs.beforeFirst();
							while(rs.next()) {
								dados_tabela=dados_tabela+rs.getString("message")+"<br>";
							}
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}else {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("");
						}
					}else if(opt.equals("22")){
						param1=req.getParameter("id_msg");
						query="UPDATE updates_message set messagem_lida='Y' where id_message='"+param1+"' and usuario='"+p.get_PessoaUsuario()+"'";
						conn.Update_simples(query);
							
						
					}else if(opt.equals("23")){
						String cor="";
						String problema="ok";
						param1="";
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
						param1=req.getParameter("mes");
						param2=req.getParameter("inicio");
						param3=req.getParameter("fim");
						Calendar inicio= Calendar.getInstance();
						Calendar fim= Calendar.getInstance();
						Date dt_inicio=format.parse(param2);
						Date dt_fim=format.parse(param3);
						inicio.setTime(dt_inicio);
						fim.setTime(dt_fim);
						
						//System.out.println("mes de pesquisa: "+param1);
						//System.out.println("inicio: "+param2);
						//System.out.println("fim: "+param3);
							dados_tabela="[";
							d.set(Calendar.MONTH, (Integer.parseInt(param1)-2));
							d.set(Calendar.DAY_OF_MONTH, 1);
							//System.out.println(f2.format(d.getTime())); 
							//System.out.println(d.get(Calendar.MONTH));
							while(inicio.before(fim)) {
								problema="ok";
									if(inicio.get(Calendar.DAY_OF_WEEK)==1 || inicio.get(Calendar.DAY_OF_WEEK)==7) {
										cor="#0099ff";
									}else if(feriado.verifica_feriado(f2.format(inicio.getTime()), conn, p)) {
										cor="#0099ff";
									}else {
										query="select datetime_mobile from registros where data_dia='"+f2.format(inicio.getTime())+"' and usuario='"+p.get_PessoaUsuario()+"' and tipo_registro='Entrada'";
										rs=conn.Consulta(query);
										if(rs.next()) {
											problema="ok";
										}else {
											problema="nok";
										}
										query="select datetime_mobile from registros where data_dia='"+f2.format(inicio.getTime())+"' and usuario='"+p.get_PessoaUsuario()+"' and tipo_registro='Inicio_intervalo'";
										rs=conn.Consulta(query);
										if(rs.next()) {
											problema="ok";
										}else {
											problema="nok";
										}
										query="select datetime_mobile from registros where data_dia='"+f2.format(inicio.getTime())+"' and usuario='"+p.get_PessoaUsuario()+"' and tipo_registro='Fim_intervalo'";
										rs=conn.Consulta(query);
										if(rs.next()) {
											problema="ok";
										}else {
											problema="nok";
										}
										query="select datetime_mobile from registros where data_dia='"+f2.format(inicio.getTime())+"' and usuario='"+p.get_PessoaUsuario()+"' and tipo_registro='Saída'";
										rs=conn.Consulta(query);
										if(rs.next()) {
											problema="ok";
										}else {
											problema="nok";
										}
										
									if(problema.equals("ok")) {
										cor="#00e600";
									}else {
										cor="#ff5350";
									}
									
									
							      }
									time = new Timestamp(inicio.getTimeInMillis());
									dados_tabela=dados_tabela+"{\"start\":\""+time.toString().substring(0, 10)+"\",\"rendering\": \"background\",\"allDay\":\"true\",\"color\":\""+cor+"\"},\n";
									inicio.add(Calendar.DAY_OF_MONTH, 1);
									
							}
							
							//System.out.println(dados_tabela);
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
							dados_tabela=dados_tabela+"]";
							//System.out.println(dados_tabela);
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						
					}else if(opt.equals("24")){
						
						query="select SQL_NO_CACHE reg_distancia,now() as agora from expediente where now()=now()";
						rs=conn.Consulta(query);
						
						if(rs.next()) {
							System.out.println("Controle de distancia é: "+rs.getString("reg_distancia"));
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(rs.getString("reg_distancia"));
						}
					}else if(opt.equals("25")){
						param1=req.getParameter("data");
						String mensagem="";
						mensagem="[\"00:01\",\"00:01\",\"00:01\",\"00:01\"]";
						query="select * from registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+param1+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
						//System.out.println(query);
						rs=conn.Consulta(query);
						if(rs.next()) {
							if(rs.getInt("hora")<10 && rs.getInt("minutos")<10) {
								mensagem="[\"0"+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\",";
								}else if(rs.getInt("hora")<10){
									mensagem="[\"0"+rs.getInt("hora")+":"+rs.getInt("minutos")+"\",";
								}else if(rs.getInt("minutos")<10) {
									mensagem="[\""+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\",";
								}else {
									mensagem="[\""+rs.getInt("hora")+":"+rs.getInt("minutos")+"\",";
								}
							}else {
								mensagem="[\"00:01\",";
							}
						    query="select * from registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+param1+"' and tipo_registro='Inicio_intervalo' order by datetime_servlet asc limit 1";
							//System.out.println(query);
							rs=conn.Consulta(query);
							if(rs.next()) {
								if(rs.getInt("hora")<10 && rs.getInt("minutos")<10) {
									mensagem=mensagem+"\"0"+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\",";
									}else if(rs.getInt("hora")<10){
										mensagem=mensagem+"\"0"+rs.getInt("hora")+":"+rs.getInt("minutos")+"\",";
									}else if(rs.getInt("minutos")<10) {
										mensagem=mensagem+"\""+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\",";
									}else {
										mensagem=mensagem+"\""+rs.getInt("hora")+":"+rs.getInt("minutos")+"\",";
									}
							
							}else {
								mensagem=mensagem+"\"00:01\",";
							}
							 query="select * from registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+param1+"' and tipo_registro='Fim_intervalo' order by datetime_servlet asc limit 1";
								//System.out.println(query);
							rs=conn.Consulta(query);
							if(rs.next()) {
								if(rs.getInt("hora")<10 && rs.getInt("minutos")<10) {
									mensagem=mensagem+"\"0"+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\",";
									}else if(rs.getInt("hora")<10){
										mensagem=mensagem+"\"0"+rs.getInt("hora")+":"+rs.getInt("minutos")+"\",";
									}else if(rs.getInt("minutos")<10) {
										mensagem=mensagem+"\""+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\",";
									}else {
										mensagem=mensagem+"\""+rs.getInt("hora")+":"+rs.getInt("minutos")+"\",";
									}
							}else {
								mensagem=mensagem+"\"00:01\",";
							}
							query="select * from registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+param1+"' and tipo_registro='Saída' order by datetime_servlet asc limit 1";
							//System.out.println(query);
						    rs=conn.Consulta(query);
						    if(rs.next()) {
								if(rs.getInt("hora")<10 && rs.getInt("minutos")<10) {
									mensagem=mensagem+"\"0"+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\"]";
									}else if(rs.getInt("hora")<10){
										mensagem=mensagem+"\"0"+rs.getInt("hora")+":"+rs.getInt("minutos")+"\"]";
									}else if(rs.getInt("minutos")<10) {
										mensagem=mensagem+"\""+rs.getInt("hora")+":0"+rs.getInt("minutos")+"\"]";
									}else {
										mensagem=mensagem+"\""+rs.getInt("hora")+":"+rs.getInt("minutos")+"\"]";
									}
							}else {
								mensagem=mensagem+"\"00:01\"]";
							}
						
						
						
						System.out.println(mensagem);
						resp.setContentType("application/json");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(mensagem);
					}else if(opt.equals("26")){
						param1=req.getParameter("entrada");
						param2=req.getParameter("saida");
						param3=req.getParameter("inicio_inter");
						param4=req.getParameter("fim_inter");
						param5=req.getParameter("data");
						//param6=req.getParameter("local");
						param7=req.getParameter("motivo");
						//System.out.println(param1);
						//System.out.println(param2);
						//System.out.println(param3);
						//System.out.println(param4);
						//System.out.println(param5);
						query="select * from ajuste_ponto where usuario='"+p.get_PessoaUsuario()+"' and other2='"+param5+"' and aprovada='N' and empresa="+p.getEmpresaObj().getEmpresa_id();
						rs=conn.Consulta(query);
						if(rs.next()) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Voce já possui uma solicitação aguardando aprovação para esse dia. Aguarde finalização!");
						}else {
						query="insert into ajuste_ponto (dt_solicitado,dt_entrada,dt_saida,local,motivo,aprovada,usuario,empresa,other2,dt_ini_inter,dt_fim_inter) values ('"+f3.format(time)+"','"+param5+" "+param1+":"+numeroAleatorio(10,59)+"','"+param5+" "+param2+":"+numeroAleatorio(10,59)+"','-','"+param7+"','N','"+p.get_PessoaUsuario()+"',"+p.getEmpresa()+",'"+param5+"','"+param5+" "+param3+":"+numeroAleatorio(10,59)+"','"+param5+" "+param4+":"+numeroAleatorio(10,59)+"')";
						if(conn.Inserir_simples(query)) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							conn.getConnection().commit();
							out.print("Ajuste Solicitado com Sucesso. Aguarde aprovação");
						}
						}
					}else if(opt.equals("27")){
						resp.setContentType("application/text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Ativo");
					}else if(opt.equals("28")){
						String tipo="";
						String resultado="";
						long daySeconds=0;
						resultado="[";
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Entrada' order by datetime_servlet desc limit 1";
					  rs=conn.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Entrada\"],";
					  }else {
						  resultado=resultado+"[],";
					  }
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Inicio_intervalo' order by datetime_servlet desc limit 1";
					  rs=conn.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Inicio_intervalo\"],";
					  }else {
						  resultado=resultado+"[],";
					  }
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Fim_intervalo' order by datetime_servlet desc limit 1";
					  rs=conn.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Fim_intervalo\"],";
					  }else {
						  resultado=resultado+"[],";
					  }
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
					  rs=conn.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Saída\"]";
					  }else {
						  resultado=resultado+"[]";
					  }
					  
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' order by datetime_servlet desc limit 1";
					  rs=conn.Consulta(query);
					  if(rs.next()) {
						  tipo=rs.getString("tipo_registro");
						  rs3=conn.Consulta("SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1");
			               
			                
			                if(rs3.next()) {
			                	
			                	Calendar c = Calendar.getInstance();
			                	Calendar now = Calendar.getInstance();
			                	long dif = 0;
			                	if(rs3.getString("timeStamp_mobile").indexOf(".")>0) {
			                		c.setTimeInMillis(Long.parseLong(rs3.getString("timeStamp_mobile").substring(0, rs3.getString("timeStamp_mobile").indexOf("."))));
			                	}else {
			                		c.setTimeInMillis(Long.parseLong(rs3.getString("timeStamp_mobile")));
			                	}
			                		dif= now.getTimeInMillis() - c.getTimeInMillis();
			                	daySeconds=TimeUnit.MILLISECONDS.toSeconds(dif) ;
			                	if(tipo.equals("Inicio_intervalo")) {
			                		if(rs.getString("timeStamp_mobile").indexOf(".")>0) {
			                			now.setTimeInMillis(Long.parseLong(rs.getString("timeStamp_mobile").substring(0, rs.getString("timeStamp_mobile").indexOf("."))));
			                		}else {
			                			now.setTimeInMillis(Long.parseLong(rs.getString("timeStamp_mobile")));	
			                		}
			                		dif= now.getTimeInMillis() - c.getTimeInMillis();
			                		daySeconds=TimeUnit.MILLISECONDS.toSeconds(dif) ;
				                }
			                }else {
			                	daySeconds=0;
			                }
					  }else {
						  tipo="";
						  daySeconds=0;
					  }
					     resultado=resultado+",["+daySeconds+"]]";
		                 System.out.println(resultado);
		                resp.setContentType("application/text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(resultado);
					
					}else if(opt.equals("29")){
						param1=req.getParameter("nome");
						param2=req.getParameter("id_");
						param3=req.getParameter("op");
						param4=req.getParameter("es");
						param5=req.getParameter("lat");
						param6=req.getParameter("lng");
						
						param6=param6.replaceAll(" ", "");
						param6=param6.replaceAll(",", ".");
						param5=param5.replaceAll(" ", "");
						param5=param5.replaceAll(",", ".");
						
						query="select * from sites where site_id='"+param2+"'";
						rs=conn.Consulta(query);
						if(rs.next()) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Site já existente. Solicitação abortada");
						}else {
						query="insert into site_aprova (site_nome,site_id,site_operadora,site_estado,dt_pedido,usuario_pedido,aprovado,empresa,site_lat,site_lng) values ('"+param1+"','"+param2+"','"+param3+"','"+param4+"','"+f3.format(time)+"','"+p.get_PessoaUsuario()+"','N','"+p.getEmpresa()+"','"+param5+"','"+param6+"')";
						if(conn.Inserir_simples(query)) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Solicitação realizada com sucesso.Aguarde Aprovação");
						}else {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Erro na solicitação do novo site.");
						}
					}
						}else if(opt.equals("30")){
							param1=req.getParameter("latitudemobile");
							param2=req.getParameter("longitudemobile");
							param3=req.getParameter("_");
							query="insert into localiza_usuarios (usuario,lat,lng,empresa) values ('"+p.get_PessoaUsuario()+"','"+param1+"','"+param2+"','"+p.getEmpresa()+"')";
							conn.Inserir_simples(query);
						}else if(opt.equals("31")){
							System.out.println("buscando quantidade atividades para "+p.get_PessoaUsuario());
							query="";
							query="select SQL_NO_CACHE count(responsavel) from rollout where tipo_campo='Milestone' and responsavel='"+p.get_PessoaUsuario()+"' and dt_fim in ('','01/01/1800') ";
							rs=conn.Consulta(query);
							if(rs.next()) {
								resp.setContentType("application/text");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(rs.getInt(1));
							}else {
								resp.setContentType("application/text");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(0);
							}
						}else if(opt.equals("32")){
							query="";
							String imagem_status;
							String bt_texto="";
							String status_bt="";
							query="select * from rollout where tipo_campo='Milestone' and responsavel='"+p.get_PessoaUsuario()+"' and dt_fim in ('','01/01/1800') ";
							rs=conn.Consulta(query);
							if(rs.next()) {
								rs.beforeFirst();
								status_bt="false";
								while(rs.next()) {
									//System.out.println(rs.getString("status_atividade"));
									if(rs.getString("status_atividade").equals("Finalizada")) {
	    								imagem_status="finished.png";
	    							}else if(rs.getString("status_atividade").equals("parada")) {
	    								imagem_status="stopped.png";
	    								status_bt="true";
	    								bt_texto="Continuar";
	    							}else if(rs.getString("status_atividade").equals("iniciada")) {
	    								imagem_status="started.png";
	    								bt_texto="Parar";
	    								status_bt="false";
	    							}else {
	    								imagem_status="notstarted.png";
	    								bt_texto="Iniciar";
	    								status_bt="true";
	    							}
									
									// esse era o dos cards onclick=\"navega('consulta_rollout_atividade');consulta_rollout_info("+rs.getString("recid")+",'"+rs.getString("milestone")+"','"+rs.getString("dt_inicio_bl")+"','"+ rs.getString("dt_fim_bl")+"')\"
								dados_tabela=dados_tabela+"<ons-card >\n"+
									      "<div class='title' style='display:block'>"+rs.getString("milestone")+"<div style='float:right'><img src='img/"+imagem_status+"'></div></div>\n"+
									      "<div class=\"content\"><div>Planejamento:"+rs.getString("dt_inicio_bl")+" - "+ rs.getString("dt_fim_bl")+"</div><div>Site:"+ rs.getString("value_atbr_field")+"</div><hr><br>"
									      		+ "<section style='padding:10px'><ons-button modifier=\"large\" style=\"border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','"+rs.getString("status_atividade")+"')\">"+bt_texto+"</ons-button><ons-button modifier=\"large\" style=\"background:green;border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','Finalizada')\">Completar</ons-button></section></div>\n"+
									    "</ons-card>\n";
									}
								//System.out.println(dados_tabela);
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
							}
						}else if(opt.equals("33")){
							
							param1=req.getParameter("recid");
							param2=req.getParameter("milestone");
							param3=req.getParameter("status");
							System.out.println(param1);
							System.out.println(param2);
							System.out.println(param3);
							query="";
							if(param3.equals("Finalizada")) {
								query="update rollout set dt_fim='"+f2.format(time)+"',status_atividade='Finalizada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
							}else if(param3.equals("nao iniciada")){
								query="update rollout set dt_inicio='"+f2.format(time)+"',status_atividade='iniciada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
							}else if(param3.equals("iniciada")) {
								query="update rollout set status_atividade='parada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
							}else if(param3.equals("parada")) {
								query="update rollout set status_atividade='iniciada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
							}
							System.out.println(query);
							if(conn.Update_simples(query)) {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Tarefa atualizada com Sucesso!");
							}
						}else if(opt.equals("34")){
							System.out.println("Salvando foto de perfil");
							
							if (ServletFileUpload.isMultipartContent(req)) {
								List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
								//System.out.println("Multipart size: " + multiparts.size());
								Iterator<FileItem> iter = multiparts.iterator();
								while (iter.hasNext()) {
									FileItem item = iter.next();
									if (item.isFormField()) {
								       // processFormField(item);
								    } else {
								    	System.out.println(item.getContentType());
								    	InputStream inputStream2= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
								    	query="";
										query="update usuarios set foto_perfil=? where id_usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'";
										PreparedStatement statement;
										 statement = conn.getConnection().prepareStatement(query);
										 statement.setBlob(1, inputStream2);
										 int row = statement.executeUpdate();
									     statement.getConnection().commit();
									     if (row>0) {
									        	System.out.println("Foto de Perfil salva com sucesso");
									        	statement.close();
									     }
								    }
								}
								}
							
							
							
						}else if(opt.equals("35")){
							System.out.println("Carregando foto de perfil de "+p.get_PessoaUsuario());
							ServletOutputStream out = resp.getOutputStream();
							query="";
							Blob image = null;
							
							query="select foto_perfil from usuarios where id_usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'";
							rs=conn.Consulta(query);
							if(rs.next()) {
								System.out.println(" foto encontrada");
								image=rs.getBlob(1);
								
								byte byteArray[]=image.getBytes(1, (int) image.length());
								resp.setContentType("image/png");
								out.write(byteArray);
								out.flush();
								out.close();
								System.out.println(" foto carregada");
								
							}
						}else if(opt.equals("36")){
							System.out.println("Buscando lista de Aprovacoes");
							param1=req.getParameter("usuario");
							param2=req.getParameter("tipo");
							query="";
							dados_tabela="<div class=\"list\">";
							if(param2.equals("ajustePonto")) {
								if(p.getPerfil_funcoes().contains("AjustePontoApprover")) {
									query="select * from ajuste_ponto where aprovada<>'R' and aprovada <> 'Y' and usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_ajuste_ponto desc";
									rs=conn.Consulta(query);
									if(rs.next()) {
										rs.beforeFirst();
										while(rs.next()) {
											dados_tabela=dados_tabela+"<div class=\"item\" style=\"height:85px\">";
											dados_tabela=dados_tabela+"<a href=\"#\" class=\"item-swipe\"><b>Ajuste de Ponto - "+rs.getString("usuario")+"</b><br>Nova Entrada:"+rs.getString("dt_entrada")+"<br>Nova Saída:"+rs.getString("dt_saida")+"<br>Motivo:"+rs.getString("motivo")+"</a>";
											dados_tabela=dados_tabela+"<div class=\"item-back\">";
											dados_tabela=dados_tabela+"<button class=\"action first btn-delete\" onclick=\"reprova('"+rs.getString("id_ajuste_ponto")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-trash\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"<button class=\"action second btn-check\" onclick=\"aprova_aprovacoes('"+rs.getString("id_ajuste_ponto")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-check\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"</div>";
											dados_tabela=dados_tabela+"</div>";
										}
									}
								}
							}else if(param2.equals("HorasExtras")) {
								if(p.getPerfil_funcoes().contains("BancoHHApprover")) {
									query="select * from horas_extras where aprovada<>'R' and aprovada <> 'Y' and id_usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_he desc";
									rs=conn.Consulta(query);
									if(rs.next()) {
										rs.beforeFirst();
										while(rs.next()) {
											dados_tabela=dados_tabela+"<div class=\"item\" style=\"height:122px\">";
											dados_tabela=dados_tabela+"<a href=\"#\" class=\"item-swipe\"><b>Horas Extras - "+rs.getString("id_usuario")+"</b><br>Data:"+rs.getString("he_data")+"<br>Entrada:"+rs.getString("entrada")+"<br>Saída:"+rs.getString("saida")+"<br>Horas Extras:"+rs.getString("he_quantidade")+"<br>Horas Noturnas:"+rs.getString("horas_noturnas")+"</a>";
											dados_tabela=dados_tabela+"<div class=\"item-back\">";
											dados_tabela=dados_tabela+"<button class=\"action first btn-delete\" onclick=\"reprova('"+rs.getString("id_he")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-trash\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"<button class=\"action second btn-check\" onclick=\"aprova_aprovacoes('"+rs.getString("id_he")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-check\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"</div>";
											dados_tabela=dados_tabela+"</div>";
										}
									}
								}
								
							}else if(param2.equals("SiteNovo")) {
								if(p.getPerfil_funcoes().contains("Site Manager")) {
									query="select * from site_aprova where aprovado<>'R' and aprovado <> 'Y' and usuario_pedido='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by sysid desc";
									rs=conn.Consulta(query);
									if(rs.next()) {
										rs.beforeFirst();
										while(rs.next()) {
											dados_tabela=dados_tabela+"<div class=\"item\">";
											dados_tabela=dados_tabela+"<a href=\"#\" class=\"item-swipe\"><b>Site Novo - "+rs.getString("usuario_pedido")+"</b><br>Site ID:"+rs.getString("site_id")+"<br>Operadora:"+rs.getString("site_operadora")+"</a>";
											dados_tabela=dados_tabela+"<div class=\"item-back\">";
											dados_tabela=dados_tabela+"<button class=\"action first btn-delete\" onclick=\"reprova('"+rs.getString("sysid")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-trash\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"<button class=\"action second btn-check\" onclick=\"aprova_aprovacoes('"+rs.getString("sysid")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-check\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"</div>";
											dados_tabela=dados_tabela+"</div>";
										}
									}
								}
							}
							dados_tabela=dados_tabela+"</div>";
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
						}else if(opt.equals("37")){
							param1=req.getParameter("tipo");
							if(param1.equals("ajustePonto")) {
							if(p.getPerfil_funcoes().contains("AjustePontoApprover")) {
							query="select distinct usuario from ajuste_ponto where aprovada<>'R' and aprovada <> 'Y' and usuario<>'"+p.get_PessoaUsuario()+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_ajuste_ponto desc";
							rs=conn.Consulta(query);
							if(rs.next()) {
								rs.beforeFirst();
								dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\"><option value='0'>Selecione um Funcionário</option>";
								while(rs.next()) {
									dados_tabela=dados_tabela+"<option value='"+rs.getString(1)+"'>"+rs.getString(1)+"</option>";
								}
								dados_tabela=dados_tabela+"</ons-select>";
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
							}else {
								dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\">";
								dados_tabela="<option value='0'>Sem Aprovações Pendentes</option>";
								dados_tabela=dados_tabela+"</ons-select>";
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
							}
							}
							}else if(param1.equals("HorasExtras")) {
								if(p.getPerfil_funcoes().contains("BancoHHApprover")) {
									query="select distinct id_usuario from horas_extras where aprovada<>'R' and aprovada <> 'Y' and id_usuario<>'"+p.get_PessoaUsuario()+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_he desc";
									rs=conn.Consulta(query);
									if(rs.next()) {
										rs.beforeFirst();
										dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\"><option value='0'>Selecione um Funcionário</option>";
										while(rs.next()) {
											dados_tabela=dados_tabela+"<option value='"+rs.getString(1)+"'>"+rs.getString(1)+"</option>";
										}
										dados_tabela=dados_tabela+"</ons-select>";
										resp.setContentType("application/html");  
										resp.setCharacterEncoding("UTF-8"); 
										PrintWriter out = resp.getWriter();
										out.print(dados_tabela);
									}else {
										dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\">";
										dados_tabela="<option value='0'>Sem Aprovações Pendentes</option>";
										dados_tabela=dados_tabela+"</ons-select>";
										resp.setContentType("application/html");  
										resp.setCharacterEncoding("UTF-8"); 
										PrintWriter out = resp.getWriter();
										out.print(dados_tabela);
									}
									}
							}else if(param1.equals("SiteNovo")) {
								if(p.getPerfil_funcoes().contains("Site Manager")) {
									query="select distinct usuario_pedido from site_aprova where aprovado<>'R' and aprovado <> 'Y' and usuario_pedido<>'"+p.get_PessoaUsuario()+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by sysid desc";
									rs=conn.Consulta(query);
									if(rs.next()) {
										rs.beforeFirst();
										dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\"><option value='0'>Selecione um Funcionário</option>";
										while(rs.next()) {
											dados_tabela=dados_tabela+"<option value='"+rs.getString(1)+"'>"+rs.getString(1)+"</option>";
										}
										dados_tabela=dados_tabela+"</ons-select>";
										resp.setContentType("application/html");  
										resp.setCharacterEncoding("UTF-8"); 
										PrintWriter out = resp.getWriter();
										out.print(dados_tabela);
									}else {
										dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\">";
										dados_tabela="<option value='0'>Sem Aprovações Pendentes</option>";
										dados_tabela=dados_tabela+"</ons-select>";
										resp.setContentType("application/html");  
										resp.setCharacterEncoding("UTF-8"); 
										PrintWriter out = resp.getWriter();
										out.print(dados_tabela);
									}
									}
							}
						}else if(opt.equals("38")){
							System.out.println("iniciando reprovação");
							param1=req.getParameter("usuario");
							param2=req.getParameter("tipo");
							param3=req.getParameter("id");
							if(param2.equals("ajustePonto")) {
								query="";
								query="update ajuste_ponto set aprovada='R' where id_ajuste_ponto="+param3+" and usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(conn.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Reprovada com Sucesso!");
								}
							}else if(param2.equals("HorasExtras")) {
								query="";
								query="update horas_extras set aprovada='R' where id_he="+param3+" and id_usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(conn.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Reprovada com Sucesso!");
								}
							}else if(param2.equals("SiteNovo")) {
								query="";
								query="update site_aprova set aprovado='R' where sysid="+param3+" and usuario_pedido='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(conn.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Reprovada com Sucesso!");
								}
							}
						}else if(opt.equals("39")){
							System.out.println("iniciando reprovação");
							param1=req.getParameter("usuario");
							param2=req.getParameter("tipo");
							param3=req.getParameter("id");
							if(param2.equals("HorasExtras")) {
								query="";
								query="update horas_extras set aprovada='Y' where id_he="+param3+" and id_usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(conn.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Reprovada com Sucesso!");
								}
							}else if(param2.equals("ajustePonto")) {
								query="";
								query="update ajuste_ponto set aprovada='Y',dt_aprovada='"+f3.format(time)+"' where id_ajuste_ponto="+param3;
								if(conn.Update_simples(query)) {
									query="select * from ajuste_ponto where id_ajuste_ponto="+param3+" and aprovada='Y'";
									rs=conn.Consulta(query);
									
										if(rs.next()) {
											if((rs.getString("motivo").toUpperCase().equals("FOLGA")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Folga",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Folga, - , - ");
											}else if((rs.getString("motivo").equals("Banco de Horas - Consumo de 8h")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Compensação",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Compensado, - , - ");
												conn.Inserir_simples("insert into horas_extras (he_data,id_usuario,he_quantidade,aprovada,dt_add,compensada,origen) values('"+rs.getString("other2")+"','"+rs.getString("usuario")+"','-8.00','Y','"+f3.format(time)+"','N','Compensação - MSTP WEB')");
											}else if((rs.getString("motivo").equals("Compensação de horas")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Compensação",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Compensado, - , - ");
												conn.Inserir_simples("insert into horas_extras (he_data,id_usuario,he_quantidade,aprovada,dt_add,compensada,origen) values('"+rs.getString("other2")+"','"+rs.getString("usuario")+"','-8.00','Y','"+f3.format(time)+"','N','Compensação - MSTP WEB')");
											}else if((rs.getString("motivo").equals("Licença Médica")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Licença Médica",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Licença Médica, - , - ");
												
											}else {
												insere_regitro(p,rs.getString("usuario"),"Entrada",conn,"0","0",rs.getString("dt_entrada"),"0",rs.getString("dt_entrada"),"PontoAjustado,Spazio RIO,SPAZIO");
												insere_regitro(p,rs.getString("usuario"),"Saída",conn,"0","0",rs.getString("dt_saida"),"0",rs.getString("dt_saida"),"PontoAjustado,Spazio RIO,SPAZIO");
											}
												//rs2=conn.Consulta("select * from usuarios where id_usuario='"+rs.getString("usuario")+"'");
												//if(rs2.next()) {
													//Semail email= new Semail();
													//email.enviaEmailSimples(rs2.getString("email"),"MSTP WEB - Atualização de Ajuste de Ponto","Prezado "+rs2.getString("nome")+", \n \n Sua solicitação de aprovação de ajuste de ponto foi aprovada. com as seguintes informações: \n ID Solicitação: "+rs.getInt(1)+"\n usuario: "+rs.getString("usuario")+"\n Nova Entrada: "+rs.getString("dt_entrada")+"\n Nova Saida: "+rs.getString("dt_saida")+"\n Local: "+rs.getString("local")+"\n Motivo: "+rs.getString("motivo")+"\n \n \n \n ");
												//}
								}
										resp.setContentType("application/html");  
										resp.setCharacterEncoding("UTF-8"); 
										PrintWriter out = resp.getWriter();
										out.print("Ajuste Aprovado com Sucesso");
							}
						}else if(param2.equals("SiteNovo")) {
							query="update site_aprova set aprovado='Y',dt_aprovado='"+f3.format(time)+"' where sysid="+param3;
							if(conn.Update_simples(query)) {
								rs=conn.Consulta("select * from site_aprova where sysid="+param3);
								if(rs.next()) {
									query="INSERT INTO sites (site_id,site_nome,site_latitude,site_longitude,site_uf,site_operadora,dt_add_site,usuario_add_site,empresa) "
							                + "VALUES ('"+rs.getString("site_id")+"','"+rs.getString("site_nome")+"','"+rs.getString("site_lat").trim()+"','"+rs.getString("site_lng").trim()+"','"+rs.getString("site_estado")+"','"+rs.getString("site_operadora")+"','"+time+"','"+rs.getString("usuario_pedido")+"','"+p.getEmpresaObj().getEmpresa_id()+"')";
									if(conn.Inserir_simples(query)) {
										resp.setContentType("application/text");  
										resp.setCharacterEncoding("UTF-8"); 
										PrintWriter out = resp.getWriter();
										out.print("Site Aprovado e criado na base de Sites.");
									}else {
										resp.setContentType("application/text");  
										resp.setCharacterEncoding("UTF-8"); 
										PrintWriter out = resp.getWriter();
										out.print("Erro no cadastro do novo Site");
									}
								}
								
								
							}
						}
					
			}else if(opt.equals("40")){
				param1=req.getParameter("log");
				Semail email=new Semail();
				email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com", "MSTP Mobile - LOG DE ERRO ENVIADO","ENVIADO POR: "+p.get_PessoaUsuario()+", \n \n data de envio: "+time+" \n\n LOG: \n\n "+param1);
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Log do erro enviado com sucesso! estaremos analisando as possíveis causas e soluções!");
				
			}
	 }catch (SQLException e) {
				
				e.printStackTrace();
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Erro!");
			}  catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(NullPointerException e)
	        {
	            System.out.print(e.getMessage());
	            System.out.print(e.getCause());
	            System.out.print(e.getStackTrace());
	        } catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
			
	 public int numeroAleatorio(int min, int max){

		    Random rand = new Random();
		    int randomNum = rand.nextInt((max - min) + 1) + min;

		    return randomNum;
		}
	 public void insere_regitro(Pessoa p,String usuario,String tipo_registro, Conexao conn,String lat,String lng,String timestam,String distancia,String datetime,String Localidade) {
				String query,query2,insere;
				Locale brasil = new Locale("pt", "BR");
				String param1,param2,param3,param4,param5,param7;
				String array_string_aux[];
				Calendar d = Calendar.getInstance();
				Date data = d.getTime();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, brasil);
				DateFormat f3 = DateFormat.getDateTimeInstance();
				Timestamp time = new Timestamp(System.currentTimeMillis());
				java.sql.Date date_sql = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				ResultSet rs ;
				param1=lat;
				param2=lng;
				param3=timestam;
				param4=distancia;
				param5=datetime;
				
				param7=Localidade;
				
				array_string_aux=Localidade.split(",");
				
				try {
				
				
				
				data = format.parse(timestam);
				d.setTime(data);
				int aux_hora=d.get(Calendar.HOUR_OF_DAY);
				int aux_min=d.get(Calendar.MINUTE);
				date_sql = new java.sql.Date(d.getTime().getTime());
				//System.out.println(param5.substring(param5.indexOf(" ")+1));
				insere="";
					insere="INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes) VALUES ('1','"+p.getEmpresaObj().getEmpresa_id()+"','"+usuario+"','"+param1+"','"+param2+"','"+f2.format(d.getTime())+"',"+param4+",'"+param5+"','"+date_sql.toString()+" "+param5.substring(param5.indexOf(" ")+1)+"',"+aux_hora+","+aux_min+",'"+tipo_registro+"','"+array_string_aux[0]+"','"+array_string_aux[1]+"','"+array_string_aux[2]+"',"+(d.get(Calendar.MONTH)+1)+")";
					//System.out.println(insere);
					if(conn.Inserir_simples(insere)){
			    		System.out.println("Registro Cadastrado");
			    			
					}
					if(tipo_registro.equals("Saída")) {
						format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//System.out.println("entrou no calculo de horas");
						String dateStart = "";
						String dateStop = "";
						String exp_entrada="";
						String exp_saida="";
						query="SELECT * FROM registros where usuario='"+usuario+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
						rs=conn.Consulta(query);
						if(rs.next()) {
							dateStart=rs.getString("datetime_servlet");
							exp_entrada=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
							//exp_entrada=exp_entrada+" 08:10:00";
						}
						query="SELECT * FROM registros where usuario='"+usuario+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
						rs=conn.Consulta(query);
						if(rs.next()) {
							dateStop=rs.getString("datetime_servlet");
							exp_saida=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
							//exp_saida=exp_saida+" 18:30:00";
						}
						query="SELECT entrada,saida FROM expediente";
						rs=conn.Consulta(query);
						if(rs.next()) {
							exp_entrada=exp_entrada+" " + rs.getString(1)+":00";
							exp_saida=exp_saida+" " + rs.getString(2)+":00";
						}

						// Custom date format
						

						Date d1 = null;
						Date d2 = null;
						
						//System.out.println("hora inicio:"+dateStart);
						//System.out.println("hora fim:"+dateStop);
						
							double horas_normais=0.0;
							NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
							DecimalFormat numberFormat = (DecimalFormat)nf;
							numberFormat.applyPattern("#0.00");
							
							double horas_noturnas=0.0;
							double horas_tot=0.0;
							double min_noturnas=0.0;
							int hh_saida;
							int hh_entrada;
						    d1 = format.parse(dateStart);
						    Calendar hora_entrada=Calendar.getInstance();
						    hora_entrada.setTime(d1);
						    //System.out.println("teste fabio: "+hora_entrada.get(Calendar.HOUR_OF_DAY));
						    hh_entrada=hora_entrada.get(Calendar.HOUR_OF_DAY);
						    //System.out.println("dt start:"+dateStart);
						    //System.out.println("dt stop:"+dateStop);
						    d2 = format.parse(dateStop);
						    Calendar hora_saida=Calendar.getInstance();
						    hora_saida.setTime(d2);
						    hh_saida=hora_saida.get(Calendar.HOUR_OF_DAY);
						    horas_tot=hh_saida-hh_entrada;
						    //System.out.println(hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE));
						    //System.out.println((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)) / 60.0);
						    horas_tot=horas_tot+((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)) / 60.0);
						    if(hh_entrada>=18 && hh_saida>=22 ) {
						    	System.out.println("opcao 1");
						    	//System.out.println(horas_tot);
						    	horas_noturnas=horas_noturnas+(hh_saida-22);
						    	min_noturnas=hora_saida.get(Calendar.MINUTE);
						    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
						    	horas_normais=horas_tot-horas_noturnas;
						    }else if(hh_entrada<=5 && hh_saida<=5){
						    	System.out.println("opcao 2");
						    	horas_noturnas=hh_saida-hh_entrada;
						    	min_noturnas=hora_saida.get(Calendar.MINUTE)+hora_entrada.get(Calendar.MINUTE);
						    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
						    	horas_normais=horas_tot-horas_noturnas;
						    }else if(hh_entrada<=5 && hh_saida>=22){
						    	System.out.println("opcao 3");
						    	horas_tot=horas_tot-10;
						    	horas_noturnas=horas_noturnas + (hh_saida - 22);
						    	horas_noturnas=horas_noturnas + (5 - hh_entrada);
						    	min_noturnas=hora_saida.get(Calendar.MINUTE)+hora_entrada.get(Calendar.MINUTE);
						    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
						    	horas_normais=horas_tot-horas_noturnas;
						    }else if(hh_entrada>=18){
						    	System.out.println("opcao 4");
						    	//horas_tot=hh_saida-hh_entrada;
						    	if(hh_saida>=22) {
						    		System.out.println("opcao 4.1");
						    		horas_noturnas=horas_noturnas + (hh_saida - 22);
						    		min_noturnas=hora_saida.get(Calendar.MINUTE);
							    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
							    	horas_normais=horas_tot-horas_noturnas;
						    	}else {
						    		System.out.println("opcao 4.2");
						    		//System.out.println((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)));
						    		horas_normais=horas_tot+((Math.abs(hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE))) / 60.0);
						    	}
						    	
						    }else {
						    	System.out.println("opcao 5");
						    	if(hh_saida>=18 && hh_saida<=22) {
						    		System.out.println("opcao 5.1");
						    		horas_noturnas=0;
						    		horas_normais=hh_saida-18;
						    		min_noturnas=hora_saida.get(Calendar.MINUTE);
						    		horas_normais=horas_normais+(min_noturnas / 60.0);
						    	}
						    }
						    
						    System.out.println("Analisando HH para "+usuario);
						    System.out.println("Hora Entrada mais cedo "+f3.format(hora_entrada.getTime()));
						    System.out.println("Hora Entrada maid tardia "+f3.format(hora_saida.getTime()));
						    System.out.println("Total "+numberFormat.format(horas_tot));
							System.out.println("Total de Horas Extras Normais "+numberFormat.format(horas_normais));
							System.out.println("Total de horas noturnas "+numberFormat.format(horas_noturnas));
						if(horas_normais>0 || horas_noturnas>0) {
						
						query="SELECT * FROM horas_extras WHERE id_usuario='"+usuario+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"' order by he_data desc limit 1";
						rs=conn.Consulta(query);
						
						
						if(rs.next()) {
							query2="update horas_extras set he_quantidade="+numberFormat.format(horas_normais)+",horas_noturnas="+numberFormat.format(horas_noturnas)+",entrada='"+f3.format(hora_entrada.getTime())+"',saida='"+f3.format(hora_saida.getTime())+"',origen='Automatico - MSTP WEB',aprovada='Y',compensada='N' where id_usuario='"+usuario+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
							conn.Alterar(query2);
						}else {
							insere="";
							insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add) values('"+usuario+"','"+f2.format(time)+"',"+numberFormat.format(horas_normais)+",'"+f3.format(hora_entrada.getTime())+"','"+f3.format(hora_saida.getTime())+"',"+numberFormat.format(horas_noturnas)+",'Y','MANUAL - MSTP WEB','N','"+f3.format(time)+"')";
							
							conn.Inserir_simples(insere);
						
						}
						}}}
						 catch (ParseException e) {
						    e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}		
	 public String[] calcula_hh(String entrada,String saida) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//DecimalFormat twoDForm = new DecimalFormat("##.##");
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
			DecimalFormat twoDForm = (DecimalFormat)nf;
			twoDForm.applyPattern("#0.00");
			Date d1 = null;
			Date d2 = null;
			//String msg="";
			String[] retorno=new String[4];
			try {
				d1 = format.parse(entrada);
				d2= format.parse(saida);
				Calendar hora_saida=Calendar.getInstance();
			    hora_saida.setTime(d2);
			    Calendar hora_entrada=Calendar.getInstance();
			    hora_entrada.setTime(d1);
			    int aux_hora_entrada=hora_entrada.get(Calendar.HOUR_OF_DAY);
				int aux_min_entrada=hora_entrada.get(Calendar.MINUTE);
				double total_hora_entrada=aux_hora_entrada + (aux_min_entrada /60.0);
				int aux_hora_saida=hora_saida.get(Calendar.HOUR_OF_DAY);
				int aux_min_saida=hora_saida.get(Calendar.MINUTE);
				double total_hora_saida=aux_hora_saida + (aux_min_saida / 60.0);
				double total_horas=total_hora_saida-total_hora_entrada;
				double horas_extras=0.0;
				double horas_extras_noturnas=0.0;
				if(hora_entrada.get(Calendar.DAY_OF_WEEK)!=1 && hora_entrada.get(Calendar.DAY_OF_WEEK)!=7) {
					if(total_horas>10.0) {
						horas_extras=total_horas-10.0;
						horas_extras_noturnas=0.0;
						if(total_hora_saida>22.0 && total_hora_saida<23.59) {
							horas_extras_noturnas=total_hora_saida-22.0;
							horas_extras=horas_extras-horas_extras_noturnas;
						}
					}else {
						horas_extras=total_horas-10.0;
						horas_extras_noturnas=0.0;
					}
					retorno[0]="Banco";
					retorno[1]=String.valueOf(twoDForm.format(horas_extras));
					retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
					retorno[3]="";
					//msg="HE:" + String.valueOf(twoDForm.format(horas_extras)) + " | HEN:" + String.valueOf(twoDForm.format(horas_extras_noturnas));
				}else {
					 
						horas_extras=total_horas-1.2;
						horas_extras_noturnas=0.0;
						if(total_hora_saida>22.0 && total_hora_saida<23.59) {
							horas_extras_noturnas=total_hora_saida-22.0;
							horas_extras=horas_extras-horas_extras_noturnas;
						}
					
					retorno[0]="Hora Extra";
					retorno[1]=String.valueOf(twoDForm.format(horas_extras));
					retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
					if(hora_entrada.get(Calendar.DAY_OF_WEEK)==7) {
						retorno[3]="Sábado";
					}else {
						retorno[3]="Domingo";
					}
					//msg="BH:" + String.valueOf(twoDForm.format(horas_extras+horas_extras_noturnas));
					 }
				
				return retorno;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
}
