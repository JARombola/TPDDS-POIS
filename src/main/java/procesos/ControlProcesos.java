package procesos;

import java.util.Timer;

import configuracionTerminales.Administrador;

public class ControlProcesos {
//	TODO: No me gusta esta clase. La veo innecesaria. 
//	No sería lógico que el proceso mismo sepa qué hay que hacer ante una falla o ante una ejecución exitosa? 
//	Y que el mismo conozca el "manejo de resultados" que tiene que utilizar? - Aldana

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
