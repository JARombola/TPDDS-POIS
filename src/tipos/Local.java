package tipos;

import principal.POI;

public class Local extends POI {
	private Rubro rubro;

	public Local() {
	}

	//---------------BUSQUEDA-----------------------------------
	public boolean tienePalabra(String texto){
		 return (super.tienePalabra(texto) || tienePalabraEnRubro(texto));
	}
		 
	public boolean tienePalabraEnRubro(String texto){
		return getRubro().tienePalabra(texto);
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
