package procesos;


import configuracionTerminales.Administrador;
import principal.POIS.POI;

public class ProcesoBajaPOIs extends Proceso{
	private POI poi;

	public ProcesoBajaPOIs(int id, Administrador admin) {
		super(admin);
		this.poi = admin.getTerminal().getPOI(id);
	}
	
	public void ejecutarProceso() throws Exception {
		mapa.eliminarPOI(poi);	
	}
	
}

