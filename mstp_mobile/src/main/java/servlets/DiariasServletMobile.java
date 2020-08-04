package servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.EmailException;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import Classes.Conexao;
import Classes.ConexaoMongo;
import Classes.Despesa;
import Classes.EspelhoDiaria;
import Classes.Pessoa;
import Classes.Semail;

/**
 * Servlet implementation class DiariasServlet
 */
@WebServlet("/DiariasServletMobile")
public class DiariasServletMobile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiariasServletMobile() {
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
		gerenciamento_diarias(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamento_diarias(request, response);
	}
	public void gerenciamento_diarias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		EspelhoDiaria espelho= new EspelhoDiaria();
		Conexao mysql = (Conexao) session.getAttribute("conexao");
		String opt="";
		String param1="";
		String param2="";
		ConexaoMongo mongo = new ConexaoMongo();
		opt=request.getParameter("opt");
		
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null) {
			response.sendRedirect("./mstp_login.html");
			return;
		}
		DateFormat f3 = DateFormat.getDateTimeInstance();
		if(opt.equals("1")) {
			try {
				String param3="";
				param1=request.getParameter("func");
				param2=request.getParameter("week");
				param3=request.getParameter("saldo");
				espelho.setFunc_espelho(param1);
				espelho.setSemana_espelho(param2);
				espelho.setSaldo_espelho(param3);
				espelho.setEmpresa(p.getEmpresaObj().getEmpresaId());
				espelho.SalvaEspelho(mongo);
			}catch(Exception e) {
				try {
					Semail email = new Semail();
					email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com","MSTP WEB - Erro Servlet Diaria", "Erro servlet diaria opt 1");
				} catch (EmailException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}if(opt.equals("2")) {
			Despesa despesa = new Despesa();
			UUID chave;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar dataDespesa = Calendar.getInstance();
			Timestamp time = new Timestamp(System.currentTimeMillis());
			try {
				dataDespesa.setTime(format.parse(request.getParameter("dataDespesa")));
				chave=UUID.randomUUID();
				
				despesa.setId_despesa(chave);
				despesa.setAno_despesa(dataDespesa.get(Calendar.YEAR));
				despesa.setDia_despesa(dataDespesa.get(Calendar.DAY_OF_MONTH));
				despesa.setMes_despesa(dataDespesa.get(Calendar.MONTH)+1);
				despesa.setSemana_despesa(dataDespesa.get(Calendar.WEEK_OF_YEAR));
				despesa.setData_despesa(request.getParameter("dataDespesa"));
				despesa.setDt_despesa(dataDespesa);
				param1=request.getParameter("valorDespesa");
				//System.out.println(param1);
				despesa.setValor_despesa(param1);
				despesa.setSite_despesa(request.getParameter("siteDespesa"));
				//despesa.setProjetoIdDespesa(Integer.parseInt(request.getParameter("projetoDespesa")));
				despesa.setProjetoNomeDespesa(request.getParameter("projetoDespesa"));
				despesa.setDescricao_despesa(request.getParameter("motivoDespesa"));
				despesa.setStatus_pagamento("PENDENTE");
				despesa.setStatusDespesa("EM REVISÃO");
				despesa.setCategoriaDespesa(request.getParameter("categoriaDespesa"));
				despesa.setObservacoes(request.getParameter("obsDespesa"));
				despesa.setUsuarioDespesa(p.get_PessoaUsuario());
				despesa.setOwner(p.getPessoaLider());
				despesa.registraDespesa(mongo, p);
				response.setContentType("application/html");  
		  		response.setCharacterEncoding("UTF-8"); 
		  		PrintWriter out = response.getWriter();
				out.print(despesa.getId_despesa());
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNomeFantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Diarias opt -  "+ opt+".Registro de Despesa. tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(opt.equals("3")) {
				String query;
				Timestamp time = new Timestamp(System.currentTimeMillis());
				String imageBase64 = request.getParameter("image64");
				String idDespesa = request.getParameter("idDespesa");
				//System.out.println(imageBase64);
				byte[] imageByte= Base64.decodeBase64(imageBase64);
				InputStream inputStream2= new ByteArrayInputStream(imageByte);
				query="";
				query="insert into notaImagem (id_despesa,usuario,dt_add,nota) values('"+idDespesa+"','"+p.get_PessoaUsuario()+"','"+time+"',?) ";
				 try {
					 PreparedStatement statement;
					 statement = mysql.getConnection().prepareStatement(query);
					 statement.setBlob(1, inputStream2);
					 int row = statement.executeUpdate();
					 statement.getConnection().commit();
					if (row>0) {
						  System.out.println("Foto Nota salvo com sucesso");
						  statement.close();
					}
				 } catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}   
				
				mongo.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNomeFantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de operações opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
		}else if(opt.equals("4")) {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String dados_tabela;
			//double money; 
			NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
			//String moneyString;
			dados_tabela="{\"draw\": 1,\n";
			dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
			dados_tabela=dados_tabela+"\"recordsFiltered\": 5 ,\n";
			dados_tabela=dados_tabela+"\"data\":[\n";
			List<Bson> filtros = new ArrayList<>();
			Document despesa;
			Bson filtro;
			filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresaId());
			filtros.add(filtro);
			filtro=Filters.not(Filters.eq("status_pagamento","PAGO"));
			filtros.add(filtro);
			filtro=Filters.not(Filters.eq("statusDespesa", "DELETADO"));
			filtros.add(filtro);
			
			FindIterable<Document> findIterable = mongo.ConsultaOrdenadaFiltroLista("Despesas", "dt_registro", -1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			int contador=0;
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					despesa=resultado.next();
					contador=contador+1;
					dados_tabela=dados_tabela+"[";
					dados_tabela=dados_tabela+"\""+despesa.get("id_despesa")+"\",";
					dados_tabela=dados_tabela+"\""+despesa.getString("data_despesa")+"\",";
					dados_tabela=dados_tabela+"\""+despesa.getString("descricao_despesa")+"\",";
					dados_tabela=dados_tabela+"\""+number_formatter.format(despesa.getDouble("valor_despesa"))+"\",";
					dados_tabela=dados_tabela+"\""+despesa.getString("statusDespesa")+"\"],";
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
				dados_tabela=dados_tabela+"]}";
				dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
			}else {
				dados_tabela=dados_tabela+"]}";
				dados_tabela=dados_tabela.replace("replace1", "0");
			}
			//System.out.println(dados_tabela);
			response.setContentType("application/json");  
			response.setCharacterEncoding("UTF-8"); 
			PrintWriter out = response.getWriter();
			out.print(dados_tabela);
			mongo.fecharConexao();
			
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNomeFantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Despesas opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
	
		}else if(opt.equals("5")) {
			param1=request.getParameter("idDespesa");
			Timestamp time = new Timestamp(System.currentTimeMillis());
			Document despesafiltro=new Document();
			Document despesaupdate=new Document();
			Document despesacomando=new Document();
			despesafiltro.append("id_despesa",UUID.fromString(param1));
			despesafiltro.append("Empresa",p.getEmpresaObj().getEmpresaId());
			despesafiltro.append("statusDespesa","EM REVISÃO");
			despesaupdate.append("statusDespesa", "DELETADO");
			despesaupdate.append("dt_ultima_mudanca", time);
			despesacomando.append("$set", despesaupdate);
			mongo.AtualizaUm("Despesas", despesafiltro, despesacomando);
			
			mongo.fecharConexao();
			response.setContentType("application/json");  
			response.setCharacterEncoding("UTF-8"); 
			PrintWriter out = response.getWriter();
			out.print("Despesa Deletada com Sucesso!");
			mongo.fecharConexao();
			
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNomeFantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Despesas opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
	
		}else if(opt.equals("6")) {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			try {
			System.out.println("1");
			String valores_Aprovados="";
			String valores_naprovado="";
			Double valor;
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			List<Bson> filtros = new ArrayList<>();
			Bson filtro;
			filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresaId());
			filtros.add(filtro);
			filtro=Filters.eq("status_pagamento","PENDENTE");
			filtros.add(filtro);
			filtro=Filters.eq("statusDespesa", "APROVADA");
			filtros.add(filtro);
			Document soma_valor=mongo.ConsultaSomaCampo("Despesas",filtros,"valor_despesa");
			System.out.println(soma_valor.toJson());
			if(soma_valor.get("sum")!=null) {
				valor=soma_valor.getDouble("sum");
				valores_Aprovados = formatter.format(valor);
				
			}else {
				valores_Aprovados = formatter.format(0.0);
			}
			System.out.println("2");
			filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresaId());
			filtros.add(filtro);
			filtro=Filters.eq("status_pagamento","PENDENTE");
			filtros.add(filtro);
			filtro=Filters.not(Filters.eq("statusDespesa", "APROVADA"));
			filtros.add(filtro);
			filtro=Filters.not(Filters.eq("statusDespesa", "DELETADO"));
			filtros.add(filtro);
			soma_valor=mongo.ConsultaSomaCampo("Despesas",filtros,"valor_despesa");
			System.out.println(soma_valor.toJson());
			if(soma_valor.get("sum")!=null) {
				valor=soma_valor.getDouble("sum");
				valores_naprovado = formatter.format(valor);
				
			}else {
				valores_naprovado = formatter.format(0.0);
			}
			System.out.println("2");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = response.getWriter();
		    out.print("[\"0,00\",\""+valores_naprovado+"\",\""+valores_Aprovados+"\"]");
		    mongo.fecharConexao();
			}catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
				System.out.println(e.getCause());
				System.out.println(e.getMessage());
			}
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNomeFantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Despesas opt -  "+ opt+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
	
		}else if(opt.equals("7")) {
			List<Bson> filtros = new ArrayList<>();
			Bson filtro;
			Document categoria;
			filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresaId());
			filtros.add(filtro);
			FindIterable<Document> findIterable = mongo.ConsultaOrdenadaFiltroLista("CategoriaDespesa", "dt_registro", -1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			String dados_tabela="<option value=\"\"></option>\n";
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					categoria=resultado.next();
					dados_tabela=dados_tabela+"<option value=\""+categoria.getString("Categoria")+"\">"+categoria.getString("Categoria")+"</option>\n";
				}
			}
			//System.out.println(dados_tabela);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = response.getWriter();
		    out.print(dados_tabela);
		    mongo.fecharConexao();
		}else if(opt.equals("8")) {
			List<Bson> filtros = new ArrayList<>();
			Bson filtro;
			Document categoria;
			filtro=Filters.eq("Empresa",p.getEmpresaObj().getEmpresaId());
			filtros.add(filtro);
			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("Projetos", filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			String dados_tabela="<option value=\"\"></option>\n";
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					categoria=resultado.next();
					dados_tabela=dados_tabela+"<option value=\""+categoria.getString("Projeto")+"\">"+categoria.getString("Projeto")+"</option>\n";
				}
			}
			//System.out.println(dados_tabela);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = response.getWriter();
		    out.print(dados_tabela);
		    mongo.fecharConexao();
		}
		
		
	}
}
