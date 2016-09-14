package configuracionTerminales;

import java.util.HashMap;
import java.util.Map;

import terminales.Busqueda;
import terminales.Terminal;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
public class FuncionesExtra {
	@Id @GeneratedValue
	private int id;
	private int tiempoMax;
	@Transient
	private Map<String,Boolean> opciones;		//Usa el map para activar/desactivar las opciones... :)
	@Transient
		private Terminal terminal;

		public FuncionesExtra(){
		
		}
	public FuncionesExtra(int tiempoMax){
		this.tiempoMax=tiempoMax;
		opciones=new HashMap<String,Boolean>();
		opciones.put("HISTORIAL", false);
		opciones.put("MAIL", false);
	}
	
	public void inicioBusqueda(){
		TiempoEjecucion.Start();
	}
	
	public void finBusqueda(Busqueda datosBusqueda){
		TiempoEjecucion.Stop();
		double tiempoBusqueda= TiempoEjecucion.getTiempoEjecucion();
		datosBusqueda.setTiempoBusqueda(tiempoBusqueda);
		this.enviarMail(tiempoBusqueda);
		guardarBusqueda(datosBusqueda);
	}
	
	private void guardarBusqueda(Busqueda datosBusqueda) {
		if (getOpciones().get("HISTORIAL")){
			terminal.guardarBusquedas(datosBusqueda);
		}
		
	}

	private void enviarMail(double tiempoBusqueda){
		if (getOpciones().get("MAIL") && tiempoBusqueda>tiempoMax){		//activado el mail, y el tiempo se excedió
			EnviadorMails mail=new EnviadorMails();
			mail.mailBusquedaLenta();
		}
	}

	public Map<String, Boolean> getOpciones() {
			return opciones;
	}	
	
	public void activarOpcion(String opcion){
		opciones.put(opcion.toUpperCase(), true);
	}
	
	public void desactivarOpcion(String opcion){
		opciones.put(opcion.toUpperCase(), false);
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
}
