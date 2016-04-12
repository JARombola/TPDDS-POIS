package principal;

import java.util.ArrayList;
import java.util.List;

public class POI implements Horarios{
	private String nombre;
	private Direccion direccion; 
	private double longitud;
	private double latitud;
	List<Horario> horariosAtencion;
	private double radioCercania = 0.5; //Una cuadra = 0.1 Kms

	public List<Horario> getHorariosAtencion() {
		return horariosAtencion;
	}

	public void agregarHorario(Horario horarioNuevo) {	//Agrega un HORARIO a la lista de horarios
		this.horariosAtencion.add(horarioNuevo);
	}
	
	/*public void horarioNuevo(int dia, String horaInicio, String horaFin){
		Horario horarioNuevo=new Horario();
		horarioNuevo.setDia(dia);
		horarioNuevo.setInicio(horaInicio);
		horarioNuevo.setFin(horaFin);
		agregarHorario(horarioNuevo);
	}*/

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

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	
	public double getRadioCercania() {
		return radioCercania;
	}

	// -----------------------METODOS----------------------------------
	public POI() {
		horariosAtencion=new ArrayList<Horario>(); 
	}
	
	//----------------------CALCULO DISPONIBILIDAD (HORARIO)------------------
	public boolean estaDisponible(){
		return(estaDisponible(this.getHorariosAtencion()));	//Usa el metodo de POI (interfaz Horario), fecha=HOY
	}
	public boolean estaDisponible(int dia, String hora){
		return(estaDisponible(this.getHorariosAtencion(), dia, hora));	//Usa el metodo de POI, pero con la fecha y hora determinadas
	}
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
		double LongPuntoAnguloRecto = longitud;
		
		distanciaHorizontal = Haversine.distance(latitud, longitud, LatPuntoAnguloRecto, LongPuntoAnguloRecto);
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
