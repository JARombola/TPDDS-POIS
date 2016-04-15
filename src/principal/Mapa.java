package principal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mapa {

	List<POI> pois;

	public Mapa() {
		pois = new ArrayList<POI>();
	}

	public List<POI> getPOI() {
		return pois;
	}

	public void setPOI(POI poi) {
		this.pois.add(poi);
	}

	public void agregarPOI(POI unPOI) {
		this.pois.add(unPOI);
	}

	public void Buscar(String texto) {

	}
}
