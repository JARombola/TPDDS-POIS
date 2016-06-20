package principal.POIS;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

import principal.Terminales.Maquina;

public abstract class POI{
	private EntesConHorarios horarios;
	private String nombre;
	private List<String> tags;
	
	private Direccion direccion; 
	private int id;

	protected double radioCercania = 0.5; //Una cuadra = 0.1 Kms
	
	
	public List<String> getTags() {
		return tags;
	}

	public void agregarTag(String tag) {
		this.tags.add(tag);
	}
	public void eliminarTags() {
		this.tags.clear();
	}
	
	public POI (){
		this.direccion = new Direccion();
		this.horarios= new EntesConHorarios();
		this.tags=new ArrayList<String>();
	}
	
	public boolean estaDisponible(int dia, LocalTime hora){
		return getHorarios().estaDisponible(dia,hora);		//le delega a EnteConHorarios
	}
	public abstract boolean estaDisponible(int dia, LocalTime hora, String palabra);
	
	public boolean equals(POI otroPoi){
		return (otroPoi.getId() == id);
	}
	
	public void modificar(POI poiEntrante){
		this.setNombre(poiEntrante.getNombre());
		if(poiEntrante.getDireccion().getCalle()!=null && !(poiEntrante.getDireccion().getCalle().isEmpty())){
			direccion.setCalle(poiEntrante.getDireccion().getCalle());
		}
		if(poiEntrante.getDireccion().getNumero()>0){
			direccion.setNumero(poiEntrante.getDireccion().getNumero());
		}
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
	public EntesConHorarios getHorarios() {
		return horarios;
	}

	public void mostrarDatos(){
		System.out.println("POI: "+this.getNombre());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
