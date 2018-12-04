package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Classes.Conexao;

/**
 * Servlet implementation class ativacc
 */
@WebServlet("/ativacc")
public class ativacc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ativacc() {
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
		ativa_conta(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ativa_conta(request, response);
	}
	
	public void ativa_conta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String param1;
		String param2;
		String param3;
		ResultSet rs ;
		Conexao conn = new Conexao();
		param1=req.getParameter("sys");
		rs=conn.Consulta("select * from usuarios where id_usuario_sys="+param1);
		try {
			if(rs.next()) {
				if(conn.Update_simples("update usuarios set validado='Y' where id_usuario_sys="+param1)) {
					resp.sendRedirect("/mstp_mobile/authcc.html");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
