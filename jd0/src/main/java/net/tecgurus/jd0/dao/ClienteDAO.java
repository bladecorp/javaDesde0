package net.tecgurus.jd0.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.tecgurus.jd0.bd.BaseDeDatos;
import net.tecgurus.jd0.dto.ClienteDTO;
import net.tecgurus.jd0.model.Banco;
import net.tecgurus.jd0.model.Cliente;
import net.tecgurus.jd0.model.EstadoCivil;

public class ClienteDAO {
	
	public Cliente obtenerClientePorId(int idCliente){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT * FROM cliente WHERE id = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setInt(1, idCliente);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Cliente cliente  = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApaterno(rs.getString("apaterno"));
				cliente.setAmaterno(rs.getString("amaterno"));
				cliente.setEdad(rs.getInt("edad"));
				cliente.setIdBanco(rs.getInt("idBanco"));
				cliente.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
				return cliente;
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return null;
	}
	
	public List<Cliente> obtenerClientesPorIdBanco(int idBanco){
		List<Cliente> clientes = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "SELECT * FROM cliente WHERE idBanco = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setInt(1, idBanco);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Cliente cliente  = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApaterno(rs.getString("apaterno"));
				cliente.setAmaterno(rs.getString("amaterno"));
				cliente.setEdad(rs.getInt("edad"));
				cliente.setIdBanco(rs.getInt("idBanco"));
				cliente.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
				clientes.add(cliente);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return clientes;
	}
	
	public List<ClienteDTO> buscarClientesPorIdBancoYvalor(String valor, int idBanco){//COMBINA COMBO BANCO Y FILTRO BUSQUEDA
		List<ClienteDTO> clientes = new ArrayList<>();
		BaseDeDatos base = new BaseDeDatos();
		try {
			String like = "%"+valor+"%";
			String query = "SELECT c.id, c.nombre, c.apaterno, c.amaterno, c.edad, c.idBanco, ec.id, ec.tipo, b.id, b.nombre "
					+ "FROM cliente c "
					+ "JOIN tipoecivil ec ON c.idEstadoCivil = ec.id "
					+ "JOIN banco b ON b.id = c.idBanco "
					+ "WHERE c.idBanco = ? AND ( c.nombre like ? OR c.apaterno like ? OR c.amaterno like ? )";
			base.conectar();
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setInt(1, idBanco);
			ps.setString(2, like);
			ps.setString(3, like);
			ps.setString(4, like);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ClienteDTO clienteDTO = new ClienteDTO();
				clienteDTO.setId(rs.getInt("c.id"));
				clienteDTO.setNombre(rs.getString("c.nombre"));
				clienteDTO.setApaterno(rs.getString("c.apaterno"));
				clienteDTO.setAmaterno(rs.getString("c.amaterno"));
				clienteDTO.setEdad(rs.getInt("c.edad"));
				
				EstadoCivil ec = new EstadoCivil();
				ec.setId(rs.getInt("ec.id"));
				ec.setTipo(rs.getString("ec.tipo"));
				clienteDTO.seteCivil(ec);
				
				Banco banco = new Banco();
				banco.setId(rs.getInt("b.id"));
				banco.setNombre(rs.getString("b.nombre"));
				clienteDTO.setBanco(banco);
				
				clientes.add(clienteDTO);
			}
		} catch (Exception e) {
			mostrarError(e);
		}finally {
			base.desconectar();
		}
		return clientes;
	}
	
	public boolean insertarCliente(Cliente cliente){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "INSERT INTO cliente(nombre, apaterno, amaterno, edad, idBanco, idEstadoCivil) VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getApaterno());
			ps.setString(3, cliente.getAmaterno());
			ps.setInt(4, cliente.getEdad());
			ps.setInt(5, cliente.getIdBanco());
			ps.setInt(6, cliente.getIdEstadoCivil());
			int exito = ps.executeUpdate();
			return exito == 1;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	public boolean actualizarCliente(Cliente cliente){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "UPDATE cliente SET nombre=?, apaterno=?, amaterno=?, edad=?, idBanco=?, idEstadoCivil=? WHERE id=?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getApaterno());
			ps.setString(3, cliente.getAmaterno());
			ps.setInt(4, cliente.getEdad());
			ps.setInt(5, cliente.getIdBanco());
			ps.setInt(6, cliente.getIdEstadoCivil());
			ps.setInt(7, cliente.getId());
			int exito = ps.executeUpdate();
			return exito == 1;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	public boolean eliminarClientePorId(int idCliente){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "DELETE FROM cliente WHERE id = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setInt(1, idCliente);
			int exito = ps.executeUpdate();
			return exito == 1;
		} catch (Exception e) {
			mostrarError(e);
			return false;
		}finally {
			base.desconectar();
		}
	}
	
	public boolean eliminarClientesPorIdBanco(int idBanco){
		BaseDeDatos base = new BaseDeDatos();
		try {
			base.conectar();
			String query = "DELETE FROM cliente WHERE idBanco = ?";
			PreparedStatement ps = base.getConexion().prepareStatement(query);
			ps.setInt(1, idBanco);
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
		System.out.println("ERROR: "+e.getMessage());
	}
	
}
