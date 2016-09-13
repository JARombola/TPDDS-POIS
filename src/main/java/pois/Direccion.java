package pois;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Direccion {

	@Id @GeneratedValue
	private int id;
	
	private String calle;

	private String[] callesEntreLasQueSeEncuentra = new String[2];;
	private int numero;
	private int piso;
	private int dpto;
	private String unidad;
	private int codigoPostal;
	private String localidad;
	private String barrio;
	private String provincia;
	private String pais;
	
	@Embedded
	private Coordenadas coordenadas;

	public Direccion() {
		coordenadas = new Coordenadas();
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


	public String[] getCallesEntreLasQueSeEncuentra() {
		return callesEntreLasQueSeEncuentra;
	}

	public int getPiso() {
		return piso;
	}

	public int getDpto() {
		return dpto;
	}

	public String getUnidad() {
		return unidad;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public String getBarrio() {
		return barrio;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getPais() {
		return pais;
	}
	

}