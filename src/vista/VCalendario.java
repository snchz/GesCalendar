package vista;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import modelo.Calendario;
import modelo.Dia;
import util.Vistas;

public class VCalendario extends JPanel{
	
	public VCalendario(Calendario c){
		super();
		setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		int i=0;
		for(Dia d:c){
			VDia vd=new VDia(d);
			vd.setBorder(javax.swing.BorderFactory.createMatteBorder(
                    1, 5, 1, 1, Color.DARK_GRAY));
			this.add(vd,Vistas.obtenerConstraints(0, i, 1, 1,GridBagConstraints.WEST,GridBagConstraints.BOTH));
			i++;
		}
		
	}
}
