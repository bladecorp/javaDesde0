package net.tecgurus.jd0.util;

import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.text.NumberFormat;

import javax.swing.ImageIcon;

public class Utilerias {

	public static Font Tahoma16(){
		return new Font("Tahoma", Font.PLAIN, 14);
	}
	
	public static ImageIcon obtenerImagen(String nombreImagen){
		URL url = Utilerias.class.getResource("/net/tecgurus/jd0/images/"+nombreImagen);
		if(url == null){
			System.out.println("*** ERROR AL OBTENER IMAGEN ***");
			url = Utilerias.class.getResource("/net/tecgurus/jd0/images/construccion.png");
		}
		return new ImageIcon(url);
	}
	
	public static ImageIcon obtenerYredimensionarImagen(int ancho, int alto, String nombreImagen){
		ImageIcon imageIcon = obtenerImagen(nombreImagen);
		Image image = imageIcon.getImage();
		return new ImageIcon(image.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
	}
	
	public static String convertirAmoneda(double saldo){
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(saldo);
	}
	
}
