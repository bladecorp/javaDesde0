package net.tecgurus.jd0.views;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import net.tecgurus.jd0.dto.ClienteDTO;
import net.tecgurus.jd0.dto.CuentaDTO;
import net.tecgurus.jd0.dto.GraficaCuentasDTO;
import net.tecgurus.jd0.model.Banco;
import net.tecgurus.jd0.model.CaracteristicasCuenta;
import net.tecgurus.jd0.model.Cliente;
import net.tecgurus.jd0.model.Cuenta;
import net.tecgurus.jd0.model.TipoCuenta;
import net.tecgurus.jd0.services.BancoService;
import net.tecgurus.jd0.services.CaracteristicasCuentaService;
import net.tecgurus.jd0.services.CatalogoService;
import net.tecgurus.jd0.services.ClienteService;
import net.tecgurus.jd0.services.CuentaService;
import net.tecgurus.jd0.util.Constantes;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

public class PanelCuentas extends JPanel implements ActionListener{
	private JComboBox<Banco> cbBanco;
	private JComboBox<Cliente> cbCliente;
	private JComboBox<TipoCuenta> cbTipoCuenta;
	private JCheckBox chbxSinComision, chbxBancaLinea, chbxDispEfectivo, chbxVip;
	private JToggleButton tglRotacion;
	private JButton btnGuardar;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPopupMenu popup;
	private JMenuItem itemDetalles;
	private JMenuItem itemBorrar;
	private JPanel panelGrafica;
//	private PiePlot plot;
	static int giro = 0;
	private Timer timer;
	

	public PanelCuentas(){
		setLayout(null);
		setSize(1250, 580);
		
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(10, 11, 392, 246);
		add(panelFormulario);
		panelFormulario.setLayout(null);
		
		cbBanco = new JComboBox<Banco>();
		cbBanco.setBounds(162, 29, 182, 20);
		cbBanco.addActionListener(this);
		panelFormulario.add(cbBanco);
		
		JLabel lblBanco = new JLabel("BANCO");
		lblBanco.setBounds(10, 29, 142, 20);
		lblBanco.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblBanco);
		
