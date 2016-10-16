package terminales;


import java.util.List;

import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import pois.POI;


public class Mapa  implements WithGlobalEntityManager {
	private static Mapa instancia;

	public Mapa() {
	}
	
	public static Mapa getInstancia(){
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
		POI poiEliminar = entityManager().find(POI.class, id);
		if(poiEliminar!=null){
			if(!entityManager().getTransaction().isActive())entityManager().getTransaction().begin();
			entityManager().remove(poiEliminar);
			entityManager().getTransaction().commit();
		//	System.out.println("POI ELIMINADO");			//TODO Borrar!
		}
	}
	
	public void agregarOmodificar (POI poiEntrante){
		POI mismoPoiEnSistema = (POI)entityManager().find(POI.class,poiEntrante.getId());
		EntityTransaction tx = entityManager().getTransaction();
		if(!entityManager().getTransaction().isActive()) tx.begin();	//TODO

		if(mismoPoiEnSistema!=null){
		//	System.out.println("___________POI MODIFICADO!");
 			mismoPoiEnSistema.modificar(poiEntrante);
 			entityManager().merge(mismoPoiEnSistema);
 		} else {
 		//	System.out.println(">>>>>POI REGISTRADO!");
 			entityManager().persist(poiEntrante);
		}
		entityManager().flush();
 		tx.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<POI> buscarPoi(String palabra){								//NO encontre forma mas linda de hacerlo sin las 2 querys, perdon (?
		List<POI> resultadosPorNombre = (List<POI>) entityManager().createQuery("from POI a WHERE (a.nombre like :palabra)")
				.setParameter("palabra", "%"+palabra+"%")
				.getResultList();
		List<POI> resultadosPorTags = (List<POI>) entityManager()
				.createQuery("from POI p where (:palabra MEMBER OF p.listaServicios.servicios.tags) OR (:palabra MEMBER OF p.tags)")
				.setParameter("palabra", palabra)
				.getResultList();
		resultadosPorNombre.addAll(resultadosPorTags);
		//entityManager().clear();
		return resultadosPorNombre;
	}
	
	public POI getPOI(String nombre){		
		return (POI)entityManager().createQuery("from POI where nombre = :nombre")
				.setParameter("nombre", nombre)
				.getSingleResult();
	}
	public POI getPOI(int id){
		return (POI)entityManager().createQuery("from POI where id = :id")
				.setParameter("id", id).getSingleResult();		
	}	
	
}
