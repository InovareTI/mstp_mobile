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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.LoggerFactory;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

import Classes.Conexao;
import Classes.ConexaoMongo;
import Classes.Pessoa;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;


/**
 * Servlet implementation class Lg_OdontoFlow
 */
@WebServlet("/loginmstp_mobile")
public class loginmstp_mobile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger root = (Logger) LoggerFactory
	        .getLogger(Logger.ROOT_LOGGER_NAME);
	static {
	    root.setLevel(Level.OFF);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginmstp_mobile() {
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
		
		
		
		Conexao con = new Conexao();
		ConexaoMongo cm = new ConexaoMongo();
		String dados;
        dados="";
        String last_login_type="";
        String last_login_time="";
        String last_login_localidade="";
        String last_login_localidade_site="";
        MessageDigest md;
        ResultSet rs ;
        ResultSet rs2 ;
        ResultSet rs3 ;
        Locale brasil = new Locale("pt", "BR");
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, brasil);
        DateFormat f3 = DateFormat.getDateTimeInstance();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println("Servlet de Login do MSTP Mobile - "+f3.format(time));
		try {
			HttpSession session = req.getSession(true);
			md = MessageDigest.getInstance( "SHA-256" );
			md.update( req.getParameter("pwd").getBytes());     
		    BigInteger hash = new BigInteger(1,md.digest() );     
		    String retornaSenha = hash.toString(16);   
	        String versao_mobile=req.getParameter("version");
	        String aparelho=req.getParameter("aparelho_id");
	        String vinculo_flag=req.getParameter("vinculo_flag");
	        //System.out.println("senha : "+retornaSenha + "fim da senha.");
	        if(!versao_mobile.equals("1.64")) {
	        	resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				dados="[[\"NOVA ATUALIZAÇÃO ENCONTRADA. <a href='#' onclick='atualizaapp()'>Clique aqui</a><script>function atualizaapp() {if(navigator.userAgent.toLowerCase().indexOf('android') > -1){window.location.href= 'market://details?id=com.inovareti.mstp_mobile';}else { $.alert('Desculpe não podemos redireciona-lo automaticamente. Acesse o link direto de download, ou GooGle Play manualmente');}} </script>\"],[\"\"]]";
				out.print(dados);
            	con.fecharConexao();
	        }else {
        	rs= con.Consulta("select * from usuarios where ATIVO='Y' and (id_usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("user")+"')");
        	
        	if (!rs.first()){
        		System.out.println("Conta Inexistente para usuário - " +req.getParameter("user") + " versao:"+versao_mobile);
        		rs.close();
        		resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				dados="[[\"Conta Inexistente! Crie uma conta antes do login!\"],[\"\"]]";
				out.print(dados);
            	con.fecharConexao();
            	
        	}else{
        		String foto_entrada = "";
                String foto_ini_inter = "";
                String foto_fim_inter = "";
                String foto_saida ="";
                String ponto_office ="";
        		if(!rs.getString("aparelho_id").equals(aparelho) && vinculo_flag.equals("N") && rs.getString("controle_vinculo").equals("true")) {
        			resp.setContentType("application/html");  
       				resp.setCharacterEncoding("UTF-8"); 
       				PrintWriter out = resp.getWriter();
       				dados="[[\"Celular não autorizado\"],[\"\"]]";
       				out.print(dados);
        		}else if(rs.getString("ferias").equals("Y")){
        			resp.setContentType("application/html");  
       				resp.setCharacterEncoding("UTF-8"); 
       				PrintWriter out = resp.getWriter();
       				dados="[[\"Usuário em período de Férias - Acesso não permitido\"],[\"\"]]";
       				out.print(dados);
        		}else {	
        		if(rs.getString("VALIDADO").equals("Y")) {
        			if(rs.getString("HASH").equals(retornaSenha)) {
                session.setAttribute("conexao",con);
                //session.setAttribute("conexaoMongo",cm);
                Bson filtro;
                List<Bson> filtros = new ArrayList<>();
                
                Pessoa p=new Pessoa();
                p.set_PessoaUsuario(rs.getString("id_usuario"));
                p.setEmpresa(rs.getString("empresa"));
                p.getEmpresaObj().setEmpresa_id(Integer.parseInt(rs.getString("empresa")));
                p.getEmpresaObj().define_empresa(con, rs.getString("empresa"));
                p.set_PessoaPerfil(rs.getString("perfil"));
                p.setPerfil_funcoes(con);
                p.set_PessoaName(rs.getString("nome"));
                p.set_PessoaUltimoLogin(rs.getString("ultimo_acesso"));
                p.setExpediente(con, Integer.parseInt(rs.getString("empresa")));
                p.set_Pessoa_PessoaTipo(rs.getString("tipo"));
                p.setPessoaLider(rs.getString("lider_usuario"));
                rs2=con.Consulta("select * from usuario_ponto where id_usuario='"+p.get_PessoaUsuario()+"'");
                if(rs2.next()) {
                		rs3=con.Consulta("select * from pontos where id_ponto="+rs2.getString("id_ponto"));
                		if(rs3.next()) {
                			p.setEscritorio(rs3.getString("nome_ponto"));
                			p.setEscritorio_lat(rs3.getString("latitude_ponto"));
                			p.setEscritorio_lng(rs3.getString("longitude_ponto"));
                		}else {
                			
                		}
                }
                rs3=con.Consulta("SELECT * FROM registros where usuario='"+p.get_PessoaUsuario()+"' and data_dia='"+f2.format(time)+"' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' order by datetime_servlet desc limit 1");
                if(rs3.next()) {
                	last_login_type=rs3.getString("tipo_registro");
                	last_login_time=rs3.getString("timeStamp_mobile");
                	last_login_localidade=rs3.getString("local_registro");
                	last_login_localidade_site=rs3.getString("tipo_local_registro");
                }else {
            		last_login_type="";
            		last_login_time="";
            	}
                Calendar now = Calendar.getInstance();
                int tempo_expediente=0;
                System.out.println("SELECT * FROM expediente where empresa="+p.getEmpresaObj().getEmpresa_id()+" and dia_expediente="+now.get(Calendar.DAY_OF_WEEK));
                rs3=con.Consulta("SELECT * FROM expediente where empresa="+p.getEmpresaObj().getEmpresa_id()+" and dia_expediente="+now.get(Calendar.DAY_OF_WEEK));
                String autorizacao_previa="";
                if(rs3.next()) {
                	autorizacao_previa=rs3.getString("autoriza_previa_he");
                	tempo_expediente=rs3.getInt("tempo_segundos_expediente");
                }else {
                	autorizacao_previa="false";
            	}
                
                session.setMaxInactiveInterval(14400);
                session.setAttribute("pessoa",p);
               // session.setAttribute("nome_empresa",p.get_PessoaMinhaEmpresaNome());
                //System.out.println(rs.getString("id_equipe"));
                //session.setAttribute("user_equipe",rs.getString("id_equipe"));
                //session.setAttribute("user_empresa",rs.getString("id_empresa"));
                
                //System.out.println("minha empresa");
                	con.Update_simples("UPDATE usuarios SET ultimo_acesso='"+time+"' WHERE (id_usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("user")+"')");
                if(rs.getString("controle_vinculo").equals("true")) {
                	con.Update_simples("UPDATE usuarios SET aparelho_id='"+aparelho+"' WHERE (id_usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("user")+"')");
                }
                if(vinculo_flag.equals("Y")) {
                	con.Inserir_simples("insert into log_mstp (usuario,operacao,dt_oper) values ('"+req.getParameter("user")+"','NOVO VINCULO DE CELULAR','"+time+"');");
                }else {}
                con.Inserir_simples("insert into log_mstp (usuario,operacao,dt_oper) values ('"+req.getParameter("user")+"','LOGIN APP','"+time+"');");
                rs3=con.Consulta("SELECT * FROM registros where usuario='"+req.getParameter("user")+"' and data_dia='"+f2.format(time)+"' and tipo_registro='Entrada' and empresa='"+p.getEmpresaObj().getEmpresa_id()+"' order by datetime_servlet asc limit 1");
                long daySeconds=0;
                
                if(rs3.next()) {
                	
                	Calendar c = Calendar.getInstance();
                	
                	long dif = 0;
                	
                	//System.out.println(rs3.getString("timeStamp_mobile"));
                	//System.out.println(rs3.getString("timeStamp_mobile").substring(0, rs3.getString("timeStamp_mobile").indexOf(".")));
                	if(rs3.getString("timeStamp_mobile").indexOf(".")>0) {
                		c.setTimeInMillis(Long.parseLong(rs3.getString("timeStamp_mobile").substring(0, rs3.getString("timeStamp_mobile").indexOf("."))));
                	}else {
                		c.setTimeInMillis(Long.parseLong(rs3.getString("timeStamp_mobile")));
                	}
                	//System.out.println(f3.format(c.getTime()));
                	//System.out.println(f3.format(now.getTime()));
                	dif= now.getTimeInMillis() - c.getTimeInMillis();
                	daySeconds=TimeUnit.MILLISECONDS.toSeconds(dif) ;
                	//System.out.println((int) (dif / 1000) % 60);
                	
                	//System.out.println(daySeconds);
                }else {
                	daySeconds =0;
                	//System.out.println(daySeconds);
                }
                rs3=con.Consulta("select * from registro_foto_controle where Empresa="+p.getEmpresaObj().getEmpresa_id());
                if(rs3.next()) {
                	foto_entrada = rs3.getString("Entrada");
                	foto_ini_inter =rs3.getString("Ini_inter");
                	foto_fim_inter =rs3.getString("Fim_inter");
                	foto_saida =rs3.getString("Saida");
                	ponto_office =rs3.getString("ponto_office");
                }else {
                	foto_entrada = "false";
                    foto_ini_inter ="false";
                    foto_fim_inter ="false";
                    foto_saida ="false";
                    ponto_office ="false";
                }
                dados="[[\"ok\"],[\""+rs.getString("perfil")+"\"],[\""+rs.getString("ultimo_acesso")+"\"],[\""+p.getEmpresa()+"\"],[\""+p.getEscritorio()+"\"],[\""+p.getEscritorio_lat()+"\"],[\""+p.getEscritorio_lng()+"\"],[\""+last_login_type+"\"],[\""+last_login_time+"\"],["+daySeconds+"],[\""+p.get_PessoaName()+"\"],[\""+p.getEmpresaObj().getNome()+"\"],["+p.getExpediente().getExpdiente_intervalo_minutos()+"],[\""+foto_entrada+"\"],[\""+foto_ini_inter+"\"],[\""+foto_fim_inter+"\"],[\""+foto_saida+"\"],[\""+ponto_office+"\"],[\""+p.getPessoaLider()+"\"],[\""+autorizacao_previa+"\"],[\""+last_login_localidade+"\"],[\""+last_login_localidade_site+"\"],["+tempo_expediente+"],[\""+p.get_PessoaTipo()+"\"]]";
                rs.close();
                rs=null;
                //System.out.println(dados);
                resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados);
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP MOBILE - "+f3.format(time)+" "+p.getEmpresaObj().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Login usuario -  "+ p.get_PessoaUsuario()+" | Versao APP:"+versao_mobile+" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
        			}else {
        				System.out.println("senha incorreta para - "+req.getParameter("user") +" versao:"+versao_mobile);
           			 rs.close();
                    rs=null;
                    con.fecharConexao();
                    //cm.fecharConexao("Servlet de Login, linha 212");
           			resp.setContentType("application/html");  
       				resp.setCharacterEncoding("UTF-8"); 
       				PrintWriter out = resp.getWriter();
       				dados="[[\"Senha Incorreta!\"],[\"\"]]";
       				out.print(dados);
        			}
        		}else {
        			System.out.println("conta sem autorização - " +req.getParameter("user")  +" versao:"+versao_mobile);
        			 rs.close();
                 rs=null;
                 con.fecharConexao();
                // cm.fecharConexao("Servlet de Login, linha 224");
        			resp.setContentType("application/html");  
    				resp.setCharacterEncoding("UTF-8"); 
    				PrintWriter out = resp.getWriter();
    				dados="[[\"Conta aguardando autorização!\"],[\"\"]]";
    				out.print(dados);
        		}
        		}
        	}
	        }
		} catch (NoSuchAlgorithmException e) {
			con.fecharConexao();
			//cm.fecharConexao("Servlet de Login, linha 236");
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
