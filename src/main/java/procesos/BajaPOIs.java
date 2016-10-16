package procesos;


import java.util.List;

import terminales.Mapa;

public class BajaPOIs extends Proceso{
	private List<Integer> IDPoisEliminar;
	

	public BajaPOIs(List<Integer> ids){
		this.setIDPoiEliminar(ids);
	}
	
	public int ejecutar() {
		Mapa mapa=Mapa.getInstancia();
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


