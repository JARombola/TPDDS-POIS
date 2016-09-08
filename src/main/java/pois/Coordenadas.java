package pois;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Coordenadas {
	@Id @GeneratedValue
	private int id;
	private double latitud;
	private double longitud;

	// -------------------GETTERS,SETTERS-----------------

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
