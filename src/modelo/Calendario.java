package modelo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Calendario implements Iterable<Dia>{
	private List<Dia> _calendario;

	public Calendario() {
		_calendario = new ArrayList<Dia>();
	}
	
	public Calendario(File fichero) {
		this();
		importarCalendario(fichero);
	}
	
	/**
	 * Agrega el dia.
	 * 
	 * @param d
	 * @return true si todo ok
	 */
	public boolean agregarDia(Dia d) {
		return _calendario.add(d);
	}

	/**
	 * 
	 * @param d
	 * @param tipo
	 * @param horas
	 * @return false, si no existe el dia. true, si existe.
	 * @throws Exception 
	 */
	public boolean agregarHorasADia(Dia d, String tipo, double horas) throws Exception{
		Dia temp=obtenerDia(d);
		if (temp==null)
			return false;
		else
			return temp.agregarHoras(tipo, horas);
	}
	
	public int obtenerNumeroDiasCalendario(){
		return _calendario.size();
	}
	
	/**
	 * 
	 * @param fichero
	 * @return
	 */
	private boolean importarCalendario(File fichero) {
		boolean res = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichero));
			String linea = br.readLine();
			while (linea != null) {
				Dia d = new Dia(linea);
				this.agregarDia(d);
				linea = br.readLine();
			}
			br.close();
			res = true;
		} catch (Exception e) {
			res = false;
			e.printStackTrace();
		}
		return res;
	}

	public boolean generarCalendario(Dia desde, Dia hasta) {
		boolean res = true;
		try {
			Dia ini, fin;
			if (desde.hashCode() <= hasta.hashCode()) {
				ini = new Dia(desde);
				fin = new Dia(hasta);
			} else {
				ini = new Dia(hasta);
				fin = new Dia(desde);
			}
			while (ini.hashCode() <= fin.hashCode()) {
				this.agregarDia(ini);
				ini = ini.obtenerDiaSiguiente();
			}
			res = true;
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	public boolean exportarCalendario(File fichero) {
		boolean res = false;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			for (Dia d : _calendario)
				bw.append(d.toString()+"\n");
			bw.close();
			res = true;
		} catch (Exception e) {
			res = false;
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Obtiene el dia. 
	 * 
	 * @param dia
	 * @param mes
	 * @param  anio
	 * @return nulo si no existe
	 */
	public Dia obtenerDia(Dia d) {
		Dia temp;
		try {
			temp = new Dia(d);
		} catch (Exception e) {
			return null;
		}
		for (Dia di : _calendario)
			if (temp.equals(di))
				return di;
		return null;
	}

	@Override
	public String toString() {
		this.ordenar();
		StringBuffer res = new StringBuffer();
		for (Dia d : _calendario)
			res.append(d + "\n");
		return res.toString();
	}

	private void ordenar() {
		Collections.sort(_calendario);
	}
	
	public Double obtenerSumaHoras(String tipo, Dia ini, Dia fin) {
		double res=0;
		for (Dia d:_calendario){
			if (d.hashCode()>=ini.hashCode()&&d.hashCode()<=fin.hashCode())
				res=res+d.obtenerSumaHoras(tipo);
		}
		return res;
	}
	
	public List<Dia> obtenerDiasSinHoras(){
		List<Dia> res=new ArrayList<>();
		for (Dia d:_calendario){
			if (d.obtenerSumaTotalHoras()==0)
				res.add(d);
		}
		Collections.sort(res);
		return res;
	}
	
	public int obtenerContadorDiasConTipo(String tipo, Dia ini, Dia fin) {
		int res=0;
		for (Dia d:_calendario){
			if (d.hashCode()>=ini.hashCode()&&d.hashCode()<=fin.hashCode()&&d.tieneHorasDelTipo(tipo))
				res++;
		}
		return res;
	}
	
	public boolean rellenarFinDeSemana(String tipo, double horas) throws Exception{
		for(Dia d:_calendario){
			if (d.obtenerDiaDeLaSemana()==6 || d.obtenerDiaDeLaSemana()==7){
				d.agregarHoras(tipo, horas);
			}
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			Calendario c = new Calendario();
			c.importarCalendario(new File("exportado.dat"));
			System.out.println(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Iterator<Dia> iterator() {
		return _calendario.iterator();
	}

}
