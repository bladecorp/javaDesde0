package net.tecgurus.jd0.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.bd.BaseDeDatos;
import net.tecgurus.jd0.dto.CuentaDTO;
import net.tecgurus.jd0.dto.GraficaCuentasDTO;
import net.tecgurus.jd0.model.Cuenta;
import net.tecgurus.jd0.model.TipoCuenta;

public class CuentaDAO {

	public List<CuentaDTO> obtenerCuentasPorIdCliente(int idCliente){
		List<CuentaDTO> cuentas = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT cu.numero, cu.saldo, cu.idCliente, tc.id, tc.tipo FROM cuenta cu JOIN tipocuenta tc ON tc.id = cu.idTipoCuenta WHERE idCliente = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setInt(1, idCliente);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				CuentaDTO cuentaDTO = new CuentaDTO();
				cuentaDTO.setNumero(rs.getLong("cu.numero"));
				cuentaDTO.setSaldo(rs.getDouble("cu.saldo"));
				cuentaDTO.setIdCliente(rs.getInt("cu.idCliente"));
				TipoCuenta tc = new TipoCuenta();
				tc.setId(rs.getInt("tc.id"));
				tc.setTipo(rs.getString("tc.tipo"));
				cuentaDTO.setTipoCuenta(tc);
				cuentas.add(cuentaDTO);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return cuentas;
	}
	
	public List<GraficaCuentasDTO> obtenerCuentasPorBanco(){
		List<GraficaCuentasDTO> listaGrafica = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT b.id, b.nombre, COUNT(cu.numero) AS num_cuentas "
							+ "FROM banco b JOIN cliente c ON b.id = c.idBanco "
							+ "JOIN cuenta cu ON c.id = cu.idCliente GROUP BY b.id";
			Statement st = base.getConexion().createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				GraficaCuentasDTO grafica = new GraficaCuentasDTO();
				grafica.setIdBanco(rs.getInt("b.id"));
				grafica.setNombre(rs.getString("b.nombre"));
				grafica.setCuentas(rs.getInt("num_cuentas"));
				listaGrafica.add(grafica);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return listaGrafica;
	}
	
	public boolean insertarCuenta(Cuenta cuenta){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "INSERT INTO cuenta (numero, saldo, idCliente, idTipoCuenta) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setLong(1, cuenta.getNumero());
			ps.setDouble(2, 0);
			ps.setInt(3, cuenta.getIdCliente());
			ps.setInt(4, cuenta.getIdTipoCuenta());
			int exito = ps.executeUpdate();
			return exito == 1;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	public boolean eliminarCuenta(long idCuenta){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "DELETE FROM cuenta WHERE numero = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setLong(1, idCuenta);
			int exito = ps.executeUpdate();
			return exito == 1;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	public boolean actualizarSaldo(long idCuenta, double saldo){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "UPDATE cuenta SET saldo = ? WHERE numero = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setDouble(1, saldo);
			ps.setLong(2, idCuenta);
			int exito = ps.executeUpdate();
			return exito == 1;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	private void mostrarError(Exception e){
		System.out.println("*** ERROR: "+e.getMessage());
	}
	
}
