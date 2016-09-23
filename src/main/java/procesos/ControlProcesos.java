package procesos;

import java.util.Timer;

import configuracionTerminales.Administrador;

public class ControlProcesos {
	private ManejoDeResultadosProcesos manejoResultados;
	private Timer timer;
	
	public ControlProcesos() {
		manejoResultados = new ManejoDeResultadosProcesos();
		timer = new Timer();
	}
	
	public void agregarProceso(Proceso procesoNuevo){
		timer.schedule(procesoNuevo, procesoNuevo.getFecha());
	}

	
	public ManejoDeResultadosProcesos getManejoResultados() {
		return manejoResultados;
	}
	public void setManejoResultados(ManejoDeResultadosProcesos manejoResultados) {
		this.manejoResultados = manejoResultados;
	}

	public void guardarResultado(ResultadoDeProceso resultado) {
		manejoResultados.agregarResultado(resultado);
		
	}

	public void tratarResultado(ResultadoDeProceso resultado, Administrador admin) {
		getManejoResultados().tratarResultado(resultado, admin);
		
	}

}
