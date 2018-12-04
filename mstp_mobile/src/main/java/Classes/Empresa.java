package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Empresa {
	
	private String nome;
	private String nome_fantasia;
	private String cnpj;
	private String cnae;
	private String dono_usuario;
	private int empresa_id;
	private String str_empresa_id;
	private String bairro;
	private String endereco;
	private String uf;
	private String cidade;
	
	
	
	public Empresa() {
		super();
		this.nome = "";
		this.nome_fantasia = "";
		this.cnpj = "";
		this.cnae = "";
		this.dono_usuario = "";
		this.empresa_id = 0;
		this.str_empresa_id = "";
		this.bairro="";
		this.cidade="";
		this.uf="";
		this.endereco="";
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome_fantasia() {
		return nome_fantasia;
	}
	public void setNome_fantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCnae() {
		return cnae;
	}
	public void setCnae(String cnae) {
		this.cnae = cnae;
	}
	public String getDono_usuario() {
		return dono_usuario;
	}
	public void setDono_usuario(String dono_usuario) {
		this.dono_usuario = dono_usuario;
	}
	public int getEmpresa_id() {
		return empresa_id;
	}
	public void setEmpresa_id(int empresa_id) {
		this.empresa_id = empresa_id;
	}
	public String getStr_empresa_id() {
		return str_empresa_id;
	}
	public void setStr_empresa_id(String str_empresa_id) {
		this.str_empresa_id = str_empresa_id;
	}
	public void define_empresa(Conexao c, String empresa_codigo) {
		String query="";
		ResultSet rs ;
		query="select *  from empresas where id_empresa="+empresa_codigo;
		rs=c.Consulta(query);
		try {
			if(rs.next()) {
				this.setNome(rs.getString("nome"));
				this.setNome_fantasia(rs.getString("nome_fantasia"));
				this.setCnpj(rs.getString("cnpj"));
				this.setCnae(rs.getString("other"));
				this.setEmpresa_id(rs.getInt(1));
				this.setStr_empresa_id(rs.getString(1));
				this.setDono_usuario(rs.getString("usuario_dono"));
				this.setCidade(rs.getString("cidade"));
				this.setUf(rs.getString("uf"));
				this.setEndereco(rs.getString("endereco"));
				this.setBairro(rs.getString("bairro"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

