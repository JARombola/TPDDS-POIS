package terminales;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import pois.POI;

@Entity
@Table(name="Busquedas")

public class Busqueda {
	@Id @GeneratedValue
	private int id;
	@Column(name="frase_buscada")
	
	private String fraseBuscada;
	@ManyToMany 
	@JoinColumn @Cascade(value=CascadeType.ALL)
	public List<POI> resultados;
	private double tiempoBusqueda;

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate fecha; 

	public Busqueda(){
	}
	
	public String getFraseBuscada() {
		return fraseBuscada;
	}
	public void setFraseBuscada(String fraseBuscada) {
		this.fraseBuscada = fraseBuscada;
	}
	public int getCantidadResultados() {
		return resultados.size();
	}
	public List<POI> getResultados() {
		return resultados;
	}
	public void setResultados(List<POI> resultados) {
		this.resultados = resultados;
	}
	public double getTiempoBusqueda() {
		return tiempoBusqueda;
	}
	public void setTiempoBusqueda(double tiempoBusqueda) {
		this.tiempoBusqueda = tiempoBusqueda;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}