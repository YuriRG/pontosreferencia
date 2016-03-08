package br.com.visaogeo.to;

public class LocalTO {
	private Long id;
	private Double longitude;
	private Double latitude;
	private String descricao;
	private EnderecoTO endereco;
	private ClassificacaoTO classificacao;
	
	public LocalTO() {
		this.endereco = new EnderecoTO();
		this.classificacao = new ClassificacaoTO();
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
	public EnderecoTO getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoTO endereco) {
		this.endereco = endereco;
	}
	public ClassificacaoTO getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(ClassificacaoTO classificacao) {
		this.classificacao = classificacao;
	}

}
