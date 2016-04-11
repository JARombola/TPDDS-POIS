package principal;

public class Maquina {
	private double latitud;
	private double longitud;
	private int comuna;
	
	public Maquina(double latitud, double longitud){
		this.latitud = latitud;
		this.longitud = longitud;
	}

	// -------------------GETTERS,SETTERS-----------------
	public double getLongitud() {
		return longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public int getComuna() {
		return comuna;
	}

	
	public void setComuna(int comuna) {
		this.comuna = comuna;
	}
	
}
