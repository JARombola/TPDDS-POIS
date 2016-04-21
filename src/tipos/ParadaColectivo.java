package tipos;

import principal.POI;

public class ParadaColectivo extends POI implements Disponibilidad{

	public ParadaColectivo() {
		radioCercania = 0.1;
	}
	
	//------------------------DISPONIBILIDAD------------------
	public boolean estaDisponible(int dia, String hora, String palabra) {
		return true;
	}
}
