package procesos;

import java.util.Date;
import java.util.TimerTask;

import terminales.Terminal;

public abstract class Proceso extends TimerTask  {
	
	protected Terminal terminal;
	protected ControlProcesos controladorProcesos;
	protected ResultadoDeProceso resultado;
	protected Date fechaEjecucion;
	protected int reintentos, cantidadAfectados=0;
	
	public Proceso(Terminal terminal){
		controladorProcesos = new ControlProcesos();
		resultado = new ResultadoDeProceso();
		this.terminal = terminal;
	}
	
	
	public void run() {
		int i=1;
		resultado.setFecha(fechaEjecucion);
		resultado.setTipoProceso(this);
		do{
			try{
				this.ejecutar();
				resultado.setEstadoEjecucion(true);
				controladorProcesos.getManejoResultados().agregarResultado(resultado);
				break;
			}
			catch( Exception e) {
				resultado.setEstadoEjecucion(false);
				controladorProcesos.getManejoResultados().agregarResultado(resultado);
				i++;
			}
		}while(i<=reintentos);
	}

	private void ejecutar() throws Exception {
		cantidadAfectados = this.ejecutarProceso();        //TODO crear nueva clase que guarde los resultados?
		resultado.setElementosAfectados(cantidadAfectados);
	}

	int ejecutarProceso() throws Exception{
		// se overridea
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


	public ControlProcesos getControladorProcesos() {
		return controladorProcesos;
	}


	public void setControladorProcesos(ControlProcesos controladorProcesos) {
		this.controladorProcesos = controladorProcesos;
	}


	public int getCantidadAfectados() {
		return cantidadAfectados;
	}


	public void setCantidadAfectados(int cantidadAfectados) {
		this.cantidadAfectados = cantidadAfectados;
	}


	public Terminal getTerminal() {
		return terminal;
	}


	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

}
