package procesos;

import java.util.Date;
import java.util.Timer;

import principal.Terminales.Mapa;


public class ControlProcesos {
	private Mapa mapa;
	private ManejoDeResultadosProcesos manejoResultados;
	Timer timer = new Timer();
	
	public ControlProcesos() {
	}
	
	public void agregarProceso(Proceso procesoNuevo, Date date){
		procesoNuevo.setMapa(mapa);
		timer.schedule(procesoNuevo, date);
	}


	public void manejarFallas(Proceso proceso) {
		manejoResultados.manejarError(proceso);
	}
	
	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
	}
	
	public void setTimer(Timer timer){
		this.timer = timer;
	}


}
