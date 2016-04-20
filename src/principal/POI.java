package principal;

import java.util.ArrayList;
import java.util.List;

public class POI{
	private EntesConHorarios horarios;
	private String nombre;
	private List<String> tags;
	
	public List<String> getTags() {
		return tags;
	}

	public void agregarTag(String tag) {
		this.tags.add(tag);
	}

	private Direccion direccion; 
	protected double radioCercania = 0.5; //Una cuadra = 0.1 Kms
	
	public POI (){
		this.direccion = new Direccion();
		this.horarios= new EntesConHorarios();
		this.tags=new ArrayList<String>();
	}
	


	//---------------BUSQUEDA-----------------------------------
	public boolean tienePalabra(String texto){
		return (getNombre().contains(texto) || getTags().contains(texto));
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
}
