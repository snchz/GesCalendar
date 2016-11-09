package vista;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import modelo.Calendario;
import modelo.Dia;
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
		inicializarComponentes();
		System.out.println("aaaa"+super.getParent());
	}
	
	private void inicializarComponentes(){
		this.setBackground(Color.WHITE);
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
}
