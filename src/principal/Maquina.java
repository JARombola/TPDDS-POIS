package principal;

public class Maquina {
	private Coordenadas coordenadas;
	private int comuna;

	public Maquina() {
		coordenadas = new Coordenadas();
	}

	// -------------------GETTERS,SETTERS-----------------
	public double getLongitud() {
		return coordenadas.getLongitud();
	}

	public double getLatitud() {
		return coordenadas.getLatitud();
	}

	public void setLatitud(double latitud) {
		coordenadas.setLatitud(latitud);
	}

	public void setLongitud(double longitud) {
		coordenadas.setLongitud(longitud);
	}

	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;
	}

}
