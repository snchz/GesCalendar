package modelo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Fecha implements Comparable<Fecha>{
	private int _dia, _mes, _anio;

	public Fecha(int anio, int mes, int dia) throws Exception {
		_anio=anio;
		_mes=mes;
		_dia=dia;
		this.validaFecha();
	}
	
	public Fecha(String fechaYYYMMDD) throws Exception {
		int ifecha = Integer.parseInt(fechaYYYMMDD);
		_anio = ifecha / 10000;
		_mes = (ifecha - _anio * 10000) / 100;
		_dia = ifecha - _anio * 10000 - _mes * 100;
		this.validaFecha();
	}

	public Fecha(Fecha fecha) {
		_anio=fecha.anio();
		_mes=fecha.mes();
		_dia=fecha.dia();
	}

	public int dia() {
		return _dia;
	}

	public int mes() {
		return _mes;
	}

	public int anio() {
		return _anio;
	}
	

	/**
	 * 
	 * @param formato yyyy/MM/dd
	 * @return
	 */
	public String obtenerFechaConFormato(String formato){
		try{
			DateTimeFormatter dtf=DateTimeFormat.forPattern(formato);
			DateTime dt=new DateTime(this.anio(), this.mes(), this.dia(),0,0,0,0);
			return dt.toString(dtf);
		}catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @return 1 Lunes, 2 Martes, 3 Miercoles, 4 Jueves, 5 Viernes, 6 Sabado, 7 Domingo
	 */
	public int obtenerDiaDeLaSemana(){
		DateTime dt=new DateTime(this.anio(), this.mes(), this.dia(), 0, 0);
		return dt.dayOfWeek().get();
	}

	private boolean validaFecha() throws Exception {
		boolean valido = true;
		try {
			new DateTime(this.anio(), this.mes(), this.dia(),0,0,0,0);
		} catch (Exception e) {
			throw new Exception("Fecha incorrecta;  Anio "+this.anio()+"  Mes: "+this.mes()+"  Dia: "+this.dia());
		}
		return valido;
	}
	

	public Fecha obtenerDiaSiguiente() {
		try {
			DateTime dt=new DateTime(this.anio(), this.mes(), this.dia(), 0, 0);
			dt=dt.plusDays(1);
			Fecha res = new Fecha(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
			return res;
		} catch (Exception e) {
			return null;
		}
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
	 * YYYYMMDD
	 */
	public int hashCode() {
		int res = anio() * 10000 + mes() * 100 + dia();
		return res;
	}
	
	@Override
	/**
	 * Ejemplo:
	 * 20160101
	 */
	public String toString() {
		return String.valueOf(this.hashCode());
	}

	@Override
	/**
	 * Numero positivo: la cadena this es mayor que la cadena o. 
	 * 0: las cadenas son guales. 
	 * Numero negativo: la cadena this es menor que la cadena o.
	 * @param arg0
	 * @return
	 */
	public int compareTo(Fecha o) {
		return this.hashCode() - o.hashCode();
	}

}
