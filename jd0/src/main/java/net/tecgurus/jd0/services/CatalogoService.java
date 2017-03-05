package net.tecgurus.jd0.services;

import java.util.List;

import net.tecgurus.jd0.dao.CatalogoDAO;
import net.tecgurus.jd0.model.EstadoCivil;
import net.tecgurus.jd0.model.TipoCuenta;
import net.tecgurus.jd0.model.TipoMovimiento;

public class CatalogoService {

	public List<TipoCuenta> obtenerTiposCuenta(){
		CatalogoDAO catalogoDAO = new CatalogoDAO();
		return catalogoDAO.obtenerTiposCuenta();
	}
	
	public List<TipoMovimiento> obtenerTiposMovimiento(){
		CatalogoDAO catalogoDAO = new CatalogoDAO();
		return catalogoDAO.obtenerTiposMovimiento();
	}
	
	public List<EstadoCivil> obtenerEstadoCivil(){
		CatalogoDAO catalogoDAO = new CatalogoDAO();
		return catalogoDAO.obtenerTipoEstadoCivil();
	}
	
}
