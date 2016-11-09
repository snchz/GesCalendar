package modelo;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Dia implements Comparable<Dia> {
	private ListaHoras _horas;
	private Fecha _fecha;
	
	public Dia(int anio, int mes, int dia) throws Exception{
		_fecha=new Fecha(anio,mes,dia);
		_horas=new ListaHoras();
	}
	
	public Dia(int anio, int mes, int dia, ListaHoras horas) throws Exception {
		_fecha=new Fecha(anio,mes,dia);
		_horas=horas;
	}
	
	public Dia(Dia d) throws Exception {
		_fecha=new Fecha(d.obtenerFecha());
		_horas=new ListaHoras(d.obtenerListaHoras());
	}
	
	public Dia(Fecha f) throws Exception {
		_fecha=new Fecha(f);
		_horas=new ListaHoras();
	}
	
	/**
	 * Con el formato del toString 20160101#EXTRA=1.0;INVIERNO=8.0 20160229#
	 * 
	 * @param dia
	 * @throws Exception
	 */
	public Dia(String registro) throws Exception {
		try{
			StringTokenizer st = new StringTokenizer(registro, "#");
			String fecha = st.nextToken();// YYYYMMDD
			_fecha=new Fecha(fecha);
			if (st.hasMoreTokens()){
				String listaHoras=st.nextToken();// EXTRA=1.0;INVIERNO=8.0 (vacio)
				_horas = new ListaHoras(listaHoras);
			}else
				_horas = new ListaHoras();
		}catch (NoSuchElementException e) {
			System.out.println("Registro erroneo: "+registro);
			e.printStackTrace();
		}
	}
	

	public ListaHoras obtenerListaHoras() {
		return _horas;
	}
	
	public Fecha obtenerFecha() {
		return _fecha;
	}
	
	
	
	
	/////////////////// METODOS @Override ///////////////////////////

	@Override
	/**
	 * Ejemplo:
	 * 20160101#EXTRA=1.0;INVIERNO=8.0
	 * 20160229#
	 */
	public String toString() {
		return this._fecha+"#"+this._horas;
	}

	@Override
	/**
	 * YYYYMMDD
	 */
	public int hashCode() {
		return this._fecha.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dia other = (Dia) obj;
		return other.hashCode() == this.hashCode();
	}

	@Override
	/**
	 * Numero positivo: la cadena this es mayor que la cadena o. 
	 * 0: las cadenas son guales. 
	 * Numero negativo: la cadena this es menor que la cadena o.
	 * @param arg0
	 * @return
	 */
	public int compareTo(Dia o) {
		return this.hashCode() - o.hashCode();
	}
}
