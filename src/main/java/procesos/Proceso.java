package procesos;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import configuracionTerminales.EnviadorMails;

public abstract class Proceso extends TimerTask  {
	
	protected DatosProceso resultado;
	protected Date fechaEjecucion;
	protected int cantidadReintentos, cantidadAfectados=-1;
	protected boolean opcionMail;
	
	public Proceso(){
		cantidadReintentos=0;
		resultado = new DatosProceso();
	}
		
	public void run() {
		int i=0;
		boolean ejecucionOk;
		ControlProcesos controladorProcesos = ControlProcesos.getInstancia();
		resultado.setFecha(fechaEjecucion);
		resultado.setTipoProceso(this);
		do{
			try{
				cantidadAfectados=ejecutar();
				ejecucionOk=true;
				break;
			}
			catch(ExcepcionFalloConfiguracion | IOException a) {
				ejecucionOk=false;
				i++;
			}
		}while(i<cantidadReintentos);
		
		resultado.setEstadoEjecucion(ejecucionOk);
		resultado.setElementosAfectados(cantidadAfectados);
		controladorProcesos.guardarResultado(resultado);
		
		if(!ejecucionOk) tratarFalla();
	}

	abstract int ejecutar() throws IOException;			//devuelve la cantidad de afectados
	
	
	private void tratarFalla(){
		if (isOpcionMail()){
			EnviadorMails.getInstancia().mailFallaProceso(this);
		}
	}
	
	
	public void setFecha(Date fecha) {
		this.fechaEjecucion=fecha;
	}
	public Date getFecha(){
		return this.fechaEjecucion;
	}
	public void setReintentos(int reintentos) {
		this.cantidadReintentos=reintentos;
	}


	public DatosProceso getResultado() {
		return resultado;
	}


	public void setResultado(DatosProceso resultado) {
		this.resultado = resultado;
	}

	public int getCantidadAfectados() {
		return cantidadAfectados;
	}


	public void setCantidadAfectados(int cantidadAfectados) {
		this.cantidadAfectados = cantidadAfectados;
	}

	public boolean isOpcionMail() {
		return opcionMail;
	}

	public void setOpcionMail(boolean opcionMail) {
		this.opcionMail = opcionMail;
	}

}
