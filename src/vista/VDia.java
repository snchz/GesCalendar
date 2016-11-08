package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.Dia;
import util.Vistas;

public class VDia extends JPanel {
	private Dimension _dimVDia;
	
	public VDia(Dia d) {
		super();
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);
		// ---------------------
		// |Fecha| Horas	1	-+|
		// |	 | Horas	2	-+|
		// |	 | Horas	3	-+|
		// |	 | Horas	4	-+|
		// ---------------------
		this.inicializarComponentes(d);
	}
	
	private JTextField fechaJTextField(Dia d){
		JTextField jtf_fecha = new JTextField(d.obtenerFechaConFormato("yyyy/MM/dd"));
		jtf_fecha.setEditable(false);
		jtf_fecha.setSize(80, 20);
		jtf_fecha.setMaximumSize(jtf_fecha.getSize());
		jtf_fecha.setHorizontalAlignment(SwingConstants.CENTER);
		return jtf_fecha;
	}
	
	private JComboBox<String> tipoJComboBox(String s){
		String[] items=new String[1]; items[0]=new String(s);
		JComboBox<String> jcb_tipo=new JComboBox<String>(items);
		return jcb_tipo;
	}
	
	
	/**
	 * Alto VDia = 20*numTiposHoras
	 * Ancho VDia = 80(fecha)+200(Combo Tipo)+30(horas)=310
	 * @param d
	 */
	private void inicializarComponentes(Dia d){
		int tamanioListaHoras=d.obtenerHoras().keySet().size();
		//Componente de Fecha
		
		this.add(fechaJTextField(d));
		
		
		Map<String, Double> m = d.obtenerHoras();
		int numeroTiposHoras=m.size();

		_dimVDia=new Dimension(310, numeroTiposHoras*20);
		
		//CONTENIDO
		int i = 0;
		for (String s : m.keySet()) {
			//TIPO
			
			this.add(jcb_tipo);
			//HORAS
			JTextField jtf_horas=new JTextField(m.get(s).toString());
			this.add(jtf_horas);
			//ELIMINAR
			JButton jb_eliminar=new JButton("-");
			this.add(jb_eliminar);
			i++;
		}

		JButton jb_anadir=new JButton("+");
		this.add(jb_anadir);
	}
	
	public void pack(){
		this.setSize(this._dimVDia);
		this.setMinimumSize(this._dimVDia);
		this.setMaximumSize(this._dimVDia);
		this.setPreferredSize(this._dimVDia);
	}

	public static void main(String[] args) throws Exception {
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// jf.setSize(300, 200);
		jf.setLocationRelativeTo(null);

		Dia d = new Dia("20160101");
		d.agregarHoras("NORMAL", 8.0);
		d.agregarHoras("EXTRA", 1.5);

		VDia vd = new VDia(d);
		jf.add(vd);
		System.out.println(vd.getSize());
		jf.setVisible(true);
		System.out.println(vd.getSize());
		vd.pack();
		System.out.println(vd.getSize());
		//jf.pack();
		System.out.println(vd.getSize());
	}

}
