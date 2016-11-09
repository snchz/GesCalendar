package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Dia;
import modelo.ParametrosModelo;
import util.Vistas;

public class VDia extends JPanel {
	private static final long serialVersionUID = -3461158432879876002L;
	private Dia _d;
	private int _tamanioListaHorasVDia;
	
	public VDia(Dia d) {
		super();
		_d=d;
		setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		// ---------------------
		// |Fecha| Horas	1	-+|
		// |	 | Horas	2	-+|
		// |	 | Horas	3	-+|
		// |	 | Horas	4	-+|
		// ---------------------
		_tamanioListaHorasVDia=_d.obtenerListaHoras().obtenerTiposUsados().size();
		this.inicializarComponentes();
	}
	
	public Dia obtenerDia(){
		return _d;
	}
	
	public void actualizarVista(){
		this.removeAll();
		inicializarComponentes();
		this.paintAll(this.getGraphics()); 
	}
	
	private void inicializarComponentes(){
		//Componente de Fecha
		JTextField jtf_fecha = Vistas.obtenerJTextField(_d.obtenerFecha().obtenerFechaConFormato("yyyy/MM/dd"), false, new Dimension(80, 20), false);
		this.add(jtf_fecha, Vistas.obtenerConstraints(0, 0, 1, _tamanioListaHorasVDia,GridBagConstraints.WEST,GridBagConstraints.NONE));

		Map<String, Double> m = _d.obtenerListaHoras().obtenerListaHoras();
		//CONTENIDO
		int i = 0;
		for (String tipoHora : m.keySet()) {
			//TIPO
			this.anadirFilaHorasFija(i,tipoHora,m.get(tipoHora).toString());
			i++;
		}

		JButton jb_anadir=Vistas.obtenerJButton("+", new Dimension(41, 26));
		jb_anadir.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					anadirFilaHorasVacia();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				actualizarVista();
			}
        });
		this.add(jb_anadir,Vistas.obtenerConstraints(4, 0, 1, _tamanioListaHorasVDia, GridBagConstraints.EAST,GridBagConstraints.NONE));
	}
	
	public void anadirFilaHorasVacia(){
		Set <String> listaTotal=new HashSet<String>(ParametrosModelo.obtenerInstancia().set_items_tipo_horas);
		listaTotal.removeAll(this._d.obtenerListaHoras().obtenerTiposUsados());
		String[] items=(String[])listaTotal.toArray(new String[listaTotal.size()]);	
		_tamanioListaHorasVDia++;
		
		//TIPO
		JComboBox<String> jcb_tipo=Vistas.obtenerJComboBox(items,true,new Dimension(100, 20));
		//jcb_tipo.setSelectedItem(listaTotal.);
		this.add(jcb_tipo,Vistas.obtenerConstraints(1, _tamanioListaHorasVDia, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		//HORAS
		JTextField jtf_horas=Vistas.obtenerJTextField(ParametrosModelo.HORAS_DEFECTO.toString(), true, new Dimension(30, 20), false);
		this.add(jtf_horas,Vistas.obtenerConstraints(2, _tamanioListaHorasVDia, 1, 1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		//ELIMINAR
		JButton jb_eliminar=Vistas.obtenerJButton("-", new Dimension(41, 26));
		jb_eliminar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jcb_tipo.getParent().remove(jcb_tipo);
					jcb_tipo.getParent().remove(jtf_horas);
					jcb_tipo.getParent().remove(jb_eliminar);
					_tamanioListaHorasVDia--;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				actualizarVista();
			}
        });
		this.add(jb_eliminar,Vistas.obtenerConstraints(3, _tamanioListaHorasVDia, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE));
			
	}
	
	public void anadirFilaHorasFija(int filaDondeEmpieza, String tipoHora, String horas){
		String[] items=new String[1];
		items[0]=tipoHora;
		//TIPO
		JComboBox<String> jcb_tipo=Vistas.obtenerJComboBox(items,false,new Dimension(100, 20));
		jcb_tipo.setSelectedItem(tipoHora);
		this.add(jcb_tipo,Vistas.obtenerConstraints(1, filaDondeEmpieza, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		//HORAS
		JTextField jtf_horas=Vistas.obtenerJTextField(horas, false, new Dimension(30, 20), false);
		this.add(jtf_horas,Vistas.obtenerConstraints(2, filaDondeEmpieza, 1, 1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		//ELIMINAR
		JButton jb_eliminar=Vistas.obtenerJButton("-", new Dimension(41, 26));
		jb_eliminar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					_d.obtenerListaHoras().eliminarHoras(tipoHora);
					_tamanioListaHorasVDia--;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				actualizarVista();
			}
        });
		this.add(jb_eliminar,Vistas.obtenerConstraints(3, filaDondeEmpieza, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE));
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