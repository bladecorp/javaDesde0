package net.tecgurus.jd0.services;

import java.util.List;

import net.tecgurus.jd0.dao.ClienteDAO;
import net.tecgurus.jd0.dto.ClienteDTO;
import net.tecgurus.jd0.dto.CuentaDTO;
import net.tecgurus.jd0.model.Cliente;

public class ClienteService {
	
	public Cliente buscarClientePorId(int idCliente){
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.obtenerClientePorId(idCliente);
	}
	
	public List<ClienteDTO> buscarClientesPorIdBancoYvalor(String valor, int idBanco){
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.buscarClientesPorIdBancoYvalor(valor, idBanco);
	}
	
	public List<Cliente> obtenerClientesPorIdBanco(int idBanco){
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.obtenerClientesPorIdBanco(idBanco);
	}
	
	public boolean insertarCliente(Cliente cliente){
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.insertarCliente(cliente);
	}
	
	public boolean actualizarCliente(Cliente cliente){
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.actualizarCliente(cliente);
	}
	
	public void eliminarClientePorId(int idCliente){
		CuentaService cuentaService = new CuentaService();
		cuentaService.eliminarCuentasPorIdCliente(idCliente);
		
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.eliminarClientePorId(idCliente);
	}
	
	public void eliminarClientesPorIdBanco(int idBanco){
		List<Cliente> clientes = obtenerClientesPorIdBanco(idBanco);
		for (Cliente cliente : clientes) {
			eliminarClientePorId(cliente.getId());
		}
	}
	
}
