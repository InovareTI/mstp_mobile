package servlets;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONException;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import Classes.Conexao;
import Classes.ConexaoMongo;
import Classes.Feriado;
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
	    	
		 String opt;
		 HttpSession session = req.getSession(true);
		 Pessoa p= (Pessoa) session.getAttribute("pessoa");
		 opt=req.getParameter("opt");
		 if(p==null || p.equals(null)) {
			 System.out.println("pessoa null com opcao:"+opt);
			 return;
		 }
		 ResultSet rs ;
			ResultSet rs2,rs3 ;
			String dados_tabela;
			
			String insere;
			String param1;
			String param2;
			String param3;
			String param4;
			String param5;
			String param6;
			String param7;
			
			String query;
			String query2 = "";
			
			String aux;
			
			String array_string_aux[];
			
			param1="";
			query="";
			param2="";
			param3="";
			param4="";
			param5="";
			param6="";
			param7="";
			
			
			aux="";
			insere="";
			opt="";
			dados_tabela="";
			
			
			Locale locale_ptBR = new Locale( "pt" , "BR" ); 
			Locale.setDefault(locale_ptBR);
			
			Conexao mysql = (Conexao) session.getAttribute("conexao");
			ConexaoMongo mongo = new ConexaoMongo();
			
			Feriado feriado= new Feriado();
			//Cliente c= new Cliente();
			//Agenda agenda=new Agenda();
			Calendar d = Calendar.getInstance();
			
			d.get(Calendar.MONTH);
			
			//System.out.println(data.);
			Locale brasil = new Locale("pt", "BR");
			DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, brasil);
			DateFormat f3 = DateFormat.getDateTimeInstance();
			Timestamp time = new Timestamp(System.currentTimeMillis());
			java.sql.Date date_sql = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			opt=req.getParameter("opt");
			//System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" acessando servlet de operações opt -  "+ opt);
			try {
				//System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" acessando servlet de operações opt -  "+ opt);
				//System.out.println(p.get_PessoaUsuario()+" - Chegou no servlet de Operações do MSTP Mobile - "+f3.format(time)+" Opção:"+opt);
				if(opt.equals("1")) {
					//System.out.println("Inserindo Registro - "+f3.format(time));
					
					Document registro=new Document();
					Document geo = new Document();
					Document geometry = new Document();
					Document properties = new Document();
					param1=req.getParameter("latitudemobile");
					param2=req.getParameter("longitudemobile");
					param3=req.getParameter("timestam");
					param4=req.getParameter("distancia");
					param5=req.getParameter("datetime");
					String [] HH=new String[4];
					param7=req.getParameter("localidade");
					param6=req.getParameter("_");
					array_string_aux=param7.split(",");
					UUID chave;
					//System.out.println("valor do param7 é :" +param7);
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
					tipo_registro=array_string_aux[3].trim();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
						if(tipo_registro.equals("Entrada")) {
						   chave=UUID.randomUUID();
						}else {
							query="";
							query="select * from registros where usuario='"+p.get_PessoaUsuario()+"' and local_registro<>'PontoAjustado'  and almoco_retorno<>'PTAJ' and tipo_registro in('Entrada','Inicio_Intervalo','Fim_Intervalo','Saída') order by datetime_servlet desc limit 1";
							rs=mysql.Consulta(query);
							if(rs.next()) {
								Calendar ultimo_reg = Calendar.getInstance();
								ultimo_reg.setTime(format.parse(rs.getString("datetime_servlet")));
								Calendar agora = Calendar.getInstance();
								Long horas=TimeUnit.MILLISECONDS.toHours(agora.getTimeInMillis() - ultimo_reg.getTimeInMillis());
								if(horas<10) {
									if(!rs.getString("chave_registros").equals("0")){
										chave=UUID.fromString(rs.getString("chave_registros"));
									}else {
										chave=UUID.randomUUID();
									}
								}else {
									chave=UUID.randomUUID();
								}
							}else {
								chave=UUID.randomUUID();
							}
						}
					insere="";
	    				insere="INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes,timeStamp_mobile,chave_registros) VALUES ('1','"+p.getEmpresa()+"','"+p.get_PessoaUsuario()+"','"+param1+"','"+param2+"','"+f2.format(time)+"',"+param4+",'"+param5+"','"+time+"',"+aux_hora+","+aux_min+",'"+tipo_registro+"','"+array_string_aux[0]+"','"+array_string_aux[1]+"','"+array_string_aux[2]+"',"+(d.get(Calendar.MONTH)+1)+",'"+param3+"','"+chave+"')";
	    				//System.out.println(insere);
	    				if(mysql.Inserir_simples(insere)){
	    					geo.append("type", "Feature");
	    					geo.append("user_tipo", p.get_PessoaTipo());
							geometry.append("type", "Point");
							geometry.append("coordinates", verifica_coordenadas(param2,param1));
							geo.append("geometry",geometry);
							properties.append("Usuario", p.get_PessoaUsuario());
							properties.append("Local_Registro", array_string_aux[1]);
							properties.append("Tipo_local", array_string_aux[0]);
							properties.append("Hora_Registro", f3.format(mobile_time.getTime()));
							properties.append("Distancia_local", param4);
							properties.append("Coordenadas", param1+","+param2);
							geo.append("properties", properties);
	    					registro.append("Usuario", p.get_PessoaUsuario());
	    					registro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
	    					registro.append("data_dia", time);
	    					registro.append("data_dia_string", f2.format(mobile_time.getTime()));
	    					registro.append("datetime_mobile", f3.format(mobile_time.getTime()));
	    					//System.out.println("hora formartada:"+ checa_formato_data_e_hora(f3.format(mobile_time.getTime())));
	    					registro.append("datetime_mobile_data", checa_formato_data_e_hora(f3.format(mobile_time.getTime())));
	    					registro.append("datetime_servlet", time);
	    					registro.append("hora", mobile_time.get(Calendar.HOUR_OF_DAY));
	    					registro.append("minuto", mobile_time.get(Calendar.MINUTE));
	    					registro.append("dia", mobile_time.get(Calendar.DAY_OF_MONTH));
	    					registro.append("mes", (mobile_time.get(Calendar.MONTH)+1));
	    					registro.append("ano", mobile_time.get(Calendar.YEAR));
	    					registro.append("tipo_registro", tipo_registro);
	    					registro.append("local_registro", array_string_aux[0]);
	    					registro.append("distancia", param4);
	    					registro.append("tipo_local_registro", array_string_aux[1]);
	    					registro.append("site_operadora_registro", array_string_aux[2]);
	    					registro.append("timeStamp_mobile", param3);
	    					registro.append("chave_registros", chave);
	    					registro.append("GEO", geo);
				    		mongo.InserirSimpels("Registros", registro);
				    		rs=mysql.Consulta("Select * from registros order by id_sistema desc limit 1");
				    		if(rs.next()){
				    			
				    			resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								//System.out.println("Registro tipo: "+tipo_registro);
								out.print(tipo_registro+";"+rs.getString("datetime_mobile"));
				    		}
				    		//envia_mensagem();
				    	
		    			}
	    				if(tipo_registro.equals("Saída")) {
	    					String saida;
	    					String entrada;
	    					String HH_tipo="";
	    					time = new Timestamp(System.currentTimeMillis());
	    					NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
    						DecimalFormat numberFormat = (DecimalFormat)nf;
    						numberFormat.applyPattern("#0.00");
    						
    						Date d1 = null;
	    					//Date d2 = null;
	    					Calendar entrada_cal=Calendar.getInstance();
	    					Calendar saida_cal=Calendar.getInstance();
	    					System.out.println("Analisando horas extras de "+p.get_PessoaUsuario());
	    					try {
	    					query="SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
							rs2=mysql.Consulta(query);
							if(rs2.next()) {
								entrada=rs2.getString("datetime_servlet");
								d1= format.parse(entrada);
								entrada_cal.setTime(d1);
							}else {
								entrada="";
							}
							query="SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
							rs2=mysql.Consulta(query);
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
							if(Double.parseDouble(HH[1])>0.2) {
								query="SELECT * FROM horas_extras WHERE id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"' order by he_data desc limit 1";
		    					rs=mysql.Consulta(query);
		    					if(HH[3].equals("Sábado") || HH[3].equals("Domingo")) {
	    							HH_tipo="Horas Extra";
	    						}else {
	    							HH_tipo="Banco";
	    						}
		    					
		    					if(rs.next()) {
		    						query2="update horas_extras set he_quantidade="+numberFormat.format(Double.parseDouble(HH[1]))+",horas_noturnas="+numberFormat.format(Double.parseDouble(HH[2]))+",entrada='"+f3.format(entrada_cal.getTime())+"',saida='"+f3.format(saida_cal.getTime())+"',origen='Automatico - MSTP MOBILE',aprovada='N',compensada='N',tipo_HH='"+HH_tipo+"',mes_HH='"+(entrada_cal.get(Calendar.MONTH)+1)+"' where id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
		    						mysql.Alterar(query2);
		    					}else {
		    						
		    						rs2=mysql.Consulta("select autoriza_previa_he from expediente where empresa="+p.getEmpresaObj().getEmpresa_id()+" and dia_expediente="+entrada_cal.get(Calendar.DAY_OF_WEEK));
		    						if(rs2.next()) {
		    							if(rs2.getString(1).equals("true")) {
		    								Bson filtro;
		    								List<Bson> filtros= new ArrayList<>();
		    								filtro=Filters.eq("usuario_solicitante",p.get_PessoaUsuario());
		    								filtros.add(filtro);
		    								filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
		    								filtros.add(filtro);
		    								filtro=Filters.eq("data_dia_he",f2.format(time));
		    								filtros.add(filtro);
		    								filtro=Filters.eq("status_autorizacao","APROVADO");
		    								filtros.add(filtro);
		    								FindIterable<Document> busca_autorizacao = mongo.ConsultaCollectioncomFiltrosLista("Autoriza_HE", filtros);
		    								MongoCursor<Document> resultado = busca_autorizacao.iterator();
		    								if(resultado.hasNext()) {
		    									insere="";
		    		    						
		    		    						insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add,tipo_HH,mes_HH,empresa) values('"+p.get_PessoaUsuario()+"','"+f2.format(time)+"',"+numberFormat.format(Double.parseDouble(HH[1]))+",'"+f3.format(entrada_cal.getTime())+"','"+f3.format(saida_cal.getTime())+"',"+numberFormat.format(Double.parseDouble(HH[2]))+",'N','Automatico - MSTP MOBILE','N','"+f3.format(time)+"','"+HH_tipo+"','"+entrada_cal.get(Calendar.MONTH)+1+"',"+p.getEmpresaObj().getEmpresa_id()+")";
		    		    						
		    		    						mysql.Inserir_simples(insere);
		    								}else {
		    									mysql.Inserir_simples("insert into log_mstp (usuario,operacao,dt_oper) values ('"+req.getParameter("user")+"','Saida SEM Autorização de HE','"+time+"');");
		    								}
		    							}else {
		    								insere="";
				    						
				    						insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add,tipo_HH,mes_HH,empresa) values('"+p.get_PessoaUsuario()+"','"+f2.format(time)+"',"+numberFormat.format(Double.parseDouble(HH[1]))+",'"+f3.format(entrada_cal.getTime())+"','"+f3.format(saida_cal.getTime())+"',"+numberFormat.format(Double.parseDouble(HH[2]))+",'N','Automatico - MSTP MOBILE','N','"+f3.format(time)+"','"+HH_tipo+"','"+entrada_cal.get(Calendar.MONTH)+1+"',"+p.getEmpresaObj().getEmpresa_id()+")";
				    						
				    						mysql.Inserir_simples(insere);
		    							}
		    						}
		    						
		    					
		    					}
							}else if(Double.parseDouble(HH[1])<0){
								query="SELECT * FROM horas_extras WHERE id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"' order by he_data desc limit 1";
								
								rs=mysql.Consulta(query);
		    					if(HH[3].equals("Sábado") || HH[3].equals("Domingo")) {
	    							HH_tipo="Horas Extra";
	    						}else {
	    							HH_tipo="Banco";
	    						}
		    					
		    					if(rs.next()) {
		    						query2="update horas_extras set he_quantidade="+numberFormat.format(Double.parseDouble(HH[1]))+",horas_noturnas="+numberFormat.format(Double.parseDouble(HH[2]))+",entrada='"+f3.format(entrada_cal.getTime())+"',saida='"+f3.format(saida_cal.getTime())+"',origen='Automatico - MSTP MOBILE',aprovada='Y',compensada='N',tipo_HH='"+HH_tipo+"',mes_HH='"+(entrada_cal.get(Calendar.MONTH)+1)+"' where id_usuario='"+p.get_PessoaUsuario()+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
		    						mysql.Alterar(query2);
		    					}else {
		    						rs2=mysql.Consulta("select autoriza_previa_he from expediente where empresa="+p.getEmpresaObj().getEmpresa_id()+" and dia_expediente="+entrada_cal.get(Calendar.DAY_OF_WEEK));
		    						if(rs2.next()) {
		    							if(rs2.getString(1).equals("true")) {
		    								Bson filtro;
		    								List<Bson> filtros= new ArrayList<>();
		    								filtro=Filters.eq("usuario_solicitante",p.get_PessoaUsuario());
		    								filtros.add(filtro);
		    								filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
		    								filtros.add(filtro);
		    								filtro=Filters.eq("data_dia_he",f2.format(time));
		    								filtros.add(filtro);
		    								filtro=Filters.eq("status_autorizacao","APROVADO");
		    								filtros.add(filtro);
		    								FindIterable<Document> busca_autorizacao = mongo.ConsultaCollectioncomFiltrosLista("Autoriza_HE", filtros);
		    								MongoCursor<Document> resultado = busca_autorizacao.iterator();
		    								if(resultado.hasNext()) {
		    									insere="";
		    		    						insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add,tipo_HH,mes_HH,empresa) values('"+p.get_PessoaUsuario()+"','"+f2.format(time)+"',"+numberFormat.format(Double.parseDouble(HH[1]))+",'"+f3.format(entrada_cal.getTime())+"','"+f3.format(saida_cal.getTime())+"',"+numberFormat.format(Double.parseDouble(HH[2]))+",'N','Automatico - MSTP MOBILE','N','"+f3.format(time)+"','"+HH_tipo+"','"+entrada_cal.get(Calendar.MONTH)+1+"',"+p.getEmpresaObj().getEmpresa_id()+")";
		    		    						mysql.Inserir_simples(insere);
		    								}else {
		    									mysql.Inserir_simples("insert into log_mstp (usuario,operacao,dt_oper) values ('"+req.getParameter("user")+"','Saida SEM Autorização de HE','"+time+"');");
		    								}
		    							}else {
		    								insere="";
				    						insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add,tipo_HH,mes_HH,empresa) values('"+p.get_PessoaUsuario()+"','"+f2.format(time)+"',"+numberFormat.format(Double.parseDouble(HH[1]))+",'"+f3.format(entrada_cal.getTime())+"','"+f3.format(saida_cal.getTime())+"',"+numberFormat.format(Double.parseDouble(HH[2]))+",'N','Automatico - MSTP MOBILE','N','"+f3.format(time)+"','"+HH_tipo+"','"+entrada_cal.get(Calendar.MONTH)+1+"',"+p.getEmpresaObj().getEmpresa_id()+")";
				    						mysql.Inserir_simples(insere);
		    							}
		    						}
		    						
		    					
		    					}
		    					
							}
							
							rs=mysql.Consulta("select count(distinct tipo_registro) from registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro in ('Entrada','Saída','Fim_intervalo','Inicio_intervalo')");
							if(rs.next()) {
								if(rs.getInt(1)>=4) {
									envia_mensagem("MSTP Mobile: \n Uau mandou bem. Seus Registros estão corretos! :-)", p.get_PessoaUsuario());
								}
							}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    					
	    				}
	    				
	    				mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
	    				
	    				
	    				
				}else if(opt.equals("2")) {
					param1=req.getParameter("func");
					//System.out.println("funcionario é " + param1);
					
					query="select * from registros where usuario='"+param1+"' and local_registro<>'PontoAjustado'  and almoco_retorno<>'PTAJ' and tipo_registro in('Entrada','Inicio_Intervalo','Fim_Intervalo','Saída') order by datetime_servlet desc limit 1";
					rs=mysql.Consulta(query);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(rs.next()) {
						Calendar ultimo_reg = Calendar.getInstance();
						ultimo_reg.setTime(format.parse(rs.getString("datetime_servlet").substring(0, 19)));
						Calendar agora = Calendar.getInstance();
						Long horas=TimeUnit.MILLISECONDS.toHours(agora.getTimeInMillis() - ultimo_reg.getTimeInMillis());
						if(horas<10) {
							if(param1.equals("todos")) {
								query="select * from registros order by datetime_servlet desc";
							}else {
								query="select * from registros where usuario='"+param1+"' and chave_registros='"+rs.getString("chave_registros")+"' order by datetime_servlet desc";
							}
							rs=mysql.Consulta(query);
							if(rs.next()) {
								aux=rs.getString("data_dia");
								
								dados_tabela="<ons-list>"+"\n";
								dados_tabela=dados_tabela+"<ons-list-header>"+rs.getString("data_dia")+"</ons-list-header>"+"\n";
								rs2=mysql.Consulta("select * from usuarios where id_usuario='"+rs.getString("usuario")+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'");
								if(rs2.next()) {
								rs.beforeFirst();
								while(rs.next()) {
									if(aux.equals(rs.getString("data_dia"))){
									
										dados_tabela=dados_tabela+
												"<ons-list-item id=\""+rs.getInt(1)+"\">Nome:"+rs2.getString("nome")+" <br>CPF:"+rs2.getString("cpf")+" <br>PIS:"+rs2.getString("pis")+" <br>GPS:"+rs.getString("latitude")+" , "+rs.getString("longitude")+" <br> "+rs.getString("local_registro")+" : "+rs.getString("tipo_local_registro")+"<br>Operadora: "+rs.getString("site_operadora_registro")+"<br>Distancia: "+rs.getInt("distancia")+" km <br> Data e hora: "+rs.getString("datetime_servlet")+"<br>Tipo Registro: "+rs.getString("tipo_registro")+"<br>Empresa:"+p.getEmpresaObj().getNome()+"<br>"+p.getEmpresaObj().getEndereco()+"</ons-list-item>"+"\n";
										
										}else {
											dados_tabela=dados_tabela+"<ons-list-header>"+rs.getString("data_dia")+"</ons-list-header>"+"\n";
											dados_tabela=dados_tabela+
													"<ons-list-item id=\""+rs.getInt(1)+"\">Nome:"+rs2.getString("nome")+" <br>CPF:"+rs2.getString("cpf")+" <br>PIS:"+rs2.getString("pis")+" <br>GPS:"+rs.getString("latitude")+" , "+rs.getString("longitude")+" <br> "+rs.getString("local_registro")+" : "+rs.getString("tipo_local_registro")+"<br>Operadora: "+rs.getString("site_operadora_registro")+"<br>Distancia: "+rs.getInt("distancia")+" km <br> Data e hora: "+rs.getString("datetime_servlet")+"<br>Tipo Registro: "+rs.getString("tipo_registro")+"<br>Empresa:"+p.getEmpresaObj().getNome()+"<br>"+p.getEmpresaObj().getEndereco()+"</ons-list-item>"+"\n";
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
						}else {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("<ons-list></ons-list>");
						}
					}
					
					
					mongo.fecharConexao();
    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
				}else if(opt.equals("3")) {
					//System.out.println("Alterando Expediente");
					param1=req.getParameter("entrada");
					param2=req.getParameter("saida");
					query="update expediente set entrada='"+param1+"',saida='"+param2+"'";
					if(mysql.Update_simples(query)) {
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Dados Alterados com sucesso!");
					}
					mongo.fecharConexao();
    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("4")) {
						//System.out.println("Buscando Expediente");
						query="select * from expediente where empresa="+p.getEmpresaObj().getEmpresa_id();
						rs=mysql.Consulta(query);
						if(rs.next()) {
							dados_tabela="[[\""+rs.getString("entrada")+"\"],[\""+rs.getString("saida")+"\"]";
							//
							
						}
						query="select entrada,Ini_inter,Fim_inter,saida from registro_foto_controle where empresa="+p.getEmpresaObj().getEmpresa_id();
						rs=mysql.Consulta(query);
						if(rs.next()) {
							dados_tabela=dados_tabela+",[\""+rs.getString(1)+"\"],";
							dados_tabela=dados_tabela+"[\""+rs.getString(2)+"\"],";
							dados_tabela=dados_tabela+"[\""+rs.getString(3)+"\"],";
							dados_tabela=dados_tabela+"[\""+rs.getString(4)+"\"]";
						}
						dados_tabela=dados_tabela+"]";
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("5")) {
						param1=req.getParameter("tipo");
						if(param1.equals("reg_distancia")) {
						if(p.getPerfil_funcoes().contains("DistanciaController")) {
						param1=req.getParameter("hora_extra");
						param2=req.getParameter("banco");
						param3=req.getParameter("reg_distancia");
						param4=req.getParameter("vinculo");
						query="update expediente set hora_extra='"+param1+"',banco_de_hora='"+param2+"',reg_distancia='"+param3+"',vinculo='"+param4+"' where empresa="+p.getEmpresaObj().getEmpresa_id();
						if(mysql.Update_simples(query)) {
							mysql.Inserir_simples("insert into log_mstp (usuario,operacao,dt_oper) values ('"+p.get_PessoaUsuario()+"','ALTEROU PARAMETROS DE HORA EXTRA,BANCO,VINCULO,DISTANCIA','"+time+"');");
							query="update usuarios set controle_vinculo='"+param4+"' where empresa='"+p.getEmpresa()+"'";
							mysql.Update_simples(query);
							//System.out.println(query);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Dados Alterados com sucesso!");
						}
						mongo.fecharConexao();
						}else {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Sem Privilégio para essa Operação");
						}
						}else if(param1.equals("reg_foto")) {
							param1=req.getParameter("entrada");
							param2=req.getParameter("iniInter");
							param3=req.getParameter("fimInter");
							param4=req.getParameter("saida");
							query="update registro_foto_controle set Entrada='"+param1+"',Ini_Inter='"+param2+"',Fim_inter='"+param3+"',Saida='"+param4+"',Alter_by='"+p.get_PessoaUsuario()+"',Alter_dt='"+time+"' where Empresa="+p.getEmpresaObj().getEmpresa_id();
							if(mysql.Update_simples(query)) {
								mysql.Inserir_simples("insert into log_mstp (usuario,operacao,dt_oper) values ('"+p.get_PessoaUsuario()+"','ALTEROU PARAMETROS DE FOTO PARA REGISTRO','"+time+"');");
								query="update usuarios set controle_vinculo='"+param4+"' where empresa='"+p.getEmpresa()+"'";
								mysql.Update_simples(query);
								//System.out.println(query);
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Dados Alterados com sucesso!");
							}
							mongo.fecharConexao();
							
						}else if(param1.equals("autorizacao_hora_extra")) {
							param1=req.getParameter("autorizacao_controle");
							
							query="update expediente set autoriza_previa_he='"+param1+"' where empresa="+p.getEmpresaObj().getEmpresa_id();
							if(mysql.Update_simples(query)) {
								mysql.Inserir_simples("insert into log_mstp (usuario,operacao,dt_oper) values ('"+p.get_PessoaUsuario()+"','ALTEROU PARAMETROS DE AUTORIZACAO PREVIA','"+time+"');");
								
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Dados Alterados com sucesso!");
							}
							mongo.fecharConexao();
						}
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("6")) {
						//System.out.println("Buscando Expediente");
						query="select * from expediente where empresa="+p.getEmpresaObj().getEmpresa_id();
						rs=mysql.Consulta(query);
						if(rs.next()) {
							dados_tabela="[["+rs.getString("hora_extra")+"],["+rs.getString("banco_de_hora")+"],["+rs.getString("reg_distancia")+"],["+rs.getString("vinculo")+"],["+rs.getString("autoriza_previa_he")+"]";
							//System.out.println(dados_tabela);
							
						}
						query="select entrada,Ini_inter,Fim_inter,saida from registro_foto_controle where empresa="+p.getEmpresaObj().getEmpresa_id();
						rs=mysql.Consulta(query);
						if(rs.next()) {
							dados_tabela=dados_tabela+",[\""+rs.getString(1)+"\"],";
							dados_tabela=dados_tabela+"[\""+rs.getString(2)+"\"],";
							dados_tabela=dados_tabela+"[\""+rs.getString(3)+"\"],";
							dados_tabela=dados_tabela+"[\""+rs.getString(4)+"\"]";
						}
						dados_tabela=dados_tabela+"]";
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("7")) {
						param1=req.getParameter("versao_atual");
						//System.out.println("Buscando Versao");
						query="select * from versao order by versao_atual desc limit 1";
						rs=mysql.Consulta(query);
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
						insere="insert into pontos (latitude_ponto,longitude_ponto,nome_ponto,usuario_registro,ativo,empresa) values('"+param1+"','"+param2+"','"+param7+"','"+param6+"','Y',"+p.getEmpresaObj().getEmpresa_id()+")";
						if(mysql.Inserir_simples(insere)) {
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("9")) {
						//System.out.println("Buscando Funcionários");
						if(p.get_PessoaPerfil().equals("ADM")) {
							rs=mysql.Consulta("select distinct usuario from registros where empresa='"+p.getEmpresaObj().getEmpresa_id()+"'");
						}else {
							rs=mysql.Consulta("select distinct usuario from registros where usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'");
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("10")) {
						//System.out.println("Encerrando sessão de "+p.get_PessoaUsuario() +" em  - "+f3.format(time));
						mysql.fecharConexao();
						session.invalidate();
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("11")) {
						String ponto="";
						query="select * from pontos where ativo='Y' and empresa="+p.getEmpresaObj().getEmpresa_id();
						param1=req.getParameter("usuario");
						rs=mysql.Consulta(query);
						rs2=mysql.Consulta("select id_ponto from usuario_ponto where id_usuario='"+param1+"'");
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("12")) {
						param1=req.getParameter("usuario");
						param2=req.getParameter("ponto");
						//param3=req.getParameter("timestam");
						rs=mysql.Consulta("select * from usuario_ponto where id_usuario='"+param1+"'");
						if(rs.next()) {
							if(mysql.Update_simples("update usuario_ponto set id_ponto='"+param2+"' where id_usuario='"+param1+"'")){
								rs2=mysql.Consulta("select * from pontos where id_ponto="+param2);
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
							if(mysql.Inserir_simples("insert into usuario_ponto (id_usuario,id_ponto) values ('"+param1+"','"+param2+"')")) {
								rs2=mysql.Consulta("select * from pontos where id_ponto="+param2);
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("13")) {
						param1=req.getParameter("usuario");
						param2=req.getParameter("ponto");
						if(p.get_PessoaPerfil().equals("ADM")) {
							rs=mysql.Consulta("select * from usuario_ponto where id_ponto='"+param2+"'");
							if(rs.next()) {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Operação nao pode ser executada enquanto houver usuarios ativos nesse ponto!");
							}else {
								if(mysql.Update_simples("update  pontos set ativo='N' where id_ponto="+param2)) {
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("14")) {
						param1=req.getParameter("usuario");
						param2=req.getParameter("ponto");
						query="select * from horas_extras where id_usuario='"+param1+"' and aprovada='Y' and compensada='N'";
						rs=mysql.Consulta(query);
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("15")) {
						
						//mongo.criar2dsphereindex();
						//CorrigeCoordenadasTabelaSites();
						Bson filtro;
						Document site = new Document();
						List<Bson> lista_filtro = new ArrayList<>();
						param2=req.getParameter("operadora");
						param3=req.getParameter("latitude");
						param4=req.getParameter("longitude");
						//Point refPoint = new Point(new Position(Double.parseDouble(param4), Double.parseDouble(param3)));
						
						
						System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" realizando pesquisa de sites próximas  "+ param2);
						FindIterable<Document> findIterable;
						if(param2.contains(";")) {
							
						String[]aux1=param2.split(";");
						filtro = Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
						lista_filtro.add(filtro);
						filtro = Filters.eq("site_operadora",aux1[0]);
						lista_filtro.add(filtro);
						filtro = Filters.eq("site_uf",aux1[1]);
						lista_filtro.add(filtro);
						filtro = Filters.eq("site_ativo","Y");
						lista_filtro.add(filtro);
						
						//System.out.println(aux1[0]);
						//System.out.println(aux1[1]);
						if(aux1[0].toUpperCase().equals("VIVO") || aux1[0].toUpperCase().equals("TIM") || aux1[0].toUpperCase().equals("OI") || aux1[0].toUpperCase().equals("CLARO") || aux1[0].toUpperCase().equals("NEXTEL")) {
							AggregateIterable aggregateIterable= mongo.ConsultaSitecomFiltrosListaAggregation(param4,param3,lista_filtro);
							MongoCursor<Document> resultado = aggregateIterable.iterator();
							if(resultado.hasNext()) {
								dados_tabela="[";
								//rs.beforeFirst();
								while(resultado.hasNext()) {
										site = resultado.next();
										//System.out.println(site.toJson());
										dados_tabela=dados_tabela+"[\""+site.getString("site_id")+"\",\""+site.getString("site_latitude").replace(",", ".")+"\",\""+site.getString("site_longitude").replace(",", ".")+"\",\""+site.getString("site_operadora")+"\",[\""+site.getString("site_id")+"\",\""+Math.round(site.get("dist",Document.class).getDouble("calculated"))+"m\"]],\n";
										
									
								}
								dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
								dados_tabela=dados_tabela+"]";
								//System.out.println(dados_tabela);
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
						}else {
							lista_filtro.clear();
							filtro = Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
							lista_filtro.add(filtro);
							filtro = Filters.regex("site_id", ".*"+aux1[0]+".*");
							lista_filtro.add(filtro);
							findIterable= mongo.ConsultaCollectioncomFiltrosLista("sites", lista_filtro);
							MongoCursor<Document> resultado = findIterable.iterator();
							if(resultado.hasNext()) {
								dados_tabela="[";
								//rs.beforeFirst();
								while(resultado.hasNext()) {
										site = resultado.next();
										dados_tabela=dados_tabela+"[\""+site.getString("site_id")+"\",\""+site.getString("site_latitude").replace(",", ".")+"\",\""+site.getString("site_longitude").replace(",", ".")+"\",\""+site.getString("site_operadora")+"\",[\""+site.getString("site_id")+"\",\"Distancia ñ calculada\"]],\n";
										
									
								}
								dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
								dados_tabela=dados_tabela+"]";
								//System.out.println(dados_tabela);
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
						}
						}else {
							lista_filtro.clear();
							filtro = Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
							lista_filtro.add(filtro);
							filtro = Filters.regex("site_id", ".*"+param2.toUpperCase()+".*");
							lista_filtro.add(filtro);
							findIterable= mongo.ConsultaCollectioncomFiltrosLista("sites", lista_filtro);
							MongoCursor<Document> resultado = findIterable.iterator();
							if(resultado.hasNext()) {
								dados_tabela="[";
								//rs.beforeFirst();
								while(resultado.hasNext()) {
										site = resultado.next();
										dados_tabela=dados_tabela+"[\""+site.getString("site_id")+"\",\""+site.getString("site_latitude").replace(",", ".")+"\",\""+site.getString("site_longitude").replace(",", ".")+"\",\""+site.getString("site_operadora")+"\",[\""+site.getString("site_id")+"\",\"Distancia ñ calculada\"]],\n";
										
									
								}
								dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
								dados_tabela=dados_tabela+"]";
								//System.out.println(dados_tabela);
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
						}
						
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("16")) {
						//System.out.println("Carregando escritorios no mapa");
						rs=mysql.Consulta("Select * from pontos where ativo ='Y' and latitude_ponto<>'' and longitude_ponto<>'' and empresa="+p.getEmpresaObj().getEmpresa_id()+" limit 50");
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("17")) {

						param1=req.getParameter("tipo");
						param5=req.getParameter("datetime");
						//System.out.println("Lançando "+param1+" via Mobile para usuário "+p.get_PessoaUsuario() + " - "+f3.format(time));
						if(param1.equals("folga")) {
						query="select id_ajuste_ponto,aprovada from ajuste_ponto where motivo='FOLGA' and other2='"+param5+"' and usuario='"+p.get_PessoaUsuario()+"'";
						rs=mysql.Consulta(query);
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
						if(mysql.Inserir_simples(query)) {
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
							rs=mysql.Consulta(query);
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
							if(mysql.Inserir_simples(query)) {
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("18")) {
						//System.out.println("Buscando Operadoras");
						Bson filtro;
						List<Bson> lista_filtros= new ArrayList<>();
						filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
						lista_filtros.add(filtro);
						filtro=Filters.eq("site_ativo","Y");
						lista_filtros.add(filtro);
						List<String> operadora = mongo.ConsultaSimplesDistinct("sites", "site_operadora", lista_filtros);
						//query="select distinct site_operadora,site_uf from sites where site_uf<>'' and site_operadora<>'' and site_ativo='Y'";
						//System.out.println("quantidade de operadoras:"+operadora.size());
						if(operadora.size()>0) {
							for(int indice=0;indice<operadora.size();indice++) {
								lista_filtros= new ArrayList<Bson>();
								filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
								lista_filtros.add(filtro);
								filtro=Filters.eq("site_ativo","Y");
								lista_filtros.add(filtro);
								filtro=Filters.eq("site_operadora",operadora.get(indice));
								lista_filtros.add(filtro);
								List<String> uf_operadora = mongo.ConsultaSimplesDistinct("sites", "site_uf", lista_filtros);
								if(uf_operadora.size()>0) {
									for(int indice_uf=0;indice_uf<uf_operadora.size();indice_uf++) {
										dados_tabela=dados_tabela+"<ons-card onclick=\"navega('consulta_sites_page');consulta_sites('"+operadora.get(indice)+";"+ uf_operadora.get(indice_uf)+"')\">\n"+
											      "<div class=\"title\">"+operadora.get(indice)+" - "+ uf_operadora.get(indice_uf)+"</div>\n"+
											      "<div class=\"content\">Sites "+operadora.get(indice)+" - "+ uf_operadora.get(indice_uf)+"</div>\n"+
											    "</ons-card>\n";
									}
								}
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("19")){
						double horas;
						query="select sum(he_quantidade),sum(horas_noturnas) from horas_extras where id_usuario='"+p.get_PessoaUsuario()+"' and aprovada='Y' and compensada='N' and other1=''";
						//System.out.println("Consultando Horas de  "+p.get_PessoaUsuario()+" em "+f3.format(time));
						rs=mysql.Consulta(query);
						if(rs.next()) {
							horas=rs.getDouble(1)+rs.getDouble(2);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(horas);
						}
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("20")){
						query="";
						if(p.get_PessoaEmail().contains("BancoHHApprover")) {
							query="select * from horas_extras where aprovada='N'";
							rs=mysql.Consulta(query);
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("21")){
						query="";
						query="select id_message,titulo,message from updates_message where usuario='"+p.get_PessoaUsuario()+"' and messagem_lida='N'";
						rs=mysql.Consulta(query);
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
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("22")){
						param1=req.getParameter("id_msg");
						query="UPDATE updates_message set messagem_lida='Y' where id_message='"+param1+"' and usuario='"+p.get_PessoaUsuario()+"'";
						mysql.Update_simples(query);
							
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
						param3=param3.replace("undefined", Integer.toString(fim.get(Calendar.DAY_OF_MONTH)));
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
									}else if(feriado.verifica_feriado(f2.format(inicio.getTime()), mysql, p)) {
										cor="#0099ff";
									}else if(p.VerificaFolga(p.get_PessoaUsuario(), f2.format(inicio.getTime()), mysql)) {
										cor="#00e600";
										problema="ok";
									}else {
										query="select count(distinct tipo_registro) from registros where data_dia='"+f2.format(inicio.getTime())+"' and usuario='"+p.get_PessoaUsuario()+"' and tipo_registro in ('Entrada','Inicio_intervalo','Fim_intervalo','Saída') limit 4";
										rs=mysql.Consulta(query);
										if(rs.next()) {
											if(rs.getInt(1)==4) {
												problema="ok";
											}else {
												problema="nok";
											}
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
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("24")){
						
						query="select SQL_NO_CACHE reg_distancia,now() as agora from expediente where now()=now()";
						rs=mysql.Consulta(query);
						
						if(rs.next()) {
							//System.out.println("Controle de distancia é: "+rs.getString("reg_distancia"));
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(rs.getString("reg_distancia"));
						}
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("25")){
						param1=req.getParameter("data");
						String mensagem="";
						mensagem="[\"00:01\",\"00:01\",\"00:01\",\"00:01\"]";
						query="select * from registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+param1+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
						//System.out.println(query);
						rs=mysql.Consulta(query);
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
							rs=mysql.Consulta(query);
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
							rs=mysql.Consulta(query);
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
						    rs=mysql.Consulta(query);
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
						
						
						//System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" resultado de busca de registros: "+ mensagem);
						//System.out.println(mensagem)
						resp.setContentType("application/json");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(mensagem);
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
						rs=mysql.Consulta(query);
						if(rs.next()) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Voce já possui uma solicitação aguardando aprovação para esse dia. Aguarde finalização!");
						}else {
						query="insert into ajuste_ponto (dt_solicitado,dt_entrada,dt_saida,local,motivo,aprovada,usuario,empresa,other2,dt_ini_inter,dt_fim_inter) values ('"+f3.format(time)+"','"+param5+" "+param1+":"+numeroAleatorio(10,59)+"','"+param5+" "+param2+":"+numeroAleatorio(10,59)+"','-','"+param7+"','N','"+p.get_PessoaUsuario()+"',"+p.getEmpresa()+",'"+param5+"','"+param5+" "+param3+":"+numeroAleatorio(10,59)+"','"+param5+" "+param4+":"+numeroAleatorio(10,59)+"')";
						if(mysql.Inserir_simples(query)) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							mysql.getConnection().commit();
							out.print("Ajuste Solicitado com Sucesso. Aguarde aprovação");
						}
						}
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("27")){
						resp.setContentType("application/text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Ativo");
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("28")){
						String tipo="";
						String resultado="";
						long daySeconds=0;
						resultado="[";
						String resultado1="[\"\"],";
						String resultado2="[\"\"],";
						String resultado3="[\"\"],";
						String resultado4="[\"\"],";
						long horas = 0;
						Calendar c = Calendar.getInstance();
		                Calendar now = Calendar.getInstance();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						query="SELECT *,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and local_registro<>'PontoAjustado' and almoco_retorno<>'PTAJ' and tipo_registro in('Entrada','Inicio_Intervalo','Fim_Intervalo','Saída') order by datetime_servlet desc limit 1";
						rs=mysql.Consulta(query);
						if(rs.next()) {
							Calendar ultimo_reg = Calendar.getInstance();
							ultimo_reg.setTime(format.parse(rs.getString("datetime_servlet")));
							Calendar agora = Calendar.getInstance();
							horas=TimeUnit.MILLISECONDS.toHours(agora.getTimeInMillis() - ultimo_reg.getTimeInMillis());
							long dif = 0;
		                	
		                	//System.out.println("Ultimo Registro em Horas:"+horas);
							if(horas<10) {
								 query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' and chave_registros='"+rs.getString("chave_registros")+"' order by datetime_servlet asc";
								 rs2=mysql.Consulta(query);
								 if(rs2.next()) {
									 if(rs2.getString("timeStamp_mobile").indexOf(".")>0) {
					                		//System.out.println(rs3.getString("timeStamp_mobile").substring(0, rs3.getString("timeStamp_mobile").indexOf(".")));
					                		c.setTimeInMillis(Long.parseLong(rs2.getString("timeStamp_mobile").substring(0, rs2.getString("timeStamp_mobile").indexOf("."))));
					                		//System.out.println(f3.format(c.getTime()));
					                	}else {
					                		c.setTimeInMillis(Long.parseLong(rs2.getString("timeStamp_mobile")));
					                		//System.out.println(f3.format(c.getTime()));
					                	}
					                	//System.out.println(f3.format(now.getTime()));
									    now = Calendar.getInstance();
					                	dif= now.getTimeInMillis() - c.getTimeInMillis();
					                	//System.out.println(dif);
					                	daySeconds=TimeUnit.MILLISECONDS.toSeconds(dif) ;
									rs2.beforeFirst();
					                while(rs2.next()) {
					                	if(rs2.getString("tipo_registro").equals("Entrada")) {
					                		
					                		resultado1="[\"Entrada\"],";
					                	}
					                	if(rs2.getString("tipo_registro").equals("Inicio_intervalo")) {
					                		 resultado2="[\"Inicio_intervalo\"],";
					                	}
					                	if(rs2.getString("tipo_registro").equals("Fim_intervalo")) {
					                		 resultado3="[\"Fim_intervalo\"],";
					                	}
					                	if(rs2.getString("tipo_registro").equals("Saída")) {
					                		 resultado4="[\"Saída\"],";
					                	}
					                }
								 }else {
									 daySeconds=0;
								 }
							}else {
								daySeconds=0;
							}
						}else {
							daySeconds=0;
						}
					resultado="["+resultado1+resultado2+resultado3+resultado4+"["+daySeconds+"]]";
					 /* query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"'   and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' and chave_registros='"+rs.getString("chave_registros")+"' order by datetime_servlet asc";
					  rs=mysql.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Entrada\"],";
					  }else {
						  resultado=resultado+"[],";
					  }
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Inicio_intervalo' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' order by datetime_servlet desc limit 1";
					  rs=mysql.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Inicio_intervalo\"],";
					  }else {
						  resultado=resultado+"[],";
					  }
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Fim_intervalo' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' order by datetime_servlet desc limit 1";
					  rs=mysql.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Fim_intervalo\"],";
					  }else {
						  resultado=resultado+"[],";
					  }
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Saída' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' order by datetime_servlet desc limit 1";
					  rs=mysql.Consulta(query);
					  if(rs.next()) {
						  resultado=resultado+"[\"Saída\"]";
					  }else {
						  resultado=resultado+"[]";
					  }
					  
					  query="SELECT tipo_registro,timeStamp_mobile,now() FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' order by datetime_servlet desc limit 1";
					  rs=mysql.Consulta(query);
					  if(rs.next()) {
						  tipo=rs.getString("tipo_registro");
						  rs3=mysql.Consulta("SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Entrada' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' order by datetime_servlet asc limit 1");
			               if(rs3.next()) {
			                	long dif = 0;
			                	if(rs3.getString("timeStamp_mobile").indexOf(".")>0) {
			                		System.out.println(rs3.getString("timeStamp_mobile").substring(0, rs3.getString("timeStamp_mobile").indexOf(".")));
			                		c.setTimeInMillis(Long.parseLong(rs3.getString("timeStamp_mobile").substring(0, rs3.getString("timeStamp_mobile").indexOf("."))));
			                		System.out.println(f3.format(c.getTime()));
			                	}else {
			                		c.setTimeInMillis(Long.parseLong(rs3.getString("timeStamp_mobile")));
			                		System.out.println(f3.format(c.getTime()));
			                	}
			                	System.out.println(f3.format(now.getTime()));
			                		dif= now.getTimeInMillis() - c.getTimeInMillis();
			                		System.out.println(dif);
			                	daySeconds=TimeUnit.MILLISECONDS.toSeconds(dif) ;
			                	System.out.println(daySeconds);
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
					     resultado=resultado+",["+daySeconds+"]]";*/
		                 System.out.println(resultado);
		                resp.setContentType("application/text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(resultado);
						mongo.fecharConexao();
	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
						rs=mysql.Consulta(query);
						if(rs.next()) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Site já existente. Solicitação abortada");
						}else {
						query="insert into site_aprova (site_nome,site_id,site_operadora,site_estado,dt_pedido,usuario_pedido,aprovado,empresa,site_lat,site_lng) values ('"+param1+"','"+param2+"','"+param3+"','"+param4+"','"+f3.format(time)+"','"+p.get_PessoaUsuario()+"','N','"+p.getEmpresa()+"','"+param5+"','"+param6+"')";
						if(mysql.Inserir_simples(query)) {
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
					}mongo.fecharConexao();
    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("30")){
							param1=req.getParameter("latitudemobile");
							param2=req.getParameter("longitudemobile");
							param3=req.getParameter("_");
							
							Document document = new Document();
							Document properties = new Document();
							Document geometry = new Document();
							Document geo = new Document();
							geometry.append("type", "Point");
							geometry.append("coordinates", Arrays.asList(Double.parseDouble(param2.replaceAll(",", ".")),Double.parseDouble(param1.replaceAll(",", "."))));
							properties.append("Usuario",p.get_PessoaUsuario());
    						properties.append("Site",time);
    						properties.append("Coordenadas",param2+","+param1);
    						properties.append("Data",time);
    						document.append("Empresa", p.getEmpresaObj().getEmpresa_id());
    						geo.append("type", "Feature");
    						geo.append("geometry", geometry);
    						geo.append("properties", properties);
    						document.append("GEO", geo);
    						mongo.InserirSimpels("Localiza_Usuarios", document);
    						
    						mongo.fecharConexao();
    	    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
    	    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("31")){
							System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" buscando quantidade de atividades");
							System.out.println("******buscando quantidade atividades para "+p.get_PessoaUsuario());
							Long qtde_atividades;
							qtde_atividades=(long) 0;
							Bson filtro;
							List<Bson> filtros = new ArrayList<Bson>();
							
							rs=mysql.Consulta("select distinct field_name from rollout_campos where field_type='Milestone' and empresa="+p.getEmpresaObj().getEmpresa_id());
							if(rs.next()) {
								//System.out.println("Achou milestones");
								rs.beforeFirst();
								while(rs.next()) {
									filtros = new ArrayList<Bson>();
									filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
									filtros.add(filtro);
									//filtro= Filters.elemMatch("Milestone", Filters.eq("edate_"+rs.getString(1),""));
									//filtros.add(filtro);
									//System.out.println(p.get_PessoaUsuario());
									//System.out.println("******resp_"+rs.getString(1));
									filtro= Filters.elemMatch("Milestone", Filters.eq("resp_"+rs.getString(1),p.get_PessoaUsuario()));
									filtros.add(filtro);
									filtro= Filters.elemMatch("Milestone", Filters.eq("edate_"+rs.getString(1),""));
									filtros.add(filtro);
									//System.out.println(rs.getString(1));
									try {
									FindIterable<Document> finddocuments = mongo.ConsultaSimplesComFiltro("rollout", filtros);
									//System.out.println("*****Consulta executada");
									MongoCursor<Document> resultado = finddocuments.iterator();
									//System.out.println("*****Contagem efetuada");
									if(resultado.hasNext()) {
									//	System.out.println("****achou documentos");
										Document atividade;
										while(resultado.hasNext()) {
											atividade=resultado.next();
											qtde_atividades=qtde_atividades+1;
										}
									}}catch(Exception e) {
										System.out.println(e.getCause());
										System.out.println(e.getMessage());
									}
									//qtde_atividades=qtde_atividades+mongo.ConsultaCountComplexa("rollout", filtros);
									System.out.println("Atividades Encontradas para "+ rs.getString(1) +" foram "+ qtde_atividades);
								}
								resp.setContentType("application/text");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(qtde_atividades);
							}else {
								resp.setContentType("application/text");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(0);
							}
							
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("32")){
							//Carrega Tarefas do Rollout no APP.
							System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" carregando Atvidades do Rollout");
							query="";
							String dstart="";
							String start="";
							String dfim="";
							String comentarios="";
							String duracao= "";
							String lat="";
							String lng="";
							String imagem_status= "notstarted.png";
							String bt_texto="";
							
							Bson filtro;
							Document linha_rollout=new Document();
							Document milestone=new Document();
							Document site=new Document();
							List<Bson> lista_filtro = new ArrayList<>();
							List<Bson> lista_filtro_site = new ArrayList<>();
							List<Document> milestones = new ArrayList<>();
							rs=mysql.Consulta("select distinct field_name from rollout_campos where field_type='Milestone' and empresa="+p.getEmpresaObj().getEmpresa_id());
							if(rs.next()) {
								rs.beforeFirst();
								while(rs.next()) {
									filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
									lista_filtro.add(filtro);
									filtro= Filters.elemMatch("Milestone", Filters.and(Filters.eq("edate_"+rs.getString(1),""),Filters.eq("resp_"+rs.getString(1),p.get_PessoaUsuario())));
									lista_filtro.add(filtro);
									FindIterable<Document> findIterable2;
									FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("rollout", lista_filtro);
									MongoCursor<Document> resultado = findIterable.iterator();
									if(resultado.hasNext()) {
										while(resultado.hasNext()) {
											linha_rollout=resultado.next();
											lista_filtro_site.clear();
											filtro=Filters.and(Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id()),Filters.eq("site_id",linha_rollout.getString("Site ID")));
											lista_filtro_site.add(filtro);
											findIterable2=mongo.ConsultaCollectioncomFiltrosLista("sites", lista_filtro_site);
											MongoCursor<Document> resultado2 = findIterable2.iterator();
											if(resultado2.hasNext()) {
												//System.out.println("achou site id:"+linha_rollout.getString("Site ID"));
												site=resultado2.next();
												if(site.isEmpty()) {
													lat="0.0";
													lng="0.0";
												}else {
													lat=site.getString("site_latitude");
													lng=site.getString("site_longitude");
													//System.out.println("Coordenadas:"+lat+","+lng);
												}
											}else {
												lat="0.0";
												lng="0.0";
											}
											milestones = (List<Document>) linha_rollout.get("Milestone");
											for(int indice=0;indice<milestones.size();indice++) {
												milestone=milestones.get(indice);
												if(milestone.getString("Milestone").equals(rs.getString(1))) {
													if(milestone.get("sdate_"+rs.getString(1))!=null && !milestone.get("sdate_"+rs.getString(1)).equals("")){
														start=f2.format(milestone.getDate("sdate_"+rs.getString(1)));
													}else {
														start="Não Iniciado";
													}
													if(milestone.get("udate_"+rs.getString(1))!=null && !milestone.get("udate_"+rs.getString(1)).equals("")){
														comentarios=milestone.getString("udate_"+rs.getString(1));
													}else {
														comentarios="Sem Anaotações";
													}
													if(milestone.get("duracao_"+rs.getString(1))!=null && !milestone.get("duracao_"+rs.getString(1)).equals("")){
														duracao=milestone.get("duracao_"+rs.getString(1)).toString();
													}else {
														duracao="";
													}
													if(milestone.get("sdate_pre_"+rs.getString(1))!=null && !milestone.get("sdate_pre_"+rs.getString(1)).equals("")){
														dstart=f2.format(milestone.getDate("sdate_pre_"+rs.getString(1)));
													}else {
														dstart="";
													}
													if(milestone.get("edate_pre_"+rs.getString(1))!=null && !milestone.get("edate_pre_"+rs.getString(1)).equals("")){
														dfim=f2.format(milestone.getDate("edate_pre_"+rs.getString(1)));
													}else {
														dfim="";
													}
													if(milestone.get("status_"+rs.getString(1)).equals("Finalizada")) {
					    								imagem_status="finished.png";
					    							}else if(milestone.get("status_"+rs.getString(1)).equals("parada")) {
					    								imagem_status="stopped.png";
					    								
					    								bt_texto="Continuar";
					    							}else if(milestone.get("status_"+rs.getString(1)).equals("iniciada")) {
					    								imagem_status="started.png";
					    								bt_texto="Parar";
					    								
					    							}else {
					    								imagem_status="notstarted.png";
					    								bt_texto="Iniciar";
					    								
					    							}
													indice=999;
												}
											}
											dados_tabela=dados_tabela+"<ons-card >\n"+
												      "<div class='title' style='display:block'>"+milestone.getString("Milestone")+"<div style='float:right'><img src='img/"+imagem_status+"'></div></div>\n"+
												      "<div class=\"content\"><div>Site:"+linha_rollout.getString("Site ID")+"</div><div>Planejamento:"+dstart+" - "+ dfim+"</div><div>Inicio Real: "+start+" - Duração(D): "+duracao+"</div><div>Comentários: "+comentarios+"</div><hr><br>"
												      +"<section style='padding:5px'><table width=\"100%\">"
													      + "<tr>"
														      
														      + "<td style='padding:2px'><a href=\"#\" class=\"myButton\"  onclick=\"atualiza_rollout("+linha_rollout.getInteger("recid")+",'"+milestone.getString("Milestone")+"','"+milestone.get("status_"+rs.getString(1))+"','"+linha_rollout.getString("Site ID")+"')\">"+bt_texto+"</a></td>"
														      + "<td style='padding:2px'><a href=\"#\" class=\"myButton\"  onclick=\"atualiza_rollout("+linha_rollout.getInteger("recid")+",'"+milestone.getString("Milestone")+"','Finalizada','"+linha_rollout.getString("Site ID")+"')\">Completar</a></td>"
													      + "</tr>"
													      + "<tr>"
													      + "<td style='padding:2px'><a href=\"#\" class=\"myButton\" ><i class=\"far fa-hand-pointer\"  onclick=\"reg_bySite('"+linha_rollout.getString("Site ID")+"','"+lat+"','"+lat+"','Site','"+p.getEmpresaObj().getEmpresa_id()+"')\"></i></a></td>"
														      + "<td style='padding:2px'>"
														      	  + "<a href=\"#\" class=\"myButton\"  style=\"border-radius: 5%;font-size:25px;\" onclick=\"navega('cheklist_select_template');setTimeout(atualiza_checklist_dados("+linha_rollout.getInteger("recid")+",'"+milestone.getString("Milestone")+"','"+linha_rollout.getString("Site ID")+"'),1000)\">Checklist</a>"
														      + "</td>"
													      + "</tr>"
													      + "</table>"
												      + "</section></div>"+		
												     // + "<section style='padding:10px'><ons-button modifier=\"large\" style=\"border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','"+rs.getString("status_atividade")+"','"+rs.getString("value_atbr_field")+"')\">"+bt_texto+"</ons-button><ons-button modifier=\"large\" style=\"background:green;border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','Finalizada','"+rs.getString("value_atbr_field")+"')\">Completar</ons-button></section></div>\n"+
												    "\n</ons-card>\n";
										}
									}
								lista_filtro.clear();
								}
							}
							
							//query="select * from rollout where tipo_campo='Milestone' and responsavel='"+p.get_PessoaUsuario()+"' and dt_fim in ('','01/01/1800') ";
							
							
							
							/*rs=mysql.Consulta(query);
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
									      +"<section style='padding:10px'><i class=\"far fa-hand-pointer\"></i></section>"		
									      + "<section style='padding:10px'><ons-button modifier=\"large\" style=\"border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','"+rs.getString("status_atividade")+"','"+rs.getString("value_atbr_field")+"')\">"+bt_texto+"</ons-button><ons-button modifier=\"large\" style=\"background:green;border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','Finalizada','"+rs.getString("value_atbr_field")+"')\">Completar</ons-button></section></div>\n"+
									    "</ons-card>\n";
									}*/
								//System.out.println(dados_tabela);
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
								mongo.fecharConexao();
			    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
			    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("33")){
							
							param1=req.getParameter("recid");
							param2=req.getParameter("milestone");
							param3=req.getParameter("status");
							param4=req.getParameter("siteid");
							System.out.println("param1:"+param1);
							System.out.println("param2:"+param2);
							System.out.println("param3:"+param3);
							System.out.println("param4:"+param4);
							Document filtro = new Document();
							Document update=new Document();
							Document updates=new Document();
							
							Document historico = new Document();
							query="";
							if(param3.equals("Finalizada")) {
								filtro.append("recid", Integer.parseInt(param1));
								filtro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
								filtro.append("Milestone.Milestone", param2);
								update.append("Milestone.$.edate_"+param2, checa_formato_data(f2.format(time)));
								update.append("Milestone.$.status_"+param2, "Finalizada");
								updates.append("$set", update);
								mongo.AtualizaUm("rollout", filtro, updates);
								//query="update rollout set dt_fim='"+f2.format(time)+"',status_atividade='Finalizada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
								historico.append("recid" , param1);
	    				        historico.append("SiteID" , param4);
	    				        historico.append("Empresa" , p.getEmpresaObj().getEmpresa_id());
	    				        historico.append("TipoCampo" , "Milestone");
	    				        historico.append("Milestone" , param2);
	    				        historico.append("Campo","Fim Real");
	    				        historico.append("Valor Anterior" , "");
	    				        historico.append("Novo Valor" , f2.format(time));
	    				        historico.append("update_by", p.get_PessoaUsuario());
	    				        historico.append("update_time", time);
	    				        mongo.InserirSimpels("rollout_history", historico);
	    						historico.clear();
	    						historico.append("recid" , param1);
	    				        historico.append("SiteID" , param4);
	    				        historico.append("Empresa" , p.getEmpresaObj().getEmpresa_id());
	    				        historico.append("TipoCampo" , "Milestone");
	    				        historico.append("Milestone" , param2);
	    				        historico.append("Campo","Status");
	    				        historico.append("Valor Anterior" , "iniciada");
	    				        historico.append("Novo Valor" , "Finalizada");
	    				        historico.append("update_by", p.get_PessoaUsuario());
	    				        historico.append("update_time", time);
	    				        mongo.InserirSimpels("rollout_history", historico);
	    						historico.clear();
							}else if(param3.equals("Nao Iniciada")){
								//System.out.println("entrou no nao iniciada");
								filtro.append("recid", Integer.parseInt(param1));
								filtro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
								filtro.append("Milestone.Milestone", param2);
								update.append("Milestone.$.sdate_"+param2, checa_formato_data(f2.format(time)));
								update.append("Milestone.$.status_"+param2, "iniciada");
								update.append("Milestone.$.duracao_"+param2, 1);
								update.append("update_by", p.get_PessoaUsuario());
								update.append("update_time", checa_formato_data(f2.format(d.getTime())));
								updates.append("$set", update);
								
								mongo.AtualizaUm("rollout", filtro, updates);
								//query="update rollout set dt_inicio='"+f2.format(time)+"',status_atividade='iniciada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
								historico.append("recid" , param1);
	    				        historico.append("SiteID" , param4);
	    				        historico.append("Empresa" , p.getEmpresaObj().getEmpresa_id());
	    				        historico.append("TipoCampo" , "Milestone");
	    				        historico.append("Milestone" , param2);
	    				        historico.append("Campo","Inicio Real");
	    				        historico.append("Valor Anterior" , "");
	    				        historico.append("Novo Valor" , f2.format(time));
	    				        historico.append("update_by", p.get_PessoaUsuario());
	    				        historico.append("update_time", time);
	    				        mongo.InserirSimpels("rollout_history", historico);
	    						historico.clear();
	    						historico.append("recid" , param1);
	    				        historico.append("SiteID" , param4);
	    				        historico.append("Empresa" , p.getEmpresaObj().getEmpresa_id());
	    				        historico.append("TipoCampo" , "Milestone");
	    				        historico.append("Milestone" , param2);
	    				        historico.append("Campo","Status");
	    				        historico.append("Valor Anterior" , "não iniciada");
	    				        historico.append("Novo Valor" , "iniciada");
	    				        historico.append("update_by", p.get_PessoaUsuario());
	    				        historico.append("update_time", time);
	    				        mongo.InserirSimpels("rollout_history", historico);
	    						historico.clear();
							}else if(param3.equals("iniciada")) {
								filtro.append("recid", Integer.parseInt(param1));
								filtro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
								filtro.append("Milestone.Milestone", param2);
								//update.append("Milestone.$.sdate_"+param2, checa_formato_data(f2.format(time)));
								update.append("Milestone.$.status_"+param2, "parada");
								updates.append("$set", update);
								mongo.AtualizaUm("rollout", filtro, updates);
								//query="update rollout set status_atividade='parada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
								historico.append("recid" , param1);
	    				        historico.append("SiteID" , param4);
	    				        historico.append("Empresa" , p.getEmpresaObj().getEmpresa_id());
	    				        historico.append("TipoCampo" , "Milestone");
	    				        historico.append("Milestone" , param2);
	    				        historico.append("Campo","Status");
	    				        historico.append("Valor Anterior" , "iniciada");
	    				        historico.append("Novo Valor" , "parada");
	    				        historico.append("update_by", p.get_PessoaUsuario());
	    				        historico.append("update_time", time);
	    				        mongo.InserirSimpels("rollout_history", historico);
							}else if(param3.equals("parada")) {
								filtro.append("recid", Integer.parseInt(param1));
								filtro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
								filtro.append("Milestone.Milestone", param2);
								//update.append("Milestone.$.sdate_"+param2, checa_formato_data(f2.format(time)));
								update.append("Milestone.$.status_"+param2, "iniciada");
								updates.append("$set", update);
								mongo.AtualizaUm("rollout", filtro, updates);
								//query="update rollout set status_atividade='iniciada' where recid="+param1+" and milestone='"+param2+"' and empresa="+p.getEmpresa();
								historico.append("recid" , param1);
	    				        historico.append("SiteID" , param4);
	    				        historico.append("Empresa" , p.getEmpresaObj().getEmpresa_id());
	    				        historico.append("TipoCampo" , "Milestone");
	    				        historico.append("Milestone" , param2);
	    				        historico.append("Campo","Status");
	    				        historico.append("Valor Anterior" , "parada");
	    				        historico.append("Novo Valor" , "iniciada");
	    				        historico.append("update_by", p.get_PessoaUsuario());
	    				        historico.append("update_time", time);
	    				        mongo.InserirSimpels("rollout_history", historico);
							}
							//System.out.println(query);
							//if(mysql.Update_simples(query)) {
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("Tarefa atualizada com Sucesso!");
							//}
								mongo.fecharConexao();
			    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
			    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("34")){
							System.out.println("Salvando foto de perfil");
							
							String imageBase64 = req.getParameter("image64");
							System.out.println(imageBase64);
							byte[] imageByte= Base64.decodeBase64(imageBase64);
							InputStream inputStream2= new ByteArrayInputStream(imageByte);
							/*if (ServletFileUpload.isMultipartContent(req)) {
								List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
								
								Iterator<FileItem> iter = multiparts.iterator();
								while (iter.hasNext()) {
									FileItem item = iter.next();
									if (item.isFormField()) {
								       System.out.println("é form");
								    } else {
								    System.out.println("é part");
								    }
								}}*/
							
							//ServletInputStream is=req.getInputStream();
							//System.out.println(is.available());
							//ByteArrayOutputStream os = null;
							//byte[] buf = new byte[1000];
							//for (int nChunk = is.read(buf); nChunk!=-1; nChunk = is.read(buf))
							//{
							//    os.write(buf, 0, nChunk);
							//} 
							//InputStream inputStream2= new ByteArrayInputStream(os.toByteArray());
							
								    	//System.out.println(item.getContentType());
								    	//System.out.println("Item size: " + item.getSize());
								    	//InputStream inputStream2= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));	    	
								    	query="";
										query="update usuarios set foto_perfil=? where id_usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'";
										PreparedStatement statement;
										 statement = mysql.getConnection().prepareStatement(query);
										 statement.setBlob(1, inputStream2);
										 int row = statement.executeUpdate();
									     statement.getConnection().commit();
									     if (row>0) {
									        	System.out.println("Foto de Perfil salva com sucesso");
									        	statement.close();
									     }
								    
							
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("35")){
							//System.out.println("Carregando foto de perfil de "+p.get_PessoaUsuario());
							ServletOutputStream out = resp.getOutputStream();
							query="";
							Blob image = null;
							
							query="select foto_perfil from usuarios where id_usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'";
							rs=mysql.Consulta(query);
							if(rs.next()) {
								//System.out.println(" foto encontrada");
								image=rs.getBlob(1);
								if(image==null) {
									query="select foto_perfil from usuarios where id_usuario='usuario_foto'";
									rs2=mysql.Consulta(query);
									if(rs2.next()) {
										image = rs2.getBlob(1);
										 //in = image.getBinaryStream(1,(int)image.length());
									}
									
								}
								byte byteArray[]=image.getBytes(1, (int) image.length());
								
								resp.setContentType("image/png");
								
								out.write(byteArray);
								out.flush();
								out.close();
								//System.out.println(" foto carregada");
								
							}
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("36")){
							//System.out.println("Buscando lista de Aprovacoes");
							param1=req.getParameter("usuario");
							param2=req.getParameter("tipo");
							query="";
							dados_tabela="<div class=\"list\">";
							if(param2.equals("ajustePonto")) {
								if(p.getPerfil_funcoes().contains("AjustePontoApprover")) {
									query="select * from ajuste_ponto where aprovada<>'R' and aprovada <> 'Y' and usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_ajuste_ponto desc";
									rs=mysql.Consulta(query);
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
									rs=mysql.Consulta(query);
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
									rs=mysql.Consulta(query);
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
							}else if(param2.equals("autoriza_he")) {
								if(p.getPerfil_funcoes().contains("BancoHHApprover")) {
									System.out.println("aqui - "+param1);
									Document autorizacao= new Document();
									Bson filtro;
									List<Bson> filtros = new ArrayList<>();
									filtro=Filters.eq("usuario_solicitante",param1);
									filtros.add(filtro);
									filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
									filtros.add(filtro);
									filtro=Filters.eq("usuario_aprovador",p.get_PessoaUsuario());
									filtros.add(filtro);
									filtro=Filters.eq("status_autorizacao","PENDENTE");
									filtros.add(filtro);
									
									
									filtro=Filters.eq("data_dia_he",f2.format(time));
									filtros.add(filtro);
									FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("Autoriza_HE", filtros);
									MongoCursor<Document> resultado = findIterable.iterator();
									if(resultado.hasNext()) {
										System.out.println("aqui 2- "+param1);
										while(resultado.hasNext()) {
											autorizacao=resultado.next();
											dados_tabela=dados_tabela+"<div class=\"item\">";
											dados_tabela=dados_tabela+"<a href=\"#\" class=\"item-swipe\"><b>Autorização Prévia de Hora Extra - "+autorizacao.getString("usuario_solicitante")+"</b><br>Local:"+autorizacao.getString("local_hora_extra")+"</a>";
											dados_tabela=dados_tabela+"<div class=\"item-back\">";
											dados_tabela=dados_tabela+"<button class=\"action first btn-delete\" onclick=\"reprova('"+autorizacao.getLong("cod_autorizacao")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-trash\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"<button class=\"action second btn-check\" onclick=\"aprova_aprovacoes('"+autorizacao.getLong("cod_autorizacao")+"')\" type=\"button\">";
											dados_tabela=dados_tabela+"<i class=\"fa fa-check\"></i>";
											dados_tabela=dados_tabela+"</button>";
											dados_tabela=dados_tabela+"</div>";
											dados_tabela=dados_tabela+"</div>";
										}
									}
									
									}
									mongo.fecharConexao();
									
								}
							
							dados_tabela=dados_tabela+"</div>";
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("37")){
							param1=req.getParameter("tipo");
							if(param1.equals("ajustePonto")) {
							if(p.getPerfil_funcoes().contains("AjustePontoApprover")) {
							query="select distinct usuario from ajuste_ponto where aprovada<>'R' and aprovada <> 'Y' and usuario<>'"+p.get_PessoaUsuario()+"' and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_ajuste_ponto desc";
							rs=mysql.Consulta(query);
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
									rs=mysql.Consulta(query);
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
									rs=mysql.Consulta(query);
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
							}else if(param1.equals("autoriza_he")) {
								Document autorizacao= new Document();
								Bson filtro;
								List<Bson> filtros = new ArrayList<>();
								filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
								filtros.add(filtro);
								filtro=Filters.eq("status_autorizacao","PENDENTE");
								filtros.add(filtro);
								
								filtro=Filters.eq("data_dia_he",f2.format(time));
								filtros.add(filtro);
								List<String> usuarios = mongo.ConsultaSimplesDistinct("Autoriza_HE", "usuario_solicitante", filtros);
								if(usuarios.size()>0){
									dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\"><option value='0'>Selecione um Funcionário</option>";
									for(int indice=0;indice<usuarios.size();indice++) {
										
										dados_tabela=dados_tabela+"<option value='"+usuarios.get(indice)+"'>"+usuarios.get(indice)+"</option>";
									}
									dados_tabela=dados_tabela+"</ons-select>";
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print(dados_tabela);
								}else {
									dados_tabela="<ons-select id=\"select_func_aprova\" onchange=\"carrega_lista_aprovacoes(this.value)\">";
									dados_tabela=dados_tabela+"<option value='0'>Sem Aprovações Pendentes</option>";
									dados_tabela=dados_tabela+"</ons-select>";
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print(dados_tabela);
								}
									
							}
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("38")){
							//System.out.println("iniciando reprovação");
							param1=req.getParameter("usuario");
							param2=req.getParameter("tipo");
							param3=req.getParameter("id");
							if(param2.equals("ajustePonto")) {
								query="";
								query="update ajuste_ponto set aprovada='R' where id_ajuste_ponto="+param3+" and usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(mysql.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Reprovada com Sucesso!");
								}
							}else if(param2.equals("HorasExtras")) {
								query="";
								query="update horas_extras set aprovada='R' where id_he="+param3+" and id_usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(mysql.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Reprovada com Sucesso!");
								}
							}else if(param2.equals("SiteNovo")) {
								query="";
								query="update site_aprova set aprovado='R' where sysid="+param3+" and usuario_pedido='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(mysql.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Reprovada com Sucesso!");
								}
							}else if(param2.equals("autoriza_he")) {
								Document update = new Document();
								Document comando_update=new Document();
								Document condicao = new Document();
								condicao.append("cod_autorizacao", Long.parseLong(param3));
								update.append("status_autorizacao", "REPROVADO");
								update.append("dt_autorizacao", f3.format(time));
								comando_update.append("$set",update);
								mongo.AtualizaUm("Autoriza_HE", condicao, comando_update);
								envia_mensagem("De:MSTP \nAUTORIZAÇÃO PARA HORA EXTRA NEGADA.\nData e Hora de Envio: "+f3.format(time),p.get_PessoaUsuario());
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print("AUTORIZAÇÃO PRÉVIA REPROVADA!");
							}
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("39")){
							//System.out.println("iniciando reprovação");
							param1=req.getParameter("usuario");
							param2=req.getParameter("tipo");
							param3=req.getParameter("id");
							if(param2.equals("HorasExtras")) {
								query="";
								query="update horas_extras set aprovada='Y' where id_he="+param3+" and id_usuario='"+param1+"' and empresa="+p.getEmpresaObj().getEmpresa_id();
								if(mysql.Alterar(query)) {
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print("Requisição Aprovada com Sucesso!");
								}
							}else if(param2.equals("ajustePonto")) {
								query="";
								query="update ajuste_ponto set aprovada='Y',dt_aprovada='"+f3.format(time)+"' where id_ajuste_ponto="+param3;
								if(mysql.Update_simples(query)) {
									query="select * from ajuste_ponto where id_ajuste_ponto="+param3+" and aprovada='Y'";
									rs=mysql.Consulta(query);
									
										if(rs.next()) {
											if((rs.getString("motivo").toUpperCase().equals("FOLGA")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Folga",mysql,"0","0",rs.getString("other2")+" 00:00:00","0","","Folga, - , - ");
											}else if((rs.getString("motivo").equals("Banco de Horas - Consumo de 8h")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Compensação",mysql,"0","0",rs.getString("other2")+" 00:00:00","0","","Compensado, - , - ");
												mysql.Inserir_simples("insert into horas_extras (he_data,id_usuario,he_quantidade,aprovada,dt_add,compensada,origen) values('"+rs.getString("other2")+"','"+rs.getString("usuario")+"','-8.00','Y','"+f3.format(time)+"','N','Compensação - MSTP WEB')");
											}else if((rs.getString("motivo").equals("Compensação de horas")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Compensação",mysql,"0","0",rs.getString("other2")+" 00:00:00","0","","Compensado, - , - ");
												mysql.Inserir_simples("insert into horas_extras (he_data,id_usuario,he_quantidade,aprovada,dt_add,compensada,origen) values('"+rs.getString("other2")+"','"+rs.getString("usuario")+"','-8.00','Y','"+f3.format(time)+"','N','Compensação - MSTP WEB')");
											}else if((rs.getString("motivo").equals("Licença Médica")) && (!rs.getString("other2").equals("0"))) {
												insere_regitro(p,rs.getString("usuario"),"Licença Médica",mysql,"0","0",rs.getString("other2")+" 00:00:00","0","","Licença Médica, - , - ");
												
											}else {
												insere_regitro(p,rs.getString("usuario"),"Entrada",mysql,"0","0",rs.getString("dt_entrada"),"0",rs.getString("dt_entrada"),"PontoAjustado,"+p.getEmpresaObj().getNome()+","+p.getEmpresaObj().getNome());
												insere_regitro(p,rs.getString("usuario"),"Saída",mysql,"0","0",rs.getString("dt_saida"),"0",rs.getString("dt_saida"),"PontoAjustado,"+p.getEmpresaObj().getNome()+","+p.getEmpresaObj().getNome());
												insere_regitro(p,rs.getString("usuario"),"Inicio_intervalo",mysql,"0","0",rs.getString("dt_ini_inter"),"0",rs.getString("dt_ini_inter"),"PontoAjustado,"+p.getEmpresaObj().getNome()+","+p.getEmpresaObj().getNome());
												insere_regitro(p,rs.getString("usuario"),"Fim_intervalo",mysql,"0","0",rs.getString("dt_fim_inter"),"0",rs.getString("dt_fim_inter"),"PontoAjustado,"+p.getEmpresaObj().getNome()+","+p.getEmpresaObj().getNome());
											}
												//rs2=mysql.Consulta("select * from usuarios where id_usuario='"+rs.getString("usuario")+"'");
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
							if(mysql.Update_simples(query)) {
								rs=mysql.Consulta("select * from site_aprova where sysid="+param3);
								if(rs.next()) {
									query="INSERT INTO sites (site_id,site_nome,site_latitude,site_longitude,site_uf,site_operadora,dt_add_site,usuario_add_site,empresa) "
							                + "VALUES ('"+rs.getString("site_id")+"','"+rs.getString("site_nome")+"','"+rs.getString("site_lat").trim()+"','"+rs.getString("site_lng").trim()+"','"+rs.getString("site_estado")+"','"+rs.getString("site_operadora")+"','"+time+"','"+rs.getString("usuario_pedido")+"','"+p.getEmpresaObj().getEmpresa_id()+"')";
									if(mysql.Inserir_simples(query)) {
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
						}else if(param2.equals("autoriza_he")) {
							Document update = new Document();
							Document comando_update=new Document();
							Document condicao = new Document();
							condicao.append("cod_autorizacao", Long.parseLong(param3));
							update.append("status_autorizacao", "APROVADO");
							update.append("dt_autorizacao", f3.format(time));
							comando_update.append("$set",update);
							mongo.AtualizaUm("Autoriza_HE", condicao, comando_update);
							envia_mensagem("De:MSTP \nAUTORIZAÇÃO PARA HORA EXTRA APROVADA\nData e Hora de Envio: "+f3.format(time),p.get_PessoaUsuario());
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("AUTORIZAÇÃO PRÉVIA APROVADA");
						}
							mongo.fecharConexao();
		    				Timestamp time2 = new Timestamp(System.currentTimeMillis());
		    				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("40")){
				param1=req.getParameter("log");
				Semail email=new Semail();
				email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com", "MSTP Mobile - LOG DE ERRO ENVIADO","ENVIADO POR: "+p.get_PessoaUsuario()+", \n \n data de envio: "+time+" \n\n LOG: \n\n "+param1);
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Log do erro enviado com sucesso! estaremos analisando as possíveis causas e soluções!");
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("41")) {
				Bson filtro;
				List<Bson> filtros = new ArrayList<>();
				Document justificativa = new Document();
				filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
				filtros.add(filtro);
				FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("Justificativas", filtros);
				MongoCursor<Document> resultado = findIterable.iterator();
				dados_tabela="<ons-select id=\"motivo_ajuste\" class=\"hora_ajuste_class\"><option value='0'>Selecione uma Opção</option>";
				
				while(resultado.hasNext()) {
					justificativa=resultado.next();
					dados_tabela=dados_tabela+"<option value='"+justificativa.getString("Justificativa")+"' data-foto='"+justificativa.getString("Foto_requerida")+"'>"+justificativa.getString("Justificativa")+"</option>\n";
				}
				dados_tabela=dados_tabela+"</ons-select>";
				//System.out.println(dados_tabela);
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("42")) {
				//System.out.println("Salvando foto de justificativa");
				int ultimo=0;
				rs=mysql.Consulta("select * from ajuste_ponto where usuario='"+p.get_PessoaUsuario()+"' order by id_ajuste_ponto desc limit 1");
				if(rs.next()) {
					ultimo=rs.getInt(1);
				}
				String imageBase64 = req.getParameter("image64");
				//System.out.println(imageBase64);
				byte[] imageByte= Base64.decodeBase64(imageBase64);
				InputStream inputStream2= new ByteArrayInputStream(imageByte);
					    	
					    	query="";
							query="update ajuste_ponto set foto_justificativa=? where id_ajuste_ponto="+ultimo+" and empresa="+p.getEmpresaObj().getEmpresa_id();
							PreparedStatement statement;
							 statement = mysql.getConnection().prepareStatement(query);
							 statement.setBlob(1, inputStream2);
							 int row = statement.executeUpdate();
						     statement.getConnection().commit();
						     if (row>0) {
						        	System.out.println("Foto de Justificativa salva com sucesso");
						        	statement.close();
						     }
					    
					
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("43")) {

				//System.out.println("Carregando sites no mapa");
				//Essa função ainda precisa ser adaptada para excuir folgas, pontos ajustados,férias e licença Médica
				Calendar dia = Calendar.getInstance();
				dia.set(Calendar.HOUR_OF_DAY,00);
				
				dia.set(Calendar.MINUTE,00);
				Document registro=new Document();
				Document geo=new Document();
				Bson filtro;
				List<Bson> lista_filtro = new ArrayList<Bson>();
				filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
				lista_filtro.add(filtro);
				//filtro=Filters.not("Empresa",p.getEmpresa().getEmpresa_id());
				lista_filtro.add(filtro);
				filtro=Filters.gte("data_dia",dia.getTime());
				lista_filtro.add(filtro);
				filtro=Filters.lte("data_dia",time);
				lista_filtro.add(filtro);
				FindIterable<Document> findIterable =mongo.ConsultaCollectioncomFiltrosLista("Registros", lista_filtro);
				MongoCursor<Document> resultado= findIterable.iterator();
				if(resultado.hasNext()) {
					
					
					dados_tabela="";
					dados_tabela=  "{"+
					    "\"type\": \"FeatureCollection\","+
					    "\"features\": [";
					while(resultado.hasNext()) {
						registro=resultado.next();
						geo = (Document) registro.get("GEO");
						
						dados_tabela=dados_tabela+geo.toJson()+",";
						
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"]";
					dados_tabela=dados_tabela +   "}";
				}else {
					dados_tabela="";
					dados_tabela=  "{"+
					    "\"type\": \"FeatureCollection\","+
					    "\"features\": []}";
				}
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
			}else if(opt.equals("44")) {
				Bson filtro;
				Document site;
				List<Bson> lista_filtro = new ArrayList<>();
				param2=req.getParameter("siteid");
				param3=req.getParameter("latitude");
				param4=req.getParameter("longitude");
				//Point refPoint = new Point(new Position(Double.parseDouble(param4), Double.parseDouble(param3)));
				System.out.println(param2);
				System.out.println(param3);
				System.out.println(param4);
				filtro = Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
				lista_filtro.add(filtro);
				filtro = Filters.eq("site_id",param2);
				lista_filtro.add(filtro);
				filtro = Filters.eq("site_ativo","Y");
				lista_filtro.add(filtro);
				
				//System.out.println("funcao 44");
				AggregateIterable<Document> aggregateIterable= mongo.ConsultaSitecomFiltrosListaAggregation(param4,param3,lista_filtro);
					MongoCursor<Document> resultado = aggregateIterable.iterator();
					if(resultado.hasNext()) {
						System.out.println("achou resultado");
						site = resultado.next();
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(Math.round(site.get("dist",Document.class).getDouble("calculated")));
					}
					mongo.fecharConexao();
					Timestamp time2 = new Timestamp(System.currentTimeMillis());
					System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
				
			}else if(opt.equals("45")) {
				//System.out.println("Salvando foto de registro");
				int ultimo=0;
				String tipo_regitro="";
				rs=mysql.Consulta("select * from registros where usuario='"+p.get_PessoaUsuario()+"' order by sys_contador desc limit 1");
				if(rs.next()) {
					ultimo=rs.getInt(1);
					tipo_regitro=rs.getString("tipo_registro");
					if(rs.getString("timeStamp_mobile").indexOf(".")>0) {
                		d.setTimeInMillis(Long.parseLong(rs.getString("timeStamp_mobile").substring(0, rs.getString("timeStamp_mobile").indexOf("."))));
                	}else {
                		d.setTimeInMillis(Long.parseLong(rs.getString("timeStamp_mobile")));
                	}
				}
				String imageBase64 = req.getParameter("image64");
				//System.out.println(imageBase64);
				byte[] imageByte= Base64.decodeBase64(imageBase64);
				InputStream inputStream2= new ByteArrayInputStream(imageByte);
					    	//InputStream inputStream2= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
					    	query="";
							query="insert into registro_foto (id_registro,usuario,timestamp_banco,foto_registro,Empresa,mes,dia,hora,min,ano,tipo_registro,data_dia) values ("+ultimo+",'"+p.get_PessoaUsuario()+"','"+time+"',?,"+p.getEmpresaObj().getEmpresa_id()+","+(d.get(Calendar.MONTH)+1)+","+d.get(Calendar.DAY_OF_MONTH)+","+d.get(Calendar.HOUR_OF_DAY)+","+d.get(Calendar.MINUTE)+","+d.get(Calendar.YEAR)+",'"+tipo_regitro+"','"+f2.format(time)+"')";
							PreparedStatement statement;
							 statement = mysql.getConnection().prepareStatement(query);
							 statement.setBlob(1, inputStream2);
							 int row = statement.executeUpdate();
						     statement.getConnection().commit();
						     if (row>0) {
						        	//System.out.println("Foto de Registro salva com sucesso");
						        	statement.close();
						     }
					    
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("46")) {
				query="";
				query="select * from versao_script";
				rs=mysql.Consulta(query);
				if(rs.next()) {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(rs.getString("versao_script"));
				}
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("47")) {
				
				param2=req.getParameter("local_");
				param3=req.getParameter("motivo");
				param4=req.getParameter("lider_");
				//System.out.println(param4);
				//System.out.println(p.getPessoaLider());
				Document autoriza_hh= new Document();
				autoriza_hh.append("cod_autorizacao", time.getTime());
				autoriza_hh.append("usuario_solicitante", p.get_PessoaUsuario());
				autoriza_hh.append("Empresa", p.getEmpresaObj().getEmpresa_id());
				autoriza_hh.append("usuario_aprovador", p.getPessoaLider());
				autoriza_hh.append("status_autorizacao", "PENDENTE");
				autoriza_hh.append("data_dia_he", f2.format(time));
				autoriza_hh.append("dt_autorizacao", "");
				autoriza_hh.append("dt_solicitacao", f3.format(time));
				autoriza_hh.append("local_hora_extra", param2);
				autoriza_hh.append("motivo_hora_extra", param3);
				autoriza_hh.append("dt_solicitacao_string", f3.format(time).toString());
				mongo.InserirSimpels("Autoriza_HE", autoriza_hh);
				envia_mensagem("De:"+p.get_PessoaUsuario()+"\nSOLICITAÇÃO DE AUTORIZAÇÃO PARA HORA EXTRA\nData e Hora de Envio: "+f3.format(time),p.getPessoaLider());
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("48")) {
				
				param2=req.getParameter("local");
				param3=req.getParameter("motivo");
				param4=req.getParameter("lider");
				Document autorizacao= new Document();
				Bson filtro;
				List<Bson> filtros = new ArrayList<>();
				filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
				filtros.add(filtro);
				filtro=Filters.eq("status_autorizacao","APROVADO");
				filtros.add(filtro);
				filtro=Filters.eq("usuario_solicitante",p.get_PessoaUsuario());
				filtros.add(filtro);
				filtro=Filters.eq("data_dia_he",f2.format(time));
				filtros.add(filtro);
				FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("Autoriza_HE", filtros);
				MongoCursor<Document> resultado=findIterable.iterator();
				if(resultado.hasNext()) {
				autorizacao = resultado.next();
				if(autorizacao.getString("status_autorizacao").equals("APROVADO")) {
					//System.out.println("achou um aprovado");
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("OK");
				}else {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("NOK");
				}
				}else {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("NOK");
				}
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("49")) {
				
					rs=mysql.Consulta("select distinct id_usuario,nome from usuarios where tipo='Lider' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'");
				 
				if(rs.next()){
    				rs.beforeFirst();
    				dados_tabela="<ons-select id=\"select_lider\" class=\"select-input hora_ajuste_class\" onchange='defini_lider(this.value)'>";
    				dados_tabela=dados_tabela+"<option value=\"\">Escolha um Líder</option>"; 
    	          while(rs.next()){
    					 if(p.getPessoaLider().equals(rs.getString("id_usuario"))) {
    						dados_tabela=dados_tabela+"<option value=\""+rs.getString("id_usuario")+"\" selected >"+rs.getString("nome")+"</option>";
    					 }else {
    						 dados_tabela=dados_tabela+"<option value=\""+rs.getString("id_usuario")+"\">"+rs.getString("nome")+"</option>";
    					 }
    				}
    				
    				dados_tabela=dados_tabela+"</ons-select>";
				}else {
					dados_tabela="<ons-select id=\"select_lider\">";
					dados_tabela=dados_tabela+"<option value=\"\">Líderes nao definidos</option>";
					dados_tabela=dados_tabela+"</ons-select>";
				}
    				
    			
				//System.out.println(dados_tabela);
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
			}else if(opt.equals("50")) {
				param1=req.getParameter("lider");
				query="";
				query="update usuarios set lider_usuario='"+param1+"' where id_usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"'";
				if(mysql.Update_simples(query)) {
					p.setPessoaLider(param1);
					session.setAttribute("pessoa",p);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("OK");
				}
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
			
			}else if(opt.equals("51")) {
				
				query="select * from vistoria_report where empresa="+p.getEmpresaObj().getEmpresa_id();
				rs=mysql.Consulta(query);
				if(rs.next()) {
					dados_tabela="<ons-select id=\"select_checklist\" class=\"select-input hora_ajuste_class\" onchange='carrega_checklist_campos(this.value,1)'>";
    				dados_tabela=dados_tabela+"<option value=\"\">Escolha um Checklist</option>"; 
    				rs.beforeFirst();
    	          while(rs.next()){
    					 dados_tabela=dados_tabela+"<option value=\""+rs.getString("id")+"\">"+rs.getString("relatorio_nome")+"</option>";
    				}
    				
    				dados_tabela=dados_tabela+"</ons-select>";
    				resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
			
			}else if(opt.equals("52")) {
				param1=req.getParameter("id_checklist");
				param2=req.getParameter("recid");
				param3=req.getParameter("site");
				param4=req.getParameter("milestone");
				param5=req.getParameter("novo");
				int id_vistoria_existente=0;
				int id_vistoria_nova=0;
				String imagens_auxiliares="";
				String texto_aux="";
				String data_aux="";
				String SubGrupos="";
				String SubAux="";
				mysql.getConnection().commit();
				if(param5.equals("0")) {

					
					query="select * from vistoria_campos where relatorio_id="+param1+" and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by tree_id";
					//System.out.println(query);
					rs=mysql.Consulta(query);
					if(rs.next()) {
						if(rs.getString("field_type").equals("Grupo")) {
							dados_tabela=dados_tabela+"<ons-card >\n"+
								      "<div class='title' style='display:block'>"+rs.getString("field_name")+"<div style='float:right'></div></div>\n"+
								      "<div class=\"content\">";
							SubGrupos="";
						}
						while(rs.next()) {
							if(rs.getString("field_type").equals("Grupo")) {
								dados_tabela=dados_tabela+"\n</ons-card>\n";
								dados_tabela=dados_tabela+"<ons-card >\n"+
						      "<div class='title' style='display:block'>"+rs.getString("field_name")+"<div style='float:right'></div></div>\n"+
						      "<div class=\"content\">";
								SubGrupos="";
						      
						}else if(rs.getString("field_type").equals("SubGrupo")){
							SubGrupos=SubGrupos+rs.getString("field_name")+" > ";
							SubAux = "<div>"+SubGrupos+"</div><hr>";	
						}else {
							    dados_tabela=dados_tabela+SubAux;
							    if(dados_tabela.length()==0) {
							    	dados_tabela=dados_tabela+"<ons-card ><div>"+rs.getString("field_name")+" - "+rs.getString("tipo")+"</div><hr><br>";
								}else {
									dados_tabela=dados_tabela+"</ons-card><ons-card ><div>"+rs.getString("field_name")+" - "+rs.getString("tipo")+"</div><hr><br>";
								}
								
								if(rs.getString("tipo").equals("Foto")) {
									dados_tabela=dados_tabela+"<section style='padding:5px'><img src='img/add_foto.png' height=\"80\" width=\"80\" onclick=\"foto_checklist("+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+",'NOVA')\"></section></div>";
								}else if(rs.getString("tipo").equals("Texto")){
									dados_tabela=dados_tabela+"<section style='padding:5px'><ons-input modifier=\"material\" type=\"text\" placeholder=\"Insira o texto aqui\" onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+",'NOVA')\" float></ons-input></section></div>";
								}else if(rs.getString("tipo").equals("Data")){
									dados_tabela=dados_tabela+"<section style='padding:5px'><ons-input modifier=\"material\" type=\"date\" placeholder=\"Insira o texto aqui\" onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+",'NOVA')\" float></ons-input></section></div>";
								}else {
									dados_tabela=dados_tabela+"<section style='padding:5px'></section></div>";
								}
								SubAux="";
							}
						     // + "<section style='padding:10px'><ons-button modifier=\"large\" style=\"border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','"+rs.getString("status_atividade")+"','"+rs.getString("value_atbr_field")+"')\">"+bt_texto+"</ons-button><ons-button modifier=\"large\" style=\"background:green;border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','Finalizada','"+rs.getString("value_atbr_field")+"')\">Completar</ons-button></section></div>\n"+
						   }
						dados_tabela=dados_tabela+"\n</ons-card>\n";
						dados_tabela=dados_tabela+"<ons-button modifier=\"large\" onclick=\"submete_checklist(0)\">Submeter</ons-button>";
						System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
					}
					
				}else {
				query="select *,NOW() from vistoria_dados where empresa="+p.getEmpresaObj().getEmpresa_id()+" and recid="+param2+" and status_vistoria<>'CANCELADO' and milestone='"+param4+"' and relatorio_id="+param1;
				rs=mysql.Consulta(query);
				if(rs.next()) {
					rs.last();
					id_vistoria_existente=rs.getInt("Id_vistoria");
					rs.first();
					query="select *,NOW() from vistoria_campos where relatorio_id="+param1+" and empresa="+p.getEmpresaObj().getEmpresa_id()+"  order by tree_id";
					//System.out.println(query);
					rs=mysql.Consulta(query);
					String tudoAprovado="Y";
					if(rs.next()) {
						if(rs.getString("field_type").equals("Grupo")) {
							dados_tabela=dados_tabela+"<ons-card >\n"+
								      "<div class='title' style='display:block'>"+rs.getString("field_name")+"<div style='float:right'></div></div>\n"+
								      "<div class=\"content\">";
							SubGrupos="";
						}
						while(rs.next()) {
							if(rs.getString("field_type").equals("Grupo")) {
								dados_tabela=dados_tabela+"\n</ons-card>\n";
								dados_tabela=dados_tabela+"<ons-card >\n"+
						      "<div class='title' style='display:block'>"+rs.getString("field_name")+"\n<div style='float:right'></div>\n</div>\n"+
						      "<div class=\"content\">";
								SubGrupos="";
						      
						}else if(rs.getString("field_type").equals("SubGrupo")){
							SubGrupos=SubGrupos+rs.getString("field_name")+" > ";
							SubAux = "\n<div>"+SubGrupos+"</div><hr>";	
						}else {
							if(rs.getString("tipo").equals("Foto")) {
							rs2=mysql.Consulta("select *,NOW() from vistoria_dados where empresa="+p.getEmpresaObj().getEmpresa_id()+" and status_vistoria<>'CANCELADO' and id_vistoria="+id_vistoria_existente+" and campo_id="+rs.getString("field_id")+" and recid="+param2+" and relatorio_id="+param1);
							String bordaCor="BordaCorAmarela";
							
							if(rs2.next()) {
								rs2.beforeFirst();
								imagens_auxiliares="";
								while(rs2.next()) {
									if(rs2.getString("status_vistoria").equals("ABERTA")) {
										bordaCor="BordaCorCinza";
										tudoAprovado="N";
										imagens_auxiliares=imagens_auxiliares+"<img class=\""+bordaCor+"\" src=\"https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet?opt=54&id_dados="+rs2.getInt(1)+"\" height=\"80\" width=\"80\" onclick=\"deleta_foto("+rs2.getInt(1)+","+rs2.getInt("relatorio_id")+")\">";
									}else if(rs2.getString("status_vistoria").equals("EM_APROVACAO")) {
										bordaCor="BordaCorAmarela";
										tudoAprovado="N";
										imagens_auxiliares=imagens_auxiliares+"<img class=\""+bordaCor+"\" src=\"https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet?opt=54&id_dados="+rs2.getInt(1)+"\" height=\"80\" width=\"80\" >";
									}else if(rs2.getString("status_vistoria").equals("APROVADO")) {
										bordaCor="BordaCorVerde";
										imagens_auxiliares=imagens_auxiliares+"<img class=\""+bordaCor+"\" src=\"https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet?opt=54&id_dados="+rs2.getInt(1)+"\" height=\"80\" width=\"80\" >";
									}else if(rs2.getString("status_vistoria").equals("REJEITADO")) {
										bordaCor="BordaCorVermelho";
										tudoAprovado="N";
										imagens_auxiliares=imagens_auxiliares+"<img class=\""+bordaCor+"\" src=\"https://inovareti.jelastic.saveincloud.net/mstp_mobile/Op_Servlet?opt=54&id_dados="+rs2.getInt(1)+"\" height=\"80\" width=\"80\" onclick=\"deleta_foto("+rs2.getInt(1)+","+rs2.getInt("relatorio_id")+")\">";
									}
									//imagens_auxiliares=imagens_auxiliares+"<img class=\""+bordaCor+"\" src=\"http://172.20.10.4:8080/mstp_mobile/Op_Servlet?opt=54&id_dados="+rs2.getInt(1)+"\" height=\"80\" width=\"80\" onclick=\"deleta_foto("+rs2.getInt(1)+","+rs2.getInt("relatorio_id")+")\">";
								}
							}else {
								imagens_auxiliares="";
							}
							}else if(rs.getString("tipo").equals("Texto")) {
								rs2=mysql.Consulta("select *,NOW() from vistoria_dados where empresa="+p.getEmpresaObj().getEmpresa_id()+" and status_vistoria<>'CANCELADO' and id_vistoria="+id_vistoria_existente+" and campo_id="+rs.getString("field_id")+" and recid="+param2+" and relatorio_id="+param1);
								if(rs2.next()) {
									
										texto_aux="<ons-input type='text' id='"+rs.getString("field_name")+"_texto' value='"+rs2.getString("campo_valor_str")+"' onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+","+id_vistoria_existente+")\"></ons-input>";
									
								}else {
									texto_aux="<ons-input type='text' id='"+rs.getString("field_name")+"_texto' value='' onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+","+id_vistoria_existente+")\"></ons-input>";
								}
							}else if(rs.getString("tipo").equals("Data")) {
								rs2=mysql.Consulta("select *,NOW() from vistoria_dados where empresa="+p.getEmpresaObj().getEmpresa_id()+" and status_vistoria<>'CANCELADO' and id_vistoria="+id_vistoria_existente+" and campo_id="+rs.getString("field_id")+" and recid="+param2+" and relatorio_id="+param1);
								if(rs2.next()) {
									
										texto_aux="<ons-input type='date' value='"+rs2.getString("campo_valor_str")+"' onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+","+id_vistoria_existente+")\"></ons-input>";
									
								}else {
									texto_aux="<ons-input type='date' value='' onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+","+id_vistoria_existente+")\"></ons-input>";
								}
							}
							dados_tabela=dados_tabela+SubAux;
							if(dados_tabela.length()==0) {
								dados_tabela=dados_tabela+"<ons-card><div>"+rs.getString("field_name")+" - "+rs.getString("tipo")+"</div><hr><br>";
							}else {
								dados_tabela=dados_tabela+"</ons-card><ons-card><div>"+rs.getString("field_name")+" - "+rs.getString("tipo")+"</div><hr><br>";
							}
								if(rs.getString("tipo").equals("Foto")) {
									dados_tabela=dados_tabela+"<section style='padding:5px'><img src='img/add_foto.png' height=\"80\" width=\"80\" onclick=\"foto_checklist("+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+","+id_vistoria_existente+")\">"+imagens_auxiliares+"</section></div>";
								}else if(rs.getString("tipo").equals("Texto")){
									dados_tabela=dados_tabela+"<section style='padding:5px'>"+texto_aux+"</section></div>";
									
								}else if(rs.getString("tipo").equals("Data")){
									dados_tabela=dados_tabela+"<section style='padding:5px'>"+texto_aux+"</section></div>";
								}else {
									dados_tabela=dados_tabela+"<section style='padding:5px'></section></div>";
								}
								SubAux="";
							}
						     // + "<section style='padding:10px'><ons-button modifier=\"large\" style=\"border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','"+rs.getString("status_atividade")+"','"+rs.getString("value_atbr_field")+"')\">"+bt_texto+"</ons-button><ons-button modifier=\"large\" style=\"background:green;border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','Finalizada','"+rs.getString("value_atbr_field")+"')\">Completar</ons-button></section></div>\n"+
						   }
						dados_tabela=dados_tabela+"\n</ons-card>\n";
						if(tudoAprovado.equals("Y")) {
							dados_tabela=dados_tabela+"<ons-button modifier=\"large\" onclick=\"carrega_checklist_campos("+param1+",0)\">Iniciar Novo Checklist</ons-button>";
						}else {
							dados_tabela=dados_tabela+"<ons-button modifier=\"large\" style=\"background-color:red;\" onclick=\"elimina_checklist("+id_vistoria_existente+","+param1+")\">Eliminar Checklist</ons-button><ons-button modifier=\"large\" onclick=\"submete_checklist("+id_vistoria_existente+")\">Submeter</ons-button>";
						}
							//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
					}
						
				}else {
					
				query="select * from vistoria_campos where relatorio_id="+param1+" and empresa="+p.getEmpresaObj().getEmpresa_id()+" order by tree_id";
				//System.out.println(query);
				rs=mysql.Consulta(query);
				if(rs.next()) {
					if(rs.getString("field_type").equals("Grupo")) {
						dados_tabela=dados_tabela+"<ons-card >\n"+
							      "<div class='title' style='display:block'>"+rs.getString("field_name")+"<div style='float:right'></div></div>\n"+
							      "<div class=\"content\">";
						SubGrupos="";
					}
					while(rs.next()) {
						if(rs.getString("field_type").equals("Grupo")) {
							dados_tabela=dados_tabela+"\n</ons-card>\n";
							dados_tabela=dados_tabela+"<ons-card >\n"+
					      "<div class='title' style='display:block'>"+rs.getString("field_name")+"<div style='float:right'></div></div>\n"+
					      "<div class=\"content\">";
							SubGrupos="";
					      
					}else if(rs.getString("field_type").equals("SubGrupo")){
						SubGrupos=SubGrupos+rs.getString("field_name")+" > ";
						SubAux = "<div>"+SubGrupos+"</div><hr>";	
					}else {
						    dados_tabela=dados_tabela+SubAux;
						    if(dados_tabela.length()==0) {
						    	dados_tabela=dados_tabela+"<ons-card ><div>"+rs.getString("field_name")+" - "+rs.getString("tipo")+"</div><hr><br>";
							}else {
								dados_tabela=dados_tabela+"</ons-card><ons-card ><div>"+rs.getString("field_name")+" - "+rs.getString("tipo")+"</div><hr><br>";
							}
							
							if(rs.getString("tipo").equals("Foto")) {
								dados_tabela=dados_tabela+"<section style='padding:5px'><img src='img/add_foto.png' height=\"80\" width=\"80\" onclick=\"foto_checklist("+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+",'NOVA')\"></section></div>";
							}else if(rs.getString("tipo").equals("Texto")){
								dados_tabela=dados_tabela+"<section style='padding:5px'><ons-input modifier=\"material\" type=\"text\" placeholder=\"Insira o texto aqui\" onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+",'NOVA')\" float></ons-input></section></div>";
							}else if(rs.getString("tipo").equals("Data")){
								dados_tabela=dados_tabela+"<section style='padding:5px'><ons-input modifier=\"material\" type=\"date\" placeholder=\"Insira o texto aqui\" onchange=\"atualiza_texto_checklist(this.value,"+rs.getString("field_id")+",'"+rs.getString("field_name")+"',"+rs.getString("relatorio_id")+",'NOVA')\" float></ons-input></section></div>";
							}else {
								dados_tabela=dados_tabela+"<section style='padding:5px'></section></div>";
							}
							SubAux="";
						}
					     // + "<section style='padding:10px'><ons-button modifier=\"large\" style=\"border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','"+rs.getString("status_atividade")+"','"+rs.getString("value_atbr_field")+"')\">"+bt_texto+"</ons-button><ons-button modifier=\"large\" style=\"background:green;border-radius: 5%;height:30px;text-align:center;display:table-cell;vertical-align:middle;font-size:30px;margin: auto;\" onclick=\"atualiza_rollout("+rs.getString("recid")+",'"+rs.getString("milestone")+"','Finalizada','"+rs.getString("value_atbr_field")+"')\">Completar</ons-button></section></div>\n"+
					   }
					dados_tabela=dados_tabela+"\n</ons-card>\n";
					dados_tabela=dados_tabela+"<ons-button modifier=\"large\" onclick=\"submete_checklist(0)\">Submeter</ons-button>";
					System.out.println(dados_tabela);
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				}
				}}
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
				
			}else if(opt.equals("53")) {
				System.out.println("guardando foto do checklist");
				param1=req.getParameter("idCampo");
				param2=req.getParameter("CampoNome");
				param3=req.getParameter("idRelatorio");
				param4=req.getParameter("idvistoria");
				param5=req.getParameter("recid");
				param6=req.getParameter("site");
				param7=req.getParameter("milestone");
				System.out.println("idcampo:"+param1);
				int id_vistoria=0;
				if(param4.equals("NOVA")) {
					rs=mysql.Consulta("select id_vistoria from vistoria_dados where empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_vistoria desc limit 1");
					if(rs.next()) {
						id_vistoria=rs.getInt(1)+1;
					}else {
						id_vistoria=1;
					}
				}else {
					id_vistoria=Integer.parseInt(param4);
				}
				String imageBase64 = req.getParameter("image64");
				System.out.println(imageBase64);
				//System.out.println(imageBase64);
				byte[] imageByte= Base64.decodeBase64(imageBase64);
				InputStream inputStream2= new ByteArrayInputStream(imageByte);
				query="";
							query="insert into vistoria_dados (id_vistoria,campo,campo_id,campo_valor_foto,relatorio_id,empresa,recid,SiteID,milestone,owner,dt_updated,update_by,campo_tipo,status_vistoria,executor_checklist,campo_valor_str) values ("+id_vistoria+",'"+param2+"',"+param1+",?,"+param3+","+p.getEmpresaObj().getEmpresa_id()+","+param5+",'"+param6+"','"+param7+"','"+p.get_PessoaUsuario()+"','"+time+"','"+p.get_PessoaUsuario()+"','Foto','ABERTA','"+p.get_PessoaUsuario()+"',?)";
							PreparedStatement statement;
							 
							 statement = mysql.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
							 statement.setBlob(1, inputStream2);
							 statement.setString(2, imageBase64);
							 int row = statement.executeUpdate();
							 
						     statement.getConnection().commit();
						     mysql.getConnection().commit();
						     if (row>0) {
						        	//System.out.println("Foto de Registro salva com sucesso");
						    	    ResultSet auxrs = statement.getGeneratedKeys();
						    	    int vistoria_dados_id=0;
						    	    if(auxrs.next()) {
						    	    	vistoria_dados_id=auxrs.getInt(1);
						    	    }
						        	statement.close();
						        	Document rolloutUpdate = new Document();
						        	Document rolloutfiltro = new Document();
						        	rolloutfiltro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
						        	rolloutfiltro.append("recid", Integer.parseInt(param5));
						        	rs=mysql.Consulta("select relatorio_nome from vistoria_report where id="+param3);
						        	if(rs.next()) {
						        		rolloutUpdate.append("ChecklistNome", rs.getString(1));
						        	}else {
						        		rolloutUpdate.append("ChecklistNome", "Nome não Encontrado");
						        	}
						        	
						        	rolloutUpdate.append("ChecklistStatus", "Iniciado");
						        	Document ComandoUpdate = new Document();
						        	ComandoUpdate.append("$set", rolloutUpdate);
						        	mongo.AtualizaUm("rollout", rolloutfiltro, ComandoUpdate);
						        	query="insert into vistoria_dados_log (vistoria_dados_id,vistoria_dados_operacao,dt_operacao,status_operacao,usuario_operacao,empresa) values ("+vistoria_dados_id+",'ENVIO','"+time+"','SUCESSO','"+p.get_PessoaUsuario()+"',"+p.getEmpresaObj().getEmpresa_id()+")";
									mysql.Inserir_simples(query);
						     }
					    
				
				mongo.fecharConexao();
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("OK");
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("54")) {
				param1=req.getParameter("id_dados");
				ServletOutputStream out = resp.getOutputStream();
				query="";
				Blob image = null;
				
				query="select campo_valor_foto from vistoria_dados where id="+param1+" and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' and status_vistoria<>'CANCELADO'";
				rs=mysql.Consulta(query);
				if(rs.next()) {
					//System.out.println(" foto encontrada");
					image=rs.getBlob(1);
					
					byte byteArray[]=image.getBytes(1, (int) image.length());
					resp.setContentType("image/png");
					out.write(byteArray);
					out.flush();
					out.close();
					//System.out.println(" foto carregada");
					
				}
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
			}else if(opt.equals("55")) {
				param1=req.getParameter("id_dados");
				query="update vistoria_dados set status_vistoria='CANCELADO',dt_updated='"+time+"',update_by='"+p.get_PessoaUsuario()+"' where id="+param1;
				mysql.Alterar(query);
			}else if(opt.equals("56")) {
				param1=req.getParameter("idVistoria");
				String flag="Y";
				query="select status_vistoria from vistoria_dados where id_vistoria="+param1+" and empresa="+p.getEmpresaObj().getEmpresa_id();
				rs=mysql.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					while(rs.next()) {
						if(rs.getString(1).equals("APROVADO")) {
							flag="N";
						}else if(rs.getString(1).equals("EM_APROVACAO")) {
							flag="N";
						}
					}
				}
				if(flag.equals("Y")) {
				query="update vistoria_dados set status_vistoria='CANCELADO',dt_updated='"+time+"',update_by='"+p.get_PessoaUsuario()+"' where id_vistoria="+param1+" and empresa="+p.getEmpresaObj().getEmpresa_id()+" and owner='"+p.get_PessoaUsuario()+"'";
				if(mysql.Alterar(query)) {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Checklist Eliminado com Sucesso!");
				}
				}else {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Checklist não pode ser eliminado, pois possui itens aprovados ou em aprovação");
				}
			}else if(opt.equals("57")) {
				param1=req.getParameter("idVistoria");
				param2 = req.getParameter("recid");
				query="update vistoria_dados set status_vistoria='EM_APROVACAO',dt_updated='"+time+"',update_by='"+p.get_PessoaUsuario()+"' where id_vistoria="+param1+" and empresa="+p.getEmpresaObj().getEmpresa_id()+" and owner='"+p.get_PessoaUsuario()+"' and status_vistoria in ('ABERTA','REJEITADO')";
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				if(mysql.Alterar(query)) {
					Document rolloutUpdate = new Document();
		        	Document rolloutfiltro = new Document();
		        	rolloutfiltro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
		        	rolloutfiltro.append("recid", Integer.parseInt(param2));
		        	
		        	rolloutUpdate.append("ChecklistStatus", "Em Aprovação");
		        	Document ComandoUpdate = new Document();
		        	ComandoUpdate.append("$set", rolloutUpdate);
		        	mongo.AtualizaUm("rollout", rolloutfiltro, ComandoUpdate);
					out.print("Checklist Enviado para aprovação com Sucesso!");
				}else {
					out.print("Checklist não pode ser reenviado para aprovação pois nao existem itens a serem aprovados!");
				}
			}else if(opt.equals("58")) {
				String param8;
				param1=req.getParameter("campoID");
				param2=req.getParameter("campoNome");
				param3=req.getParameter("relatorioID");
				param4=req.getParameter("idVistoria");
				param5=req.getParameter("recid");
				param6=req.getParameter("site");
				param7=req.getParameter("milestone");
				param8=req.getParameter("valor");
				//System.out.println("idcampo:"+param1);
				int id_vistoria=0;
				if(param4.equals("NOVA")) {
					rs=mysql.Consulta("select id_vistoria from vistoria_dados where empresa="+p.getEmpresaObj().getEmpresa_id()+" order by id_vistoria desc limit 1");
					if(rs.next()) {
						id_vistoria=rs.getInt(1)+1;
					}else {
						id_vistoria=1;
					}
					query="";
					query="insert into vistoria_dados (id_vistoria,campo,campo_id,campo_valor_str,relatorio_id,empresa,recid,SiteID,milestone,owner,dt_updated,update_by,campo_tipo,status_vistoria,executor_checklist) values ("+id_vistoria+",'"+param2+"',"+param1+",'"+param8+"',"+param3+","+p.getEmpresaObj().getEmpresa_id()+","+param5+",'"+param6+"','"+param7+"','"+p.get_PessoaUsuario()+"','"+time+"','"+p.get_PessoaUsuario()+"','Texto','ABERTA','"+p.get_PessoaUsuario()+"')";
					mysql.Inserir_simples(query);
					Document rolloutUpdate = new Document();
		        	Document rolloutfiltro = new Document();
		        	rolloutfiltro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
		        	rolloutfiltro.append("recid", Integer.parseInt(param5));
		        	rs=mysql.Consulta("select relatorio_nome from vistoria_report where id="+param3);
		        	if(rs.next()) {
		        		rolloutUpdate.append("ChecklistNome", rs.getString(1));
		        	}else {
		        		rolloutUpdate.append("ChecklistNome", "Nome não Encontrado");
		        	}
		        	
		        	rolloutUpdate.append("ChecklistStatus", "Iniciado");
		        	Document ComandoUpdate = new Document();
		        	ComandoUpdate.append("$set", rolloutUpdate);
		        	mongo.AtualizaUm("rollout", rolloutfiltro, ComandoUpdate);
				}else {
					id_vistoria=Integer.parseInt(param4);
					query="";
					query="update vistoria_dados set campo_valor_str='"+param8+"',dt_updated='"+time+"',update_by='"+p.get_PessoaUsuario()+"' where id_vistoria="+id_vistoria+" and campo='"+param2+"' and relatorio_id="+param3+" and empresa="+p.getEmpresaObj().getEmpresa_id()+" and recid="+param5;
					if(mysql.Alterar2(query)>0){
						
					}else {
						query="";
						query="insert into vistoria_dados (id_vistoria,campo,campo_id,campo_valor_str,relatorio_id,empresa,recid,SiteID,milestone,owner,dt_updated,update_by,campo_tipo,status_vistoria,executor_checklist) values ("+id_vistoria+",'"+param2+"',"+param1+",'"+param8+"',"+param3+","+p.getEmpresaObj().getEmpresa_id()+","+param5+",'"+param6+"','"+param7+"','"+p.get_PessoaUsuario()+"','"+time+"','"+p.get_PessoaUsuario()+"','Texto','ABERTA','"+p.get_PessoaUsuario()+"')";
						mysql.Inserir_simples(query);
						Document rolloutUpdate = new Document();
			        	Document rolloutfiltro = new Document();
			        	rolloutfiltro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
			        	rolloutfiltro.append("recid", Integer.parseInt(param5));
			        	rs=mysql.Consulta("select relatorio_nome from vistoria_report where id="+param3);
			        	if(rs.next()) {
			        		rolloutUpdate.append("ChecklistNome", rs.getString(1));
			        	}else {
			        		rolloutUpdate.append("ChecklistNome", "Nome não Encontrado");
			        	}
			        	
			        	rolloutUpdate.append("ChecklistStatus", "Iniciado");
			        	Document ComandoUpdate = new Document();
			        	ComandoUpdate.append("$set", rolloutUpdate);
			        	mongo.AtualizaUm("rollout", rolloutfiltro, ComandoUpdate);
					}
				}
				
				
				
				
			}else if(opt.equals("59")) {
				Bson filtro;
				dados_tabela="";
				System.out.println("Buscando Menu principal");
				List<Bson> filtros = new ArrayList<>();
				filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresa_id());
				filtros.add(filtro);
				filtro= Filters.eq("Usuario",p.get_PessoaUsuario());
				filtros.add(filtro);
				FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("UsuarioModulo", filtros);
				MongoCursor<Document> resultado = findIterable.iterator();
				if(resultado.hasNext()) {
					Document menu;
					System.out.println("Montando Menus");
					while(resultado.hasNext()) {
						menu = resultado.next();
						List<Document> modulos = (List<Document>) menu.get("Modulos");
						for(int cont=0;cont<modulos.size();cont++) {
							if(modulos.get(cont).getString("nome").equals("Registro")) {
								dados_tabela=dados_tabela+"<ons-card class=\"animated bounceInUp\" onclick=\"navega('home.html');atualiza_origem_registro('registro')\">"+
						        "<div class=\"title\">Registro</div>"+
						          "<div class=\"content\">"+
						           " <div class=\"left\">Marcação de Ponto</div>"+
						            "<div class=\"right\"><ons-icon size=\"20px\" icon=\"fa-map-pin\"></ons-icon></div>"+
						          "</div>"+
						    "</ons-card>";
							}else if(modulos.get(cont).getString("nome").equals("Sites")) {
								dados_tabela=dados_tabela+"<ons-card class=\"animated bounceInUp\" onclick=\"navega('sites')\">"+
						        "<div class=\"title\">Sites</div>"+
						        "<div class=\"content\">Registro em Sites</div>"+
						      "</ons-card>";
							}else if(modulos.get(cont).getString("nome").equals("BancoHoras")) {
								
								dados_tabela=dados_tabela+"<ons-card class=\"animated bounceInUp\" onclick=\"navega('about.html');carrega_saldo_horas();carrega_horas();\">"+
							      "<div class=\"title\">Banco de Horas</div>"+
							      "<div class=\"content\">Meu saldo de Horas : <label id=\"saldo_horas_Label\"></label></div>"+
							    "</ons-card>";
							}else if(modulos.get(cont).getString("nome").equals("Atividades")) {
								
								dados_tabela=dados_tabela+"<ons-card class=\"animated bounceInUp\" onclick=\"navega('Atividades_info')\">\n" + 
										"      <div class=\"title\">Atividades</div>\n" + 
										"      <div class=\"content\"><label>Minhas Atividades : </label><div class=\"lbltaskcount\" id=\"quantidade_atividades_label\" style=\"font-size:32px;color:white;display:inline-block\" ><ons-progress-circular indeterminate></ons-progress-circular></div></div>\n" + 
										"    </ons-card>";
							}else if(modulos.get(cont).getString("nome").equals("Equipes")) {
								
								dados_tabela=dados_tabela+"<ons-card class=\"animated bounceInUp\" style=\"background:gray;\" onclick=\"navega('Equipes_map')\">\n" + 
										"      <div class=\"title\">Equipes</div>\n" + 
										"      <div class=\"content\">Equipes em campo </div>\n" + 
										"    </ons-card>";
							}
						}
					}
				}else {
					System.out.println("Usuario nao possui acesso aos modulos");
					dados_tabela = "Menu nao Encontrado";
				}
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
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
			} catch(NullPointerException e1)
	        {
	            System.out.println("pegou algum null point");
				System.out.println(e1.getMessage());
	            System.out.println(e1.getCause());
	            System.out.println(e1.getStackTrace());
	            e1.printStackTrace();
	        }  catch (ParseException e) {
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
	 public void insere_regitro(Pessoa p,String usuario,String tipo_registro, Conexao mysql,String lat,String lng,String timestam,String distancia,String datetime,String Localidade) {
				String query,query2,insere;
				Locale brasil = new Locale("pt", "BR");
				String param1,param2,param3,param4,param5;
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
				Document registro=new Document();
				Document geo = new Document();
				Document geometry = new Document();
				Document properties = new Document();
				
				ConexaoMongo mongo = new ConexaoMongo();
				array_string_aux=Localidade.split(",");
				
				try {
				
				
				
				data = format.parse(timestam);
				d.setTime(data);
				int aux_hora=d.get(Calendar.HOUR_OF_DAY);
				int aux_min=d.get(Calendar.MINUTE);
				date_sql = new java.sql.Date(d.getTime().getTime());
				//System.out.println(param5.substring(param5.indexOf(" ")+1));
				insere="";
					insere="INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes) "
							+ "VALUES ('1','"+p.getEmpresaObj().getEmpresa_id()+"','"+usuario+"','"+param1+"','"+param2+"','"+f2.format(d.getTime())+"',"+param4+",'"+param5+"','"+date_sql.toString()+" "+param5.substring(param5.indexOf(" ")+1)+"',"+aux_hora+","+aux_min+",'"+tipo_registro+"','"+array_string_aux[0]+"','"+array_string_aux[1]+"','"+array_string_aux[2]+"',"+(d.get(Calendar.MONTH)+1)+")";
					//System.out.println(insere);
					if(mysql.Inserir_simples(insere)){
			    		System.out.println("Registro Cadastrado");
			    		geo.append("type", "Feature");
						geometry.append("type", "Point");
						geometry.append("coordinates", verifica_coordenadas(param2,param1));
						geo.append("geometry",geometry);
						properties.append("Usuario", p.get_PessoaUsuario());
						properties.append("Local_Registro", array_string_aux[1]);
						properties.append("Tipo_local", array_string_aux[0]);
						properties.append("Hora_Registro", f3.format(d.getTime()));
						properties.append("Distancia_local", param4);
						properties.append("Coordenadas", param1+","+param2);
						geo.append("properties", properties);
    					registro.append("Usuario", p.get_PessoaUsuario());
    					registro.append("Empresa", p.getEmpresaObj().getEmpresa_id());
    					registro.append("data_dia", d.getTime());
    					registro.append("data_dia_string", f2.format(d.getTime()));
    					registro.append("datetime_mobile", param5);
    					//System.out.println("hora formartada:"+ checa_formato_data_e_hora(f3.format(mobile_time.getTime())));
    					registro.append("datetime_mobile_data", checa_formato_data_e_hora(f3.format(d.getTime())));
    					registro.append("datetime_servlet", date_sql);
    					registro.append("hora", d.get(Calendar.HOUR_OF_DAY));
    					registro.append("minuto", d.get(Calendar.MINUTE));
    					registro.append("dia", d.get(Calendar.DAY_OF_MONTH));
    					registro.append("mes", (d.get(Calendar.MONTH)+1));
    					registro.append("ano", d.get(Calendar.YEAR));
    					registro.append("tipo_registro", tipo_registro);
    					registro.append("local_registro", array_string_aux[0]);
    					registro.append("distancia", param4);
    					registro.append("tipo_local_registro", array_string_aux[1]);
    					registro.append("site_operadora_registro", array_string_aux[2]);
    					registro.append("timeStamp_mobile", param3);
    					registro.append("GEO", geo);
			    		mongo.InserirSimpels("Registros", registro);	
					}
					if(tipo_registro.equals("Saída")) {
						format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//System.out.println("entrou no calculo de horas");
						String dateStart = "";
						String dateStop = "";
						String exp_entrada="";
						String exp_saida="";
						query="SELECT * FROM registros where usuario='"+usuario+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
						rs=mysql.Consulta(query);
						if(rs.next()) {
							dateStart=rs.getString("datetime_servlet");
							exp_entrada=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
							//exp_entrada=exp_entrada+" 08:10:00";
						}
						query="SELECT * FROM registros where usuario='"+usuario+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
						rs=mysql.Consulta(query);
						if(rs.next()) {
							dateStop=rs.getString("datetime_servlet");
							exp_saida=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
							//exp_saida=exp_saida+" 18:30:00";
						}
						query="SELECT entrada,saida FROM expediente";
						rs=mysql.Consulta(query);
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
						rs=mysql.Consulta(query);
						
						
						if(rs.next()) {
							query2="update horas_extras set he_quantidade="+numberFormat.format(horas_normais)+",horas_noturnas="+numberFormat.format(horas_noturnas)+",entrada='"+f3.format(hora_entrada.getTime())+"',saida='"+f3.format(hora_saida.getTime())+"',origen='Automatico - MSTP WEB',aprovada='Y',compensada='N' where id_usuario='"+usuario+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
							mysql.Alterar(query2);
						}else {
							insere="";
							insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add) values('"+usuario+"','"+f2.format(time)+"',"+numberFormat.format(horas_normais)+",'"+f3.format(hora_entrada.getTime())+"','"+f3.format(hora_saida.getTime())+"',"+numberFormat.format(horas_noturnas)+",'Y','MANUAL - MSTP WEB','N','"+f3.format(time)+"')";
							
							mysql.Inserir_simples(insere);
						
						}
						}}
				mongo.fecharConexao();	
				}
						 catch (ParseException e) {
						    e.printStackTrace();
						    mongo.fecharConexao();	
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							mongo.fecharConexao();	
						} 
					}		
	 public Date checa_formato_data(String data) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				return format.parse(data);
				
			}catch (ParseException e) {
				//System.out.println(data + " - Data inválida");
				return null;
				
			} 
		}
	 public Date checa_formato_data_e_hora(String data) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				
				return format.parse(data);
				
			}catch (ParseException e) {
				//System.out.println(data + " - Data inválida");
				return null;
				
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
				if(!entrada.equals("") && !saida.equals("")) {
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
				}
				return retorno;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	 public List<Double> verifica_coordenadas(String lng,String lat) {
		 try {
			 Double f_lat=Double.parseDouble(lat.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
			 Double f_lng=Double.parseDouble(lng.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
			 return Arrays.asList(f_lng,f_lat);
		 }catch (NumberFormatException e) {
				
				
				return Arrays.asList(-10.00,-10.00);
		}
	 }
	 public void envia_mensagem(String mensagem, String usuario_alvo) {
		 try {
			   String jsonResponse;
			  // System.out.println("controle1");
			   URL url = new URL("https://onesignal.com/api/v1/notifications");
			   HttpURLConnection con = (HttpURLConnection)url.openConnection();
			   con.setUseCaches(false);
			   con.setDoOutput(true);
			   con.setDoInput(true);
			  // System.out.println("controle2");
			   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			   con.setRequestProperty("Authorization", "Basic ZTFhZmE3YTItMDczNC00OWM3LTk0ZTgtMzUzYjg5OTY1ZGFj");
			   con.setRequestMethod("POST");
			  // System.out.println("controle3");
			   String strJsonBody = "{"
			                      +   "\"app_id\": \"ae9ad50e-520d-436a-b0b0-23aaddedee7b\","
			                      +   "\"filters\": [{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+usuario_alvo+"\"},{\"operator\": \"OR\"},{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+usuario_alvo.toLowerCase()+"\"},{\"operator\": \"OR\"}, {\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+usuario_alvo.toUpperCase()+"\"}],"
			                      +   "\"data\": {\"foo\": \"bar\"},"
			                      +   "\"contents\": {\"en\": \""+mensagem+"\"}"
			                      + "}";
			         
			   
			   //System.out.println("strJsonBody:\n" + strJsonBody);

			   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			   con.setFixedLengthStreamingMode(sendBytes.length);
			  // System.out.println("controle4");
			   OutputStream outputStream = con.getOutputStream();
			   outputStream.write(sendBytes);

			   int httpResponse = con.getResponseCode();
			  // System.out.println("httpResponse: " + httpResponse);

			   if (  httpResponse >= HttpURLConnection.HTTP_OK
			      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
			      Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   else {
			      Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   //System.out.println("jsonResponse:\n" + jsonResponse);
			   
			} catch(Throwable t) {
			   t.printStackTrace();
			}


	 }
	 public void CorrigeCoordenadasTabelaSites() {
		 int contador=0;
		 try {
			System.out.println("iniando ajustes de coordenadas da empresa 5");
			ConexaoMongo conexao= new ConexaoMongo();
			Bson filtro ;
			Document site;
			Document update;
			Document comando;
			Document condicao;
			List<Double> coordenadas;
			filtro=Filters.eq("Empresa",5);
			List<Bson> filtros = new ArrayList<>();
			filtros.add(filtro);
			filtro=Filters.eq("site_ativo","Y");
			filtros.add(filtro);
			
			
			FindIterable<Document> findIterable= conexao.ConsultaCollectioncomFiltrosLista("sites", filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			while(resultado.hasNext()) {
				condicao=new Document();
				update=new Document();
				comando=new Document();
				site=resultado.next();
				condicao.append("Empresa", 5);
				condicao.append("site_id",site.getString("site_id"));
				
				coordenadas=verifica_coordenadas(site.getString("site_longitude"), site.getString("site_latitude"));
				update.append("GEO.geometry.coordinates", coordenadas);
				
				
				comando.append("$set", update);
				//System.out.println(comando.toJson());
				conexao.AtualizaUm("sites", condicao, comando);
				contador=contador+1;
				}
			System.out.println(contador + "Sites atualizados");
			
		 }catch (Exception e) {
			 System.out.println(contador + "Sites atualizados");
		}
	 }
}
