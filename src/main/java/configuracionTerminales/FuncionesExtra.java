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
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
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
	@OneToOne @JoinColumn
	private Terminal terminal;
	@Transient
	Datastore store;
	@Transient
	Morphia morphia;
	@Transient	
	MongoClient mongo;
	@Transient	
	private String pathMongoBusquedas;
	
	private boolean mail;
	private boolean historial;

	public FuncionesExtra(){
	}
	
	public FuncionesExtra(int tiempoMax){
		this.tiempoMax=tiempoMax;
		mail=false;
		historial=false;
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
		if (isHistorial()){
			//persistirRelacional(datosBusqueda);
			persistirBusquedasMongo(datosBusqueda);
			
		}
	}
	
	public void persistirBusquedasMongo(Busqueda datosBusqueda){
		morphia = new Morphia();
		morphia.mapPackage("pois");
		morphia.mapPackage("tiposPoi");
		morphia.mapPackage("terminales");
		morphia.getMapper().getConverters().addConverter( new LocalDateConverter() );
		mongo = new MongoClient();
		String base = "B_"+terminal.getNombre();
		store = morphia.createDatastore(mongo, base);
		int a = (int)store.getCount(Busqueda.class);
		datosBusqueda.setId(a);
		store.save(datosBusqueda);
	}

	private void enviarMail(double tiempoBusqueda){
		if (isMail() && (tiempoBusqueda>tiempoMax)){		//activado el mail, y el tiempo se excedi�
			EnviadorMails mail=new EnviadorMails(terminal.getAdministrador());
			mail.mailBusquedaLenta();
		}
	}
	
	public void activarOpcion(String opcion){
		opcion=opcion.toUpperCase();
		if(opcion.equals("MAIL")){
			mail=true;
		}else if(opcion.equals("HISTORIAL")){
			historial=true;
		} else{throw new ExcepcionFalloConfiguracion(getTerminal());}
	}
	
	public void desactivarOpcion(String opcion){
		opcion=opcion.toUpperCase();
		if(opcion.equals("MAIL")){
			mail=false;
		}else if(opcion.equals("HISTORIAL")){
			historial=false;
		} else{throw new ExcepcionFalloConfiguracion(getTerminal());}
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

	public boolean isMail() {
		return mail;
	}

	public void setMail(boolean mail) {
		this.mail = mail;
	}

	public boolean isHistorial() {
		return historial;
	}

	public void setHistorial(boolean historial) {
		this.historial = historial;
	}
}
