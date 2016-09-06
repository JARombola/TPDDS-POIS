package terminales;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="busquedas")

public class Busqueda {
	@Id @GeneratedValue
	private int id;
	
	private String fraseBuscada;
	public int cantidadResultados;
	private double tiempoBusqueda;
	//@Convert -> CONVERTIR LOCALDATE Para que funque con la BD.
	private LocalDate fecha; 
	
	public Busqueda(){
		this.fecha=LocalDate.now();
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