package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import modelo.Dia;

public class VDia extends JPanel {

	public VDia(Dia d) {
		super();
		setLayout(new BorderLayout());
		JTextField jtf_fecha = new JTextField(d.obtenerFechaConFormato("yyyy/MM/dd"));
		jtf_fecha.setEditable(false);
		this.add(jtf_fecha, BorderLayout.WEST);

		Map<String, Double> m = d.obtenerHoras();
		JTable jt = new JTable(m.size(), 2);
		jt.getColumnModel().getColumn(0).setHeaderValue("Tipo");
		jt.getColumnModel().getColumn(1).setHeaderValue("Horas");
		int i = 0;
		for (String s : m.keySet()) {
			jt.setValueAt(s, i, 0);
			jt.setValueAt(m.get(s), i, 1);
			i++;
		}
		// Para mostrar la cabecera se mete la tabla dentro de un scroll pane
		this.add(new JScrollPane(jt), BorderLayout.CENTER);
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
