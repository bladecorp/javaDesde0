package net.tecgurus.jd0.services;

import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.dao.CaracteristicasCuentaDAO;
import net.tecgurus.jd0.model.CaracteristicasCuenta;

public class CaracteristicasCuentaService {

	public List<CaracteristicasCuenta> obtenerCaracteristicasPorIdCuenta(long idCuenta){
		CaracteristicasCuentaDAO carDAO = new CaracteristicasCuentaDAO();
		return carDAO.obtenerCaracteristicasPorIdCuenta(idCuenta);
	}
	
	public boolean insertarCaracteristicas(List<Integer> caracteristicas, long idCuenta){
		List<CaracteristicasCuenta> caracteristicasCuenta = new ArrayList<>();
		for (Integer idCaracteristica : caracteristicas) {
			CaracteristicasCuenta car = new CaracteristicasCuenta();
			car.setIdCaracteristica(idCaracteristica);
			car.setIdCuenta(idCuenta);
			caracteristicasCuenta.add(car);
		}
		CaracteristicasCuentaDAO carDAO = new CaracteristicasCuentaDAO();
		return carDAO.insertarCaracteristicas(caracteristicasCuenta);		
	}
	
	public void eliminarCaracteristicasPorIdCuenta(long idCuenta){
		CaracteristicasCuentaDAO carDAO = new CaracteristicasCuentaDAO();
		carDAO.eliminarCaracteristicasPorIdCuenta(idCuenta);
	}
	
}
