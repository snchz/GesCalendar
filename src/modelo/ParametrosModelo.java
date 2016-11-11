package modelo;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class ParametrosModelo {
	public static String MENU_SALIR = "Salir";
	public static String MENU_SALIR_DESC = "Salir de la aplicacion";
	public static String APLICACION = "GesCalendario";
	public static String MENU_ARCHICHO_IMPORTAR = "Importar...";
	public static String MENU_ARCHICHO_IMPORTARDEFECTO = "Importar desde .gcd";
	public static String MENU_ARCHIVO = "Archivo";

	public static Color FONDO_VCALENDARIO=Color.BLUE;
	public static Color FONDO_VDIA=Color.CYAN;
	public static Color FONDO_VLISTAHORAS=Color.GREEN;
	public static Color FONDO_VPRINCIPAL=Color.ORANGE;

	public static String ITEM_TIPO_HORAS_RESTANTES = "RESTANTE";
	public static Double HORAS_TOTAL = 24.0;
	public static Double HORAS_DEFECTO = 0.0;

	public Set<String> set_items_tipo_horas = null;

	private ParametrosModelo() {
		set_items_tipo_horas = new HashSet<String>();
		set_items_tipo_horas.add(ParametrosModelo.ITEM_TIPO_HORAS_RESTANTES);
	}

	private static ParametrosModelo _instancia = null;

	public static ParametrosModelo obtenerInstancia() {
		if (_instancia == null) {
			_instancia = new ParametrosModelo();
		}
		return _instancia;
	}
}
