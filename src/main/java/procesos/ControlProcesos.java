package procesos;

import java.sql.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import principal.Terminales.Mapa;


public class ControlProcesos {
	private Mapa mapa;
	private ManejoDeResultadosProcesos manejoResultados;
	Timer timer;
	
	public ControlProcesos() {
	}
	
	public void agregarProceso(Proceso procesoNuevo, java.util.Date date){
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
