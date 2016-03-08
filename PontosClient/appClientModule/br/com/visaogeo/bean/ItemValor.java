package br.com.visaogeo.bean;

public class ItemValor {
	
	private String item;
	private String valor;
	
	public ItemValor(String item, String valor) {
		this.item = item;
		this.valor = valor;
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
