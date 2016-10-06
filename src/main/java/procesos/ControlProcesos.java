package procesos;

import java.util.Timer;


public class ControlProcesos {
//	TODO: No me gusta esta clase. La veo innecesaria. 
//	No sería lógico que el proceso mismo sepa qué hay que hacer ante una falla o ante una ejecución exitosa? 
//	Y que el mismo conozca el "manejo de resultados" que tiene que utilizar? - Aldana

	private static ControlProcesos control;			//Singleton
	private Timer timer;
	
	public ControlProcesos() {
		timer = new Timer();
	}
	
	public static ControlProcesos getInstancia(){
		if(control==null) 
			control = new ControlProcesos();
		return control;
	}
	
	public void agregarProceso(Proceso procesoNuevo){
		timer.schedule(procesoNuevo, procesoNuevo.getFecha());
	}

	public void guardarResultado(DatosProceso resultado) {
		ResultadosProcesos.getInstacia().agregarResultado(resultado);
		
	}

}
