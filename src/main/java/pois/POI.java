package pois;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalTime;

import terminales.Maquina;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@org.mongodb.morphia.annotations.Embedded
public abstract class POI{
	
	//@org.mongodb.morphia.annotations.Id
	@Id @GeneratedValue
	private int id;
	
	@org.mongodb.morphia.annotations.Transient
	@OneToOne @Cascade(value=CascadeType.ALL)
	private ListaHorarios horarios;
	private String nombre;

	@org.mongodb.morphia.annotations.Embedded
	@ElementCollection
	private List<String> tags;
	
	@org.mongodb.morphia.annotations.Embedded
	@OneToOne @JoinColumn @Cascade(value=CascadeType.ALL)			//CASCADE => Cuando persiste el POI persiste la direccion (en vez de tener que persistirla por separado)
	private Direccion direccion; 
	
	@org.mongodb.morphia.annotations.Transient
	@Transient
	protected double radioCercania = 0.5; //Una cuadra = 0.1 Kms
		
	public POI (){
		direccion = new Direccion();
		horarios= new ListaHorarios();
		tags = new ArrayList<String>();
	}
	
	public abstract boolean estaDisponible(int dia, LocalTime hora, String palabra);
	
	public boolean estaDisponible(int dia, LocalTime hora){
		return getHorarios().estaDisponible(dia,hora);		//le delega a ListaHorarios
	}
	
	public boolean equals(POI otroPoi){
		return (otroPoi.getId() == id);
	}
	
	public void modificar(POI poiEntrante){
		this.setNombre(poiEntrante.getNombre());
		if(poiEntrante.getDireccion()!=null){
				if(poiEntrante.getDireccion().getCalle()!=null){
					direccion.setCalle(poiEntrante.getDireccion().getCalle());
				}
				if(poiEntrante.getDireccion().getNumero()>0){
					direccion.setNumero(poiEntrante.getDireccion().getNumero());
				}
		}
		tags = poiEntrante.getTags();
	}

	//---------------BUSQUEDA-----------------------------------
	public boolean tienePalabra(String texto){
		return (getNombre().contains(texto) || getTags().contains(texto)); //|| getDireccion().getCalle().contains(texto));
	}

	//-----------------CERCANIA---------------------------------------------------

	public double distanciaAOtroPunto(Coordenadas coordenadas){ //Sirve para la distancia entre POIs o distancia con la maquina que se esta usando
		double distanciaHorizontal;
		double distanciaVertical;
		double LatPuntoAnguloRecto = direccion.getLatitud();
		double LongPuntoAnguloRecto = direccion.getLongitud();
		
		distanciaHorizontal = Haversine.distance(direccion.getLatitud(), direccion.getLongitud(), LatPuntoAnguloRecto, LongPuntoAnguloRecto);
		distanciaVertical = Haversine.distance( coordenadas.getLatitud(), coordenadas.getLongitud(), LatPuntoAnguloRecto, LongPuntoAnguloRecto);
		
		return(distanciaHorizontal + distanciaVertical); //Lo devuelve en Kms
	}
	
	public boolean estaCerca(Maquina puntoActual){
		double distancia = distanciaAOtroPunto(puntoActual.getCoordenadas());
		return (distancia <= this.getRadioCercania()); 
	}

	// -------------------GETTERS,SETTERS-----------------
	public List<String> getTags() {
		return tags;
	}
	public void agregarTag(String tag) {
		this.tags.add(tag);
	}
	public void eliminarTags() {
		this.tags.clear();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public void setLongitud(double longitud) {
		direccion.setLongitud(longitud);
	}

	public void setLatitud(double latitud) {
		direccion.setLatitud(latitud);
	}
	public double getRadioCercania() {
		return radioCercania;
	}
	public ListaHorarios getHorarios() {
		return horarios;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHorarios(ListaHorarios horarios) {
		this.horarios = horarios;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	

}
