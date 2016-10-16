package tiposPoi;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalTime;

import pois.ListaServicios;
import pois.POI;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@org.mongodb.morphia.annotations.Entity(value="POI")
@Entity
public class Banco extends POI{
	
	private String sucursal;
	private String gerente;
	@org.mongodb.morphia.annotations.Transient
	@Transient 
	int LUNES=1,VIERNES=5;
	
	@org.mongodb.morphia.annotations.Embedded
	@OneToOne @Cascade(value = CascadeType.ALL)
	private ListaServicios listaServicios;
	
	@org.mongodb.morphia.annotations.Transient
	@Transient 
	LocalTime INICIO=new LocalTime(10,00), FIN= new LocalTime(15,00);
	
	public Banco(){
		super();
		listaServicios=new ListaServicios();
	}
	
	public void modificar(Banco poiEntrante){
		if(poiEntrante.getListaServicios().getServicios().size()>0){
			listaServicios = poiEntrante.getListaServicios();
		}
		super.modificar(poiEntrante);
	}
	
	//------------------------DISPONIBILIDAD------------------
	
	public void agregarServicio(Servicio unServicio){
		this.listaServicios.agregarServicio(unServicio);
	}

	
	private boolean estaDisponibleSinServicio(int dia, LocalTime hora){
		return ((dia>=LUNES) && (dia<=VIERNES) && (hora.isAfter(INICIO))&& (hora.isBefore(FIN)));
	}
	
	public boolean estaDisponible(int dia, LocalTime hora,String servicioBuscado){
		if(servicioBuscado=="")
			return estaDisponibleSinServicio(dia, hora);
		else
			return getListaServicios().estaDisponible(dia, hora, servicioBuscado);
	}
	
	//---------------BUSQUEDA-----------------------------------
	
	public boolean tienePalabra(String texto){
		return (super.tienePalabra(texto) || tienePalabraEnServicio(texto));
		
	}
	
	public boolean tienePalabraEnServicio(String texto){
		return getListaServicios().tienePalabra(texto);
	}
	
		
	// -------------------GETTERS,SETTERS-----------------
	public ListaServicios getListaServicios() {
		return listaServicios;
	}
	
	public void setServicios(ListaServicios servicios) {
		this.listaServicios = servicios;
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

	public void setListaServicios(ListaServicios listaServicios) {
		this.listaServicios = listaServicios;
	}

}