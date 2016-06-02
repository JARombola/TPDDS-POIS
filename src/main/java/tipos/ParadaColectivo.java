package tipos;

import org.joda.time.LocalTime;

import principal.POI;

public class ParadaColectivo extends POI{

	public ParadaColectivo() {
		radioCercania = 0.1;
	}
	
	//------------------------DISPONIBILIDAD------------------
	public boolean estaDisponible(int dia, LocalTime hora, String palabra) {
		return true;
	}
	
	public boolean tienePalabra(String palabra) {
		
		return (getNombre().contains(palabra));
	}
	
	/*
	public boolean estaDisponible(int dia, LocalTime hora) {
		return true;
	}*/
}
