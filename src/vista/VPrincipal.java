package vista;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.joda.time.DateTime;

import modelo.Calendario;
import modelo.Dia;
import modelo.ParametrosModelo;

public class VPrincipal extends JFrame {
	private static final long serialVersionUID = -851955688502061398L;

	public VPrincipal(VCalendario vc) {
		super();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setBackground(ParametrosModelo.FONDO_VPRINCIPAL);

		iniciarComponentes(vc);

		pack();
		setVisible(true);
	}

	private void iniciarComponentes(VCalendario vc) {
		JScrollPane jsp = new JScrollPane(vc, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//vc.validate();
		vc.setVisible(true);
		//Dimension d=new Dimension(vc.getSize());
		//d.setSize(d.width+100, d.height);
		//jsp.setPreferredSize(d);
		add(jsp, BorderLayout.WEST);
		// Vistas.obtenerConstraints(0, 0, 1,
		// 4,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH));
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Closed");
				vc.obtenerCalendario().exportarCalendario(new File("exportado.dat"));
				e.getWindow().dispose();
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

				try {
					DateTime dt = DateTime.now();
					Dia actual = new Dia(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

					Calendario c = new Calendario();
					c.importarCalendario(new File("exportado.dat"));
					c.generarCalendario(new Dia("20160101"), actual);
					// c.exportarCalendario(new File("exportado.dat"));

					VCalendario vc = new VCalendario(c);
					VPrincipal vp = new VPrincipal(vc);
					SwingUtilities.updateComponentTreeUI(vp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}//perf, correa

}
