package configuracionTerminales;

import java.util.HashMap;
import java.util.Map;

import terminales.Busqueda;
import terminales.RepositorioTerminales;
import terminales.LocalDateConverter;
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
	private Map<String,AdapterBooleano> opciones;		//Usa el map para activar/desactivar las opciones... :)
	@Transient
	private Terminal terminal;
	@Transient
	Datastore store;
	@Transient
	Morphia morphia;
	@Transient	
	MongoClient mongo;
	@Transient	
	private String pathMongoBusquedas;

	public FuncionesExtra(){
	
	}
	
	public FuncionesExtra(int tiempoMax){
		this.tiempoMax=tiempoMax;
		opciones=new HashMap<String,AdapterBooleano>();
		opciones.put("HISTORIAL", new AdapterBooleano(false));
		opciones.put("MAIL", new AdapterBooleano(false));
	}
	
	public void inicioBusqueda(){
		TiempoEjecucion.Start();
	}
	
	public void finBusqueda(Busqueda datosBusqueda){
		TiempoEjecucion.Stop();
		double tiempoBusqueda= TiempoEjecucion.getTiempoEjecucion();
		datosBusqueda.setTiempoBusqueda(tiempoBusqueda);
		enviarMail(tiempoBusqueda);
		guardarBusqueda(datosBusqueda);
	}
	
	public void guardarBusqueda(Busqueda datosBusqueda) {  
		if (getOpciones().get("HISTORIAL").isActivado()){
			persistirRelacional(datosBusqueda);
			persistirBusquedasMongo(datosBusqueda);
			
		}
	}
	
	private void persistirRelacional(Busqueda datosBusqueda){
		EntityTransaction tx = entityManager().getTransaction(); //TODO 
		if (!entityManager().getTransaction().isActive()) 
			tx.begin();
		entityManager().merge(datosBusqueda);
		tx.commit();	//TODO
	}
	
	public void persistirBusquedasMongo(Busqueda datosBusqueda){
		morphia = new Morphia();
		morphia.mapPackage("pois");
		morphia.mapPackage("tiposPoi");
		morphia.mapPackage("terminales");
		morphia.getMapper().getConverters().addConverter( new LocalDateConverter() );
		mongo = new MongoClient();
		String base = "Busquedas_"+terminal.getNombre();
		store = morphia.createDatastore(mongo, base);
		int a = (int)store.getCount(Busqueda.class);
		datosBusqueda.setId(a);
		store.save(datosBusqueda);
	}

	private void enviarMail(double tiempoBusqueda){
		if (getOpciones().get("MAIL").isActivado() && (tiempoBusqueda>tiempoMax)){		//activado el mail, y el tiempo se excedi�
			EnviadorMails mail=new EnviadorMails(terminal.getAdministrador());
			mail.mailBusquedaLenta();
		}
	}

	public Map<String, AdapterBooleano> getOpciones() {
			return opciones;
	}	
	
	public void activarOpcion(String opcion){
		if(opciones.get(opcion.toUpperCase())!=null){
			opciones.replace(opcion.toUpperCase(), new AdapterBooleano(true));
			RepositorioTerminales.getInstancia().actualizarTerminal(getTerminal());
		}
			else{throw new ExcepcionFalloConfiguracion(getTerminal());}
	}
	
	public void desactivarOpcion(String opcion){
		if(opciones.get(opcion.toUpperCase())!=null){
			opciones.replace(opcion.toUpperCase(), new AdapterBooleano(false));
			RepositorioTerminales.getInstancia().actualizarTerminal(getTerminal());
		}
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
		return (opciones.get(opcion.toUpperCase()).isActivado());
	}
}
