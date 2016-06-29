package procesos;

import java.util.TimerTask;

import principal.Terminales.Mapa;

public abstract class Proceso extends TimerTask  {
	Mapa mapa;

	public Proceso(){
	}

	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
	}

}
