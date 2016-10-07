package terminales;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import pois.POI;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;


public class Mapa extends AbstractPersistenceTest implements WithGlobalEntityManager {
	
	private List<POI> pois;
	private static Mapa instancia;

	public Mapa() {
		pois = new ArrayList<POI>();
	}
	
	public void cargarPOIS(){
		pois = entityManager().createQuery("from POI").getResultList();
	}
	
	public static Mapa getInstancia(){
		if (instancia==null){
			instancia = new Mapa();
			instancia.cargarPOIS();
		}
		return instancia;
	}
	
	public static void removeInstancia(){
		instancia = null;
	}

	// -------------------ABM POIS-----------------------
	public void registrarPOI(String tipo) throws Exception{
		POI puntoNuevo=crearPoi(tipo);
		setPOI(puntoNuevo);
	}
	
	public POI crearPoi(String tipo) throws Exception {
		POI poiNuevo;
		switch (tipo.toUpperCase()){
			case "BANCO": poiNuevo = new Banco(); break;
			case "PARADA": poiNuevo = new ParadaColectivo(); break;
			case "LOCAL": poiNuevo= new Local(); break;
			case "CGP": poiNuevo = new CGP(); break;
			default: throw new Exception("Tipo de POI Incorrecto!");
		}
		return poiNuevo;
	}
		
	public void eliminarPOI (int id){
		boolean existia = getListaPOIS().removeIf(poi->poi.getId()==id);	//lo borra de la lista del mapa
		if (existia){
			remove(find(POI.class, id));					//lo borra de la base
		}
	}
	
	public void agregarOmodificar (POI poiEntrante){
		List<POI> mismoPoiEnSistema = pois.stream()
									.filter(poi->poi.equals(poiEntrante))
									.collect(Collectors.toList());
		beginTransaction();
 		if(mismoPoiEnSistema.size()>=1){
 			mismoPoiEnSistema.get(0).modificar(poiEntrante);
 			persist(mismoPoiEnSistema.get(0));
 		} else {
 			pois.add(poiEntrante);
 			persist(poiEntrante);
		}
 		//commitTransaction();
	}
	
	public POI getPOI(String nombre){		
		return pois.stream()
				.filter(poi->poi.getNombre().equals(nombre))
				.findFirst()
				.orElse(null);
	}
	
	public POI getPOI(int id){
		POI poiBuscado = pois.stream()
					.filter(poi->poi.getId() == id)
					.findFirst()
					.orElse(null);
		return poiBuscado;
		
	}	
	
	public List<POI> getListaPOIS(){
		return pois;
	}
	
	public void setPOI(POI poi) {
		pois.add(poi);
	}
}
