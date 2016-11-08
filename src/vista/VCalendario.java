package vista;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.Calendario;
import modelo.Dia;

public class VCalendario extends JPanel{
	
	public VCalendario(Calendario c){
		super();
		setLayout(new GridLayout(3,1));
		for(Dia d:c){
			VDia vd=new VDia(d);
			this.add(vd);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		JFrame jf=new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(300, 200);
		jf.setLocationRelativeTo(null);

		Dia d1=new Dia("20160102");
		Dia d=new Dia("20160101");
		d.agregarHoras("NORMAL", 8.0);
		d.agregarHoras("EXTRA", 1.5);
		
		Calendario c=new Calendario();
		c.agregarDia(d);
		c.agregarDia(d1);
		
		VCalendario vc=new VCalendario(c);
		jf.add(vc);
		jf.setVisible(true);
	}
}
