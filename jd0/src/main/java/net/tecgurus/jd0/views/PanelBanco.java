package net.tecgurus.jd0.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import net.tecgurus.jd0.dto.GraficaCuentasDTO;
import net.tecgurus.jd0.model.Banco;
import net.tecgurus.jd0.services.BancoService;
import net.tecgurus.jd0.util.Utilerias;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;

public class PanelBanco extends JPanel implements ActionListener{
	private JTextField tfBanco;
	private DefaultTableModel tableModel;
	private JTable table;
	private JButton btnGuardar;
	private JPopupMenu popup;
	private JMenuItem itemEditar, itemBorrar;
	private JLabel lblImagen;
	private JPanel panelImagen;
	
	public PanelBanco(){
		setLayout(null);
		setSize(1250, 581);
		
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(10, 11, 501, 135);
		add(panelFormulario);
		panelFormulario.setBorder(BorderFactory.createRaisedBevelBorder());
		panelFormulario.setLayout(null);
		
		tfBanco = new JTextField();
		tfBanco.setBounds(167, 53, 164, 20);
		panelFormulario.add(tfBanco);
		tfBanco.setColumns(10);
		
		JLabel lblNombreDelBanco = new JLabel("Nombre del Banco:");
		lblNombreDelBanco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreDelBanco.setBounds(43, 54, 127, 14);
		panelFormulario.add(lblNombreDelBanco);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(341, 52, 89, 23);
		btnGuardar.addActionListener(this);
		panelFormulario.add(btnGuardar);
		
		JPanel panelTabla = new JPanel();
		panelTabla.setBounds(521, 11, 719, 186);
		add(panelTabla);
		panelTabla.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 718, 186);
		panelTabla.add(scrollPane);
		
		table = new JTable();
		String[] nombreColumnas = {"ID", "NOMBRE", "IMAGEN"};
		table.setModel(new DefaultTableModel(new Object[][] {},nombreColumnas));
	//	table.getColumnModel().getColumn(0).setPreferredWidth(100);
	//	table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(100); 
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
		tableModel = (DefaultTableModel)table.getModel(); 
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event){
				int row = table.rowAtPoint(event.getPoint()); //OBTENEMOS PUNTO DE SELECCION
				table.getSelectionModel().setSelectionInterval(row, row); // SELECCIONAMOS FILA
				String imagen = (String)table.getValueAt(row, 2);
				lblImagen.setIcon(Utilerias.obtenerImagen(imagen));
				
				if(event.getButton() == MouseEvent.BUTTON3){
					popup.show(table, event.getX(), event.getY());
				}
			}
		});   
		
		popup = new JPopupMenu();
		itemEditar = new JMenuItem("Editar");
		itemBorrar = new JMenuItem("Borrar");
		itemEditar.addActionListener(this);
		itemBorrar.addActionListener(this);
		
		popup.add(itemEditar);
		popup.addSeparator();
		popup.add(itemBorrar);
		
		panelImagen = new JPanel();
		panelImagen.setBounds(54, 237, 1186, 333);
		add(panelImagen);
		panelImagen.setLayout(null);
		
		lblImagen = new JLabel("");
		lblImagen.setBounds(10, 11, 1027, 311);
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setVerticalAlignment(SwingConstants.CENTER);
		panelImagen.add(lblImagen);
		
		cargarContenidoInicial();
	}
	
	private void obtenerBancos(){
		tableModel.setRowCount(0); //LIMPIAR TABLA
		BancoService bancoService = new BancoService();
		List<Banco> bancos = bancoService.obtenerBancos();
		for (Banco banco : bancos) {
			Object[] fila = {banco.getId(), banco.getNombre(), banco.getImagen()};
			tableModel.addRow(fila);
		}
		tableModel.fireTableDataChanged();
	}
	
	private void insertarBanco(){
		Banco banco = new Banco();
		banco.setNombre(tfBanco.getText().trim());
		BancoService bancoService = new BancoService();
		boolean exito = bancoService.insertarBanco(banco);
		if(exito){
			tfBanco.setText("");
			obtenerBancos();
			return;
		}
		JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el banco", "Error al Guardar", JOptionPane.OK_OPTION);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == btnGuardar){
			if(tfBanco.getText().trim().isEmpty()){
				JOptionPane.showMessageDialog(this,"El nombre del banco es requerido", "Error en formulario", JOptionPane.OK_OPTION);
				return;
			}
			insertarBanco();
		}else if(event.getSource() == itemEditar){
			int id = (int)table.getValueAt(table.getSelectedRow(), 0);
			String nombre = (String)table.getValueAt(table.getSelectedRow(), 1);
			editarBanco(id, nombre);
		}else if(event.getSource() == itemBorrar){
			int id = (int)table.getValueAt(table.getSelectedRow(), 0);
			eliminarBanco(id);
		}
		
	}
	
	private void editarBanco(int id, String nombre){
		Object respuesta = JOptionPane.showInputDialog(this, "Editar Nombre", "Escriba el nuevo nombre del banco", 
								JOptionPane.OK_CANCEL_OPTION, null, null, nombre);
		if(respuesta != null){ // SI ES NULL ES PORQUE EL USUARIO SELECCIONÓ CANCELAR O CERRÓ EL DIALOGO.
			String nuevoNombre = respuesta.toString().trim();
			if(nuevoNombre.isEmpty()){
				JOptionPane.showMessageDialog(this, "El nombre del banco no puede estar vacío", "Atención",
						JOptionPane.OK_OPTION);
				return;
			}
			Banco banco = new Banco();
			banco.setId(id);
			banco.setNombre(nuevoNombre);
			BancoService bancoService = new BancoService();
			boolean exito = bancoService.actualizarBanco(banco);
			if(exito){
				obtenerBancos();
				return;
			}
			JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar el banco", "Error al Actualizar",
					JOptionPane.OK_OPTION);
		}
		
	}
	
	private void eliminarBanco(int idBanco){
		BancoService bancoService = new BancoService();
		bancoService.eliminarBancoPorId(idBanco);
		obtenerBancos();
	}
	
	public void cargarContenidoInicial(){ //METODO QUE SERÁ INVOCADO DESDE EL CARD PANEL
		obtenerBancos();
	}
	
	/*private void cargarGrafica(){
		Default pie = new DefaultPieDataset();
		
		for (GraficaCuentasDTO grafica : graficas) {
			pie.setValue(grafica.getNombre(), grafica.getCuentas());
		}  
		Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
		JFreeChart chart = ChartFactory.createPieChart3D("CUENTAS POR BANCO", pie);
		chart.setBackgroundPaint(trans);
		PiePlot3D pp3d = (PiePlot3D)chart.getPlot();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setLocation(0, 0);
		chartPanel.setVisible(true);
		chartPanel.setSize(new Dimension(883, 559));
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(trans);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
	            "{0}\nCUENTAS: {1}\nPCT DE MERCADO: ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
	        plot.setLabelGenerator(gen);
		panelGrafica.add(chartPanel);
	}  */
	
}
