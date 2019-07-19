package Classes;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import org.bson.Document;

public class Despesa implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private UUID id_despesa;
	private Integer dia_despesa;
	private Integer mes_despesa;
	private Integer ano_despesa;
	private String data_despesa;
	private Integer semana_despesa;
	private Double valor_despesa;
	private String status_pagamento;
	private String site_despesa;
	private String descricao_despesa;
	private String categoriaDespesa;
	private String cod_espelho;
	private String observacoes;
	private Integer projetoIdDespesa;
	private String usuarioDespesa;
	private String owner;
	private String statusDespesa;
	private String projetoNome;
	
	public Despesa() {
		
	}

	public Despesa(UUID id_despesa, Integer dia_despesa, Integer mes_despesa, Integer ano_despesa, String data_despesa,
			Integer semana_despesa, Double valor_despesa, String status_pagamento, String site_despesa,
			String descricao_despesa,String cod_espelho) {
		super();
		this.id_despesa = id_despesa;
		this.dia_despesa = dia_despesa;
		this.mes_despesa = mes_despesa;
		this.ano_despesa = ano_despesa;
		this.data_despesa = data_despesa;
		this.semana_despesa = semana_despesa;
		this.valor_despesa = valor_despesa;
		this.status_pagamento = status_pagamento;
		this.site_despesa = site_despesa;
		this.descricao_despesa = descricao_despesa;
		this.setCod_espelho(cod_espelho);
	}

	public UUID getId_despesa() {
		return id_despesa;
	}

	public void setId_despesa(UUID id_despesa) {
		this.id_despesa = id_despesa;
	}

	public Integer getProjetoIdDespesa() {
		return projetoIdDespesa;
	}

	public void setProjetoIdDespesa(Integer projetoIdDespesa) {
		this.projetoIdDespesa = projetoIdDespesa;
	}

	public String getUsuarioDespesa() {
		return usuarioDespesa;
	}

	public void setUsuarioDespesa(String usuarioDespesa) {
		this.usuarioDespesa = usuarioDespesa;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStatusDespesa() {
		return statusDespesa;
	}

	public void setStatusDespesa(String statusDespesa) {
		this.statusDespesa = statusDespesa;
	}

	public Integer getDia_despesa() {
		return dia_despesa;
	}

	public void setDia_despesa(Integer dia_despesa) {
		this.dia_despesa = dia_despesa;
	}

	public String getCategoriaDespesa() {
		return categoriaDespesa;
	}

	public void setCategoriaDespesa(String categoriaDespesa) {
		this.categoriaDespesa = categoriaDespesa;
	}

	public Integer getMes_despesa() {
		return mes_despesa;
	}

	public void setMes_despesa(Integer mes_despesa) {
		this.mes_despesa = mes_despesa;
	}

	public Integer getAno_despesa() {
		return ano_despesa;
	}

	public void setAno_despesa(Integer ano_despesa) {
		this.ano_despesa = ano_despesa;
	}

	public String getData_despesa() {
		return data_despesa;
	}

	public void setData_despesa(String data_despesa) {
		this.data_despesa = data_despesa;
	}

	public Integer getSemana_despesa() {
		return semana_despesa;
	}

	public void setSemana_despesa(int i) {
		this.semana_despesa = i;
	}

	public Double getValor_despesa() {
		return valor_despesa;
	}

	public void setValor_despesa(String valor_despesa) {
		//System.out.println(valor_despesa);
		valor_despesa=valor_despesa.replace(".","");
		valor_despesa=valor_despesa.replace(",",".");
		//System.out.println(valor_despesa);
		this.valor_despesa = Double.parseDouble(valor_despesa);
	}
	public void setProjetoNomeDespesa(String projetoNome) {
		this.projetoNome=projetoNome;
	}
	public String getProjetoNomeDespesa() {
		return projetoNome;
	}
	public String getStatus_pagamento() {
		return status_pagamento;
	}

	public void setStatus_pagamento(String status_pagamento) {
		this.status_pagamento = status_pagamento;
	}

	public String getSite_despesa() {
		return site_despesa;
	}

	public void setSite_despesa(String site_despesa) {
		this.site_despesa = site_despesa;
	}

	public String getDescricao_despesa() {
		return descricao_despesa;
	}

	public void setDescricao_despesa(String descricao_despesa) {
		this.descricao_despesa = descricao_despesa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_despesa == null) ? 0 : id_despesa.hashCode());
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
		Despesa other = (Despesa) obj;
		if (id_despesa == null) {
			if (other.id_despesa != null)
				return false;
		} else if (!id_despesa.equals(other.id_despesa))
			return false;
		return true;
	}

	public String getCod_espelho() {
		return cod_espelho;
	}

	public void setCod_espelho(String cod_espelho) {
		this.cod_espelho = cod_espelho;
	}
	
	public Boolean registraDespesa(ConexaoMongo mongo, Pessoa p) {
		Document despesaDoc = new Document();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		despesaDoc.append("id_despesa", id_despesa);
		despesaDoc.append("Empresa", p.getEmpresaObj().getEmpresa_id());
		despesaDoc.append("dia_despesa", dia_despesa);
		despesaDoc.append("mes_despesa", mes_despesa);
		despesaDoc.append("ano_despesa",ano_despesa);
		despesaDoc.append("data_despesa", data_despesa);
		despesaDoc.append("semana_despesa", semana_despesa);
		despesaDoc.append("valor_despesa", valor_despesa);
		despesaDoc.append("status_pagamento", status_pagamento);
		despesaDoc.append("site_despesa", site_despesa);
		despesaDoc.append("descricao_despesa", descricao_despesa);
		despesaDoc.append("categoria", categoriaDespesa);
		despesaDoc.append("observacoes", observacoes);
		despesaDoc.append("cod_espelho", cod_espelho);
		despesaDoc.append("projetoIdDespesa", projetoIdDespesa);
		despesaDoc.append("projetoNomeDespesa", projetoNome);
		despesaDoc.append("usuarioDespesa", usuarioDespesa);
		despesaDoc.append("owner", owner);
		despesaDoc.append("dt_registro", time);
		despesaDoc.append("dt_ultima_mudanca", time);
		despesaDoc.append("statusDespesa", statusDespesa);
		mongo.InserirSimpels("Despesas", despesaDoc);
		return false;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	
}
