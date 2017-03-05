package net.tecgurus.jd0.views;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import net.tecgurus.jd0.model.Administrador;
import net.tecgurus.jd0.services.AdminService;
import net.tecgurus.jd0.util.Constantes;
import net.tecgurus.jd0.util.Utilerias;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.Font;

public class Login extends JDialog implements ActionListener {
	
	private JTextField tfUsuario;
	private JTextField tfPassword;
	private JLabel lblMensaje;
	private JButton btnLogin;
//	private JLabel lblBtn;
	
	public Login() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //cerrar dialog
		setVisible(true);
	//	setResizable(false);
		setTitle("ACCESO AL SISTEMA BANCARIO");
		setBounds(400, 80, 534, 525);
		getContentPane().setLayout(null);
		
	//	ImageIcon image = new ImageIcon(getClass().getResource("/net/tecgurus/jd0/images/logo.png"));
		JLabel lblImage = new JLabel(Utilerias.obtenerImagen(Constantes.Imagen.LOGO));
		lblImage.setBounds(32, 11, 430, 190);
		getContentPane().add(lblImage);
		
		tfUsuario = new JTextField();
		tfUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		tfUsuario.setBounds(199, 269, 119, 20);
		tfUsuario.addActionListener(this);
		getContentPane().add(tfUsuario);
		
		tfPassword = new JPasswordField();
		tfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		tfPassword.setBounds(199, 343, 119, 20);
		tfPassword.addActionListener(this);
		getContentPane().add(tfPassword);
		
		JLabel lblUsuario = new JLabel("Usuario");
//		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(10, 244, 498, 14);
		getContentPane().add(lblUsuario);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
//		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(10, 318, 498, 14);
		getContentPane().add(lblPassword);
		
		lblMensaje = new JLabel("");
		lblMensaje.setFont(Utilerias.Tahoma16());
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(10, 210, 498, 33);
		
		getContentPane().add(lblMensaje);
		
	//	btnLogin = new JButton(Utilerias.obtenerImagen(Constantes.Imagen.LOGIN)); //imagen normal pero desproporcionada
		btnLogin = new JButton(Utilerias.obtenerYredimensionarImagen(200, 60, Constantes.Imagen.LOGIN));
		btnLogin.setBounds(10, 392, 498, 83);
		btnLogin.setContentAreaFilled(false); //TRANSPARENCIA. QUITA EL FONDO DEL BOTON
		btnLogin.setFocusPainted(false); // QUITA EL BORDE CUANDO SE HACE CLICK
		btnLogin.addActionListener(this);
		getContentPane().add(btnLogin);  
		
//		JLabel lblBackground = new JLabel(imageBackground); //agregar imagen de fondo
//		JLabel lblBackground = new JLabel(Utilerias.obtenerImagen(Constantes.Imagen.BACKGROUND_LOGIN));
//		lblBackground.setBounds(0, 0, 518, 486);
//		getContentPane().add(lblBackground);		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == tfUsuario || e.getSource() == tfPassword || e.getSource() == btnLogin){
			validarYaccesar();
		}
	}
	
	private void validarYaccesar(){
		String usuario = tfUsuario.getText().trim();
		String password = tfPassword.getText().trim();
		if(usuario.isEmpty()){
			lblMensaje.setText("Debe escribir el nombre de usuario");
			return;
		}
		if(password.isEmpty()){
			lblMensaje.setText("Debe escribir el password");
			return;
		}
		AdminService adminService = new AdminService();
		Administrador admin = adminService.obtenerAdministradorPorIdYpassword(usuario, password);
		if(admin != null){
			accesar();
		}else{
			lblMensaje.setText("Credenciales Incorrectas");
			return;
		}
	}
	
	private void accesar(){
		System.out.println("Intentando Ingresar al MainFrame !!");
		dispatchEvent(new WindowEvent(Login.this, WindowEvent.WINDOW_CLOSING));
		new MainFrame();
	}
}
