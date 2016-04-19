package tipos;

import principal.POI;

public class Local extends POI {
	private Rubro rubro;

	public Local() {
	}

	//---------------BUSQUEDA-----------------------------------
	public boolean tienePalabra(String texto){
		 return (this.tienePalabraEnNombre(texto) || this.tienePalabraEnRubro(texto));
	}
		 
	public boolean tienePalabraEnRubro(String texto){
		return this.getRubro().getNombre().contains(texto);
	}
	
	// -------------------GETTERS,SETTERS-----------------
	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public double getRadioCercania() {
		return rubro.getRadioCercania();
	}
}
