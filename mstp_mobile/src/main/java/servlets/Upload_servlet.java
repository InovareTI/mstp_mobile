package servlets;




import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;


import Classes.Conexao;
import Classes.Pessoa;

/**
 * Servlet implementation class Upload_servlet
 */
@WebServlet("/Upload_servlet")
public class Upload_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload_servlet() {
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
		try {
			upload_foto(request,response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			upload_foto(request,response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void upload_foto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, JSONException{
		
		System.out.println("entrou no servlet de upload OdontoFlow");
		String opt;
		HttpSession session = req.getSession(true);
		Conexao conn = (Conexao) session.getAttribute("conexao");
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		InputStream inputStream=null;
		opt=req.getParameter("opt");
		String cliente="";
		String agendamento="";
		Timestamp time = new Timestamp(System.currentTimeMillis());
		try{
			if(opt.equals("1")){
		if (ServletFileUpload.isMultipartContent(req)) {
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			//System.out.println("Multipart size: " + multiparts.size());
			Iterator<FileItem> iter = multiparts.iterator();
			
			while (iter.hasNext()) {
				FileItem item = iter.next();
				
				if (item.isFormField()) {
					if(item.getFieldName().equals("cliente")){
						cliente=item.getString();
						//System.out.println("Cliente é:"+cliente);
					}
			    }
				}
			
			iter = multiparts.iterator();
			
			while (iter.hasNext()) {
				FileItem item = iter.next();
				
				if (item.isFormField()) {
					
			    } else {
			       // processUploadedFile(item);
			    	 inputStream = item.getInputStream();
			    	 String sql = "update clientes set foto_cliente=?,foto_name='"+item.getName()+"',foto_tipo='"+item.getContentType()+"',foto_tamanho='"+item.getSize()+"' where id_cliente_clinica='"+cliente+"' and id_clinica='"+p.get_PessoaMinhaEmpresa()+"'";
				     //System.out.println(item.getSize());
				     //System.out.println(item.getName());
				     //System.out.println(sql);
			    	 PreparedStatement statement;
					 statement = conn.getConnection().prepareStatement(sql);
					 statement.setBlob(1, inputStream);
					 int row = statement.executeUpdate();
				     statement.getConnection().commit();
				     if (row>0) {
				        	//System.out.println("File uploaded and saved into database");
				        	statement.close();
				        	
				        	resp.setContentType("application/json"); 
				        	JSONObject jsonObject = new JSONObject(); 
				        	jsonObject.put("", ""); 
				        	PrintWriter pw = resp.getWriter();
				        	pw.print(jsonObject); 
				        	pw.close();
				        	//System.out.println(filecontent.toString());
				        }
			    }
			}
		}
			}else if(opt.equals("2")){
				if (ServletFileUpload.isMultipartContent(req)) {
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
					System.out.println("entrou na opçao 2");
					Iterator<FileItem> iter = multiparts.iterator();
					
					while (iter.hasNext()) {
						FileItem item = iter.next();
						
						if (item.isFormField()) {
							if(item.getFieldName().equals("cliente")){
								cliente=item.getString();
								//System.out.println("Cliente é:"+cliente);
							}else if(item.getFieldName().equals("agendamento")){
								agendamento=item.getString();
							}
					    }
						}
					
					iter = multiparts.iterator();
					
					while (iter.hasNext()) {
						FileItem item = iter.next();
						
						if (item.isFormField()) {
							
					    } else {
					       // processUploadedFile(item);
					    	 inputStream = item.getInputStream();
					    	 String sql = "insert into cliente_arquivos (id_cliente,id_clinica,id_agendamento,tipo_arquivo,arquivo,dt_add,criador,arquivo_nome) values ('"+cliente+"','"+p.get_PessoaMinhaEmpresa()+"','"+agendamento+"','"+item.getContentType()+"',?,'"+time+"','"+p.get_PessoaUsuario()+"','"+item.getName()+"')";
						     //System.out.println(item.getSize());
						     //System.out.println(item.getName());
						     System.out.println(sql);
					    	 PreparedStatement statement;
							 statement = conn.getConnection().prepareStatement(sql);
							 statement.setBlob(1, inputStream);
							 int row = statement.executeUpdate();
						     statement.getConnection().commit();
						     if (row>0) {
						        	//System.out.println("File uploaded and saved into database");
						        	statement.close();
						        	
						        	resp.setContentType("application/json"); 
						        	JSONObject jsonObject = new JSONObject(); 
						        	jsonObject.put("", ""); 
						        	PrintWriter pw = resp.getWriter();
						        	pw.print(jsonObject); 
						        	pw.close();
						        	//System.out.println(filecontent.toString());
						        }
					    }
					}
				}
			}
			
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			inputStream.close();
			conn.fecharConexao();
			e.printStackTrace();
		}catch (Exception e){
			inputStream.close();
			conn.fecharConexao();
    		System.out.println(e.getMessage());
            e.printStackTrace();
    	}
		
	}
	
}
