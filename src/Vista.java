import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class Vista extends JFrame {
	
	public Vista(){
		iniciarUI();
	}

	private void iniciarUI() {
		crearMenuBar();
		
		setTitle(Configuracion.APLICACION);
        setSize(300, 200);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        crearContenido();
	}

	private void crearContenido() {
		JTable table = new JTable();
	}

	private void crearMenuBar() {
		//BARRA DE MENU
		JMenuBar jmb = new JMenuBar();
		
		//--MENU ARCHIVO
		JMenu jmArchivo = new JMenu(Configuracion.MENU_ARCHIVO);
		jmArchivo.setMnemonic(KeyEvent.VK_A);
        
        //----SUBMENU IMPORTAR
        JMenu impMenu = new JMenu(Configuracion.MENU_ARCHICHO_IMPORTAR);
        JMenuItem newsfMi = new JMenuItem(Configuracion.MENU_ARCHICHO_IMPORTARDEFECTO);
        impMenu.add(newsfMi);
        jmArchivo.add(impMenu);
        
		//----ITEM SALIR
        JMenuItem jmiSalir = new JMenuItem(Configuracion.MENU_SALIR);
        jmiSalir.setMnemonic(KeyEvent.VK_S);
        jmiSalir.setToolTipText(Configuracion.MENU_SALIR_DESC);
        jmiSalir.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        jmiSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
        jmArchivo.add(jmiSalir);
        
        
        //--AÑADIR ITEMS ARCHIVO
        jmb.add(jmArchivo);
        
        //PONER BARRA
        this.setJMenuBar(jmb);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            Vista ex = new Vista();
            ex.setVisible(true);
        });
	}

}
