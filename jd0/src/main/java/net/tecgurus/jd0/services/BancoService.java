package net.tecgurus.jd0.services;

import java.util.List;

import net.tecgurus.jd0.dao.BancoDAO;
import net.tecgurus.jd0.model.Banco;

public class BancoService {

	public List<Banco> obtenerBancos(){
		BancoDAO bancoDAO = new BancoDAO();
		return bancoDAO.obtenerBancos();
	}
	
	public boolean insertarBanco(Banco banco){
		BancoDAO bancoDAO = new BancoDAO();
		return bancoDAO.insertarBanco(banco);
	}
	
	public boolean actualizarBanco(Banco banco){
		BancoDAO bancoDAO = new BancoDAO();
		return bancoDAO.actualizarBanco(banco);
	}
	
	public void eliminarBancoPorId(int idBanco){
		ClienteService clienteService = new ClienteService();
		clienteService.eliminarClientesPorIdBanco(idBanco);
		BancoDAO bancoDAO = new BancoDAO();
		bancoDAO.eliminarBancoPorId(idBanco);
	}
	
}
