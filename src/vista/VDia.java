package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Dia;
import util.Configuracion;
import util.Vistas;

public class VDia extends JPanel {
	private Dia _d;
	
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
		this.inicializarComponentes();
	}
	
	public void actualizarComponente(){
		this.removeAll();
		this.inicializarComponentes();
	}
	
	public Dia obtenerDia(){
		return _d;
	}
	
	private void inicializarComponentes(){
		int tamanioListaHoras=_d.obtenerTiposUsados().size();
		//Componente de Fecha
		JTextField jtf_fecha = Vistas.obtenerJTextField(_d.obtenerFechaConFormato("yyyy/MM/dd"), false, new Dimension(80, 20), false);
		this.add(jtf_fecha, Vistas.obtenerConstraints(0, 0, 1, tamanioListaHoras,GridBagConstraints.WEST,GridBagConstraints.NONE));

		Map<String, Double> m = _d.obtenerHoras();
		//CONTENIDO
		int i = 0;
		for (String tipoHora : m.keySet()) {
			//TIPO
			this.anadirHoras(i,tipoHora,m.get(tipoHora).toString());
			i++;
		}
		if(i==0){
			this.anadirHoras(i,"","0");
		}

		JButton jb_anadir=Vistas.obtenerJButton("+", new Dimension(41, 26));
		jb_anadir.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				System.out.println(b.getParent());
				VDia vd=(VDia) b.getParent();
				try {
					vd.obtenerDia().agregarHoras(Configuracion.ITEMS_TIPO_HORAS.iterator().next(), 0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
        });
		this.add(jb_anadir,Vistas.obtenerConstraints(4, 0, 1, tamanioListaHoras, GridBagConstraints.EAST,GridBagConstraints.NONE));
	}
	
	public void anadirHoras(int filaDondeEmpieza, String tipoHora, String horas){
		String[] items=(String[])_d.obtenerTiposUsados().toArray(new String[Configuracion.ITEMS_TIPO_HORAS.size()]);	
		//TIPO
		JComboBox<String> jcb_tipo=Vistas.obtenerJComboBox(items,false,new Dimension(100, 20));
		jcb_tipo.setSelectedItem(tipoHora);
		this.add(jcb_tipo,Vistas.obtenerConstraints(1, filaDondeEmpieza, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		//HORAS
		JTextField jtf_horas=Vistas.obtenerJTextField(horas, false, new Dimension(30, 20), false);
		this.add(jtf_horas,Vistas.obtenerConstraints(2, filaDondeEmpieza, 1, 1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		//ELIMINAR
		JButton jb_eliminar=Vistas.obtenerJButton("-", new Dimension(41, 26));
		this.add(jb_eliminar,Vistas.obtenerConstraints(3, filaDondeEmpieza, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE));
		
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