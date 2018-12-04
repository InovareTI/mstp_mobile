package servlets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;

import Classes.Semail;

/**
 * Servlet implementation class emailcontact
 */
@WebServlet("/emailcontact")
public class emailcontact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public emailcontact() {
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
		contato_email(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		contato_email(request, response);
	}
	public void contato_email(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		String from;
		String nome;
		String message;
		String cel;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		from=req.getParameter("email_contato");
		message=req.getParameter("message_contato");
		cel=req.getParameter("cel_contato");
		nome=req.getParameter("nome_contato");
		try {
			Semail email= new Semail();
			email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com", "OdontoFlow - Contato via site.","CONTATO ODONTOFLOW \n \n CONTATO REALIZADO EM:"+time+" \n \n CONTATO NOME:"+nome+"\n \n CONTATO EMAIL:"+from+"\n \n CONTATO CEL:"+cel+" \n \n CONTATO MESSAGE:"+message+" \n \n");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
