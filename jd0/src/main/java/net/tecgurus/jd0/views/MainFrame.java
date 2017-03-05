package net.tecgurus.jd0.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.JLabel;

public class MainFrame extends JFrame implements MenuListener, ActionListener{
	
	private JMenu menuAdministrador, menuAdmin, menuBanco, menuCliente, menuCuenta, menuMovimiento;
	private JMenuItem itemAdmin, itemExit;
	private JPanel panelPrincipal;
	private JLabel lblTitulo;
	private PanelAdmin panelAdmin;
	private PanelBanco panelBanco;
	private PanelCliente panelCliente;
	private PanelCuentas panelCuentas;
	private PanelMovimiento panelMovimiento;
	
	public MainFrame(){
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(1270, 680);
		setTitle("SISTEMA BANCARIO");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setJMenuBar(createMenuBar());
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBounds(10, 5, 1234, 36);
		getContentPane().add(panelTitulo);
		panelTitulo.setLayout(null);
		panelTitulo.setBackground(Color.BLACK);
		
		lblTitulo = new JLabel("TITULO");
		lblTitulo.setBounds(0, 0, 1234, 36);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setVerticalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
	//	lblTitulo.setBorder(BorderFactory.createRaisedBevelBorder());
		lblTitulo.setForeground(Color.WHITE);
	//	lblTitulo.setBackground(Color.BLACK);
		panelTitulo.add(lblTitulo);
		
		panelPrincipal = new JPanel(new CardLayout());
		panelAdmin = new PanelAdmin();
		panelBanco = new PanelBanco();
		panelCliente = new PanelCliente();
		panelCuentas = new PanelCuentas();
		panelMovimiento = new PanelMovimiento();
		panelPrincipal.add(panelAdmin, "panelAdmin");
		panelPrincipal.add(panelBanco, "panelBanco");
		panelPrincipal.add(panelCliente, "panelCliente");
		panelPrincipal.add(panelCuentas, "panelCuenta");
		panelPrincipal.add(panelMovimiento, "panelMovimiento");
		panelPrincipal.setSize(new Dimension(1200, 500));
		panelPrincipal.setBounds(10, 40, 1350, 660);
		getContentPane().add(panelPrincipal);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event){
				panelCuentas.detenerTimer();
				dispose();
			//	System.gc();
			}
		});
	}
	
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		menuAdministrador = new JMenu("ADMINISTRADOR");
		menuAdmin = new JMenu("ADMIN");
		menuBanco = new JMenu("BANCO");
		menuCliente = new JMenu("CLIENTE");
		menuCuenta = new JMenu("CUENTA");
		menuMovimiento = new JMenu("MOVIMIENTO");
		itemAdmin = new JMenuItem("VER");
		itemExit = new JMenuItem("SALIR");
		
		JMenuItem itmOp1 = new JMenuItem("Opcion 1"); //BLOQUE DE RELLENO
		JMenuItem itmOp2 = new JMenuItem("Opcion 2");
		menuAdmin.add(itmOp1);
		menuAdmin.add(itmOp2);
		
		menuAdmin.add(itemAdmin);
		
		menuAdministrador.add(menuAdmin);
		menuAdministrador.addSeparator();
		menuAdministrador.add(itemExit);
		menuBar.add(menuAdministrador);
		menuBar.add(menuBanco);
		menuBar.add(menuCliente);
		menuBar.add(menuCuenta);
		menuBar.add(menuMovimiento);
		agregarListener();
		return menuBar;
	}
	
	private void agregarListener(){
		menuBanco.addMenuListener(this);
		menuCliente.addMenuListener(this);
		menuCuenta.addMenuListener(this);
		menuMovimiento.addMenuListener(this);
		itemAdmin.addActionListener(this);
		itemExit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == itemAdmin){
			CardLayout card = (CardLayout)panelPrincipal.getLayout();
			panelAdmin.cargarContenidoInicial();
			card.show(panelPrincipal, "panelAdmin");
			lblTitulo.setText("ADMINISTRADOR");
		}else if(event.getSource() == itemExit){
		//	System.exit(0);
			WindowListener[] windowListeners = getWindowListeners();
			for (WindowListener listener : windowListeners) {
				listener.windowClosing(new WindowEvent(MainFrame.this, 0));
			}
		}
	}
	
	@Override
	public void menuSelected(MenuEvent event) {
		CardLayout card = (CardLayout)panelPrincipal.getLayout();
		if(event.getSource() == menuBanco){
			panelBanco.cargarContenidoInicial();
			card.show(panelPrincipal, "panelBanco");
			lblTitulo.setText("BANCO");
		}else if(event.getSource() == menuCliente){
			panelCliente.cargarContenidoInicial();
			lblTitulo.setText("CLIENTE");
			card.show(panelPrincipal, "panelCliente");
		}else if(event.getSource() == menuCuenta){
			panelCuentas.cargarContenidoInicial();
			lblTitulo.setText("CUENTAS");
			card.show(panelPrincipal, "panelCuenta");
		}else if(event.getSource() == menuMovimiento){
			panelMovimiento.cargarContenidoInicial();
			lblTitulo.setText("MOVIMIENTOS");
			card.show(panelPrincipal, "panelMovimiento");
		}
	} 

	@Override
	public void menuCanceled(MenuEvent e) {
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		
	}

}
