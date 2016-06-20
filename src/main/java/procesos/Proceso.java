package procesos;

import principal.POIS.Horario;
import principal.Terminales.Mapa;

public interface Proceso {
	public void ejecutar();
	public void setFecha(Horario dia);
	public void setHorario(Horario horario);
	public void setMapa(Mapa mapa);

}
