package terminales;

import pois.Comuna;
import pois.Coordenadas;

public class Maquina {
	private Coordenadas coordenadas;
	private Comuna comuna;

	
	public Maquina(double latitud, double longitud) {
		coordenadas = new Coordenadas();
		coordenadas.setLatitud(latitud);
		coordenadas.setLongitud(longitud);
	}

	// -------------------GETTERS,SETTERS-----------------
	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public Comuna getComuna() {
		return comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}


}