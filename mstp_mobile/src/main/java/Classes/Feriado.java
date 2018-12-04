package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Feriado {
	private String data;
	private String nome;
	
	public Feriado() {
		data="";
		nome="";
	}
	
	public Boolean verifica_feriado(String data, Conexao c,Pessoa p) {
		ResultSet rs;
		rs=c.Consulta("select * from feriados where dt_inicio='"+data+"' and empresa="+p.getEmpresaObj().getEmpresa_id());
		try {
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
