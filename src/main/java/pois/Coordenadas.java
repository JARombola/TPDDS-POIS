package pois;

import javax.persistence.Embeddable;


@org.mongodb.morphia.annotations.Embedded
@Embeddable
public class Coordenadas {
	
	private double latitud;
	private double longitud;
	
	public Coordenadas(){
		
	}
	
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
	

}
