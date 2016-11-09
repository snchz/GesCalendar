package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Vistas {

	/**
	 * 
	 * anchor 
	 * GridBagConstraints.CENTER Es la opción por defecto 
	 * GridBagConstraints.NORTH 
	 * GridBagConstraints.NORTHEAST 
	 * GridBagConstraints.WEST  
	 * GridBagConstraints.NORTHWEST 
	 * 
	 * fill 
	 * GridBagConstraints.NONE para que no se estire en ningún sentido, es
	 * 		la opción por defecto. 
	 * GridBagConstraints.VERTICAL para que se estire sólo en vertical 
	 * GridBagConstraints.HORIZONTAL para que se estire sólo en horizontal. 
	 * GridBagConstraints.BOTH para que se estire en ambas dimensiones
	 * 
	 * @param columnaDondeEmpieza
	 * @param filaDondeEmpieza
	 * @param columnasQueOcupa
	 * @param filasQueOcupa
	 * @param anchor
	 * @param fill
	 * @return
	 */
	public static GridBagConstraints obtenerConstraints(int columnaDondeEmpieza, int filaDondeEmpieza,
			int columnasQueOcupa, int filasQueOcupa, int anchor, int fill) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = columnaDondeEmpieza; 
		constraints.gridy = filaDondeEmpieza; 
		constraints.gridwidth = columnasQueOcupa;
		constraints.gridheight = filasQueOcupa; 
		//constraints.weighty = 0.0;
		constraints.fill = fill;
		constraints.anchor = anchor;
		return constraints;
	}

	public static JButton obtenerJButton(String texto, Dimension dimension) {
		JButton res = new JButton(texto);
		if (dimension!=null){
		res.setPreferredSize(dimension);
		res.setMinimumSize(dimension);
		res.setMaximumSize(dimension);
		}
		return res;
	}

	public static JComboBox<String> obtenerJComboBox(String[] items, boolean habilitado, Dimension dimension) {
		JComboBox<String> res = new JComboBox<String>(items);
		res.setEnabled(habilitado);
		res.setPreferredSize(dimension);
		res.setMinimumSize(dimension);
		res.setMaximumSize(dimension);
		return res;
	}

	public static JTextField obtenerJTextField(String texto, boolean editable, Dimension dimension, boolean borde) {
		JTextField res = new JTextField(texto);
		res.setEditable(editable);
		res.setPreferredSize(dimension);
		res.setMinimumSize(dimension);
		res.setMaximumSize(dimension);
		res.setBackground(Color.WHITE);
		res.setHorizontalAlignment(SwingConstants.CENTER);
		if (!borde)
			res.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		return res;
	}
}