		JLabel lblCliente = new JLabel("CLIENTE");
		lblCliente.setBounds(10, 64, 142, 14);
		lblCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblCliente);
		
		JLabel lblTipoDeCuenta = new JLabel("TIPO DE CUENTA");
		lblTipoDeCuenta.setBounds(10, 92, 142, 14);
		lblTipoDeCuenta.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFormulario.add(lblTipoDeCuenta);
		
		JLabel lblCaracteristicas = new JLabel("CARACTER\u00CDSTICAS");
		lblCaracteristicas.setBounds(10, 126, 372, 14);
		lblCaracteristicas.setHorizontalAlignment(SwingConstants.CENTER);
		panelFormulario.add(lblCaracteristicas);
		
		cbCliente = new JComboBox<>();
		cbCliente.setBounds(162, 60, 182, 20);
		cbCliente.addActionListener(this);
		panelFormulario.add(cbCliente);
		
		cbTipoCuenta = new JComboBox<>();
		cbTipoCuenta.setBounds(162, 89, 182, 20);
		cbTipoCuenta.addActionListener(this);
		panelFormulario.add(cbTipoCuenta);
		
		chbxSinComision = new JCheckBox("Sin Comisi\u00F3n");
		chbxSinComision.setBounds(72, 147, 123, 23);
		panelFormulario.add(chbxSinComision);
		
		chbxBancaLinea = new JCheckBox("Banca En L\u00EDnea");
		chbxBancaLinea.setBounds(197, 147, 139, 23);
		panelFormulario.add(chbxBancaLinea);
		
		chbxDispEfectivo = new JCheckBox("Disposici\u00F3n En Efectivo");
		chbxDispEfectivo.setBounds(197, 173, 185, 23);
		panelFormulario.add(chbxDispEfectivo);
		
		chbxVip = new JCheckBox("Beneficios VIP");
		chbxVip.setBounds(72, 173, 123, 23);
		panelFormulario.add(chbxVip);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setBounds(135, 212, 109, 23);
		btnGuardar.addActionListener(this);
		panelFormulario.add(btnGuardar);
		
		tglRotacion = new JToggleButton("Rotar");
		tglRotacion.setBounds(21, 212, 82, 23);
		tglRotacion.addActionListener(this);
		tglRotacion.setSelected(true);
		tglRotacion.setText("Detener");
		panelFormulario.add(tglRotacion);
		
		panelGrafica = new JPanel();
		panelGrafica.setBounds(427, 11, 813, 570);
		add(panelGrafica);
		panelGrafica.setLayout(null);
		
		JPanel panelTabla = new JPanel();
		panelTabla.setBounds(10, 268, 392, 313);
		add(panelTabla);
		panelTabla.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 372, 291);
		panelTabla.add(scrollPane);
		
		table = new JTable();
		Object[] nombreColumnas = {"NUMERO", "TIPO DE CUENTA", "SALDO"};
		table.setModel(new DefaultTableModel(new Object[][]{}, nombreColumnas));
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		tableModel = (DefaultTableModel) table.getModel();
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event){
				int row = table.rowAtPoint(event.getPoint()); //OBTENEMOS PUNTO DE SELECCION
				table.getSelectionModel().setSelectionInterval(row, row); // SELECCIONAMOS FILA
				
				if(event.getButton() == MouseEvent.BUTTON3){
					popup.show(table, event.getX(), event.getY());
				}
			}
		});
		
		itemDetalles = new JMenuItem("Detalles");
		itemDetalles.addActionListener(this);
		itemBorrar = new JMenuItem("Borrar");
		itemBorrar.addActionListener(this);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 268, 407, 2);
		add(separator);
		
		popup = new JPopupMenu();
		popup.add(itemDetalles);
		popup.addSeparator();
		popup.add(itemBorrar);
		
		cargarGrafica();//se llama a este metodo por unica vez para iniciar la grafica, desppués se invocará siempre a refrescar grafica
	}
	
	private void obtenerBancos(){
		cbBanco.removeAllItems();
		BancoService bancoService = new BancoService();
		List<Banco> bancos = bancoService.obtenerBancos();
		for (Banco banco : bancos) {
			cbBanco.addItem(banco);
		}
	}
	
	private void obtenerClientesPorIdBanco(){
		if(cbBanco.getSelectedItem() == null){
			return;
		}
		cbCliente.removeAllItems();
		int idBanco = ((Banco)cbBanco.getSelectedItem()).getId();
		ClienteService clienteService = new ClienteService();
		List<Cliente> clientes = clienteService.obtenerClientesPorIdBanco(idBanco);
		for (Cliente cliente : clientes) {
			cbCliente.addItem(cliente);
		}
	}
	
	private void obtenerTiposDeCuenta(){
		cbTipoCuenta.removeAllItems();
		CatalogoService catalogoService = new CatalogoService();
		List<TipoCuenta> tiposCuenta = catalogoService.obtenerTiposCuenta();
		for (TipoCuenta tipoCuenta : tiposCuenta) {
			cbTipoCuenta.addItem(tipoCuenta);
		}
	}
	
	private void recargarTabla(){
		tableModel.setRowCount(0);
		if(cbCliente.getSelectedItem() == null){
			return;
		}
		int idCliente = ((Cliente)cbCliente.getSelectedItem()).getId();
		CuentaService cuentaService = new CuentaService();
		List<CuentaDTO> clientes = cuentaService.obtenerCuentasPorIdCliente(idCliente);
		for (CuentaDTO cuentaDTO : clientes) {
			Object[] fila = {cuentaDTO.getNumero(), cuentaDTO.getTipoCuenta().getTipo(), cuentaDTO.getSaldo()};
			tableModel.addRow(fila);
		}
		tableModel.fireTableDataChanged();
	}
	
	private void insertarCuenta(){
		if(!validarFormulario()){
			return;
		}
		
		int idCliente = ((Cliente)cbCliente.getSelectedItem()).getId();
		int tipoCuenta = ((TipoCuenta)cbTipoCuenta.getSelectedItem()).getId();
		Cuenta cuenta = new Cuenta();
		cuenta.setNumero(System.currentTimeMillis());
		cuenta.setIdCliente(idCliente);
		cuenta.setIdTipoCuenta(tipoCuenta);
		
		List<Integer> caracteristicas = new ArrayList<>();
		if(chbxSinComision.isSelected()){
			caracteristicas.add(Constantes.Caracteristicas.SIN_COMISION);
		}
		if(chbxBancaLinea.isSelected()){
			caracteristicas.add(Constantes.Caracteristicas.BANCA_LINEA);
		}
		if(chbxDispEfectivo.isSelected()){
			caracteristicas.add(Constantes.Caracteristicas.DISPOSICION_EFECTIVO);
		}
		if(chbxVip.isSelected()){
			caracteristicas.add(Constantes.Caracteristicas.BENEFICIOS_VIP);
		}
		
		CuentaService cuentaService = new CuentaService();
		boolean exito = cuentaService.insertarCuenta(cuenta, caracteristicas);
		if(exito){
			recargarTabla();
			refrescarGrafica();
		}else{
			JOptionPane.showMessageDialog(this,  "Ocurrió un error al guardar la cuenta", "Error", JOptionPane.OK_OPTION);
		}
		
	}
	
	private boolean validarFormulario(){
		if(cbBanco.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this,  "Seleccione un banco", "Atención", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(cbCliente.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this,  "Seleccione un cliente", "Atención", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(cbTipoCuenta.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this,  "Seleccione un tipo de cuenta", "Atención", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getSource() == cbBanco){
			cbCliente.removeAllItems();
			obtenerClientesPorIdBanco();
		}else if(ev.getSource() == cbCliente){
			recargarTabla();
		}else if(ev.getSource() == tglRotacion){
			if(tglRotacion.isSelected()){
				tglRotacion.setText("Detener");
				if(!timer.isRunning()){
					timer.start();
				}
			}else{
				tglRotacion.setText("Rotar");
				timer.stop();
			}
		}else if(ev.getSource() == btnGuardar){
			insertarCuenta();
		}else if(ev.getSource() == itemDetalles){
			int fila = table.getSelectedRow();
			long idCuenta = (long)table.getValueAt(fila, 0);
			crearDialogoDetalles(idCuenta);
		}else if(ev.getSource() == itemBorrar){
			long idCuenta = (long)table.getValueAt(table.getSelectedRow(), 0);
			eliminarCuenta(idCuenta);
		}
	}
	
	private void crearDialogoDetalles(long idCuenta){
		
		JPanel pnlDetalles = new JPanel();
		pnlDetalles.setLayout(null);
		pnlDetalles.setPreferredSize(new Dimension(450, 250));
		
		JLabel lblCaractersticas = new JLabel("CARACTER\u00CDSTICAS");
		lblCaractersticas.setFont(new Font("Stencil", Font.ITALIC, 16));
		lblCaractersticas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCaractersticas.setBounds(10, 11, 430, 14);
		pnlDetalles.add(lblCaractersticas);
		
		CaracteristicasCuentaService carService = new CaracteristicasCuentaService();
		List<CaracteristicasCuenta> caracteristicas = carService.obtenerCaracteristicasPorIdCuenta(idCuenta);
		
		if(caracteristicas.isEmpty()){
			JLabel lblCarac = new JLabel("LA CUENTA NO TIENE CARACTERISTICAS ESPECIALES");
			lblCarac.setBounds(10, 55, 430, 14);
			lblCarac.setHorizontalAlignment(SwingConstants.CENTER);
			pnlDetalles.add(lblCarac);
		}else{
			int altura = 9;
			for (CaracteristicasCuenta carac : caracteristicas) {
				String mensaje = "";
				if(carac.getIdCaracteristica() == Constantes.Caracteristicas.SIN_COMISION){
					mensaje = "* SIN COMISIÓN ANUAL *";
				}else if(carac.getIdCaracteristica() == Constantes.Caracteristicas.BANCA_LINEA){
					mensaje = "* ACCESO A BANCA EN LÍNEA *";
				}else if(carac.getIdCaracteristica() == Constantes.Caracteristicas.DISPOSICION_EFECTIVO){
					mensaje = "* DISPOSICIONES EN EFECTIVO SIN LÍMITE *";
				}else if(carac.getIdCaracteristica() == Constantes.Caracteristicas.BENEFICIOS_VIP){
					mensaje = "* BENEFICIOS EXCLUSIVOS POR SER CLIENTE VIP *";
				}
				altura += 46;
				JLabel lblCarac = new JLabel(mensaje);
				lblCarac.setBounds(10, altura, 430, 14);
				lblCarac.setHorizontalAlignment(SwingConstants.CENTER);
				pnlDetalles.add(lblCarac);
			}
		}
		JOptionPane.showMessageDialog(null, pnlDetalles, "Detalles de la Cuenta",JOptionPane.PLAIN_MESSAGE);
		
	}

	private void eliminarCuenta(long idCuenta){
		CuentaService cuentaService = new CuentaService();
		cuentaService.eliminarCuentaPorId(idCuenta);
		recargarTabla();
		refrescarGrafica();
	}
	
	private void refrescarGrafica(){
		timer.stop(); // QUITAR ESTA LINEA SI SE QUITA EL TIMER
	//	panelGrafica.removeAll(); //SE PONE SOLO EN CASO DE QUE SE QUITE EL TIMER
	//	panelGrafica.revalidate(); //SE PONE SOLO EN CASO DE QUE SE QUITE EL TIMER
		cargarGrafica();
		panelGrafica.repaint();
	}
	
	private void cargarGrafica(){
		CuentaService cuentaService = new CuentaService();
		List<GraficaCuentasDTO> graficas = cuentaService.obtenerCuentasPorBanco();
		
		DefaultPieDataset pie = new DefaultPieDataset();
		for (GraficaCuentasDTO grafica : graficas) {
			pie.setValue(grafica.getNombre(), grafica.getCuentas());
		}
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		JFreeChart chart = ChartFactory.createPieChart3D("CUENTAS POR BANCO", pie, true, true, false);
		chart.setBackgroundPaint(trans);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setLocation(0, 0);
		chartPanel.setVisible(true);
		chartPanel.setSize(new Dimension(813, 559));
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(trans);
		plot.setForegroundAlpha( 0.60f );             
	    plot.setInteriorGap( 0.02 );  
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
	            "{0}\nCUENTAS: {1}\nMERCADO: ({2})", new DecimalFormat("0"), new DecimalFormat("0.00%"));
	        plot.setLabelGenerator(gen);  
		panelGrafica.add(chartPanel);
	
		timer=new Timer(60,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				// giro establece el angulo
				plot.setStartAngle(giro++);
			}
		});
		timer.start();
	//	iniciarTimerDeRotacion();
	}
	
	private void iniciarTimerDeRotacion(){
	//	if(timer == null){
			// Ejecuta cada 150ms 
/*			timer=new Timer(60,new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent ae){
					// giro establece el angulo
					plot.setStartAngle(giro++);
				}
			});
			timer.start();  */
	//	}
	}
	
	public void cargarContenidoInicial(){
		obtenerBancos();
		obtenerTiposDeCuenta();
		refrescarGrafica();
	}
	
	public void detenerTimer(){
		if(timer != null){
			timer.stop();
		}
	}
	
}
