package net.tecgurus.jd0.services;

import java.util.List;

import net.tecgurus.jd0.dao.AdminDAO;
import net.tecgurus.jd0.model.Administrador;

public class AdminService {

	public List<Administrador> obtenerAdministradores(){
		AdminDAO adminDAO = new AdminDAO();
		return adminDAO.obtenerAdministradores();
	}
	
	public Administrador obtenerAdministradorPorIdYpassword(String user, String password){
		AdminDAO adminDAO = new AdminDAO();
		return adminDAO.obtenerAdministradorPorIdYpassword(user, password);
	}
	
	public boolean insertarAdministrador(Administrador admin){
		AdminDAO adminDAO = new AdminDAO();
		return adminDAO.insertarAdministrador(admin);
	}
	
	public boolean actualizarAdministrador(Administrador admin){
		AdminDAO adminDAO = new AdminDAO();
		return adminDAO.actualizarAdministradorPorId(admin);
	}
	
	public void eliminarAdministradorPorId(String user){
		AdminDAO adminDAO = new AdminDAO();
		adminDAO.eliminarAdministradorPorId(user);
	}
	
}
