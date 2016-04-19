package principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Mapa {

	List<POI> pois;

	public Mapa() {
		pois = new ArrayList<POI>();
	}
	
	public void agregarPOI(POI unPOI) {
		this.pois.add(unPOI);
	}

	//---------------BUSQUEDA-----------------------------------
	public List<POI> Buscar(String texto) {
	
		List<POI> resultadosBusqueda;
		resultadosBusqueda = new ArrayList<POI>();
		resultadosBusqueda = getPOI().stream().filter(poi->(poi.tienePalabra(texto))).collect(Collectors.toList());
		
		return resultadosBusqueda;
	}

	// -------------------GETTERS,SETTERS-----------------
	
	public List<POI> getPOI() {
		return pois;
	}

	public void setPOI(POI poi) {
		this.pois.add(poi);
	}

}
