package br.com.visaogeo.entidade;

import br.com.visaogeo.to.ClassificacaoTO;

public class Classificacao {
	private Long id;
	private String tipo;
	private Integer tamanho;
	private String atividade;
	
	public Classificacao() {}
	
	public Classificacao(ClassificacaoTO classificacaoTO) {
		this.id = classificacaoTO.getId();
		this.tipo = classificacaoTO.getTipo();
		this.tamanho = classificacaoTO.getTamanho();
		this.atividade = classificacaoTO.getAtividade();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getTamanho() {
		return tamanho;
	}
	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	
	
}
