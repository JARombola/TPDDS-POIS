package procesos;


import pois.POI;
import terminales.Terminal;

public class ProcesoBajaPOIs extends Proceso{
	private POI poi;

	public ProcesoBajaPOIs(int id, Terminal terminal) {
		super(terminal);
		this.poi = terminal.getPOI(id);
	}
	
	public int ejecutarProceso() throws Exception {
		int resultado;
		if(poi != null){
			terminal.getMapa().eliminarPOI(poi);	
			resultado=1;
		}else{
			resultado=0;
		}
		return resultado;
	}
	
}

