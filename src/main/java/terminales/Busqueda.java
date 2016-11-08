package terminales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.joda.time.LocalDate;

import pois.POI;

@org.mongodb.morphia.annotations.Entity

public class Busqueda {
	
	@org.mongodb.morphia.annotations.Id
	private int id;
	
	private int idTerminal;

	@Column(name="frase_buscada")
	private String fraseBuscada;
	
	@org.mongodb.morphia.annotations.Embedded
	public List<POI> resultados;
	
	private double tiempoBusqueda;

	private LocalDate fecha; 
	

	public Busqueda(){
		resultados=new ArrayList<POI>();
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

	
	public String getNombreTerminal() {
		return RepositorioTerminales.getInstancia().getTerminal(idTerminal).getNombre();
	
	}
	
	public int getIdTerminal() {
		return idTerminal;
	}

	public void setIdTerminal(int idTerminal) {
		this.idTerminal = idTerminal;
	}

	
}