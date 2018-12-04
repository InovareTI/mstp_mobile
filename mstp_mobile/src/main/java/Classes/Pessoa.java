package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;

import Classes.Pessoa;
import Classes.Conexao;

public class Pessoa {

	private String name;
	private String email;
	private String usuario;
	private int idade;
	private int total_empresas;
	private String doc_cpf;
	private String doc_rg;
	private String cro;
	private String escritorio;
	private String escritorio_lat;
	private String escritorio_lng;
	private String cor;
	private String empresa;
	private Empresa empresaObj;
	private String minhaempresa;
	private String perfil_funcoes;
	private String minhaempresa_nome;
	private String relacao_empresa;
	private String ultimo_login;
	private String perfil;
	private ResultSet rs;
	
	 public Pessoa() {
		 empresaObj=new Empresa();
	}
	
	public Empresa getEmpresaObj() {
		return empresaObj;
	}

	public void setEmpresaObj(Empresa empresaObj) {
		this.empresaObj = empresaObj;
	}
	public String getPerfil_funcoes() {
		return this.perfil_funcoes;
	}
public void setPerfil_funcoes(Conexao conn) {
		
		String query="";
		ResultSet rs ;
		query="select * from perfil_funcoes where usuario_id='"+this.usuario+"' and ativo='Y'";
		rs=conn.Consulta(query);
		try {
			if(rs.next()) {
				rs.beforeFirst();
				while(rs.next()) {
					this.perfil_funcoes = this.perfil_funcoes +rs.getString("funcao_nome")+";";
				}
			}else {
				this.perfil_funcoes="usuário";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getEscritorio() {
		return escritorio;
	}
	public void setEscritorio(String escritorio) {
		this.escritorio = escritorio;
	}
	public String getEscritorio_lat() {
		return escritorio_lat;
	}
	public void setEscritorio_lat(String escritorio_lat) {
		this.escritorio_lat = escritorio_lat;
	}
	public String getEscritorio_lng() {
		return escritorio_lng;
	}
	public void setEscritorio_lng(String escritorio_lng) {
		this.escritorio_lng = escritorio_lng;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public void set_PessoaUsuario(String n){
		usuario=n;
	}
	public void set_PessoaPerfil(String p){
		perfil=p;
	}
	public String get_PessoaPerfil(){
		return perfil;
	}
	public void set_PessoaCor(String c){
		cor=c;
	}
	public String get_PessoaMinhaCor(){
		return cor;
	}
	public String get_PessoaCorUsuario(String u,Conexao c){
		rs=c.Consulta("select cor from ortodonticos_login where usuario='"+u+"'");
		try{
		if(rs.next()){
			return rs.getString("cor");
		}else{
			return "";
		}
		}catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
	public void set_PessoaUltimoLogin(String l){
		ultimo_login=l;
	}
	public void set_PessoaEmail(String e){
		email=e;
	}
	public String get_PessoaEmail(){
		return email;
	}
	public void set_PessoaMinhaEmpresa(String e){
		minhaempresa=e;
	}
	public void set_PessoaMinhaEmpresaNome(Conexao c){
		 rs=c.Consulta("select * from clinica where id_clinica="+minhaempresa);
		try{
			if(rs.next()){
				minhaempresa_nome=rs.getString("nome_clinica");
			}else{
				minhaempresa_nome="Empresa não registrada";
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String get_PessoaMinhaEmpresaNome(){
		return minhaempresa_nome;
	}
	public String get_PessoaRelacaoEmpresa(){
		return relacao_empresa;
	}
	public void set_PessoaRelacaoEmpresa(Conexao c){
		ResultSet rs=c.Consulta("Select empresa_relacao from usuario_empresa where id_empresa='"+minhaempresa+"' and id_usuario='"+usuario+"'");
		
		try {
			if(rs.next()){
				relacao_empresa=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String get_PessoaMinhaEmpresa(){
		return minhaempresa;
	}
	public int get_PessoaTotalEmpresas(Conexao c){
		ResultSet rs=c.Consulta("Select count(id_usuario) from usuario_empresa where id_usuario='"+usuario+"'");
		try {
			if(rs.next()){
				return rs.getInt(1);
			}else{
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return 0;
		}
	}
	public String get_PessoaUltimoLogin(String l){
		return ultimo_login;
	}
	public String get_PessoaUsuario(){
		return usuario;
	}
	public void set_PessoaName(String n){
		name=n;
	}
	public String get_PessoaName(){
		return name;
	}
	public void set_PessoaIdade(int i){
		idade=i;
	}
	public int get_PessoaIdade(){
		return idade;
	}
	public void set_PessoaDocCpf(String c){
		doc_cpf=c;
	}
	public String get_PessoaDocCpf(){
		return doc_cpf;
	}
	public void set_PessoaDocRG(String r){
		doc_rg=r;
	}
	public String get_PessoaDocRG(){
		return doc_rg;
	}public void set_Pessoa_cro(String cr){
		cro=cr;
	}
	public String get_Pessoa_cro(){
		return cro;
	}
	public void set_Pessoa(Pessoa p){
		this.set_PessoaName(p.get_PessoaName());
		this.set_PessoaIdade(p.get_PessoaIdade());
		this.set_PessoaDocCpf(p.get_PessoaDocCpf());
		this.set_PessoaDocRG(p.get_PessoaDocRG());
		
	}
	public Pessoa get_Pessoa(){
		return this;
	}
	
}
