package principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import externos.OrigenDatos;
import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;


public class Mapa {

	static List<POI> pois;
	List<OrigenDatos> origenesDatos;

	public Mapa() {
		pois = new ArrayList<POI>();
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
		pois.add(poi);
	}
	// -------------------ABM POIS-----------------------
	public void registrarPOI(String tipo){
		POI puntoNuevo=crearPoi(tipo);
		setPOI(puntoNuevo);
	}
	
	public POI crearPoi(String tipo) {
		Map<String,POI>map=new HashMap<String,POI>();
		map.put("Banco", new Banco());
		map.put("Parada", new ParadaColectivo());
		map.put("Local", new Local());
		map.put("CGP",new CGP());
		return map.get(tipo);
	}
	

	
	
}
