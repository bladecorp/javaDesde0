package net.tecgurus.jd0.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

import net.tecgurus.jd0.model.Administrador;
import net.tecgurus.jd0.services.AdminService;
import net.tecgurus.jd0.util.Constantes;
import net.tecgurus.jd0.util.Utilerias;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class PanelAdmin extends JPanel implements ActionListener{
	private JTextField tfNombre;
	private JTextField tfApaterno;
	private JTextField tfAmaterno;
	private JTextField tfUsuario;
	private JTextField tfPassword;
	private JButton btnGuardar, btnRefrescarTabla, btnLimpiar;
	private JTable tblAdmin;
	private DefaultTableModel tblAdminModel;
	private JPopupMenu popup;
	private JMenuItem menuBorrar;
	
	public PanelAdmin() {
	//	setVisible(true);
		setLayout(null); 
		setSize(new Dimension(1250, 580));
		JPanel panelFormulario = new JPanel(); 
		panelFormulario.setBounds(10, 11, 359, 569);
		panelFormulario.setLayout(null);
		
	/*	TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Agregar Administrador");
		title.setTitleJustification(TitledBorder.CENTER); //TitledBorder.RIGHT o sin esta linea para que se alinee a la izquierda
		title.setTitlePosition(TitledBorder.ABOVE_TOP); //este saca el titulo de la linea
		panelFormulario.setBorder(title);  */
		
	//	panelFormulario.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	//	panelFormulario.setBorder(BorderFactory.createLineBorder(Color.black));
	//	panelFormulario.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	//	panelFormulario.setBorder(BorderFactory.createRaisedBevelBorder());
	//	panelFormulario.setBorder(BorderFactory.createLoweredBevelBorder());
		panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                          BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		
		
		add(panelFormulario);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(102, 60, 160, 20);
		panelFormulario.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfApaterno = new JTextField();
		tfApaterno.setBounds(102, 146, 160, 20);
		panelFormulario.add(tfApaterno);
		tfApaterno.setColumns(10);
		
		tfAmaterno = new JTextField();
		tfAmaterno.setBounds(102, 241, 160, 20);
		panelFormulario.add(tfAmaterno);
		tfAmaterno.setColumns(10);
		
		tfUsuario = new JTextField();
		tfUsuario.setBounds(102, 331, 160, 20);
		panelFormulario.add(tfUsuario);
		tfUsuario.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(102, 432, 160, 20);
		panelFormulario.add(tfPassword);
		tfPassword.setColumns(10);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setBounds(102, 482, 160, 54);
		btnGuardar.setIcon(Utilerias.obtenerImagen(Constantes.Imagen.SAVE));
		btnGuardar.addActionListener(this);
		panelFormulario.add(btnGuardar);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(10, 35, 339, 14);
		lblNombre.setFont(Utilerias.Tahoma16());
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		panelFormulario.add(lblNombre);
		
		JLabel lblApellidoPaterno = new JLabel("A. PATERNO");
		lblApellidoPaterno.setBounds(10, 121, 339, 14);
		lblApellidoPaterno.setFont(Utilerias.Tahoma16());
		lblApellidoPaterno.setHorizontalAlignment(SwingConstants.CENTER);
		panelFormulario.add(lblApellidoPaterno);
		
		JLabel lblApellidoMaterno = new JLabel("A. MATERNO");
		lblApellidoMaterno.setBounds(10, 216, 339, 14);
		lblApellidoMaterno.setFont(Utilerias.Tahoma16());
		lblApellidoMaterno.setHorizontalAlignment(SwingConstants.CENTER);
		panelFormulario.add(lblApellidoMaterno);
		
		JLabel lblUsuario = new JLabel("USUARIO");
		lblUsuario.setBounds(10, 306, 339, 14);
		lblUsuario.setFont(Utilerias.Tahoma16());
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelFormulario.add(lblUsuario);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(10, 407, 339, 14);
		lblPassword.setFont(Utilerias.Tahoma16());
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		panelFormulario.add(lblPassword);
		
		btnLimpiar = new JButton(Utilerias.obtenerImagen(Constantes.Imagen.CLEAR));
		btnLimpiar.setBounds(10, 11, 54, 54);
	//	btnLimpiar.setIcon(Utilerias.obtenerImagen(Constantes.Imagen.CLEAR));
		btnLimpiar.setFocusPainted(false); //PARA QUITAR EL BORDE CUANDO SE HACE CLICK
		btnLimpiar.setContentAreaFilled(false); //TRANSPARENCIA EN BOTON
		btnLimpiar.addActionListener(this);
		panelFormulario.add(btnLimpiar);
		
		btnRefrescarTabla = new JButton(Utilerias.obtenerImagen(Constantes.Imagen.REFRESH));
		btnRefrescarTabla.setBounds(295, 11, 54, 54);
	//	btnRefrescarTabla.setIcon(Utilerias.obtenerImagen(Constantes.Imagen.REFRESH));
		btnRefrescarTabla.setFocusPainted(false);
		btnRefrescarTabla.setContentAreaFilled(false);
		btnRefrescarTabla.addActionListener(this);
		panelFormulario.add(btnRefrescarTabla);
		
		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(null);
		panelTabla.setBounds(379, 11, 861, 620);
		add(panelTabla);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(new Dimension(857, 569));
		panelTabla.add(scrollPane);
		
		tblAdmin = new JTable();
		scrollPane.setViewportView(tblAdmin);
		String[] nombresColumnas = {"USUARIO", "PASSWORD", "NOMBRE", "A. PATERNO", "A. MATERNO"};
		tblAdmin.setModel(new DefaultTableModel(new Object[][] {},nombresColumnas));
		tblAdmin.setEnabled(false);
		tblAdminModel = (DefaultTableModel) tblAdmin.getModel();
		
		popup = new JPopupMenu();
		menuBorrar = new JMenuItem("Borrar");
		menuBorrar.addActionListener(this);
		popup.add(menuBorrar);
		
		tblAdmin.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event){
				int row = tblAdmin.rowAtPoint(event.getPoint());
				tblAdmin.getSelectionModel().setSelectionInterval(row, row);
				tfUsuario.setEditable(false); // para no poder cambiar el nombre de usuario
				tfUsuario.setText((String)tblAdmin.getValueAt(row, 0));
				tfPassword.setText((String)tblAdmin.getValueAt(row, 1));
				tfNombre.setText((String)tblAdmin.getValueAt(row, 2));
				tfApaterno.setText((String)tblAdmin.getValueAt(row, 3));
				tfAmaterno.setText((String)tblAdmin.getValueAt(row, 4));
				if(event.getButton() == MouseEvent.BUTTON3){
					popup.show(tblAdmin, event.getX(), event.getY());
				}
			}
		});
		
		cargarContenidoInicial();
		
	}
	
	private List<Administrador> obtenerAdministradores(){
		tblAdminModel.setRowCount(0);
		AdminService adminService = new AdminService();
		List<Administrador> administradores = adminService.obtenerAdministradores();
		if(administradores != null){
			for (Administrador admin : administradores) {
				tblAdminModel.addRow(new Object[]{admin.getUsuario(), admin.getPassword(), admin.getNombre(), admin.getApaterno(), admin.getAmaterno()});
			}
		}else{
			administradores = new ArrayList<>();
		}
		tblAdminModel.fireTableDataChanged();
		return administradores;
	}
	
	private void insertarAdministrador(){
		Administrador admin = validarFormulario();
		if(admin != null){
			AdminService service = new AdminService();
			boolean exito = service.insertarAdministrador(admin);
			if(exito){
				limpiarCampos();
				obtenerAdministradores();
			}else{
				JOptionPane.showMessageDialog(this, "No fue posible guardar la información. "
				+ "Posiblemente el nombre de usuario ya esté registrado","Error al Guardar", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private void eliminarAdministrador(String user){
		AdminService service = new AdminService();
		service.eliminarAdministradorPorId(user);
		limpiarCampos();
		obtenerAdministradores();
	}
	
	private void actualizarAdministrador(){
		Administrador admin = validarFormulario();
		if(admin != null){
			AdminService service = new AdminService();
			boolean exito = service.actualizarAdministrador(admin);
			if(exito){
				limpiarCampos();
				obtenerAdministradores();
			}else{
				JOptionPane.showMessageDialog(this, "No fue posible actualizar la información. ","Error al Actualizar", 
						JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnGuardar){
			if(tblAdmin.getSelectedRow() == -1)
				insertarAdministrador();
			else
				actualizarAdministrador();
		}else if(event.getSource() == menuBorrar){
			int row = tblAdmin.getSelectedRow();
			String user = (String)tblAdmin.getValueAt(row, 0);
			eliminarAdministrador(user);
		}else if(event.getSource() == btnLimpiar){
			tblAdmin.clearSelection(); //quitamos fila seleccionada
			limpiarCampos();
		}else if(event.getSource() == btnRefrescarTabla){
			limpiarCampos();
			obtenerAdministradores();
		}
	}
	
	private Administrador validarFormulario(){
		Administrador admin = null;
		String mensaje = null;
		if(tfNombre.getText().isEmpty()){
			mensaje = "Debe escribir el nombre";
		}else if(tfApaterno.getText().isEmpty()){
			mensaje = "Debe escribir el apellido paterno";
		}else if(tfUsuario.getText().isEmpty()){
			mensaje = "Debe escribir el usuario";
		}else if(tfPassword.getText().isEmpty()){
			mensaje = "Debe escribir el password";
		}
		
		if(mensaje != null){
			JOptionPane.showMessageDialog(this, mensaje,"Formulario Incompleto", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
			return admin;
		}
		admin = new Administrador();
		admin.setUsuario(tfUsuario.getText().trim());
		admin.setPassword(tfPassword.getText().trim());
		admin.setNombre(tfNombre.getText().trim());
		admin.setApaterno(tfApaterno.getText().trim());
		admin.setAmaterno(tfAmaterno.getText().trim());
		
		return admin;
	}
	
	private void limpiarCampos(){
		tfNombre.setText("");
		tfApaterno.setText("");
		tfAmaterno.setText("");
		tfUsuario.setText("");
		tfPassword.setText("");
		tfUsuario.setEditable(true);
	}
	
	public void cargarContenidoInicial(){//METODO QUE SERÁ INVOCADO DESDE EL CARD PANEL
		limpiarCampos();
		obtenerAdministradores();
	}
	
}
