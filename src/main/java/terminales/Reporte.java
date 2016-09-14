package terminales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
public class Reporte {
	
	@Id @GeneratedValue
	private int idReporte;
	
	@ElementCollection @Embedded 
	private List<DatosReporte> datos;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate fecha;
	private String terminal;
	private String tipoReporte;
	
	public Reporte(){
		datos = new ArrayList<DatosReporte>();
	}
	
	public Reporte(String tipo){
		tipoReporte = tipo;
		datos = new ArrayList<DatosReporte>();
		fecha = LocalDate.now();
	}
	
	public void agregarDatos(LocalDate fecha, int resultados){
		DatosReporte datosReporte = new DatosReporte(fecha,resultados);
		datos.add(datosReporte);
	}
	
	public void agregarDatos(DatosReporte datosReporte){
		datos.add(datosReporte);
	}
	
	
	public List<DatosReporte> getDatos() {
		return datos;
	}
	public void setDatos(List<DatosReporte> datos) {
		this.datos = datos;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	public void mostrarInformacion(){
		System.out.println(getFecha() + " ----" + getTerminal() + "("+getTipoReporte()+")\nBUSQUEDAS:");
		getDatos().stream().forEach(d -> System.out.println(d.getFecha() + " --- Cantidad Resultados: " + d.getResultados()));
	}
}