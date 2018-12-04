package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class perfil {
private String perfil;
private String perfil_campos;
	
	public perfil(){
		perfil="";
		perfil_campos="";
	}
	
	public String get_perfil(){
		
		return perfil;
	}
	public void set_Perfil_nome(String p){
		perfil=p;
	}
	public String get_campos_perfil(Conexao c, String tabela){
		ResultSet rs;
		String query="Select tabela_campos from perfil_campos where perfil_nome='"+perfil+"' and tabela_nome='"+tabela+"'";
		perfil_campos="";
		rs=c.Consulta(query);
		try {
				if(rs.next()){
					rs.beforeFirst();
					//System.out.println("achou conteudo de campos");
					while(rs.next()){
						perfil_campos=perfil_campos+rs.getString(1)+",";
					}
					perfil_campos=perfil_campos.substring(0,perfil_campos.length()-1);
				}
				return perfil_campos;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return perfil_campos;
			}
		
	}
}

