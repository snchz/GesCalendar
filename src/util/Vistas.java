package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Vistas {
	public static GridBagConstraints obtenerConstraints(int columnaDondeEmpieza, int filaDondeEmpieza, int columnasQueOcupa, int filasQueOcupa, int anchor,int fill){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = columnaDondeEmpieza; // El área empieza en la columna cero.
		constraints.gridy = filaDondeEmpieza; // El área empieza en la fila cero
		constraints.gridwidth = columnasQueOcupa; // El área de texto ocupa dos columnas.
		constraints.gridheight = filasQueOcupa; // El área de texto ocupa 2 filas.
		constraints.weighty = 0.0;
		constraints.fill=fill;
		constraints.anchor=anchor;
		return constraints;
	}
	
	public static JButton obtenerJButton(String texto, Dimension dimensiones){
		JButton jb=new JButton(texto);
		if (dimensiones!=null)
			jb.setSize(dimensiones);
		return jb;
	}
	
	public static JComboBox<String> obtenerJComboBox(String[] items,boolean habilitado,int ancho){
		JComboBox<String> res=new JComboBox<String>(items);
		res.setEnabled(habilitado);
		res.getSize().width=ancho;
		return res;
	}
	
	public static JTextField obtenerJTextField(String texto,boolean editable, int ancho, boolean borde){
		JTextField res =new JTextField(texto);
		res.setEditable(editable);
		res.setSize(new Dimension(res.getSize().height,ancho));
		res.setMinimumSize(new Dimension(res.getSize().height,ancho));
		res.setBackground(Color.WHITE);
		if (!borde)
			res.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		return res;
	}
}
