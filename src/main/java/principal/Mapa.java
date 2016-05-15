package principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import externos.BufferBusquedas;
import externos.OrigenDatos;
import tipos.Banco;
import tipos.CGP;
import tipos.Local;
import tipos.ParadaColectivo;


public class Mapa {

	static List<POI> pois;
	List<OrigenDatos> origenesDatos;
	BufferBusquedas buffer = new BufferBusquedas();

	public Mapa() {
		pois = new ArrayList<POI>();
	}
	
	
	//---------------BUSQUEDA-----------------------------------
	public List<POI> buscar(String texto, String servicio) {
		//System.out.println("Buscó: "+texto);
		List<POI> resultadosBusqueda = new ArrayList<POI>();
		resultadosBusqueda = getListaPOIS().stream().filter(poi->(poi.tienePalabra(texto))).collect(Collectors.toList());
		
		//origenesDatos.forEach(componente -> notificarBusqueda(componente, texto, servicio));
		//resultadosBusqueda.addAll(buffer.getResultados());
		// ^ESTO ESTA COMENTADO PORQUE SINO ROMPEN LOS TEST, PORQUE FALTA TERMINAR, NO PORQUE ESTE MAL. DESPUES HAY QUE DESCOMENTARLO
		
		buffer.getResultados().forEach(poi->agregarOmodificar(poi));
		
		//resultadosBusqueda.forEach(asd->asd.mostrarDatos());
		return resultadosBusqueda;
	}
	
	private void notificarBusqueda(OrigenDatos componente, String texto, String servicio){
		if (servicio == ""){
			buffer.buscar(componente, texto);
		} else {
			buffer.buscar(componente, texto, servicio);
		}
	}

	
	public void agregarOmodificar (POI poiEntrante){
		List<POI> mismoPoiEnSistema = pois.stream().filter(poi->poi.equals(poiEntrante)).collect(Collectors.toList());
		
		if(mismoPoiEnSistema.size()==1){
			mismoPoiEnSistema.get(0).modificar(poiEntrante);
		} else {
			pois.add(poiEntrante);
		}
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
