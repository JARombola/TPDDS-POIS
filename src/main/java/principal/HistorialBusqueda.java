package principal;

import java.util.List;

import org.joda.time.LocalDate;


public class HistorialBusqueda {
	
	private String fraseBuscada;
	public int cantidadResultados;
	private double tiempoBusqueda;
	private LocalDate fecha; 
	private List<POI> resultadosBusqueda;
	
	public List<POI> getResultadosBusqueda() {
		return resultadosBusqueda;
	}


	public void setResultadosBusqueda(List<POI> resultadosBusqueda) {
		this.resultadosBusqueda = resultadosBusqueda;
	}


	public HistorialBusqueda(){
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