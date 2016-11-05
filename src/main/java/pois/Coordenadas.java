package pois;

import javax.persistence.Embeddable;


@org.mongodb.morphia.annotations.Embedded
@Embeddable
public class Coordenadas {
	//@Column(nullable = true)
	private double latitud;
	//@Column(nullable = true)
	private double longitud;
	
	public Coordenadas(){
		latitud=10;
		longitud=0;
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
