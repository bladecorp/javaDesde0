package net.tecgurus.jd0.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.bd.BaseDeDatos;
import net.tecgurus.jd0.model.EstadoCivil;
import net.tecgurus.jd0.model.TipoCuenta;
import net.tecgurus.jd0.model.TipoMovimiento;

public class CatalogoDAO {

	public List<TipoCuenta> obtenerTiposCuenta(){
		List<TipoCuenta> tiposCuenta = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT * FROM tipocuenta";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				TipoCuenta tipoCuenta = new TipoCuenta();
				tipoCuenta.setId(rs.getInt("id"));
				tipoCuenta.setTipo(rs.getString("tipo"));
				tiposCuenta.add(tipoCuenta);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return tiposCuenta;
	}
	
	public List<TipoMovimiento> obtenerTiposMovimiento(){
		List<TipoMovimiento> tiposMovimiento = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT * FROM tipomovimiento";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				TipoMovimiento tipoMovimiento = new TipoMovimiento();
				tipoMovimiento.setId(rs.getInt("id"));
				tipoMovimiento.setTipo(rs.getString("tipo"));
				tiposMovimiento.add(tipoMovimiento);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return tiposMovimiento;
	}
	
	public List<EstadoCivil> obtenerTipoEstadoCivil(){
		List<EstadoCivil> estadosCiviles = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT * FROM tipoecivil";
			Statement st = base.getConexion().createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				EstadoCivil eCivil = new EstadoCivil();
				eCivil.setId(rs.getInt("id"));
				eCivil.setTipo(rs.getString("tipo"));
				estadosCiviles.add(eCivil);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return estadosCiviles;
	}
	
	private void mostrarError(Exception e){
		System.out.println("ERROR: "+e.getMessage());
	}
}
