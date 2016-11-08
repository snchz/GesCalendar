package vista;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Dia;
import util.Vistas;

public class VDia extends JPanel {

	public VDia(Dia d) {
		super();
		setLayout(new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);
		// ---------------------
		// |Fecha| Horas	1	-+|
		// |	 | Horas	2	-+|
		// |	 | Horas	3	-+|
		// |	 | Horas	4	-+|
		// ---------------------
		this.inicializarComponentes(d);
	}
	
	
	
	private void inicializarComponentes(Dia d){
		int tamanioListaHoras=d.obtenerHoras().keySet().size();
		//Componente de Fecha
		JTextField jtf_fecha = Vistas.obtenerJTextField(d.obtenerFechaConFormato("yyyy/MM/dd"), false, 20, false);
		this.add(jtf_fecha, Vistas.obtenerConstraints(0, 0, 1, tamanioListaHoras,GridBagConstraints.WEST,GridBagConstraints.NONE));

		Map<String, Double> m = d.obtenerHoras();
		
		//CONTENIDO
		int i = 0;
		for (String s : m.keySet()) {
			//TIPO
			String[] items=new String[1]; items[0]=new String(s);
			JComboBox<String> jcb_tipo=Vistas.obtenerJComboBox(items,false,15);
			this.add(jcb_tipo,Vistas.obtenerConstraints(1, i, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
			//HORAS
			JTextField jtf_horas=Vistas.obtenerJTextField(m.get(s).toString(), false, 3, false);
			this.add(jtf_horas,Vistas.obtenerConstraints(2, i, 1, 1,GridBagConstraints.EAST,GridBagConstraints.NONE));
			//ELIMINAR
			JButton jb_eliminar=Vistas.obtenerJButton("-", null);
			this.add(jb_eliminar,Vistas.obtenerConstraints(3, i, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE));
			i++;
		}

		JButton jb_anadir=Vistas.obtenerJButton("+", null);
		this.add(jb_anadir,Vistas.obtenerConstraints(4, 0, 1, tamanioListaHoras, GridBagConstraints.EAST,GridBagConstraints.NONE));
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
		jf.setVisible(true);
		jf.pack();
	}

}
