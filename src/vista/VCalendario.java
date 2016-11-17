package vista;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.Calendario;
import modelo.Dia;
import modelo.ParametrosModelo;

public class VCalendario extends JPanel{
	private Dia _iniSemana;
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
		try {
			_iniSemana=new Dia(2016, 11, 7);
		} catch (Exception e) {
			e.printStackTrace();
		}
		inicializarComponentes();
	}
	
	public Calendario obtenerCalendario(){
		return _c;
	}
	
	private void inicializarComponentes(){
		int i=0;

		JButton jb_semanaAnterior=ParametrosVista.obtenerJButton("<-", new Dimension(41, 26));
		jb_semanaAnterior.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					_iniSemana=new Dia(_iniSemana.obtenerFecha().obtenerLunesAnterior());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				actualizarVista();
			}
        });
		JButton jb_semanaSiguiente=ParametrosVista.obtenerJButton("->", new Dimension(41, 26));
		jb_semanaSiguiente.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					_iniSemana=new Dia(_iniSemana.obtenerFecha().obtenerLunesSiguiente());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				actualizarVista();
			}
        });

		this.add(jb_semanaAnterior,ParametrosVista.obtenerConstraints(0, i, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		this.add(jb_semanaSiguiente,ParametrosVista.obtenerConstraints(1, i, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		i++;
		try {
			Dia finSemana = new Dia(_iniSemana.obtenerFecha().obtenerDomingoSiguiente());
			for(Dia d:_c){
				if (d.hashCode()>=_iniSemana.hashCode() && d.hashCode()<=finSemana.hashCode()){
					VDia vd=new VDia(d);
					vd.setBorder(javax.swing.BorderFactory.createMatteBorder(
		                    1, 5, 1, 1, Color.DARK_GRAY));
					this.add(vd,ParametrosVista.obtenerConstraints(0, i, 1, 1,GridBagConstraints.WEST,GridBagConstraints.BOTH));
					i++;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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

	public void actualizarVista() {
		this.removeAll();
		inicializarComponentes();
		this.paintAll(this.getGraphics()); 
	}
}
