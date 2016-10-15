package terminales;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import pois.POI;
import tiposPoi.Banco;
import tiposPoi.CGP;
import tiposPoi.Local;
import tiposPoi.ParadaColectivo;


public class Mapa  implements WithGlobalEntityManager {
	private List<POI> pois;
	private static Mapa instancia;

	public Mapa() {
		pois = new ArrayList<POI>();
		
	}
	
	@SuppressWarnings("unchecked")
	public void cargarPOIS(){
//		TODO: No está bueno que carguen todos los pois en memoria, 
//		piensen que si fuesen mucho esto sería un problema ya que no daría a basto 
//		la memoria de la máquina virtual. Entiendo que hicieron esto para no cambiar tanto la 
//		implementación, pero no sería una implementación correcta. - Aldana.
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
//	TODO: Esto quedaría obsoleto por 'agregarOModificar' no? - Aldana
	public void registrarPOI(String tipo) throws Exception{
		POI puntoNuevo=crearPoi(tipo);
		setPOI(puntoNuevo);
	}
	
	public POI crearPoi(String tipo) throws Exception {
//		TODO: Esto no sé cuándo lo hicieron pero no está bueno que tengan este type test para instanciar
//		un tipo de poi cuando directamente podrían instanciar la instancia en donde sea que esto fuese necesario.
//		No respeta el paradigma, lo que lo hace propenso a errores. Piensen que, por ejemplo
//		de esta forma no puedo aprovechar las ventajas del chequeo de tipos estático del lenguaje. - Aldana
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
//		TODO: Esto no es correcto. No mantengan la una lista en memoria y además persistido en la base, sólo deberían
//		interactuar con la base. - Aldana
		boolean existia = getListaPOIS().removeIf(poi->poi.getId()==id);	//lo borra de la lista del mapa
		if (existia){
			entityManager().remove(entityManager().find(POI.class, id));					//lo borra de la base
		}
	}
	
	public void agregarOmodificar (POI poiEntrante){
//			TODO: Nuevamente no mantengan la lista en memoria. - Aldana
		List<POI> mismoPoiEnSistema = pois.stream()
									.filter(poi->poi.equals(poiEntrante))
									.collect(Collectors.toList());
		
		EntityTransaction tx = entityManager().getTransaction();
		if(!entityManager().getTransaction().isActive()) tx.begin();	//TODO
 		if(mismoPoiEnSistema.size()>=1){
 			mismoPoiEnSistema.get(0).modificar(poiEntrante);
 			entityManager().persist(mismoPoiEnSistema.get(0));
 		} else {
 			pois.add(poiEntrante);
 			entityManager().persist(poiEntrante);
		}
 		tx.commit();
	}
	
	public POI getPOI(String nombre){		
		return (POI)entityManager().createQuery("from POI where nombre = :nombre")
				.setParameter("nombre", nombre).getSingleResult();
	}
	public POI getPOI(int id){
		return (POI)entityManager().createQuery("from POI where id = :id")
				.setParameter("id", id).getSingleResult();		
	}	
	
	
	public List<POI> getListaPOIS(){
		return pois;
	}
	
	public void setPOI(POI poi) {
//		TODO: No sé dónde llaman a esto, pero nuevamente. No usen la lista en memoria.
//		Si el método no se usa más lo borran, y sino modifíquenlo para que interactúe con la base. - Aldana
		pois.add(poi);
	}
}
