import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Dia implements Comparable<Dia> {
	private Map<String, Double> _horas;
	private int _dia, _mes, _anio;

	public Dia(int dia, int mes, int anio) throws Exception{
		_dia = dia;
		_mes = mes;
		_anio = anio;
		this.asignarHoras(new HashMap<String, Double>());
		if (!esValido())
			throw new Exception("Fecha invalida: " + this.hashCode());
	}
	
	public Dia(int dia, int mes, int anio, Map<String, Double> horas) throws Exception {
		this(dia,mes,anio);
		this.asignarHoras(horas);
	}

	public Dia(Dia dia) throws Exception {
		this(dia.obtenerDia(),dia.obtenerMes(),dia.obtenerAnio(),new HashMap<String, Double>(dia.obtenerHoras()));
	}
	
	/**
	 * 
	 * @return 1 Lunes, 2 Martes, 3 Miercoles, 4 Jueves, 5 Viernes, 6 Sabado, 7 Domingo
	 */
	public int obtenerDiaDeLaSemana(){
		LocalDate ld = LocalDate.of(this._anio, this._mes, this._dia);
		return ld.getDayOfWeek().getValue();
	}

	/**
	 * Con el formato del toString 20160101#EXTRA=1.0;INVIERNO=8.0 20160229#
	 * 
	 * @param dia
	 * @throws Exception
	 */
	public Dia(String registro) throws Exception {
		try {
			StringTokenizer st = new StringTokenizer(registro, "#");
			String fecha = st.nextToken();// YYYYMMDD
			asignarFecha(fecha);
			_horas = new HashMap<String, Double>();
			String lista;
			if (st.hasMoreElements())
				lista = st.nextToken();// EXTRA=1.0;INVIERNO=8.0 � (vacio)
			else
				lista = "";
			st = new StringTokenizer(lista, ";");
			while (st.hasMoreTokens()) {
				String tipo_horas = st.nextToken();
				StringTokenizer stth = new StringTokenizer(tipo_horas, "=");
				String tipo = stth.nextToken();
				String horas = stth.nextToken();
				_horas.put(tipo, Double.valueOf(horas));
			}
		} catch (Exception e) {
			throw new Exception("String de Dia con formato incorrecto " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param fecha
	 *            con formato YYYYMMDD
	 * @return
	 * @throws Exception
	 */
	private boolean asignarFecha(String fecha) throws Exception {
		try {
			int ifecha = Integer.parseInt(fecha);
			_anio = ifecha / 10000;
			_mes = (ifecha - _anio * 10000) / 100;
			_dia = ifecha - _anio * 10000 - _mes * 100;
		} catch (Exception e) {
			throw new Exception("Fecha invalida: " + this.hashCode());
		}
		return true;
	}

	public Dia obtenerDiaSiguiente() {
		try {
			LocalDate ld = LocalDate.of(this._anio, this._mes, this._dia);
			LocalDate ldsig = ld.plusDays(1);
			Dia res = new Dia(ldsig.getDayOfMonth(), ldsig.getMonthValue(), ldsig.getYear(), new HashMap<>());
			return new Dia(res);
		} catch (Exception e) {
			return null;
		}
	}

	public int obtenerDia() {
		return _dia;
	}

	public int obtenerMes() {
		return _mes;
	}

	public int obtenerAnio() {
		return _anio;
	}

	public Map<String, Double> obtenerHoras() {
		return _horas;
	}

	public Double obtenerSumaTotalHoras() {
		double res = 0;
		for (Double d : _horas.values())
			res = res + d;
		return res;
	}

	public Double obtenerSumaHoras(String tipo) {
		Double temp=_horas.get(tipo);
		double res=0;
		if (temp==null)
			res=0;
		else
			res=temp.doubleValue();
		return res;
	}
	
	public boolean tieneHorasDelTipo(String tipo){
		return _horas.containsKey(tipo);
	}

	/**
	 * Setea las horas con el contenido de horas
	 * @param horas
	 * @return
	 * @throws Exception 
	 */
	public boolean asignarHoras(Map<String, Double> horas) throws Exception {
		_horas = new HashMap<String, Double>();
		_horas.putAll(horas);
		if (this.obtenerSumaTotalHoras()>24)
			throw new Exception("Mas de 24 horas al dia no es posible.");
		return true;
	}
	
	public boolean agregarHoras(String tipo, double horas) throws Exception {
		_horas.put(tipo, horas);
		if (this.obtenerSumaTotalHoras()>24)
			throw new Exception("Mas de 24 horas al dia no es posible: "+this.toString());
		return true;
	}

	private boolean esValido() {
		boolean valido = true;
		try {
			LocalDate.of(_anio, _mes, _dia);
		} catch (DateTimeException e) {
			valido = false;
		}
		return valido;
	}

	@Override
	/**
	 * Ejemplo:
	 * 20160101#EXTRA=1.0;INVIERNO=8.0
	 * 20160229#
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.hashCode());
		sb.append("#");
		for (String tipo : _horas.keySet()) {
			sb.append(tipo);
			sb.append("=");
			sb.append(_horas.get(tipo));
			sb.append(";");
		}
		if (sb.substring(sb.length() - 1, sb.length()).equals(";"))
			sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	@Override
	/**
	 * YYYYMMDD
	 */
	public int hashCode() {
		// 20161130
		// 2016 1 1 -> 20160000+100+1=20160101
		int res = _anio * 10000 + _mes * 100 + _dia;
		return res;
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
