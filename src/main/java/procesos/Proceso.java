package procesos;

import java.util.Date;
import java.util.TimerTask;

import configuracionTerminales.Administrador;
import principal.Terminales.Mapa;

public abstract class Proceso extends TimerTask  {
	protected Mapa mapa;
	protected static Administrador admin;
	protected ControlProcesos controladorProcesos;
	protected ResultadoDeProceso resultado;
	protected Date fechaEjecucion;
	protected int reintentos, cantidadAfectados=0;
	
	public Proceso(Administrador administrador){
		mapa = administrador.getTerminal().getMapa();
		controladorProcesos = administrador.getControlador();
		resultado = new ResultadoDeProceso();
		admin = administrador;
	}
	
	
	public void run() {
		int i=0;
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
		cantidadAfectados = this.ejecutarProceso();        //verificar como queda si tira excepcion
		resultado.setElementosAfectados(cantidadAfectados);
	}

	int ejecutarProceso() throws Exception{
		// se overridea
		return 0;
	}
	
	
	public void setMapa(Mapa mapa) {
		this.mapa=mapa;
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

}
