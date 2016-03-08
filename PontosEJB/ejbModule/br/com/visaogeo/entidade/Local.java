package br.com.visaogeo.entidade;

import br.com.visaogeo.to.LocalTO;

public class Local {
	private Long id;
	private Double longitude;
	private Double latitude;
	private String descricao;
	private Endereco endereco;
	private Classificacao classificacao;
	
	public Local() {}
	
	public Local(LocalTO localTO) {
		this.id = localTO.getId();
		this.longitude = localTO.getLongitude();
		this.latitude = localTO.getLatitude();
		this.descricao = localTO.getDescricao();
		this.endereco = new Endereco(localTO.getEndereco());
		this.classificacao = new Classificacao(localTO.getClassificacao());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Classificacao getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}

}
