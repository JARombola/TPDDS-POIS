package principal;

public class Direccion {

	public Direccion() {
		coordenadas = new Coordenadas();
	}

	private String calle;
	private String[] callesEntreLasQueSeEncuentra = new String[2];
	private int numero;
	private int piso;
	private int dpto;
	private String unidad;
	private int codigoPostal;
	private String localidad;
	private String barrio;
	private String provincia;
	private String pais;
	private Coordenadas coordenadas;

	// -------------------GETTERS,SETTERS-----------------

	public double getLatitud() {
		return coordenadas.getLatitud();
	}

	public void setLatitud(double latitud) {
		coordenadas.setLatitud(latitud);
	}

	public double getLongitud() {
		return coordenadas.getLongitud();
	}

	public void setLongitud(double longitud) {
		coordenadas.setLongitud(longitud);
	}

}