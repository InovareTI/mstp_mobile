package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente {

	private String nome_cliente;
	
	public String get_ClienteNome(Conexao con,String id){
		
		try{
			ResultSet rs=con.Consulta("select nome from clientes where id_cliente="+id);
			if(rs.next()){
				nome_cliente=rs.getString("nome");
				return nome_cliente;
			}else{
				return "";
			}
		}catch (SQLException e) {
			
			e.printStackTrace();
			return "";
		}
	}
	
}
