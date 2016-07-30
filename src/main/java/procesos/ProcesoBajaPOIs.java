package procesos;


import configuracionTerminales.Administrador;
import principal.POIS.POI;

public class ProcesoBajaPOIs extends Proceso{
	private POI poi;

	public ProcesoBajaPOIs(int id, Administrador admin) {
		super(admin);
		this.poi = admin.getTerminal().getPOI(id);
	}
	
	public int ejecutarProceso() throws Exception {
		int resultado;
		if(poi != null){
			mapa.eliminarPOI(poi);	
			resultado=1;
		}else{
			resultado=0;
		}
		return resultado;
	}
	
}

