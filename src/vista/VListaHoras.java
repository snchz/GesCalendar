package vista;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.ListaHoras;
import modelo.ParametrosModelo;

public class VListaHoras extends JPanel {
	private static final long serialVersionUID = 5792964151871781810L;
	private ListaHoras _lh;
	
	
	
	//////////////////////////////
	///		CONTRUCTORES		//
	//////////////////////////////
	
	public VListaHoras(ListaHoras lh){
		super();
		this._lh=lh;
		this.setLayout(new GridBagLayout());
		this.setBackground(ParametrosModelo.FONDO_VLISTAHORAS);
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
		if(horas==0)
			editable=true;
		
		Set <String> listaTotal=new HashSet<String>(ParametrosModelo.obtenerInstancia().set_items_tipo_horas);
		//eliminar todos los usados menos el seleccionado
		listaTotal.removeAll(this._lh.obtenerTiposUsadosMenos(tipoHora));
		String[] items=(String[])listaTotal.toArray(new String[listaTotal.size()]);	
		
		//Los items son todos menos los usados, pero incluyendo el seleccionado
		JComboBox<String> jcb_tipo=ParametrosVista.obtenerJComboBox(items,editable,new Dimension(100, 20));
		jcb_tipo.setSelectedItem(tipoHora);
		this.add(jcb_tipo,ParametrosVista.obtenerConstraints(1, fila, 1, 1,GridBagConstraints.WEST,GridBagConstraints.NONE));
		
		JTextField jtf_horas=ParametrosVista.obtenerJTextField(horas.toString(), editable, new Dimension(30, 20), false);
		this.add(jtf_horas,ParametrosVista.obtenerConstraints(2, fila, 1, 1,GridBagConstraints.EAST,GridBagConstraints.NONE));
		jtf_horas.addFocusListener(new java.awt.event.FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				_lh.eliminarHoras((String)jcb_tipo.getSelectedItem());
				try {
					_lh.anadirHoras((String)jcb_tipo.getSelectedItem(), Double.valueOf(jtf_horas.getText()));
					System.out.println("Cambios realizados.");
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		JButton jb_eliminar=ParametrosVista.obtenerJButton("-", new Dimension(41, 26));
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
		this.add(jb_eliminar,ParametrosVista.obtenerConstraints(3, fila, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE));
		if(fila==0){
			//Si es la primera fila anadimos el boton de agregar mas lineas
			JButton jb_anadir=ParametrosVista.obtenerJButton("+", new Dimension(41, 26));
			jb_anadir.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						//buscamos un item que no este anadido ya
						Set<String> tiposNoUsados=_lh.obtenerTiposNoUsadosDe(ParametrosModelo.obtenerInstancia().set_items_tipo_horas);
						System.out.println("TiposNoUsados: "+tiposNoUsados);
						if (tiposNoUsados.size()>0)
							_lh.anadirHoras(tiposNoUsados.iterator().next(), 0.0);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					actualizarVista();
				}
	        });
			this.add(jb_anadir,ParametrosVista.obtenerConstraints(4, fila, 1, 1, GridBagConstraints.EAST,GridBagConstraints.NONE));
			this.add(new JLabel("      "),ParametrosVista.obtenerConstraints(5, fila, 1, 1, GridBagConstraints.EAST,GridBagConstraints.NONE));
			
		}
	}
	
	public void actualizarVista(){
		this.removeAll();
		inicializarComponentes();
		((VDia)this.getParent()).actualizarVista();
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
