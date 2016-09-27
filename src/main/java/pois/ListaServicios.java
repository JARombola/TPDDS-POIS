package pois;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalTime;

import tiposPoi.Servicio;
@org.mongodb.morphia.annotations.Embedded
@Entity
public class ListaServicios {
	@org.mongodb.morphia.annotations.Transient
	@Id @GeneratedValue
	private int id;
	
	@org.mongodb.morphia.annotations.Embedded
	@OneToMany @Cascade(value = CascadeType.ALL)
	private List<Servicio> servicios;
	
	public ListaServicios(){	
		servicios=new ArrayList<Servicio>();
	}
	
	public List<Servicio> getServicios() {
		return servicios;
	}
	
	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}
	
	public boolean estaDisponible(int dia, LocalTime hora, String servicioBuscado){
		boolean abierto=(getServicios().stream()
				.filter(unServicio->(unServicio.tienePalabra(servicioBuscado)))		
				.anyMatch(unServicio->unServicio.estaDisponible(dia,hora)));		
		return abierto;
	}
	
	public void agregarServicio(Servicio servicioNuevo){
		servicios.add(servicioNuevo);
	}
	
	public boolean tienePalabra(String palabra){
		return getServicios().stream().anyMatch(servicio->servicio.tienePalabra(palabra));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

