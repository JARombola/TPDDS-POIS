package terminales;

import javax.persistence.Entity;

import org.joda.time.LocalDate;

@Entity
public class DatosReporte {
	
	private LocalDate fecha;
	private int resultados;

	public DatosReporte(){
		
	}
	
	public DatosReporte(LocalDate fecha, int resultados){
		this.fecha=fecha;
		this.resultados=resultados;
	}
	
	
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getResultados() {
		return resultados;
	}

	public void setResultados(int resultados) {
		this.resultados = resultados;
	}

}
