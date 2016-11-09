package vista;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import modelo.Calendario;
import modelo.Dia;

public class VPrincipal extends JFrame{
	
	public VPrincipal(VCalendario vc){
		iniciarComponentes(vc);
	}

	private void iniciarComponentes(VCalendario vc) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setLocationRelativeTo(null);
		
		add(vc);
		pack();
		setMinimumSize(getSize());
		setVisible(true);
	}

	public static void main(String[] args)  {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel ( "javax.swing.plaf.nimbus.NimbusLookAndFeel" );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                
                try{
        			Calendario c=new Calendario();
        	
        			Dia d=new Dia("20160101");
        			d.agregarHoras("NORMAL", 8.0);
        			d.agregarHoras("EXTRA", 1.5);
        			d.agregarHoras("ROLLOUT", 4);
        			c.agregarDia(d);
        			
        	
        			Dia d1=new Dia("20160102");
        			d1.agregarHoras("NORMAL", 8.0);
        			c.agregarDia(d1);
        	
        			Dia d2=new Dia("20160103");
        			c.agregarDia(d2);
        			
        			Dia d3=new Dia("20160104");
        			d3.agregarHoras("NORMAL", 8.0);
        			d3.agregarHoras("EXTRA", 2.0);
        			c.agregarDia(d3);
        			
        			VCalendario vc=new VCalendario(c);
        			VPrincipal vp=new VPrincipal(vc);
        			SwingUtilities.updateComponentTreeUI(vp);
        		}catch (Exception e) {
        			e.printStackTrace();
        		}
            }
        });
		
		
		
	}

}
