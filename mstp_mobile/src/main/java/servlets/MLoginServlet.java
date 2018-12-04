package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Classes.Conexao;
import Classes.Pessoa;

/**
 * Servlet implementation class Lg_OdontoFlow
 */
@WebServlet("/MLoginServlet")
public class MLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MLoginServlet() {
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
		login(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		login(request, response);
	}
	
	public void login(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		System.out.println("Servlet de Login.");
		Conexao con = new Conexao();
		
		
        
        MessageDigest md;
        ResultSet rs ;
        
        System.out.println("Servlet de Login.");
		try {
			HttpSession session = req.getSession(true);
				md = MessageDigest.getInstance( "SHA-256" );
				md.update( req.getParameter("pwd").getBytes());     
		        BigInteger hash = new BigInteger(1,md.digest() );     
		        String retornaSenha = hash.toString(16);   
	        
	        
	        //System.out.println("senha : "+retornaSenha + "fim da senha.");
        	rs= con.Consulta("select * from ortodonticos_login where VALIDADO='Y' and ATIVO='Y' and HASH='"+retornaSenha+"' and (usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("user")+"')");
        	
        	if (!rs.first()){
        		System.out.println(" consulta nula");
        		rs.close();
            	con.fecharConexao();
            	resp.sendRedirect("./error.html");
        	}
        	else{
        		
        		//HttpSession session = req.getSession();
                session.setAttribute("conexao",con);
                Pessoa p=new Pessoa();
                p.set_PessoaUsuario(rs.getString("usuario"));
                //p.setEmail(rs.getString("email"));
               // p.set_Pessoa_cro(rs.getString("cro"));
                p.set_PessoaDocCpf(rs.getString("cpf"));
                p.set_PessoaName(rs.getString("nome"));
                p.set_PessoaUltimoLogin(rs.getString("ultimologin"));
               // p.set_PessoaMinhaEmpresa(rs.getString("empresa_socia"));
               // p.set_PessoaCor(rs.getString("cor"));
               // p.set_PessoaMinhaEmpresaNome(con);
               // p.set_PessoaRelacaoEmpresa(con);
                //System.out.println("minha empresa �:"+u.getIDEmpresa());
                session.setMaxInactiveInterval(3600);
                session.setAttribute("pessoa",p);
               // session.setAttribute("nome_empresa",p.get_PessoaMinhaEmpresaNome());
                //System.out.println(rs.getString("id_equipe"));
                //session.setAttribute("user_equipe",rs.getString("id_equipe"));
                //session.setAttribute("user_empresa",rs.getString("id_empresa"));
                Timestamp time = new Timestamp(System.currentTimeMillis());
                //System.out.println("minha empresa");
                //con.Update_simples("UPDATE ortodonticos_login SET ultimologin='"+time+"' WHERE (usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("user")+"')");
               // System.out.println("minha empresa2");
                rs.close();
                rs=null;
               resp.sendRedirect("./main.jsp");
        	}
	        
		} catch (NoSuchAlgorithmException e) {
			con.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (SQLException sqle) {
			con.fecharConexao();
        	System.out.println(sqle.getMessage());
            sqle.printStackTrace();
        }catch (Exception e) {
        	con.fecharConexao();
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
		
		
		
		
	}
}
