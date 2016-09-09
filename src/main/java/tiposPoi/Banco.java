package tiposPoi;

import org.joda.time.LocalTime;
import pois.ListaServicios;
import pois.POI;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Banco extends POI{
	@Id @GeneratedValue
	private int id;
	private String sucursal;
	private String gerente;
	@Transient
	int LUNES=1,VIERNES=5;
	private ListaServicios servicios;
	@Transient
	LocalTime INICIO=new LocalTime(10,00), FIN= new LocalTime(15,00);
	
	public Banco(){
		servicios=new ListaServicios();
	}
	
	public void modificar(Banco poiEntrante){
		if(poiEntrante.getServicios().getServicios().size()>0){
			servicios = poiEntrante.getServicios();
		}
		super.modificar(poiEntrante);
	}
	
	//------------------------DISPONIBILIDAD------------------
	

	
	
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
	public ListaServicios getServicios() {
		return servicios;
	}
	
	public void setServicios(ListaServicios servicios) {
		this.servicios = servicios;
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