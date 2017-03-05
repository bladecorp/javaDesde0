package net.tecgurus.jd0.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDeDatos {
	
	private Connection conexion;

	public void conectar() throws Exception{
		if(conexion != null){return;}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("No se encontro del driver de MYSQL");
		}
		String connectionUrl = "jdbc:mysql://localhost:3306/base_jd0";
		conexion = DriverManager.getConnection(connectionUrl, "root", "5438");
		System.out.println("Conexion exitosa!! ");
	}
	
	public void desconectar(){
		if(conexion != null){
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("No fue posible cerrar la conexion a la base de datos.");
			}
		}
	}

	public Connection getConexion() {
		return conexion;
	}

}
