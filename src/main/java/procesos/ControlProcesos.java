package procesos;

import java.util.Date;
import java.util.Timer;

import principal.Terminales.Mapa;


public class ControlProcesos {
	private Mapa mapa;
	private ManejoDeResultadosProcesos manejoResultados;
	Timer timer = new Timer();
	
	
	public ControlProcesos() {
		manejoResultados = new ManejoDeResultadosProcesos();
	}
	
	public void agregarProceso(Proceso procesoNuevo, Date date, int cantidadReintentos){
		procesoNuevo.setMapa(mapa);
		procesoNuevo.setReintentos(cantidadReintentos);
		procesoNuevo.setFecha(date);
		timer.schedule(procesoNuevo, date);
	}

	
	
	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
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


}
