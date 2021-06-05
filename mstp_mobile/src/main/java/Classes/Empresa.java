package Classes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Empresa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String nomeFantasia;
	private String cnpj;
	private String cnae;
	private String donoUsuario;
	private int empresaId;
	private String strEmpresaId;
	private String bairro;
	private String endereco;
	private String uf;
	private String cidade;
	private Boolean mstpFree;
	
	
	
	public Empresa(String nome, String nome_fantasia, String cnpj, String cnae, String dono_usuario, int empresa_id,
			String str_empresa_id, String bairro, String endereco, String uf, String cidade, Boolean mstp_free) {
		super();
		this.nome = nome;
		this.nomeFantasia = nome_fantasia;
		this.cnpj = cnpj;
		this.cnae = cnae;
		this.donoUsuario = dono_usuario;
		this.empresaId = empresa_id;
		this.strEmpresaId = str_empresa_id;
		this.bairro = bairro;
		this.endereco = endereco;
		this.uf = uf;
		this.cidade = cidade;
		this.mstpFree = mstp_free;
	}
	
	public Empresa() {
		super();
		this.nome = "";
		this.nomeFantasia = "";
		this.cnpj = "";
		this.cnae = "";
		this.donoUsuario = "";
		this.empresaId = 0;
		this.strEmpresaId = "";
		this.bairro="";
		this.cidade="";
		this.uf="";
		this.endereco="";
		this.mstpFree=false;
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
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nome_fantasia) {
		this.nomeFantasia = nome_fantasia;
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
	public String getDonoUsuario() {
		return donoUsuario;
	}
	public void setDonoUsuario(String dono_usuario) {
		this.donoUsuario = dono_usuario;
	}
	public int getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(int empresa_id) {
		this.empresaId = empresa_id;
	}
	public String getStrEmpresaId() {
		return strEmpresaId;
	}
	public void setStrEmpresaId(String str_empresa_id) {
		this.strEmpresaId = str_empresa_id;
	}
	public Boolean getMstpFree() {
		return mstpFree;
	}
	public void setMstpFree(Boolean mstp_free) {
		this.mstpFree = mstp_free;
	}
	public void define_empresa(String empresa_codigo) {
		String query="";
		ResultSet rs ;
		Conexao c=new Conexao();
		System.out.println("Buscando empresa:"+empresa_codigo);
		query="select *  from empresas where id_empresa="+empresa_codigo;
		rs=c.Consulta(query);
		try {
			if(rs.next()) {
				this.setNome(rs.getString("nome"));
				this.setNomeFantasia(rs.getString("nome_fantasia"));
				this.setCnpj(rs.getString("cnpj"));
				this.setCnae(rs.getString("other"));
				this.setEmpresaId(rs.getInt(1));
				this.setStrEmpresaId(rs.getString(1));
				this.setDonoUsuario(rs.getString("usuario_dono"));
				this.setCidade(rs.getString("cidade"));
				this.setUf(rs.getString("uf"));
				this.setEndereco(rs.getString("endereco"));
				this.setBairro(rs.getString("bairro"));
				if(rs.getString("MSTP_FREE").equals("Y")) {
					this.setMstpFree(true);
				}else {
					this.setMstpFree(false);
				}
				
			}
			c.fecharConexao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			c.fecharConexao();
			e.printStackTrace();
		}
	}

}

