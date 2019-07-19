package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;

import Classes.Conexao;
import Classes.Semail;

/**
 * Servlet implementation class RecuperaSenha
 */
@WebServlet("/RecuperaSenha")
public class RecuperaSenha extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperaSenha() {
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
		reinicializa_senha(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		reinicializa_senha(request, response);
	}
	
	public void reinicializa_senha(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		String param1;
		String param2;
		String param3;
		String param4;
		String opt;
		opt="";
		param1="";
		param2="";
		param3="";
		param4="";
		opt=req.getParameter("opt");
		
		MessageDigest md;
        ResultSet rs ;
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Conexao con = new Conexao();
        Locale brasil = new Locale("pt", "BR");
        DateFormat f3 = DateFormat.getDateTimeInstance();
        try {
        	System.out.println("Chegou no servlet de Operações do MSTP Mobile - "+f3.format(time));
        	if(opt.equals("1")) {
        		param1=req.getParameter("usuario");
        		param2=req.getParameter("sdt45");
        		if(param2.equals("MSTPMOBILEX2")) {
        if(!param1.equals("")){
        	if (!param1.equals(null)){
        		
        		rs=con.Consulta("select * from usuarios where id_usuario='"+param1+"' or email='"+param1+"'");
        		if(rs.next()){
				//md = MessageDigest.getInstance( "SHA-256" );
				String senha_provisoria=RandomStringUtils.random(8,true,true);
				//md.update( senha_provisoria.getBytes());     
		        //BigInteger hash = new BigInteger(1,md.digest() );     
		        //String retornaSenha = hash.toString(16);
		        if(con.Alterar("update usuarios set codigotrocaSenha='"+senha_provisoria+"' where id_usuario='"+param1+"' or email='"+param1+"'")){
		        
		        Semail email= new Semail();
				email.enviaEmailSimples(rs.getString("email"), "MSTP Mobile - Notificação de Solicitação de Senha.","Prezado "+rs.getString("nome")+", \n \n Informamos que foi solicitado código de troca de senha  no sistema MSTP. \n \n  Operação realizada em: "+time+" \n \n Caso não tenha realizado essa operação solicitamos que entre em contato com o administrador do sistema para sua empresa! \n \n \n Novos dados de Acesso: \n usuario\\email:"+param1+" \n Código para troca de senha:"+senha_provisoria+" \n \n \n Esse é um email Automático gerado pelo sistema. Favor não responder!");
			
		        }
				
        		}
        	}}
        }}else if(opt.equals("2")) {
        	param1=req.getParameter("codigo");
        	param2=req.getParameter("novasenha");
        	param3=req.getParameter("novasenha2");
        	param4=req.getParameter("user");
        	md = MessageDigest.getInstance( "SHA-256" );
			md.update( param1.getBytes());     
	        BigInteger hash = new BigInteger(1,md.digest() );     
	        String retornaSenha = hash.toString(16);
	        rs= con.Consulta3parametros("select * from usuarios where ATIVO='Y' and codigotrocaSenha=? and (id_usuario=? or email=?)",param1,param4,param4);
	        if(rs.next()) {
	        		md = MessageDigest.getInstance( "SHA-256" );
				md.update( param2.getBytes());     
		        BigInteger hash2 = new BigInteger(1,md.digest() );     
		        retornaSenha = hash2.toString(16);
		        if(con.Alterar("update usuarios set hash='"+retornaSenha+"',senha_prov='N',codigotrocaSenha='' where id_usuario='"+param4+"' or email='"+param4+"'")){
			        
			        Semail email= new Semail();
					email.enviaEmailSimples(rs.getString("email"), "MSTP Mobile - Notificação de Troca de Senha.","Prezado "+rs.getString("nome")+", \n \n Informamos que sua senha foi alterada no sistema MSTP. \n \n  Operação realizada em: "+time+" \n \n Caso não tenha realizado essa operação solicitamos que entre em contato com o administrador do sistema para sua empresa! \n \n \n  \n \n \n Esse é um email Automático gerado pelo sistema. Favor não responder!");
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Senha Alterada com Sucesso!");
			        }else {
			        	resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Erro na troca de senha!");
			        }
	        }
        }
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
