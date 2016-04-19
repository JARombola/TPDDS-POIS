package tipos;

import principal.POI;

public class ParadaColectivo extends POI {

	public ParadaColectivo() {
		radioCercania = 0.1;
	}
	
	//------------------------DISPONIBILIDAD------------------
	public boolean estaDisponible() {
		return true;
	}
}
