package principal.Terminales;

import org.joda.time.LocalDate;

public class DatosReporte {
	private LocalDate fecha;
	private String terminal;
	private int datos;

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
	public int getDatos() {
		return datos;
	}
	public void setDatos(int datos) {
		this.datos = datos;
	}
	
	

}
