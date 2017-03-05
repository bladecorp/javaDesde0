package net.tecgurus.jd0.model;

public class TipoCuenta {

	private int id;
	private String tipo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString(){
		return (id+". "+tipo).toUpperCase();
	}
	
}
