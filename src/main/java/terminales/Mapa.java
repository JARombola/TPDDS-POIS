package terminales;


import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import pois.POI;


public class Mapa implements WithGlobalEntityManager,TransactionalOps {
	private static Mapa instancia;

	public Mapa() {
	}
	
	public static Mapa getInstancia(){				//Creo que no es necesario ....
		if (instancia==null){
			instancia = new Mapa();
		}
		return instancia;
	}
	
	public static void removeInstancia(){
		instancia = null;
	}

	// -------------------ABM POIS-----------------------
		
	public void eliminarPOI (int id){
		withTransaction(()->{
			POI poiEliminar = entityManager().find(POI.class, id);
			if(poiEliminar!=null){
				entityManager().remove(poiEliminar);
			}
		});
	}
	
	public void agregarOmodificar (POI poiEntrante){
		withTransaction(()->{
			POI mismoPoiEnSistema = (POI)entityManager().find(POI.class,poiEntrante.getId());
	
			if(mismoPoiEnSistema!=null){
	 			mismoPoiEnSistema.modificar(poiEntrante);
	 			entityManager().merge(mismoPoiEnSistema);
	 		} else {
	 			entityManager().persist(poiEntrante);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<POI> buscarPoi(String palabra){								//NO encontre forma mas linda de hacerlo sin las 2 querys, perdon (?
		entityManager().clear();
		List<POI> resultadosPorNombre = (List<POI>) entityManager().createQuery("from POI a WHERE (a.nombre like :palabra)")
				.setParameter("palabra", "%"+palabra+"%")
				.getResultList();
		
		List<POI> resultadosPorTags = (List<POI>) entityManager()
				.createQuery("from POI p where (:palabra MEMBER OF p.listaServicios.servicios.tags) OR (:palabra MEMBER OF p.tags)")
				.setParameter("palabra", palabra)
				.getResultList();
		resultadosPorNombre.addAll(resultadosPorTags);
		return resultadosPorNombre;
	}
	
	public POI getPOI(String nombre){	
		entityManager().clear();
		return (POI)entityManager().createQuery("from POI where nombre = :nombre")
				.setParameter("nombre", nombre)
				.getSingleResult();
	}
	public POI getPOI(int id){
		entityManager().clear();
		return (POI)entityManager().createQuery("from POI where id = :id")
				.setParameter("id", id)
				.getSingleResult();		
	}	
	
	@SuppressWarnings("unchecked")
	public List<POI> filtrarTipo(String tipo){
		entityManager().clear();
		String consulta = "from " + tipo;
		List<POI> resultados= (List<POI>) entityManager().createQuery(consulta)
				.getResultList();
		entityManager().clear();
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public List<POI> filtrarNombre(String nombreBuscado){
		entityManager().clear();
		List<POI> resultados= (List<POI>) entityManager().createQuery("from POI p WHERE p.nombre=:nombreBuscado")
				.setParameter("nombreBuscado", nombreBuscado)
				.getResultList();
		entityManager().clear();
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public List<POI> todos(){
		entityManager().clear();
		List<POI> pois = entityManager().createQuery("from POI").getResultList();
		return pois;
	}
	

	
}
