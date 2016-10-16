package configuracionTerminales;

import java.util.HashMap;
import java.util.Map;

import terminales.Busqueda;
import terminales.Terminal;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Transient;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import procesos.ExcepcionFalloConfiguracion;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import com.mongodb.MongoClient;


@Entity
public class FuncionesExtra implements WithGlobalEntityManager {
	
	@Id @GeneratedValue
	private int id;
	private int tiempoMax;
	@ElementCollection
	@MapKeyJoinColumn
	private Map<String,adapterBooleano> opciones;		//Usa el map para activar/desactivar las opciones... :)
	@Transient
	private Terminal terminal;
	@Transient
	Datastore store;
	@Transient
	Morphia morphia;
	@Transient	
	MongoClient mongo;

	public FuncionesExtra(){
	
	}
	
	public FuncionesExtra(int tiempoMax){
		this.tiempoMax=tiempoMax;
		opciones=new HashMap<String,adapterBooleano>();
		opciones.put("HISTORIAL", new adapterBooleano(false));
		opciones.put("MAIL", new adapterBooleano(false));
	}
	
	public void inicioBusqueda(){
		TiempoEjecucion.Start();
	}
	
	public void finBusqueda(Busqueda datosBusqueda){
		TiempoEjecucion.Stop();
		double tiempoBusqueda= TiempoEjecucion.getTiempoEjecucion();
		datosBusqueda.setTiempoBusqueda(tiempoBusqueda);
		this.enviarMail(tiempoBusqueda);
		this.guardarBusqueda(datosBusqueda);
	}
	
	private void guardarBusqueda(Busqueda datosBusqueda) {  
		if (getOpciones().get("HISTORIAL").isActivado()){
			terminal.guardarBusquedas(datosBusqueda);
			persistirRelacional(datosBusqueda);
			persistirMongo(datosBusqueda);
			
		}
	}
	
	private void persistirRelacional(Busqueda datosBusqueda){
		EntityTransaction tx = entityManager().getTransaction(); //TODO 
		if (!entityManager().getTransaction().isActive()) 
			tx.begin();
		entityManager().merge(datosBusqueda);
		tx.commit();	//TODO
	//	entityManager().clear();
	}
	
	private void persistirMongo(Busqueda datosBusqueda){
		morphia = new Morphia();
		morphia.getMapper().getOptions().setStoreNulls(true);
		morphia.mapPackage("pois");
		morphia.mapPackage("tiposPoi");
		morphia.mapPackage("terminales");
		morphia.mapPackage("configuracionTerminales");
		morphia.mapPackage("terminales");
		mongo = new MongoClient();
		store = morphia.createDatastore(mongo, "Busquedas");
		int a = (int)store.getCount(Busqueda.class);
		datosBusqueda.setId(a+2);
		System.out.println(a);
		if(!datosBusqueda.resultados.isEmpty())System.out.println(datosBusqueda.resultados.get(0));
		store.save(datosBusqueda);
	}

	private void enviarMail(double tiempoBusqueda){
		if (getOpciones().get("MAIL").isActivado() && (tiempoBusqueda>tiempoMax)){		//activado el mail, y el tiempo se excedi�
			EnviadorMails mail=new EnviadorMails(terminal.getAdministrador());
			mail.mailBusquedaLenta();
		}
	}

	public Map<String, adapterBooleano> getOpciones() {
			return opciones;
	}	
	
	public void activarOpcion(String opcion){
		if(opciones.get(opcion)!=null){
			opciones.replace(opcion.toUpperCase(), new adapterBooleano(true));
		}
			else{throw new ExcepcionFalloConfiguracion(getTerminal());}
	}
	
	public void desactivarOpcion(String opcion){
		if(opciones.get(opcion)!=null){
			opciones.replace(opcion.toUpperCase(), new adapterBooleano(false));}
		else{throw new ExcepcionFalloConfiguracion(getTerminal());}
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	public int getTiempoMax() {
		return tiempoMax;
	}
	public boolean estaActivado(String opcion) {
		return (opciones.get(opcion).isActivado());
	}
}
