package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

import Classes.Conexao;
import Classes.Semail;

/**
 * Servlet implementation class Reg_servlet
 */
@WebServlet("/Reg_servlet")
public class Reg_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reg_servlet() {
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
		run_registro(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		run_registro(request, response);
	}
	public void run_registro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Conexao con = new Conexao();
				
				
		        
		        MessageDigest md;
		        ResultSet rs ;
		        String opt;
		        String query;
		        String dados_tabela;
		        System.out.println("Entrou no Servlet");
		        opt=req.getParameter("opt");
				try {
				if(opt.equals("2")){
					if(!req.getParameter("validaCode").equals("")){
						//System.out.println("funcao de validao de usuario");
						rs= con.Consulta("select * from ortodonticos_login where COD_VALIDACAO='"+req.getParameter("validaCode")+"'");
			        	//System.out.println("Consultou usuario");
			        	if (rs.first()){
			        		Semail email= new Semail();
			        		con.Update_simples("UPDATE ortodonticos_login SET VALIDADO='Y',ATIVO='Y' WHERE COD_VALIDACAO='"+req.getParameter("validaCode")+"'");
			        		con.Inserir_simples("insert into odonto_rating (id_usuario,feedback,feedback_1,feedback_2,feedback_3,feedback_4,feedback_5) values('"+rs.getString("usuario")+"','0','0','0','0','0','0')");
			        		email.enviaEmailSimples(rs.getString("email"), "OdontoFlow - Link de Ativação","Prezado, \n Sua conta foi ativada com sucesso.\n");
			        		email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com", "OdontoFlow - Novo Usuário Registrado","Prezado, \n nova conta ativada.\n "+rs.getString("usuario"));
			        	}
			        	con.fecharConexao();
			        	resp.sendRedirect("/odontoFlow");
					}else{
					//HttpSession session = req.getSession(true);
					Semail email= new Semail();
					md = MessageDigest.getInstance( "SHA-256" );
					md.update( req.getParameter("pwd").getBytes());     
			        BigInteger hash = new BigInteger(1,md.digest() );     
			        String retornaSenha = hash.toString(16);
			        String acesso;
			        String cli=req.getParameter("clinica");
			        int last_id=0;
			        md.update( (req.getParameter("pwd").concat(req.getParameter("user")).getBytes())); 
			        hash = new BigInteger(1,md.digest() );  
			        String cod_valida=hash.toString(16); 
		        	rs= con.Consulta("select * from ortodonticos_login where usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("email")+"'");
		        	//System.out.println("Consultou usuario");
		        	if (!rs.first()){
		        		//System.out.println("Senha ou usuario incorreto.");
		        		
		        		Timestamp time = new Timestamp(System.currentTimeMillis());
		        		
		        		if(cli.equals("nova")){
		        			con.Inserir_simples("insert into clinica (nome_clinica,cnpj,estado,socia1_cro) values('"+req.getParameter("clinica_nome")+"','0','0','"+req.getParameter("cro")+"')");
		        			rs=con.Consulta("select id_clinica from clinica where nome_clinica='"+req.getParameter("clinica_nome")+"'");
		        			if(rs.next()){
		        				last_id=rs.getInt(1);
		        			}
		        			con.Inserir_simples("insert into usuario_empresa (id_usuario,id_empresa,empresa_relacao) values('"+req.getParameter("user")+"','"+last_id+"','SOCIA')");
		        			acesso="Autorizado";
		        		}else{
		        			last_id=Integer.parseInt(req.getParameter("clinica"));
		        			acesso="BLOQUEADO";
		        		}
		        		con.Inserir_simples("INSERT INTO ortodonticos_login (usuario,NOME,EMAIL,VALIDADO,COD_VALIDACAO,ATIVO,ultimologin,HASH,cro,empresa_socia,acesso_autorizado) VALUES ('"+req.getParameter("user")+"', '"+req.getParameter("nome")+"', '"+req.getParameter("email")+"', 'N', '"+cod_valida+"', 'N', '"+time+"', '"+retornaSenha+"','"+req.getParameter("cro")+"','"+last_id+"','"+acesso+"')");
		        		//System.out.println("Inseriu usuario");
		        		
		        		email.enviaEmailSimples(req.getParameter("email"), "OdontoFlow - Link de Ativação","Prezado "+req.getParameter("nome")+", \n Agradecemos seu registro em nosso sistema. \n Para ativar sua conta basta clicar no link abaixo:\n \n \n http://www.odontoflow.com.br/odontoFlow/Reg_servlet?opt=2&validaCode="+cod_valida);
		        		email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com", "OdontoFlow - INFORME DE NOVO USUARIO","NOME: "+req.getParameter("nome")+" \n EMAIL:"+req.getParameter("email"));
		        		resp.setContentType("application/text");  
			    		resp.setCharacterEncoding("UTF-8"); 
			    		//PrintWriter out = resp.getWriter();
			    		//out.print("Link de Ativação Enviado para o email informado");
			    		resp.getWriter().write("<b>Link de Ativação Enviado para o email informado</b>"); 
		        	}
		        	else{
		        		 
		                resp.setContentType("application/text");  
			    		resp.setCharacterEncoding("UTF-8"); 
			    		//PrintWriter out = resp.getWriter();
			    		//out.print("Usuário ou email ja Existente. Por favor contate o administrador do sistema para sua empresa!");
			    		resp.getWriter().write("Email ou usuário existente!"); 
			    		rs.close();
		                rs=null;
		                con.fecharConexao();
		        	}
					}
				}else if(opt.equals("1")){
					System.out.println("pegando clinicas");
					query="select * from clinica where nome_clinica<>'Demonstração'";
					//System.out.println(query);
					rs=con.Consulta(query);
					dados_tabela="[";
					if(rs.next()){
						rs.beforeFirst();
						while(rs.next()){
							dados_tabela=dados_tabela+"{\"id\":\""+rs.getString(1)+"\",\"nome_completo\":\""+rs.getString(3)+"\",\"name\":\""+rs.getString(2)+"\",\"estado\":\""+rs.getString(7)+"\",\"bairro\":\""+rs.getString(18)+"\",\"lat\":\""+rs.getString(16)+"\",\"long\":\""+rs.getString(17)+"\"},\n";
						}
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]";
					System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					con.fecharConexao();
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
