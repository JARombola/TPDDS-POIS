package procesos;


import configuracionTerminales.Administrador;
import principal.POIS.POI;

public class ProcesoBajaPOIs extends Proceso{
	private POI poi;

	public ProcesoBajaPOIs(int id) {
		this.poi = mapa.getPOI(id);
	}

	public void run() {
		try{
			mapa.eliminarPOI(poi);
		}
		catch( Exception e) {
			//Aca supongo que es mejor con burbujeo, no que el Proceso conozca a su controlador 
		}
		

	}
}
