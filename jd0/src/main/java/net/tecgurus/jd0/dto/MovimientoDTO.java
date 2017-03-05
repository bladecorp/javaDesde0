package net.tecgurus.jd0.dto;

import net.tecgurus.jd0.model.Movimiento;
import net.tecgurus.jd0.model.TipoMovimiento;

public class MovimientoDTO extends Movimiento{

	private TipoMovimiento tipoMovimiento;

	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	
	
	
}
