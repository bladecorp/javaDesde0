package net.tecgurus.jd0.views;

import javax.swing.JPanel;

import net.tecgurus.jd0.model.Cliente;
import javax.swing.JLabel;

public class DialogCliente extends JPanel{

	public DialogCliente(Cliente cliente){
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(79, 56, 46, 14);
		add(lblNombre);
		
		JLabel lblAPaterno = new JLabel("A. Paterno");
		lblAPaterno.setBounds(79, 111, 46, 14);
		add(lblAPaterno);
		
		JLabel lblAMaterno = new JLabel("A. Materno");
		lblAMaterno.setBounds(79, 164, 46, 14);
		add(lblAMaterno);
		
	}
	
}
