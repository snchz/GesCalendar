import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Calendario{
	private List<Dia> _calendario;

	public Calendario(){
		_calendario=new ArrayList<Dia>();
	}
	
	/**
	 * 
	 * @param fichero
	 * @return
	 */
	public boolean importarCalendario(File fichero){
		boolean res=false;
		_calendario=new ArrayList<Dia>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichero));
			String linea=br.readLine();
			while (linea!=null){
				Dia d=new Dia(linea);
				_calendario.add(d);
				linea=br.readLine();
			}
			br.close();
			res=true;
		} catch (Exception e) {
			res=false;
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean generarCalendario(Dia desde, Dia hasta){
		boolean res=true;
		Dia ini,fin;
		if (desde.hashCode()<=hasta.hashCode()){
			ini=new Dia(desde);
			fin=new Dia(hasta);
		}else{
			ini=new Dia(hasta);
			fin=new Dia(desde);
		}
		while (ini.hashCode()<=fin.hashCode()){
			_calendario.add(ini);
			ini=ini.obtenerDiaSiguiente();
		}
		return res;
	}
	
	public boolean exportarCalendario(File fichero){
		boolean res=false;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			for(Dia d:_calendario)
				bw.append(d.toString());
			bw.close();
			res=true;
		} catch (Exception e) {
			res=false;
			e.printStackTrace();
		}
		return res;
	}
	
	
	/**
	 * Agrega el dia, si existe lo sustituye.
	 * @param d
	 * @return true si todo ok
	 */
	public boolean agregarDia(Dia d){
		_calendario.remove(d);
		return _calendario.add(d);
	}
	
	/**
	 * Obtiene el día.
	 * @param dia
	 * @param mes
	 * @param año
	 * @return nulo si no existe
	 */
	public Dia obtenerDia(int dia, int mes, int año){
		Dia temp;
		try {
			temp = new Dia(dia, mes, año, new HashMap<String, Double>());
		} catch (Exception e) {
			return null;
		}
		for (Dia d:_calendario)
			if (temp.equals(d))
				return d;
		return null;
	}
	
	@Override
	public String toString() {
		this.ordenar();
		StringBuffer res=new StringBuffer();
		for (Dia d:_calendario)
			res.append(d+"\n");
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	private void ordenar() {
		Collections.sort(_calendario);
	}

	public static void main(String[] args) {
		Calendario c=new Calendario();
		System.out.println("Importando...");
		c.importarCalendario(new File("exportado.dat"));
		System.out.println("Importado");
		Dia d=null;
		try {
			d = new Dia(8, 8, 2016, new HashMap<>());
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.agregarDia(d);
		try {
			System.out.println("Generando...");
			c.generarCalendario(new Dia("20160101"), new Dia("20161231"));
			System.out.println("Generado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exportando...");
		c.exportarCalendario(new File("exportado.dat"));
		System.out.println("Exportado");
		System.out.println(c);
	}


}
