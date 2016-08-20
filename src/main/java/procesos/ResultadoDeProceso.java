package procesos;

import java.util.Date;

public class ResultadoDeProceso {
	
	private int elementosAfectados;
	private boolean estadoEjecucion;
	private Date fecha;
	private Proceso tipoProceso;

	
	public int getElementosAfectados() {
		return elementosAfectados;
	}
	public void setElementosAfectados(int elementosAfectados) {
		this.elementosAfectados = elementosAfectados;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isEstadoEjecucion() {
		return estadoEjecucion;
	}
	public void setEstadoEjecucion(boolean estadoEjecucion) {
		this.estadoEjecucion = estadoEjecucion;
	}
	public Proceso getTipoProceso() {
		return tipoProceso;
	}
	public void setTipoProceso(Proceso tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

}
