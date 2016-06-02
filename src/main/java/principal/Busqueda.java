package principal;

import org.joda.time.LocalDate;


public class Busqueda {
	
	private String fraseBuscada;
	public int cantidadResultados;
	private int tiempoBusqueda;
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
	public int getTiempoBusqueda() {
		return tiempoBusqueda;
	}
	public void setTiempoBusqueda(int tiempoBusqueda) {
		this.tiempoBusqueda = tiempoBusqueda;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	
}