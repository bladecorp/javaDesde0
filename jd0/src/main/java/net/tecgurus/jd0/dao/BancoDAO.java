package net.tecgurus.jd0.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.bd.BaseDeDatos;
import net.tecgurus.jd0.model.Banco;

public class BancoDAO {
	
	public List<Banco> obtenerBancos(){
		List<Banco> bancos = new ArrayList<>();
		BaseDeDatos bd = new BaseDeDatos();
		try {
			bd.conectar();
			String query = "SELECT * FROM banco";
			Statement statement = bd.getConexion().createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Banco banco = new Banco();
				banco.setId(rs.getInt("id"));
				banco.setNombre(rs.getString("nombre"));
				banco.setImagen(rs.getString("imagen"));
				bancos.add(banco);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally{
			bd.desconectar();
		}
		return bancos;
	}
	
	public boolean insertarBanco(Banco banco){
		BaseDeDatos bd = new BaseDeDatos();
		try {
			bd.conectar();
			String query = "INSERT INTO banco (nombre, imagen) VALUES (?, ?)";
			PreparedStatement ps = bd.getConexion().prepareStatement(query);
			ps.setString(1, banco.getNombre());
			ps.setString(2, banco.getImagen());
			int exito = ps.executeUpdate();
			if(exito == 1){
				return true;
			}
			return false;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			bd.desconectar();
		}
	}
	
	public boolean actualizarBanco(Banco banco){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "UPDATE banco SET nombre = ?, imagen = ? WHERE id = ?"; 
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setString(1, banco.getNombre());
			ps.setString(2, banco.getImagen());
			ps.setInt(3, banco.getId());
			int exito = ps.executeUpdate();
			if(exito == 1){
				return true;
			}
			return false;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	public boolean eliminarBancoPorId(int idBanco){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "DELETE FROM banco WHERE id = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setInt(1, idBanco);
			int exito = ps.executeUpdate();
			if(exito == 1){
				return true;
			}
			return false;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	private void mostrarError(Exception e){
		System.out.println("ERROR: "+e.getMessage());
	}

}
