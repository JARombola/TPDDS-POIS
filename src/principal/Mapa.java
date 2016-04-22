package principal;

import java.util.ArrayList;
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
		//System.out.println("Buscó: "+texto);
		List<POI> resultadosBusqueda;
		resultadosBusqueda = new ArrayList<POI>();
		resultadosBusqueda = getListaPOIS().stream().filter(poi->(poi.tienePalabra(texto))).collect(Collectors.toList());
		//resultadosBusqueda.forEach(asd->asd.mostrarDatos());
		return resultadosBusqueda;
	}

	// -------------------GETTERS,SETTERS-----------------
	
	public List<POI> getListaPOIS() {
		return pois;
	}

	public void setPOI(POI poi) {
		this.pois.add(poi);
	}

}
