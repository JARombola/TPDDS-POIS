package tiposPoi;

import org.joda.time.LocalTime;

import pois.POI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ParadaColectivo extends POI{
	@Id @GeneratedValue
	private int id;
	@Transient
	double radioCercania=0.1;
	
	public ParadaColectivo() {

	}
	
	//------------------------DISPONIBILIDAD------------------
	public boolean estaDisponible(int dia, LocalTime hora, String palabra) {
		return true;
	}
	
	public boolean tienePalabra(String palabra) {
		return (getNombre().contains(palabra) || this.getTags().contains(palabra));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
