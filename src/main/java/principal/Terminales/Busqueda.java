package principal.Terminales;

import org.joda.time.LocalDate;


public class Busqueda {
	
	private String fraseBuscada;
	public int cantidadResultados;
	private double tiempoBusqueda;
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