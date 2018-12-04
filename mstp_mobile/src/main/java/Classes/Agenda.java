package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Agenda {

	public Evento evento;
	
	public Agenda(){
		evento=new Evento();
	}
	public boolean adicionar_evento(Conexao c){
		String insere="";
		insere="insert into agendamento (usuario_clinico,nome_cliente,id_cliente2,dt_add,dt_inicio,dt_fim,status_agendamento,id_clinica,hora,atributo_agendamento1) values ('"+evento.getUsuario()+"','"+evento.getCliente()+"','"+evento.getCliente_id()+"','"+evento.getDt_add()+"','"+evento.getInicio()+"','"+evento.getInicio()+"','Agendado','"+evento.getClinica_id()+"','"+evento.getHora()+"','"+evento.getServico()+"')";
		//System.out.println(insere);
		if(c.Inserir_simples(insere)){
			return true;
		}else{
			return false;
		}
	}
	public boolean atualizar_evento(Conexao c,String id_evento){
		String query;
		System.out.println("Atualizando evento id:"+id_evento);
		query="update agendamento set "
				+ "usuario_clinico='"+evento.getUsuario()+"',"
				+ "nome_cliente='"+evento.getCliente()+"',"
				+ "id_cliente2='"+evento.getCliente_id()+"',"
				+ "dt_inicio='"+evento.getInicio()+"',"
				+ "dt_fim='"+evento.getInicio()+"',"
				+ "status_agendamento='Agendado',"
				+ "hora='"+evento.getHora()+"',"
				+ "atributo_agendamento1='"+evento.getServico()+"' "
				+ "where id_clinica='"+evento.getClinica_id()+"' and id_agendamento_sys="+id_evento;
		System.out.println(query);
		if(c.Alterar(query)){
			return true;
		}else{
			return false;
		}
	}
	public void remover_evento(Conexao c,int id_evento){
		
	}
	public String carregar_eventos(Conexao c,String start,String end, Pessoa p){
		String aux_clinicos="";
		String[] clinicos;
		String query="";
		String currentTime;
		ResultSet rs;
		String dados_tabela="[";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		
			query="select * from agendamento where id_clinica='"+p.get_PessoaMinhaEmpresa()+"' and dt_inicio>='"+start+"' and dt_fim<='"+end+"' and status_agendamento<>'CANCELADO'";
		
		//System.out.println(query);
		rs=c.Consulta(query);
		try {
			if(rs.next()){
				rs.beforeFirst();
				while(rs.next()){
					date = format.parse(rs.getString("dt_inicio"));
					currentTime = sdf.format(date);
					dados_tabela=dados_tabela+"{\"title\":\""+rs.getString("nome_cliente")+"\",\"start\":\""+rs.getString("dt_inicio")+"T"+rs.getString("hora")+"\",\"start_aux\":\""+currentTime+"\",\"id\":\""+rs.getString("id_agendamento_sys")+"\",\"id_cliente\":\""+rs.getString("id_cliente2")+"\",\"hora\":\""+rs.getString("hora")+"\",\"usuario\":\""+rs.getString("usuario_clinico")+"\",\"servico\":\""+rs.getString("atributo_agendamento1")+"\",\"color\":\""+p.get_PessoaCorUsuario(rs.getString("usuario_clinico"), c)+"\"},";
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
				dados_tabela=dados_tabela+"]";
			}else{
			dados_tabela=dados_tabela+"]";
			}
			return dados_tabela;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "[]";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "[]";
		}
		
	}
}


