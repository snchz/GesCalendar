import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Dia implements Comparable<Dia>{
	private Map<String, Double> _horas;
	private int _dia, _mes, _a�o;

	public Dia(int dia, int mes, int a�o, Map<String, Double> horas) throws Exception {
		_dia = dia;
		_mes = mes;
		_a�o = a�o;
		_horas=new HashMap<String, Double>();
		_horas.putAll(horas);
		if (!esValido())
			throw new Exception("Fecha inv�lida: " + this.hashCode());
	}
	
	public Dia(Dia dia){
		_dia=dia.getDia();
		_mes=dia.getMes();
		_a�o=dia.getA�o();
		_horas=new HashMap<String, Double>(dia.getHoras());
	}
	
	/**
	 * Con el formato del toString
	 * 20160101#EXTRA=1.0;INVIERNO=8.0
	 * 20160229#
	 * @param dia
	 * @throws Exception 
	 */
	public Dia(String diahoras) throws Exception{
		try{
			StringTokenizer st=new StringTokenizer(diahoras,"#");
			String fecha=st.nextToken();//YYYYMMDD
			setFecha(fecha);
			_horas=new HashMap<String, Double>();
			String lista;
			if (st.hasMoreElements())
				lista=st.nextToken();//EXTRA=1.0;INVIERNO=8.0 �  (vacio)
			else
				lista="";
			st=new StringTokenizer(lista,";");
			while (st.hasMoreTokens()){
				String tipo_horas=st.nextToken();
				StringTokenizer stth=new StringTokenizer(tipo_horas,"=");
				String tipo=stth.nextToken();
				String horas=stth.nextToken();
				_horas.put(tipo, Double.valueOf(horas));
			}
		}catch (Exception e) {
			throw new Exception("String de Dia con formato incorrecto "+e.getMessage());
		}
	}

	/**
	 * 
	 * @param fecha con formato YYYYMMDD
	 * @return
	 * @throws Exception 
	 */
	private boolean setFecha(String fecha) throws Exception {
		try{
			int ifecha=Integer.parseInt(fecha);
			_a�o=ifecha/10000;
			_mes=(ifecha-_a�o*10000)/100;
			_dia=ifecha-_a�o*10000-_mes*100;
		}catch (Exception e) {
			throw new Exception("Fecha inv�lida: " + this.hashCode());
		}
		return true;
	}
	
	public Dia obtenerDiaSiguiente(){
		 try {
			 LocalDate ld=LocalDate.of(this._a�o, this._mes, this._dia);
			 LocalDate ldsig=ld.plusDays(1);
			 Dia res=new Dia(ldsig.getDayOfMonth(), ldsig.getMonthValue(), ldsig.getYear(), new HashMap<>());
			return new Dia(res);
		} catch (Exception e) {
			return null;
		}
	}

	public int getDia() {
		return _dia;
	}

	public int getMes() {
		return _mes;
	}

	public int getA�o() {
		return _a�o;
	}

	public Map<String, Double> getHoras() {
		return _horas;
	}

	public Double getTotalHoras() {
		double res=0;
		for (Double d:_horas.values())
			res=res+d;
		return res;
	}

	public Double getHoras(String tipo) {
		return _horas.get(tipo);
	}

	public boolean setHoras(Map<String, Double> horas) {
		_horas=new HashMap<String, Double>();
		_horas.putAll(horas);
		return true;
	}

	private boolean esValido() {
		boolean valido = true;
	    try {
	        LocalDate.of(_a�o, _mes, _dia);
	    } catch (DateTimeException e) {
	    	valido = false;
	    }
	    return valido;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(this.hashCode());
		sb.append("#");
		for (String tipo:_horas.keySet()){
			sb.append(tipo);
			sb.append("=");
			sb.append(_horas.get(tipo));
			sb.append(";");
		}
		if (sb.substring(sb.length()-1, sb.length()).equals(";"))
			sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}

	@Override
	/**
	 * YYYYMMDD
	 */
	public int hashCode() {
		// 20161130
		//2016 1 1 -> 20160000+100+1=20160101
		int res = _a�o * 10000 + _mes * 100 + _dia;
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

	public static void main(String[] args) {
		try {
			Dia d1=new Dia("20160101#EXTRA=1.0;INVIERNO=8.0");
			Dia d2=new Dia("20160229#");
			System.out.println(d1);
			System.out.println(d2);
			
			/*
			HashMap<String, Double> horas=new HashMap<String, Double>();
			horas.put("INVIERNO", 8.0);
			horas.put("EXTRA", 1.0);
			Dia d1 = new Dia(1, 1, 2016, horas);
			System.out.println(d1);

			HashMap<String, Double> horas12=new HashMap<String, Double>();
			Dia d12 = new Dia(29, 2, 2016, horas12);
			System.out.println(d12);
			
			HashMap<String, Double> horas2=new HashMap<String, Double>();
			horas2.put("INVIERNO", 8.0);
			Dia d2 = new Dia(30, 2, 2016, horas2);
			System.out.println(d2);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * N�mero positivo: la cadena 1 es mayor que la cadena 2.
	 * 0: las cadenas son iguales.
	 * N�mero negativo: la cadena 1 es menor que la cadena 2.
	 * @param arg0
	 * @return
	 */
	public int compareTo(Dia o) {
		return this.hashCode()-o.hashCode();
	}
}
