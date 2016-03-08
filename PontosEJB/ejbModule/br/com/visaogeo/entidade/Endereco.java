package br.com.visaogeo.entidade;

import br.com.visaogeo.to.EnderecoTO;

public class Endereco {
	private Long id;
	private String rua;
	private Integer numero;
	private String bairro;
	private String cidade;
	private String estado;
	
	public Endereco() {}

	public Endereco(EnderecoTO enderecoTO) {
		this.id = enderecoTO.getId();
		this.rua = enderecoTO.getRua();
		this.numero = enderecoTO.getNumero();
		this.bairro = enderecoTO.getBairro();
		this.estado = enderecoTO.getEstado();
		this.cidade= enderecoTO.getCidade();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
