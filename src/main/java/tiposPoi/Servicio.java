package tiposPoi;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.LocalTime;

import pois.Horario;
import pois.ListaHorarios;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@org.mongodb.morphia.annotations.Embedded
@Entity
public class Servicio{
	
	@org.mongodb.morphia.annotations.Transient
	@Id @GeneratedValue
	private int id;
	
	@org.mongodb.morphia.annotations.Transient
	@OneToOne @Cascade(value = CascadeType.ALL)
	private ListaHorarios horarios;
	
	private String nombre;
	private String descripcion;
	@org.mongodb.morphia.annotations.Embedded
	@ElementCollection
	private List<String> tags;
	

	public Servicio() {

	}
	
	public Servicio(String nombre) {
		horarios = new ListaHorarios();
		tags = new ArrayList<String>();
		this.setNombre(nombre);
	}

	// -------------------GETTERS,SETTERS-----------------
	public List<String> getTags() {
		return tags;
	}
	
	public void agregarTag(String tag) {
		this.tags.add(tag);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ListaHorarios getHorarios() {
		return horarios;
	}
	public boolean tienePalabra(String palabra){
		return (getNombre().contains(palabra) || getTags().contains(palabra));
	}
	
	public boolean estaDisponible(int dia,LocalTime hora){
		return getHorarios().estaDisponible(dia,hora);
	}

	public void setHorarios(ListaHorarios horarios) {
		this.horarios = horarios;
	}
	
	public void agregarHorario(Horario unHorario){
		this.getHorarios().getHorariosAtencion().add(unHorario);
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
