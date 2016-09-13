package terminales;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name="Busquedas")
public class Busqueda {
	@Id @GeneratedValue
	private int id;
	@Column(name="frase_buscada")
	private String fraseBuscada;
	public int cantidadResultados;
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
		return cantidadResultados;
	}
	public void setCantidadResultados(int cantidadResultados) {
		this.cantidadResultados = cantidadResultados;
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
	
	
}