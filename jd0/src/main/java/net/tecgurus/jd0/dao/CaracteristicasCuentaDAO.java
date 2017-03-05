package net.tecgurus.jd0.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.bd.BaseDeDatos;
import net.tecgurus.jd0.model.CaracteristicasCuenta;

public class CaracteristicasCuentaDAO {

	public List<CaracteristicasCuenta> obtenerCaracteristicasPorIdCuenta(long idCuenta){
		List<CaracteristicasCuenta> caracteristicas = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT * FROM caracteristicas_cuenta WHERE idCuenta = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setLong(1, idCuenta);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				CaracteristicasCuenta car = new CaracteristicasCuenta();
				car.setId(rs.getInt("id"));
				car.setIdCaracteristica(rs.getInt("idCaracteristica"));
				car.setIdCuenta(rs.getLong("idCuenta"));
				caracteristicas.add(car);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return caracteristicas;
	}
	
	public boolean insertarCaracteristicas(List<CaracteristicasCuenta> caracteristicas){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "INSERT INTO caracteristicas_cuenta (idCaracteristica, idCuenta) VALUES(?, ?)";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			for (CaracteristicasCuenta car : caracteristicas) {
				ps.setInt(1, car.getIdCaracteristica());
				ps.setLong(2, car.getIdCuenta());
				ps.addBatch();
			}
			ps.executeBatch();
			return true;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}

	public boolean eliminarCaracteristicasPorIdCuenta(long idCuenta){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "DELETE FROM caracteristicas_cuenta WHERE idCuenta = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setLong(1, idCuenta);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	private void mostrarError(Exception e){
		System.out.println("** ERROR: "+e.getMessage());
	}
	
}
