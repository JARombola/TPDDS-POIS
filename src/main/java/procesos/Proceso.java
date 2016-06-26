package procesos;

import java.util.TimerTask;

import configuracionTerminales.Administrador;
import principal.Terminales.Mapa;

public abstract class Proceso extends TimerTask {
	Mapa mapa;
	ControlProcesos controladorProcesos;
	
	public Proceso(Administrador admin){
		mapa = admin.getTerminal().getMapa();
		controladorProcesos = admin.getControlador();
	}

	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
	}

}
