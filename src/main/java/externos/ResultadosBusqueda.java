package externos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;

public class ResultadosBusqueda {
	
	private String fraseBuscada;
	private int cantidadResultados;
	private LocalDate fecha;

	private int tiempoBusqueda;
	
	public ResultadosBusqueda(){
		new LocalDate();
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
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
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
		
}
