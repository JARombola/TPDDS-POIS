package terminales;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Embeddable
public class DatosReporte {
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
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
