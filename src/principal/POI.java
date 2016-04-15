package principal;

import java.util.ArrayList;
import java.util.List;

public class POI extends EntesConHorarios{
	private String nombre;
	private Direccion direccion; 
	protected double radioCercania = 0.5; //Una cuadra = 0.1 Kms
	
	public POI (){
		this.direccion = new Direccion();
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

	// -----------------------METODOS----------------------------------
	
	
	/*public void horarioNuevo(int dia, String horaInicio, String horaFin){
		Horario horarioNuevo=new Horario();
		horarioNuevo.setDia(dia);
		horarioNuevo.setInicio(horaInicio);
		horarioNuevo.setFin(horaFin);
		agregarHorario(horarioNuevo);
	}*/
	
	//----------------------CALCULO DISPONIBILIDAD (HORARIO)------------------
	
	/*
	public boolean estaDisponible(int dia, String hora){	//Fecha minima y maxima (Intervalo en que esta disponible el lugar).					// Si uso "hora" el lambda tira error (?) -.-
		boolean abierto=(horariosAtencion.stream()
						.filter(horario->(horario.getDia()==dia))		//Filtra los dias que coinciden con la fecha
						.anyMatch(horario->horario.estaAbierto(hora)));			//se fija si el horario coincide con los registrados
		return abierto;
	}
	
	public boolean estaDisponible(){
		Calendar diaActual=new GregorianCalendar();		//HOY
		int hoy=diaActual.get(Calendar.DAY_OF_WEEK);
		String hora = Integer.toString(diaActual.get(Calendar.HOUR_OF_DAY));
		int minutos=(diaActual.get(Calendar.MINUTE));
		if (minutos<10) {hora+=":0"+minutos;}
		else {hora+=":"+minutos;};				
		//TODO ESO PARA CONSEGUIR EL DIA Y LA HORA -.-
		return(estaDisponible(hoy,hora));
	}*/

	//-----------------CALCULO DISTANCIA---------------------------------------------------

	public double distanciaAOtroPunto(double latitudOtro, double longitudOtro){ //Sirve para la distancia entre POIs o distancia con la maquina que se esta usando
		double distanciaHorizontal;
		double distanciaVertical;
		double LatPuntoAnguloRecto = latitudOtro;
		double LongPuntoAnguloRecto = direccion.getLongitud();
		
		distanciaHorizontal = Haversine.distance(direccion.getLatitud(), direccion.getLongitud(), LatPuntoAnguloRecto, LongPuntoAnguloRecto);
		distanciaVertical = Haversine.distance(latitudOtro, longitudOtro, LatPuntoAnguloRecto, LongPuntoAnguloRecto);
		
		return(distanciaHorizontal + distanciaVertical); //Lo devuelve en Kms
	}
	
	public boolean estaCerca(Maquina puntoActual){
		double latitud = puntoActual.getLatitud();
		double longitud = puntoActual.getLongitud();
		double distancia = distanciaAOtroPunto(latitud,longitud);
		System.out.println("Distancia: "+distancia+ "  |  Radio: "+this.getRadioCercania());
		
		return (distancia <= this.getRadioCercania()); 
	}
}
