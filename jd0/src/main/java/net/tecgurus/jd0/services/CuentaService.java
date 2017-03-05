package net.tecgurus.jd0.services;

import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.dao.CaracteristicasCuentaDAO;
import net.tecgurus.jd0.dao.CuentaDAO;
import net.tecgurus.jd0.dto.CuentaDTO;
import net.tecgurus.jd0.dto.GraficaCuentasDTO;
import net.tecgurus.jd0.model.CaracteristicasCuenta;
import net.tecgurus.jd0.model.Cuenta;

public class CuentaService {

	public List<CuentaDTO> obtenerCuentasPorIdCliente(int idCliente){
		CuentaDAO cuentaDAO = new CuentaDAO();
		return cuentaDAO.obtenerCuentasPorIdCliente(idCliente);
	}
	
	public List<GraficaCuentasDTO> obtenerCuentasPorBanco(){
		CuentaDAO cuentaDAO = new CuentaDAO();
		return cuentaDAO.obtenerCuentasPorBanco();
	}
	
	public boolean insertarCuenta(Cuenta cuenta, List<Integer> caracteristicas){
		CuentaDAO cuentaDAO = new CuentaDAO();
		boolean exito = cuentaDAO.insertarCuenta(cuenta);
		if(exito){
			CaracteristicasCuentaService carCuentaService = new CaracteristicasCuentaService();
			return carCuentaService.insertarCaracteristicas(caracteristicas, cuenta.getNumero());
		}
		return false;
	}
	
	public void eliminarCuentaPorId(long idCuenta){ // TEMA DE TRANSACCIONALIDAD EN LA CAPA DE SERVICIO
		CaracteristicasCuentaService carService = new CaracteristicasCuentaService();
		carService.eliminarCaracteristicasPorIdCuenta(idCuenta);
		MovimientoService movimientoService = new MovimientoService();
		movimientoService.borrarMovimientosPorIdCuenta(idCuenta);
		CuentaDAO cuentaDAO = new CuentaDAO();
		cuentaDAO.eliminarCuenta(idCuenta);
	}
	
	public void eliminarCuentasPorIdCliente(int idCliente){
		List<CuentaDTO> cuentas = obtenerCuentasPorIdCliente(idCliente);
		for (CuentaDTO cuentaDTO : cuentas) {
			eliminarCuentaPorId(cuentaDTO.getNumero());
		}
	}
	
	public boolean actualizarSaldo(long idCuenta, double saldo){
		CuentaDAO cuentaDAO = new CuentaDAO();
		return cuentaDAO.actualizarSaldo(idCuenta, saldo);
	}
	
}
