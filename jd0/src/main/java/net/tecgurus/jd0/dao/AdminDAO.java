package net.tecgurus.jd0.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.bd.BaseDeDatos;
import net.tecgurus.jd0.model.Administrador;

public class AdminDAO {

	public List<Administrador> obtenerAdministradores(){
		BaseDeDatos bd = new BaseDeDatos();
		
		try{
			
		//	Statement statement = bd.getConexion().createStatement();
		//	statement.executeQuery(sql); //SOLO PARA SELECTS
		//	statement.execute(sql);// PARA INSERT, UPDATE, DELETE
			List<Administrador> administradores = new ArrayList<>();
			String query = "SELECT * FROM administrador ORDER BY nombre;";
			bd.conectar();
			PreparedStatement ps = bd.getConexion().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Administrador admin = new Administrador();
				admin.setUsuario(rs.getString("user"));
				admin.setPassword(rs.getString("password"));
				admin.setNombre(rs.getString("nombre"));
				admin.setApaterno(rs.getString("apaterno"));
				admin.setAmaterno(rs.getString("amaterno"));
				administradores.add(admin);
			}
			return administradores;
		}catch(Exception e){
			mostrarError(e);
			return null;
		}finally {
			bd.desconectar();
		}
		
	}
	
	public Administrador obtenerAdministradorPorIdYpassword(String user, String password){
		BaseDeDatos bd = new BaseDeDatos();
		try{
			bd.conectar();
			String query = "SELECT * FROM administrador WHERE user = ? AND password = ?";
			PreparedStatement ps = bd.getConexion().prepareStatement(query);
			ps.setString(1, user);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Administrador admin = new Administrador();
				admin.setUsuario(rs.getString("user"));
				admin.setPassword(rs.getString("password"));
				admin.setNombre(rs.getString("nombre"));
				admin.setApaterno(rs.getString("apaterno"));
				admin.setAmaterno(rs.getString("amaterno"));
				return admin;
			}
			return null;
		}catch(Exception e){
			mostrarError(e);
			return null;
		}finally {
			bd.desconectar();
		}
	}
	
	public boolean insertarAdministrador(Administrador admin){
		BaseDeDatos bd = new BaseDeDatos();
		try {
			String query = "INSERT INTO administrador VALUES(?, ?, ?, ?, ?)";
			bd.conectar();
			PreparedStatement ps = bd.getConexion().prepareStatement(query);
			ps.setString(1, admin.getUsuario());
			ps.setString(2, admin.getPassword());
			ps.setString(3, admin.getNombre()); 
			ps.setString(4, admin.getApaterno());
			ps.setString(5, admin.getAmaterno());
			int exito = ps.executeUpdate();
			if(exito == 1){
				System.out.println("SE INSERTÓ CORRECTAMENTE");
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
	
	public boolean actualizarAdministradorPorId(Administrador admin){
		BaseDeDatos bd = new BaseDeDatos();
		try{
			String query = "UPDATE administrador SET password=?, nombre=?, apaterno=?, amaterno=? WHERE user=?";
			bd.conectar();
			PreparedStatement ps = bd.getConexion().prepareStatement(query);
			ps.setString(1, admin.getPassword());
			ps.setString(2, admin.getNombre());
			ps.setString(3, admin.getApaterno());
			ps.setString(4, admin.getAmaterno());
			ps.setString(5, admin.getUsuario());
			int exito = ps.executeUpdate();
			if(exito == 1){
				return true;
			}
			return false;
		}catch(Exception e){
			mostrarError(e);
			return false;
		}finally {
			bd.desconectar();
		}
		
	}
	
	public boolean eliminarAdministradorPorId(String user){
		BaseDeDatos bd = new BaseDeDatos();
		try {
			String query = "DELETE FROM administrador WHERE user=?";
			bd.conectar();
			PreparedStatement ps = bd.getConexion().prepareStatement(query);
			ps.setString(1, user);
			int exito = ps.executeUpdate();
			if(exito == 1){
				return true;
			}
			return false;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally{
			bd.desconectar();
		}
	}
	
	private void mostrarError(Exception e){
		System.out.println("ERROR: "+e.getMessage());
	}
	
}
