package net.tecgurus.jd0.dto;

import net.tecgurus.jd0.model.TipoCuenta;

public class CuentaDTO {

	private long numero;
	private double saldo;
	private int idCliente;
	private TipoCuenta tipoCuenta;
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return tipoCuenta.getTipo()+" - "+numero;
	}
	
	
	
}
