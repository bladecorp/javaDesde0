package net.tecgurus.jd0.views;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import net.tecgurus.jd0.dto.ClienteDTO;
import net.tecgurus.jd0.model.Banco;
import net.tecgurus.jd0.model.Cliente;
import net.tecgurus.jd0.model.EstadoCivil;
import net.tecgurus.jd0.services.BancoService;
import net.tecgurus.jd0.services.CatalogoService;
import net.tecgurus.jd0.services.ClienteService;
import net.tecgurus.jd0.util.Constantes;
import net.tecgurus.jd0.util.GenerarArchivo;
import net.tecgurus.jd0.util.Utilerias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class PanelCliente extends JPanel implements ActionListener {
	private JComboBox<Banco> cbBanco;
	private JTextField tfNombre, tfApaterno, tfAmaterno, tfBuscar;
	private JSpinner spEdad;
	private JRadioButton rdSoltero, rdCasado, rdDivorciado, rdViudo, rdOtro;
	private ButtonGroup grupoEstadoCivil;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnGuardar, btnRefrescarTabla, btnLimpiar;
	private List<ClienteDTO> clientes;
	private JPopupMenu popup;
	private JMenuItem itemBorrar;
	private JButton btnExcel;

	public PanelCliente() {
		setLayout(null);
		setSize(1250, 581);
		
		///***** BLOQUE SUPERIOR IZQUIERDO ********////
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 370, 98);
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(panel);
		panel.setLayout(null);
		
		JLabel lblBanco = new JLabel("BANCO");
		lblBanco.setBounds(10, 25, 161, 14);
		lblBanco.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblBanco);
		
		cbBanco = new JComboBox<Banco>();
		cbBanco.setBounds(178, 21, 179, 20);
		panel.add(cbBanco);
		cbBanco.addActionListener(this);
		
		JLabel lblBuscarCliente = new JLabel("BUSCAR CLIENTE");
		lblBuscarCliente.setBounds(10, 59, 161, 14);
		lblBuscarCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblBuscarCliente);
		
		tfBuscar = new JTextField();
		tfBuscar.setBounds(178, 56, 179, 20);
		tfBuscar.addActionListener(this);
		panel.add(tfBuscar);
		tfBuscar.setColumns(10);
		
		///***** BLOQUE INFERIOR IZQUIERDO ********////
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(10, 120, 370, 461);
		panelFormulario.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(panelFormulario);
		panelFormulario.setLayout(null);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(10, 15, 161, 14);
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblNombre);
		
		JLabel lblAPaterno = new JLabel("A. PATERNO");
		lblAPaterno.setBounds(10, 63, 161, 14);
		lblAPaterno.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblAPaterno);
		
		JLabel lblAMaterno = new JLabel("A. MATERNO");
		lblAMaterno.setBounds(10, 109, 161, 14);
		lblAMaterno.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblAMaterno);
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(18, 18, 120, 1); //SpinnerDateModel();
		spEdad = new JSpinner(spinnerModel);
		spEdad.setBounds(178, 155, 179, 20);
		panelFormulario.add(spEdad);
		
		rdSoltero = new JRadioButton("Solter@");
		rdSoltero.setBounds(178, 203, 109, 23);
		rdSoltero.setActionCommand("1");
		rdSoltero.setSelected(true);
		panelFormulario.add(rdSoltero);
		
		rdCasado = new JRadioButton("Casad@");
		rdCasado.setBounds(178, 229, 109, 23);
		rdCasado.setActionCommand("2");
		panelFormulario.add(rdCasado);
		
		rdDivorciado = new JRadioButton("Divorciad@");
		rdDivorciado.setBounds(178, 255, 109, 23);
		rdDivorciado.setActionCommand("3");
		panelFormulario.add(rdDivorciado);
		
		rdViudo = new JRadioButton("Viud@");
		rdViudo.setBounds(178, 281, 109, 23);
		rdViudo.setActionCommand("4");
		panelFormulario.add(rdViudo);
		
		rdOtro = new JRadioButton("Otro");
		rdOtro.setBounds(178, 307, 109, 23);
		rdOtro.setActionCommand("5");
		panelFormulario.add(rdOtro); 
		
		grupoEstadoCivil = new ButtonGroup();
		grupoEstadoCivil.add(rdSoltero);
		grupoEstadoCivil.add(rdCasado);
		grupoEstadoCivil.add(rdDivorciado);
		grupoEstadoCivil.add(rdViudo);
		grupoEstadoCivil.add(rdOtro);     
		
		tfNombre = new JTextField();
		tfNombre.setBounds(178, 11, 179, 20);
		panelFormulario.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfApaterno = new JTextField();
		tfApaterno.setBounds(178, 59, 179, 20);
		panelFormulario.add(tfApaterno);
		tfApaterno.setColumns(10);
		
		tfAmaterno = new JTextField();
		tfAmaterno.setBounds(178, 105, 179, 20);
		panelFormulario.add(tfAmaterno);
		tfAmaterno.setColumns(10);
		
		JLabel lblEdad = new JLabel("EDAD");
		lblEdad.setBounds(10, 159, 161, 14);
		lblEdad.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblEdad);
		
		JLabel lblEstadoCivil = new JLabel("ESTADO CIVIL");
		lblEstadoCivil.setBounds(10, 211, 161, 14);
		lblEstadoCivil.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblEstadoCivil);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setBounds(140, 350, 146, 51);
		btnGuardar.addActionListener(this);
		panelFormulario.add(btnGuardar);
		
		btnLimpiar = new JButton(Utilerias.obtenerImagen(Constantes.Imagen.CLEAR));
		btnLimpiar.setBounds(31, 389, 57, 61);
	//	btnLimpiar.setIcon(Utilerias.obtenerImagen(Constantes.Imagen.CLEAR));
		btnLimpiar.setFocusPainted(false);
		btnLimpiar.setContentAreaFilled(false);
		btnLimpiar.addActionListener(this);
		panelFormulario.add(btnLimpiar);
		
		btnRefrescarTabla = new JButton(Utilerias.obtenerImagen(Constantes.Imagen.REFRESH));
		btnRefrescarTabla.setBounds(31, 322, 57, 56);
		btnRefrescarTabla.setFocusPainted(false);
		btnRefrescarTabla.setContentAreaFilled(false);
	//	btnRefrescarTabla.setBorderPainted(false);
		btnRefrescarTabla.addActionListener(this);
		panelFormulario.add(btnRefrescarTabla);
		
		btnExcel = new JButton(Utilerias.obtenerImagen(Constantes.Imagen.EXCEL));
		btnExcel.setBounds(296, 399, 74, 51); btnExcel.addActionListener(this);
	//	btnExcel.setOpaque(false);
		btnExcel.setFocusPainted(false);
		btnExcel.setContentAreaFilled(false);
	//	btnExcel.setBorderPainted(false);
		panelFormulario.add(btnExcel);
		
		///***** BLOQUE COMPLETO DERECHA ********////
		JPanel panelTabla = new JPanel();
		panelTabla.setBounds(390, 11, 850, 570);
		panelTabla.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(panelTabla);
		panelTabla.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 849, 570);
		panelTabla.add(scrollPane);
		
		table = new JTable();
		String[] nombreColumnas = {"ID","NOMBRE", "A. PATERNO", "A. MATERNO", "EDAD", "BANCO", "ESTADO CIVIL"};
	//	Object[][] nObjeto = {};
		table.setModel(new DefaultTableModel(new Object[][]{}, nombreColumnas));
		table.setEnabled(false);
		tableModel = (DefaultTableModel)table.getModel();
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event){
				int row = table.rowAtPoint(event.getPoint()); //OBTENEMOS PUNTO DE SELECCION
				table.getSelectionModel().setSelectionInterval(row, row); // SELECCIONAMOS FILA
				obtenerCliente();
				if(event.getButton() == MouseEvent.BUTTON3){
					popup.show(table, event.getX(), event.getY());
				}
			}
		});
		
		clientes = new ArrayList<>();
		
		popup = new JPopupMenu();
		
		itemBorrar = new JMenuItem("Borrar");
		itemBorrar.addActionListener(this);
		popup.add(itemBorrar);
						
		cargarContenidoInicial();
	}
	
	private void cargarBancos(){
		cbBanco.removeAllItems();
		BancoService bancoService = new BancoService();
		List<Banco> bancos = bancoService.obtenerBancos();
		for (Banco banco : bancos) {
			cbBanco.addItem(banco);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == cbBanco || event.getSource() == tfBuscar){
			limpiarCampos();
			cargarClientes();
		}else if(event.getSource() == btnGuardar){
			Cliente cliente = validarCliente();
			if(cliente != null){
				if(table.getSelectedRow() == -1){
					insertarCliente(cliente);
				}else{
					actualizarCliente(cliente);
				}
			}
		}else if(event.getSource() == itemBorrar){
			int id = (int)table.getValueAt(table.getSelectedRow(), 0);
			eliminarCliente(id);
		}else if(event.getSource() == btnRefrescarTabla){
			cargarClientes();
		}else if(event.getSource() == btnLimpiar){
			limpiarCampos();
			table.clearSelection();
		}else if(event.getSource() == btnExcel){
			try {
				GenerarArchivo.excel(clientes);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error al generar el archivo", "Error Excel", JOptionPane.OK_OPTION);
			}
		}
	}
	
	private void obtenerCliente(){
		int id = (int)table.getValueAt(table.getSelectedRow(), 0);
		ClienteService clienteService = new ClienteService();
		Cliente cliente = clienteService.buscarClientePorId(id);
		tfNombre.setText(cliente.getNombre());
		tfApaterno.setText(cliente.getApaterno());
		tfAmaterno.setText(cliente.getAmaterno());
		spEdad.setValue(cliente.getEdad());
		switch(cliente.getIdEstadoCivil()){
			case Constantes.TipoEdoCivil.SOLTERO:
				rdSoltero.setSelected(true);
				break;
			case Constantes.TipoEdoCivil.CASADO:
				rdCasado.setSelected(true);
				break;
			case Constantes.TipoEdoCivil.DIVORCIADO:
				rdDivorciado.setSelected(true);
				break;
			case Constantes.TipoEdoCivil.VIUDO:
				rdViudo.setSelected(true);
				break;
			case Constantes.TipoEdoCivil.OTRO:
				rdOtro.setSelected(true);
				break;
		}
	}
	
	private void actualizarCliente(Cliente cliente){
		ClienteService clienteService = new ClienteService();
		boolean exito = clienteService.actualizarCliente(cliente);
		if(exito){
			cargarClientes();
			limpiarCampos();
		}else{
			JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar el cliente", "Error", JOptionPane.OK_OPTION);
		}
	}
	
	private void eliminarCliente(int idCliente){
		ClienteService clienteService = new ClienteService();
		clienteService.eliminarClientePorId(idCliente);
		cargarClientes();
	}
	
	private Cliente validarCliente(){
		
		String mensaje = null;
		if(tfNombre.getText().trim().isEmpty()){
			mensaje = "Debe escribir el nombre";
		}else if(tfApaterno.getText().trim().isEmpty()){
			mensaje = "Debe escribir el apellido paterno";
		}else if(tfAmaterno.getText().trim().isEmpty()){
			mensaje = "Debe escribir el apellido materno"; 
		}  
		
		Cliente cliente = null;
		if(mensaje != null){
			JOptionPane.showMessageDialog(this, mensaje, "Error en Formulario", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
		}else{
			cliente = new Cliente();
			cliente.setNombre(tfNombre.getText().trim());
			cliente.setApaterno(tfApaterno.getText().trim());
			cliente.setAmaterno(tfAmaterno.getText().trim());
			cliente.setEdad((int)spEdad.getValue());
			Banco banco = (Banco)cbBanco.getSelectedItem();
			cliente.setIdBanco(((Banco)cbBanco.getSelectedItem()).getId());
			int estado = Integer.parseInt(grupoEstadoCivil.getSelection().getActionCommand());
			cliente.setIdEstadoCivil(estado);
		}
		
		return cliente;
	}
	
	private void insertarCliente(Cliente cliente){
		ClienteService clienteService = new ClienteService();
		boolean exito = clienteService.insertarCliente(cliente);
		if(exito){
			cargarClientes();
			limpiarCampos();
		}else{
			JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el cliente", "Error", JOptionPane.OK_OPTION);
		}
	}
		
	private void cargarClientes(){
		tableModel.setRowCount(0); //LIMPIAR TABLA
		
		String valor = tfBuscar.getText().trim();
		Banco banco = (Banco)cbBanco.getSelectedItem();
		if(banco != null){
			ClienteService clienteService = new ClienteService();
			clientes = clienteService.buscarClientesPorIdBancoYvalor(valor, banco.getId());
			for (ClienteDTO cliente : clientes) {
				Object[] client = {cliente.getId(), cliente.getNombre(), cliente.getApaterno(), cliente.getAmaterno(), 
									cliente.getEdad(), cliente.getBanco().getNombre(), cliente.geteCivil().getTipo()};
				tableModel.addRow(client);
			}
		}
		tableModel.fireTableDataChanged();
	}
	
	private void limpiarCampos(){
	//	tfBuscar.setText("");
		tfNombre.setText("");
		tfApaterno.setText("");
		tfAmaterno.setText("");
	}
	
	public void cargarContenidoInicial(){//METODO QUE SERÁ INVOCADO DESDE EL CARD PANEL
		limpiarCampos();
		cargarBancos();
	}
	
}
