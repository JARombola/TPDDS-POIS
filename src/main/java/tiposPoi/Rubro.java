package tiposPoi;

public class Rubro {
	
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
}
