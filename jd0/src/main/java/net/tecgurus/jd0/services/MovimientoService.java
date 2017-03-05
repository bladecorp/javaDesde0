package net.tecgurus.jd0.services;

import java.util.List;

import net.tecgurus.jd0.dao.MovimientoDAO;
import net.tecgurus.jd0.dto.MovimientoDTO;
import net.tecgurus.jd0.model.Movimiento;

public class MovimientoService {

	public List<MovimientoDTO> obtenerMovimientosPorIdCuentaYtipo(long idCuenta, int idTipoMovimiento){
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		return movimientoDAO.obtenerMovimientosPorIdCuentaYtipo(idCuenta, idTipoMovimiento);
	}
	
	public boolean insertarMovimiento(Movimiento movimiento){
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		boolean exito = movimientoDAO.insertarMovimiento(movimiento);
		if(exito){
			CuentaService cuentaService = new CuentaService();
			return cuentaService.actualizarSaldo(movimiento.getIdCuenta(), movimiento.getSaldoActual());
		}
		return false;
	}
	
	public void borrarMovimientosPorIdCuenta(long idCuenta){
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		movimientoDAO.borrarMovimientosPorIdCuenta(idCuenta);
	}
	
}
