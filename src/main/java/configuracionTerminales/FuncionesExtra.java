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


@Entity
public class FuncionesExtra {
	@Id @GeneratedValue
	private int id;
	private int tiempoMax;
	
	@ElementCollection
	@MapKeyJoinColumn
	private Map<String,adapterBooleano> opciones;		//Usa el map para activar/desactivar las opciones... :)
	@Transient
	private Terminal terminal;

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
		}
		
	}

	private void enviarMail(double tiempoBusqueda){
		if (getOpciones().get("MAIL").isActivado() && tiempoBusqueda>tiempoMax){		//activado el mail, y el tiempo se excedió
			EnviadorMails mail=new EnviadorMails(terminal.getAdministrador());
			mail.mailBusquedaLenta();
		}
	}

	public Map<String, adapterBooleano> getOpciones() {
			return opciones;
	}	
	
	public void activarOpcion(String opcion) throws Exception{
		if(opciones.get(opcion)!=null){opciones.put(opcion.toUpperCase(), new adapterBooleano(true));}
		else{throw new Exception("Opcion incorrecta");}
	}
	
	public void desactivarOpcion(String opcion){
		opciones.put(opcion.toUpperCase(), new adapterBooleano(false));
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
