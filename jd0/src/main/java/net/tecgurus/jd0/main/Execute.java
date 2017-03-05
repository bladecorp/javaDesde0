package net.tecgurus.jd0.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.tecgurus.jd0.views.Login;
import net.tecgurus.jd0.views.MainFrame;


public class Execute {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{ 
					// **** NORMALES ****//
					//javax.swing.plaf.nimbus.NimbusLookAndFeel
					
					//******* JTATTOO ****///
					//com.jtattoo.plaf.aero.AeroLookAndFeel
					//smart.SmartLookAndFeel
					//noire.NoireLookAndFeel
					//aero.AeroLookAndFeel
					//aluminium.AluminiumLookAndFeel
					//barnstein.BarnsteinLookAndFeel
					//fast.FastLookAndFeel
					//graphite.GraphiteLookAndFeel
					//hifi.HiFiLookAndFeel
					//luna.LunaLookAndFeel
					//mcwin.McWinLookAndFeel
					//mint.MintLookAndFeel
					//noire.NoireLookAndFeel
					//smart.SmartLookAndFeel
					//texture.TextureLookAndFeel
					UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
					new Login();
		//		new MainFrame();
				}catch(Exception e){
					System.out.println("ERROR AL CARGAR EL TEMA");
				}
			}
		});

	}

}
