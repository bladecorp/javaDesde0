package net.tecgurus.jd0.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.bd.BaseDeDatos;
import net.tecgurus.jd0.dto.MovimientoDTO;
import net.tecgurus.jd0.model.Movimiento;
import net.tecgurus.jd0.model.TipoMovimiento;

public class MovimientoDAO {

	public List<MovimientoDTO> obtenerMovimientosPorIdCuentaYtipo(long idCuenta, int tipoMovimiento){
		List<MovimientoDTO> movimientos = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			boolean hayTipoMovimiento = tipoMovimiento > 0;
			String tipo =  hayTipoMovimiento ? " AND idTipoMovimiento = ?" : "";
			String query = "SELECT m.id, m.fecha, m.mensaje, m.monto, m.saldoAnterior, m.saldoActual, tm.id, tm.tipo "
					+ "FROM movimiento m JOIN tipomovimiento tm ON m.idTipoMovimiento = tm.id WHERE idCuenta = ?"+tipo+" ORDER BY m.fecha DESC";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setLong(1, idCuenta);
			if(hayTipoMovimiento){
				ps.setInt(2, tipoMovimiento);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MovimientoDTO movimientoDTO = new MovimientoDTO();
				movimientoDTO.setId(rs.getInt("m.id"));
				movimientoDTO.setFecha(rs.getTimestamp("m.fecha"));
				movimientoDTO.setMensaje(rs.getString("m.mensaje"));
				movimientoDTO.setMonto(rs.getDouble("m.monto"));
				movimientoDTO.setSaldoAnterior(rs.getDouble("m.saldoAnterior"));
				movimientoDTO.setSaldoActual(rs.getDouble("m.saldoActual"));
				
				movimientoDTO.setTipoMovimiento(new TipoMovimiento(rs.getInt("tm.id"), rs.getString("tm.tipo")));
				
				movimientos.add(movimientoDTO);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return movimientos;
	}
	
	public boolean insertarMovimiento(Movimiento movimiento){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "INSERT INTO movimiento(mensaje, fecha, monto, saldoActual, saldoAnterior, idTipoMovimiento, idCuenta) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setString(1, movimiento.getMensaje());
			ps.setTimestamp(2, new java.sql.Timestamp(movimiento.getFecha().getTime()));
			ps.setDouble(3, movimiento.getMonto());
			ps.setDouble(4, movimiento.getSaldoActual());
			ps.setDouble(5, movimiento.getSaldoAnterior());
			ps.setInt(6, movimiento.getIdTipoMovimiento());
			ps.setLong(7, movimiento.getIdCuenta());
			int exito = ps.executeUpdate();
			return exito == 1;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	public boolean borrarMovimientosPorIdCuenta(long idCuenta){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "DELETE FROM movimiento WHERE idCuenta = ?";
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
		System.out.println("*** ERROR: "+e.getMessage());
	}
	
}
