package procesos;

import configuracionTerminales.Administrador;
import principal.POIS.POI;
import principal.Terminales.Mapa;

public class ProcesoBajaPOIs extends Proceso{
	private POI poi;
	private Mapa mapa;
	
	public ProcesoBajaPOIs(int id, Administrador admin) {
		this.poi = admin.getTerminal().getPOI(id);
		mapa = admin.getTerminal().getMapa();
	}

	public void run() {
		mapa.eliminarPOI(poi);
	}



}
