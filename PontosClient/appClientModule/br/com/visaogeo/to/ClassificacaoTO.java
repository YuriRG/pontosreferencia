package br.com.visaogeo.to;

import java.util.ArrayList;
import java.util.List;

import br.com.visaogeo.bean.ItemValor;

public class ClassificacaoTO {
	private Long id;
	private String tipo;
	private Integer tamanho;
	private String atividade;
	private List<ItemValor> tipos;

	public ClassificacaoTO() {
		this.tipos = new ArrayList<ItemValor>();
		this.tipos.add(new ItemValor("Residencial", "residencial"));
		this.tipos.add(new ItemValor("Comercial", "comercial"));
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
	public List<ItemValor> getTipos() {
		return tipos;
	}
	public void setTipos(List<ItemValor> tipos) {
		this.tipos = tipos;
	}	
}
