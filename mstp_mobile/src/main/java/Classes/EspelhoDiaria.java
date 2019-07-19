package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class EspelhoDiaria implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id_espelho;
	private Integer empresa;
	
	private String cod_espelho;
	private String func_espelho;
	private String semana_espelho;
	private String saldo_espelho;
	
	private List<Despesa> diarias = new ArrayList<>();
	
	public EspelhoDiaria() {
		
	}

	public EspelhoDiaria(Integer id_espelho,Integer empresa, String cod_espelho, String func_espelho, String semana_espelho,
			String saldo_espelho) {
		super();
		this.id_espelho = id_espelho;
		this.empresa = empresa;
		this.cod_espelho = cod_espelho;
		this.func_espelho = func_espelho;
		this.semana_espelho = semana_espelho;
		this.saldo_espelho = saldo_espelho;
	}

	public Integer getId_espelho() {
		return id_espelho;
	}

	public void setId_espelho(Integer id_espelho) {
		this.id_espelho = id_espelho;
	}
	public Integer getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}
	public String getCod_espelho() {
		return cod_espelho;
	}

	public void setCod_espelho(String cod_espelho) {
		this.cod_espelho = cod_espelho;
	}

	public String getFunc_espelho() {
		return func_espelho;
	}

	public void setFunc_espelho(String func_espelho) {
		this.func_espelho = func_espelho;
	}

	public String getSemana_espelho() {
		return semana_espelho;
	}

	public void setSemana_espelho(String semana_espelho) {
		this.semana_espelho = semana_espelho;
	}

	public String getSaldo_espelho() {
		return saldo_espelho;
	}

	public void setSaldo_espelho(String saldo_espelho) {
		this.saldo_espelho = saldo_espelho;
	}

	public List<Despesa> getDiarias() {
		return diarias;
	}

	public void setDiarias(List<Despesa> diarias) {
		this.diarias = diarias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_espelho == null) ? 0 : id_espelho.hashCode());
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
		EspelhoDiaria other = (EspelhoDiaria) obj;
		if (id_espelho == null) {
			if (other.id_espelho != null)
				return false;
		} else if (!id_espelho.equals(other.id_espelho))
			return false;
		return true;
	}
	public boolean SalvaEspelho(ConexaoMongo mongo) {
		
		Document espelho = new Document();
		espelho.append("semana", this.semana_espelho);
		espelho.append("usuario", this.func_espelho);
		espelho.append("Empresa", this.empresa);
		espelho.append("saldo", this.saldo_espelho);
		mongo.InserirSimpels("resumo_diarias", espelho);
		return true;
	}
}
