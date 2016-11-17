package vista;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Dia;
import modelo.ParametrosModelo;

public class VDia extends JPanel {
	private static final long serialVersionUID = -3461158432879876002L;
	private Dia _d;
	
	public VDia(Dia d) {
		super();
		_d=d;
		setLayout(new GridBagLayout());
		this.setBackground(ParametrosModelo.FONDO_VDIA);
		// ---------------------
		// |Fecha| Horas	1	-+|
		// |	 | Horas	2	-+|
		// |	 | Horas	3	-+|
		// |	 | Horas	4	-+|
		// ---------------------
		this.inicializarComponentes();
	}
	
	public Dia obtenerDia(){
		return _d;
	}
	
	public void actualizarVista(){
		this.removeAll();
		inicializarComponentes();
		//((VCalendario)this.getParent()).actualizarVista();
		this.paintAll(this.getGraphics()); 
	}
	
	private void inicializarComponentes(){
		//Componente de Fecha
		JTextField jtf_fecha = ParametrosVista.obtenerJTextField(_d.obtenerFecha().obtenerFechaConFormato("yyyy/MM/dd"), false, new Dimension(80, 20), false);
		this.add(jtf_fecha, ParametrosVista.obtenerConstraints(0, 0, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));

		VListaHoras lh=new VListaHoras(this._d.obtenerListaHoras());
		
		this.add(lh,ParametrosVista.obtenerConstraints(1, 0, 1, 1, GridBagConstraints.EAST,GridBagConstraints.NONE));
	}
	

	public static void main(String[] args) throws Exception {
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// jf.setSize(300, 200);
		jf.setLocationRelativeTo(null);

		Dia d = new Dia("20160101");
		d.obtenerListaHoras().anadirHoras("NORMAL", 8.0);
		d.obtenerListaHoras().anadirHoras("EXTRA", 1.5);

		VDia vd = new VDia(d);
		jf.add(vd);
		jf.setVisible(true);
		jf.pack();
	}

}