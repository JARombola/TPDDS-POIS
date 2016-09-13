package tiposPoi;

import org.joda.time.LocalTime;

import pois.POI;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class ParadaColectivo extends POI{
	
	@Transient
	private double radioCercania=0.1;
	
	public ParadaColectivo() {
	}
	
	//------------------------DISPONIBILIDAD------------------
	public boolean estaDisponible(int dia, LocalTime hora, String palabra) {
		return true;
	}
	
	public boolean tienePalabra(String palabra) {
		return (getNombre().contains(palabra) || this.getTags().contains(palabra));
	}

	public double getRadioCercania() {
		return this.radioCercania;
	}


}
