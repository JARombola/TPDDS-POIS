package configuracionTerminales;

import java.util.HashMap;
import java.util.Map;

import terminales.Busqueda;
import terminales.Terminal;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Transient;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import procesos.ExcepcionFalloConfiguracion;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;


@Entity
public class FuncionesExtra extends AbstractPersistenceTest implements WithGlobalEntityManager {
	@Id @GeneratedValue
	private int id;
	private int tiempoMax;
	
	@ElementCollection
	@MapKeyJoinColumn
	private Map<String,adapterBooleano> opciones;		//Usa el map para activar/desactivar las opciones... :)
	@Transient
	private Terminal terminal;
	private Datastore store;

	public FuncionesExtra(){
	
	}
	
	public FuncionesExtra(int tiempoMax){
		this.tiempoMax=tiempoMax;
		opciones=new HashMap<String,adapterBooleano>();
		opciones.put("HISTORIAL", new adapterBooleano(false));
		opciones.put("MAIL", new adapterBooleano(false));

		Morphia morphia = new Morphia();
		morphia.mapPackage("terminales");
		MongoClient mongo = new MongoClient();
		store = morphia.createDatastore(mongo, "Busquedas");
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
			store.save(datosBusqueda);
			
			beginTransaction();
			persist(datosBusqueda);
			commitTransaction();
			
		}
		
	}

	private void enviarMail(double tiempoBusqueda){
		if (getOpciones().get("MAIL").isActivado() && tiempoBusqueda>tiempoMax){		//activado el mail, y el tiempo se excedi�
			EnviadorMails mail=new EnviadorMails(terminal.getAdministrador());
			mail.mailBusquedaLenta();
		}
	}

	public Map<String, adapterBooleano> getOpciones() {
			return opciones;
	}	
	
	public void activarOpcion(String opcion){
		if(opciones.get(opcion)!=null){opciones.put(opcion.toUpperCase(), new adapterBooleano(true));}
			else{throw new ExcepcionFalloConfiguracion(getTerminal());}
	}
	
	public void desactivarOpcion(String opcion){
		if(opciones.get(opcion)!=null){opciones.put(opcion.toUpperCase(), new adapterBooleano(false));}
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
