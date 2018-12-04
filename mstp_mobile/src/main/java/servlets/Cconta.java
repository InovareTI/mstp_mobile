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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;

import Classes.Conexao;
import Classes.Semail;

/**
 * Servlet implementation class Cconta
 */
@WebServlet("/Cconta")
public class Cconta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cconta() {
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
		criaconta(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		criaconta(request, response);
	}
	
	public void criaconta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String param1;
		String param2;
		String param3;
		String param4;
		String param5;
		String query;
		String link="";
		int last_id;
		last_id=0;
		MessageDigest md;
		ResultSet rs ;
		Conexao conn = new Conexao();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f3 = DateFormat.getDateTimeInstance();
		System.out.println("Chegou no servlet de criação de contas do MSTP MOBILE - "+f3.format(time));
		param1=req.getParameter("nome");
		param2=req.getParameter("usuario");
		param3=req.getParameter("senha");
		param4=req.getParameter("email");
		param5=req.getParameter("empresa");
		try {
			md = MessageDigest.getInstance( "SHA-256" );
		
		md.update( param3.getBytes());     
        BigInteger hash = new BigInteger(1,md.digest() );     
        String retornaSenha = hash.toString(16);   
		rs=conn.Consulta("select id_usuario from usuarios where id_usuario='"+param2+"' or email='"+param4+"'");
		if(rs.next()) {
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print("Usuário ou email já existente!");
		}else {
		query="insert into usuarios (id_usuario,nome,HASH,email,perfil,empresa,ATIVO) values('"+param2+"','"+param1+"','"+retornaSenha+"','"+param4+"','User','"+param5+"','Y') ";
		if(conn.Inserir_simples(query)) {
			rs=conn.Consulta("select id_usuario_sys from usuarios order by id_usuario_sys desc limit 1");
			if(rs.next()) {
				last_id=rs.getInt(1);
			}
			rs=conn.Consulta("select email from usuarios where perfil='ADM' and empresa='"+param5+"'");
			if(rs.next()) {
			Semail email= new Semail();
			link="http://inovareti.jelasticlw.com.br/mstp_mobile/ativacc?sys="+last_id+"&cont="+rs.getString(1);
			email.enviaEmailSimples(rs.getString("email"), "MSTP - Link de Ativação","Prezado, \n Abaixo segue o link de autorização para a conta .\n"+param2+" - "+param1+"\n \n "+link);
			}
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print("Conta Criada, Aguardando Aprovação!");
			conn.fecharConexao();
		}
		}
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.fecharConexao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.fecharConexao();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.fecharConexao();
		}
	}

}
