package Classes;

import java.net.MalformedURLException;

import org.apache.commons.mail.*;

public class Semail {
	
	public Semail() throws EmailException, MalformedURLException {  
	      
	      //enviaEmailFormatoHtml();  
	    }  
	      
	    /** 
	     * envia email simples(somente texto) 
	     * @throws EmailException 
	     */  
	    public void enviaEmailSimples(String emailto,String assunto,String mgs) throws EmailException {  
	          
	        SimpleEmail email = new SimpleEmail();  
	        email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail  
	        email.addTo(emailto); //destinatário  
	        email.setFrom("mstp_mobile@inovare-ti.com", "MSTP - Notificação por email"); // remetente  
	        email.setSubject(assunto); // assunto do e-mail  
	        email.setMsg(mgs); //conteudo do e-mail  
	        email.setAuthentication("mstp@inovare-ti.com", "ci8aaq-k37nyP-7pisicp5\"");  
	        email.setSmtpPort(587);  
	        //email.setSSLOnConnect(true); 
	        email.setStartTLSRequired(true);
	        email.setStartTLSEnabled(true);
	        email.send();     
	    }  

	    public void enviaEmailHtml(String emailto,String assunto,String mgs) throws EmailException, MalformedURLException { 
	    	
	    	  HtmlEmail email = new HtmlEmail();
	    	  email.setHostName("smtp.gmail.com");
	    	  email.addTo(emailto);
	    	  email.setFrom("odontoflow@inovare-ti.com", "OdontoFlow - Notificação por email");
	    	  email.setSubject(assunto);
	    	  
	    	  // embed the image and get the content id
	    	 // URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
	    	  //String cid = email.embed(url, "Apache logo");
	    	  
	    	  // set the html message
	    	  email.setHtmlMsg(mgs);

	    	  // set the alternative message
	    	  email.setTextMsg("Your email client does not support HTML messages");

	    	  // send the email
	    	  email.setAuthentication("odontoflow@inovare-ti.com", "x5w6a!aL");  
		       email.setSmtpPort(587);  
		       
		        email.setStartTLSRequired(true);
		        email.setStartTLSEnabled(true);
	    	  email.send();
	    	
	    }
	    
}

