package net.tecgurus.jd0.views;

import javax.swing.JPanel;

import net.tecgurus.jd0.dto.CuentaDTO;
import net.tecgurus.jd0.dto.MovimientoDTO;
import net.tecgurus.jd0.model.Banco;
import net.tecgurus.jd0.model.Cliente;
import net.tecgurus.jd0.model.Cuenta;
import net.tecgurus.jd0.model.Movimiento;
import net.tecgurus.jd0.model.TipoMovimiento;
import net.tecgurus.jd0.services.BancoService;
import net.tecgurus.jd0.services.CatalogoService;
import net.tecgurus.jd0.services.ClienteService;
import net.tecgurus.jd0.services.CuentaService;
import net.tecgurus.jd0.services.MovimientoService;
import net.tecgurus.jd0.util.Constantes;
import net.tecgurus.jd0.util.Utilerias;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.record.aggregates.RecordAggregate;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JTextArea;

public class PanelMovimiento extends JPanel implements ActionListener{
	
	private JComboBox<Banco> cbBanco;
	private JComboBox<Cliente> cbCliente;
	private JComboBox<CuentaDTO> cbCuenta;
	private JComboBox<TipoMovimiento> cbTipoMov;
	private JTextField tfMonto;
	private JTextArea tfMensaje;
	private JButton btnAplicar;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lblSaldo;

	public PanelMovimiento() {
		setLayout(null);
		setSize(1250, 581);
		
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(10, 11, 392, 570);
		add(panelFormulario);
		panelFormulario.setLayout(null);
		
		JLabel lblBanco = new JLabel("BANCO");
		lblBanco.setBounds(10, 38, 131, 14);
		lblBanco.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblBanco);
		
		cbBanco = new JComboBox<Banco>();
		cbBanco.setBounds(151, 35, 214, 20);
		cbBanco.addActionListener(this);
		panelFormulario.add(cbBanco);
		
