package net.tecgurus.jd0.views;

import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PnlTest extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlTest() {
		setLayout(null);
		
		JLabel lblCaractersticas = new JLabel("CARACTER\u00CDSTICAS");
		lblCaractersticas.setFont(new Font("Stencil", Font.ITALIC, 16));
		lblCaractersticas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCaractersticas.setBounds(10, 11, 430, 14);
		add(lblCaractersticas);
		
		JLabel lblComision = new JLabel("Aplica");
		lblComision.setBounds(10, 55, 430, 14);
		add(lblComision);
		
		JLabel lblBanca = new JLabel("No Aplica");
		lblBanca.setBounds(10, 93, 430, 14);
		add(lblBanca);
		
		JLabel lblEfectivo = new JLabel("No Aplica");
		lblEfectivo.setBounds(10, 139, 430, 14);
		add(lblEfectivo);
		
		JLabel lblVip = new JLabel("New label");
		lblVip.setBounds(10, 188, 430, 14);
		add(lblVip);

	}
}
