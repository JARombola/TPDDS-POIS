package tipos;

import java.util.List;

public class Rubro {
	private double radioCercania;
	public String nombre;

	public Rubro(String rubroNombre){
		this.nombre=rubroNombre;
	}
	
	private List<String> tags;
	
	public List<String> getTags() {
		return tags;
	}

	public void agregarTag(String tag) {
		this.tags.add(tag);
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
		return (getNombre().contains(palabra) || getTags().contains(palabra));
	}
}
