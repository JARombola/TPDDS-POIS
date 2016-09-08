package pois;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Embeddable
public class Direccion {

	@Id @GeneratedValue
	int id;
	
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
	@JoinColumn
	private Coordenadas coordenadas;

	public Direccion() {
	}
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

	public void setCallesEntreLasQueSeEncuentra(String[] callesEntreLasQueSeEncuentra) {
		this.callesEntreLasQueSeEncuentra = callesEntreLasQueSeEncuentra;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public void setDpto(int dpto) {
		this.dpto = dpto;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	

}