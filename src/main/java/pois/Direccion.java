package pois;

public class Direccion {

	public Direccion() {
	}

	private String calle;


	private String[] callesEntreLasQueSeEncuentra;
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
	
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	

}