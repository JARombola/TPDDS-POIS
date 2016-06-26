package procesos;

import java.sql.Date;
import java.util.List;
import java.util.TimerTask;

import principal.Terminales.Mapa;

public abstract class Proceso extends TimerTask {
	Mapa mapa;
	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
	}

	

}
