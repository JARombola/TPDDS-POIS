package tiposPoi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rubro {
	@Id @GeneratedValue
	private int id;
	private double radioCercania;
	public String nombre;

	public Rubro(){
	}
	
	// -------------------GETTERS,SETTERS-----------------
	public void setRadioCercania(double radioCercania) {
		this.radioCercania = radioCercania;
	}

	public double getRadioCercania() {
		return radioCercania;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean tienePalabra(String palabra){
		return (getNombre().contains(palabra));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
