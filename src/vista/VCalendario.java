package vista;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.Calendario;
import modelo.Dia;
import modelo.ParametrosModelo;
import util.Vistas;

public class VCalendario extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5612581520418566166L;
	private Calendario _c;
	
	public VCalendario(Calendario c){
		super();
		_c=c;
		setLayout(new GridBagLayout());
		setBackground(ParametrosModelo.FONDO_VCALENDARIO);
		inicializarComponentes();
	}
	
	public Calendario obtenerCalendario(){
		return _c;
	}
	
	private void inicializarComponentes(){
		int i=0;
		for(Dia d:_c){
			VDia vd=new VDia(d);
			vd.setBorder(javax.swing.BorderFactory.createMatteBorder(
                    1, 5, 1, 1, Color.DARK_GRAY));
			this.add(vd,Vistas.obtenerConstraints(0, i, 1, 1,GridBagConstraints.WEST,GridBagConstraints.BOTH));
			i++;
		}
	}
	
	public void actualizarComponente(){
		this.removeAll();
		this.inicializarComponentes();
	}
	

	public static void main(String[] args) throws Exception {
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// jf.setSize(300, 200);
		jf.setLocationRelativeTo(null);

		Dia d = new Dia("20160101");
		d.obtenerListaHoras().anadirHoras("NORMAL", 8.0);
		d.obtenerListaHoras().anadirHoras("EXTRA", 1.5);
		Dia d1 = new Dia("20160102");
		d1.obtenerListaHoras().anadirHoras("NORMAL", 8.0);
		d1.obtenerListaHoras().anadirHoras("GUARDIA", 1.0);
		
		Calendario c=new Calendario();
		c.agregarDia(d);
		c.agregarDia(d1);

		VCalendario vc = new VCalendario(c);
		jf.add(vc);
		jf.setVisible(true);
		jf.pack();
	}
}
