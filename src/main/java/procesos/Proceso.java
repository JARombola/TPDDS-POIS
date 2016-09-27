package procesos;

import java.util.Date;
import java.util.TimerTask;


import terminales.ControlTerminales;

public abstract class Proceso extends TimerTask  {
	
	protected ControlTerminales centralTerminales;
	protected ControlProcesos controladorProcesos;	//lo conoce para poder enviarle los Resultados
	protected ResultadoDeProceso resultado;
	protected Date fechaEjecucion;
	protected int reintentos, cantidadAfectados=0;
	
	public Proceso(ControlProcesos control, ControlTerminales terminales){
		resultado = new ResultadoDeProceso();
		controladorProcesos=control;
		centralTerminales=terminales;
	}
		
	public void run() {
		int i=0;
		resultado.setFecha(fechaEjecucion);
		resultado.setTipoProceso(this);
		do{
			try{
				this.ejecutar();
				resultado.setEstadoEjecucion(true);
				controladorProcesos.guardarResultado(resultado);
				break;
			}
			catch(ExcepcionFallo e) {
				resultado.setEstadoEjecucion(false);
				controladorProcesos.guardarResultado(resultado);
				controladorProcesos.tratarResultado(resultado, e.getTerminal().getAdministrador());
				i++;
			}
		}while(i<=reintentos);
	}

	private void ejecutar() {
//		TODO: Este método podrían sacarlo, porque tienen muchos métodos que se llaman parecido y confunde un poco. 
//		Y ya que lo sacan, 'ejecutarProceso' (el método de abajo) lo pueden renombrar para que se llame simplemente
//		'ejecutar', ya que 'proceso.ejecutarProceso()' suena redundante -Aldana
		
		cantidadAfectados = this.ejecutarProceso();        //TODO crear nueva clase que guarde los resultados?
		resultado.setElementosAfectados(cantidadAfectados);
	}

	int ejecutarProceso(){
		// se overridea
		//TODO: Si se overridea entonces que sea abstracto. - Aldana
		return 0;
	}
	
	
	public void setFecha(Date fecha) {
		this.fechaEjecucion=fecha;
	}
	public Date getFecha(){
		return this.fechaEjecucion;
	}
	public void setReintentos(int reintentos) {
		this.reintentos=reintentos;
	}


	public ResultadoDeProceso getResultado() {
		return resultado;
	}


	public void setResultado(ResultadoDeProceso resultado) {
		this.resultado = resultado;
	}

	public int getCantidadAfectados() {
		return cantidadAfectados;
	}


	public void setCantidadAfectados(int cantidadAfectados) {
		this.cantidadAfectados = cantidadAfectados;
	}


	public ControlProcesos getControladorProcesos() {
		return controladorProcesos;
	}


	public void setControladorProcesos(ControlProcesos controladorProcesos) {
		this.controladorProcesos = controladorProcesos;
	}

	public ControlTerminales getCentralTerminales() {
		return centralTerminales;
	}

	public void setCentralTerminales(ControlTerminales centralTerminales) {
		this.centralTerminales = centralTerminales;
	}
}
