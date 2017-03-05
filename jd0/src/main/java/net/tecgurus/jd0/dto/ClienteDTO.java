package net.tecgurus.jd0.dto;

import net.tecgurus.jd0.model.Banco;
import net.tecgurus.jd0.model.Cliente;
import net.tecgurus.jd0.model.EstadoCivil;

public class ClienteDTO {

	private int id;
	private String nombre;
	private String apaterno;
	private String amaterno;
	private int edad;
	private EstadoCivil eCivil;
	private Banco banco;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApaterno() {
		return apaterno;
	}
	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}
	public String getAmaterno() {
		return amaterno;
	}
	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public EstadoCivil geteCivil() {
		return eCivil;
	}
	public void seteCivil(EstadoCivil eCivil) {
		this.eCivil = eCivil;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	@Override
	public String toString() {
		return "ClienteDTO [id=" + id + ", nombre=" + nombre + ", apaterno=" + apaterno + ", amaterno=" + amaterno
				+ ", edad=" + edad + ", eCivil=" + eCivil + ", banco=" + banco + "]";
	}
	
	
	
}
