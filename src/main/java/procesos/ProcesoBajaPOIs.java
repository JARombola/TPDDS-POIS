package procesos;


import java.util.List;

import terminales.Mapa;

public class ProcesoBajaPOIs extends Proceso{
	private List<Integer> IDPoisEliminar;
	

	public ProcesoBajaPOIs(List<Integer> ids){
		this.setIDPoiEliminar(ids);
	}
	
	public int ejecutar() {
		Mapa mapa=Mapa.getInstance();
			IDPoisEliminar.forEach(poi-> mapa.eliminarPOI(poi));
		return IDPoisEliminar.size();
	}

	public List<Integer> getIDPoiEliminar() {
		return IDPoisEliminar;
	}

	public void setIDPoiEliminar(List<Integer> iDPoiEliminar) {
		IDPoisEliminar = iDPoiEliminar;
	}
	
}

