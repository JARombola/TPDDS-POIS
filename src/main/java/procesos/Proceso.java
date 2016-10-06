package procesos;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;


import terminales.ControlTerminales;

public abstract class Proceso extends TimerTask  {
	
	protected ControlTerminales centralTerminales;
	protected ControlProcesos controladorProcesos;
	protected ResultadoDeProceso resultado;
	protected Date fechaEjecucion;
	protected int reintentos, cantidadAfectados=0;
	
	public Proceso(){
		resultado = new ResultadoDeProceso();
	}
		
	public void run() {
		controladorProcesos = ControlProcesos.getInstancia();
		centralTerminales = ControlTerminales.getInstancia();
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
			catch(ExcepcionFalloConfiguracion e) {
				resultado.setEstadoEjecucion(false);
				controladorProcesos.guardarResultado(resultado);
				controladorProcesos.tratarResultado(resultado, e.getTerminal().getAdministrador());
				i++;
			} catch (IOException e) {
				// TODO Fall� al leer archivo para actualizar comerciales
				e.printStackTrace();
			}
		}while(i<=reintentos);
	}

	private void ejecutar() throws IOException{
//		TODO: Este método podrían sacarlo, porque tienen muchos métodos que se llaman parecido y confunde un poco. 
//		Y ya que lo sacan, 'ejecutarProceso' (el método de abajo) lo pueden renombrar para que se llame simplemente
//		'ejecutar', ya que 'proceso.ejecutarProceso()' suena redundante -Aldana
		
		cantidadAfectados = ejecutarProceso();        //TODO crear nueva clase que guarde los resultados?
		resultado.setElementosAfectados(cantidadAfectados);
	}

	abstract int ejecutarProceso() throws IOException;
	
	
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
