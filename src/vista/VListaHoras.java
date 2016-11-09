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

import modelo.ListaHoras;
import modelo.ParametrosModelo;
import util.Vistas;

public class VListaHoras extends JPanel {
	private ListaHoras _lh;
	
	public VListaHoras(ListaHoras lh){
		super();
		this._lh=lh;
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		this.inicializarComponentes();
	}
	
	/**
	 * --------------------------
	 * |Tipo Hora	| Horas	|-|+|	
	 * |Tipo Hora	| Horas	|-| |
	 * --------------------------
	 */
	private void inicializarComponentes() {
		Map<String,Double> listado=_lh.obtenerListaHoras();
		int fila=0;
		for(String tipoHora:listado.keySet()){
			Double horas=listado.get(tipoHora);
			anadirFilaHoras(fila, tipoHora, horas);
			fila++;
		}
	}
	
	private void anadirFilaHoras(int fila, String tipoHora, Double horas) {
		boolean editable=false;
		String[] items=new String[1];
		items[0]=tipoHora;
		if(horas==0){
			Set <String> listaTotal=new HashSet<String>(ParametrosModelo.obtenerInstancia().set_items_tipo_horas);
			listaTotal.removeAll(this._lh.obtenerTiposUsados());
			items=(String[])listaTotal.toArray(new String[listaTotal.size()]);	
			editable=true;
		}
		
		JComboBox<String> jcb_tipo=Vistas.obtenerJComboBox(items,editable,new Dimension(100, 20));
		jcb_tipo.setSelectedItem(tipoHora);
		this.add(jcb_tipo,Vistas.obtenerConstraints(1, fila, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		
		JTextField jtf_horas=Vistas.obtenerJTextField(horas.toString(), editable, new Dimension(30, 20), false);
		this.add(jtf_horas,Vistas.obtenerConstraints(2, fila, 1, 1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		
		JButton jb_eliminar=Vistas.obtenerJButton("-", new Dimension(41, 26));
		jb_eliminar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					_lh.eliminarHoras(tipoHora);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				actualizarVista();
			}
        });
		this.add(jb_eliminar,Vistas.obtenerConstraints(3, fila, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE));
		if(fila==0){
			//Si es la primera fila anadimos el boton de agregar mas lineas
			JButton jb_anadir=Vistas.obtenerJButton("+", new Dimension(41, 26));
			jb_anadir.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						//buscamos un item que no esté añadido ya
						Set <String> listaTotal=new HashSet<String>(ParametrosModelo.obtenerInstancia().set_items_tipo_horas);
						listaTotal.removeAll(_lh.obtenerTiposUsados());
						if (listaTotal.size()>0)
							_lh.anadirHoras(listaTotal.iterator().next(), 0.0);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					actualizarVista();
				}
	        });
			this.add(jb_anadir,Vistas.obtenerConstraints(4, fila, 1, 1, GridBagConstraints.EAST,GridBagConstraints.NONE));
			
		}
	}
	
	public void actualizarVista(){
		this.removeAll();
		inicializarComponentes();
		this.paintAll(this.getGraphics()); 
	}

	public static void main(String[] args) throws Exception {
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);

		ListaHoras lh=new ListaHoras("NORMAL=8.0;EXTRA=1.0");

		VListaHoras vlh = new VListaHoras(lh);
		jf.add(vlh);
		jf.setVisible(true);
		jf.pack();
	}

}
