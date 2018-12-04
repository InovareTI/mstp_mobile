package Classes;

import java.sql.Timestamp;

public class Evento {
    private String cliente;
    private String inicio;
    private String fim;
    private Timestamp dt_add;
    private String hora;
    private String servico;
    private String evento_id;
    private String cliente_id;
    private String cliente_nome;
    private String clinica_id;
    private String evento_status;
    private String usuario;
    
	public Evento(){
		
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public Timestamp getDt_add() {
		return dt_add;
	}

	public void setDt_add(Timestamp dt_add) {
		this.dt_add = dt_add;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getEvento_id() {
		return evento_id;
	}

	public void setEvento_id(String evento_id) {
		this.evento_id = evento_id;
	}

	public String getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(String cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getCliente_nome() {
		return cliente_nome;
	}

	public void setCliente_nome(String cliente_nome) {
		this.cliente_nome = cliente_nome;
	}

	public String getClinica_id() {
		return clinica_id;
	}

	public void setClinica_id(String clinica_id) {
		this.clinica_id = clinica_id;
	}

	public String getEvento_status() {
		return evento_status;
	}

	public void setEvento_status(String evento_status) {
		this.evento_status = evento_status;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
