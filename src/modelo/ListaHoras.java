package modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


public class ListaHoras {
	/**
	 * Minimo, contendrá el siguiente par:
	 * ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES, ParametrosModelo.HORAS_TOTAL
	 */
	private Map<String, Double> _horas;
	
	public ListaHoras(){
		_horas=new HashMap<String, Double>();
		try {
			anadirHoras(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES, ParametrosModelo.HORAS_TOTAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListaHoras(ListaHoras obtenerListaHoras) {
		_horas=new HashMap<String, Double>(obtenerListaHoras.obtenerListaHoras());
	}
	
	/**
	 * EXTRA=1.0;INVIERNO=8.0 o vacio
	 * @param listaHoras
	 * @throws Exception 
	 * @throws  
	 */
	public ListaHoras(String registroListaHoras) throws Exception{
		_horas=new HashMap<String, Double>();
		try {
			anadirHoras(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES, ParametrosModelo.HORAS_TOTAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (registroListaHoras!=null){
			StringTokenizer st = new StringTokenizer(registroListaHoras, ";");
			while (st.hasMoreTokens()) {
				String tipo_horas = st.nextToken();
				StringTokenizer stth = new StringTokenizer(tipo_horas, "=");
				String tipo = stth.nextToken();
				String horas = stth.nextToken();
				this.anadirHoras(tipo, Double.valueOf(horas));
			}
		}
		
		this.validaHoras();
	}
	
	public Map<String, Double> obtenerListaHoras(){
		return _horas;
	}

	public Set<String> obtenerTiposUsados(){
		Set<String> res=new HashSet<String>();
		for(String tipo:_horas.keySet()){
			res.add(tipo);
		}
		return res;
	}

	public boolean anadirHoras(String tipo, Double horas) throws Exception {
		_horas.put(tipo, horas);
		validaHoras();
		ParametrosModelo.obtenerInstancia().set_items_tipo_horas.add(tipo);
		return true;
	}
	
	private boolean validaHoras() throws Exception {
		if (this._horas==null)
			this._horas=new HashMap<String, Double>();
			
		Double sumaHorasTotal=this.obtenerSumaTotalHoras();
		Double sumaHorasDelITEM_TIPO_HORA_DEFECTO=this.obtenerSumaHorasDelTipo(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES);
		Double sumaHorasSinITEM_TIPO_HORA_DEFECTO=sumaHorasTotal-sumaHorasDelITEM_TIPO_HORA_DEFECTO;
		
		if (sumaHorasTotal==ParametrosModelo.HORAS_TOTAL)
			return true;
		else if (sumaHorasTotal>ParametrosModelo.HORAS_TOTAL){
			//Puede pasar dos cosas:
			// sumaHorasSinITEM_TIPO_HORA_DEFECTO se puede reducir hasta igualarlo a Configuracion.HORAS_TOTAL
			// aunque sumaHorasSinITEM_TIPO_HORA_DEFECTO sea 0, se excedan horas
			Double diferencia=ParametrosModelo.HORAS_TOTAL-sumaHorasSinITEM_TIPO_HORA_DEFECTO;
			if (diferencia>0){
				_horas.remove(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES);
				_horas.put(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES, diferencia);
				return true;
			}else{
				throw new Exception("Fecha invalida: " + this.hashCode());
			}
		}else if (sumaHorasTotal<ParametrosModelo.HORAS_TOTAL){
			Double diferencia=ParametrosModelo.HORAS_TOTAL-sumaHorasSinITEM_TIPO_HORA_DEFECTO;
			_horas.remove(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES);
			_horas.put(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES, diferencia);
			return true;
		}
		return false;
	}
	


	public Double obtenerSumaTotalHoras() {
		double res = 0;
		for (Double d : _horas.values())
			res = res + d;
		return res;
	}

	public Double obtenerSumaHorasDelTipo(String tipo) {
		Double temp=_horas.get(tipo);
		double res=0;
		if (temp==null)
			res=0;
		else
			res=temp.doubleValue();
		return res;
	}
	

	@Override
	/**
	 * Ejemplo:
	 * EXTRA=1.0;INVIERNO=8.0
	 * o vacio
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
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

	public void eliminarHoras(String tipoHora) {
		_horas.remove(tipoHora);
		try {
			validaHoras();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