		JLabel lblCliente = new JLabel("CLIENTE");
		lblCliente.setBounds(10, 93, 131, 14);
		lblCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblCliente);
		
		cbCliente = new JComboBox<Cliente>();
		cbCliente.setBounds(151, 90, 214, 20);
		cbCliente.addActionListener(this);
		panelFormulario.add(cbCliente);
		
		JLabel lblCuenta = new JLabel("CUENTA");
		lblCuenta.setBounds(10, 159, 131, 14);
		lblCuenta.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblCuenta);
		
		cbCuenta = new JComboBox<CuentaDTO>();
		cbCuenta.setBounds(151, 153, 214, 20);
		cbCuenta.addActionListener(this);
		panelFormulario.add(cbCuenta);
		
		JLabel lblTipoDeMovimiento = new JLabel("TIPO DE MOVIMIENTO");
		lblTipoDeMovimiento.setBounds(10, 214, 131, 14);
		lblTipoDeMovimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblTipoDeMovimiento);
		
		cbTipoMov = new JComboBox<TipoMovimiento>();
		cbTipoMov.setBounds(151, 211, 214, 20);
		cbTipoMov.addActionListener(this);
		panelFormulario.add(cbTipoMov);
				
		JLabel lblMonto = new JLabel("MONTO");
		lblMonto.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonto.setBounds(10, 287, 372, 14);
		panelFormulario.add(lblMonto);
		
		tfMonto = new JTextField();
		tfMonto.setBounds(132, 313, 128, 20);
		panelFormulario.add(tfMonto);
		
		btnAplicar = new JButton("APLICAR");
		btnAplicar.setBounds(147, 473, 89, 34);
		btnAplicar.addActionListener(this);
		panelFormulario.add(btnAplicar);
		
		JLabel lblMensaje = new JLabel("MENSAJE");
		lblMensaje.setBounds(10, 367, 372, 14);
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		panelFormulario.add(lblMensaje);
		
		tfMensaje = new JTextArea();
		tfMensaje.setLineWrap(true);
		tfMensaje.setWrapStyleWord(true);
		tfMensaje.setBounds(89, 391, 207, 60);
		tfMensaje.addKeyListener(new KeyAdapter() {
			 @Override
		     public void keyTyped(KeyEvent e) {
				 if (tfMensaje.getText().length() >= 60 ) // limit to 3 characters
					 e.consume();   
			 }
		});
		panelFormulario.add(tfMensaje);
		
		JPanel panelTabla = new JPanel();
		panelTabla.setBounds(412, 11, 830, 570);
		add(panelTabla);
		panelTabla.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 830, 352);
		panelTabla.add(scrollPane);
		
		table = new JTable();
		Object[] nombreColumnas = {"ID", "TIPO", "MENSAJE", "FECHA", "SALDO ANTERIOR", "MONTO", "SALDO ACTUAL"};
		table.setModel(new DefaultTableModel(new Object[][]{}, nombreColumnas));
		tableModel = (DefaultTableModel) table.getModel();
		table.setEnabled(false);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setMaxWidth(80);
		table.getColumnModel().getColumn(3).setMinWidth(100);
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i = 0 ; i < nombreColumnas.length; i++){
			table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
		
		scrollPane.setViewportView(table);
		
		JLabel lblTitSaldo = new JLabel("SALDO:");
		lblTitSaldo.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTitSaldo.setBounds(214, 412, 116, 61);
		panelTabla.add(lblTitSaldo);
		
		lblSaldo = new JLabel();
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblSaldo.setBounds(340, 412, 401, 61);
		panelTabla.add(lblSaldo);
		
		cargarContenidoInicial();
		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
				
		if(ev.getSource() == cbBanco){
			cargarClientes();
		}else if(ev.getSource() == cbCliente){
			cargarCuentas();
		}else if(ev.getSource() == cbCuenta){
			CuentaDTO cuentaDTO = (CuentaDTO)cbCuenta.getSelectedItem();
			if(cuentaDTO != null){
				lblSaldo.setText(Utilerias.convertirAmoneda(cuentaDTO.getSaldo()));
				obtenerMovimientos(cuentaDTO.getNumero());
			}
		}else if(ev.getSource() == cbTipoMov){
			deshabilitarComponentes();
			CuentaDTO cuentaDTO = (CuentaDTO)cbCuenta.getSelectedItem();
			if(cuentaDTO != null){
				obtenerMovimientos(cuentaDTO.getNumero());
			}
		}else if(ev.getSource() == btnAplicar){
			insertarMovimiento();
		}
	}
	
	private void obtenerMovimientos(long numeroCuenta){
		tableModel.setRowCount(0);	
		TipoMovimiento tipoMovimiento = (TipoMovimiento)cbTipoMov.getSelectedItem();
		if(tipoMovimiento != null){
			MovimientoService movimientoService = new MovimientoService();
			List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosPorIdCuentaYtipo(numeroCuenta, tipoMovimiento.getId());
			for (MovimientoDTO mov : movimientos) {
				tableModel.addRow(new Object[]{mov.getId(), mov.getTipoMovimiento().getTipo(), mov.getMensaje(), mov.getFecha(), 
						Utilerias.convertirAmoneda(mov.getSaldoAnterior()), 
			mov.getTipoMovimiento().getId() == Constantes.TipoMovimiento.RETIRO?"-"+Utilerias.convertirAmoneda(mov.getMonto()):Utilerias.convertirAmoneda(mov.getMonto()),	
						Utilerias.convertirAmoneda(mov.getSaldoActual()) });
			}
		}
		tableModel.fireTableDataChanged();
	}
	
	private void cargarBancos(){
		cbBanco.removeAllItems();
		List<Banco> bancos = null;
		BancoService bancoService = new BancoService();
		bancos = bancoService.obtenerBancos();
		for (Banco banco : bancos) {
			cbBanco.addItem(banco);
		}
		
		if(bancos == null || bancos.isEmpty()){
			limpiarCampos();
		}
	}
	
	private void cargarClientes(){
		cbCliente.removeAllItems();
		Banco banco = (Banco)cbBanco.getSelectedItem();
		List<Cliente> clientes = null;
		if(banco != null){
			ClienteService clienteService = new ClienteService();
			clientes = clienteService.obtenerClientesPorIdBanco(banco.getId());
			for (Cliente cliente : clientes) {
				cbCliente.addItem(cliente);
			}
		}
		if(clientes == null || clientes.isEmpty()){
			limpiarCampos();
		}
		
	}
	
	private void cargarCuentas(){
		cbCuenta.removeAllItems();
		Cliente cliente = (Cliente)cbCliente.getSelectedItem();
		List<CuentaDTO> cuentas = null;
		if(cliente != null){
			CuentaService cuentaService = new CuentaService();
			cuentas = cuentaService.obtenerCuentasPorIdCliente(cliente.getId());
			for (CuentaDTO cuentaDTO : cuentas) {
				cbCuenta.addItem(cuentaDTO);
			}
		}
		if(cuentas == null || cuentas.isEmpty()){
			limpiarCampos();
		}
	}
	
	private void cargarTiposMovimiento(){
		cbTipoMov.removeAllItems();
		CatalogoService catalogoService = new CatalogoService();
		List<TipoMovimiento> tiposMov = catalogoService.obtenerTiposMovimiento();
		cbTipoMov.addItem(new TipoMovimiento(0, "TODOS"));
		for (TipoMovimiento tipoMovimiento : tiposMov) {
			cbTipoMov.addItem(tipoMovimiento);
		}
	}
	
	private void deshabilitarComponentes(){
		TipoMovimiento tipoMov = (TipoMovimiento)cbTipoMov.getSelectedItem();
		if(tipoMov != null){
			if(tipoMov.getId() == Constantes.TipoMovimiento.TODOS || tipoMov.getId() == Constantes.TipoMovimiento.CONSULTA){
				tfMonto.setText("");
				tfMonto.setEnabled(false);
			}else{
				tfMonto.setEnabled(true);
			}
		}
	}
	
	
	private void insertarMovimiento(){
		TipoMovimiento tipoMovimiento = (TipoMovimiento)cbTipoMov.getSelectedItem();
		CuentaDTO cuentaDTO = (CuentaDTO)cbCuenta.getSelectedItem();
		if(cuentaDTO == null || tipoMovimiento == null){
			JOptionPane.showMessageDialog(this, "Debe seleccionar una cuenta y un tipo de movimiento", "Atención", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(tipoMovimiento.getId() == Constantes.TipoMovimiento.TODOS){
			JOptionPane.showMessageDialog(this, "Tipo de Movimiento inválido", "Atención", JOptionPane.WARNING_MESSAGE);
			return;
		}
		double monto = 0;
		double saldoActual = cuentaDTO.getSaldo();
		if(tipoMovimiento.getId() == Constantes.TipoMovimiento.ABONO || tipoMovimiento.getId() == Constantes.TipoMovimiento.RETIRO){
			if(tfMonto.getText() == null || tfMonto.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Un monto mayor a cero es requerido", "Atención", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			try{
				monto = Double.parseDouble(tfMonto.getText());
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, "Debe ingresar un monto válido", "Atención", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(tipoMovimiento.getId() == Constantes.TipoMovimiento.ABONO){
				saldoActual += monto;
			}else{
				if( (saldoActual-monto) < 0){
					JOptionPane.showMessageDialog(this, "Saldo Insuficiente para realizar la operación", "Atención", JOptionPane.WARNING_MESSAGE);
					return;
				}
				saldoActual -= monto;
			}
		}
		
		Movimiento mov = new Movimiento();
		mov.setFecha(new Date());
		mov.setMensaje(tfMensaje.getText().trim());
		mov.setMonto(monto);
		mov.setSaldoAnterior(cuentaDTO.getSaldo());
		mov.setSaldoActual(saldoActual);
		mov.setIdTipoMovimiento(tipoMovimiento.getId());
		mov.setIdCuenta(cuentaDTO.getNumero());
		
		MovimientoService movimientoService = new MovimientoService();
		boolean exito = movimientoService.insertarMovimiento(mov);
		if(exito){
			cuentaDTO.setSaldo(saldoActual);
			lblSaldo.setText(Utilerias.convertirAmoneda(cuentaDTO.getSaldo()));
			obtenerMovimientos(cuentaDTO.getNumero());
			tfMonto.setText("");
			tfMensaje.setText("");
			JOptionPane.showMessageDialog(this, "Movimiento aplicado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el movimiento", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	public void cargarContenidoInicial() {
		limpiarCampos();
		cargarTiposMovimiento();
		cargarBancos();
	}
	
	private void limpiarTabla(){
		tableModel.setRowCount(0);
		tableModel.fireTableDataChanged();
	}
	
	private void limpiarCampos(){
		tfMonto.setText("");
		tfMensaje.setText("");
		lblSaldo.setText("$0.00");
		limpiarTabla();
	}
	
}
