package tipos;


import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import principal.EntesConServicios;
import principal.POI;


public class Banco extends POI{
	
	private String sucursal;
	private String gerente;

	
	int LUNES=1,VIERNES=5;
	private EntesConServicios servicios;
	
	public void modificar(Banco poiEntrante){
		if(poiEntrante.getServicios().getServicios().size()>0){
			servicios = poiEntrante.getServicios();
		}
		super.modificar(poiEntrante);
	}
	
	//------------------------DISPONIBILIDAD------------------
	
	LocalTime INICIO=new LocalTime(10,00),
			  FIN= new LocalTime(15,00);
	
	public Banco(){
		servicios=new EntesConServicios();
	}
	

	public void agregarServicio(Servicio unServicio){
		this.servicios.agregarServicio(unServicio);
	}
	
	
	private boolean estaDisponibleSinServicio(int dia, LocalTime hora){
		return ((dia>=LUNES) && (dia<=VIERNES) && (hora.isAfter(INICIO))&& (hora.isBefore(FIN)));
	}
	
	public boolean estaDisponible(int dia, LocalTime hora,String servicioBuscado){
		if(servicioBuscado=="")
			return estaDisponibleSinServicio(dia, hora);
		else
			return getServicios().estaDisponible(dia, hora, servicioBuscado);
	}
	
	//---------------BUSQUEDA-----------------------------------
	
	public boolean tienePalabra(String texto){
		return (super.tienePalabra(texto) || tienePalabraEnServicio(texto));
		
	}
	
	public boolean tienePalabraEnServicio(String texto){
		return getServicios().tienePalabra(texto);
	}
	
		
	// -------------------GETTERS,SETTERS-----------------
	public EntesConServicios getServicios() {
		return servicios;
	}
	
	
	public String getSucursal() {
		return sucursal;
	}


	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}


	public String getGerente() {
		return gerente;
	}


	public void setGerente(String gerente) {
		this.gerente = gerente;
	}
}