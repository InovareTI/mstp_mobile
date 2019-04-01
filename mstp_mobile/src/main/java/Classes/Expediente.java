package Classes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Expediente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String expediente_nome;
	private int expediente_id;
	private String expediente_entrada;
	private String expediente_inicio_intervalo;
	private String expediente_fim_intervalo;
	private String expediente_saida;
	private int expdiente_intervalo_minutos;
	public Expediente(Conexao c , int empresa) {
		super();
		ResultSet rs = c.Consulta("select * from expediente where empresa="+empresa);
		try {
			if(rs.next()) {
				this.expediente_nome = rs.getString("expediente");
				this.expediente_id = rs.getInt("id_expediente");
				this.expediente_entrada = rs.getString("entrada");
				this.expediente_inicio_intervalo = rs.getString("entrada");
				this.expediente_fim_intervalo = rs.getString("entrada");
				this.expediente_saida = rs.getString("saida");
				this.expdiente_intervalo_minutos = rs.getInt("intervalo_min");
			}else {
				this.expediente_nome = "";
				this.expediente_id = 0;
				this.expediente_entrada = "";
				this.expediente_inicio_intervalo = "";
				this.expediente_fim_intervalo = "";
				this.expediente_saida = "";
				this.expdiente_intervalo_minutos = 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getExpediente_nome() {
		return expediente_nome;
	}
	public void setExpediente_nome(String expediente_nome) {
		this.expediente_nome = expediente_nome;
	}
	public int getExpediente_id() {
		return expediente_id;
	}
	public void setExpediente_id(int expediente_id) {
		this.expediente_id = expediente_id;
	}
	public String getExpediente_entrada() {
		return expediente_entrada;
	}
	public void setExpediente_entrada(String expediente_entrada) {
		this.expediente_entrada = expediente_entrada;
	}
	public String getExpediente_inicio_intervalo() {
		return expediente_inicio_intervalo;
	}
	public void setExpediente_inicio_intervalo(String expediente_inicio_intervalo) {
		this.expediente_inicio_intervalo = expediente_inicio_intervalo;
	}
	public String getExpediente_fim_intervalo() {
		return expediente_fim_intervalo;
	}
	public void setExpediente_fim_intervalo(String expediente_fim_intervalo) {
		this.expediente_fim_intervalo = expediente_fim_intervalo;
	}
	public String getExpediente_saida() {
		return expediente_saida;
	}
	public void setExpediente_saida(String expediente_saida) {
		this.expediente_saida = expediente_saida;
	}
	public int getExpdiente_intervalo_minutos() {
		return expdiente_intervalo_minutos;
	}
	public void setExpdiente_intervalo_minutos(int expdiente_intervalo_minutos) {
		this.expdiente_intervalo_minutos = expdiente_intervalo_minutos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expediente_id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expediente other = (Expediente) obj;
		if (expediente_id != other.expediente_id)
			return false;
		return true;
	}
	
	
	
	
	
	
}
