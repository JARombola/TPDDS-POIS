package procesos;

import java.util.Date;
import java.util.Timer;

import terminales.Terminal;


public class ControlProcesos {
	private Terminal terminal;
	private ManejoDeResultadosProcesos manejoResultados;
	Timer timer = new Timer();
	
	public ControlProcesos() {
		manejoResultados = new ManejoDeResultadosProcesos();
	}
	
	public void agregarProceso(Proceso procesoNuevo, Date date, int cantidadReintentos){
		procesoNuevo.setTerminal(terminal);
		procesoNuevo.setReintentos(cantidadReintentos);
		procesoNuevo.setFecha(date);
		timer.schedule(procesoNuevo, date);
	}

	
	public void setTimer(Timer timer){
		this.timer = timer;
	}
	public ManejoDeResultadosProcesos getManejoResultados() {
		return manejoResultados;
	}
	public void setManejoResultados(ManejoDeResultadosProcesos manejoResultados) {
		this.manejoResultados = manejoResultados;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}


}
