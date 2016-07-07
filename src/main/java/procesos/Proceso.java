package procesos;

import java.util.TimerTask;

import configuracionTerminales.Administrador;
import principal.Terminales.Mapa;

public abstract class Proceso extends TimerTask  {
	Mapa mapa;
	Administrador admin;
	ControlProcesos controladorProcesos;
	EstadoEjecucion estadoDeEjecucion = null;
	
	public Proceso(Administrador admin){
		mapa = admin.getTerminal().getMapa();
		controladorProcesos = admin.getControlador();
		this.admin = admin;
	}

	public Proceso(){
	}
	
	public void fallar() {
		this.estadoDeEjecucion = EstadoEjecucion.falla(this);
	}
	
	public void ejecucionExitosa() {
		this.estadoDeEjecucion = EstadoEjecucion.exitoso(this);
	}

	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
	}
	
	public void run() {
		try{
			this.ejecutar();	
		}
		catch( Exception e) {
			this.fallar();
			this.controladorProcesos.manejarFallas(this);
		}
		
	}

	private void ejecutar() throws Exception {
		this.ejecutarProceso();
		this.ejecucionExitosa();
	}

	void ejecutarProceso() throws Exception{
		// se overridea
	}

}
